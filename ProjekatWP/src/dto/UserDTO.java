package dto;


public class UserDTO {
	
	private String oldEmail;
	private String email; //id, obavezno
	private String password; //obavezno
	private String name; //obavezno
	private String surname; //obavezno
	private String organizationName; //obavezno
	private String userType; //obavezno
	
	public UserDTO() {
		super();
	}

	public UserDTO(String email, String password, String name, String surname, String organizationName,
			String userType) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.organizationName = organizationName;
		this.userType = userType;
	}
	
	public String getOldEmail() {
		return oldEmail;
	}

	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
