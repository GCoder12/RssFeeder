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
    val listener: AdapterListener
//TODO Add trending list
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()
    val TAG = ParentListAdapter::class.simpleName

    lateinit var adapterList: List<BaseRowItem>
    val adapterMap: MutableMap<AdapterKey, List<AdapterDataItem>> = mutableMapOf()

    /**
     * AdapterItemType(Trending,ListOf(TrendingObjects))
     * AdapterItemType(POSTCARD,ListOf(CategoryWithNews))
     */
    inner class AdapterKey(
        /**
         * ItemViewType corresponding to this maps values
         */
        val valueViewType: ItemViewType,
        val category: String,
        var isHorizontal: Boolean = true,
        var showSectionTitle : Boolean = true
    ) {
        override fun equals(other: Any?): Boolean {
            if (other is AdapterKey) {
                if (valueViewType == other.valueViewType && category == other.category)
                    return true
            }
            return false;
        }

        override fun hashCode(): Int {
            return valueViewType.ordinal + category.hashCode()
        }
    }

    /**
     * BaseClass
     */
    abstract inner class BaseRowItem(
        val itemViewType: ItemViewType
    )

    /**
     * Section title row with a category
     */
    inner class HeaderRowItem(
        itemViewType: ItemViewType,
        val sectionViewType: ItemViewType,
        val category: String
    ) : BaseRowItem(itemViewType)

    /**
     * Actual item row displaying a non section title ItemViewType
     */
    inner class RowItem(
        itemViewType: ItemViewType,
        /**
         * List of [AdapterDataItem] to be passed to child adapter
         */
        val rowItemOrItems: List<AdapterDataItem>
    ) : BaseRowItem(itemViewType)

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
        buildOrientationList()


    }

    fun buildOrientationList() {
        //Flattening the map into a useable list for adapters
        val list = mutableListOf<BaseRowItem>()

        adapterMap.forEach { mapEntry : Map.Entry<AdapterKey,List<AdapterDataItem>> ->
            if (mapEntry.key.valueViewType == ItemViewType.POSTCARD) {
                //Add a section title
                if (mapEntry.key.showSectionTitle) {
                    list.add(HeaderRowItem(
                        ItemViewType.SECTION_TITLE,
                        ItemViewType.POSTCARD,
                        mapEntry.key.category))
                }
            }
            //If vertical add 1 element per row
            if (!mapEntry.key.isHorizontal) {
                for (adapterItem in mapEntry.value) {
                    list.add(RowItem(mapEntry.key.valueViewType, listOf(adapterItem)))
                }
            } else { //Horizontal so just add list
                list.add(
                    RowItem(
                        mapEntry.key.valueViewType,
                        mapEntry.value
                    )
                )
            }

        }
        adapterList = list.toMutableList()
    }

    fun changeCategoryOrientation(position: Int) {
        val adapterItem = adapterList.get(position)
        //Get section title for this position, switch it's isHorizontal
        (adapterItem as HeaderRowItem)?.let {
            //Construct key to find key in adapterMap
            val key = getKeyForHeaderRowItem(adapterItem)?.let {
                it.isHorizontal = !it.isHorizontal
            }
        }

        buildOrientationList()
    }

    private fun getKeyForHeaderRowItem(headerRowItem: HeaderRowItem) : AdapterKey? {
        val aKey = AdapterKey(headerRowItem.sectionViewType,headerRowItem.category)
        for ((key, value) in adapterMap) {
            if (aKey == key)
                return key
        }
        return null
    }

    override fun getItemViewType(position: Int): Int {
        return adapterList.get(position).itemViewType.ordinal

    }

    inner class TitleViewHolder(view : View, adapterListener: AdapterListener) : RecyclerView.ViewHolder(view) {
        val categoryTextView = view.item_category
        val dropDownArrow = view.drop_down_arrow

        init {
            view.setOnClickListener {
                Log.d(TAG, "Clicked on ${categoryTextView.text} position ${adapterPosition}")
                adapterListener.itemSelected(adapterPosition)
            }
        }

        fun bind(headerRowItem: HeaderRowItem) {
            categoryTextView.text = headerRowItem.category
            val key = getKeyForHeaderRowItem(headerRowItem)
            key?.let {
                dropDownArrow.rotation = if (it.isHorizontal) -90f else 0f
            }

        }
    }


    inner class RowViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val recyclerView = view.child_recycler


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            ItemViewType.SECTION_TITLE.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_item_category, parent, false)
                TitleViewHolder(view,listener)
            }
            ItemViewType.POSTCARD.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_recycler_view, parent, false)
                RowViewHolder(view)
            }
            else -> RowViewHolder(View(parent.context))
        }
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val baseRowItem = adapterList.get(position)
        when (getItemViewType(position)) {

            ItemViewType.POSTCARD.ordinal -> {
                val rowItem = baseRowItem as RowItem
                val holder = holder as RowViewHolder
                val childAdapter = ChildListAdapter(rowItem.rowItemOrItems)
                holder.recyclerView.apply {
                    setRecycledViewPool(viewPool)
                    adapter = childAdapter
                    this.layoutManager = LinearLayoutManager(
                        holder.recyclerView.context, RecyclerView.HORIZONTAL, false
                    )
                }
            }
            ItemViewType.SECTION_TITLE.ordinal -> {
                val headerRowItem = baseRowItem as HeaderRowItem
                val holder = holder as TitleViewHolder
                holder.bind(headerRowItem)
            }
        }


    }
}