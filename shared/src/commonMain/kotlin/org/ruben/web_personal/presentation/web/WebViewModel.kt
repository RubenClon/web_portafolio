package org.ruben.web_personal.presentation.web

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.ruben.web_personal.domain.PortfolioRepository
import org.ruben.web_personal.domain.SectionId

class WebViewModel(
    private val repository: PortfolioRepository
) : ViewModel() {

    private val _state = MutableStateFlow(WebUiState(isLoading = true))
    val state: StateFlow<WebUiState> = _state.asStateFlow()

    init {
        val projects = repository.getProjectsBySection(SectionId.WEB)
        _state.update {
            it.copy(
                projects = projects,
                featuredProject = projects.firstOrNull { p -> p.featured },
                isLoading = false
            )
        }
    }
}
