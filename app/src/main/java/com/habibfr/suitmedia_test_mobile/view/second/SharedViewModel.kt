package com.habibfr.suitmedia_test_mobile.view.second

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habibfr.suitmedia_test_mobile.data.pref.UserModel
import com.habibfr.suitmedia_test_mobile.data.remote.repository.UserRepository
import kotlinx.coroutines.launch

class SharedViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userData = MutableLiveData<UserModel>()
    val userData: LiveData<UserModel> = _userData

    init {
        viewModelScope.launch {
            userRepository.getDataUser().collect { userData ->
                _userData.value = userData
            }
        }
    }

    fun setName(newName: String) {
        viewModelScope.launch {
            userRepository.setName(newName)
        }
    }

    fun setSelectedUser(newSelectedUser: String) {
        viewModelScope.launch {
            userRepository.setSelectedUser(newSelectedUser)
        }
    }
}