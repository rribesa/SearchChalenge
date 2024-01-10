package com.rribesa.search.domain.usecase

interface FilterHistoryUseCase {
    suspend operator fun invoke(term: String): Result<List<String>>
}