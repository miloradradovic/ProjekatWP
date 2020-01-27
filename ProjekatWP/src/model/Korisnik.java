package model;

public class Korisnik {
	
	private String korisnickoIme;
	private String lozinka;
	private String ime;
	private String prezime;
	private Organizacija organizacija;
	private TipKorisnika uloga;
	
	public Korisnik() {
		super();
		this.organizacija = new Organizacija();
	}

	public Korisnik(String korisnickoIme, String lozinka, String ime, String prezime, Organizacija organizacija,
			TipKorisnika uloga) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.organizacija = organizacija;
		this.uloga = uloga;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Organizacija getOrganizacija() {
		return organizacija;
	}

	public void setOrganizacija(Organizacija organizacija) {
		this.organizacija = organizacija;
	}

	public TipKorisnika getUloga() {
		return uloga;
	}

	public void setUloga(TipKorisnika uloga) {
		this.uloga = uloga;
	}

	
	
}
