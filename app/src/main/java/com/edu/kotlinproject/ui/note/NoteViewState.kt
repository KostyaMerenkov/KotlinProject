package com.edu.kotlinproject.ui.note

import com.edu.kotlinproject.data.model.Note
import com.edu.kotlinproject.ui.base.BaseViewState

class NoteViewState(data: Data = Data(), error: Throwable? = null) :
    BaseViewState<NoteViewState.Data>(data, error) {

    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}

