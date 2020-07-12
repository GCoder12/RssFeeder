package com.news.newsreader.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.news.newsreader.DataHelper.Companion.getFifthTestItem
import com.news.newsreader.R
import com.news.newsreader.DataHelper.Companion.getFirstTestItem
import com.news.newsreader.DataHelper.Companion.getFourthTestItem
import com.news.newsreader.DataHelper.Companion.getSecondTestItem
import com.news.newsreader.DataHelper.Companion.getThirdTestItem
import com.news.newsreader.model.AdapterDataItem
import com.news.newsreader.model.db.models.CategoryWithNews
import com.news.newsreader.model.db.models.NewsCategoryModel
import com.news.newsreader.model.db.models.NewsModel
import com.news.newsreader.ui.adapter.ParentListAdapter

/**
 * A fragment representing a list of Items.
 */
class ItemFragment : Fragment() {

    lateinit var newsViewModel  : NewsViewModel
    lateinit var recyclerView: RecyclerView

    val TAG = ItemFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        newsViewModel.items.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"Setting list ${it.flatMap { it.newsModel }.map { "${it.title}" }}")
            setList(it)
        })

        if (view is RecyclerView) {
            recyclerView = view
        }
        newsViewModel.fetchNewsItems()
        return view
    }

    private fun setList(list : List<CategoryWithNews>) {

        // Set the adapter
        with(recyclerView) {
            layoutManager =  LinearLayoutManager(context)
            adapter = ParentListAdapter(list)
        }
    }

}