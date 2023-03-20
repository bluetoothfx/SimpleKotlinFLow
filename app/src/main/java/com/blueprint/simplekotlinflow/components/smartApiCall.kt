package com.blueprint.simplekotlinflow.components

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

suspend fun <T> smartApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> Response<T>
): Flow<ResultWrapper<T>> = flow {
    emit(ResultWrapper.Loading)
    val response = apiCall()
    if (response.isSuccessful) {
        val data = response.body()
        if (data != null) {
            emit(ResultWrapper.Success(data))
        } else {
            val error = response.errorBody()
            if (error != null) {
                emit(ResultWrapper.Failure(IOException(error.toString())))
            } else {
                emit(ResultWrapper.Failure(IOException("something went wrong")))
            }
        }
    } else {
        emit(ResultWrapper.Failure(Throwable(response.errorBody().toString())))
    }
}.catch { e ->
    e.printStackTrace()
    emit(ResultWrapper.Failure(Exception(e)))
}.flowOn(dispatcher)
