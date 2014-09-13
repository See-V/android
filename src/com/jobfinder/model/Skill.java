package com.jobfinder.model;

import java.io.Serializable;

import org.json.JSONObject;

public class Skill implements Serializable {

	public static final String TAG = "Skill";

	private static final long serialVersionUID = 2805846004168206388L;
	
	// Levels
	public static final int LEVEL_EXPERT = 5;
	public static final int LEVEL_CONFIRMED = 4;
	public static final int LEVEL_JUNIOR = 3;
	public static final int LEVEL_GOOD = 2;
	public static final int LEVEL_MIDDLE = 1;

	// JSON Attributes
	public static final String JSON_SKILL_NAME = "skill_name";
	public static final String JSON_LEVEL = "level";

	// Variables
	private String name;
	private int level;

	// Constructors
	public Skill() {}

	public Skill(String name, int level) {
		this.name = name;
		this.level = level;
	}

	public Skill(JSONObject json) {
		if(json != null) {
			if(!json.isNull(JSON_SKILL_NAME))
				this.name = json.optString(JSON_SKILL_NAME);
			if(!json.isNull(JSON_LEVEL))
				this.level = json.optInt(JSON_LEVEL, LEVEL_MIDDLE);
		}
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
