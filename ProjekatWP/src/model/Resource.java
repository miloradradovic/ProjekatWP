package model;

public abstract class Resource {
	
	@Override
	public String toString() {
		return "Resource [resourceName=" + resourceName + "]";
	}

	private String resourceName; //id, obavezno

	public Resource() {
		super();
	}

	public Resource(String name) {
		super();
		this.resourceName = name;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

}
