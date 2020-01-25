package model;

import java.time.LocalDateTime;

public class Aktivnost {
	
	private LocalDateTime upaljena;
	private LocalDateTime ugasena;
	
	public Aktivnost() {
		super();
	}

	public Aktivnost(LocalDateTime upaljena, LocalDateTime ugasena) {
		super();
		this.upaljena = upaljena;
		this.ugasena = ugasena;
	}

	public LocalDateTime getUpaljena() {
		return upaljena;
	}

	public void setUpaljena(LocalDateTime upaljena) {
		this.upaljena = upaljena;
	}

	public LocalDateTime getUgasena() {
		return ugasena;
	}

	public void setUgasena(LocalDateTime ugasena) {
		this.ugasena = ugasena;
	}
	
	

}
