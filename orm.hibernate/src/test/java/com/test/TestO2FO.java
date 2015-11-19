package com.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.entities.o2fo.Course;
import com.entities.o2fo.Student;
import com.enums.CourseType;

public class TestO2FO extends BaseTest {
	
	@Test
	public void testSave() {
		Student s1 = new Student("张三", new Date(), new Course(CourseType.SPRING, 90));
		Student s2 = new Student("李四", new Date(), new Course(CourseType.STRUCTS2, 70));
		Student s3 = new Student("王五", new Date(), new Course(CourseType.SPRING, 90));
		// this.session.persist(s1);
		// this.session.persist(s2);
		// this.session.persist(s3);
		this.session.persist(s1.getCourse());
		this.session.persist(s2.getCourse());
		this.session.persist(s3.getCourse());
	}

	@Test
	public void testStudent() {
		Student student = this.session.get(Student.class, 1);
		System.out.println(student);
	}

	@Test
	public void testStudentLazy() {
		Student student = this.session.get(Student.class, 1);
		student.initCourse();
		System.out.println(student + "\t" + student.getCourse());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testStudentList() {
		List<Student> students = this.session.createQuery("FROM com.entities.o2fo.Student").list();
		for (Student student : students) {
			System.out.println(student);
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testStudentListLazy() {
		List<Student> students = this.session.createQuery("FROM com.entities.o2fo.Student").list();
		for (Student student : students) {
			student.initCourse();
		}
		for (Student student : students) {
			System.out.println(student + "\t" + student.getCourse());
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testStudentListFetch() {
		List<Student> students = this.session.createQuery("FROM com.entities.o2fo.Student s LEFT JOIN FETCH s.course").list();
		for (Student student : students) {
			System.out.println(student + "\t" + student.getCourse());
		}
	}

	@Test
	public void testCourse() {
		Course course = this.session.get(Course.class, 1);
		System.out.println(course);
	}

	@Test
	public void testCourseLazy() {
		Course course = this.session.get(Course.class, 1);
		course.getStudent().hashCode();
		System.out.println(course + "\t" + course.getStudent());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCourseList() {
		List<Course> courses = this.session.createQuery("FROM com.entities.o2fo.Course").list();
		for (Course course : courses) {
			System.out.println(course);
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCourseListLazy() {
		List<Course> courses = this.session.createQuery("FROM com.entities.o2fo.Course").list();
		for (Course course : courses) {
			course.getStudent().hashCode();
		}
		for (Course course : courses) {
			System.out.println(course + "\t" + course.getStudent());
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCourseListFetch() {
		List<Course> courses = this.session.createQuery("FROM com.entities.o2fo.Course c LEFT JOIN FETCH c.student").list();
		for (Course course : courses) {
			System.out.println(course + "\t" + course.getStudent());
		}
	}

}
