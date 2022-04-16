package com.jefriap.submission1made.core.domain.usecase

import com.jefriap.submission1made.core.data.Resource
import com.jefriap.submission1made.core.domain.model.MovieModel
import com.jefriap.submission1made.core.domain.model.TvShowModel
import kotlinx.coroutines.flow.Flow

interface FilmCatalogueUseCase {

    fun getAllMovies(sort: String): Flow<Resource<List<MovieModel>>>

    fun getAllTvShows(sort: String): Flow<Resource<List<TvShowModel>>>

    fun getFavoriteMovies(sort: String): Flow<List<MovieModel>>

    fun getFavoritesTvShows(sort: String): Flow<List<TvShowModel>>

    fun setMovieFavorite(movie: MovieModel, state: Boolean)

    fun setTvShowFavorite(tvShow: TvShowModel, state: Boolean)
}