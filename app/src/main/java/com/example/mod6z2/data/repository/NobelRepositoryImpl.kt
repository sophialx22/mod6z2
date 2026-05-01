package com.example.nobelprize.data.repository
import com.example.nobelprize.data.api.NobelApiService
import com.example.nobelprize.data.dto.LaureateDto
import com.example.nobelprize.domain.model.Laureate
import com.example.nobelprize.domain.model.NobelPrize
import com.example.nobelprize.domain.repository.NobelRepository

class NobelRepositoryImpl(
    private val apiService: NobelApiService
) : NobelRepository {

    override suspend fun getNobelPrizes(year: String?, category: String?): Result<List<NobelPrize>> {
        return try {
            val response = apiService.getNobelPrizes(year = year, category = category)
            val prizes = response.nobelPrizes.map { dto ->
                NobelPrize(
                    awardYear = dto.awardYear,
                    category = dto.category.en,
                    laureates = dto.laureates?.map { laureateDto ->
                        laureateDto.toDomain()
                    } ?: emptyList()
                )
            }
            Result.success(prizes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun LaureateDto.toDomain(): Laureate {
        return Laureate(
            id = id,
            fullName = fullName?.en ?: knownName?.en ?: "Unknown Laureate",
            motivation = motivation?.en ?: "No motivation provided",
            portion = portion
        )
    }
}