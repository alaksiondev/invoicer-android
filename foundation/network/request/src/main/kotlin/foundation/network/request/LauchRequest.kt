package foundation.network.request

import foundation.exception.RequestError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

sealed interface RequestState<out T> {
    data object Started : RequestState<Nothing>
    data class Success<T>(val data: T) : RequestState<T>
    data class Error(val exception: RequestError) : RequestState<Nothing>
    data object Finished: RequestState<Nothing>
}

fun <T> launchRequest(
    block: suspend () -> T,
): Flow<RequestState<T>> = flow {
    emit(RequestState.Started)
    runCatching {
        block()
    }.onFailure { error ->
        if (error is RequestError) emit(RequestState.Error(error))
        else RequestError.Other(error)
    }.onSuccess {
        emit(RequestState.Success(it))
    }
    emit(RequestState.Finished)
}