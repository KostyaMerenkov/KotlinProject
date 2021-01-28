package com.edu.kotlinproject.ui.note

import androidx.lifecycle.ViewModel
import com.edu.kotlinproject.data.Repository
import com.edu.kotlinproject.data.model.Note
import java.util.*

class NoteViewModel(private val repository: Repository = Repository) : ViewModel() {

    private var pendingNote: Note? = null

    fun saveChanges(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        if (pendingNote != null) {
            repository.saveNote(pendingNote!!)
        }
    }

    fun getID(): String {
        return UUID.randomUUID().toString()
    }
}