package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import dto.UserDTO;
import dto.VMDTO;
import model.App;
import model.CategoryVM;
import model.User;
import model.UserType;
import model.VM;
import spark.Session;

public class SparkMain {
	
	private static Gson g;
	private static App app;

	public static void main(String[] args) {

		app = new App();
		g = new Gson();
		port(8081);
		try {
			staticFiles.externalLocation(new File("./static").getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		User u = new User();
		u.setEmail("email");
		u.setPassword("password");
		u.setUserType(UserType.SuperAdmin);
		VM vm = new VM();
		vm.setCategoryName("kategorija");
		vm.setOrganizationName("organizacija");
		vm.setResourceName("vm");
		CategoryVM c = new CategoryVM();
		c.setCategoryName("kategorija");
		c.setGPU(1);
		c.setNumberOfCores(1);
		c.setRAM(1);
		app.getUsers().add(u);
		app.getVms().add(vm);
		app.getCategories().add(c);
		
		//provjeriti da li ulogovani korisnik ima pravo na ovu stranicu
		//ako nema, vrati error 403
		//nesto = req.body() -> req.body treba da bude u obliku json-a nestoDTO
		//nestoDTO = g.fromJson(nesto, nestoDTO.class);
		//onda se iz nestoDTO napravi objekat nesto iz modela
		//provjeri argumente, ako su nevalidni vrati 400
		//ako je sve ok vrati 200
		
		//login
		post("login", (req, res)->{
			res.type("application/json");
			String user = req.body();
			UserDTO userdto = g.fromJson(user, UserDTO.class);
			User found_user = app.findUserLogIn(userdto);
			if(found_user == null) {
				res.status(400);
				return "400 Bad request";
			}
			
			Session ss = req.session(true);
			ss.attribute("user", found_user);
			res.status(200);
			if(found_user.getUserType() == UserType.SuperAdmin) {
				return "superadmin";
			}else if(found_user.getUserType() == UserType.Admin) {
				return "admin";
			}else {
				return "user";
			}
		});
		
		//getting the current logged in SUPER ADMINISTRATOR
		get("SuperAdministrator/VMs/viewVMs/getCurrentUser", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				res.status(200);
				return g.toJson(app.getCurrentLoggedInUser(req));
			}
		});
		
		//getting VMs of the current logged in SUPERADMINISTRATOR
		get("SuperAdministrator/VMs/viewVMs/getVMs", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				res.status(200);
				ArrayList<VMDTO> vms = app.getVMDTOs(app.getCurrentLoggedInUser(req));
				return g.toJson(vms);
			}
		});
		
		//deleting VM SUPERADMIN
		post("SuperAdministrator/VMs/viewVMs/deleteVM", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String vmName = req.body();
				app.deleteVM(vmName);
				res.status(200);
				return "200 OK";
			}
		});
		
		post("SuperAdministrator/VMs/viewVMs/editVM", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String virt = req.body();
				VMDTO vmdto = g.fromJson(virt, VMDTO.class);
				app.editVM(vmdto);
				res.status(200);
				return "200 OK";
			}
		});
	}

}
