package org.ruben.web_personal.presentation.games

import org.ruben.web_personal.domain.Project

data class GamesUiState(
    val projects: List<Project> = emptyList(),
    val featuredProject: Project? = null,
    val isLoading: Boolean = false
)
