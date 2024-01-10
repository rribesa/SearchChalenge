package com.rribesa.logs

import android.util.Log

class LoggerImpl : Logger {
    override suspend fun registerLog(log: SearchChalengeLog) {
        when (log) {
            is SearchChalengeLog.Error -> Log.e(log.origin, log.error.message.orEmpty())
            is SearchChalengeLog.Warning -> Log.w(log.origin, log.message)
        }
    }
}