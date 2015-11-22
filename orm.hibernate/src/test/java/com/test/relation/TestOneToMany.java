package com.test.relation;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.entities.relation.otm.Course;
import com.entities.relation.otm.Student;
import com.enums.CourseType;
import com.model.PageModel;
import com.model.StudentCourseModel;
import com.service.StudentCourseService;
import com.test.BaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-otm.xml")
public class TestOneToMany extends BaseTest {

	@Test
	public void testSave() {
		Student s1 = new Student("张三", new Date(), new Course(CourseType.MATHS, 90), new Course(CourseType.CHINESE, 60), new Course(CourseType.ENGLISH, 80));
		Student s2 = new Student("李四", new Date(), new Course(CourseType.MATHS, 70), new Course(CourseType.CHINESE, 60), new Course(CourseType.ENGLISH, 70));
		Student s3 = new Student("王五", new Date(), new Course(CourseType.MATHS, 55), new Course(CourseType.CHINESE, 60), new Course(CourseType.ENGLISH, 60));
		this.save(s1);
		this.save(s2);
		this.save(s3);
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
		course.getStudent().hashCode();
		System.out.println(course + "\t" + course.getStudent());
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
			course.getStudent().hashCode();
		}
		for (Course course : courses) {
			System.out.println(course + "\t" + course.getStudent());
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCourseListFacth() {
		List<Course> courses = this.session.createQuery("FROM Course c LEFT JOIN FETCH c.student").list();
		for (Course course : courses) {
			System.out.println(course + "\t" + course.getStudent());
		}
	}

	private void save(Student student) {
		this.session.persist(student);
		for (Course course : student.getCourses()) {
			this.session.persist(course);
		}
	}

	private @Autowired StudentCourseService studentResultService;

	@Test
	@SuppressWarnings("unchecked")
	public void testNamedNativeQuery1() {
		Query query = this.session.getNamedQuery("Student.NamedNativeQuery1");
		List<StudentCourseModel> list = query.list();
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
	@SuppressWarnings("unchecked")
	public void testNamedNativeQuery2() {
		Query query = this.session.getNamedQuery("Student.NamedNativeQuery2");
		List<StudentCourseModel> list = query.list();
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
	@SuppressWarnings("unchecked")
	public void testNamedQuery() {
		Query query = this.session.getNamedQuery("Course.NamedQuery");
		List<StudentCourseModel> list = query.list();
		LinkedList<Course> courseList = new LinkedList<Course>();
		for (StudentCourseModel model : list) {
			model.buildCourseList(courseList);
		}
		for (Course course : courseList) {
			System.out.println(course + "\t" + course.getStudent());
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
		List<Course> courseList = this.studentResultService.findByQBC(c);
		PageModel page = c.getPageModel();
		System.out.println("页码：" + page.getPage());
		System.out.println("页容：" + page.getSize());
		System.out.println("总页：" + page.getTotalPages());
		System.out.println("总行：" + page.getTotalRows());
		for (Course course : courseList) {
			System.out.println(course + "\t" + course.getStudent());
		}
	}

}
