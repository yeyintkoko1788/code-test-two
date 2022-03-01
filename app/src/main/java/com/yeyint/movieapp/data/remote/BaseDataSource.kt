package com.yeyint.movieapp.data.remote

import com.yeyint.movieapp.common.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.Success(body)
            }
            return Resource.Error(" ${response.code()} ${response.message()}")
        } catch (e: HttpException) {
            return Resource.Error(e.localizedMessage ?: "An unexpected error occured")
        } catch (e: IOException) {
            return Resource.Error("Couldn't reach server. Check your internet connection.")
        }
    }
}