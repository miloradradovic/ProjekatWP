package model;

public class Disk extends Resurs {
	
	private String ime;
	private TipDiska tip;
	private int kapacitet;
	private VM virtuelna;
	
	public Disk() {
		super();
		this.virtuelna = new VM();
	}

	public Disk(String ime, TipDiska tip, int kapacitet, VM virtuelna) {
		super();
		this.ime = ime;
		this.tip = tip;
		this.kapacitet = kapacitet;
		this.virtuelna = virtuelna;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public TipDiska getTip() {
		return tip;
	}

	public void setTip(TipDiska tip) {
		this.tip = tip;
	}

	public int getKapacitet() {
		return kapacitet;
	}

	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}

	public VM getVirtuelna() {
		return virtuelna;
	}

	public void setVirtuelna(VM virtuelna) {
		this.virtuelna = virtuelna;
	}
	
	

}