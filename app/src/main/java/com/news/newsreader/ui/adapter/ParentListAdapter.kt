package com.news.newsreader.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.newsreader.R
import com.news.newsreader.model.db.models.CategoryWithNews
import kotlinx.android.synthetic.main.fragment_item_category.view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*

/**
 * Adapter that adds a [ChildListAdapter] to each row
 * Allows for 1+ items per row for horizontal scrolling
 * Will also handle section title views, I.E. for categories/channels
 */
class ParentListAdapter(
    categoryWithNews: List<CategoryWithNews>,
    val listener: ParentListAdapter.AdapterListener
//TODO Add trending list
) : RecyclerView.Adapter<ParentListAdapter.ParentViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()
    val TAG = ParentListAdapter::class.simpleName

    val adapterList: List<AdapterItems>
    val adapterMap: MutableMap<AdapterKey, List<AdapterDataItem>> = mutableMapOf()

    /**
     * AdapterItemType(Trending,ListOf(TrendingObjects))
     * AdapterItemType(POSTCARD,ListOf(CategoryWithNews))
     */
    inner class AdapterKey(
        val itemViewType: ItemViewType,
        val category: String,
        var isHorizontal: Boolean = true
    ) {
        override fun equals(other: Any?): Boolean {
            if (other is AdapterKey) {
                if (itemViewType == other.itemViewType && category == other.category)
                    return true
            }
            return false;
        }

        override fun hashCode(): Int {
            return itemViewType.ordinal + category.hashCode()
        }
    }

    inner class AdapterItems(
        val itemViewType: ItemViewType,
        val items: List<AdapterDataItem>
    )

    enum class ItemViewType {
        TRENDING,
        POSTCARD,
        SECTION_TITLE
    }

    interface AdapterListener {
        fun itemSelected(position: Int)
    }

    init {
        //Create a map with key being a ItemViewType combined with Category section title
        //and value being a list of data items used by adapter to bind to views
        categoryWithNews.forEach { cat ->
            val listAdapterDataItems = cat.newsModel.map {
                AdapterDataItemImpl(
                    it.link,
                    it.imageUrl,
                    it.title,
                    it.description,
                    cat.newsCategoryModel.category,
                    cat.newsCategoryModel.category
                )
            }
            val key = AdapterKey(ItemViewType.POSTCARD, cat.newsCategoryModel.category)
            adapterMap.put(key, listAdapterDataItems)
        }
        //Flattening the map into a useable list for adapters
        val list = mutableListOf<AdapterItems>()
        adapterMap.forEach { mapEntry ->
            //If vertical add 1 element per row
            if (!mapEntry.key.isHorizontal) {
                for (adapterItem in mapEntry.value) {
                    list.add(AdapterItems(mapEntry.key.itemViewType, listOf(adapterItem)))
                }
            } else { //Horizontal so just add list
                list.add(
                    AdapterItems(
                        mapEntry.key.itemViewType,
                        mapEntry.value
                    )
                )
            }

        }

        //Add section title view types
        val mutableList = list.toMutableList()
        val mutableIter = mutableList.listIterator()
        while (mutableIter.hasNext()) {
            val item = mutableIter.next()
            if (item.itemViewType == ItemViewType.POSTCARD) {
                mutableIter.previous()
                mutableIter.add(
                    AdapterItems(
                        ItemViewType.SECTION_TITLE,
                        item.items
                    )
                )
                mutableIter.next()
            }
        }
        adapterList = mutableList.toList()

    }


    override fun getItemViewType(position: Int): Int {
        return adapterList.get(position).itemViewType.ordinal
    }

    inner class ParentViewHolder(view: View, adapterListener: AdapterListener) :
        RecyclerView.ViewHolder(view) {
        val recyclerView = view.child_recycler
        val categoryTextView = view.item_category

        init {
            if (categoryTextView != null) {
                view.setOnClickListener {
                    Log.d(TAG, "Clicked on ${categoryTextView.text} position ${adapterPosition}")
                    adapterListener.itemSelected(adapterPosition)
                }
            }
        }

        fun onBind(adapterDataItem: AdapterDataItem) {
            if (categoryTextView != null) {
                categoryTextView.text = adapterDataItem.categoryTitle
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = when (viewType) {
            ItemViewType.SECTION_TITLE.ordinal -> {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_item_category, parent, false)
            }
            ItemViewType.POSTCARD.ordinal -> {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_recycler_view, parent, false)
            }
            else -> View(parent.context)
        }
        return ParentViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {

        val childAdapter = ChildListAdapter(adapterList.get(position).items)
        when (getItemViewType(position)) {
            ItemViewType.POSTCARD.ordinal -> {
                holder.recyclerView.apply {
                    setRecycledViewPool(viewPool)
                    adapter = childAdapter
                    this.layoutManager = LinearLayoutManager(
                        holder.recyclerView.context, RecyclerView.HORIZONTAL, false
                    )
                }
            }
            ItemViewType.SECTION_TITLE.ordinal -> {
                if (adapterList.get(position).items.isNotEmpty())
                    holder.onBind(adapterList.get(position).items.first())
            }
        }


    }
}