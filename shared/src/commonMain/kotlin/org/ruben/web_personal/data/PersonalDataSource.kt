package org.ruben.web_personal.data

import org.ruben.web_personal.domain.PersonalInfo
import org.ruben.web_personal.domain.Skill
import org.ruben.web_personal.domain.SkillLevel
import org.ruben.web_personal.domain.SocialLink

class PersonalDataSource {
    fun getPersonalInfo() = PersonalInfo(
        name = "Ruben",
        role = "Software Engineer | Multiplatform Developer",
        bio = "Construyo aplicaciones nativas eficientes para múltiples plataformas utilizando una única base de código con Kotlin. Ayudo a empresas a escalar sus productos en Android, iOS y Web de forma ágil y mantenible.",
        skills = listOf(
            // Lenguajes
            Skill("Kotlin",            SkillLevel.EXPERT,       "Lenguajes"),
            Skill("Java",              SkillLevel.ADVANCED,     "Lenguajes"),
            Skill("PHP",               SkillLevel.INTERMEDIATE, "Lenguajes"),
            Skill("GDScript (Godot)",  SkillLevel.ADVANCED,     "Lenguajes"),
            Skill("HTML5",             SkillLevel.ADVANCED,     "Lenguajes"),
            Skill("CSS3",              SkillLevel.INTERMEDIATE, "Lenguajes"),
            Skill("SQL",               SkillLevel.ADVANCED,     "Lenguajes"),
            // Frameworks & Ecosistema
            Skill("Kotlin Multiplatform (KMP)", SkillLevel.ADVANCED,     "Frameworks & Ecosistema"),
            Skill("Ktor",                        SkillLevel.INTERMEDIATE, "Frameworks & Ecosistema"),
            Skill("Odoo (ERP)",                  SkillLevel.INTERMEDIATE, "Frameworks & Ecosistema"),
            // Motores de Desarrollo
            Skill("Godot Engine (2D/3D)", SkillLevel.ADVANCED, "Motores de Desarrollo"),
            // Bases de Datos
            Skill("MySQL", SkillLevel.ADVANCED, "Bases de Datos"),
            // Diseño & 3D
            Skill("Blender",        SkillLevel.INTERMEDIATE, "Diseño & 3D"),
            Skill("Impresión 3D",   SkillLevel.INTERMEDIATE, "Diseño & 3D"),
        ),
        socialLinks = listOf(
            SocialLink("GitHub",   "https://github.com/rubenclon",    "github"),
            SocialLink("LinkedIn", "https://www.linkedin.com/in/ruben-montilla-095a62150",        "linkedin"),
            SocialLink("Email",    "mailto:rmontilla@gmail.com",       "email"),
        ),
        contactEmail = "rmontilla@gmail.com"
    )
}
