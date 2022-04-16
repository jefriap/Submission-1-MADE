package com.jefriap.submission1made.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.submission.filmcatalogue.data.local.entity.MovieEntity
import com.submission.filmcatalogue.data.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getMovies(query: SupportSQLiteQuery): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<MovieEntity>)

    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getTvShows(query: SupportSQLiteQuery): Flow<List<TvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShow: List<TvShowEntity>)

    @Transaction
    @Query("SELECT * FROM movieentity WHERE movieId = :movieId")
    fun getDetailMovie(movieId: Int): LiveData<MovieEntity>

    @Transaction
    @Query("SELECT * FROM tvshowentity WHERE tvShowId = :tvShowId")
    fun getDetailTvShow(tvShowId: Int): LiveData<TvShowEntity>

    @Update
    fun updateMovieFavorite(movie: MovieEntity)

    @Update
    fun updateTvShowFavorite(tvShow: TvShowEntity)

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getFavoriteMovies(query: SupportSQLiteQuery): Flow<List<MovieEntity>>

    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getFavoriteTvShows(query: SupportSQLiteQuery): Flow<List<TvShowEntity>>
}