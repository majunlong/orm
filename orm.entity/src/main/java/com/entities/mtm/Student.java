package com.entities.mtm;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = { "courses" })
@EqualsAndHashCode(exclude = { "courses" })
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MTM_STUDENT")
public class Student {

	@Id
	@Column(name = "STUDENT_ID")
	@GeneratedValue(generator = "system_native")
	@GenericGenerator(name = "system_native", strategy = "native")
	private Integer id;

	@Column(name = "STUDENT_NAME")
	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name = "STUDENT_BIRTH")
	private Date birth;

	@OrderBy("id")
	@ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
	private Set<Course> courses;

	public Student(String name, Date birth, Course... courses) {
		this.name = name;
		this.birth = birth;
		this.courses = new LinkedHashSet<Course>();
		for (Course course : courses) {
			this.courses.add(course);
			if (course.getStudents() == null) {
				course.setStudents(new LinkedHashSet<Student>());
			}
			course.getStudents().add(this);
		}
	}

}
