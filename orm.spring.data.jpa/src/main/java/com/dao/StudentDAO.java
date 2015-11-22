package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entities.relation.otm.Student;
import com.model.StudentCourseModel;

public interface StudentDAO extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {

	@Query("FROM Student s WHERE s.name = :name")
	Student findByQuery(@Param("name") String name);

	@Query(name = "Student.NamedNativeQuery1", nativeQuery = true)
	List<StudentCourseModel> findByNamedNativeQuery1();
	
	@Query(name = "Student.NamedNativeQuery2", nativeQuery = true)
	List<StudentCourseModel> findByNamedNativeQuery2();

}
