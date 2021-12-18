package com.example.newsapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsapi.databinding.ActivityDetailedInfoBinding
import com.squareup.picasso.Picasso

class DetailedInfo : AppCompatActivity() {
    lateinit var binding: ActivityDetailedInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val new = intent.getSerializableExtra("DetNew") as? News

        binding.tvTitle.text = new?.title
        binding.tvDate.text = new?.date
        binding.tvDec.text = new?.description
        binding.tvFullNew.text = "The scourse of new ${new?.link}"
        Picasso.with(this)
            .load(new?.image)
            .into(binding.imageView)

    }
}