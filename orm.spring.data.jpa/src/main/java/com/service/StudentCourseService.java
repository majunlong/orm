package com.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dao.CourseDAO;
import com.entities.relation.otm.Course;
import com.entities.relation.otm.Student;
import com.model.StudentCourseModel;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class StudentCourseService {

	@Autowired
	private CourseDAO courseDAO;
	
	public List<Student> findByQBC(Course course) {
		List<StudentCourseModel> list = this.courseDAO.findByQBC(course);
		LinkedList<Student> studentList = new LinkedList<Student>();
		for (StudentCourseModel model : list) {
			model.buildStudentList(studentList);
		}
		return studentList;
	}

}
