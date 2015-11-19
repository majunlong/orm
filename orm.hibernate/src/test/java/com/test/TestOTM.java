package com.test;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.entities.otm.Course;
import com.entities.otm.Student;
import com.enums.CourseType;
import com.model.PageModel;
import com.service.StudentCourseService;

public class TestOTM extends BaseTest {

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
		List<Student> students = this.session.createQuery("FROM com.entities.otm.Student").list();
		for (Student student : students) {
			System.out.println(student);
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testStudentListLazy() {
		List<Student> students = this.session.createQuery("FROM com.entities.otm.Student").list();
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
		List<Student> students = this.session.createQuery("SELECT distinct s FROM com.entities.otm.Student s LEFT JOIN FETCH s.courses").list();
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
		List<Course> courses = this.session.createQuery("FROM com.entities.otm.Course").list();
		for (Course course : courses) {
			System.out.println(course);
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCourseListLazy() {
		List<Course> courses = this.session.createQuery("FROM com.entities.otm.Course").list();
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
		List<Course> courses = this.session.createQuery("FROM com.entities.otm.Course c LEFT JOIN FETCH c.student").list();
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

	private StringBuilder hql = new StringBuilder();
	private @Autowired StudentCourseService studentResultService;

	@Test
	public void testCount() { // 查询英语分数大于60分的人数
		this.hql.append("SELECT COUNT(s) ");
		this.hql.append("FROM com.entities.otm.Course c ");
		this.hql.append("LEFT JOIN c.student s ");
		this.hql.append("WHERE c.type = :type AND c.score > :score");

		Query query = this.session.createQuery(hql.toString());
		query.setString("type", CourseType.ENGLISH.name());
		query.setInteger("score", 60);

		Long count = (Long) query.uniqueResult();
		System.out.println("英语分数大于60分的学生人数:" + count);
	}

	@Test
	public void testSum() { // 查询李四的总分
		this.hql.append("SELECT SUM(c.score) ");
		this.hql.append("FROM com.entities.otm.Course c ");
		this.hql.append("LEFT JOIN c.student s ");
		this.hql.append("WHERE s.name = :name");

		Query query = this.session.createQuery(this.hql.toString());
		query.setString("name", "李四");

		Long totalScor = (Long) query.uniqueResult();
		System.out.println("李四的总分:" + totalScor);
	}

	@Test
	public void testLeftJoin() { // 查询张三的数学成绩
		this.hql.append("SELECT c ");
		this.hql.append("FROM com.entities.otm.Course c ");
		this.hql.append("LEFT JOIN c.student s ");
		this.hql.append("WHERE s.name = :name AND c.type = :type");

		Query query = this.session.createQuery(this.hql.toString());
		query.setString("name", "张三");
		query.setString("type", CourseType.MATHS.name());

		Course course = (Course) query.uniqueResult();
		System.out.println("科目\t成绩\t分数");
		System.out.print(course.getType().getValue() + "\t");
		System.out.print(course.getResults() + "\t");
		System.out.print(course.getScore() + "\n");
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testNamedNativeSQL() { // 查询总分大于180分的学生和总分
		Query query = this.session.getNamedQuery("Student.findByTotalScoreGreaterThan");
		query.setInteger("score", 180);

		List<Student> students = query.list();
		System.out.println("学生\t总分");
		for (Student student : students) {
			System.out.print(student.getName() + "\t");
			System.out.print(student.getTotalScore() + "\n");
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testNamedHQL() { // 查询语文分数最低的学生和分数
		Query query = this.session.getNamedQuery("Course.findStudentByType");
		query.setString("type", CourseType.CHINESE.name());

		List<Course> courses = query.list();
		System.out.println("学生\t科目\t成绩\t分数");
		for (Course course : courses) {
			System.out.print(course.getStudent().getName() + "\t");
			System.out.print(course.getType().getValue() + "\t");
			System.out.print(course.getResults() + "\t");
			System.out.print(course.getScore() + "\n");
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
		c.getPageModel().setSize(3);
		List<Course> courses = this.studentResultService.getByCourseAndPageable(c);
		PageModel page = c.getPageModel();
		System.out.println("页码：" + page.getPage());
		System.out.println("页容：" + page.getSize());
		System.out.println("总页：" + page.getTotalPages());
		System.out.println("总行：" + page.getTotalRows());
		System.out.println("学生\t科目\t成绩\t分数");
		for (Course course : courses) {
			System.out.print(course.getStudent().getName() + "\t");
			System.out.print(course.getType().getValue() + "\t");
			System.out.print(course.getResults() + "\t");
			System.out.print(course.getScore() + "\n");
		}
	}

}
