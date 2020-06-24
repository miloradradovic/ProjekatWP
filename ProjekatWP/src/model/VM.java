package model;

import java.util.ArrayList;

public class VM extends Resource {
	
	@Override
	public String toString() {
		return "VM [organizationName=" + organizationName + ", categoryName=" + categoryName + ", connectedDiscs="
				+ connectedDiscs + ", activities=" + activities + "]";
	}

	private String organizationName; //obavezno
	private String categoryName; //obavezno, odavde kupi ram, gpu i broj jezgara
	private ArrayList<String> connectedDiscs;
	private ArrayList<Activity> activities;
	
	public VM() {
		super();
		this.connectedDiscs = new ArrayList<String>();
		this.activities = new ArrayList<Activity>();
	}

	public VM(String name, String organizationName, String categoryName, ArrayList<String> connectedDiscs,
			ArrayList<Activity> activities) {
		super(name);
		this.organizationName = organizationName;
		this.categoryName = categoryName;
		this.connectedDiscs = connectedDiscs;
		this.activities = activities;
	}





	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public ArrayList<String> getConnectedDiscs() {
		return connectedDiscs;
	}

	public void setConnectedDiscs(ArrayList<String> connectedDiscs) {
		this.connectedDiscs = connectedDiscs;
	}

	public ArrayList<Activity> getActivities() {
		return activities;
	}

	public void setActivities(ArrayList<Activity> activities) {
		this.activities = activities;
	}
}
