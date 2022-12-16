package com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.credit

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class CreditDtoTest {

    private lateinit var creditDto: CreditDto
    private lateinit var listCrewDto: List<CrewDto>
    private lateinit var listCastDto: List<CastDto>

    @Before
    fun setup() {
        listCrewDto = listOf(
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

        listCastDto = listOf(
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

        creditDto = CreditDto(
            cast = listCastDto,
            crew = listCrewDto
        )
    }

    @Test
    fun `creditDto can be mapped to credit`() {
        val creditEntity = creditDto.toCredit()

        assertThat(creditEntity.crew).isEqualTo(listCrewDto.toCrew())
        assertThat(creditEntity.cast).isEqualTo(listCastDto.toCast())
    }
}