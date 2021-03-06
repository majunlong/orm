package com.test.relation;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.entities.relation.otmu.Course;
import com.entities.relation.otmu.Student;
import com.enums.CourseType;
import com.test.BaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-otmu.xml")
public class TestOneToManyUnique extends BaseTest {
	
	@Test
	public void testSave() {
		Student s1 = new Student("张三", new Date(), new Course(CourseType.SPRING, 90));
		Student s2 = new Student("李四", new Date(), new Course(CourseType.STRUCTS2, 70));
		Student s3 = new Student("王五", new Date(), new Course(CourseType.HIBERNATE, 80));
		this.session.persist(s1);
		this.session.persist(s2);
		this.session.persist(s3);
		this.session.persist(s1.getCourse());
		this.session.persist(s2.getCourse());
		this.session.persist(s3.getCourse());
	}

	@Test
	public void testStudentEager() {
		Student student = this.session.get(Student.class, 1);
		System.out.println(student + "\t" + student.getCourse());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testStudentListFetch() {
		List<Student> students = this.session.createQuery("FROM Student s LEFT JOIN FETCH s.course").list();
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
	@SuppressWarnings("unchecked")
	public void testCourseList() {
		List<Course> courses = this.session.createQuery("FROM Course").list();
		for (Course course : courses) {
			System.out.println(course);
		}
	}

}
