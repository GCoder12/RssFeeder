package com.news.newsreader.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.news.newsreader.DataHelper.Companion.getFifthTestItem
import com.news.newsreader.R
import com.news.newsreader.DataHelper.Companion.getFirstTestItem
import com.news.newsreader.DataHelper.Companion.getFourthTestItem
import com.news.newsreader.DataHelper.Companion.getSecondTestItem
import com.news.newsreader.DataHelper.Companion.getThirdTestItem
import com.news.newsreader.model.db.models.NewsModel
import com.news.newsreader.ui.adapter.ParentListAdapter

/**
 * A fragment representing a list of Items.
 */
class ItemFragment : Fragment() {

    lateinit var newsViewModel  : NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        val list = arrayListOf<List<NewsModel>>()
        for (i in 0..3) {
            val modelList : List<NewsModel> = when (i) {
                0 -> arrayListOf(getFirstTestItem())
                1 -> arrayListOf(getFirstTestItem(), getSecondTestItem(), getThirdTestItem())
                2 -> arrayListOf(getFirstTestItem(), getFifthTestItem(), getFourthTestItem())
                3 -> arrayListOf(getFifthTestItem(), getFourthTestItem(), getThirdTestItem(),
                    getSecondTestItem(), getFirstTestItem())
                else -> arrayListOf(NewsModel())
            }
            list.add(modelList)
        }


        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager =  LinearLayoutManager(context)
                adapter = ParentListAdapter(list)
            }
        }
        return view
    }

}