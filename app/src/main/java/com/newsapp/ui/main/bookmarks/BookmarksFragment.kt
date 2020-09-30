package com.newsapp.ui.main.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsapp.R
import com.newsapp.databinding.FragmentBookmarksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksFragment : Fragment() {

    private lateinit var binding: FragmentBookmarksBinding
    private val viewModel: BookmarksViewModel by viewModels()
    private lateinit var adapter: BookmarksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarksBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setViewModelObservers()
    }


    private fun setupRecyclerView() {
        binding.recyclerViewBookmarks.layoutManager = LinearLayoutManager(context)
        adapter = BookmarksAdapter(viewModel) { news, imageView ->
            findNavController().navigate(
                R.id.action_viewPagerFragment_to_newsDetailFragment,
                bundleOf("id" to news.id), null, null
            )
        }

        binding.recyclerViewBookmarks.adapter = adapter
    }

    private fun setViewModelObservers() {
        viewModel.bookmarks.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()) {
                binding.recyclerViewBookmarks.visibility = View.GONE

                binding.noResultLottie.visibility = View.VISIBLE
                binding.tvNoResult.visibility = View.VISIBLE
            } else {
                binding.recyclerViewBookmarks.visibility = View.VISIBLE

                binding.noResultLottie.visibility = View.GONE
                binding.tvNoResult.visibility = View.GONE
            }

            it?.let { adapter.addNews(it) }
        })
    }
}