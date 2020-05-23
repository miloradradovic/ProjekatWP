package dto;

import java.util.ArrayList;

public class VMDTO {
	
	private String oldResourceName;
	
	private String resourceName;
	private String organizationName; //obavezno
	private String categoryName; //obavezno, odavde kupi ram, gpu i broj jezgara
	private int numberOfCores; //obavezno, mora biti vece od 0
	private int RAM; //obavezno, mora biti vece od 0
	private int GPU; //obavezno, mora biti vece ili jednako 0
	private ArrayList<String> connectedDiscs;
	private ArrayList<String> activityFROM;
	private ArrayList<String> activityTO;
	
	public VMDTO() {
		super();
		this.connectedDiscs = new ArrayList<String>();
		this.activityFROM = new ArrayList<String>();
		this.activityTO = new ArrayList<String>();
	}

	public VMDTO(String resourceName, String organizationName, String categoryName, int numberOfCores, int rAM, int gPU,
			ArrayList<String> connectedDiscs, ArrayList<String> activityFROM, ArrayList<String> activityTO) {
		super();
		this.resourceName = resourceName;
		this.organizationName = organizationName;
		this.categoryName = categoryName;
		this.numberOfCores = numberOfCores;
		RAM = rAM;
		GPU = gPU;
		this.connectedDiscs = connectedDiscs;
		this.activityFROM = activityFROM;
		this.activityTO = activityTO;
	}
	
	public String getOldResourceName() {
		return oldResourceName;
	}

	public void setOldResourceName(String oldResourceName) {
		this.oldResourceName = oldResourceName;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getNumberOfCores() {
		return numberOfCores;
	}

	public void setNumberOfCores(int numberOfCores) {
		this.numberOfCores = numberOfCores;
	}

	public int getRAM() {
		return RAM;
	}

	public void setRAM(int rAM) {
		RAM = rAM;
	}

	public int getGPU() {
		return GPU;
	}

	public void setGPU(int gPU) {
		GPU = gPU;
	}

	public ArrayList<String> getConnectedDiscs() {
		return connectedDiscs;
	}

	public void setConnectedDiscs(ArrayList<String> connectedDiscs) {
		this.connectedDiscs = connectedDiscs;
	}

	public ArrayList<String> getActivityFROM() {
		return activityFROM;
	}

	public void setActivityFROM(ArrayList<String> activityFROM) {
		this.activityFROM = activityFROM;
	}

	public ArrayList<String> getActivityTO() {
		return activityTO;
	}

	public void setActivityTO(ArrayList<String> activityTO) {
		this.activityTO = activityTO;
	}

}
