package com.entities.otm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import com.enums.CourseType;
import com.model.PageModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = { "student" })
@EqualsAndHashCode(exclude = { "student" })
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OTM_COURSE", uniqueConstraints = { @UniqueConstraint(name = "UK_OTM_COURSE_SELECTION", columnNames = { "STUDENT_ID", "COURSE_TYPE" }) })
@NamedQuery(
	name = "Course.findStudentByType",
	query = "SELECT new com.entities.otm.Course(c.id, c.type, c.score, s.id, s.name, s.birth) "
		+ "FROM com.entities.otm.Course c LEFT JOIN c.student s "
		+ "WHERE c.type = :type AND c.score = ("
		+ "SELECT MIN(cc.score) FROM com.entities.otm.Course cc WHERE c.type = cc.type"
		+ ") ORDER BY s.id")
public class Course {

	public Course(Integer id, CourseType type, Integer score, Integer studentId, String studentName, Date studentBirth) {
		this.id = id;
		this.type = type;
		this.score = score;
		this.student = new Student(studentId, studentName, studentBirth);
	}

	@Id
	@Column(name = "COURSE_ID")
	@GeneratedValue(generator = "system_native")
	@GenericGenerator(name = "system_native", strategy = "native")
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name = "COURSE_TYPE")
	private CourseType type;

	@Column(name = "COURSE_SCORE")
	private Integer score;

	@Formula("(case when COURSE_SCORE >= 90 then 'A' when COURSE_SCORE >= 80 then 'B' when COURSE_SCORE >= 70 then 'C' when COURSE_SCORE >= 60 then 'D' else 'E' end)")
	private Character results;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STUDENT_ID", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_OTM_STUDENT_ID") )
	private Student student;

	@Transient
	private PageModel pageModel;
	
	public Course(CourseType type, Integer score) {
		this.type = type;
		this.score = score;
	}

	public Course(Integer id, CourseType type) {
		super();
		this.id = id;
		this.type = type;
	}
	
	
	
	
	
	
	
}
