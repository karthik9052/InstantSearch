package com.karthik.reloyassignment.adapter


import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

class ImageStateAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<RecyclerView.ViewHolder>()  {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, loadState: LoadState) {
        (holder as ImageStateVH).bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): RecyclerView.ViewHolder {
        return  ImageStateVH.onCreateViewHolder(parent,retry)
    }

}