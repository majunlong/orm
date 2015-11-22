package com.entities.embed;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.Parent;

import com.enums.CourseType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = { "parent" })
@EqualsAndHashCode(exclude = { "parent" })
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Course {

	@Enumerated(EnumType.STRING)
	@Column(name = "STUDENT_COURSE_TYPE")
	private CourseType type;

	@Column(name = "STUDENT_COURSE_SCORE")
	private Integer score;

	@Parent
	private Object parent;

	public Course(CourseType type, Integer score) {
		this.type = type;
		this.score = score;
	}

}
