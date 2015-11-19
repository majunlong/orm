package com.entities.o2um;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

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
@Table(name = "O2UM_COURSE", uniqueConstraints = { @UniqueConstraint(name = "UK_O2UM_STUDENT_ID", columnNames = { "STUDENT_ID" }) })
@TableGenerator(name = "hibernateSequence", table = "HIBERNATE_SEQUENCE", pkColumnName = "ID_NAME", pkColumnValue = "O2UM_COURSE_ID", valueColumnName = "ID_VALUE", initialValue = 0, allocationSize = 1)
public class Course {

	@Id
	@Column(name = "COURSE_ID")
	@GeneratedValue(generator = "hibernateSequence", strategy = GenerationType.TABLE)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name = "COURSE_TYPE")
	private CourseType type;

	@Column(name = "COURSE_SCORE")
	private Integer score;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STUDENT_ID", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_O2UM_STUDENT_ID") )
	private Student student;

	public Course(CourseType type, Integer score) {
		this.type = type;
		this.score = score;
	}

}
