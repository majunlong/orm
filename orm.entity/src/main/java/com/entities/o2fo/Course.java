package com.entities.o2fo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.enums.CourseType;

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
@Table(name = "O2FO_COURSE")
public class Course {

	@Id
	@Column(name = "COURSE_ID")
	@GeneratedValue(generator = "system_foreign")
	@GenericGenerator(name = "system_foreign", strategy = "foreign", parameters = { @Parameter(name = "property", value = "student") })
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name = "COURSE_TYPE")
	private CourseType type;

	@Column(name = "COURSE_SCORE")
	private Integer score;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "FK_O2FO_COURSE_ID"))
	private Student student;
	
	public Course(CourseType type, Integer score) {
		this.type = type;
		this.score = score;
	}

}
