package com.example.nobelprize.presentation
import com.example.nobelprize.domain.model.NobelPrize

sealed class NobelListState {
    object Loading : NobelListState()
    data class Success(val prizes: List<NobelPrize>) : NobelListState()
    data class Error(val message: String) : NobelListState()
}