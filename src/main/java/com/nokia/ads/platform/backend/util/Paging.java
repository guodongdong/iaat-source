package com.nokia.ads.platform.backend.util;

public class Paging {

	private int pageSize = 10;
	private int totalRecords = 0;
	private int totalPages = 0;
	private int currentPage = 1;
	private int currentRecord = 0;
	private int totalActuals = 0;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecords = totalRecord;
		if (0 == this.totalActuals)
			this.totalActuals = this.totalRecords;
		this.getTotalPage();
	}

	public int getTotalPage() {
		totalPages = (totalRecords + pageSize - 1) / pageSize;
		if (this.currentPage > totalPages) {
			this.currentPage = 1;
		}
		return totalPages;
	}

	// public void setTotalPage(int totalRecord,int pageSize) {
	// this.totalPage = (totalRecord+pageSize-1)/pageSize;
	// }

	// public int getCurrentPage() {
	// return currentPage;
	// }

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentRecord() {
		currentRecord = (currentPage - 1) * pageSize;
		return currentRecord;
	}

	public int getTotalActuals() {
		return totalActuals;
	}

	public void setTotalActuals(int totalActuals) {
		this.totalActuals = totalActuals;
	}

	// public void setCurrentRecord(int currentRecord) {
	// this.currentRecord = currentRecord;
	// }

}
