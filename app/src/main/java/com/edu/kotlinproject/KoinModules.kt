package com.edu.kotlinproject

import com.edu.kotlinproject.data.model.Repository
import com.edu.kotlinproject.data.model.providers.FireStoreProvider
import com.edu.kotlinproject.data.model.providers.RemoteDataProvider
import com.edu.kotlinproject.ui.main.MainViewModel
import com.edu.kotlinproject.ui.note.NoteViewModel
import com.edu.kotlinproject.ui.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module


val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FireStoreProvider(get(), get()) } bind RemoteDataProvider::class
    single { Repository(get()) }

}

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val noteModule = module {
    viewModel { NoteViewModel(get()) }
}
