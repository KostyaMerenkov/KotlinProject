package com.edu.kotlinproject.ui.main

import com.edu.kotlinproject.data.model.Note
import com.edu.kotlinproject.ui.BaseViewState

class MainViewState(val notes: List<Note>? = null, error: Throwable? = null) : BaseViewState<List<Note>?>(notes, error)