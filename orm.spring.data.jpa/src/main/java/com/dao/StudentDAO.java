package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entities.otm.Student;
import com.enums.CourseType;

public interface StudentDAO extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {

	Long countByCourses_typeAndCourses_scoreGreaterThan(CourseType type, Integer score);

	@Query("SELECT SUM(c.score) FROM com.entities.otm.Course c LEFT JOIN c.student s WHERE s.name = :name")
	Long sumCourseScoreByName(@Param("name") String name);

	@Query(name = "Student.findByTotalScoreGreaterThan", nativeQuery = true)
	List<Student> findByTotalScoreGreaterThan(@Param("score") Integer score);

}
