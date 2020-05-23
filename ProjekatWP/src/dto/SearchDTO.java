package dto;

public class SearchDTO {
	
	private String vmName;
	private int coresFrom;
	private int coresTo;
	private int ramFrom;
	private int ramTo;
	private int gpuFrom;
	private int gpuTo;
	
	public SearchDTO() {
		super();
	}

	public SearchDTO(String vmName, int coresFrom, int coresTo, int ramFrom, int ramTo, int gpuFrom, int gpuTo) {
		super();
		this.vmName = vmName;
		this.coresFrom = coresFrom;
		this.coresTo = coresTo;
		this.ramFrom = ramFrom;
		this.ramTo = ramTo;
		this.gpuFrom = gpuFrom;
		this.gpuTo = gpuTo;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public int getCoresFrom() {
		return coresFrom;
	}

	public void setCoresFrom(int coresFrom) {
		this.coresFrom = coresFrom;
	}

	public int getCoresTo() {
		return coresTo;
	}

	public void setCoresTo(int coresTo) {
		this.coresTo = coresTo;
	}

	public int getRamFrom() {
		return ramFrom;
	}

	public void setRamFrom(int ramFrom) {
		this.ramFrom = ramFrom;
	}

	public int getRamTo() {
		return ramTo;
	}

	public void setRamTo(int ramTo) {
		this.ramTo = ramTo;
	}

	public int getGpuFrom() {
		return gpuFrom;
	}

	public void setGpuFrom(int gpuFrom) {
		this.gpuFrom = gpuFrom;
	}

	public int getGpuTo() {
		return gpuTo;
	}

	public void setGpuTo(int gpuTo) {
		this.gpuTo = gpuTo;
	}
	
}
