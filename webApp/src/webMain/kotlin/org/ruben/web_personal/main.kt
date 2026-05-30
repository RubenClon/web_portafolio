package org.ruben.web_personal

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.koin.core.context.startKoin
import org.ruben.web_personal.di.appModule

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin { modules(appModule) }
    ComposeViewport(document.body!!) { App() }
}