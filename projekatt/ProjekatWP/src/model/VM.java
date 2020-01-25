package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class VM extends Resurs {
	
	private String ime;
	private Kategorija kategorija;
	private ArrayList<Disk> zakaceniDiskovi;
	private ArrayList<Aktivnost> aktivnost;
	
	public VM() {
		super();
		this.kategorija = new Kategorija();
		this.zakaceniDiskovi = new ArrayList<Disk>();
		this.aktivnost = new ArrayList<Aktivnost>();
	}

	public VM(String ime, Kategorija kategorija, ArrayList<Disk> zakaceniDiskovi, ArrayList<Aktivnost> aktivnost) {
		super();
		this.ime = ime;
		this.kategorija = kategorija;
		this.zakaceniDiskovi = zakaceniDiskovi;
		this.aktivnost = aktivnost;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public Kategorija getKategorija() {
		return kategorija;
	}

	public void setKategorija(Kategorija kategorija) {
		this.kategorija = kategorija;
	}

	public ArrayList<Disk> getZakaceniDiskovi() {
		return zakaceniDiskovi;
	}

	public void setZakaceniDiskovi(ArrayList<Disk> zakaceniDiskovi) {
		this.zakaceniDiskovi = zakaceniDiskovi;
	}

	public ArrayList<Aktivnost> getAktivnost() {
		return aktivnost;
	}

	public void setAktivnost(ArrayList<Aktivnost> aktivnost) {
		this.aktivnost = aktivnost;
	}
	
	

}
