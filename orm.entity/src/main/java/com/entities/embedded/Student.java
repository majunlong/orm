package com.entities.embedded;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
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
@DynamicInsert
@DynamicUpdate
@Table(name = "EBDD_STUDENT")
public class Student {

	@Id
	@GeneratedValue(generator = "system_native")
	@GenericGenerator(name = "system_native", strategy = "native")
	@Column(name = "STUDENT_ID")
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name = "STUDENT_BIRTH")
	private Date birth;
	
	@Embedded
	private FullName fullName;

	@OrderBy("STUDENT_ID")
	@ElementCollection(fetch = FetchType.LAZY)
	@AttributeOverrides({ @AttributeOverride(name = "type", column = @Column(name = "COURSE_TYPE") ), @AttributeOverride(name = "score", column = @Column(name = "COURSE_SCORE") ) })
	@CollectionTable(name = "EBDD_COURSE", joinColumns = { @JoinColumn(name = "STUDENT_ID", nullable = false, updatable = false) }, foreignKey = @ForeignKey(name = "FK_EBDD_STUDENT_ID") , uniqueConstraints = { @UniqueConstraint(name = "UK_EBDD_COURSE_SELECTION", columnNames = { "STUDENT_ID", "COURSE_TYPE" }) })
	private List<Course> courses;

	public void initCourses() {
		this.courses.size();
	}

	public Student(Date birth, FullName fullName, Course... courses) {
		this.birth = birth;
		this.fullName = fullName;
		fullName.setParent(this);
		this.courses = Arrays.asList(courses);
		for (Course course : courses) {
			course.setParent(this);
		}
	}

}
