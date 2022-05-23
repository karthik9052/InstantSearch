package com.karthik.reloyassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karthik.reloyassignment.R
import com.karthik.reloyassignment.data.response.Hit
import com.karthik.reloyassignment.databinding.RecyclerviewSearchListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageListVH(
 private val binding: RecyclerviewSearchListBinding
): RecyclerView.ViewHolder(binding.root){

    fun bind(data: Hit, imageListHandler: ImageListHandler) {
        CoroutineScope(Dispatchers.Main).launch {
            Glide.with(binding.imageView.context)
                .load(data.previewURL)
                .error(R.drawable.ic_baseline_image_24)
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(binding.imageView)
        }
        binding.imageView.setOnClickListener {
            imageListHandler.onModelClicked(data)
        }
    }

    companion object {

        fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val binding = RecyclerviewSearchListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ImageListVH(binding = binding)
        }
    }

}