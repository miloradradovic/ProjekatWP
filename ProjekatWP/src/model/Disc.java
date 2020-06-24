package model;

import java.time.LocalDateTime;

public class Disc extends Resource {
	
	private String organizationName; //obavezno
	private DiscType type; //obavezno
	private int capacity; //obavezno
	private String vmName; //obavezno
	private LocalDateTime created;
	
	public Disc() {
		super();
	}
	
	public Disc(String name, String organizationName, DiscType type, int capacity, String vmName,
			LocalDateTime created) {
		super(name);
		this.organizationName = organizationName;
		this.type = type;
		this.capacity = capacity;
		this.vmName = vmName;
		this.created = created;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public DiscType getType() {
		return type;
	}

	public void setType(DiscType type) {
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

	@Override
	public String toString() {
		return "Disc [organizationName=" + organizationName + ", type=" + type + ", capacity=" + capacity + ", vmName="
				+ vmName + ", created=" + created + "]";
	}
	
}
