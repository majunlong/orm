package com.entities.collection;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.enums.CourseType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "C_STUDENT")
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

	@Enumerated(EnumType.STRING)
	@Column(name = "COURSE_TYPE")
	@OrderColumn(name = "LIST_INDEX")
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "CLIST_COURSE", foreignKey = @ForeignKey(name = "FK_CLIST_STUDENT_ID") , joinColumns = { @JoinColumn(name = "STUDENT_ID") })
	private List<CourseType> courseList;

	@Enumerated(EnumType.STRING)
	@Column(name = "COURSE_TYPE")
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "CSET_COURSE", foreignKey = @ForeignKey(name = "FK_CSET_STUDENT_ID") , joinColumns = { @JoinColumn(name = "STUDENT_ID") })
	private Set<CourseType> courseSet;

	@Column(name = "COURSE_SCORE")
	@MapKeyEnumerated(EnumType.STRING)
	@MapKeyColumn(name = "COURSE_TYPE")
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "CMAP_COURSE", foreignKey = @ForeignKey(name = "FK_CMAP_STUDENT_ID") , joinColumns = { @JoinColumn(name = "STUDENT_ID") })
	private Map<CourseType, Integer> courseMap;

	public Student(String name, Date birth, List<CourseType> courseList, Set<CourseType> courseSet, Map<CourseType, Integer> courseMap) {
		this.name = name;
		this.birth = birth;
		this.courseList = courseList;
		this.courseSet = courseSet;
		this.courseMap = courseMap;
	}

}
