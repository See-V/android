package com.jobfinder.ws;

import android.content.Context;
import android.util.Log;

import com.jobfinder.callbacks.OnNetworkOperationListener;
import com.jobfinder.model.Skill;
import com.loopj.android.http.RequestParams;

public class SkillWS extends JobFinderWS {

	public static final String TAG = "SkillWS";

	public static final String WS_OBJECT_SKILL = "";

	private static final String USER_ID_TAG = "[user_id]";

	// ws methods
	public static final String WS_GET_SKILLS = "user/:" + USER_ID_TAG + "/skills.json";
	public static final String WS_POST_SKILL = "user/:" + USER_ID_TAG + "/skills";

	// parameters names
	public static final String PARAM_SKILL_NAME = Skill.JSON_SKILL_NAME;
	public static final String PARAM_LEVEL = Skill.JSON_LEVEL;

	public SkillWS(Context context, OnNetworkOperationListener listener) {
		super(context, listener);
	}

	public SkillWS(Context context) {
		super(context);
	}

	public void getSkillsOfUser(int id, OnNetworkOperationListener listener) {
		setListener(listener);
		RequestParams params = new RequestParams();
		String paramId = WS_GET_SKILLS.replace(USER_ID_TAG, String.valueOf(id));
		Log.d(TAG, "paramId : " + paramId);
		post(paramId, params);
	}

	public void addSkill(int profileId, String skillName, int level, OnNetworkOperationListener listener) {
		setListener(listener);
		RequestParams params = new RequestParams();
		params.add(PARAM_SKILL_NAME, skillName);
		params.add(PARAM_LEVEL, String.valueOf(level));
		String paramId = WS_POST_SKILL.replace(USER_ID_TAG, String.valueOf(profileId));
		Log.d(TAG, "paramId : " + paramId);
		post(paramId, params);
	}

	@Override
	protected String getWebServiceName() {
		return WS_OBJECT_SKILL;
	}

}
