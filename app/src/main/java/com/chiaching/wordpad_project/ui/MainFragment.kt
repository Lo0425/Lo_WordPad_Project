package com.chiaching.wordpad_project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.navArgs
import com.chiaching.wordpad_project.adapter.PagerAdapter
import com.chiaching.wordpad_project.databinding.FragmentMainBinding
import com.chiaching.wordpad_project.model.Word
import com.chiaching.wordpad_project.repository.WordRepository
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment: Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val newWordsFragment = NewWordFragment.getInstance()
    private val completedWordsFragment = CompletedWordFragment.getInstance()
    lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navArgs: DetailsFragmentArgs by navArgs()

        val adapter = PagerAdapter(
            listOf(newWordsFragment, completedWordsFragment ),
            requireActivity().supportFragmentManager,
            lifecycle
        )

//        val search = binding.svSearch.on




        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout,binding.viewPager){ tab, pos ->
            tab.text = when(pos){
                0 -> "New Word"
                1 -> "Completed"
                else -> "none"
            }
        }.attach()


        setFragmentResultListener("from_details") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                newWordsFragment.refresh("")
                completedWordsFragment.refresh("")
            }
        }

        setFragmentResultListener("from_done") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                newWordsFragment.refresh("")
                completedWordsFragment.refresh("")

            }
        }

        setFragmentResultListener("from_add_item") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                newWordsFragment.refresh("")
            }
        }


    }
}
