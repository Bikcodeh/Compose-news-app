package com.bikcodeh.newsapp.domain.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> makeSafeRequest(
    execute: suspend () -> T
): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            Result.Success(execute())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}