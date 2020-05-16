package model;

public class User {
	
	private String email; //id, obavezno
	private String password; //obavezno
	private String name; //obavezno
	private String surname; //obavezno
	private String organizationName; //obavezno
	private UserType userType; //obavezno
	
	public User() {
		super();
	}

	public User(String email, String password, String name, String surname, String organizationName,
			UserType userType) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.organizationName = organizationName;
		this.userType = userType;
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

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
}
