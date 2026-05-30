package org.ruben.web_personal.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class AppNavigator {
    var current by mutableStateOf<Screen>(Screen.Personal)
    fun navigateTo(screen: Screen) { current = screen }
}
