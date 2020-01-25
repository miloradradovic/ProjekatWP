package model;

import java.util.ArrayList;

public class Organizacija {
	
	private String ime;
	private String opis;
	private String logo; //putanja do slike
	private ArrayList<Korisnik> korisnici;
	private ArrayList<Resurs> resursi;
	
	public Organizacija() {
		super();
		this.korisnici = new ArrayList<Korisnik>();
		this.resursi = new ArrayList<Resurs>();
	}

	public Organizacija(String ime, String opis, String logo, ArrayList<Korisnik> korisnici,
			ArrayList<Resurs> resursi) {
		super();
		this.ime = ime;
		this.opis = opis;
		this.logo = logo;
		this.korisnici = korisnici;
		this.resursi = resursi;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public ArrayList<Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(ArrayList<Korisnik> korisnici) {
		this.korisnici = korisnici;
	}

	public ArrayList<Resurs> getResursi() {
		return resursi;
	}

	public void setResursi(ArrayList<Resurs> resursi) {
		this.resursi = resursi;
	}
	
	

	
}
