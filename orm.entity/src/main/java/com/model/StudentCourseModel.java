package com.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import com.entities.relation.otm.Course;
import com.entities.relation.otm.Student;
import com.enums.CourseType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentCourseModel {

	private Integer studentId;
	private String studentName;
	private Date studentBirth;
	private Long studentTotalScore;
	private Integer courseId;
	private CourseType courseType;
	private Integer courseScore;
	private Character courseResults;

	public StudentCourseModel(Integer studentId, String studentName, Date studentBirth, Integer courseId, CourseType courseType, Integer courseScore) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentBirth = studentBirth;
		this.courseId = courseId;
		this.courseType = courseType;
		this.courseScore = courseScore;
	}

	public StudentCourseModel(Integer studentId, String studentName, Date studentBirth, Long studentTotalScore, Integer courseId, String courseType, Integer courseScore) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentBirth = studentBirth;
		this.studentTotalScore = studentTotalScore;
		this.courseId = courseId;
		this.courseType = CourseType.valueOf(courseType);
		this.courseScore = courseScore;
	}

	public StudentCourseModel(Integer studentId, String studentName, Date studentBirth, Integer courseId, CourseType courseType, Integer courseScore, Character courseResults) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentBirth = studentBirth;
		this.courseId = courseId;
		this.courseType = courseType;
		this.courseScore = courseScore;
		this.courseResults = courseResults;
	}

	private void destory() {
		this.studentId = null;
		this.studentName = null;
		this.studentBirth = null;
		this.studentTotalScore = null;
		this.courseId = null;
		this.courseType = null;
		this.courseScore = null;
		this.courseResults = null;
	}

	private Student student() {
		Student student = new Student();
		student.setId(this.studentId);
		student.setName(this.studentName);
		student.setBirth(this.studentBirth);
		student.setTotalScore(this.studentTotalScore);
		return student;
	}

	private Course course() {
		Course course = new Course();
		course.setId(this.courseId);
		course.setType(this.courseType);
		course.setScore(this.courseScore);
		course.setResults(this.courseResults);
		return course;
	}

	private void initStudentList(LinkedList<Student> list) {
		Course course = this.course();
		LinkedHashSet<Course> courses = new LinkedHashSet<Course>();
		courses.add(course);
		Student student = this.student();
		course.setStudent(student);
		student.setCourses(courses);
		list.add(student);
	}

	public void buildStudentList(LinkedList<Student> list) {
		if (list.size() == 0) {
			this.initStudentList(list);
			this.destory();
			return;
		}
		Student student = list.getLast();
		if (student.getId() == this.studentId) {
			Course course = this.course();
			course.setStudent(student);
			student.getCourses().add(course);
		} else {
			this.initStudentList(list);
		}
		this.destory();
	}

	private void initCourseList(LinkedList<Course> list) {
		Course course = this.course();
		LinkedHashSet<Course> courses = new LinkedHashSet<Course>();
		courses.add(course);
		Student student = this.student();
		course.setStudent(student);
		student.setCourses(courses);
		list.add(course);
	}

	public void buildCourseList(LinkedList<Course> list) {
		if (list.size() == 0) {
			this.initCourseList(list);
			this.destory();
			return;
		}
		Student student = list.getLast().getStudent();
		if (student.getId() == this.studentId) {
			Course course = this.course();
			course.setStudent(student);
			student.getCourses().add(course);
			list.add(course);
		} else {
			this.initCourseList(list);
		}
		this.destory();
	}

}
