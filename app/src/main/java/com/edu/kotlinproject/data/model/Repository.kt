package com.edu.kotlinproject.data.model

import com.edu.kotlinproject.data.model.providers.FireStoreProvider
import com.edu.kotlinproject.data.model.providers.RemoteDataProvider
import java.util.*

object Repository {

    private val remoteDataProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteDataProvider.subscribeToAllNotes()

    fun saveNote(note: Note) = remoteDataProvider.saveNote(note)

    fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)

    fun getCurrentUser() = remoteDataProvider.getCurrentUser()
}