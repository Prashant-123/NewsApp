package com.newsapp.ui.main.bookmarks

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.data.entities.News
import com.newsapp.databinding.ItemHeadlineBinding
import com.newsapp.utils.Converters
import kotlinx.android.synthetic.main.item_headline.view.*
import timber.log.Timber

class BookmarksAdapter(
    private val viewModel: BookmarksViewModel,
    private val itemClickListener: (News, ImageView) -> Unit
) : RecyclerView.Adapter<BookmarksAdapter.MyViewHolder>() {

    private var itemList = ArrayList<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding : ItemHeadlineBinding = ItemHeadlineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = itemList[position]
        holder.bind(current)
        holder.itemView.setOnClickListener { itemClickListener(current, holder.itemView.imageView) }

        holder.itemView.bookmarkCheckbox.setOnClickListener(null)
        holder.itemView.bookmarkCheckbox.isChecked = current.isBookmarked

        holder.itemView.bookmarkCheckbox.setOnClickListener {
            current.isBookmarked = holder.itemView.bookmarkCheckbox.isChecked

            if (current.isBookmarked) {
                viewModel.bookmarkNews(current.id!!, 1)
            } else {
                viewModel.bookmarkNews(current.id!!, 0)
            }
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun addNews(news: List<News>) {
        itemList.clear()
        itemList.addAll(news)
        notifyDataSetChanged()
    }

    class MyViewHolder(private val itemHeadlineBinding: ItemHeadlineBinding) : RecyclerView.ViewHolder(
        itemHeadlineBinding.root
    ) {
        fun bind(news: News) {
            Timber.d(news.urlToImage)

            itemHeadlineBinding.tvHeading.text = news.title
            itemHeadlineBinding.tvSource.text = news.source.name
            itemHeadlineBinding.tvTimestamp.text = Converters.parseTimestamp(news.publishedAt!!)

            itemHeadlineBinding.bookmarkCheckbox.setOnCheckedChangeListener(null);
            itemHeadlineBinding.bookmarkCheckbox.isChecked = news.isBookmarked
            Glide.with(itemHeadlineBinding.root)
                .load(news.urlToImage)
                .placeholder(R.drawable.placeholder)
                .into(itemHeadlineBinding.imageView)
        }
    }
}