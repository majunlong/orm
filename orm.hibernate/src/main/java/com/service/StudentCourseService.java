package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dao.StudentCourseDAO;
import com.entities.otm.Course;

@Service
public class StudentCourseService {

	@Autowired
	private StudentCourseDAO studentCourseDAO;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Course> getByCourseAndPageable(Course course) {
		return this.studentCourseDAO.getByCourseAndPageable(course);
	}

}
