package com.jxareas.techhub.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jxareas.techhub.R

class SearchCoursesFragment : Fragment() {

    companion object {
        fun newInstance() = SearchCoursesFragment()
    }

    private lateinit var viewModel: SearchCoursesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_courses, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchCoursesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}