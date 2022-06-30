package com.jxareas.techhub.data.cache.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.jxareas.techhub.data.cache.model.CachedCourse
import com.jxareas.techhub.data.cache.relations.CourseWithFavorite
import java.util.*

@Dao
interface CourseDao : BaseDao<CachedCourse> {

    @Transaction
    @Query("SELECT * FROM courses")
    suspend fun getAll(): List<CourseWithFavorite>

    @Transaction
    @Query("SELECT * FROM courses WHERE lastAccessed <> 0 ORDER BY lastAccessed DESC LIMIT 10")
    fun getRecentCourses(): List<CourseWithFavorite>

    @Query("UPDATE courses SET lastAccessed = :lastAccessed WHERE courseId = :courseId")
    suspend fun updateAccessedDate(lastAccessed: Date, courseId: Int)

    @Query("DELETE FROM courses")
    fun deleteAll()

    @Transaction
    @Query("SELECT * FROM courses WHERE name LIKE '%' || :courseName || '%'")
    fun getAllCoursesByName(courseName: String): List<CourseWithFavorite>

    @Query("SELECT * FROM courses WHERE topicName LIKE :topic")
    fun getCoursesByTopic(topic: String): List<CachedCourse>

    @Transaction
    @Query("SELECT * FROM courses where courseId = :id")
    fun getById(id: Int): CourseWithFavorite

    @Query(
        """
            SELECT * FROM courses WHERE courseId<>:id
            ORDER BY instructorPhoto LIKE(SELECT instructorPhoto FROM courses WHERE courseId = :id) DESC,
                    topicName = (SELECT topicName FROM courses WHERE courseId = :id) DESC
            LIMIT 20
        """
    )
    fun getRelatedCourses(id: Int): List<CachedCourse>
}
