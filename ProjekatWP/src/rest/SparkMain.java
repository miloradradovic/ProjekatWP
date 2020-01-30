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
							//if(r.getClass()==VM.class) {
								//virtuelne2.add((model.VM) r);
							//}
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
		
	}

}
