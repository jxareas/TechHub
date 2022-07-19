package com.jxareas.techhub.ui.details


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.techhub.domain.repository.CourseRepository
import com.jxareas.techhub.domain.repository.FavoriteRepository
import com.jxareas.techhub.domain.model.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val favoriteRepository: FavoriteRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _course = MutableLiveData<Course>()
    internal var course: LiveData<Course> = _course

    private val _relatedCourses = MutableLiveData<List<Course>>()
    internal var relatedCourses: LiveData<List<Course>> = _relatedCourses

    init {
        savedStateHandle.get<Int>("courseId")?.let { courseId ->
            loadCourse(courseId)
            getRelatedCourses(courseId)
        }

    }

    private fun getRelatedCourses(courseId: Int) {
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

    fun onUpdate(course: Course) {
        viewModelScope.launch {
            courseRepository.updateAccessedDate(course)
        }
    }

    fun addOrRemoveFromFavorites(course: Course) {
        viewModelScope.launch {
            favoriteRepository.addOrRemoveFromFavorites(
                course.courseId,
                shouldAdd = course.favorite
            )
        }
    }

}
