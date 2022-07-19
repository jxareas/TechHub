package com.jxareas.techhub.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxareas.techhub.domain.repository.FavoriteRepository
import com.jxareas.techhub.domain.model.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteCoursesViewModel @Inject constructor(
    private val repository: FavoriteRepository
) : ViewModel() {

    private val _favorites = MutableLiveData<List<Course>>()
    val favorites: LiveData<List<Course>> = _favorites

    init {
        getAllFavoriteCourses()
    }

    fun onFavoriteRemoved(id: Int) {
        viewModelScope.launch {
            repository.removeFromFavorites(id)
            getAllFavoriteCourses()
        }
    }

    fun getAllFavoriteCourses() {
        viewModelScope.launch {
            repository.getFavoriteCourses().collectLatest { favoriteCourses ->
                _favorites.postValue(favoriteCourses)
            }
        }
    }

}
