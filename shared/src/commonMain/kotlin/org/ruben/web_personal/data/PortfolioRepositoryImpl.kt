package org.ruben.web_personal.data

import org.ruben.web_personal.domain.PersonalInfo
import org.ruben.web_personal.domain.Project
import org.ruben.web_personal.domain.PortfolioRepository
import org.ruben.web_personal.domain.Section
import org.ruben.web_personal.domain.SectionId

class PortfolioRepositoryImpl(
    private val personalDataSource: PersonalDataSource,
    private val kmpDataSource: KmpProjectsDataSource,
    private val webDataSource: WebProjectsDataSource,
    private val gamesDataSource: GamesDataSource,
) : PortfolioRepository {

    override fun getPersonalInfo(): PersonalInfo =
        personalDataSource.getPersonalInfo()

    override fun getSections(): List<Section> = listOf(
        Section(SectionId.PERSONAL, "Personal", "Sobre mí y mis habilidades", 0xFF6200EE),
        Section(SectionId.KMP, "KMP", "Proyectos Kotlin Multiplatform", 0xFF0095D5),
        Section(SectionId.WEB, "Web", "Proyectos web", 0xFF00BFA5),
        Section(SectionId.GAMES, "Juegos", "Juegos desarrollados", 0xFFFF6D00),
    )

    override fun getProjectsBySection(sectionId: SectionId): List<Project> = when (sectionId) {
        SectionId.PERSONAL -> emptyList()
        SectionId.KMP      -> kmpDataSource.getProjects()
        SectionId.WEB      -> webDataSource.getProjects()
        SectionId.GAMES    -> gamesDataSource.getProjects()
    }

    override fun getFeaturedProjects(): List<Project> =
        (kmpDataSource.getProjects() + webDataSource.getProjects() + gamesDataSource.getProjects())
            .filter { it.featured }
}
