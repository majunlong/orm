package com.dao;

import java.util.List;

import com.entities.otm.Course;

public interface CourseDAOExtension {

	 List<Course> findByCourseAndPageable(Course course);
	
}
