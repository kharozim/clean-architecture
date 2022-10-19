package com.ozimos.sample.ui.picsum

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import com.ozimos.sample.data.domain.PicsumDomain
import com.ozimos.sample.databinding.ActivityPicsumBinding
import com.ozimos.sample.ui.util.StateUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PicsumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPicsumBinding

    private val viewModel: PicsumViewModel by lazy { DI.provideViewModel() }
    private val adapter by lazy { PicsumAdapter(listPicture) }
    private val listPicture = ArrayList<Coba>()
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPicsumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        setObserver()
        setData()

    }

    private fun setData() {
        binding.run {
            rvPicsum.adapter = adapter
            swipeRefresh.setOnRefreshListener {
                getData()
            }
        }
    }

    private fun setObserver() {
        viewModel.listPicsum.observe(this) {
            when (it) {
                is StateUtil.Loading -> {
                    isLoading = true
                    binding.swipeRefresh.isRefreshing = false
                    binding.appendProgress.isInvisible = false
                    adapter.setLoading(true)

                }
                is StateUtil.Failed -> {
                    isLoading = false
                    binding.swipeRefresh.isRefreshing = false
                    binding.appendProgress.isInvisible = true
                    adapter.setLoading(false)
                    adapter.setError(it.message)
                }
                is StateUtil.Success -> {
                    adapter.setLoading(false)
                    isLoading = false
                    binding.swipeRefresh.isRefreshing = false
                    binding.appendProgress.isInvisible = true
                    showData(it.data)
                }
            }
        }
    }

    private fun showData(data: List<PicsumDomain>) {
        val tempSize = listPicture.size
        data.map { listPicture.add(Coba.Data(it)) }
        adapter.notifyItemRangeInserted(tempSize, listPicture.size)


        adapter.onclick = {
            if (it != null) {
                Toast.makeText(this, it.author, Toast.LENGTH_SHORT).show()
            } else {
                if (adapter.itemCount == 0)
                    getData()
                else{
                    viewModel.loadMorePicture()
                }
            }
        }
        adapter.onLastItem = {
            Log.e("TAG", "showData: onlast item")
            if (!isLoading) {
                viewModel.loadMorePicture()
            }
        }
    }

    private fun getData() {
        val tempSize = listPicture.size
        listPicture.clear()
        adapter.notifyItemRangeRemoved(0, tempSize)
        viewModel.initPicture()
    }
}