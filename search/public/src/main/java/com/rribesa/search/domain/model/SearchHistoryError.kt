package com.rribesa.search.domain.model

sealed class SearchHistoryError : Throwable() {
    data class SaveHistoryError(val originError: Throwable) : SearchHistoryError()
    data class GetHistoryError(val originError: Throwable) : SearchHistoryError()
}
