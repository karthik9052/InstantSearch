package com.karthik.reloyassignment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.karthik.reloyassignment.adapter.ImageListAdapter
import com.karthik.reloyassignment.adapter.ImageListHandler
import com.karthik.reloyassignment.adapter.ImageStateAdapter
import com.karthik.reloyassignment.data.APIService
import com.karthik.reloyassignment.data.response.Hit
import com.karthik.reloyassignment.databinding.ActivityMainBinding
import com.karthik.reloyassignment.viewmodel.MainActivityViewModel
import com.karthik.reloyassignment.viewmodel.MainViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var   viewModel: MainActivityViewModel

    private val recyclerViewAdapter: ImageListAdapter by lazy {
        ImageListAdapter(object : ImageListHandler{
            override fun onModelClicked(data: Hit) {
                val intent = Intent(this@MainActivity, ImageDetailScreen::class.java)
                intent.putExtra("ImageDetails", data)
                startActivity(intent)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initRecyclerView()
        initViewModel()
        setUpSearchStateFlow()

    }

    private fun initRecyclerView() {
        viewBinding.rvImageSearchList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
       val  imageStateAdapter = ImageStateAdapter { recyclerViewAdapter.retry() }
        viewBinding.rvImageSearchList.adapter = recyclerViewAdapter.withLoadStateFooter(imageStateAdapter)
    }

    private fun initViewModel(){
         viewModel  = ViewModelProvider(this@MainActivity,
            MainViewModelFactory(APIService.getApiService())).get(MainActivityViewModel::class.java)
    }

    private fun setUpSearchStateFlow() {
        lifecycleScope.launch {
            viewBinding.svImage.getQueryTextChangeStateFlow()
                .debounce(300)
                .filter { query ->
                    if (query.isEmpty()) {
                        viewBinding.tvNoImagesFound.isVisible = query.isEmpty()
                        return@filter false
                    } else {
                        return@filter true
                    }
                }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    viewModel.getListData(query)
                }
                .flowOn(Dispatchers.Default)
                .collectLatest {
                    recyclerViewAdapter.submitData(it)
                }
        }
    }

}