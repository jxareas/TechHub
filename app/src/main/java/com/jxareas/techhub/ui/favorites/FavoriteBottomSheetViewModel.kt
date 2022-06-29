package com.jxareas.techhub.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.data.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteBottomSheetViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _course = MutableLiveData<CachedCourse>()
    internal var course: LiveData<CachedCourse> = _course

    init {
        savedStateHandle.get<Int>("courseId")?.let { courseId ->
            loadCourse(courseId)
        }
    }

    private fun loadCourse(courseId: Int) {
        viewModelScope.launch {
            courseRepository.getCourseById(courseId).collectLatest { cachedCourse ->
                _course.postValue(cachedCourse)
            }
        }
    }

    fun onUpdate(course: CachedCourse) {
        viewModelScope.launch {
            courseRepository.updateCourse(course)
        }
    }

}
