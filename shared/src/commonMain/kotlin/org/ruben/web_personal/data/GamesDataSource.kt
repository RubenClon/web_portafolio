package org.ruben.web_personal.data

import org.ruben.web_personal.domain.Project
import org.ruben.web_personal.domain.SectionId

class GamesDataSource {
    fun getProjects() = listOf(
        Project(
            id = "game-1",
            title = "Rub and Dar",
            description = "Videojuego de plataformas y puzles en 2D con ambientación futurista. " +
                    "Desarrollado en Godot Engine, el proyecto destaca por la implementación de mecánicas de precisión, " +
                    "gestión de estados de personajes y un diseño de niveles estructurado mediante nodos reutilizables.",
            tags = listOf("Godot Engine", "GDScript", "Mecánicas 2D", "Itch.io"),
            repoUrl = "https://github.com/RubenClon/Rub-and-Dar.git",
            demoUrl = "https://rubenclon.itch.io/rub-dar",
            imageRes = "rd",
            section = SectionId.GAMES,
            featured = false
        ),
        Project(
            id = "game-2",
            title = "K34-Superviviente",description = "Videojuego de acción y supervivencia desarrollado cooperativamente en una Game Jam. " +
                    "Aborda retos técnicos críticos como la optimización de físicas en tiempo real para gestionar " +
                    "oleadas masivas de enemigos en pantalla, un sistema dinámico de spawn y arquitectura basada en eventos.",
            tags = listOf("Godot Engine", "Game Jam", "Trabajo en Equipo", "Optimización"),
            repoUrl = "https://github.com/RubenClon/K34-survival.git",
            demoUrl = "https://k34dev.itch.io/k34-superviviente",
            imageRes = "k34",
            section = SectionId.GAMES,
            featured = false
        )
    )
}
