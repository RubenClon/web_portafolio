package org.ruben.web_personal.presentation.kmp

import org.ruben.web_personal.domain.Project

data class KmpUiState(
    val projects: List<Project> = emptyList(),
    val featuredProject: Project? = null,
    val isLoading: Boolean = false
)