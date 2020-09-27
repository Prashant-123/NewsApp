package com.newsapp.ui.main.headlines

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
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
        setToolbar()
        exitTransition = MaterialElevationScale(true)
        binding = FragmentHeadlinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setViewModelObservers()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = HeadlinesAdapter { news, imageView ->
            val extras = FragmentNavigatorExtras(
                imageView to "imageView",
            )
            findNavController().navigate(
                R.id.action_headlinesFragment_to_newsDetailFragment,
                bundleOf("id" to news.id), null, extras)
        }

        binding.swipeRefreshLayout.setOnRefreshListener { setViewModelObservers() }

        binding.recyclerView.adapter = adapter
    }

    private fun setViewModelObservers() {
        viewModel.headlines.observe(viewLifecycleOwner, {
            when (it.status) {
                SUCCESS -> {
                    it.data?.let { adapter.addNews(it) }
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                ERROR -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                }

                LOADING -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }

    private fun setToolbar() {
        val window: Window = requireActivity().window
        window.statusBarColor = Color.BLACK
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}