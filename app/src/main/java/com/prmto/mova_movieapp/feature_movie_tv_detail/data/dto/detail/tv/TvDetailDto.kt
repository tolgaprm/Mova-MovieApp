package com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.tv

import com.prmto.mova_movieapp.core.data.dto.Genre
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.credit.CreditDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.credit.toCredit
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.ProductionCompany
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.ProductionCountry
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.SpokenLanguage
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.watch_provider.WatchProviders
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.TvDetail
import com.squareup.moshi.Json

data class TvDetailDto(
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "created_by") val createdBy: List<CreatedByDto>,
    @Json(name = "episode_run_time") val episodeRunTime: List<Int>,
    @Json(name = "first_air_date") val firstAirDate: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @Json(name = "in_production") val inProduction: Boolean,
    val languages: List<String>,
    @Json(name = "last_air_date") val lastAirDate: String,
    @Json(name = "last_episode_to_air") val lastEpisodeToAir: LastEpisodeToAir,
    val name: String,
    val networks: List<Network>,
    @Json(name = "next_episode_to_air") val nextEpisodeToAir: Any?,
    @Json(name = "number_of_episodes") val numberOfEpisodes: Int,
    @Json(name = "number_of_seasons") val numberOfSeasons: Int,
    @Json(name = "origin_country") val originCountry: List<String>,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "original_name") val originalName: String,
    val overview: String,
    val popularity: Double,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompany>,
    @Json(name = "production_countries") val productionCountries: List<ProductionCountry>,
    val seasons: List<Season>,
    @Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val type: String,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int,
    val credits: CreditDto,
    @Json(name = "watch/providers") val watchProviders: WatchProviders
)

fun TvDetailDto.toTvDetail(): TvDetail {
    return TvDetail(
        id = id,
        genres = genres,
        firstAirDate = firstAirDate,
        lastAirDate = lastAirDate,
        createdBy = createdBy.toListOfCreatedBy(),
        numberOfSeasons = numberOfSeasons,
        originalName = originalName,
        name = name,
        overview = overview,
        posterPath = posterPath,
        seasons = seasons,
        status = status,
        voteAverage = voteAverage,
        voteCount = voteCount,
        watchProviders = watchProviders,
        credit = credits.toCredit()
    )
}