package com.example.gymguide.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gymguide.data.Article
import com.example.gymguide.data.TrainerDataSource
import com.example.gymguide.databinding.ItemArticleBinding
import com.example.gymguide.databinding.ItemExerciseBinding
import com.example.gymguide.databinding.ItemExerciseRecommendationBinding
import com.example.gymguide.databinding.ItemTrainerBinding


class ArticleAdapter :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var clickListener: ClickListener? = null

    inner class ArticleViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var articles: List<Article>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun getItemCount() = articles.size

    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.binding.apply {
            tvArticleName.text = article.name
            tvArticleDesc.text = article.description
            Glide.with(root.context)
                .load(article.pictureUrl)
                .centerCrop()
                .into(ivArticle)
        }
        holder.itemView.setOnClickListener { clickListener?.onItemClicked(article) }
    }
    interface ClickListener {
        fun onItemClicked(article: Article)
    }
}

