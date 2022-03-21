package com.test.news.presentation.ui.first

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.news.R
import com.test.news.data.net.Resource
import com.test.news.data.net.Status
import com.test.news.databinding.FragmentFirstBinding
import com.test.news.domain.model.News
import com.test.news.presentation.ui.adapter.NewsAdapter
import com.test.news.presentation.ui.adapter.OnNewsListItemViewClickListener
import com.test.news.presentation.ui.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FirstFragment : BaseBindingFragment<FragmentFirstBinding>(), OnNewsListItemViewClickListener {

    override val layoutId: Int = R.layout.fragment_first
    override val viewModel: FirstViewModel by viewModel()

    private val timer = object : CountDownTimer(180000, 180000) {

        override fun onTick(p0: Long) {}

        override fun onFinish() {
            viewModel.offSetData.value = null
        }
    }

    private val observerAllFavoriteNews = Observer<List<News.NewsItem>> {
        it?.let {
            viewModel.newsFavoriteList.clear()
            viewModel.newsFavoriteList.addAll(it)
            viewModel.offSetData.value = null
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private val observerOffSet = Observer<Resource<News>> {
        when (it.status) {
            Status.SUCCESS -> {
                setNewsAndIsFavorite(it.data)
                viewModel.newsList.clear()
                viewModel.newsList.addAll(it.data?.news ?: emptyList())
                binding.recyclerView.adapter?.notifyDataSetChanged()
                resetTimer()
            }
            Status.ERROR -> showLoading(false)
            Status.LOADING -> showLoading()
        }
    }

    private val observerOffSetNext = Observer<Resource<News>> {
        when (it.status) {
            Status.SUCCESS -> {
                setNewsAndIsFavorite(it.data)
                val size = viewModel.newsList.size
                viewModel.newsList.addAll(it.data?.news ?: emptyList())
                val sizeNew = viewModel.newsList.size
                binding.recyclerView.adapter?.notifyItemRangeChanged(size, sizeNew)
                resetTimer()
            }
            Status.ERROR -> showLoading(false)
            Status.LOADING -> showLoading()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObserver()
        initRefresh()
    }

    override fun onClickOpenUrl(url: String?) {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        })
    }

    override fun onClickAdd(item: News.NewsItem) {
        viewModel.insertPosition.set(viewModel.newsList.indexOf(item))
        viewModel.insertNewsData.value = item
    }

    override fun onClickRemove(item: News.NewsItem) {
        viewModel.deletePosition.set(viewModel.newsList.indexOf(item))
        viewModel.deleteNewsData.value = item
    }

    private fun setNewsAndIsFavorite(news: News?) {
        showLoading(false)
        viewModel.news.set(news)
        if (viewModel.newsFavoriteList.isNotEmpty()) {
            viewModel.newsFavoriteList.forEach { itemFavorite ->
                news?.news?.find { itemRemote ->
                    itemFavorite.url == itemRemote.url
                }?.isFavorite = true
            }
        }
    }

    private fun resetTimer() {
        timer.cancel()
        timer.start()
    }

    private fun showLoading(visible: Boolean = true) {
        if (visible) {
            if (!viewModel.visibleIsRefreshing.get()) {
                viewModel.visibleProgressBar.set(visible)
            }
        } else {
            viewModel.visibleProgressBar.set(visible)
            viewModel.visibleIsRefreshing.set(visible)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = NewsAdapter(viewModel.newsList, this@FirstFragment)
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    if (!viewModel.visibleProgressBar.get()) {
                        if ((layoutManager.childCount + layoutManager.findFirstVisibleItemPosition()) >= layoutManager.itemCount) {
                            viewModel.offSetNextData.value = viewModel.news.get()?.offset
                        }
                    }
                }
            }
        })
    }

    private fun initObserver() {
        viewModel.getAllFavoriteNews.reObserve(viewLifecycleOwner, observerAllFavoriteNews)
        viewModel.offSet.reObserve(viewLifecycleOwner, observerOffSet)
        viewModel.offSetNext.reObserve(viewLifecycleOwner, observerOffSetNext)
        viewModel.insertNews.reObserve(viewLifecycleOwner, {
            it.let {
                viewModel.newsList[viewModel.insertPosition.get()].isFavorite = true
                binding.recyclerView.adapter?.notifyItemChanged(viewModel.insertPosition.get())
            }
        })
        viewModel.deleteNews.reObserve(viewLifecycleOwner, {
            it.let {
                if (it == 1) {
                    viewModel.newsList[viewModel.deletePosition.get()].isFavorite = false
                    binding.recyclerView.adapter?.notifyItemChanged(viewModel.deletePosition.get())
                }
            }
        })
        viewModel.getAllFavoriteNewsData.value = 0
    }

    private fun initRefresh() {
        binding.srlUpdate.setOnRefreshListener {
            viewModel.visibleIsRefreshing.set(true)
            viewModel.news.set(null)
            viewModel.getAllFavoriteNewsData.value = 0
        }
    }
}