package com.test.news.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.news.R
import com.test.news.databinding.NewsItemBinding
import com.test.news.domain.model.News
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(
    private val newsList: ArrayList<News.NewsItem>,
    private val onClickListener: OnNewsListItemViewClickListener? = null
) : RecyclerView.Adapter<ViewHolderNews>(), Filterable {

    var newsFilterList = ArrayList<News.NewsItem>()

    init {
        newsFilterList = newsList
    }

    override fun getItemCount(): Int = newsFilterList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderNews =
        ViewHolderNews(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.news_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolderNews, position: Int) =
        holder.bind(newsFilterList[position], onClickListener)

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                newsFilterList = if (charSearch.isEmpty()) {
                    newsList
                } else {
                    val resultList = ArrayList<News.NewsItem>()
                    for (row in newsList) {
                        if (row.plainTitle?.lowercase(Locale.ROOT)?.contains(charSearch.lowercase(Locale.ROOT)) == true ||
                            row.plainSubtitle?.lowercase(Locale.ROOT)?.contains(charSearch.lowercase(Locale.ROOT)) == true
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = newsFilterList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                newsFilterList = results?.values as ArrayList<News.NewsItem>
                notifyDataSetChanged()
            }
        }
    }
}

class ViewHolderNews(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: News.NewsItem, onClickListener: OnNewsListItemViewClickListener?) {
        binding.item = item
        binding.llNewsItem.setOnClickListener {
            onClickListener?.onClickOpenUrl(item.url)
        }
        binding.buttonAddOrRemove.setOnClickListener {
            if (item.isFavorite) {
                onClickListener?.onClickRemove(item)
            } else {
                onClickListener?.onClickAdd(item)
            }
        }
        binding.executePendingBindings()
    }
}

interface OnNewsListItemViewClickListener {

    fun onClickOpenUrl(url: String?)

    fun onClickAdd(item: News.NewsItem)

    fun onClickRemove(item: News.NewsItem)
}