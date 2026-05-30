package org.ruben.web_personal.data

import org.ruben.web_personal.domain.Project
import org.ruben.web_personal.domain.SectionId

class KmpProjectsDataSource {
    fun getProjects() = listOf(
        Project(
            id = "kmp-portfolio",
            title = "Metricora",
            description = "Aplicación multiplataforma de contabilidad orientada al modelo fiscal español. " +
                    "Permite la gestión integral de ingresos, gastos, cálculo automatizado de IVA/IRPF y generación de liquidaciones trimestrales. " +
                    "El núcleo del proyecto comparte el 100% de la lógica de negocio y arquitectura en 5 plataformas: Android, iOS, Windows, macOS y Linux.",
            tags = listOf("Kotlin Multiplatform", "Compose Multiplatform", "Koin", "SQLDelight", "Coroutines/Flow", "Architecture"),
            repoUrl = "https://github.com/RubenClon/metricora.git",
            demoUrl = null,
            imageRes = "metricoraimg",
            section = SectionId.KMP,
            featured = true
        ),
        Project(
            id = "kmp-project-2",
            title = "App KMP — Ejemplo",
            description = "Aplicación de ejemplo con Kotlin Multiplatform compartiendo lógica entre Android y Web.",
            tags = listOf("KMP", "Android", "Ktor", "SQLDelight"),
            repoUrl = null,
            demoUrl = null,
            imageRes = null,
            section = SectionId.KMP,
            featured = false
        ),
    )
}
