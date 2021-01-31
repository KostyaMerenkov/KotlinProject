package com.edu.kotlinproject

import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.edu.kotlinproject.R
import com.edu.kotlinproject.data.model.Note
import com.edu.kotlinproject.databinding.ActivityMainBinding
import com.edu.kotlinproject.ui.BaseActivity
import com.edu.kotlinproject.ui.main.MainAdapter
import com.edu.kotlinproject.ui.main.MainViewModel
import com.edu.kotlinproject.ui.main.MainViewState
import com.edu.kotlinproject.ui.note.NoteActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : BaseActivity<List<Note>?, MainViewState>() {

    override val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    override val layoutRes: Int = R.layout.activity_main
    private lateinit var adapter: MainAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        adapter = MainAdapter( object : MainAdapter.OnItemClickListener {
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
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun openNoteScreen(note: Note?) {
        val intent = NoteActivity.getStartIntent(this, note?.id)
        startActivity(intent)
    }

    override fun renderData(data: List<Note>?) {
        if (data == null) return
        adapter.notes = data
    }

}