package com.products.arch.extensions

import com.products.core.domain.error.toException
import retrofit2.Response

inline fun <T, R> Response<T>.mapSuccess(
    crossinline block: (T) -> R
): R {
    val safeBody = body()
    if (this.isSuccessful && safeBody != null) {
        return block(safeBody)
    } else {
        throw toException()
    }
}