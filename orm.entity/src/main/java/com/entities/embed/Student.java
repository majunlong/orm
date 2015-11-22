package com.entities.embed;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EMBED_STUDENT")
public class Student {

	@Id
	@Column(name = "STUDENT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "STUDENT_NAME")
	private String name;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "STUDENT_BIRTH")
	private Date birth;

	@Embedded
	private Course course1;

	@Embedded
	@AttributeOverrides({ 
		@AttributeOverride(name = "type", column = @Column(name = "COURSE_TYPE") ), 
		@AttributeOverride(name = "score", column = @Column(name = "COURSE_SCORE") ) 
	})
	private Course course2;

	public Student(String name, Date birth, Course course1, Course course2) {
		this.name = name;
		this.birth = birth;
		this.course1 = course1;
		this.course2 = course2;
		course1.setParent(this);
		course2.setParent(this);
	}

}
