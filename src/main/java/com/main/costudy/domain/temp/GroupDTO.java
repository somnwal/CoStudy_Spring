package com.main.costudy.domain.temp;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class GroupDTO {
	String groupURL;
	String groupName;
	String groupMaster;
	String groupProfile;
	String groupAbout;
	String studyStart;
	String studyFinish;
	String studyLocation;
	int groupPop;
	int groupScore;
	
	
	public String getGroupURL() {
		return groupURL;
	}
	public void setGroupURL(String groupURL) {
		this.groupURL = groupURL;
	}
	public String getGroupName() {
		return groupName;
	}
	public String getGroupName2() {
		String result = groupName;
		
		try {
			result = URLDecoder.decode(result, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupMaster() {
		return groupMaster;
	}
	public void setGroupMaster(String groupMaster) {
		this.groupMaster = groupMaster;
	}
	public String getGroupProfile() {
		return groupProfile;
	}
	public void setGroupProfile(String groupProfile) {
		this.groupProfile = groupProfile;
	}
	public String getGroupAbout() {
		return groupAbout;
	}
	public void setGroupAbout(String groupAbout) {
		this.groupAbout = groupAbout;
	}
	public String getStudyStart() {
		return studyStart;
	}
	public void setStudyStart(String studyStart) {
		this.studyStart = studyStart;
	}
	public String getStudyFinish() {
		return studyFinish;
	}
	public void setStudyFinish(String studyFinish) {
		this.studyFinish = studyFinish;
	}
	public String getStudyLocation() {
		return studyLocation;
	}
	public void setStudyLocation(String studyLocation) {
		this.studyLocation = studyLocation;
	}
	public int getGroupPop() {
		return groupPop;
	}
	public void setGroupPop(int groupPop) {
		this.groupPop = groupPop;
	}
	public int getGroupScore() {
		return groupScore;
	}
	public void setGroupScore(int groupScore) {
		this.groupScore = groupScore;
	}
	
	
	
}
