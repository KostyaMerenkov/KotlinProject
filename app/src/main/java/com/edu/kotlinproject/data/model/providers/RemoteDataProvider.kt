package com.edu.kotlinproject.data.model.providers

import androidx.lifecycle.LiveData
import com.edu.kotlinproject.data.model.Note
import com.edu.kotlinproject.data.model.NoteResult
import com.edu.kotlinproject.data.model.User

interface RemoteDataProvider {

    fun subscribeToAllNotes(): LiveData<NoteResult>

    fun getNoteById(id: String): LiveData<NoteResult>

    fun saveNote(note: Note) : LiveData<NoteResult>

    fun getCurrentUser(): LiveData<User?>

}