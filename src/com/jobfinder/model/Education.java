package com.jobfinder.model;

import java.io.Serializable;

import org.json.JSONObject;

public class Education implements Serializable {

	public static final String TAG = "Education";

	private static final long serialVersionUID = 2805846004168206388L;

	//JSON Attributes
	public static final String JSON_SCHOOL_NAME = "school_name";
	public static final String JSON_START_DATE = "start_date";
	public static final String JSON_END_DATE = "end_date";
	public static final String JSON_DEGREE = "degree";
	public static final String JSON_FIELD_OF_STUDY = "field_of_study";

	// Variables
	protected String schoolName;
	protected String startDate;
	protected String endDate;
	protected String degree;
	protected String fieldOfStudy;

	// Constructors
	public Education() {}

	public Education(JSONObject json) {
		if(json != null) {
			if(!json.isNull(JSON_SCHOOL_NAME))
				this.schoolName = json.optString(JSON_SCHOOL_NAME);
			if(!json.isNull(JSON_START_DATE))
				this.startDate = json.optString(JSON_START_DATE);
			if(!json.isNull(JSON_END_DATE))
				this.endDate = json.optString(JSON_END_DATE);
			if(!json.isNull(JSON_DEGREE))
				this.degree = json.optString(JSON_DEGREE);
			if(!json.isNull(JSON_FIELD_OF_STUDY))
				this.fieldOfStudy = json.optString(JSON_FIELD_OF_STUDY);
		}
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getFieldOfStudy() {
		return fieldOfStudy;
	}

	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}
}
