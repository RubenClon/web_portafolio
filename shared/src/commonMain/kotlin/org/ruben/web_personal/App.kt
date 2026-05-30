package org.ruben.web_personal

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.ruben.web_personal.presentation.games.GamesViewModel
import org.ruben.web_personal.presentation.kmp.KmpViewModel
import org.ruben.web_personal.presentation.navigation.AppNavigator
import org.ruben.web_personal.presentation.navigation.Screen
import org.ruben.web_personal.presentation.personal.PersonalViewModel
import org.ruben.web_personal.presentation.web.WebViewModel
import org.ruben.web_personal.ui.components.gridBackground
import org.ruben.web_personal.ui.sections.games.GamesScreen
import org.ruben.web_personal.ui.sections.kmp.KmpScreen
import org.ruben.web_personal.ui.sections.personal.PersonalScreen
import org.ruben.web_personal.ui.sections.web.WebScreen
import org.ruben.web_personal.ui.theme.AppTheme
import org.ruben.web_personal.ui.theme.GamesPrimary
import org.ruben.web_personal.ui.theme.KmpPrimary
import org.ruben.web_personal.ui.theme.PersonalPrimary
import org.ruben.web_personal.ui.theme.WebPrimary

private val COMPACT_BREAKPOINT = 600.dp

@Composable
fun App() {
    AppTheme {
        val navigator = remember { AppNavigator() }

        val personalVm: PersonalViewModel = koinInject()
        val kmpVm: KmpViewModel           = koinInject()
        val webVm: WebViewModel           = koinInject()
        val gamesVm: GamesViewModel       = koinInject()

        val scope = rememberCoroutineScope()
        var bgScrollOffset by remember { mutableStateOf(0f) }
        var scrollDelta    by remember { mutableStateOf(0f) }
        var resetJob: Job? by remember { mutableStateOf(null) }

        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    bgScrollOffset += available.y
                    scrollDelta = available.y
                    // Cancelamos el reset anterior y lanzamos uno nuevo 120 ms después
                    resetJob?.cancel()
                    resetJob = scope.launch {
                        delay(120)
                        scrollDelta = 0f
                    }
                    return Offset.Zero   // no consumimos — el hijo sigue scrollando
                }
            }
        }

        // La rotación sigue la velocidad instantánea del scroll y spring-vuelve a 0
        val rotationAngle by animateFloatAsState(
            targetValue   = (scrollDelta * 5f).coerceIn(-12f, 12f),
            animationSpec = spring(dampingRatio = 0.6f, stiffness = 180f),
            label         = "bgRotation",
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
                .gridBackground(
                    minSquareSize = 15.dp,
                    maxSquareSize = 50.dp,
                    gap           = 5.dp,
                    elevation     = 10.dp,
                    scrollOffset  = bgScrollOffset,
                    rotationAngle = rotationAngle,
                ),
        ) {
            TopNavBar(
                current    = navigator.current,
                onNavigate = { navigator.navigateTo(it) },
            )

            AnimatedContent(
                targetState = navigator.current,
                transitionSpec = {
                    val dir = if (targetState.index > initialState.index)
                        AnimatedContentTransitionScope.SlideDirection.Start
                    else
                        AnimatedContentTransitionScope.SlideDirection.End
                    slideIntoContainer(dir, tween(320)) togetherWith
                            slideOutOfContainer(dir, tween(320))
                },
                modifier = Modifier.fillMaxSize(),
                label    = "sectionContent",
            ) { screen ->
                when (screen) {
                    is Screen.Personal -> PersonalScreen(
                        viewModel  = personalVm,
                        onNavigate = { navigator.navigateTo(it) },
                    )
                    is Screen.Kmp    -> KmpScreen(kmpVm)
                    is Screen.Web    -> WebScreen(webVm)
                    is Screen.Games  -> GamesScreen(gamesVm)
                }
            }
        }
    }
}

// ─── Barra de navegación ──────────────────────────────────────────────────────

@Composable
private fun TopNavBar(current: Screen, onNavigate: (Screen) -> Unit) {
    val accentColor by animateColorAsState(
        targetValue = when (current) {
            is Screen.Personal -> PersonalPrimary
            is Screen.Kmp      -> KmpPrimary
            is Screen.Web      -> WebPrimary
            is Screen.Games    -> GamesPrimary
        },
        animationSpec = tween(300),
        label = "navAccent",
    )

    Surface(
        color           = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp,
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val isCompact = maxWidth < COMPACT_BREAKPOINT

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = if (isCompact) 16.dp else 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                // Logo / nombre
                Text(
                    text     = "< Ruben />",
                    style    = MaterialTheme.typography.titleLarge,
                    color    = accentColor,
                    modifier = Modifier.padding(vertical = 16.dp),
                )

                if (isCompact) {
                    // Menú hamburguesa
                    HamburgerMenu(
                        current     = current,
                        accentColor = accentColor,
                        onNavigate  = onNavigate,
                    )
                } else {
                    // Tabs horizontales
                    Row {
                        Screen.all.forEach { screen ->
                            NavTab(
                                screen      = screen,
                                isSelected  = current == screen,
                                accentColor = accentColor,
                                onClick     = { onNavigate(screen) },
                            )
                        }
                    }
                }
            }
        }
    }
}

// ─── Menú desplegable (compacto) ─────────────────────────────────────────────

@Composable
private fun HamburgerMenu(
    current: Screen,
    accentColor: Color,
    onNavigate: (Screen) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Box(modifier = Modifier.size(24.dp)
            .clickable { expanded = true },
            contentAlignment = Alignment.Center
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Box(modifier = Modifier.height(4.dp)
                    .width(20.dp)
                    .clip(RoundedCornerShape(8.dp))
                .background(accentColor)) {}
                Box(modifier = Modifier.height(4.dp)
                    .width(20.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(accentColor)) {}
                Box(modifier = Modifier.height(4.dp)
                    .width(20.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(accentColor)) {}
            }

        }

        DropdownMenu(
            expanded          = expanded,
            onDismissRequest  = { expanded = false },
        ) {
            Screen.all.forEach { screen ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text  = screen.label,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (current == screen) accentColor
                                    else MaterialTheme.colorScheme.onSurface,
                        )
                    },
                    onClick = {
                        onNavigate(screen)
                        expanded = false
                    },
                )
            }
        }
    }
}

// ─── Tab individual (escritorio) ─────────────────────────────────────────────

@Composable
private fun NavTab(
    screen: Screen,
    isSelected: Boolean,
    accentColor: Color,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            text  = screen.label,
            style = MaterialTheme.typography.labelLarge,
            color = if (isSelected) accentColor
                    else MaterialTheme.colorScheme.onSurfaceVariant,
        )
        AnimatedVisibility(visible = isSelected) {
            Spacer(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .width(20.dp)
                    .height(2.dp)
                    .background(accentColor),
            )
        }
    }
}
