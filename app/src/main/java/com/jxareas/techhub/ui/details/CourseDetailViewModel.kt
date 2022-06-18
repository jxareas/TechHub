package com.jxareas.techhub.ui.details

import androidx.lifecycle.*
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.data.repository.CourseRepository
import com.jxareas.techhub.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val dispatchers: DispatcherProvider,
    savedStateHandle: SavedStateHandle
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
            courseRepository.loadCourseById(courseId).collectLatest { cachedCourse ->
                _course.postValue(cachedCourse)
            }
        }
    }

}