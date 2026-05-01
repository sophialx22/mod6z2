package com.example.nobelprize.domain.repository

import com.example.nobelprize.domain.model.NobelPrize

interface NobelRepository {
    suspend fun getNobelPrizes(year: String?, category: String?): Result<List<NobelPrize>>
}