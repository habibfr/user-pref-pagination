package com.habibfr.suitmedia_test_mobile.data.remote.api.retrofit

import com.habibfr.suitmedia_test_mobile.data.remote.api.response.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users/")
    suspend fun getUsers(
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1,
    ): UsersResponse
}