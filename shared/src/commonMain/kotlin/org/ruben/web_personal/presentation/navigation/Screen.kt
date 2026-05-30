package org.ruben.web_personal.presentation.navigation

sealed class Screen(val index: Int, val label: String) {
    data object Personal : Screen(0, "Personal")
    data object Kmp      : Screen(1, "KMP")
    data object Web      : Screen(2, "Web")
    data object Games    : Screen(3, "Juegos")

    companion object {
        val all = listOf(Personal, Kmp, Web, Games)
    }
}