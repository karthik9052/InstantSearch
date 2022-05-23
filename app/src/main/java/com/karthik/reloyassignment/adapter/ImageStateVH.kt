package com.karthik.reloyassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.karthik.reloyassignment.databinding.ImagelistLoadStateBinding

class ImageStateVH(
    private val binding: ImagelistLoadStateBinding, private val retry: () -> Unit
): RecyclerView.ViewHolder(binding.root){

    fun bind(loadState: LoadState) {
        binding.loadStateRetry.isVisible = loadState !is LoadState.Loading
        binding.loadStateErrorMessage.isVisible = loadState !is LoadState.Loading
        binding.loadStateProgress.isVisible = loadState is LoadState.Loading

        binding.loadStateRetry.setOnClickListener {
            retry.invoke()
        }
    }

    companion object {

        fun onCreateViewHolder(parent: ViewGroup,retry: () -> Unit): RecyclerView.ViewHolder {
            val binding = ImagelistLoadStateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ImageStateVH(binding = binding,retry)
        }
    }

}