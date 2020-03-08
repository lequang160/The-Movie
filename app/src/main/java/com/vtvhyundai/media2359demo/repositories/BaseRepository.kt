package com.vtvhyundai.media2359demo.repositories

import com.vtvhyundai.media2359demo.core.ResultState
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException


open class BaseRepository {
    suspend fun <T : Any> safeApiCall(call: suspend () -> T): ResultState<T> = newsApiOutput(call)

    private suspend fun <T : Any> newsApiOutput(
        call: suspend () -> T
    ): ResultState<T> {
        return try {
            ResultState.Success(call.invoke())
        } catch (e: HttpException) {
            ResultState.Error(e)
        } catch (e: TimeoutCancellationException) {
            ResultState.Error(e)
        } catch (e: Throwable) {
            ResultState.Error(Exception(e.message))
        }
    }
}