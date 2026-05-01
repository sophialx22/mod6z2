package com.example.nobelprize.domain.usecase
import com.example.nobelprize.domain.model.NobelPrize
import com.example.nobelprize.domain.repository.NobelRepository

class GetNobelPrizesUseCase(
    private val repository: NobelRepository
) {
    suspend operator fun invoke(year: String?, category: String?): Result<List<NobelPrize>> {
        return repository.getNobelPrizes(year, category)
    }
}