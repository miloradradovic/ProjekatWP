package dto;

import java.util.ArrayList;

import model.Resource;

public class MonthlyCost {
	
	private String dateTo;
	private String dateFrom;
	private ArrayList<Resource> resources;
	private ArrayList<Integer> costs;
	
	public MonthlyCost() {
		super();
	}

	public MonthlyCost(String dateTo, String dateFrom, ArrayList<Resource> resources, ArrayList<Integer> costs) {
		super();
		this.dateTo = dateTo;
		this.dateFrom = dateFrom;
		this.resources = resources;
		this.costs = costs;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public ArrayList<Resource> getResources() {
		return resources;
	}

	public void setResources(ArrayList<Resource> resources) {
		this.resources = resources;
	}

	public ArrayList<Integer> getCosts() {
		return costs;
	}

	public void setCosts(ArrayList<Integer> costs) {
		this.costs = costs;
	}

}
