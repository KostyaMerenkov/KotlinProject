package com.edu.kotlinproject.ui

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.edu.kotlinproject.R
import com.edu.kotlinproject.data.model.Note
import com.edu.kotlinproject.databinding.ActivityMainBinding
import com.edu.kotlinproject.ui.main.MainAdapter
import com.edu.kotlinproject.ui.main.MainViewModel
import com.edu.kotlinproject.ui.main.MainViewState
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity <T, VS: BaseViewState<T>>: AppCompatActivity(){

    abstract val viewModel: BaseViewModel<T, VS>
    abstract val layoutRes: Int
    //abstract val ui: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(ui.root)
        setContentView(layoutRes)

        viewModel.getViewState().observe(this, object : Observer<VS> {
            override fun onChanged(t: VS?) {
                if (t == null) return
                if (t.data != null) renderData(t.data!!)
                if (t.error != null) renderError(t.error)
            }
        })
    }

    protected fun renderError(error: Throwable) {
        if (error.message != null) showError(error.message!!)
    }

    abstract fun renderData(data: T)

    protected fun showError(error: String) {
//        val snackbar = Snackbar.make(ui.root, error, Snackbar.LENGTH_INDEFINITE)
//        snackbar.setAction(R.string.ok_bth_title, View.OnClickListener { snackbar.dismiss() })
//        snackbar.show()
    }

}