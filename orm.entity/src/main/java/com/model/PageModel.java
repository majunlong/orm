package com.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PageModel {

	private int page;

	private int size;

	private long totalRows;

	private long totalPages;

	public void setRows(long rows) {
		this.totalRows = rows;
		if (this.size < 1) {
			this.size = 3;
		}
		long i = rows % this.size;
		this.totalPages = i > 0 ? rows / this.size + 1 : rows / this.size;
		if (this.page < 1) {
			this.page = Integer.parseInt(this.totalPages + "");
		} else if (this.page > this.totalPages) {
			this.page = 1;
		}
	}

}
