package com.entities.otm;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

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
@Table(name = "OTM_STUDENT", uniqueConstraints = { @UniqueConstraint(name = "UK_OTM_STUDENT_NAME", columnNames = { "STUDENT_NAME" }) })
@SqlResultSetMapping(name = "StudentSqlResult", classes = { @ConstructorResult(targetClass = Student.class,
	columns = {
		@ColumnResult(name = "STUDENT_ID"),
		@ColumnResult(name = "STUDENT_NAME"),
		@ColumnResult(name = "STUDENT_BIRTH"),
		@ColumnResult(name = "STUDENT_TOTAL_SCORE", type = Long.class)}
)})
@NamedNativeQuery(resultSetMapping = "StudentSqlResult", name = "Student.findByTotalScoreGreaterThan",
	query = "select "
			+ "s.STUDENT_ID, "
			+ "s.STUDENT_NAME, "
			+ "s.STUDENT_BIRTH, "
			+ "sum(c.COURSE_SCORE) as STUDENT_TOTAL_SCORE "
			+ "from OTM_STUDENT s "
			+ "left outer join OTM_COURSE c on s.STUDENT_ID = c.STUDENT_ID "
			+ "group by s.STUDENT_ID "
			+ "having sum(c.COURSE_SCORE) > :score "
			+ "order by s.STUDENT_NAME"
)
public class Student {

	public Student(Integer id, String name, Date birth) {
		this.id = id;
		this.name = name;
		this.birth = birth;
	}

	public Student(Integer id, String name, Date birth, Long totalScore) {
		this.id = id;
		this.name = name;
		this.birth = birth;
		this.totalScore = totalScore;
	}

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
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
	private Set<Course> courses;

	@Transient
	private Long totalScore;

	public Student(String name, Date birth, Course... courses) {
		this.name = name;
		this.birth = birth;
		this.courses = new LinkedHashSet<Course>();
		for (Course course : courses) {
			course.setStudent(this);
			this.courses.add(course);
		}
	}

}
