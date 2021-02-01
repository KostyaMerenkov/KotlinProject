package com.edu.kotlinproject.ui.splash

import com.edu.kotlinproject.data.model.NoAuthException
import com.edu.kotlinproject.data.model.Repository
import com.edu.kotlinproject.ui.base.BaseViewModel

class SplashViewModel(private val repository: Repository = Repository) : BaseViewModel<Boolean?, SplashViewState>(){
    fun requestUser() {
        repository.getCurrentUser().observeForever { user ->
            viewStateLiveData.value = user?.let {
                SplashViewState(isAuth = true)
            } ?: SplashViewState(error = NoAuthException())
        }
    }
}