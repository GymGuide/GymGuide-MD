package com.example.gymguide.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.gymguide.data.Article
import com.example.gymguide.data.TrainerDataSource
import com.example.gymguide.databinding.ActivityDetailTrainerBinding

class DetailTrainerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTrainerBinding
    private lateinit var articleAdapter: ArticleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTrainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        val name: String? = extras?.getString("name")
        val location: String? = extras?.getString("location")
        val picture: String? = extras?.getString("picture")
        val rating: String? = extras?.getString("rating")
        val description: String? = extras?.getString("description")

        binding.tvTrainerName.text = name
        binding.tvTrainerLocation.text = location
        Glide.with(this)
            .load(picture)
            .centerCrop()
            .into(binding.ivTrainerImage)
        binding.tvTrainerDescription.text = description

        setupRecyclerView()

        binding.buttonAppointment.setOnClickListener {
            val intent = Intent(this, BookTrainerActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("location",location)
            intent.putExtra("picture",picture)
            intent.putExtra("rating",rating)
            intent.putExtra("description",description)
            startActivity(intent)
        }

        binding.buttonChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("location",location)
            intent.putExtra("picture",picture)
            intent.putExtra("rating",rating)
            intent.putExtra("description",description)
            startActivity(intent)
        }

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