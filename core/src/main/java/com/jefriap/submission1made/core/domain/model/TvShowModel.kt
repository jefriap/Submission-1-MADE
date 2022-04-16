package com.jefriap.submission1made.core.domain.model

data class TvShowModel(
    var tvShowId: Int,
    var name: String,
    var overview: String,
    var rating: Double,
    var releaseDate: String,
    var poster: String,
    var backdrop: String,
    var favorite: Boolean = false,
)
