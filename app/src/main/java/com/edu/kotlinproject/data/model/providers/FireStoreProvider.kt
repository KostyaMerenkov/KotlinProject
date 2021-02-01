package com.edu.kotlinproject.data.model.providers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.edu.kotlinproject.data.model.NoAuthException
import com.edu.kotlinproject.data.model.Note
import com.edu.kotlinproject.data.model.NoteResult
import com.edu.kotlinproject.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*


private const val NOTES_COLLECTION = "NOTES"
private const val USERS_COLLECTION = "USERS"

class FireStoreProvider : RemoteDataProvider {

    companion object {
        private val TAG = "${FireStoreProvider::class.java.simpleName} :"
    }

    private val db = FirebaseFirestore.getInstance()
    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser

    override fun subscribeToAllNotes(): LiveData<NoteResult> =
            MutableLiveData<NoteResult>().apply {
                try {
                    getUserNotesCollection().addSnapshotListener { snapshot, error ->
                        value = error?.let { NoteResult.Error(error) }
                                ?: snapshot?.let { query ->
                                    val notes = query.documents.map { document ->
                                        document.toObject(Note::class.java)
                                    }
                                    NoteResult.Success(notes)
                                }
                    }
                } catch (error: Throwable) {
                    value = NoteResult.Error(error)
                }
            }

    override fun getNoteById(id: String): LiveData<NoteResult> =
            MutableLiveData<NoteResult>().apply {
                try {
                    getUserNotesCollection().document(id).get()
                            .addOnSuccessListener {
                                value = NoteResult.Success(it.toObject(Note::class.java))
                            }
                            .addOnFailureListener {
                                value = NoteResult.Error(it)
                            }
                } catch (error: Throwable) {
                    value = NoteResult.Error(error)
                }
            }

    override fun saveNote(note: Note): LiveData<NoteResult> =
            MutableLiveData<NoteResult>().apply {
                try {
                    getUserNotesCollection().document(note.id)
                            .set(note)
                            .addOnSuccessListener {
                                Log.d(TAG, "Note $note is saved")
                                value = NoteResult.Success(note)
                            }
                            .addOnFailureListener {
                                Log.d(TAG, "Error while saving note $note, message ${it.message}")
                                value = NoteResult.Error(it)
                            }
                } catch (error: Throwable) {
                    value = NoteResult.Error(error)
                }
            }

    override fun getCurrentUser(): LiveData<User?> =
        MutableLiveData<User?>().apply {
            value = currentUser?.let {
                User(
                        it.displayName ?: "",
                        it.email ?: ""
                ) }
        }

    private fun getUserNotesCollection() = currentUser?.let { firebaseUser ->
        db.collection(USERS_COLLECTION)
                .document(firebaseUser.uid)
                .collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()
}

