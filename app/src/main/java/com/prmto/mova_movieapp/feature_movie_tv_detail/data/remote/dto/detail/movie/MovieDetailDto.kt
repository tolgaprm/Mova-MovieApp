package com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.detail.movie

import com.prmto.mova_movieapp.core.data.remote.dto.genre.Genre
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.credit.CreditDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.detail.ProductionCompany
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.detail.ProductionCountry
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.detail.SpokenLanguage
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.watchProvider.WatchProviderDto
import com.squareup.moshi.Json

data class MovieDetailDto(
    val adult: Boolean?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "belongs_to_collection") val belongsToCollection: Any?,
    val budget: Int?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int?,
    @Json(name = "imdb_id") val imdbId: String?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "original_title") val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompany>?,
    @Json(name = "production_countries") val productionCountries: List<ProductionCountry>?,
    @Json(name = "release_date") val releaseDate: String?,
    val revenue: Any?,
    val runtime: Int?,
    @Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    @Json(name = "vote_average") val voteAverage: Double?,
    @Json(name = "vote_count") val voteCount: Int?,
    val credits: CreditDto?,
    @Json(name = "watch/providers") val watchProviders: WatchProviderDto?
)