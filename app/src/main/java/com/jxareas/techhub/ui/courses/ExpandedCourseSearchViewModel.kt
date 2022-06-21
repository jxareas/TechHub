package com.jxareas.techhub.ui.courses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.data.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpandedCourseSearchViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
) : ViewModel() {

    private val _courses = MutableLiveData<List<CachedCourse>>()
    internal var courses: LiveData<List<CachedCourse>> = _courses

    init {
        getAllRecentlyAccessedCourses()
    }

    fun getAllRecentlyAccessedCourses() {
        viewModelScope.launch {
            courseRepository.getRecentCourses().collectLatest { recentCourses ->
                _courses.postValue(recentCourses)
            }
        }
    }

}