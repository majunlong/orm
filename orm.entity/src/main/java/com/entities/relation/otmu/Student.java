package com.entities.relation.otmu;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = { "course" })
@EqualsAndHashCode(exclude = { "course" })
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OTMU_STUDENT")
@TableGenerator(name = "hibernateSequence", table = "HIBERNATE_SEQUENCE", pkColumnName = "ID_NAME", pkColumnValue = "OTMU_STUDENT_ID", valueColumnName = "ID_VALUE", initialValue = 0, allocationSize = 1)
public class Student {

	@Id
	@Column(name = "STUDENT_ID")
	@GeneratedValue(generator = "hibernateSequence", strategy = GenerationType.TABLE)
	private Integer id;

	@Column(name = "STUDENT_NAME")
	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name = "STUDENT_BIRTH")
	private Date birth;

	@OneToOne(mappedBy = "student", fetch = FetchType.EAGER)
	private Course course;

	public Student(String name, Date birth) {
		this.name = name;
		this.birth = birth;
	}

	public Student(String name, Date birth, Course course) {
		this.name = name;
		this.birth = birth;
		this.course = course;
		this.course.setStudent(this);
	}

}
