package com.newsapp.ui.main.viewpager

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialElevationScale
import com.newsapp.databinding.FragmentViewPagerBinding
import com.newsapp.ui.main.bookmarks.BookmarksFragment
import com.newsapp.ui.main.headlines.HeadlinesFragment

class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(true)
        handleOnBackPressed()
    }

    private fun handleOnBackPressed() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.viewPager.currentItem == 1) {
                    binding.viewPager.currentItem = 0
                } else {
                    isEnabled = false
                    activity?.onBackPressed()
                }
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setToolbar()

        binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)

        val fragmentList = arrayListOf(
            HeadlinesFragment(),
            BookmarksFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter

        return binding.root
    }

    private fun setToolbar() {
        val window: Window = requireActivity().window
        window.statusBarColor = Color.BLACK
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}