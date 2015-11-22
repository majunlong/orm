package com.entities.relation.mtm;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.enums.CourseType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = { "students" })
@EqualsAndHashCode(exclude = { "students" })
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MTM_COURSE")
public class Course {

	@Id
	@Column(name = "COURSE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name = "COURSE_TYPE")
	private CourseType type;

	@Column(name = "COURSE_SCORE")
	private Integer score;

	@OrderBy("id")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "MTM_RELATION", 
		joinColumns = { @JoinColumn(name = "COURSE_ID", nullable = false, updatable = false) }, foreignKey = @ForeignKey(name = "FK_MTM_COURSE_ID") , 
		inverseJoinColumns = { @JoinColumn(name = "STUDENT_ID", nullable = false, updatable = false) }, inverseForeignKey = @ForeignKey(name = "FK_MTM_STUDENT_ID")
	)
	private Set<Student> students;

	public Course(CourseType type, Integer score) {
		this.type = type;
		this.score = score;
	}

}
