package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entities.otm.Course;
import com.enums.CourseType;

public interface CourseDAO extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course>, CourseDAOExtension {

	Course findByTypeAndStudent_name(CourseType type, String name);

	@Query(name = "Course.findStudentByType")
	List<Course> findStudentByType(@Param ("type") CourseType type);

}
