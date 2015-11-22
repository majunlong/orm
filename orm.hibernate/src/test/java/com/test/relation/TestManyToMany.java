package com.test.relation;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.entities.relation.mtm.Course;
import com.entities.relation.mtm.Student;
import com.enums.CourseType;
import com.test.BaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-mtm.xml")
public class TestManyToMany extends BaseTest {

	@Test
	public void testSave() {
		Course c1 = new Course(CourseType.MATHS, 60);
		Course c2 = new Course(CourseType.CHINESE, 60);
		Course c3 = new Course(CourseType.ENGLISH, 60);
		Student s1 = new Student("张三", new Date(), c1, c2, c3);
		Student s2 = new Student("李四", new Date(), c1, c2, c3);
		Student s3 = new Student("王五", new Date(), c1, c2, c3);
		this.session.persist(s1);
		this.session.persist(s2);
		this.session.persist(s3);
		this.session.persist(c1);
		this.session.persist(c2);
		this.session.persist(c3);
	}

	@Test
	public void testStudent() {
		Student student = this.session.get(Student.class, 1);
		System.out.println(student);
	}

	@Test
	public void testStudentLazy() {
		Student student = this.session.get(Student.class, 1);
		student.getCourses().size();
		System.out.println(student);
		for (Course course : student.getCourses()) {
			System.out.println("\t" + course);
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testStudentList() {
		List<Student> students = this.session.createQuery("FROM Student").list();
		for (Student student : students) {
			System.out.println(student);
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testStudentListLazy() {
		List<Student> students = this.session.createQuery("FROM Student").list();
		for (Student student : students) {
			student.getCourses().size();
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
	public void testStudentListFacth() {
		List<Student> students = this.session.createQuery("SELECT distinct s FROM Student s LEFT JOIN FETCH s.courses").list();
		for (Student student : students) {
			System.out.println(student);
			for (Course course : student.getCourses()) {
				System.out.println("\t" + course);
			}
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
		course.getStudents().size();
		System.out.println(course);
		for (Student student : course.getStudents()) {
			System.out.println("\t" + student);
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCourseList() {
		List<Course> courses = this.session.createQuery("FROM Course").list();
		for (Course course : courses) {
			System.out.println(course);
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCourseListLazy() {
		List<Course> courses = this.session.createQuery("FROM Course").list();
		for (Course course : courses) {
			course.getStudents().size();
		}
		for (Course course : courses) {
			System.out.println(course);
			for (Student student : course.getStudents()) {
				System.out.println("\t" + student);
			}
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCourseListFacth() {
		List<Course> courses = this.session.createQuery("SELECT distinct c FROM Course c LEFT JOIN FETCH c.students").list();
		for (Course course : courses) {
			System.out.println(course);
			for (Student student : course.getStudents()) {
				System.out.println("\t" + student);
			}
		}
	}

}
