package com.entities.relation.otm;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Formula;

import com.model.StudentCourseModel;

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
@Table(name = "OTM_STUDENT", uniqueConstraints = { @UniqueConstraint(name = "UK_OTM_STUDENT_NAME", columnNames = { "STUDENT_NAME" }) })
@SqlResultSetMapping(name = "StudentCourseSqlResult",
	classes = {
		@ConstructorResult(targetClass = StudentCourseModel.class, columns = {
			@ColumnResult(name = "STUDENT_ID", type = Integer.class),
			@ColumnResult(name = "STUDENT_NAME", type = String.class),
			@ColumnResult(name = "STUDENT_BIRTH", type = Date.class),
			@ColumnResult(name = "STUDENT_TOTAL_SCORE", type = Long.class),
			@ColumnResult(name = "COURSE_ID", type = Integer.class),
			@ColumnResult(name = "COURSE_TYPE", type = String.class),
			@ColumnResult(name = "COURSE_SCORE", type = Integer.class)
		})
	}
)
@NamedNativeQueries({
	@NamedNativeQuery(name = "Student.NamedNativeQuery1", resultSetMapping = "StudentCourseSqlResult",
	query = "select s.STUDENT_ID, s.STUDENT_NAME, s.STUDENT_BIRTH, " +
			"(select sum(c.COURSE_SCORE) from OTM_COURSE c where c.STUDENT_ID = s.STUDENT_ID) as STUDENT_TOTAL_SCORE, " +
			"c.COURSE_ID, c.COURSE_TYPE, c.COURSE_SCORE " +
			"from OTM_STUDENT s left outer join OTM_COURSE c on s.STUDENT_ID = c.STUDENT_ID " +
			"order by s.STUDENT_ID, c.COURSE_ID"
	),
	@NamedNativeQuery(name = "Student.NamedNativeQuery2", resultSetMapping = "StudentCourseSqlResult",
	query = "select s.*, c.COURSE_ID, c.COURSE_TYPE, c.COURSE_SCORE " +
			"from (select s.STUDENT_ID, s.STUDENT_NAME, s.STUDENT_BIRTH, sum(c.COURSE_SCORE) as STUDENT_TOTAL_SCORE " +
			"from OTM_STUDENT s left outer join OTM_COURSE c on s.STUDENT_ID = c.STUDENT_ID group by s.STUDENT_ID) s " +
			"left outer join OTM_COURSE c on s.STUDENT_ID = c.STUDENT_ID " +
			"order by s.STUDENT_ID, c.COURSE_ID"
	)
})
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

	@Formula("(select SUM(c.COURSE_SCORE) from OTM_COURSE c WHERE c.STUDENT_ID = STUDENT_ID)")
	private Long totalScore;

	@OrderBy("id")
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
	private Set<Course> courses;

	public Student(String name, Date birth, Course... courses) {
		this.name = name;
		this.birth = birth;
		this.courses = new LinkedHashSet<Course>();
		for (Course course : courses) {
			this.courses.add(course);
			course.setStudent(this);
		}
	}

}
