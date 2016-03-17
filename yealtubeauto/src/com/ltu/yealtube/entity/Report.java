package com.ltu.yealtube.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Report {

	@Id
	private Long id;

	@Index
	private String date;

	private int pendingCount;

	private int sentCount;

	private int unsentCount;

	private int cancelledCount;

	private int inWorkCount;

	private int exceptionCount;

	private int processCount;

	private Date modifiedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPendingCount() {
		return pendingCount;
	}

	public void setPendingCount(int pendingCount) {
		this.pendingCount = pendingCount;
	}

	public int getSentCount() {
		return sentCount;
	}

	public void setSentCount(int sentCount) {
		this.sentCount = sentCount;
	}

	public int getUnsentCount() {
		return unsentCount;
	}

	public void setUnsentCount(int unsentCount) {
		this.unsentCount = unsentCount;
	}

	public int getCancelledCount() {
		return cancelledCount;
	}

	public void setCancelledCount(int cancelledCount) {
		this.cancelledCount = cancelledCount;
	}

	public int getInWorkCount() {
		return inWorkCount;
	}

	public void setInWorkCount(int inWorkCount) {
		this.inWorkCount = inWorkCount;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public int getExceptionCount() {
		return exceptionCount;
	}

	public int getProcessCount() {
		return processCount;
	}

	public void setProcessCount(int processCount) {
		this.processCount = processCount;
	}

	public void setExceptionCount(int exceptionCount) {
		this.exceptionCount = exceptionCount;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public Report() {

	}

	public Report(Long id, String date, int pendingCount, int sentCount, int unsentCount, int cancelledCount, int inWorkCount,
			int exceptionCount, int processCount) {
		this.id = id;
		this.date = date;
		this.pendingCount = pendingCount;
		this.sentCount = sentCount;
		this.unsentCount = unsentCount;
		this.cancelledCount = cancelledCount;
		this.inWorkCount = inWorkCount;
		this.exceptionCount = exceptionCount;
		this.processCount = processCount;
	}

	public Report(String date, int pendingCount, int sentCount, int unsentCount, int cancelledCount, int inWorkCount,
			int exceptionCount, int processCount) {
		this.date = date;
		this.pendingCount = pendingCount;
		this.sentCount = sentCount;
		this.unsentCount = unsentCount;
		this.cancelledCount = cancelledCount;
		this.inWorkCount = inWorkCount;
		this.exceptionCount = exceptionCount;
		this.processCount = processCount;
	}

	public Report(String date) {
		this.date = date;
		this.pendingCount = 0;
		this.sentCount = 0;
		this.unsentCount = 0;
		this.cancelledCount = 0;
		this.inWorkCount = 0;
		this.exceptionCount = 0;
		this.processCount = 0;
	}

	@Override
	public String toString() {
		return "Report [id=" + id + ", date=" + date + ", pendingCount=" + pendingCount + ", sentCount=" + sentCount
				+ ", unsentCount=" + unsentCount + ", cancelledCount=" + cancelledCount + ", inWorkCount=" + inWorkCount
				+ ", exceptionCount=" + exceptionCount + ", processCount=" + processCount + ", modifiedAt=" + modifiedAt + "]";
	}

}
