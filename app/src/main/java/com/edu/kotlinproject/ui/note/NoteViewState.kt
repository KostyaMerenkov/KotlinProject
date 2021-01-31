package com.edu.kotlinproject.ui.note

import com.edu.kotlinproject.data.model.Note
import com.edu.kotlinproject.ui.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error)