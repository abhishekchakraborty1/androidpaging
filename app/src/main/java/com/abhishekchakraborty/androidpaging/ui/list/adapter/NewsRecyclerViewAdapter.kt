package com.abhishekchakraborty.androidpaging.ui.list.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.abhishekchakraborty.androidpaging.model.ResultData
import com.abhishekchakraborty.androidpaging.ui.list.callback.OnItemClickListener
import com.abhishekchakraborty.androidpaging.ui.list.types.NewsViewHolder

class NewsRecyclerViewAdapter(
    private val listener: OnItemClickListener
) : PagingDataAdapter<ResultData, NewsViewHolder>(NEWS_COMPARATOR) {

    companion object {
        private val NEWS_COMPARATOR = object : DiffUtil.ItemCallback<ResultData>() {
            override fun areItemsTheSame(oldItem: ResultData, newItem: ResultData): Boolean =
                oldItem.source_id == newItem.source_id

            override fun areContentsTheSame(oldItem: ResultData, newItem: ResultData): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { news ->
            holder.bind(news)
        }
    }
}
