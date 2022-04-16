package com.jefriap.submission1made.core.di

import androidx.room.Room
import com.jefriap.submission1made.core.BuildConfig
import com.jefriap.submission1made.core.data.FilmCatalogueRepository
import com.jefriap.submission1made.core.data.source.local.LocalDataSource
import com.jefriap.submission1made.core.data.source.local.room.FilmDatabase
import com.jefriap.submission1made.core.data.source.remote.RemoteDataSource
import com.jefriap.submission1made.core.data.source.remote.network.ApiService
import com.jefriap.submission1made.core.domain.repository.IFilmCatalogueRepository
import com.jefriap.submission1made.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

/*
* add your
* BASE_URL
* in file local.properties
 */
const val baseUrl = BuildConfig.BASE_URL

val databaseModule = module {
    factory { get<FilmDatabase>().filmDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            FilmDatabase::class.java, "catalogue"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        val loggingInterceptor =
            if(BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            }else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val builder = request
                    .newBuilder()
                val mutatedRequest = builder.build()
                val response = chain.proceed(mutatedRequest)
                response
            }
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IFilmCatalogueRepository> { FilmCatalogueRepository(get(), get(), get()) }
}