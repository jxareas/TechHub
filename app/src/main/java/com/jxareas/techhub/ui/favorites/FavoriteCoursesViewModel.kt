package com.jxareas.techhub.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.data.repository.impl.CourseRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteCoursesViewModel @Inject constructor(
    private val coursesRepository: CourseRepositoryImpl
) : ViewModel() {

    private val _courses = MutableLiveData<List<CachedCourse>>()
    internal val courses: LiveData<List<CachedCourse>> = _courses


    init {
        getAllFavoriteCourses()
    }

    private fun getAllFavoriteCourses() {
        viewModelScope.launch {
            coursesRepository.getAllCourses {  }.collectLatest { favoriteCourses ->
                _courses.postValue(favoriteCourses)
            }
        }
    }

}