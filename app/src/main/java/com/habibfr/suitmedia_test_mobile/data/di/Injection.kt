package com.habibfr.suitmedia_test_mobile.data.di

import android.content.Context
import com.habibfr.suitmedia_test_mobile.data.pref.UserPreference
import com.habibfr.suitmedia_test_mobile.data.pref.dataStore
import com.habibfr.suitmedia_test_mobile.data.remote.api.retrofit.ApiConfig
import com.habibfr.suitmedia_test_mobile.data.remote.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val pref = UserPreference.getInstance(context.dataStore)


        return UserRepository.getInstance(apiService, pref)
    }
}