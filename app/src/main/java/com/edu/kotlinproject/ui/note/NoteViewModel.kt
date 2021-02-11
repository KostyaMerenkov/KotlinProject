package com.edu.kotlinproject.ui.note

import androidx.lifecycle.Observer
import com.edu.kotlinproject.data.model.Note
import com.edu.kotlinproject.data.model.NoteResult
import com.edu.kotlinproject.data.model.Repository

import com.edu.kotlinproject.ui.base.BaseViewModel
import java.util.*


class NoteViewModel(private val repository: Repository) :
    BaseViewModel<NoteViewState.Data, NoteViewState>() {

    private val currentNote: Note?
    get() = viewStateLiveData.value?.data?.note

    fun saveChanges(note: Note) {
        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = note))
    }

    override fun onCleared() {
        currentNote?.let { repository.saveNote(it) }
    }

    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever(Observer<NoteResult> { t ->
            t?.let { noteResult ->
                viewStateLiveData.value = when (noteResult) {
                    is NoteResult.Success<*> ->
                        NoteViewState(NoteViewState.Data(note = noteResult.data as? Note))
                    is NoteResult.Error ->
                        NoteViewState(error = noteResult.error)
                }
            }
        })
    }

    fun getID(): String {
        return UUID.randomUUID().toString()
    }

    fun deleteNote() {
        currentNote?.let { it ->
            repository.deleteNote(it.id).observeForever(Observer<NoteResult> { t ->
                t?.let { noteResult ->
                    viewStateLiveData.value = when (noteResult) {
                        is NoteResult.Success<*> -> NoteViewState(NoteViewState.Data(isDeleted = true))
                        is NoteResult.Error -> NoteViewState(error = noteResult.error)
                    }
                }
            })
        }
    }

}