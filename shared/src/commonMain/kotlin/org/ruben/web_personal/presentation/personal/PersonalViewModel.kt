package org.ruben.web_personal.presentation.personal

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.ruben.web_personal.domain.PortfolioRepository

class PersonalViewModel(
    private val repository: PortfolioRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PersonalUiState(isLoading = true))
    val state: StateFlow<PersonalUiState> = _state.asStateFlow()

    init {
        val info = repository.getPersonalInfo()
        _state.update {
            it.copy(personalInfo = info, isLoading = false)
        }
    }
}
