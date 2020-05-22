package dto;

import java.util.ArrayList;

public class OrganizationDTO {
	
	private String orgName; //id, obavezno
	private String description; //obavezno
	private String logo; //opciono, postoji opcija koristi defaultni logo
	private ArrayList<String> usersEmails;
	private ArrayList<String> resourcesNames;
	
	public OrganizationDTO() {
		super();
		this.usersEmails = new ArrayList<String>();
		this.resourcesNames = new ArrayList<String>();
	}

	public OrganizationDTO(String orgName, String description, String logo) {
		super();
		this.orgName = orgName;
		this.description = description;
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
	
}
