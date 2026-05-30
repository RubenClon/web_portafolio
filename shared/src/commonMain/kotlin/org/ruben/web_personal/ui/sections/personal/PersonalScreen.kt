package org.ruben.web_personal.ui.sections.personal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.ruben.web_personal.presentation.navigation.Screen
import org.ruben.web_personal.presentation.personal.PersonalViewModel
import org.ruben.web_personal.ui.theme.GamesPrimary
import org.ruben.web_personal.ui.theme.KmpPrimary
import org.ruben.web_personal.ui.theme.WebPrimary


@Composable
fun PersonalScreen(
    viewModel: PersonalViewModel,
    onNavigate: (Screen) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(brush = Brush.verticalGradient(
                colors = listOf<Color>(
                    KmpPrimary.copy(alpha = 0.5f),
                    WebPrimary.copy(alpha = 0.5f),
                    GamesPrimary.copy(alpha = 0.5f)
                )
            ))
            .padding(bottom = 64.dp),
    ) {
        state.personalInfo?.let { info ->
            HeroSection(info)
            SkillsSection(info.skills, onNavigate)
            ContactSection(info)
        }
    }
}
