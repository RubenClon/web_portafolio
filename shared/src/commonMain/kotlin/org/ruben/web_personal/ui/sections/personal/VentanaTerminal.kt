package org.ruben.web_personal.ui.sections.personal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.ruben.web_personal.domain.PersonalInfo
import org.ruben.web_personal.ui.theme.PersonalPrimary
import org.ruben.web_personal.ui.theme.PersonalSecondary

private val TerminalShape = RoundedCornerShape(8.dp)
private val TerminalBg    = Color(0xFF1A1A1B)
private val BarTop        = Color(0xFF3D3D3E)
private val BarBottom     = Color(0xFF2C2C2E)
private val Prompt        = Color(0xFF9A9B9E)

// ─── Ventana de terminal estilo macOS ────────────────────────────────────────

@Composable
fun VentanaTerminal(
    info: PersonalInfo,
    displayedRole: String,
    cursorAlpha: Float,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .shadow(elevation = 24.dp, shape = TerminalShape)
            .clip(TerminalShape)
            .background(TerminalBg)
            .border(1.dp, Color(0xFF757678), TerminalShape),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            TitleBar()

            // ── Contenido ─────────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // nombre
                TerminalLine(prompt = "nombre") {
                    Text(
                        text = info.name,
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }

                // role → typewriter con cursor parpadeante
                TerminalLine(prompt = "role") {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                MaterialTheme.typography.headlineLarge.toSpanStyle()
                                    .copy(color = PersonalSecondary),
                            ) { append(displayedRole) }
                            withStyle(
                                MaterialTheme.typography.headlineLarge.toSpanStyle()
                                    .copy(color = PersonalSecondary.copy(alpha = cursorAlpha)),
                            ) { append("|") }
                        },
                    )
                }

                // about → bio
                TerminalLine(prompt = "about") {
                    Text(
                        text = info.bio,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.widthIn(max = 560.dp),
                    )
                }
            }
        }
    }
}

// ─── Barra de título con dots macOS ──────────────────────────────────────────

@Composable
private fun TitleBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Brush.verticalGradient(listOf(BarTop, BarBottom))),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ControlDot(Color(0xFFFF5F57))   // cerrar
            Spacer(Modifier.width(6.dp))
            ControlDot(Color(0xFFFFBD2E))   // minimizar
            Spacer(Modifier.width(6.dp))
            ControlDot(Color(0xFF28C840))   // maximizar

            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    text = "bash — terminal",
                    style = MaterialTheme.typography.labelSmall,
                    color = Prompt,
                )
            }

            // Espacio simétrico para centrar el título visualmente
            Spacer(Modifier.width(54.dp))
        }
    }
}

@Composable
private fun ControlDot(color: Color) {
    Box(
        modifier = Modifier
            .size(12.dp)
            .shadow(elevation = 4.dp, shape = CircleShape)
            .clip(CircleShape)
            .background(color),
    )
}

// ─── Línea de terminal: prompt + contenido arbitrario ────────────────────────

@Composable
private fun TerminalLine(
    prompt: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    MaterialTheme.typography.bodyMedium.toSpanStyle()
                        .copy(color = PersonalSecondary),
                ) { append("* ") }
                withStyle(
                    MaterialTheme.typography.bodyMedium.toSpanStyle()
                        .copy(color = Prompt),
                ) { append(prompt) }
            },
        )
        content()
    }
}
