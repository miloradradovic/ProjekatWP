package rest;


import model.Korisnik;
import model.TipKorisnika;
import model.VM;
import model.Kategorija;
import model.Aktivnost;
import model.Aplikacija;
import model.Disk;
import model.Organizacija;
import model.Resurs;
import spark.Session;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SparkMain {
	
	
	private static Gson g = new Gson();
	private static Aplikacija app;
	private static String idVMIzmena = "";
	private static VM virtuelnaEdit = new VM();
	
	public static void main(String[] args) throws IOException {
		
		app = new Aplikacija();
		
		port(80);
		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		Kategorija ka = new Kategorija("bla", 2, 3, 5);
		Organizacija org = new Organizacija();
		ArrayList<String> diskovi = new ArrayList<String>();
		for(Disk disk : app.getDiskovi()) {
			diskovi.add(disk.getIme());
		}
		VM vm = new VM("bla", ka, diskovi, app.getAk(), org);
		org.setIme("org1");
		org.getResursi().add(vm.getIme());
		app.getVirtuelne().add(vm);

		Korisnik a = new Korisnik();
		a.setKorisnickoIme("maki");
		a.setLozinka("123");
		a.setUloga(TipKorisnika.SuperAdmin);
		app.getKorisnici().add(a);
		
		post("/login", (req, res) ->{
			res.type("application/json");
			String korisnik = req.body();
			Korisnik k = g.fromJson(korisnik, Korisnik.class);
			Session ss = req.session(true);
			Korisnik u = ss.attribute("user");
			System.out.println(res);
			for(Korisnik k2 : app.getKorisnici()) {
				if(k2.getKorisnickoIme().equals(k.getKorisnickoIme()) && k2.getLozinka().equals(k.getLozinka())) {
					u = k2;
					System.out.println(u.getKorisnickoIme());
					ss.attribute("user",u);
					res.status(200);
					return true;
					
				}
			}
			res.status(200);
			return false;
		});
		
		get("/dobaviVirtuelne", (req, res) ->{
			res.type("application/json");
			Session ss = req.session(true);
			Korisnik m = ss.attribute("user");
			res.status(200);
			if(m.getUloga()==TipKorisnika.SuperAdmin) {
				return g.toJson(app.getVirtuelne());
			}else {
				res.status(200);
				ArrayList<VM> virtuelne2 = new ArrayList<VM>();
				for (Organizacija o : app.getOrganizacije()) {
					if (o.getIme().equals(m.getOrganizacija().getIme())) {
						for(String r : o.getResursi()) {
							for(VM v : app.getVirtuelne()) {
								if(v.getIme().equals(r)) {
									virtuelne2.add(v);
								}
							}
						}
					}
				}
				return g.toJson(virtuelne2);
			}
		});
		
		get("/dobaviTrenutnogKorisnika",(req,res)->{
			res.type("application/json");
			Session ss = req.session(true);
			Korisnik k = ss.attribute("user");
			res.status(200);
			if(k==null) {
				return false;
			}
			else if(k.getUloga()==TipKorisnika.SuperAdmin) {
				return 0;
			}else if(k.getUloga()==TipKorisnika.Admin) {
				return 1;
			}else{
				return 2;
			}
		});
		
		get("/dobaviSveOrganizacije",(req,res)->{
			res.type("application/json");
			Session ss = req.session(true);
			Korisnik k = ss.attribute("user");
			if(k.getUloga()==TipKorisnika.SuperAdmin) {
				return g.toJson(app.getOrganizacije());
			}else if(k.getUloga()==TipKorisnika.Admin) {
				for(Organizacija o : app.getOrganizacije()) {
					if(o.getKorisnici().contains(k.getKorisnickoIme())) {
						return g.toJson(o);
					}
				}
			}
			return false;
		});
		
		/*
		post("/dodajKorisnika",(req,res)->{
			res.type("application/json");
			Session ss = req.session(true);
			String data = req.body();
			Korisnik k = g.fromJson(data, Korisnik.class);
			
		});
		*/
		get("/dobaviVMpoID",(req,res)->{
			res.type("application/json");
			Session ss = req.session(true);
			for(VM virtuelna : app.getVirtuelne()) {
				if(virtuelna.getIme().equals(idVMIzmena)) {
					return g.toJson(virtuelna);
				}
			}
			return false;
		});
		
		post("/dobaviAktivnostiZaVM",(req,res)->{
			res.type("application/json");
			Session ss = req.session(true);
			String id = req.body();
			for(VM virtuelna2 : app.getVirtuelne()) {
				if(virtuelna2.getIme().equals(id)) {
					return g.toJson(virtuelna2.getAktivnost());
				}
			}
			return false;
		});
		
		post("/dobaviKategorije",(req,res)->{
			res.type("application/json");
			Session ss = req.session(true);
			String id = req.body();
			ArrayList<Kategorija> ks = new ArrayList<Kategorija>();
			for(Kategorija k : app.getKategorije()) {
				if(k.getIme().equals(id)) {
					ks.add(k);
				}
			}
			return g.toJson(ks);
		});
		
		post("/obrisiVM",(req,res)->{
			res.type("application/json");
			Session ss = req.session(true);
			String id = req.body();
			for(VM virtuelna : app.getVirtuelne()) {
				if(virtuelna.getIme().equals(id)) {
					app.getVirtuelne().remove(virtuelna);
					break;
				}
			}
			for(Disk d : app.getDiskovi()) {
				if(d.getVirtuelna().equals(id)) {
					d.setVirtuelna("");
					break;
				}
			}
			for(Organizacija o : app.getOrganizacije()) {
				for(String s : o.getResursi()) {
					if(s.equals(id)) {
						o.getResursi().remove(s);
						break;
					}
				}
			}
			return true;
			
		});
		
		//izmeniVM(saljes id i sacuva se id)
		post("/izmeniVM",(req,res)->{
			res.type("application/json");
			Session ss = req.session(true);
			String id = req.body();
			idVMIzmena = id;
			return true;
		});
		
		post("/dobaviOrganizacijuPoID",(req,res)->{
			res.type("application/json");
			Session ss = req.session(true);
			String id = req.body();
			for(Organizacija o : app.getOrganizacije()) {
				if(o.getIme().equals(id)) {
					return g.toJson(o);
				}
			}
			return false;
		});
		
		post("/dobaviEnumUloga",(req,res)->{
			res.type("application/json");
			Session ss = req.session(true);
			String id = req.body();
			if(id.equals("Admin")) {
				return g.toJson(TipKorisnika.Admin);
			}else {
				return g.toJson(TipKorisnika.Korisnik);
			}
		});
		
		post("/dodajKorisnika",(req,res)->{
			res.type("application/json");
			Session ss = req.session(true);
			String korisnik = req.body();
			Korisnik k = g.fromJson(korisnik, Korisnik.class);
			for(Korisnik k2 : app.getKorisnici()) {
				if(k2.getKorisnickoIme().equals(k2.getKorisnickoIme())) {
					return false;
				}
			}
			app.getKorisnici().add(k);
			for(Organizacija o : app.getOrganizacije()) {
				o.getKorisnici().add(k.getKorisnickoIme());
			}
			return true;
		});
	}

}
