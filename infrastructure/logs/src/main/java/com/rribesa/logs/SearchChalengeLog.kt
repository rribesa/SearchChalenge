package com.rribesa.logs

sealed class SearchChalengeLog {
    data class Error(
        val error: Throwable,
        val origin: String,
    ) : SearchChalengeLog()

    data class Warning(
        val message: String,
        val origin: String,
    ): SearchChalengeLog()
}
