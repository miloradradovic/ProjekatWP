package model;

public class CategoryVM {
	
	private String categoryName; //id, obavezno
	private int numberOfCores; //obavezno, mora biti vece od 0
	private int RAM; //obavezno, mora biti vece od 0
	private int GPU; //obavezno, mora biti vece ili jednako 0
	
	public CategoryVM() {
		super();
	}
	
	public CategoryVM(String name, int number_of_cores, int RAM, int GPU) {
		super();
		this.categoryName = name;
		this.numberOfCores = number_of_cores;
		this.RAM = RAM;
		this.GPU = GPU;
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
	
}
