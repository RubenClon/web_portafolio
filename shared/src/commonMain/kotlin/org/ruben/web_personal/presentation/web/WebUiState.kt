package org.ruben.web_personal.presentation.web

import org.ruben.web_personal.domain.Project

data class WebUiState(
    val projects: List<Project> = emptyList(),
    val featuredProject: Project? = null,
    val isLoading: Boolean = false
)
