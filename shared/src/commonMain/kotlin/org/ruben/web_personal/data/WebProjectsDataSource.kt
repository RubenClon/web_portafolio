package org.ruben.web_personal.data

import org.ruben.web_personal.domain.Project
import org.ruben.web_personal.domain.SectionId

class WebProjectsDataSource {
    fun getProjects() = listOf(
        Project(
            id = "web-project-1",
            title = "Este Portafolio (Kotlin/Wasm)",
            description = "Aplicación web de alto rendimiento desarrollada íntegramente con Compose Multiplatform y compilada a WebAssembly (Wasm). " +
                    "Este proyecto actúa como prueba de concepto técnica, demostrando cómo reutilizar el 100% del código de interfaz de usuario " +
                    "y lógica de Kotlin nativo directamente en el navegador, logrando un renderizado fluido a 60fps sin depender de frameworks JS tradicionales.",
            tags = listOf("Kotlin/Wasm", "Compose Multiplatform", "WebAssembly", "UI/UX"),
            repoUrl = "tu_enlace_a_github",
            demoUrl = null,
            imageRes ="webimg",
            section = SectionId.WEB,
            featured = true
        ),
    )
}
