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
    lateinit var categoriesList : List<NewsCategoryModel>
    lateinit var categoriesSelectedList : List<CategoryWithNews>

    private val REQUEST_CODE = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_activity_toolbar)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.categories.observe(this, Observer {
            Log.d(TAG,"Received categories $it")
            categoriesList = it
        })
        viewModel.items.observe(this, Observer {
            Log.d(TAG,"Received CategoriesWithNews $it")
            categoriesSelectedList = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.menu_activity_main_actions,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_action_categories -> {
                val intent = Intent(this,CategoriesActivity::class.java);
                intent.putExtra(Constants.KEY_EXTRA_AVAILABLE_CATEGORIES,ArrayList(categoriesList.map {
                    it.category
                }))
                intent.putExtra(Constants.KEY_EXTRA_SELECTED_CATEGORIES,ArrayList(categoriesSelectedList.map {
                    it.newsCategoryModel.category
                }))
                startActivityForResult(intent,REQUEST_CODE)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                val categoriesSelected = it.getStringArrayListExtra(Constants.KEY_EXTRA_SELECTED_CATEGORIES)
                Log.d(TAG,"Categories selected ${categoriesSelected}")
            }

        }
    }
}