package com.habibfr.suitmedia_test_mobile.view.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.habibfr.suitmedia_test_mobile.data.di.Injection
import com.habibfr.suitmedia_test_mobile.data.remote.repository.UserRepository
import com.habibfr.suitmedia_test_mobile.view.second.SharedViewModel
import com.habibfr.suitmedia_test_mobile.view.third.ThirdViewModel

class ViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ThirdViewModel::class.java) -> {
                ThirdViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(SharedViewModel::class.java) -> {
                SharedViewModel(userRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.provideRepository(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}