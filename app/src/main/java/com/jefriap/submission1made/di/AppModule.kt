package com.jefriap.submission1made.di

import com.jefriap.submission1made.core.domain.usecase.FilmCatalogueInteractor
import com.jefriap.submission1made.core.domain.usecase.FilmCatalogueUseCase
import com.jefriap.submission1made.ui.detail.DetailViewModel
import com.jefriap.submission1made.ui.movie.MovieViewModel
import com.jefriap.submission1made.ui.tvshow.TvShowViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<FilmCatalogueUseCase> { FilmCatalogueInteractor(get()) }
}

@ExperimentalCoroutinesApi
@FlowPreview
val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}