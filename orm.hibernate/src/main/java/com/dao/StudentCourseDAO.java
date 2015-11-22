package com.dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entities.relation.otm.Course;
import com.entities.relation.otm.Student;
import com.model.PageModel;
import com.model.StudentCourseModel;

@Repository
public class StudentCourseDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Course> findByQBC(Course course) {
		Criteria studentCriteria = null;
		Student student = course.getStudent();
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Course.class, "c");
		criteria.setProjection(Projections.rowCount());
		if (course.getType() != null) {
			criteria.add(Restrictions.eq("type", course.getType()));
		}
		if (course.getScore() != null) {
			if (course.getScore() >= 0) {
				criteria.add(Restrictions.ge("score", course.getScore()));
			} else {
				criteria.add(Restrictions.le("score", Math.abs(course.getScore())));
			}
		}
		if (student != null && student.getName() != null) {
			studentCriteria = criteria.createCriteria("student", "s", JoinType.LEFT_OUTER_JOIN);
			studentCriteria.add(Restrictions.like("name", student.getName(), MatchMode.ANYWHERE));
		}
		long count = (long) criteria.uniqueResult();
		if (count < 1) {
			return null;
		}
		if (studentCriteria == null) {
			studentCriteria = criteria.createCriteria("student", "s", JoinType.LEFT_OUTER_JOIN);
		}
		criteria.setProjection(Projections.projectionList()
			.add(Projections.property("s.id"), "studentId")
			.add(Projections.property("s.name"), "studentName")
			.add(Projections.property("s.birth"), "studentBirth")
			.add(Projections.property("c.id"), "courseId")
			.add(Projections.property("c.type"), "courseType")
			.add(Projections.property("c.score"), "courseScore"));
		criteria.setResultTransformer(Transformers.aliasToBean(StudentCourseModel.class));
		criteria.addOrder(Order.asc("s.id"));
		criteria.addOrder(Order.asc("c.id"));
		PageModel pageModel = course.getPageModel();
		pageModel.setRows(count);
		criteria.setFirstResult((pageModel.getPage() - 1) * pageModel.getSize());
		criteria.setMaxResults(pageModel.getSize());
		List<StudentCourseModel> list = criteria.list();
		LinkedList<Course> courseList = new LinkedList<Course>();
		for (StudentCourseModel model : list) {
			model.buildCourseList(courseList);
		}
		return courseList;
	}

}
