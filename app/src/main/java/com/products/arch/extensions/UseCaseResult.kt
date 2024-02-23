package com.products.arch.extensions

import com.products.core.domain.error.ExceptionModel
import com.products.core.domain.error.toException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform

sealed class UseCaseResult<out T> {
    data class Success<T>(val value: T) : UseCaseResult<T>()
    data class Error(val exception: ExceptionModel) : UseCaseResult<Nothing>()
}

@Suppress("TooGenericExceptionCaught")
inline fun <T> useCaseFlow(
    coroutineDispatcher: CoroutineDispatcher,
    crossinline block: suspend () -> T,
): Flow<UseCaseResult<T>> = flow {
    try {
        val repoResult = block()
        emit(UseCaseResult.Success(repoResult))
    } catch (e: ExceptionModel) {
        emit(UseCaseResult.Error(e))
    } catch (e: Exception) {
        emit(UseCaseResult.Error(e.toException()))
    }
}.flowOn(coroutineDispatcher)

fun <T> Flow<UseCaseResult<T>>.onSuccess(action: suspend (T) -> Unit): Flow<UseCaseResult<T>> =
    transform { result ->
        if (result is UseCaseResult.Success<T>) {
            action(result.value)
        }
        return@transform emit(result)
    }
