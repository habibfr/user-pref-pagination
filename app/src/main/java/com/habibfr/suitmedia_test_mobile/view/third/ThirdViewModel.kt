package com.habibfr.suitmedia_test_mobile.view.third

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habibfr.suitmedia_test_mobile.data.remote.Result
import com.habibfr.suitmedia_test_mobile.data.remote.api.response.UsersResponse
import com.habibfr.suitmedia_test_mobile.data.remote.repository.UserRepository
import kotlinx.coroutines.launch

class ThirdViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    val users: LiveData<Result<UsersResponse>> = userRepository.users

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            userRepository.getUsers()
        }
    }
}