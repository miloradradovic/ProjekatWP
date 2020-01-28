package rest;

import com.google.gson.Gson;

import model.Korisnik;
import model.TipKorisnika;
import model.VM;
import model.Kategorija;
import model.Aktivnost;
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

public class SparkMain {
	
	private static Gson g = new Gson();
	//Ovdje ce da idu liste i mape koje ce da se ucitavaju iz fajla
	private static ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();
	private static ArrayList<VM> VM = new ArrayList<VM>();
	private static ArrayList<Disk> diskovi = new ArrayList<Disk>();
	private static ArrayList<Aktivnost> ak = new ArrayList<Aktivnost>();
	private static ArrayList<Organizacija> organizacije = new ArrayList<Organizacija>();
	private static ArrayList<Kategorija> kategorije = new ArrayList<Kategorija>();
	
	
	public static void main(String[] args) throws IOException {
		
		port(80);
		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		Kategorija ka = new Kategorija("bla", 2, 3, 5);
		VM vm = new VM("bla", ka, diskovi, ak);
		VM.add(vm);

		Korisnik a = new Korisnik();
		a.setKorisnickoIme("maki");
		a.setLozinka("123");
		korisnici.add(a);
		
		post("/login", (req, res) ->{
			res.type("application/json");
			String korisnik = req.body();
			Korisnik k = g.fromJson(korisnik, Korisnik.class);
			
			for(Korisnik k2 : korisnici) {
				if(k2.getKorisnickoIme().equals(k.getKorisnickoIme()) && k2.getLozinka().equals(k.getLozinka())) {
					Session ss = req.session(true);
					ss.attribute("user",k2);
					res.status(200);
					return true;
					
				}
			}
			res.status(200);
			return false;
		});
		
		get("/pregledVM", (req, res) ->{
			res.type("application/json");
			Session ss = req.session(true);
			Korisnik m = ss.attribute("user");
			if(m.getUloga()==TipKorisnika.SuperAdmin) {
				res.status(200);
				return g.toJson(VM);
			}else {
				res.status(200);
				ArrayList<VM> virtuelne = new ArrayList<VM>();
				for (Organizacija o : organizacije) {
					if (o.getIme().equals(m.getOrganizacija().getIme())) {
						for(Resurs r : o.getResursi()) {
							if(r.getClass()==VM.class) {
								virtuelne.add((model.VM) r);
							}
						}
					}
				}
				return g.toJson(virtuelne);
			}
		}
	);
		
		get("/dobaviTrenutnogKorisnika",(req,res)->{
			res.type("application/json");
			Session ss = req.session(true);
			Korisnik k = ss.attribute("user");
			res.status(200);
			if(k.getUloga()==TipKorisnika.SuperAdmin) {
				return 0;
			}else if(k.getUloga()==TipKorisnika.Admin) {
				return 1;
			}else if(k.getUloga()==TipKorisnika.Korisnik) {
				return 2;
			}else {
				return false;
			}
		});
		
	}

}
