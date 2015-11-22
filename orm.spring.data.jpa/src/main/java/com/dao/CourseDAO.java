package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.entities.relation.otm.Course;
import com.enums.CourseType;
import com.model.StudentCourseModel;

public interface CourseDAO extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course>, CourseDAOExtension {

	Course findByStudent_nameAndType(String name, CourseType type);

	@Query(name = "Course.NamedQuery")
	List<StudentCourseModel> findByNamedQuery();

}
