package com.newsapp.ui.main.newsDetail

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.newsapp.databinding.FragmentNewsDetailBinding
import com.newsapp.utils.Converters
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailBinding
    private val viewModel: NewsDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setToolbar()

        binding = FragmentNewsDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsId = arguments?.getInt("id", 0)!!

        viewModel.getNewsById(newsId).observe(viewLifecycleOwner, {
            binding.tvTitle.text = it.title
            binding.tvSource.text = it.source.name
            binding.tvTimestamp.text = Converters.parseTimestamp(it.publishedAt!!)
            binding.description.text = it.description
            Glide.with(requireContext()).load(it.urlToImage).into(binding.backgroundImage)
        })

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setToolbar() {
        val window: Window = requireActivity().window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        window.statusBarColor = Color.TRANSPARENT
    }
}