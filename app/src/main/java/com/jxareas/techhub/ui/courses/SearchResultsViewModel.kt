package com.jxareas.techhub.ui.courses

import androidx.lifecycle.*
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.data.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultsViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _courses = MutableLiveData<List<CachedCourse>>()
    internal val courses: LiveData<List<CachedCourse>>
        get() = _courses

    init {
        savedStateHandle.get<String>("query")?.let { query ->
            getSearchedResults(query)
        }
    }

    private fun getSearchedResults(query: String) {
        viewModelScope.launch {
            courseRepository.getCoursesByName(query).collectLatest { listOfCourses ->
                _courses.postValue(listOfCourses)
            }
        }
    }
}