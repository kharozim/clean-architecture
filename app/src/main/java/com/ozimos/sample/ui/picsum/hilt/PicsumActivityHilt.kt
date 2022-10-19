package com.ozimos.sample.ui.picsum.hilt

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.ozimos.sample.data.domain.PicsumDomain
import com.ozimos.sample.databinding.ActivityPicsumBinding
import com.ozimos.sample.ui.picsum.paging3.PicsumAdapterPaging
import com.ozimos.sample.ui.util.StateLoadingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PicsumActivityHilt : AppCompatActivity() {

    private lateinit var binding: ActivityPicsumBinding

    private val viewModel : PicsumHiltVIewModel by viewModels()
    private val adapter by lazy { PicsumAdapterPaging() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPicsumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        setObserver()
        setView()
        setStateAdapter()

    }

    private fun setStateAdapter() {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                adapter.loadStateFlow.collect {
//                    Log.e("TAG", "setStateAdapter append: ${it.source.append}")
//                    binding.appendProgress.isInvisible = !(it.source.append is LoadState.Loading)
//                }
//            }
//        }

        adapter.addLoadStateListener {
            Log.e("TAG", "setStateAdapter append: ${it.append}")
            binding.appendProgress.isInvisible = it.source.refresh !is LoadState.Loading

            binding.swipeRefresh.isRefreshing = false

        }
    }

    private fun setView() {
        binding.run {
            rvPicsum.adapter =
                adapter.withLoadStateFooter(footer = StateLoadingAdapter { adapter.retry() })

            swipeRefresh.setOnRefreshListener {
                adapter.refresh()
            }

        }
    }

    private fun setObserver() {
        viewModel.listPicsumPagingData.observe(this) {
            showData(it)
        }
    }

    private fun showData(data: PagingData<PicsumDomain>) {

        lifecycleScope.launch {
            adapter.submitData(data)
        }
        adapter.onclick = {
            Toast.makeText(this, it.author, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getData() {
        viewModel.getListPicturePagingData()
    }
}