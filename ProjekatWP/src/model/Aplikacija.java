package model;

import java.util.ArrayList;

public class Aplikacija {
	
	private ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();
	private ArrayList<VM> virtuelne = new ArrayList<VM>();
	private ArrayList<Disk> diskovi = new ArrayList<Disk>();
	private ArrayList<Aktivnost> ak = new ArrayList<Aktivnost>();
	private ArrayList<Organizacija> organizacije = new ArrayList<Organizacija>();
	private ArrayList<Kategorija> kategorije = new ArrayList<Kategorija>();
	
	public Aplikacija() {
		this.setKorisnici(new ArrayList<Korisnik>());
		this.setVirtuelne(new ArrayList<VM>());
		this.setDiskovi(new ArrayList<Disk>());
		this.setAk(new ArrayList<Aktivnost>());
		this.setOrganizacije(new ArrayList<Organizacija>());
		this.setKategorije(new ArrayList<Kategorija>());
	}

	public ArrayList<Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(ArrayList<Korisnik> korisnici) {
		this.korisnici = korisnici;
	}

	public ArrayList<VM> getVirtuelne() {
		return virtuelne;
	}

	public void setVirtuelne(ArrayList<VM> virtuelne) {
		this.virtuelne = virtuelne;
	}

	public ArrayList<Disk> getDiskovi() {
		return diskovi;
	}

	public void setDiskovi(ArrayList<Disk> diskovi) {
		this.diskovi = diskovi;
	}

	public ArrayList<Aktivnost> getAk() {
		return ak;
	}

	public void setAk(ArrayList<Aktivnost> ak) {
		this.ak = ak;
	}

	public ArrayList<Organizacija> getOrganizacije() {
		return organizacije;
	}

	public void setOrganizacije(ArrayList<Organizacija> organizacije) {
		this.organizacije = organizacije;
	}

	public ArrayList<Kategorija> getKategorije() {
		return kategorije;
	}

	public void setKategorije(ArrayList<Kategorija> kategorije) {
		this.kategorije = kategorije;
	}
	
	

}
