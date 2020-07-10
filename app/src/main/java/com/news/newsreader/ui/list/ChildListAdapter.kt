package com.news.newsreader.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.news.newsreader.R
import com.news.newsreader.model.AdapterDataItem
import kotlinx.android.synthetic.main.fragment_news_item_list.view.*

/**
 * Adapter for displaying items on each row of list
 */
class ChildListAdapter(
    val list : List<AdapterDataItem>
): RecyclerView.Adapter<ChildListAdapter.ChildViewHolder>() {

    class ChildViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val root : View = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_news_item_list,parent,false)
        return ChildViewHolder(view);
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val item = list.get(position)
        holder.root.apply {
            feed_item_heading_tv.setText(item.title)
            feed_item_description_tv.setText(item.description)
        }
    }
}