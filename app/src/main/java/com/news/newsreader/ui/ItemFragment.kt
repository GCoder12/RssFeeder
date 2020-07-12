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
import com.news.newsreader.R
import com.news.newsreader.model.db.models.CategoryWithNews
import com.news.newsreader.ui.adapter.ParentListAdapter

/**
 * A fragment representing a list of Items.
 */
class ItemFragment : Fragment() {

    lateinit var newsViewModel  : NewsViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter : ParentListAdapter

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
            Log.d(TAG,"Setting list ${it}")
            setList(it)
        })
        newsViewModel.categories.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"Received categories ${it}")

        })

        if (view is RecyclerView) {
            recyclerView = view
        }
        newsViewModel.fetchNewsItems()
        return view
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