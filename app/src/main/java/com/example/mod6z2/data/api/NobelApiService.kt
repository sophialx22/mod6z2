package com.example.nobelprize.data.api
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import com.example.nobelprize.data.dto.NobelPrizeResponseDto

class NobelApiService(private val client: HttpClient) {
    suspend fun getNobelPrizes(
        limit: Int = 50,
        offset: Int = 0,
        year: String? = null,
        category: String? = null
    ): NobelPrizeResponseDto {
        return client.get {
            url("https://api.nobelprize.org/2.1/nobelPrizes")
            parameter("limit", limit)
            parameter("offset", offset)
            year?.let { parameter("nobelPrizeYear", it) }
            category?.let { parameter("nobelPrizeCategory", category) }
        }.body()
    }
}