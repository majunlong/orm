package com.enums;

public enum CourseType {

	CHINESE("语文"), ENGLISH("英语"), MATHS("数学"),
	SPRING("Spring"), HIBERNATE("Hibernate"), STRUCTS2("Structs2");

	private final String value;

	private CourseType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
