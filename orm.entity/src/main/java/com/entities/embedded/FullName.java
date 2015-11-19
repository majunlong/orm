package com.entities.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Parent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "parent" })
@EqualsAndHashCode(exclude = { "parent" })
@Embeddable
public class FullName {

	@Column(name = "STUDENT_FIRST_NAME")
	private String firstName;

	@Column(name = "STUDENT_LAST_NAME")
	private String lastName;

	@Parent
	private Object parent;

	public FullName(String firstName, String lastName) {
		this.lastName = lastName;
		this.firstName = firstName;
	}

}
