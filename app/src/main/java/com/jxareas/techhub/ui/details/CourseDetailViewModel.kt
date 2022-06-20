package com.jxareas.techhub.ui.details


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
class CourseDetailViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _course = MutableLiveData<CachedCourse>()
    internal var course: LiveData<CachedCourse> = _course

    private val _relatedCourses = MutableLiveData<List<CachedCourse>>()
    internal var relatedCourses : LiveData<List<CachedCourse>> = _relatedCourses

    init {
        savedStateHandle.get<Int>("courseId")?.let { courseId ->
            loadCourse(courseId)
            getRelatedCourses(courseId)
        }

    }

    private fun getRelatedCourses(courseId : Int) {
         viewModelScope.launch {
             courseRepository.getRelatedCourses(courseId).collectLatest { cachedCourses ->
                 _relatedCourses.postValue(cachedCourses)
             }
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