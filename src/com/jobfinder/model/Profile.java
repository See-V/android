package com.jobfinder.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONObject;

public class Profile implements Serializable {

	public static final String TAG = "Profile";

	private static final long serialVersionUID = -1136989553474931932L;

	//JSON Attributes
	public static final String JSON_FIRST_NAME = "first_name";
	public static final String JSON_LAST_NAME = "last_name";
	public static final String JSON_EMAIL = "email";
	public static final String JSON_HEADLINE = "headline";
	public static final String JSON_DESCRIPTION = "description";
	public static final String JSON_PHONE = "phone";
	public static final String JSON_ADDRESS = "address";
	public static final String JSON_LOCATION = "location";

	// Variables
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String headline;
	protected String description;
	protected String phoneNumber;
	protected String address;
	protected String location; // example : country name | city name
	
	protected ArrayList<Education> listEducations;
	protected ArrayList<Skill> listSkills;

	// Constructors
	public Profile() {}

	public Profile(JSONObject json) {
		if(json != null) {
			if(!json.isNull(JSON_FIRST_NAME))
				this.firstName = json.optString(JSON_FIRST_NAME);
			if(!json.isNull(JSON_LAST_NAME))
				this.lastName = json.optString(JSON_LAST_NAME);
			if(!json.isNull(JSON_EMAIL))
				this.firstName = json.optString(JSON_EMAIL);
			if(!json.isNull(JSON_HEADLINE))
				this.headline = json.optString(JSON_HEADLINE);
			if(!json.isNull(JSON_DESCRIPTION))
				this.description = json.optString(JSON_DESCRIPTION);
			if(!json.isNull(JSON_PHONE))
				this.phoneNumber = json.optString(JSON_PHONE);
			if(!json.isNull(JSON_ADDRESS))
				this.address = json.optString(JSON_ADDRESS);
			if(!json.isNull(JSON_LOCATION))
				this.location = json.optString(JSON_LOCATION);
		}
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public ArrayList<Skill> getListSkills() {
		return listSkills;
	}

	public void setListSkills(ArrayList<Skill> listSkills) {
		this.listSkills = listSkills;
	}

	public ArrayList<Education> getListEducations() {
		return listEducations;
	}

	public void setListEducations(ArrayList<Education> listEducations) {
		this.listEducations = listEducations;
	}

}
