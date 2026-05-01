package com.example.nobelprize.domain.model

data class Laureate(
    val id: String,
    val fullName: String,
    val motivation: String,
    val portion: String,
)

data class NobelPrize(
    val awardYear: String,
    val category: String,
    val laureates: List<Laureate>,
)