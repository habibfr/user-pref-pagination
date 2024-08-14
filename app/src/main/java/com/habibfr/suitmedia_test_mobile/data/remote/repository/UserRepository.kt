package com.habibfr.suitmedia_test_mobile.data.remote.repository

import UserPagingSource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.google.gson.Gson
import com.habibfr.suitmedia_test_mobile.data.pref.UserModel
import com.habibfr.suitmedia_test_mobile.data.pref.UserPreference
import com.habibfr.suitmedia_test_mobile.data.remote.Result
import com.habibfr.suitmedia_test_mobile.data.remote.api.response.ErrorResponse
import com.habibfr.suitmedia_test_mobile.data.remote.api.response.User
import com.habibfr.suitmedia_test_mobile.data.remote.api.response.UsersResponse
import com.habibfr.suitmedia_test_mobile.data.remote.api.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    private val _users = MutableLiveData<Result<UsersResponse>>()
    val users: LiveData<Result<UsersResponse>> = _users

    fun getUsersPaginate(): LiveData<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1
            ),
            pagingSourceFactory = {
                UserPagingSource(apiService)
            }
        ).liveData
    }

    suspend fun getUsers() {
        _users.value = Result.Loading
        try {
            val response = apiService.getUsers(1, 10)
            _users.value = Result.Success(response)
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            _users.value = Result.Error(errorMessage ?: "An error occurred")
        }
    }

    fun getAdvertisedUsersPaging(): LiveData<PagingData<User>> {
        return Pager(config = PagingConfig(pageSize = 5),
            pagingSourceFactory = { UserPagingSource(apiService) }).liveData
    }

    suspend fun setName(name: String) {
        userPreference.editName(name)
    }

    suspend fun setSelectedUser(selectedUser: String) {
        userPreference.editSelectedUser(selectedUser)
    }

    fun getDataUser(): Flow<UserModel> {
        return userPreference.getDataUser()
    }

    suspend fun logout() {
        userPreference.clear()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }
    }
}