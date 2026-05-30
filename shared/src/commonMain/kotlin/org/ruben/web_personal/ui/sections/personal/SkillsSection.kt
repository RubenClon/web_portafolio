package org.ruben.web_personal.ui.sections.personal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ruben.web_personal.domain.Skill
import org.ruben.web_personal.presentation.navigation.Screen
import org.ruben.web_personal.ui.components.SectionTitle
import org.ruben.web_personal.ui.theme.GamesPrimary
import org.ruben.web_personal.ui.theme.GamesSecondary
import org.ruben.web_personal.ui.theme.KmpPrimary
import org.ruben.web_personal.ui.theme.KmpSecondary
import org.ruben.web_personal.ui.theme.PersonalSecondary
import org.ruben.web_personal.ui.theme.SurfaceVariant
import org.ruben.web_personal.ui.theme.WebPrimary
import org.ruben.web_personal.ui.theme.WebSecondary

// Tecnologías que tienen enlace directo a una sección del portfolio
private val skillNavigation: Map<String, Screen> = mapOf(
    "Kotlin" to Screen.Kmp,
    "Kotlin Multiplatform (KMP)" to Screen.Kmp,
    "GDScript (Godot)" to Screen.Games,
    "Godot Engine (2D/3D)" to Screen.Games,
)

// Color de acento propio para skills sin sección dedicada
private val JavaAccent = Color(0xFFE8821C)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillsSection(skills: List<Skill>, onNavigate: (Screen) -> Unit) {
    val grouped = skills.groupBy { it.category }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    brush = Brush.horizontalGradient(
                        0.0f to Color.White.copy(alpha = 0.8f),
                        1.0f to Color.Transparent,
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .background(
                brush = Brush.horizontalGradient(
                    0.0f to  SurfaceVariant.copy(alpha = 0.9f),
                    0.7f to SurfaceVariant.copy(alpha = 0.7f),
                    1.0f to Color.Transparent,
                )
            )
            .padding(horizontal = 48.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        SectionTitle(title = "Tecnologías y Herramientas", color = PersonalSecondary)

        grouped.forEach { (category, categorySkills) ->
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = category.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.5.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    categorySkills.forEach { skill ->
                        val target = skillNavigation[skill.name]
                        SkillTag(
                            name = skill.name,
                            targetScreen = target,
                            onClick = { target?.let(onNavigate) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SkillTag(
    name: String,
    targetScreen: Screen?,
    onClick: () -> Unit,
) {
    val isJava = name == "Java"

    val accentColor: Color? = when {
        targetScreen is Screen.Kmp    -> KmpPrimary
        targetScreen is Screen.Web    -> WebPrimary
        targetScreen is Screen.Games  -> GamesPrimary
        isJava                        -> JavaAccent
        else                          -> null
    }

    // Para el texto se usan los secundarios (más claros) para garantizar contraste
    // sobre el fondo translúcido del acento primario (~3:1 con primario, ~5.5:1 con secundario)
    val textColor: Color = when {
        targetScreen is Screen.Kmp   -> KmpSecondary
        targetScreen is Screen.Web   -> WebSecondary
        targetScreen is Screen.Games -> GamesSecondary
        isJava                       -> JavaAccent
        else                         -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    val bgColor = accentColor?.copy(alpha = 0.18f) ?: MaterialTheme.colorScheme.surfaceVariant
    val border = accentColor?.copy(alpha = 0.55f) ?: MaterialTheme.colorScheme.outline

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, border),
        modifier = if (targetScreen != null)
            Modifier.clickable(onClick = onClick)
        else
            Modifier,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor,
            )
            // Indicador de enlace solo en tags con navegación
            if (targetScreen != null) {
                Text(
                    text = "->",
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor.copy(alpha = 0.85f),
                )
            }
        }
    }
}
