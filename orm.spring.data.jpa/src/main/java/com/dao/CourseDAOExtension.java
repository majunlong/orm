package com.dao;

import java.util.List;

import com.entities.relation.otm.Course;
import com.model.StudentCourseModel;

public interface CourseDAOExtension {

	 List<StudentCourseModel> findByQBC(Course course);
	
}
