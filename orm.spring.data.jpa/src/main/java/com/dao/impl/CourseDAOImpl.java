package com.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import com.dao.CourseDAOExtension;
import com.entities.relation.otm.Course;
import com.entities.relation.otm.Student;
import com.enums.CourseType;
import com.model.PageModel;
import com.model.StudentCourseModel;

public class CourseDAOImpl implements CourseDAOExtension {

	@PersistenceContext
	private EntityManager entityManager;

	private <T> void courseCriteria(Course course, CriteriaBuilder builder, CriteriaQuery<T> query, Root<Course> root, boolean isCount) {
		Student student = course.getStudent();
		Path<Student> studentPath = root.get("student");
		List<Predicate> list = new ArrayList<Predicate>();
		if (course.getType() != null) {
			list.add(builder.equal(root.get("type"), course.getType()));
		}
		if (course.getScore() != null) {
			if (course.getScore() >= 0) {
				list.add(builder.ge(root.get("score").as(Integer.class), course.getScore()));
			} else {
				list.add(builder.le(root.get("score").as(Integer.class), Math.abs(course.getScore())));
			}
		}
		if (student != null && student.getName() != null) {
			if (isCount) {
				root.join("student", JoinType.LEFT);
			}
			list.add(builder.like(studentPath.get("name").as(String.class), "%" + student.getName() + "%"));
		}
		if (list.size() > 0) {
			query.where(list.toArray(new Predicate[list.size()]));
		}
	}

	private long count(Course course) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Course> root = query.from(Course.class);
		this.courseCriteria(course, builder, query, root, true);
		query.select(builder.count(root));
		TypedQuery<Long> typedQuery = this.entityManager.createQuery(query);
		return typedQuery.getSingleResult();
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findByQBC(Course course) {
		Long count = this.count(course);
		if (count < 1) {
			return null;
		}
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<?> query = builder.createQuery();
		Root<Course> root = query.from(Course.class);
		root.join("student", JoinType.LEFT);
		this.courseCriteria(course, builder, query, root, false);
		Path<Student> studentPath = root.get("student");
		query.select((Selection) builder.construct(StudentCourseModel.class,
			studentPath.get("id").as(Integer.class),
			studentPath.get("name").as(String.class),
			studentPath.get("birth").as(Date.class),
			root.get("id").as(Integer.class),
			root.get("type").as(CourseType.class),
			root.get("score").as(Integer.class)));
		query.orderBy(builder.asc(studentPath.get("id")), builder.asc(root.get("id")));
		TypedQuery<?> typedQuery = this.entityManager.createQuery(query);
		PageModel pageModel = course.getPageModel();
		pageModel.setRows(count);
		typedQuery.setFirstResult(pageModel.getPage() - 1);
		typedQuery.setMaxResults(pageModel.getSize());
		return typedQuery.getResultList();
	}

}
