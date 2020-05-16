package dto;

public class DiscDTO {
	
	private String resourceName;
	private String organizationName; //obavezno
	private String type; //obavezno
	private int capacity; //obavezno
	private String vmName; //obavezno
	
	public DiscDTO() {
		super();
	}

	public DiscDTO(String resourceName, String organizationName, String type, int capacity, String vmName) {
		super();
		this.resourceName = resourceName;
		this.organizationName = organizationName;
		this.type = type;
		this.capacity = capacity;
		this.vmName = vmName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}
	
}
