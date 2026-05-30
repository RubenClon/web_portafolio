package org.ruben.web_personal.presentation.personal

import org.ruben.web_personal.domain.PersonalInfo

data class PersonalUiState(
    val personalInfo: PersonalInfo? = null,
    val isLoading: Boolean = false
)
