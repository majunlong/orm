package com.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.entities.otm.Course;
import com.entities.otm.Student;
import com.enums.CourseType;
import com.model.PageModel;
import com.service.StudentCourseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-content.xml")
public class TestSpringDataJPA {

	@Autowired
	private StudentCourseService studentCourseService;

	@Test
	public void testCount() { // 查询英语分数大于60分的人数
		Long count = this.studentCourseService.countByCourseTypeAndGreaterThanCourseScore(CourseType.ENGLISH, 60);
		System.out.println("英语分数大于60分的学生人数:" + count);
	}

	@Test
	public void testSum() { // 查询李四的总分
		Long totalScor = (Long) this.studentCourseService.sumCourseScoreByName("李四");
		System.out.println("李四的总分:" + totalScor);
	}

	@Test
	public void testLeftJoin() { // 查询张三的数学成绩
		Course course = this.studentCourseService.findByTypeAndStudentName(CourseType.MATHS, "张三");
		System.out.println("科目\t成绩\t分数");
		System.out.print(course.getType().getValue() + "\t");
		System.out.print(course.getResults() + "\t");
		System.out.print(course.getScore() + "\n");
	}

	@Test
	public void testNamedHQL() { // 查询语文分数最低的学生和分数
		List<Course> courses = this.studentCourseService.findByType(CourseType.CHINESE);
		System.out.println("学生\t科目\t成绩\t分数");
		for (Course course : courses) {
			System.out.print(course.getStudent().getName() + "\t");
			System.out.print(course.getType().getValue() + "\t");
			System.out.print(course.getResults() + "\t");
			System.out.print(course.getScore() + "\n");
		}
	}

	@Test
	public void testNamedNativeSQL() { // 查询总分大于180分的学生和总分
		List<Student> students = this.studentCourseService.findByGreaterThanTotalScore(180);
		System.out.println("学生\t总分");
		for (Student student : students) {
			System.out.print(student.getName() + "\t");
			System.out.print(student.getTotalScore() + "\n");
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
		List<Course> courses = this.studentCourseService.findByCourseAndPageable(c);
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
