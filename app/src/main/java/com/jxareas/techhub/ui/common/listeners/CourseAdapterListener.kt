package com.jxareas.techhub.ui.common.listeners

import com.jxareas.techhub.domain.model.Course
import com.jxareas.techhub.ui.common.events.OnClicked


@FunctionalInterface
interface CourseAdapterListener : OnClicked<Course>
