package model;

import java.util.ArrayList;

public class Organization {
	
	@Override
	public String toString() {
		return "Organization [orgName=" + orgName + ", description=" + description + ", logo=" + logo + ", usersEmails="
				+ usersEmails + ", resourcesNames=" + resourcesNames + "]";
	}

	private String orgName; //id, obavezno
	private String description; //obavezno
	private String logo; //opciono, postoji opcija koristi defaultni logo
	private ArrayList<String> usersEmails;
	private ArrayList<String> resourcesNames;
	
	public Organization() {
		super();
		this.usersEmails = new ArrayList<String>();
		this.resourcesNames = new ArrayList<String>();
	}

	public Organization(String orgName, String description, String logo, ArrayList<String> usersEmails,
			ArrayList<String> resourcesNames) {
		super();
		this.orgName = orgName;
		this.description = description;
		this.logo = logo;
		this.usersEmails = usersEmails;
		this.resourcesNames = resourcesNames;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public ArrayList<String> getUsersEmails() {
		return usersEmails;
	}

	public void setUsersEmails(ArrayList<String> usersEmails) {
		this.usersEmails = usersEmails;
	}

	public ArrayList<String> getResourcesNames() {
		return resourcesNames;
	}

	public void setResourcesNames(ArrayList<String> resourcesNames) {
		this.resourcesNames = resourcesNames;
	}
}
