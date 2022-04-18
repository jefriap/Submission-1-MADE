package com.jefriap.submission1made.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jefriap.submission1made.core.data.Resource
import com.jefriap.submission1made.core.domain.model.MovieModel
import com.jefriap.submission1made.core.domain.model.TvShowModel
import com.jefriap.submission1made.core.domain.usecase.FilmCatalogueUseCase

class TvShowViewModel(private val filmCatalogueUseCase: FilmCatalogueUseCase) : ViewModel() {
    fun getTvShows(sort: String): LiveData<Resource<List<TvShowModel>>> {
        return filmCatalogueUseCase.getAllTvShows(sort).asLiveData()
    }
}