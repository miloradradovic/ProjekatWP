package rest;

import com.google.gson.Gson;

import model.Korisnik;
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
	
	public static void main(String[] args) throws IOException {
		
		port(80);
		staticFiles.externalLocation(new File("./static").getCanonicalPath());

		
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
			return false;
		});
	}

}
