package com.edu.kotlinproject.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.edu.kotlinproject.R
import com.edu.kotlinproject.data.model.Note
import com.edu.kotlinproject.databinding.ActivityMainBinding
import com.edu.kotlinproject.ui.LogoutDialog
import com.edu.kotlinproject.ui.base.BaseActivity
import com.edu.kotlinproject.ui.note.NoteActivity
import com.edu.kotlinproject.ui.splash.SplashActivity
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : BaseActivity<List<Note>?, MainViewState>(), LogoutDialog.LogoutListener {

    override val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    override val ui: ActivityMainBinding
            by lazy {
                ActivityMainBinding.inflate(layoutInflater)
            }
    override val layoutRes: Int = R.layout.activity_main
    private lateinit var adapter: MainAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        adapter = MainAdapter(object : MainAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                openNoteScreen(note)
            }
        })
        mainRecycler.adapter = adapter

        fab.setOnClickListener { openNoteScreen(null) }

//        super.onCreate(savedInstanceState)
//        ui = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(ui.root)
//
//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)
//
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        adapter = MainAdapter(object : MainAdapter.OnItemClickListener {
//            override fun onItemClick(note: Note) {
//                openNoteScreen(note)
//            }
//        })
//        ui.mainRecycler.adapter = adapter
//
//        viewModel.viewState().observe(this, Observer<MainViewState> { t ->
//            t?.let { adapter.notes = it.notes }
//        })
//
//        ui.fab.setOnClickListener {
//            openNoteScreen(null)
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.menu_main, menu)
        MenuInflater(this).inflate(R.menu.menu_main, menu).let { true }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when(item.itemId) {
            R.id.action_sign_out -> showLogoutDialog().let { true }
            else -> false
        }

    private fun showLogoutDialog() {
        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG) ?:
        LogoutDialog.createInstance().show(supportFragmentManager, LogoutDialog.TAG)
    }

    private fun openNoteScreen(note: Note?) {
        val intent = NoteActivity.getStartIntent(this, note?.id)
        startActivity(intent)
    }

    override fun renderData(data: List<Note>?) {
        if (data == null) return
        adapter.notes = data
    }

    companion object {
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this, SplashActivity::class.java))
                finish()

            }
    }

}