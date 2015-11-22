package com.test.embed;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.entities.embed.Course;
import com.entities.embed.Student;
import com.enums.CourseType;
import com.test.BaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-embed.xml")
public class TestEmbed extends BaseTest {

	@Test
	public void testSave() {
		Student student = new Student("张三", new Date(), new Course(CourseType.SPRING, 80), new Course(CourseType.HIBERNATE, 75));
		this.session.persist(student);
	}

	@Test
	public void testStudent() {
		Student student = this.session.get(Student.class, 2);
		System.out.println(student);
		System.out.println(student.getCourse1().getParent());
		System.out.println(student.getCourse2().getParent());
	}

}
