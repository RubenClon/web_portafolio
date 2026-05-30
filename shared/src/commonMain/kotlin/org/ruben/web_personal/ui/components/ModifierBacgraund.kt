package org.ruben.web_personal.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.sin
import kotlin.math.sqrt

// ─── Tokens de color del fondo de cuadrícula ──────────────────────────────────
private val GridBackground = Color(0xFF272542)   // blanco/gris base
private val GridSquare     = Color(0xFF757678)   // cuadrado gris oscuro
private val GridShadow     = Color(0xFF454C5F)   // color base para la sombra

// ─── Modo de crecimiento ───────────────────────────────────────────────────────

/**
 * Controla cómo se distribuye el tamaño de los cuadrados por la superficie.
 * El progreso interpolado va de [minSquareSize] (0.0) a [maxSquareSize] (1.0).
 */
sealed interface GridGrowth {
    /** Todos los cuadrados del mismo tamaño (usa maxSquareSize). */
    data object None : GridGrowth

    /** Crece en diagonal: esquina superior-izquierda (pequeño) → inferior-derecha (grande). */
    data object Diagonal : GridGrowth

    /** Crece desde el centro hacia los cuatro bordes. */
    data object Radial : GridGrowth

    /** Crece de izquierda a derecha. */
    data object Horizontal : GridGrowth

    /** Crece de arriba a abajo. */
    data object Vertical : GridGrowth
}

// ─── Modificador reutilizable ─────────────────────────────────────────────────

/**
 * Pinta un fondo de cuadrícula con cuadrados y sombra de elevación.
 *
 * El tamaño de cada cuadrado varía entre [minSquareSize] y [maxSquareSize] según
 * el [growth] elegido. La cuadrícula usa una celda fija (basada en [maxSquareSize] + [gap])
 * para que la rejilla permanezca alineada; los cuadrados más pequeños se centran en su celda.
 *
 * @param minSquareSize   Lado mínimo del cuadrado (origen del gradiente de tamaño).
 * @param maxSquareSize   Lado máximo del cuadrado. Por defecto ~5 mm a 96 dpi.
 * @param gap             Separación base entre cuadrados (aplica al tamaño máximo).
 * @param elevation       Desplazamiento máximo de la sombra (profundidad visual).
 * @param growth          Patrón de distribución del crecimiento.
 * @param scrollOffset    Desplazamiento acumulado del scroll (en píxeles) para efecto parallax.
 * @param rotationAngle   Giro en grados aplicado a cada cuadrado mientras hay movimiento de scroll.
 * @param backgroundColor Color de fondo de toda la superficie.
 * @param squareColor     Color de relleno de los cuadrados.
 * @param shadowColor     Color base de la sombra (su alpha se modula por capas).
 */
fun Modifier.gridBackground(
    minSquareSize: Dp      = 6.dp,
    maxSquareSize: Dp      = 19.dp,
    gap: Dp                = 6.dp,
    elevation: Dp          = 4.dp,
    growth: GridGrowth     = GridGrowth.Diagonal,
    scrollOffset: Float    = 0f,
    rotationAngle: Float   = 0f,
    backgroundColor: Color = GridBackground,
    squareColor: Color     = GridSquare,
    shadowColor: Color     = GridShadow,
): Modifier = this.drawBehind {

    // 1. Fondo plano ────────────────────────────────────────────────────────────
    drawRect(color = backgroundColor)

    val sqMinPx = minSquareSize.toPx()
    val sqMaxPx = maxSquareSize.toPx()
    val gapPx   = gap.toPx()
    val elPx    = elevation.toPx()
    val cell    = sqMaxPx + gapPx   // celda fija → cuadrícula siempre alineada

    val cols = (size.width  / cell).toInt() + 2
    val rows = (size.height / cell).toInt() + 2

    // Parallax: el fondo se desplaza a una fracción del scroll del contenido.
    // Tomamos módulo de la celda para que el patrón sea infinito y sin saltos.
    val rawOffset = scrollOffset * 0.25f
    val yOffset   = rawOffset % cell  // valor en [-cell, +cell]

    // 2. Cuadrados con tamaño variable y sombra escalonada ──────────────────────
    // Empezamos en row = -1 para rellenar el hueco superior cuando yOffset > 0
    for (row in -1..rows) {
        for (col in 0..cols) {
            val clampedRow = row.coerceIn(0, rows)
            val progress   = growthProgress(growth, clampedRow, col, rows, cols)
            val currentSq  = sqMinPx + (sqMaxPx - sqMinPx) * progress

            // Centrar el cuadrado dentro de su celda fija
            val shift = (sqMaxPx - currentSq) / 2f
            val x = col * cell + shift
            val y = row * cell + shift + yOffset

            // Pivote = centro del cuadrado para rotar alrededor de él
            val cx = x + currentSq / 2f
            val cy = y + currentSq / 2f

            // Fase única por cuadrado: combina row y col con frecuencias distintas
            // para evitar simetrías repetitivas. Resultado en [-1, 1].
            val phase = sin(row * 1.3f + col * 0.9f).toFloat()
            val squareRotation = rotationAngle * phase

            withTransform({
                rotate(degrees = squareRotation, pivot = Offset(cx, cy))
            }) {
                // Capa de sombra 1 — más difusa, más alejada
                drawRect(
                    color   = shadowColor.copy(alpha = 0.10f),
                    topLeft = Offset(x + elPx, y + elPx),
                    size    = Size(currentSq + elPx * 0.5f, currentSq + elPx * 0.5f),
                )

                // Capa de sombra 2 — media distancia
                drawRect(
                    color   = shadowColor.copy(alpha = 0.25f),
                    topLeft = Offset(x + elPx * 0.55f, y + elPx * 0.55f),
                    size    = Size(currentSq, currentSq),
                )

                // Capa de sombra 3 — muy pegada al cuadrado, más opaca
                drawRect(
                    color   = shadowColor.copy(alpha = 0.35f),
                    topLeft = Offset(x + elPx * 0.25f, y + elPx * 0.25f),
                    size    = Size(currentSq, currentSq),
                )

                // Cuadrado principal
                drawRect(
                    color   = squareColor,
                    topLeft = Offset(x, y),
                    size    = Size(currentSq, currentSq),
                )
            }
        }
    }
}

// ─── Función interna de progreso [0..1] ───────────────────────────────────────

private fun growthProgress(
    growth: GridGrowth,
    row: Int, col: Int,
    totalRows: Int, totalCols: Int,
): Float = when (growth) {

    GridGrowth.None -> 1f

    GridGrowth.Diagonal -> {
        val rx = if (totalCols > 0) col.toFloat() / totalCols else 0f
        val ry = if (totalRows > 0) row.toFloat() / totalRows else 0f
        ((rx + ry) / 2f).coerceIn(0f, 1f)
    }

    GridGrowth.Horizontal -> {
        if (totalCols > 0) (col.toFloat() / totalCols).coerceIn(0f, 1f) else 1f
    }

    GridGrowth.Vertical -> {
        if (totalRows > 0) (row.toFloat() / totalRows).coerceIn(0f, 1f) else 1f
    }

    GridGrowth.Radial -> {
        val cx = totalCols / 2f
        val cy = totalRows / 2f
        val dx = col - cx
        val dy = row - cy
        val dist    = sqrt(dx * dx + dy * dy)
        val maxDist = sqrt(cx * cx + cy * cy)
        if (maxDist > 0f) (dist / maxDist).coerceIn(0f, 1f) else 0f
    }
}
