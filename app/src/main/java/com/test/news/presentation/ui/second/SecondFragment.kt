package com.test.news.presentation.ui.second

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import com.test.news.R
import com.test.news.databinding.FragmentSecondBinding
import com.test.news.domain.model.News
import com.test.news.presentation.ui.adapter.NewsAdapter
import com.test.news.presentation.ui.adapter.OnNewsListItemViewClickListener
import com.test.news.presentation.ui.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SecondFragment : BaseBindingFragment<FragmentSecondBinding>(),
    OnNewsListItemViewClickListener {

    override val layoutId: Int = R.layout.fragment_second
    override val viewModel: SecondViewModel by viewModel()

    private var adapter: NewsAdapter? = null

    @SuppressLint("NotifyDataSetChanged")
    private val observerAllFavoriteNews = Observer<List<News.NewsItem>> {
        it?.let {
            viewModel.newsFavoriteList.clear()
            viewModel.newsFavoriteList.addAll(it)
            adapter?.filter?.filter(binding.etSearch.text.toString())
            adapter?.notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    override fun onClickOpenUrl(url: String?) {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        })
    }

    override fun onClickAdd(item: News.NewsItem) {}

    override fun onClickRemove(item: News.NewsItem) {
        viewModel.deleteNewsData.value = item
    }

    private fun initView() {
        adapter = NewsAdapter(viewModel.newsFavoriteList, this)
        binding.etSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                adapter?.filter?.filter(text.toString())
            }
        })
        binding.recyclerView.adapter = adapter
    }

    private fun initObserver() {
        viewModel.deleteNews.reObserve(viewLifecycleOwner, {
            it.let {
                viewModel.getAllFavoriteNewsData.value = 0
            }
        })
        viewModel.getAllFavoriteNews.reObserve(viewLifecycleOwner, observerAllFavoriteNews)
        viewModel.getAllFavoriteNewsData.value = 0
    }
}