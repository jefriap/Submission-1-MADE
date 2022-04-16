package com.jefriap.submission1made.core.data.source.local

import com.jefriap.submission1made.core.data.source.local.room.FilmDao
import com.jefriap.submission1made.core.utils.SortUtils
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.data.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val mFilmDao: FilmDao) {

    fun getAllMovies(sort: String): Flow<List<MovieEntity>> {
        val query = SortUtils.getSortedQueryFavoriteMovies(sort)
        return mFilmDao.getMovies(query)
    }

    fun getAllTvShows(sort: String): Flow<List<TvShowEntity>> {
        val query = SortUtils.getSortedQueryFavoriteMovies(sort)
        return mFilmDao.getTvShows(query)
    }

    fun getAllFavoriteMovies(sort: String): Flow<List<MovieEntity>> {
        val query = SortUtils.getSortedQueryFavoriteMovies(sort)
        return mFilmDao.getFavoriteMovies(query)
    }

    fun getAllFavoriteTvShows(sort: String): Flow<List<TvShowEntity>> {
        val query = SortUtils.getSortedQueryFavoriteTvShows(sort)
         return mFilmDao.getFavoriteTvShows(query)
    }

    suspend fun insertMovies(movies: List<MovieEntity>) = mFilmDao.insertMovie(movies)

    suspend fun insertTvShows(tvShows: List<TvShowEntity>) = mFilmDao.insertTvShow(tvShows)

    fun setMovieFavorite(movie: MovieEntity, newState: Boolean) {
        movie.favorite = newState
        mFilmDao.updateMovieFavorite(movie)
    }

    fun setTvShowFavorite(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.favorite = newState
        mFilmDao.updateTvShowFavorite(tvShow)
    }
}