package com.example.nobelprize.data.dto
import kotlinx.serialization.Serializable

@Serializable
data class NobelPrizeResponseDto(
    val nobelPrizes: List<NobelPrizeDto>,
    val meta: MetaDto
)

@Serializable
data class NobelPrizeDto(
    val awardYear: String,
    val category: CategoryDto,
    val laureates: List<LaureateDto>? = emptyList(),
)

@Serializable
data class CategoryDto(
    val en: String,
)

@Serializable
data class LaureateDto(
    val id: String,
    val fullName: FullNameDto? = null,
    val motivation: MotivationDto? = null,
    val portion: String = "1",
    val knownName: KnownNameDto? = null,
)

@Serializable
data class FullNameDto(
    val en: String? = null,
)

@Serializable
data class KnownNameDto(
    val en: String? = null,
)

@Serializable
data class MotivationDto(
    val en: String? = null,
)

@Serializable
data class MetaDto(
    val offset: Int,
    val limit: Int,
    val count: Int
)