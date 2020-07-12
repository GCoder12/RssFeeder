package com.news.newsreader.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.newsreader.R
import com.news.newsreader.model.db.models.CategoryWithNews
import com.news.newsreader.model.db.models.NewsCategoryModel
import kotlinx.android.synthetic.main.layout_recycler_view.view.*

/**
 * Adapter that adds a [ChildListAdapter] to each row
 * Allows for 1+ items per row for horizontal scrolling
 * Will also handle section title views, I.E. for categories/channels
 */
class ParentListAdapter(
    val categoryWithNews : List<CategoryWithNews>
) : RecyclerView.Adapter<ParentListAdapter.ParentViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    class ParentViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val recyclerView = view.child_recycler;
        fun onBind() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_view,parent,false)
        return ParentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryWithNews.size
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {

        val childAdapter = ChildListAdapter(categoryWithNews.get(position).newsModel)
        holder.recyclerView.apply {
            setRecycledViewPool(viewPool)
            this.adapter = childAdapter
            this.layoutManager = LinearLayoutManager(
                holder.recyclerView.context,LinearLayoutManager.HORIZONTAL,false)
        }
    }
}