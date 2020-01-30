package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class VM extends Resurs {
	
	private String ime;
	private Kategorija kategorija;
	private ArrayList<String> zakaceniDiskovi;
	private ArrayList<Aktivnost> aktivnost;
	private Organizacija organizacija;
	
	public VM() {
		super();
		this.kategorija = new Kategorija();
		this.zakaceniDiskovi = new ArrayList<String>();
		this.aktivnost = new ArrayList<Aktivnost>();
		this.organizacija = new Organizacija();
	}

	public VM(String ime, Kategorija kategorija, ArrayList<String> zakaceniDiskovi, ArrayList<Aktivnost> aktivnost, Organizacija organizacija) {
		super();
		this.ime = ime;
		this.kategorija = kategorija;
		this.zakaceniDiskovi = zakaceniDiskovi;
		this.aktivnost = aktivnost;
		this.organizacija = organizacija;
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

	public ArrayList<String> getZakaceniDiskovi() {
		return zakaceniDiskovi;
	}

	public void setZakaceniDiskovi(ArrayList<String> zakaceniDiskovi) {
		this.zakaceniDiskovi = zakaceniDiskovi;
	}

	public ArrayList<Aktivnost> getAktivnost() {
		return aktivnost;
	}

	public void setAktivnost(ArrayList<Aktivnost> aktivnost) {
		this.aktivnost = aktivnost;
	}

	public Organizacija getOrganizacija() {
		return organizacija;
	}

	public void setOrganizacija(Organizacija organizacija) {
		this.organizacija = organizacija;
	}
	
	

}
