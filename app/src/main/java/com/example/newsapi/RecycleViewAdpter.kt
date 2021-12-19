package com.example.newsapi

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatDrawableManager.get
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi.databinding.CardCellBinding
import com.squareup.picasso.Picasso
import java.lang.reflect.Array.get
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class RecycleViewAdpter(var listNews : List<News> , var context : Context) : RecyclerView.Adapter<RecycleViewAdpter.ItemHolder>() {
    class ItemHolder(val binding: CardCellBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(CardCellBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var list = listNews
        holder.binding.apply {
            Picasso.with(context)
                .load(list[position].image)
                .into(imvImage)
            tvTitle.text = list[position].title
            tvDate.text = list[position].date
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context , DetailedInfo :: class.java)
            intent.putExtra("DetNew", list[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listNews.size

    fun update(newList : List<News>){
        listNews = newList
        notifyDataSetChanged()
    }
    

}