package org.ruben.web_personal.presentation.kmp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.ruben.web_personal.domain.PortfolioRepository
import org.ruben.web_personal.domain.SectionId

class KmpViewModel(
    private val repository: PortfolioRepository
) : ViewModel() {

    private val _state = MutableStateFlow(KmpUiState(isLoading = true))
    val state: StateFlow<KmpUiState> = _state.asStateFlow()

    init {
        val projects = repository.getProjectsBySection(SectionId.KMP)
        _state.update {
            it.copy(
                projects = projects,
                featuredProject = projects.firstOrNull { p -> p.featured },
                isLoading = false
            )
        }
    }
}