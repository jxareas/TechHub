package com.jxareas.techhub.data.cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.jxareas.techhub.data.cache.model.CachedCourse

@Dao
interface CourseDao : BaseDao<CachedCourse> {

    @Query("SELECT * FROM courses")
    fun getAll() : List<CachedCourse>

    @Query("SELECT * FROM courses WHERE favorite = 1")
    fun getFavorites() : List<CachedCourse>

    @Query("SELECT * FROM courses WHERE lastAccessed <> 0 ORDER BY lastAccessed DESC LIMIT 10")
    fun getRecentCourses() : List<CachedCourse>

    @Query("DELETE FROM courses")
    fun deleteAll()

    @Query("SELECT * FROM courses WHERE name LIKE '%' || :courseName || '%'")
    fun getAllCoursesByName(courseName : String) : List<CachedCourse>

    @Query("SELECT * FROM courses WHERE topicName LIKE :topic")
    fun getCoursesByTopic(topic : String) : List<CachedCourse>

    @Query("SELECT * FROM courses where courseId = :id")
    fun getById(id : Int) : CachedCourse

    @Query("SELECT * FROM courses WHERE courseId <> :id " +
            "ORDER BY instructorPhoto LIKE (SELECT instructorPhoto FROM courses WHERE courseId = :id) DESC, " +
            "topicName =  (SELECT topicName FROM courses WHERE courseId = :id) DESC")
    fun getRelatedCourses(id : Int) : List<CachedCourse>
}
