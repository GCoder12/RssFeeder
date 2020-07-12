package com.news.newsreader.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.news.newsreader.R
import com.news.newsreader.StrUtil
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_news_item_list.view.*
import java.lang.Exception

/**
 * Adapter for displaying items on each row of list
 */
class ChildListAdapter(
    val list: List<AdapterDataItem>
) : RecyclerView.Adapter<ChildListAdapter.ChildViewHolder>() {

    val TAG = ChildListAdapter::class.simpleName

    class ChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root: View = view

        fun bind(item: AdapterDataItem) {
            if (StrUtil.notEmpty(item.imageUrl))
                Picasso.get().load(item.imageUrl)
                    .noPlaceholder()
                    .into(root.feed_item_avatar, object : Callback {
                        override fun onSuccess() {
                            root.feed_item_avatar_placeholder.visibility = View.GONE
                        }

                        override fun onError(e: Exception?) {
                        }

                    })

            root.apply {
                feed_item_heading_tv.setText(item.title)
                feed_item_description_tv.setText(item.description)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_news_item_list, parent, false)
        return ChildViewHolder(view);
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val item = list.get(position)
        Log.d(TAG, "Item link url is ${item.imageUrl}")
        holder.bind(item)
    }
}