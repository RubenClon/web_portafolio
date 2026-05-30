package org.ruben.web_personal.ui.sections.personal

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.ruben.web_personal.domain.PersonalInfo
import org.ruben.web_personal.ui.theme.PersonalPrimary
import org.ruben.web_personal.ui.theme.SurfaceVariant
import web_personal.shared.generated.resources.Res
import web_personal.shared.generated.resources.STK

private val COMPACT_BREAKPOINT = 600.dp

@Composable
fun HeroSection(info: PersonalInfo) {
    var displayedRole by remember { mutableStateOf("") }

    val infiniteTransition = rememberInfiniteTransition(label = "cursor")
    val cursorAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue  = 0f,
        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse),
        label        = "cursorAlpha",
    )

    LaunchedEffect(info.role) {
        displayedRole = ""
        delay(400)
        info.role.forEachIndexed { i, _ ->
            delay(55)
            displayedRole = info.role.substring(0, i + 1)
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PersonalPrimary.copy(alpha = 0.08f),
                        Color.Transparent,
                    ),
                )
            ),
    ) {
        val isCompact = maxWidth < COMPACT_BREAKPOINT

        if (isCompact) {
            // ── Móvil: columna centrada ────────────────────────────────────────
            Column(
                modifier            = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                ProfileImage(size = 160.dp)

                VentanaTerminal(
                    info          = info,
                    displayedRole = displayedRole,
                    cursorAlpha   = cursorAlpha,
                    modifier      = Modifier.fillMaxWidth(),
                )
            }
        } else {
            // ── Escritorio: fila imagen + terminal ────────────────────────────
            Row(
                modifier            = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp, vertical = 96.dp),
                horizontalArrangement = Arrangement.spacedBy(48.dp),
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                ProfileImage(size = 260.dp)

                VentanaTerminal(
                    info          = info,
                    displayedRole = displayedRole,
                    cursorAlpha   = cursorAlpha,
                    modifier      = Modifier.weight(1f),
                )
            }
        }
    }
}

// ─── Imagen de perfil circular ────────────────────────────────────────────────

@Composable
private fun ProfileImage(size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .shadow(elevation = 16.dp, shape = CircleShape, spotColor = PersonalPrimary)
            .clip(CircleShape)
            .background(SurfaceVariant),
    ) {
        Image(
            painter            = painterResource(Res.drawable.STK),
            contentDescription = "Foto de perfil",
            modifier           = Modifier
                .size(size)
                .clip(CircleShape),
            contentScale       = ContentScale.Crop,
        )
    }
}
