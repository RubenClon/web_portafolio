package org.ruben.web_personal.di

import org.koin.dsl.module
import org.ruben.web_personal.data.GamesDataSource
import org.ruben.web_personal.data.KmpProjectsDataSource
import org.ruben.web_personal.data.PersonalDataSource
import org.ruben.web_personal.data.PortfolioRepositoryImpl
import org.ruben.web_personal.data.WebProjectsDataSource
import org.ruben.web_personal.domain.PortfolioRepository
import org.ruben.web_personal.presentation.games.GamesViewModel
import org.ruben.web_personal.presentation.kmp.KmpViewModel
import org.ruben.web_personal.presentation.personal.PersonalViewModel
import org.ruben.web_personal.presentation.web.WebViewModel

val appModule = module {
    // Data sources
    single { PersonalDataSource() }
    single { KmpProjectsDataSource() }
    single { WebProjectsDataSource() }
    single { GamesDataSource() }

    // Repository
    single<PortfolioRepository> {
        PortfolioRepositoryImpl(get(), get(), get(), get())
    }

    // ViewModels
    factory { PersonalViewModel(get()) }
    factory { KmpViewModel(get()) }
    factory { WebViewModel(get()) }
    factory { GamesViewModel(get()) }
}
