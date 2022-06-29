package com.jxareas.techhub.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.data.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteCoursesViewModel @Inject constructor(
    private val coursesRepository: CourseRepository
) : ViewModel() {

    private val _favorites = MutableLiveData<List<CachedCourse>>()
    val favorites: LiveData<List<CachedCourse>> = _favorites

    init {
        getAllFavoriteCourses()
    }

    fun onFavoriteRemoved(id: Int) {
        viewModelScope.launch {
            coursesRepository.removeFromFavorites(id)
            getAllFavoriteCourses()
        }
    }

    fun getAllFavoriteCourses() {
        viewModelScope.launch {
            coursesRepository.getFavoriteCourses().collectLatest { favoriteCourses ->
                _favorites.postValue(favoriteCourses)
            }
        }
    }

}
