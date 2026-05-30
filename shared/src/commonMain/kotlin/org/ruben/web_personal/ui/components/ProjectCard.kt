package org.ruben.web_personal.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.ruben.web_personal.domain.Project
import web_personal.shared.generated.resources.Res
import web_personal.shared.generated.resources.rd
import web_personal.shared.generated.resources.k34
import web_personal.shared.generated.resources.webimg
import web_personal.shared.generated.resources.metricoraimg
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProjectCard(
    project: Project,
    accentColor: Color,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val imagePainter = projectPainter(project.imageRes)

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.35f)),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
            // Imagen del proyecto (solo si existe)
            if (imagePainter != null) {
                Image(
                    painter = imagePainter,
                    contentDescription = project.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    contentScale = ContentScale.FillHeight,
                )
                HorizontalDivider(color = accentColor.copy(alpha = 0.2f), thickness = 1.dp)
            }

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                if (project.featured) {
                    Surface(
                        color = accentColor.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(4.dp),
                    ) {
                        Text(
                            text = "DESTACADO",
                            style = MaterialTheme.typography.labelSmall,
                            color = accentColor,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        )
                    }
                }

                Text(
                    text = project.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Text(
                    text = project.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    project.tags.forEach { tag -> TagChip(tag, accentColor) }
                }

                if (project.repoUrl != null || project.demoUrl != null) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        project.repoUrl?.let { url ->
                            TextButton(
                                onClick = { uriHandler.openUri(url) },
                                colors = ButtonDefaults.textButtonColors(contentColor = accentColor),
                            ) {
                                Text("GitHub")
                            }
                        }
                        project.demoUrl?.let { url ->
                            TextButton(
                                onClick = { uriHandler.openUri(url) },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                ),
                            ) {
                                Text("Jugar")
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Mapea el identificador de imagen del dominio al Painter de Compose.
 * Añade un nuevo 'when' por cada imagen que agregues en composeResources/drawable/.
 */
@Composable
private fun projectPainter(imageRes: String?): Painter? = when (imageRes) {
    "rd"  -> painterResource(Res.drawable.rd)
    "k34" -> painterResource(Res.drawable.k34)
    "webimg"-> painterResource(Res.drawable.webimg)
    "metricoraimg"-> painterResource(Res.drawable.metricoraimg)
    else  -> null
}

@Composable
private fun TagChip(tag: String, accentColor: Color) {
    Surface(
        color = accentColor.copy(alpha = 0.12f),
        shape = RoundedCornerShape(4.dp),
    ) {
        Text(
            text = tag,
            style = MaterialTheme.typography.labelSmall,
            color = accentColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}
