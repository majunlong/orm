package com.test.collection;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.entities.collection.Student;
import com.enums.CourseType;
import com.test.BaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-collection.xml")
public class TestCollection extends BaseTest {

	@Test
	public void testSave() {
		Student student = new Student("张三", new Date(), new ArrayList<CourseType>(), new LinkedHashSet<CourseType>(), new HashMap<CourseType, Integer>());
		student.getCourseList().add(CourseType.MATHS);
		student.getCourseList().add(CourseType.CHINESE);
		student.getCourseList().add(CourseType.ENGLISH);
		student.getCourseSet().add(CourseType.MATHS);
		student.getCourseSet().add(CourseType.CHINESE);
		student.getCourseSet().add(CourseType.ENGLISH);
		student.getCourseMap().put(CourseType.MATHS, 90);
		student.getCourseMap().put(CourseType.CHINESE, 80);
		student.getCourseMap().put(CourseType.ENGLISH, 70);
		this.session.persist(student);
	}

	@Test
	public void testStudent() {
		Student student = this.session.get(Student.class, 1);
		System.out.println(student);
	}

}
