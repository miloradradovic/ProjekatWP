package rest;

import java.time.LocalDateTime;

import model.Korisnik;
import model.TipKorisnika;
import model.VM;
import model.Kategorija;
import model.Aktivnost;
import model.Aplikacija;
import model.Disk;
import model.Organizacija;
import model.Resurs;
import model.TipDiska;
import spark.Session;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SparkMain {
	
	
	private static Gson g = new Gson();
	private static Aplikacija app;
	private static String idVMIzmena = "";
	
	public static void main(String[] args) throws IOException {
		
		app = new Aplikacija();
		
		port(80);
		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		Kategorija ka = new Kategorija("k1", 2, 3, 5);
		Kategorija ka2 = new Kategorija("k2", 2, 3, 5);
		Organizacija org = new Organizacija();
		
		Aktivnost a1 = new Aktivnost(LocalDateTime.now(), LocalDateTime.now());
		Aktivnost a2 = new Aktivnost(LocalDateTime.now(), LocalDateTime.now());

		app.getAk().add(a1);
		app.getAk().add(a2);
		Disk d1 = new Disk("d1", TipDiska.SSD, 12, org.getIme());
		app.getDiskovi().add(d1);
		ArrayList<String> diskovi = new ArrayList<String>();
		for(Disk disk : app.getDiskovi()) {
			diskovi.add(disk.getIme());
		}
		VM vm = new VM("vm1", ka, diskovi, app.getAk(), org);
		VM vm3 = new VM("vm2", ka2, diskovi, app.getAk(), org);
		org.setIme("org1");
		org.getResursi().add(vm.getIme());
		app.getVirtuelne().add(vm);
		org.getResursi().add(vm3.getIme());
		org.setOpis("opis");
		org.setLogo("logo");
		app.getVirtuelne().add(vm3);
		app.getKategorije().add(ka);
		app.getKategorije().add(ka2);
		


		Korisnik a = new Korisnik();
		a.setKorisnickoIme("maki");
		a.setLozinka("123");
		a.setIme("Marina");
		a.setPrezime("Gusa");
		a.setUloga(TipKorisnika.SuperAdmin);
		a.setOrganizacija(org);
		app.getKorisnici().add(a);
		org.getKorisnici().add(a.getKorisnickoIme());
		app.getOrganizacije().add(org);
		
		Korisnik kor2 = new Korisnik();
		kor2.setKorisnickoIme("mica");
		kor2.setLozinka("123");
		kor2.setIme("Milorad");
		kor2.setPrezime("Radovic");
		kor2.setUloga(TipKorisnika.Admin);
		kor2.setOrganizacija(org);
		app.getKorisnici().add(kor2);
		org.getKorisnici().add(kor2.getKorisnickoIme());
		app.getOrganizacije().add(org);
		
		Korisnik a3 = new Korisnik();
		a3.setKorisnickoIme("marko");
		a3.setLozinka("123");
		a3.setUloga(TipKorisnika.Korisnik);
		a3.setIme("Marko");
		a3.setPrezime("Cvijanovic");
		a3.setOrganizacija(org);
		app.getKorisnici().add(a3);
		org.getKorisnici().add(a3.getKorisnickoIme());
		app.getOrganizacije().add(org);
		
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
		
		get("/dobaviDiskove", (req, res) ->{
			res.type("application/json");
			Session ss = req.session(true);
			Korisnik m = ss.attribute("user");
			res.status(200);
			if(m.getUloga()==TipKorisnika.SuperAdmin) {
				return g.toJson(app.getDiskovi());
			}else {
				res.status(200);
				ArrayList<Disk> diskk = new ArrayList<Disk>();
				for (Organizacija o : app.getOrganizacije()) {
					if (o.getIme().equals(m.getOrganizacija().getIme())) {
						for(String d : o.getResursi()) {
							for(Disk dd : app.getDiskovi()) {
								if(dd.getIme().equals(d)) {
									diskk.add(dd);
								}
							}
						}
					}
				}
				return g.toJson(diskk);
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
		
		get("/dobaviKorisnike", (req, res) ->{
			res.type("application/json");
			Session ss = req.session(true);
			Korisnik m = ss.attribute("user");
			res.status(200);
			if(m.getUloga()==TipKorisnika.SuperAdmin) {
				return g.toJson(app.getKorisnici());
			}else {
				res.status(200);
				ArrayList<Korisnik> kor = new ArrayList<Korisnik>();
				for (Organizacija o : app.getOrganizacije()) {
					if (o.getIme().equals(m.getOrganizacija().getIme())) {
						kor.add(m);
					}
				}
				return g.toJson(kor);
			}
		});
		
		get("/dobaviOrganizacije", (req, res) ->{
			res.type("application/json");
			Session ss = req.session(true);
			Korisnik m = ss.attribute("user");
			res.status(200);
			return g.toJson(app.getOrganizacije());
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
			System.out.println(idVMIzmena);
			for(VM virtuelna : app.getVirtuelne()) {
				if(virtuelna.getIme().equals(idVMIzmena)) {
					System.out.println(virtuelna.getIme());
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
			String id = req.body(); //ime vm
			VM vm2 = new VM();
			for(VM vm1 : app.getVirtuelne()) {
				if(vm1.getIme().equals(id)) {
					vm2=vm1;
				}
			}
			ArrayList<Kategorija> ks = new ArrayList<Kategorija>();
			for(Kategorija k : app.getKategorije()) {
				if(k.getIme().equals(vm2.getKategorija().getIme())==false) {
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
		
		post("/obrisiDisk",(req,res)->{
			res.type("application/json");
			Session ss = req.session(true);
			String ime = req.body();
			for(Disk d : app.getDiskovi()) {
				if(d.getIme().equals(ime)) {
					app.getDiskovi().remove(d);
					break;
				}
			}
			for(VM virm : app.getVirtuelne()) {
				for(String disk : virm.getZakaceniDiskovi()) {
					if(disk.equals(ime)) {
						virm.getZakaceniDiskovi().remove(disk);
						break;
					}
				}
			}
			for(Organizacija o : app.getOrganizacije()) {
				for(String disk : o.getResursi()) {
					if(disk.equals(ime)) {
						o.getResursi().remove(disk);
						break;
					}
				}
			}
			return true;
			
		});
		
		post("/obrisiKorisnika",(req,res)->{
			res.type("application/json");
			Session ss = req.session(true);
			String korisnickoIme = req.body();
			for(Korisnik k : app.getKorisnici()) {
				if(k.getKorisnickoIme().equals(korisnickoIme)) {
					app.getKorisnici().remove(k);
					break;
				}
			}
			for(Organizacija o : app.getOrganizacije()) {
				for(String k : o.getKorisnici()) {
					if(k.equals(korisnickoIme)) {
						o.getKorisnici().remove(k);
						break;
					}
				}
			}
			return true;
			
		});
		
		post("/obrisiKategoriju",(req,res)->{
			res.type("application/json");
			Session ss = req.session(true);
			String ime = req.body();
			for (VM vir : app.getVirtuelne())
			{
				if (!vir.getKategorija().getIme().equals(ime))
				{
					for(Kategorija k : app.getKategorije()) {
						if(k.getIme().equals(ime)) {
							app.getKorisnici().remove(k);
							break;
						}
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
		
		post("/dobaviDiskoveZaVM",(req,res)->{
			res.type("application/json");
			Session ss = req.session(true);
			String id = req.body();
			ArrayList<Disk> diskovi2 = new ArrayList<Disk>();
			for(Disk d : app.getDiskovi()) {
				if(d.getVirtuelna().equals(id)) {
					diskovi2.add(d);
				}
			}
			return g.toJson(diskovi2);
		});
		
		post("/noviPodaci",(req,res)->{
			res.type("application/json");
			Session ss = req.session(true);
			String sve = req.body();
			String[] splitovano = sve.split("\"");
			System.out.println(sve);
			String novoIme = sve.split("\"")[3];
			String novaKategorija = sve.split("\"")[7];
			ArrayList<String> datumiPaljenje = new ArrayList<String>();
			int i = 11;
			while(true) {
				datumiPaljenje.add(splitovano[i]);
				if(splitovano[i+1].equals("],")) {
					break;
				}else {
					i = i + 2;
				}
			}
			ArrayList<String> datumiGasenje = new ArrayList<String>();
			int j = i+4;
			while(true) {
				datumiGasenje.add(splitovano[j]);
				if(splitovano[j+1].equals("]}")) {
					break;
				}else {
					j = j + 2;
				}
			}
			for(VM virtuelna : app.getVirtuelne()) {
				if(vm.getIme().equals(idVMIzmena)) {
					for(Kategorija k : app.getKategorije()) {
						if(k.getIme().equals(novaKategorija)) {
							virtuelna.setKategorija(k);
							break;
						}
					}
					vm.setIme(novoIme);
					
				}
			}
			
			
			System.out.println(datumiPaljenje);
			System.out.println(datumiGasenje);
			return 0;
		});
	}

}
