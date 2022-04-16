package com.jefriap.submission1made.core.data

import com.jefriap.submission1made.core.data.source.local.LocalDataSource
import com.jefriap.submission1made.core.data.source.remote.RemoteDataSource
import com.jefriap.submission1made.core.data.source.remote.response.ApiResponse
import com.jefriap.submission1made.core.data.source.remote.response.MovieItemResponse
import com.jefriap.submission1made.core.data.source.remote.response.TvShowItemResponse
import com.jefriap.submission1made.core.domain.model.MovieModel
import com.jefriap.submission1made.core.domain.model.TvShowModel
import com.jefriap.submission1made.core.domain.repository.IFilmCatalogueRepository
import com.jefriap.submission1made.core.utils.AppExecutors
import com.jefriap.submission1made.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FilmCatalogueRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IFilmCatalogueRepository {
    override fun getAllMovies(sort: String): Flow<Resource<List<MovieModel>>> {
        return object : NetworkBoundResource<List<MovieModel>, List<MovieItemResponse>>() {
            override fun loadFromDB(): Flow<List<MovieModel>> {
                return localDataSource.getAllMovies(sort).map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MovieModel>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MovieItemResponse>>> {
                return remoteDataSource.getMovies()
            }

            override suspend fun saveCallResult(data: List<MovieItemResponse>) {
                val movieList = DataMapper.mapMovieResponseToEntities(data)
                localDataSource.insertMovies(movieList)
            }

        }.asFlow()
    }

    override fun getAllTvShows(sort: String): Flow<Resource<List<TvShowModel>>> {
        return object : NetworkBoundResource<List<TvShowModel>, List<TvShowItemResponse>>() {
            override fun loadFromDB(): Flow<List<TvShowModel>> {
                return localDataSource.getAllTvShows(sort).map {
                    DataMapper.mapTvShowEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<TvShowModel>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<TvShowItemResponse>>> {
                return remoteDataSource.getTvShows()
            }

            override suspend fun saveCallResult(data: List<TvShowItemResponse>) {
                val tvShowList = DataMapper.mapTvShowResponseToEntities(data)
                localDataSource.insertTvShows(tvShowList)
            }
        }.asFlow()
    }

    override fun getFavoriteMovies(sort: String): Flow<List<MovieModel>> {
        return localDataSource.getAllFavoriteMovies(sort).map {
            DataMapper.mapMovieEntitiesToDomain(it)
        }
    }

    override fun getFavoritesTvShows(sort: String): Flow<List<TvShowModel>> {
        return localDataSource.getAllFavoriteTvShows(sort).map {
            DataMapper.mapTvShowEntitiesToDomain(it)
        }
    }

    override fun setMovieFavorite(movie: MovieModel, state: Boolean) {
        val movieEntity = DataMapper.mapMovieDomainToEntities(movie)
        appExecutors.diskIO().execute {
            localDataSource.setMovieFavorite(movieEntity, state)
        }
    }

    override fun setTvShowFavorite(tvShow: TvShowModel, state: Boolean) {
        val tvShowEntity = DataMapper.mapTvShowDomainToEntities(tvShow)
        appExecutors.diskIO().execute {
            localDataSource.setTvShowFavorite(tvShowEntity, state)
        }
    }

}