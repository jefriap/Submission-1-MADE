package com.jefriap.submission1made.core.domain.model

data class MovieModel(
    var movieId: Int,
    var title: String,
    var overview: String,
    var rating: Double,
    var poster: String,
    var backdrop: String,
    var releaseDate: String,
    var favorite: Boolean = false,
)
