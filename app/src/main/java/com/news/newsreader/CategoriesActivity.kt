package com.news.newsreader

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.CheckBox
import com.news.newsreader.ui.NewsViewModel
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.activity_category_item.*
import kotlinx.android.synthetic.main.activity_category_item.view.*
import kotlinx.android.synthetic.main.activity_category_item.view.category_checkbox

class CategoriesActivity : AppCompatActivity() {

    lateinit var viewModel : NewsViewModel
    lateinit var selectedCategories : Set<String>
    val categoryCheckboxes : MutableList<CheckBox> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        categories_holder.removeAllViews()
        setSupportActionBar(my_toolbar)
//        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
//        viewModel.categories.observe(this, Observer {
//            it.forEach{
//                addCategory(it)
//            }
//        })
        selectedCategories = intent.getStringArrayListExtra(Constants.KEY_EXTRA_SELECTED_CATEGORIES).toSet()
        val availableCategories = intent.getStringArrayListExtra(Constants.KEY_EXTRA_AVAILABLE_CATEGORIES)
        availableCategories.forEach {
            addCategory(it)
        }
    }

    private fun addCategory(category: String) {
        val categoryItem = LayoutInflater.from(this).inflate(R.layout.activity_category_item,
            categories_holder,false)
        categoryItem.category_title.text = category
        categoryItem.category_checkbox.isChecked = if (selectedCategories.contains(category)) true else false
        categoryItem.category_checkbox.tag = category
        categoryCheckboxes.add(categoryItem.category_checkbox)
        categories_holder.addView(categoryItem)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.menu_activity_categories_actions,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsMenuClosed(menu: Menu?) {
        super.onOptionsMenuClosed(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_action_ok -> {
                val list = categoryCheckboxes.map {
                    if (it.isChecked) it.tag as String else null
                }
                val nonNullList = list.filterNotNull()
                val result = Intent()
                result.putStringArrayListExtra(Constants.KEY_EXTRA_SELECTED_CATEGORIES,
                    ArrayList(nonNullList))

                setResult(Activity.RESULT_OK,result)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}