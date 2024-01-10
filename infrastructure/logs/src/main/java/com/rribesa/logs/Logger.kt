package com.rribesa.logs

interface Logger {
    suspend fun registerLog(log: SearchChalengeLog)
}