package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dao.CourseDAO;
import com.dao.StudentDAO;
import com.entities.otm.Course;
import com.entities.otm.Student;
import com.enums.CourseType;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class StudentCourseService {

	@Autowired
	private StudentDAO studentDAO;

	@Autowired
	private CourseDAO courseDAO;

	public Long countByCourseTypeAndGreaterThanCourseScore(CourseType type, Integer score) {
		return this.studentDAO.countByCourses_typeAndCourses_scoreGreaterThan(type, score);
	}

	public Long sumCourseScoreByName(String name) {
		return this.studentDAO.sumCourseScoreByName(name);
	}

	public Course findByTypeAndStudentName(CourseType type, String name) {
		return this.courseDAO.findByTypeAndStudent_name(type, name);
	}

	public List<Course> findByType(CourseType type) {
		return this.courseDAO.findStudentByType(type);
	}

	public List<Course> findByCourseAndPageable(Course course) {
		return this.courseDAO.findByCourseAndPageable(course);
	}

	public List<Student> findByGreaterThanTotalScore(Integer score) {
		return this.studentDAO.findByTotalScoreGreaterThan(score);
	}

}
