package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import dto.CategoryDTO;
import dto.DiscDTO;
import dto.OrganizationDTO;
import dto.SearchDTO;
import dto.UserDTO;
import dto.VMDTO;
import model.App;
import model.CategoryVM;
import model.Organization;
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
			if(found_user == null || userdto.getEmail().equals("") || userdto.getPassword().equals("")) {
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
		
		get("getCurrentUser", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) == 0) {
				return 0;
			}else if(app.checkLoggedInUser(req) == 1) {
				return 1;
			}else if(app.checkLoggedInUser(req) == 2) {
				return 2;
			}else {
				return 3;
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
		post("SuperAdministrator/VMs/editVM/deleteVM", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String vmName = req.body();
				int flag = app.deleteVM(vmName);
				if(flag == 0) {
					res.status(400);
					return "400 bad request";
				}
				res.status(200);
				return "200 OK";
			}
		});
		
		post("SuperAdministrator/VMs/editVM/editVM", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String virt = req.body();
				try {
					VMDTO vmdto = g.fromJson(virt, VMDTO.class);
					if(vmdto.getOldResourceName().equals(vmdto.getResourceName()) && vmdto.getOldResourceName().equals("") == false && vmdto.getResourceName().equals("") == false && vmdto.getCategoryName().equals("") == false && vmdto.getGPU() > 0 && vmdto.getRAM() > 0 && vmdto.getNumberOfCores() > 0 && vmdto.getOrganizationName().equals("") == false) {
						int flag = app.editVM(vmdto);
						if(flag == 0) {
							res.status(400);
							return "400 bad request";
						}
						res.status(200);
						return "200 OK";
					}else if(app.findVMByName(vmdto.getResourceName()) == null && vmdto.getOldResourceName().equals("") == false && vmdto.getResourceName().equals("") == false && vmdto.getCategoryName().equals("") == false && vmdto.getGPU() > 0 && vmdto.getRAM() > 0 && vmdto.getNumberOfCores() > 0 && vmdto.getOrganizationName().equals("") == false) {
						int flag = app.editVM(vmdto);
						if(flag == 0) {
							res.status(400);
							return "400 bad request";
						}
						res.status(200);
						return "200 OK";
					}else {
						res.status(400);
						return "400 Bad request";
					}
				}catch(Exception e) {
					res.status(400);
					return "400 Bad request";
				}
			}
		});
		
		post("SuperAdministrator/VMs/editVM/getVMByName", (req, res)-> {
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String virt = req.body();
				VM vm = app.findVMByName(virt);
				if(vm == null) {
					res.status(400);
					return "400 bad request";
				}
				VMDTO dto = app.convertVMtoVMDTO(vm);
				res.status(200);
				return g.toJson(dto);
			}
		});
		
		get("SuperAdministrator/VMs/editVM/getCategories",(req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				ArrayList<CategoryDTO> categoriesdto = new ArrayList<CategoryDTO>();
				ArrayList<CategoryVM> categories = app.getCategories();
				for(CategoryVM cvm : categories) {
					CategoryDTO dto = app.convertCattoCatDTO(cvm);
					categoriesdto.add(dto);
				}
				return g.toJson(categoriesdto);
				
			}
		});
		
		post("SuperAdministrator/VMs/addVM/addVM", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				try {
					VMDTO dto = g.fromJson(req.body(), VMDTO.class);
					if(dto.getResourceName().equals("") || dto.getCategoryName().equals("") || dto.getOrganizationName().equals("") || app.findVMByName(dto.getResourceName()) != null || dto.getRAM() <= 0 || dto.getGPU() <= 0 || dto.getNumberOfCores() <= 0) {
						res.status(400);
						return "400 Bad request";
					}else {
						int flag = app.addVM(dto);
						if(flag == 0) {
							res.status(400);
							return "400 bad request";
						}
						res.status(200);
						return "200 OK";
					}
				}catch(Exception e) {
					res.status(400);
					return "400 bad request";
				}
			}
		});
		
		get("SuperAdministrator/VMs/addVM/getOrganizations", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				ArrayList<OrganizationDTO> dto = new ArrayList<OrganizationDTO>();
				ArrayList<Organization> orgs = app.getOrganizations();
				for(Organization o : orgs) {
					OrganizationDTO orgdto = app.convertOrgtoOrgDTO(o);
					dto.add(orgdto);
				}
				return g.toJson(dto);
			}
		});
		
		get("SuperAdministrator/VMs/addVM/getCategories", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				ArrayList<CategoryDTO> categoriesdto = new ArrayList<CategoryDTO>();
				ArrayList<CategoryVM> categories = app.getCategories();
				for(CategoryVM cvm : categories) {
					CategoryDTO dto = app.convertCattoCatDTO(cvm);
					categoriesdto.add(dto);
				}
				return g.toJson(categoriesdto);
				
			}
		});
		
		post("SuperAdministrator/VMs/addVM/getAvailableDiscs", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				try {
					Organization o = app.findOrgByName(req.body());
					if(o == null) {
						res.status(400);
						return "400 bad request";
					}
					OrganizationDTO dto = new OrganizationDTO();
					dto.setOrgName(req.body());
					ArrayList<DiscDTO> discs = app.getAvailableDiscs(dto);
					res.status(200);
					return g.toJson(discs);
				}catch(Exception e) {
					res.status(400);
					return "400 bad request";
				}
			}
		});
		
		post("SuperAdministrator/VMs/viewVMs/searchVM", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				try {
					SearchDTO dto = g.fromJson(req.body(), SearchDTO.class);
					ArrayList<VMDTO> dtos = app.searchVM(dto);
					res.status(200);
					return g.toJson(dtos);
				}catch(Exception e) {
					res.status(400);
					return "400 bad request";
				}
			}
		});
	}

}
