package com.prmto.mova_movieapp.data.detail.tv

import com.google.common.truth.Truth.assertThat
import com.prmto.mova_movieapp.data.models.detail.tv.CreatedByDto
import com.prmto.mova_movieapp.data.models.detail.tv.toListOfCreatedBy
import org.junit.Before
import org.junit.Test

class CreatedByDtoTest {

    lateinit var listOfCreatedByDto: List<CreatedByDto>

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
    }

    @Test
    fun `list of the createdByDto can be mapped to list of the createdByEntity`() {
        val createdByEntity = listOfCreatedByDto.toListOfCreatedBy().first()

        assertThat(createdByEntity.id).isEqualTo(66633)
        assertThat(createdByEntity.name).isEqualTo("Vince Gilligan")
    }
}