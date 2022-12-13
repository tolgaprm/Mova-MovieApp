package com.prmto.mova_movieapp.data.detail.tv

import com.google.common.truth.Truth.assertThat
import com.prmto.mova_movieapp.data.models.Genre
import com.prmto.mova_movieapp.data.models.credit.CastDto
import com.prmto.mova_movieapp.data.models.credit.CreditDto
import com.prmto.mova_movieapp.data.models.credit.CrewDto
import com.prmto.mova_movieapp.data.models.credit.toCredit
import com.prmto.mova_movieapp.data.models.detail.ProductionCompany
import com.prmto.mova_movieapp.data.models.detail.ProductionCountry
import com.prmto.mova_movieapp.data.models.detail.SpokenLanguage
import com.prmto.mova_movieapp.data.models.detail.tv.*
import com.prmto.mova_movieapp.data.models.watch_provider.WatchProviders
import org.junit.Before
import org.junit.Test

class TvDetailDtoTest {

    lateinit var tvDetailDto: TvDetailDto
    lateinit var listOfCreatedByDto: List<CreatedByDto>
    lateinit var listOfGenre: List<Genre>
    lateinit var listOfSeasons: List<Season>
    lateinit var listOfCastDto: List<CastDto>
    lateinit var listOfCrewDto: List<CrewDto>

    @Before

    fun setup() {

        listOfCreatedByDto = listOf(
            CreatedByDto(
                id = 66633,
                creditId = "52542286760ee31328001a7b",
                gender = 2,
                name = "Vince Gilligan",
                profilePath = "/uFh3OrBvkwKSU3N5y0XnXOhqBJz.jpg"
            )
        )

        listOfGenre = listOf(
            Genre(
                id = 18,
                name = "Drama"
            )
        )

        listOfSeasons = listOf(
            Season(
                airDate = "2009-02-17",
                episodeCount = 11,
                id = 3577,
                name = "Specials",
                overview = "",
                posterPath = "/40dT79mDEZwXkQiZNBgSaydQFDP.jpg",
                seasonNumber = 0
            )
        )

        listOfCastDto = listOf(
            CastDto(
                id = 17419,
                adult = false,
                gender = 2,
                knownForDepartment = "Acting",
                name = "Bryan Cranston",
                originalName = "Bryan Cranston",
                popularity = 38.295,
                profilePath = "/7Jahy5LZX2Fo8fGJltMreAI49hC.jpg",
                character = "Walter White",
                creditId = "52542282760ee313280017f9",
                order = 0
            )
        )

        listOfCrewDto = listOf(
            CrewDto(
                id = 66633,
                adult = false,
                gender = 2,
                knownForDepartment = "Writing",
                name = "Vince Gilligan",
                originalName = "Vince Gilligan",
                popularity = 11.61,
                profilePath = "/uFh3OrBvkwKSU3N5y0XnXOhqBJz.jpg",
                creditId = "52542287760ee31328001af1",
                department = "Production",
                job = "Executive Producer"
            )
        )

        tvDetailDto = TvDetailDto(
            backdropPath = "/84XPpjGvxNyExjSuLQe0SzioErt.jpg",
            createdBy = listOfCreatedByDto,
            episodeRunTime = listOf(45, 47),
            firstAirDate = "2008-01-20",
            genres = listOfGenre,
            homepage = "http://www.amc.com/shows/breaking-bad",
            id = 1396,
            inProduction = false,
            languages = listOf("en"),
            lastAirDate = "2013-09-29",
            lastEpisodeToAir =
            LastEpisodeToAir(
                airDate = "2013-09-29",
                episodeNumber = 16,
                id = 62161,
                name = "Felina",
                overview = "All bad things must come to an end.",
                productionCode = "",
                seasonNumber = 5,
                stillPath = "/pA0YwyhvdDXP3BEGL2grrIhq8aM.jpg",
                voteAverage = 9.303,
                voteCount = 152
            ),
            name = "Breaking Bad",
            nextEpisodeToAir = null,
            networks =
            listOf(
                Network(
                    id = 174,
                    name = "AMC",
                    logoPath = "/pmvRmATOCaDykE6JrVoeYxlFHw3.png",
                    originCountry = "US"
                )
            ),
            numberOfEpisodes = 62,
            numberOfSeasons = 5,
            originCountry = listOf("US"),
            originalLanguage = "en",
            originalName = "Breaking Bad",
            overview = "When Walter White, a New Mexico chemistry teacher, is diagnosed with Stage III cancer and given a prognosis of only two years left to live. He becomes filled with a sense of fearlessness and an unrelenting desire to secure his family's financial future at any cost as he enters the dangerous world of drugs and crime.",
            popularity = 293.492,
            posterPath = "/ggFHVNu6YYI5L9pCfOacjizRGt.jpg",
            productionCompanies =
            listOf(
                ProductionCompany(
                    id = 11073,
                    logoPath = null,
                    name = "Sony Pictures Television Studios",
                    originCountry = "US"
                )
            ),

            productionCountries =
            listOf(
                ProductionCountry(
                    iso_3166_1 = "US",
                    name = "United States of America"
                )
            ),
            seasons = listOfSeasons,
            spokenLanguages =
            listOf(
                SpokenLanguage(
                    iso_639_1 = "en",
                    name = "English"
                )
            ),
            status = "Ended",
            tagline = "Remember my name",
            type = "Scripted",
            voteAverage = 8.851,
            voteCount = 10524,
            credits = CreditDto(
                cast = listOfCastDto,
                crew = listOfCrewDto
            ),
            watchProviders = WatchProviders(null)
        )
    }

    @Test
    fun `tvDetailDto can be mapped to tvDetail`() {
        val tvDetail = tvDetailDto.toTvDetail()

        assertThat(tvDetail.id).isEqualTo(tvDetailDto.id)
        assertThat(tvDetail.genres).isEqualTo(listOfGenre)
        assertThat(tvDetail.firstAirDate).isEqualTo(tvDetailDto.firstAirDate)
        assertThat(tvDetail.lastAirDate).isEqualTo(tvDetailDto.lastAirDate)
        assertThat(tvDetail.createdBy).isEqualTo(listOfCreatedByDto.toListOfCreatedBy())
        assertThat(tvDetail.numberOfSeasons).isEqualTo(tvDetailDto.numberOfSeasons)
        assertThat(tvDetail.originalName).isEqualTo(tvDetailDto.originalName)
        assertThat(tvDetail.name).isEqualTo(tvDetailDto.name)
        assertThat(tvDetail.overview).isEqualTo(tvDetailDto.overview)
        assertThat(tvDetail.posterPath).isEqualTo(tvDetailDto.posterPath)
        assertThat(tvDetail.seasons).isEqualTo(tvDetailDto.seasons)
        assertThat(tvDetail.status).isEqualTo(tvDetailDto.status)
        assertThat(tvDetail.voteAverage).isEqualTo(tvDetailDto.voteAverage)
        assertThat(tvDetail.voteCount).isEqualTo(tvDetailDto.voteCount)
        assertThat(tvDetail.watchProviders).isEqualTo(tvDetailDto.watchProviders)
        assertThat(tvDetail.credit).isEqualTo(tvDetailDto.credits.toCredit())


    }
}