package com.edu.kotlinproject.ui.splash

import com.edu.kotlinproject.ui.base.BaseViewState

class SplashViewState(isAuth: Boolean? = null, error: Throwable? = null) :
    BaseViewState<Boolean?>(isAuth, error)
