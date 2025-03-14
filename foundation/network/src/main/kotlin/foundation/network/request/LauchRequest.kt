package foundation.network.request

import foundation.network.RequestError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

sealed interface RequestState<out T> {
    data object Started : RequestState<Nothing>
    data class Success<T>(val data: T) : RequestState<T>
    data class Error(val exception: RequestError) : RequestState<Nothing>
    data object Finished : RequestState<Nothing>
}

fun <T> launchRequest(
    block: suspend () -> T,
): Flow<RequestState<T>> = flow {
    emit(RequestState.Started)
    runCatching {
        block()
    }.onFailure { error ->
        if (error is RequestError) emit(RequestState.Error(error))
        else emit(RequestState.Error(RequestError.Other(error)))
    }.onSuccess {
        emit(RequestState.Success(it))
    }
    emit(RequestState.Finished)
}

suspend fun <T> Flow<RequestState<T>>.handle(
    onStart: suspend () -> Unit = {},
    onFinish: suspend () -> Unit = {},
    onFailure: suspend (RequestError) -> Unit,
    onSuccess: suspend (T) -> Unit
) {
    collect {
        when (it) {
            is RequestState.Error -> onFailure(it.exception)
            RequestState.Finished -> onFinish()
            RequestState.Started -> onStart()
            is RequestState.Success -> onSuccess(it.data)
        }
    }
}