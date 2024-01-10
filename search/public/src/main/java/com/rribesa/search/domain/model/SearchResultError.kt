package com.rribesa.search.domain.model

sealed class SearchResultError : Throwable() {
    data class NetworkError(val originError: Throwable) : SearchResultError()
}