package com.test;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dao.CourseDAO;
import com.dao.StudentDAO;
import com.entities.relation.otm.Course;
import com.entities.relation.otm.Student;
import com.enums.CourseType;
import com.model.PageModel;
import com.model.StudentCourseModel;
import com.service.StudentCourseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-content.xml")
public class TestSpringDataJPA {

	@Autowired
	private CourseDAO courseDAO;
	@Autowired
	private StudentDAO studentDAO;
	@Autowired
	private StudentCourseService studentCourseService;

	@Test
	public void testFind() {
		Course course = this.courseDAO.findByStudent_nameAndType("张三", CourseType.MATHS);
		System.out.println(course);
	}
	
	@Test
	public void testQuery() {
		Student student = this.studentDAO.findByQuery("张三");
		System.out.println(student);
	}

	@Test
	public void testNamedNativeQuery1() {
		List<StudentCourseModel> list = this.studentDAO.findByNamedNativeQuery1();
		LinkedList<Course> courseList = new LinkedList<Course>();
		for (StudentCourseModel model : list) {
			model.buildCourseList(courseList);
		}
		for (Course course : courseList) {
			System.out.println(course + "\t" + course.getStudent());
		}
	}

	@Test
	public void testNamedNativeQuery2() {
		List<StudentCourseModel> list = this.studentDAO.findByNamedNativeQuery2();
		LinkedList<Course> courseList = new LinkedList<Course>();
		for (StudentCourseModel model : list) {
			model.buildCourseList(courseList);
		}
		for (Course course : courseList) {
			System.out.println(course + "\t" + course.getStudent());
		}
	}

	@Test
	public void testNamedQuery() {
		List<StudentCourseModel> list = this.courseDAO.findByNamedQuery();
		LinkedList<Student> studentList = new LinkedList<Student>();
		for (StudentCourseModel model : list) {
			model.buildStudentList(studentList);
		}
		for (Student student : studentList) {
			System.out.println(student);
			for (Course course : student.getCourses()) {
				System.out.println("\t" + course);
			}
		}
	}

	@Test
	public void testQBC() {
		Course c = new Course();
		// c.setType(CourseType.CHINESE);
		c.setScore(-100);
		c.setStudent(new Student());
		// c.getStudent().setName("张三");
		c.setPageModel(new PageModel());
		c.getPageModel().setPage(1);
		c.getPageModel().setSize(9);
		List<Student> studentList = this.studentCourseService.findByQBC(c);
		PageModel page = c.getPageModel();
		System.out.println("页码：" + page.getPage());
		System.out.println("页容：" + page.getSize());
		System.out.println("总页：" + page.getTotalPages());
		System.out.println("总行：" + page.getTotalRows());
		for (Student student : studentList) {
			System.out.println(student);
			for (Course course : student.getCourses()) {
				System.out.println("\t" + course);
			}
		}
	}

}
