package com.prmto.mova_movieapp.data.credit

import com.google.common.truth.Truth.assertThat
import com.prmto.mova_movieapp.data.models.credit.CastDto
import com.prmto.mova_movieapp.data.models.credit.toCast
import org.junit.Before
import org.junit.Test

class CastDtoTest {

    private lateinit var listCastDto: List<CastDto>

    @Before
    fun setup() {
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
    }

    @Test
    fun `list of the castDto can be mapped to list of the castDto`() {
        val castEntity = listCastDto.toCast().first()

        assertThat(castEntity.id).isEqualTo(17419)
        assertThat(castEntity.originalName).isEqualTo("Bryan Cranston")
        assertThat(castEntity.name).isEqualTo("Bryan Cranston")
        assertThat(castEntity.profilePath).isEqualTo("/7Jahy5LZX2Fo8fGJltMreAI49hC.jpg")
        assertThat(castEntity.character).isEqualTo("Walter White")
    }
}