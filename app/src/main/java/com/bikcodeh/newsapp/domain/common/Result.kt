package com.bikcodeh.newsapp.domain.common

sealed class Result<T> {
    class Success<T>(val data: T) : Result<T>()
    class Error<T>(val message: String?) : Result<T>()
}

inline fun <R, T> Result<T>.fold(
    onSuccess: (value: T?) -> R,
    onFailure: (error: String?) -> R,
): R = when (this) {
    is Result.Success -> onSuccess(data)
    is Result.Error -> onFailure(message)
}

fun <T> Result<T>.isSuccess(): Boolean = when (this) {
    is Result.Success -> true
    is Result.Error -> false
}

fun <T> Result<T>.isFailure(): Boolean = when (this) {
    is Result.Success -> false
    is Result.Error -> true
}
