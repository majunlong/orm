package com.dao;

import java.util.ArrayList;
import java.util.Date;
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

import com.entities.otm.Course;
import com.entities.otm.Student;
import com.enums.CourseType;
import com.model.PageModel;

@Repository
public class StudentCourseDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public static class CourseModel {

		private Integer id;
		private CourseType type;
		private Integer score;
		private Integer studentId;
		private String studentName;
		private Date studentBirth;

		public Course initCourse() {
			try {
				return new Course(id, type, score, studentId, studentName, studentBirth);
			} finally {
				this.id = null;
				this.type = null;
				this.score = null;
				this.studentId = null;
				this.studentName = null;
				this.studentBirth = null;
			}
		}

	}

	@SuppressWarnings("unchecked")
	public List<Course> getByCourseAndPageable(Course course) {
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
		criteria.setProjection(Projections.projectionList().add(Projections.property("c.id"), "id").add(Projections.property("c.type"), "type").add(Projections.property("c.score"), "score").add(Projections.property("s.id"), "studentId").add(Projections.property("s.name"), "studentName").add(Projections.property("s.birth"), "studentBirth"));
		criteria.setResultTransformer(Transformers.aliasToBean(CourseModel.class));
		criteria.addOrder(Order.asc("s.id"));
		criteria.addOrder(Order.asc("c.id"));
		PageModel pageModel = course.getPageModel();
		pageModel.setRows(count);
		criteria.setFirstResult((pageModel.getPage() - 1) * pageModel.getSize());
		criteria.setMaxResults(pageModel.getSize());
		List<CourseModel> modelList = criteria.list();
		List<Course> list = new ArrayList<Course>();
		for (CourseModel model : modelList) {
			list.add(model.initCourse());
		}
		return list;
	}

}
