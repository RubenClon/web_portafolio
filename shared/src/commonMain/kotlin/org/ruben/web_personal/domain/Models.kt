package org.ruben.web_personal.domain

// Secciones del portafolio
enum class SectionId { PERSONAL, KMP, WEB, GAMES }

data class Section(
    val id: SectionId,
    val title: String,
    val description: String,
    val accentColor: Long       // hex, cada sección tiene su identidad visual
)

// Proyecto/trabajo mostrado en cada sección
data class Project(
    val id: String,
    val title: String,
    val description: String,
    val tags: List<String>,
    val repoUrl: String?,
    val demoUrl: String?,
    val imageRes: String?,
    val section: SectionId,
    val featured: Boolean = false
)

// Información personal (sección PERSONAL)
data class PersonalInfo(
    val name: String,
    val role: String,
    val bio: String,
    val skills: List<Skill>,
    val socialLinks: List<SocialLink>,
    val contactEmail: String
)

data class Skill(val name: String, val level: SkillLevel, val category: String)
enum class SkillLevel { BEGINNER, INTERMEDIATE, ADVANCED, EXPERT }

data class SocialLink(val platform: String, val url: String, val iconRes: String)

// Contrato de repositorio — definido en domain, implementado en data
interface PortfolioRepository {
    fun getPersonalInfo(): PersonalInfo
    fun getSections(): List<Section>
    fun getProjectsBySection(sectionId: SectionId): List<Project>
    fun getFeaturedProjects(): List<Project>
}