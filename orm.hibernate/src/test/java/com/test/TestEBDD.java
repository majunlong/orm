package com.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.entities.embedded.Course;
import com.entities.embedded.FullName;
import com.entities.embedded.Student;
import com.enums.CourseType;

public class TestEBDD extends BaseTest {

	@Test
	public void testSave() {
		Student s1 = new Student(new Date(), new FullName("张", "三"), new Course(CourseType.MATHS, 90), new Course(CourseType.CHINESE, 60), new Course(CourseType.ENGLISH, 80));
		Student s2 = new Student(new Date(), new FullName("李", "四"), new Course(CourseType.MATHS, 70), new Course(CourseType.CHINESE, 60), new Course(CourseType.ENGLISH, 70));
		Student s3 = new Student(new Date(), new FullName("王", "五"), new Course(CourseType.MATHS, 55), new Course(CourseType.CHINESE, 60), new Course(CourseType.ENGLISH, 60));
		this.session.persist(s1);
		this.session.persist(s2);
		this.session.persist(s3);
	}

	@Test
	public void testStudent() {
		Student student = this.session.get(Student.class, 1);
		System.out.println(student);
	}

	@Test
	public void testStudentLazy() {
		Student student = this.session.get(Student.class, 1);
		student.initCourses();
		System.out.println(student);
		for (Course course : student.getCourses()) {
			System.out.println("\t" + course);
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testStudentList() {
		List<Student> students = this.session.createQuery("FROM com.entities.embedded.Student").list();
		for (Student student : students) {
			System.out.println(student);
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testStudentListLazy() {
		List<Student> students = this.session.createQuery("FROM com.entities.embedded.Student").list();
		for (Student student : students) {
			student.initCourses();
		}
		for (Student student : students) {
			System.out.println(student);
			for (Course course : student.getCourses()) {
				System.out.println("\t" + course);
			}
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testStudentListFetch() {
		List<Student> students = this.session.createQuery("SELECT distinct s FROM com.entities.embedded.Student s LEFT JOIN FETCH s.courses").list();
		for (Student student : students) {
			System.out.println(student);
			for (Course course : student.getCourses()) {
				System.out.println("\t" + course);
			}
		}
	}

}
