package org.ruben.web_personal.ui.sections.games

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.ruben.web_personal.domain.Project
import org.ruben.web_personal.presentation.games.GamesViewModel
import org.ruben.web_personal.ui.components.ProjectCard
import org.ruben.web_personal.ui.components.SectionTitle
import org.ruben.web_personal.ui.theme.GamesPrimary
import org.ruben.web_personal.ui.theme.KmpPrimary

@Composable
fun GamesScreen(viewModel: GamesViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Box(
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
                            0.0f to GamesPrimary.copy(alpha = 0.8f),
                            0.8f to GamesPrimary.copy(alpha = 0.9f),
                            1.0f to Color.Transparent,
                        ),
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                .background(
                    brush = Brush.horizontalGradient(
                        0.0f to  Color.Black,
                        0.8f to Color.Black.copy(alpha = 0.9f),
                        1.0f to Color.Transparent,
                    )
                )
                .padding(horizontal = 48.dp, vertical = 48.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SectionTitle(title = "Desarrollo de Videojuegos", color = GamesPrimary)
                Text(
                    text ="Proyectos y prototipos interactivos donde exploro la optimización de recursos, " +
                            "físicas en tiempo real y arquitectura de software aplicada al desarrollo de videojuegos.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            if (state.projects.isNotEmpty()) {
                state.featuredProject?.let { featured ->
                    ProjectCard(project = featured, accentColor = GamesPrimary)
                }
                val remaining = state.projects.filter { !it.featured }
                if (remaining.isNotEmpty()) {
                    GamesGrid(projects = remaining)
                }
            }
        }

        Box(Modifier.padding(bottom = 64.dp))
    }
}

@Composable
private fun GamesGrid(projects: List<Project>) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val columns = if (maxWidth > 720.dp) 2 else 1
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            projects.chunked(columns).forEach { rowProjects ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    rowProjects.forEach { project ->
                        ProjectCard(
                            project = project,
                            accentColor = GamesPrimary,
                            modifier = Modifier.weight(1f),
                        )
                    }
                    if (rowProjects.size < columns) {
                        Box(Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
