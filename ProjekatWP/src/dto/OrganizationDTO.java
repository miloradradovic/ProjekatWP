package dto;

public class OrganizationDTO {
	
	private String orgName; //id, obavezno
	private String description; //obavezno
	private String logo; //opciono, postoji opcija koristi defaultni logo
	
	public OrganizationDTO() {
		super();
	}

	public OrganizationDTO(String orgName, String description, String logo) {
		super();
		this.orgName = orgName;
		this.description = description;
		this.logo = logo;
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
