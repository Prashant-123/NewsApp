package com.newsapp.ui.main.headlines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsapp.R
import com.newsapp.data.remote.Resource.Status.*
import com.newsapp.databinding.FragmentHeadlinesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeadlinesFragment : Fragment() {

    private lateinit var binding : FragmentHeadlinesBinding
    private val viewModel: HeadlinesViewModel by viewModels()
    private lateinit var adapter: HeadlinesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeadlinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setViewModelObservers()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewHeadlines.layoutManager = LinearLayoutManager(context)
        adapter = HeadlinesAdapter(viewModel) { news, imageView ->
            val extras = FragmentNavigatorExtras(
                imageView to "imageView",
            )
            findNavController().navigate(
                R.id.action_viewPagerFragment_to_newsDetailFragment,
                bundleOf("id" to news.id), null, extras
            )
        }

        binding.swipeRefreshLayout.setOnRefreshListener { setViewModelObservers() }

        binding.recyclerViewHeadlines.adapter = adapter
    }

    private fun setViewModelObservers() {
        viewModel.headlines.observe(viewLifecycleOwner, {
            when (it.status) {
                SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        adapter.addNews(it.data)
                        binding.progressbar.visibility = View.GONE
                    }

                    binding.swipeRefreshLayout.isRefreshing = false
                }
                ERROR -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.progressbar.visibility = View.GONE
                }

                LOADING -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.progressbar.visibility = View.VISIBLE
                }
            }
        })
    }
}