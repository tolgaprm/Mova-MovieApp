package com.prmto.mova_movieapp.data.credit

import com.google.common.truth.Truth.assertThat
import com.prmto.mova_movieapp.data.models.credit.CrewDto
import com.prmto.mova_movieapp.data.models.credit.toCrew
import org.junit.Before
import org.junit.Test

class CrewDtoTest {

    private lateinit var listCrewDto: List<CrewDto>

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
    }

    @Test
    fun `list of the crewDto can be mapped to list of the crewDto`() {
        val castEntity = listCrewDto.toCrew().first()

        assertThat(castEntity.id).isEqualTo(66633)
        assertThat(castEntity.originalName).isEqualTo("Vince Gilligan")
        assertThat(castEntity.name).isEqualTo("Vince Gilligan")
        assertThat(castEntity.profilePath).isEqualTo("/uFh3OrBvkwKSU3N5y0XnXOhqBJz.jpg")
        assertThat(castEntity.department).isEqualTo("Production")

    }
}