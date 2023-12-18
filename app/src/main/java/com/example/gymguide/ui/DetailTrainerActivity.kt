package com.example.gymguide.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymguide.R
import com.example.gymguide.data.Article
import com.example.gymguide.data.TrainerDataSource
import com.example.gymguide.data.Trainer
import com.example.gymguide.databinding.ActivityDetailTrainerBinding

class DetailTrainerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTrainerBinding
    private lateinit var articleAdapter: ArticleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTrainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        articleAdapter.articles = TrainerDataSource.articles
    }

    private fun setupRecyclerView() = binding.rvArticle.apply {
        articleAdapter = ArticleAdapter()
        articleAdapter.setClickListener(object : ArticleAdapter.ClickListener {
            override fun onItemClicked(article: Article) {
                val intent = Intent(context, ArticleActivity::class.java)
                // TODO: Pass data through intent
//                intent.putExtra("name", article.name)
                startActivity(intent)
            }
        })
        adapter = articleAdapter
        layoutManager = LinearLayoutManager(context)
    }
}