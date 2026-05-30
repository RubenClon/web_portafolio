package org.ruben.web_personal.ui.sections.personal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import org.ruben.web_personal.domain.PersonalInfo
import org.ruben.web_personal.ui.components.SectionTitle
import org.ruben.web_personal.ui.theme.PersonalPrimary
import org.ruben.web_personal.ui.theme.PersonalSecondary
import org.ruben.web_personal.ui.theme.SurfaceVariant

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContactSection(info: PersonalInfo) {
    val uriHandler = LocalUriHandler.current

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
            .drawBehind {
                drawLine(
                    brush = Brush.horizontalGradient(
                        0.0f to Color.White.copy(alpha = 0.8f),
                        1.0f to Color.Transparent,
                    ),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
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
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        SectionTitle(title = "Contacto", color = PersonalSecondary)

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            shape = RoundedCornerShape(12.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "¿Tienes un proyecto interesante?",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "Estoy abierto a nuevas oportunidades y colaboraciones. No dudes en escribirme.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                OutlinedButton(
                    onClick = { uriHandler.openUri("mailto:${info.contactEmail}") },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = PersonalPrimary),
                    border = BorderStroke(1.dp, PersonalPrimary),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(info.contactEmail)
                }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    info.socialLinks.forEach { link ->
                        OutlinedButton(
                            onClick = { uriHandler.openUri(link.url) },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text(link.platform)
                        }
                    }
                }
            }
        }
    }
}
