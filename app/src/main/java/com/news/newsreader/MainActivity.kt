package com.news.newsreader

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.news.newsreader.model.db.models.CategoryWithNews
import com.news.newsreader.model.db.models.NewsCategoryModel
import com.news.newsreader.ui.NewsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName
    lateinit var viewModel : NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_activity_toolbar)
        (this.application as NewsReaderApplication)?.let {
            viewModel = ViewModelProvider(this,it.AppContainer.viewModelFactory).get(NewsViewModel::class.java)
            viewModel.categories.observe(this, Observer {
                Log.d(TAG,"Received categories $it")
            })

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.menu_activity_main_actions,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return false
    }
}