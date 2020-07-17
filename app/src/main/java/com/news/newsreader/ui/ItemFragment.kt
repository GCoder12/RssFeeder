package com.news.newsreader.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.news.newsreader.CategoriesActivity
import com.news.newsreader.Constants
import com.news.newsreader.NewsReaderApplication
import com.news.newsreader.R
import com.news.newsreader.model.db.models.CategoryWithNews
import com.news.newsreader.model.db.models.NewsCategoryModel
import com.news.newsreader.ui.adapter.ParentListAdapter

/**
 * A fragment representing a list of Items.
 */
class ItemFragment : Fragment() {

    private val REQUEST_CODE = 1

    lateinit var newsViewModel  : NewsViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter : ParentListAdapter
    lateinit var categoriesList : List<NewsCategoryModel>
    lateinit var categoriesSelectedList : List<NewsCategoryModel>

    val TAG = ItemFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        val appData = (activity?.application as NewsReaderApplication).AppContainer
        appData?.let {
            newsViewModel = ViewModelProvider(this,it.viewModelFactory).get(NewsViewModel::class.java)
//            newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
            newsViewModel.items.observe(viewLifecycleOwner, Observer {
                Log.d(TAG,"Setting list ${it}")
                setList(it)
            })
            newsViewModel.categories.observe(viewLifecycleOwner, Observer {
                Log.d(TAG,"Received categories ${it}")
                categoriesList = it
                categoriesSelectedList = it.filter {it.isDisplayed==true}

            })

            if (view is RecyclerView) {
                recyclerView = view
            }
            newsViewModel.fetchNewsItems()
            newsViewModel.getAllCategories()
            return view
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_action_categories -> {
                val intent = Intent(activity, CategoriesActivity::class.java);
                intent.putExtra(Constants.KEY_EXTRA_AVAILABLE_CATEGORIES,ArrayList(categoriesList.map {
                    it.category
                }))
                intent.putExtra(Constants.KEY_EXTRA_SELECTED_CATEGORIES,ArrayList(categoriesSelectedList.map {
                    it.category
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
                newsViewModel.updateDisplayedNews(categoriesSelected)
                Log.d(TAG,"Categories selected ${categoriesSelected}")
            }

        }
    }


    private val adapterListener = object : ParentListAdapter.AdapterListener {
        override fun itemSelected(position: Int) {
            Log.d(TAG,"Position $position selected")
            recyclerAdapter.changeCategoryOrientation(position)
            recyclerAdapter.notifyDataSetChanged()

        }

    }

    private fun setList(list : List<CategoryWithNews>) {

        // Set the adapter
        with(recyclerView) {
            layoutManager =  LinearLayoutManager(context)
            recyclerAdapter = ParentListAdapter(list,adapterListener)

            adapter = recyclerAdapter
        }

    }

}