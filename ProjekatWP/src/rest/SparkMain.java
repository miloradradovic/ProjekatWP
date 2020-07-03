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
import model.Disc;
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
		
		
		get("logout", (req, res)->{
			res.type("application/json");
			Session s = req.session(false);
			s.invalidate();
			res.status(200);
			return "200 OK";
		});
		
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
		
		get("SuperAdministrator/Categories/viewCategories/getCategories", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				res.status(200);
				ArrayList<CategoryDTO> c = app.getCategoryDTOs();
				return g.toJson(c);
			}
		});
		
		get("SuperAdministrator/Organizations/viewOrganizations/getOrganizations", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				res.status(200);
				ArrayList<OrganizationDTO> c = app.getOrganizationDTOs();
				return g.toJson(c);
			}
		});
		
		//getting discs of the current logged in SUPERADMINISTRATOR
		get("SuperAdministrator/Discs/viewDiscs/getDiscs", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				res.status(200);
				ArrayList<DiscDTO> discs = app.getDiscDTOs(app.getCurrentLoggedInUser(req));
				return g.toJson(discs);
			}
		});
		
		get("Administrator/Discs/viewDiscs/getDiscs", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				res.status(200);
				ArrayList<DiscDTO> discs = app.getDiscDTOs(app.getCurrentLoggedInUser(req));
				return g.toJson(discs);
			}
		});
		
		get("User/Discs/viewDiscs/getDiscs", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 1) {
				res.status(403);
				return "403 Not authorized";
			}else {
				res.status(200);
				ArrayList<DiscDTO> discs = app.getDiscDTOs(app.getCurrentLoggedInUser(req));
				return g.toJson(discs);
			}
		});
		
		post("SuperAdministrator/Discs/addDisc/getAvailableVMs", (req, res)->{
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
					ArrayList<VMDTO> vms = app.getAvailableVMs(dto);
					res.status(200);
					return g.toJson(vms);
				}catch(Exception e) {
					res.status(400);
					return "400 bad request";
				}
			}
		});
		
		post("Administrator/Discs/addDisc/getAvailableVMs", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				try {
					OrganizationDTO dto = app.convertOrgtoOrgDTO(app.findOrgByName(app.getCurrentLoggedInUser(req).getOrganizationName()));
					ArrayList<VMDTO> vms = app.getAvailableVMs(dto);
					res.status(200);
					return g.toJson(vms);
				}catch(Exception e) {
					res.status(400);
					return "400 bad request";
				}
			}
		});
		
		post("SuperAdministrator/Discs/editDisc/getAvailableVMs", (req, res)->{
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
					ArrayList<VMDTO> vms = app.getAvailableVMs(dto);
					res.status(200);
					return g.toJson(vms);
				}catch(Exception e) {
					res.status(400);
					return "400 bad request";
				}
			}
		});
		
		post("Administrator/Discs/editDisc/getAvailableVMs", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				try {
					OrganizationDTO dto = app.convertOrgtoOrgDTO(app.findOrgByName(app.getCurrentLoggedInUser(req).getOrganizationName()));
					dto.setOrgName(req.body());
					ArrayList<VMDTO> vms = app.getAvailableVMs(dto);
					res.status(200);
					return g.toJson(vms);
				}catch(Exception e) {
					res.status(400);
					return "400 bad request";
				}
			}
		});
		
		
		//getting VMs of the current logged in ADMINISTRATOR
		get("Administrator/VMs/viewVMs/getVMs", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				res.status(200);
				ArrayList<VMDTO> vms = app.getVMDTOs(app.getCurrentLoggedInUser(req));
				return g.toJson(vms);
			}
		});
		
		//getting VMs of the current logged in USER
		get("User/VMs/viewVMs/getVMs", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 1) {
				res.status(403);
				return "403 Not authorized";
			}else {
				ArrayList<VMDTO> vms = app.getVMDTOs(app.getCurrentLoggedInUser(req));
				res.status(200);
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
				app.writeToFiles();
				res.status(200);
				return "200 OK";
			}
		});
		
		post("SuperAdministrator/Discs/editDisc/deleteDisc", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String dName = req.body();
				int flag = app.deleteDisc(dName);
				if(flag == 0) {
					res.status(400);
					return "400 bad request";
				}
				app.writeToFiles();
				res.status(200);
				return "200 OK";
			}
		});
		
		post("SuperAdministrator/Categories/editCategory/deleteCategory", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String c = req.body();
				int flag = app.deleteCategory(c);
				if(flag == 0) {
					res.status(400);
					return "400 bad request";
				}
				app.writeToFiles();
				res.status(200);
				return "200 OK";
			}
		});
		
		
		post("Administrator/VMs/editVM/deleteVM", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String vmName = req.body();
				int flag = app.deleteVM(vmName);
				if(flag == 0) {
					res.status(400);
					return "400 bad request";
				}
				app.writeToFiles();
				res.status(200);
				return "200 OK";
			}
		});
		
		post("Administrator/Users/editUser/deleteUser", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String userEmail = req.body();
				int flag = app.deleteUser(userEmail);
				if(flag == 0) {
					res.status(400);
					return "400 bad request";
				}else {
					app.writeToFiles();
					res.status(200);
					return "200 OK";
				}
			}
		});
		
		post("SuperAdministrator/Users/editUser/deleteUser", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String userEmail = req.body();
				int flag = app.deleteUser(userEmail);
				if(flag == 0) {
					res.status(400);
					return "400 bad request";
				}
				app.writeToFiles();
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
						app.writeToFiles();
						res.status(200);
						return "200 OK";
					}else if(app.findVMByName(vmdto.getResourceName()) == null && vmdto.getOldResourceName().equals("") == false && vmdto.getResourceName().equals("") == false && vmdto.getCategoryName().equals("") == false && vmdto.getGPU() > 0 && vmdto.getRAM() > 0 && vmdto.getNumberOfCores() > 0 && vmdto.getOrganizationName().equals("") == false) {
						int flag = app.editVM(vmdto);
						if(flag == 0) {
							res.status(400);
							return "400 bad request";
						}
						app.writeToFiles();
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
		
		post("SuperAdministrator/Discs/editDisc/editDisc", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String d = req.body();
				try {
					DiscDTO discdto = g.fromJson(d, DiscDTO.class);
					if(discdto.getOldResourceName().equals(discdto.getResourceName()) && discdto.getOldResourceName().equals("") == false && discdto.getResourceName().equals("") == false && discdto.getCapacity() > 0 && discdto.getOrganizationName().equals("") == false  && discdto.getType().equals("") == false) {
						int flag = app.editDisc(discdto);
						if(flag == 0) {
							res.status(400);
							return "400 bad request";
						}
						app.writeToFiles();
						res.status(200);
						return "200 OK";
					}else if(app.findDiscByName(discdto.getResourceName()) == null && discdto.getOldResourceName().equals("") == false && discdto.getResourceName().equals("") == false  && discdto.getCapacity() > 0 && discdto.getOrganizationName().equals("") == false && discdto.getType().equals("") == false) {
						int flag = app.editDisc(discdto);
						if(flag == 0) {
							res.status(400);
							return "400 bad request";
						}
						app.writeToFiles();
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
		
		post("SuperAdministrator/Categories/editCategory/editCategory", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String c = req.body();
				try {
					CategoryDTO cdto = g.fromJson(c, CategoryDTO.class);
					System.out.println(cdto.getOldCategoryName() + ", " + cdto.getCategoryName() + ", " + cdto.getNumberOfCores() + ", " + cdto.getGPU() + ", " + cdto.getRAM());
					if(cdto.getOldCategoryName().equals(cdto.getCategoryName()) && cdto.getOldCategoryName().equals("") == false && cdto.getCategoryName().equals("") == false && cdto.getNumberOfCores() > 0 && cdto.getRAM() > 0 && cdto.getGPU() > 0) {
						int flag = app.editCategory(cdto);
						if(flag == 0) {
							res.status(400);
							System.out.println("1");
							return "400 bad request";
						}
						app.writeToFiles();
						res.status(200);
						return "200 OK";
					}else if(app.findCatByName(cdto.getCategoryName()) == null && cdto.getOldCategoryName().equals("") == false && cdto.getCategoryName().equals("") == false  && cdto.getNumberOfCores() > 0 && cdto.getRAM() > 0 && cdto.getGPU() > 0) {
						int flag = app.editCategory(cdto);
						if(flag == 0) {
							res.status(400);
							System.out.println("2");
							return "400 bad request";
						}
						app.writeToFiles();
						res.status(200);
						return "200 OK";
					}else {
						res.status(400);
						System.out.println("3");
						return "400 Bad request";
					}
				}catch(Exception e) {
					res.status(400);
					System.out.println("4");
					return "400 Bad request";
				}
			}
		});
		
		post("SuperAdministrator/Organizations/editOrganization/editOrganization", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String o = req.body();
				try {
					OrganizationDTO odto = g.fromJson(o, OrganizationDTO.class);
					System.out.println(odto.getOldOrgName() + ", " + odto.getOrgName() + ", " + odto.getDescription() + ", " + odto.getLogo());
					if(odto.getOldOrgName().equals(odto.getOrgName()) && odto.getOldOrgName().equals("") == false && odto.getDescription().equals("") && odto.getLogo().equals("")) {
						int flag = app.editOrganization(odto);
						if(flag == 0) {
							res.status(400);
							System.out.println("1");
							return "400 bad request";
						}
						app.writeToFiles();
						res.status(200);
						return "200 OK";
					}else if(app.findOrgByName(odto.getOrgName()) == null && odto.getOldOrgName().equals("") == false && odto.getOrgName().equals("") == false  && odto.getDescription().equals("") == false && odto.getLogo().equals("") == false) {
						int flag = app.editOrganization(odto);
						if(flag == 0) {
							res.status(400);
							System.out.println("2");
							return "400 bad request";
						}
						app.writeToFiles();
						res.status(200);
						return "200 OK";
					}else {
						res.status(400);
						System.out.println("3");
						return "400 Bad request";
					}
				}catch(Exception e) {
					res.status(400);
					System.out.println("4");
					return "400 Bad request";
				}
			}
		});
		
		post("Administrator/Organizations/editOrganization/editOrganization", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String o = req.body();
				try {
					OrganizationDTO odto = g.fromJson(o, OrganizationDTO.class);
					System.out.println(odto.getOldOrgName() + ", " + odto.getOrgName() + ", " + odto.getDescription() + ", " + odto.getLogo());
					if(odto.getOldOrgName().equals(odto.getOrgName()) && odto.getOldOrgName().equals("") == false && odto.getDescription().equals("") == false) {
						if(odto.getLogo().equals("")) {
							odto.setLogo(app.getDefaultLogo());
						}
						int flag = app.editOrganization(odto);
						if(flag == 0) {
							res.status(400);
							System.out.println("1");
							return "400 bad request";
						}
						app.writeToFiles();
						res.status(200);
						return "200 OK";
					}else if(app.findOrgByName(odto.getOrgName()) == null && odto.getOldOrgName().equals("") == false && odto.getOrgName().equals("") == false  && odto.getDescription().equals("") == false) {
						if(odto.getLogo().equals("")) {
							odto.setLogo(app.getDefaultLogo());
						}
						int flag = app.editOrganization(odto);
						if(flag == 0) {
							res.status(400);
							System.out.println("2");
							return "400 bad request";
						}
						app.writeToFiles();
						res.status(200);
						return "200 OK";
					}else {
						res.status(400);
						System.out.println("3");
						return "400 Bad request";
					}
				}catch(Exception e) {
					res.status(400);
					System.out.println("4");
					return "400 Bad request";
				}
			}
		});
		
		post("Administrator/Discs/editDisc/editDisc", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String d = req.body();
				try {
					DiscDTO discdto = g.fromJson(d, DiscDTO.class);
					if(discdto.getOldResourceName().equals(discdto.getResourceName()) && discdto.getOldResourceName().equals("") == false && discdto.getResourceName().equals("") == false && discdto.getCapacity() > 0 && discdto.getOrganizationName().equals("") == false  && discdto.getType().equals("") == false) {
						int flag = app.editDisc(discdto);
						if(flag == 0) {
							res.status(400);
							return "400 bad request";
						}
						app.writeToFiles();
						res.status(200);
						return "200 OK";
					}else if(app.findDiscByName(discdto.getResourceName()) == null && discdto.getOldResourceName().equals("") == false && discdto.getResourceName().equals("") == false  && discdto.getCapacity() > 0 && discdto.getOrganizationName().equals("") == false && discdto.getType().equals("") == false) {
						int flag = app.editDisc(discdto);
						if(flag == 0) {
							res.status(400);
							return "400 bad request";
						}
						app.writeToFiles();
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
		
		post("Administrator/VMs/editVM/editVM", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
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
						app.writeToFiles();
						res.status(200);
						return "200 OK";
					}else if(app.findVMByName(vmdto.getResourceName()) == null && vmdto.getOldResourceName().equals("") == false && vmdto.getResourceName().equals("") == false && vmdto.getCategoryName().equals("") == false && vmdto.getGPU() > 0 && vmdto.getRAM() > 0 && vmdto.getNumberOfCores() > 0 && vmdto.getOrganizationName().equals("") == false) {
						int flag = app.editVM(vmdto);
						if(flag == 0) {
							res.status(400);
							return "400 bad request";
						}
						app.writeToFiles();
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
		
		post("Administrator/Users/editUser/editUser", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String user = req.body();
				UserDTO dto = g.fromJson(user, UserDTO.class);
				//uradi provjere
				int flag = app.editUser(dto);
				if(flag == 0) {
					res.status(400);
					return "400 Bad request";
				}else {
					app.writeToFiles();
					res.status(200);
					return "200 OK";
				}
			}
		});
		
		post("SuperAdministrator/Users/editUser/editUser", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String user = req.body();
				try {
					UserDTO dto = g.fromJson(user, UserDTO.class);
					if(dto.getPassword().length() < 8 || dto.getEmail().equals("") || dto.getName().equals("") || dto.getOrganizationName().equals("") || dto.getPassword().equals("") || dto.getSurname().equals("") || dto.getUserType().equals("") || app.checkStringLetters(dto.getName()) == false || app.checkStringLetters(dto.getSurname()) == false) {
						res.status(400);
						return "400 Bad request";
					}else {
						int flag = app.editUser(dto);
						if(flag == 0) {
							res.status(400);
							return "400 Bad request";
						}
						app.writeToFiles();
						res.status(200);
						return "200 OK";
					}
				}catch(Exception e) {
					e.printStackTrace();
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
		
		post("SuperAdministrator/Discs/editDisc/getDiscByName", (req, res)-> {
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String d = req.body();
				Disc disc = app.findDiscByName(d);
				if(disc == null) {
					res.status(400);
					return "400 bad request";
				}
				DiscDTO dto = app.convertDisctoDiscDTO(disc);
				res.status(200);
				return g.toJson(dto);
			}
		});
		
		post("SuperAdministrator/Categories/editCategory/getCategoryByName", (req, res)-> {
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String c = req.body();
				CategoryVM cat = app.findCatByName(c);
				if(cat == null) {
					res.status(400);
					return "400 bad request";
				}
				CategoryDTO dto = app.convertCattoCatDTO(cat);
				res.status(200);
				return g.toJson(dto);
			}
		});
		
		post("SuperAdministrator/Organizations/editOrganization/getOrganizationByName", (req, res)-> {
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String o = req.body();
				Organization org = app.findOrgByName(o);
				if(org == null) {
					res.status(400);
					return "400 bad request";
				}
				OrganizationDTO dto = app.convertOrgtoOrgDTO(org);
				res.status(200);
				return g.toJson(dto);
			}
		});
		
		post("Administrator/Discs/editDisc/getDiscByName", (req, res)-> {
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String d = req.body();
				Disc disc = app.findDiscByName(d);
				if(disc == null) {
					res.status(400);
					return "400 bad request";
				}
				DiscDTO dto = app.convertDisctoDiscDTO(disc);
				res.status(200);
				return g.toJson(dto);
			}
		});
		
		post("User/Discs/editDisc/getDiscByName", (req, res)-> {
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 1) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String d = req.body();
				Disc disc = app.findDiscByName(d);
				if(disc == null) {
					res.status(400);
					return "400 bad request";
				}
				DiscDTO dto = app.convertDisctoDiscDTO(disc);
				res.status(200);
				return g.toJson(dto);
			}
		});
		
		post("Administrator/VMs/editVM/getVMByName", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
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
		
		post("User/VMs/editVM/getVMByName", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 1) {
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
		
		get("Administrator/VMs/editVM/getCategories", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
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
						app.writeToFiles();
						res.status(200);
						return "200 OK";
					}
				}catch(Exception e) {
					res.status(400);
					return "400 bad request";
				}
			}
		});
		
		post("SuperAdministrator/Discs/addDisc/addDisc", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				try {
					DiscDTO dto = g.fromJson(req.body(), DiscDTO.class);
					if(dto.getResourceName().equals("") || dto.getType().equals("") || dto.getOrganizationName().equals("") || app.findDiscByName(dto.getResourceName()) != null) {
						res.status(400);
						return "400 Bad request";
					}else {
						int flag = app.addDisc(dto);
						if(flag == 0) {
							res.status(400);
							return "400 bad request";
						}
						app.writeToFiles();
						res.status(200);
						return "200 OK";
					}
				}catch(Exception e) {
					res.status(400);
					return "400 bad request";
				}
			}
		});
		
		post("SuperAdministrator/Categories/addCategory/addCategory", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				try {
					CategoryDTO dto = g.fromJson(req.body(), CategoryDTO.class);
					System.out.println(dto.getCategoryName() + ", " + dto.getNumberOfCores() + ", " + dto.getRAM() + ", " + dto.getGPU());
					if(dto.getCategoryName().equals("") || dto.getNumberOfCores() <= 0 || dto.getRAM() <= 0 || dto.getGPU() <= 0 || app.findCatByName(dto.getCategoryName()) != null) {
						res.status(400);
						System.out.println("1");
						return "400 Bad request";
					}else {
						int flag = app.addCategory(dto);
						if(flag == 0) {
							res.status(400);
							System.out.println("2");
							return "400 bad request";
						}
						app.writeToFiles();
						res.status(200);
						return "200 OK";
					}
				}catch(Exception e) {
					res.status(400);
					System.out.println("3");
					return "400 bad request";
				}
			}
		});
		
		post("SuperAdministrator/Organizations/addOrganization/addOrganization", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				try {
					OrganizationDTO dto = g.fromJson(req.body(), OrganizationDTO.class);
					if(dto.getOrgName().equals("") || dto.getDescription().equals("") || app.findOrgByName(dto.getOrgName()) != null) {
						res.status(400);
						System.out.println("1");
						return "400 Bad request";
					}else {
						if(dto.getLogo().equals("")) {
							dto.setLogo(app.getDefaultLogo());
						}
						int flag = app.addOrganization(dto);
						if(flag == 0) {
							res.status(400);
							System.out.println("2");
							return "400 bad request";
						}
						app.writeToFiles();
						res.status(200);
						return "200 OK";
					}
				}catch(Exception e) {
					res.status(400);
					System.out.println("3");
					return "400 bad request";
				}
			}
		});
		
		post("Administrator/Discs/addDisc/addDisc", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				try {
					DiscDTO dto = g.fromJson(req.body(), DiscDTO.class);
					if(dto.getResourceName().equals("") || dto.getType().equals("") || dto.getOrganizationName().equals("") || app.findDiscByName(dto.getResourceName()) != null) {
						res.status(400);
						System.out.println("1");
						return "400 Bad request";
					}else {
						int flag = app.addDisc(dto);
						if(flag == 0) {
							res.status(400);
							System.out.println("2");
							return "400 bad request";
						}
						app.writeToFiles();
						res.status(200);
						return "200 OK";
					}
				}catch(Exception e) {
					res.status(400);
					System.out.println("3ne cu");
					return "400 bad request";
				}
			}
		});
		
		
		get("SuperAdministrator/Users/addUser/getOrganizations", (req, res)->{
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
		
		get("SuperAdministrator/Discs/addDisc/getOrganizations", (req, res)->{
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
		
		get("SuperAdministrator/Discs/addDisc/getVMs", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				ArrayList<VMDTO> vmsdto = new ArrayList<VMDTO>();
				ArrayList<VM> vms = app.getVms();
				for(VM vm : vms) {
					VMDTO dto = app.convertVMtoVMDTO(vm);
					vmsdto.add(dto);
				}
				return g.toJson(vmsdto);
				
			}
		});
		
		get("Administrator/Discs/addDisc/getVMs", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				ArrayList<VMDTO> vmsdto = new ArrayList<VMDTO>();
				ArrayList<VM> vms = app.getVms();
				for(VM vm : vms) {
					VMDTO dto = app.convertVMtoVMDTO(vm);
					vmsdto.add(dto);
				}
				return g.toJson(vmsdto);
				
			}
		});
		
		post("Administrator/Users/addUser/addUser", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				UserDTO dto = g.fromJson(req.body(), UserDTO.class);
				//uraditi provjeru
				int flag = app.addUser(dto);
				if(flag == 0) {
					res.status(400);
					return "400 bad request";
				}else {
					app.writeToFiles();
					res.status(200);
					return "200 OK";
				}
			}
		});
		
		post("SuperAdministrator/Users/addUser/addUser", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				try {
					UserDTO dto = g.fromJson(req.body(), UserDTO.class);
					//uraditi provjeru
					int flag = app.addUser(dto);
					if(flag == 0) {
						res.status(400);
						return "400 bad request";
					}else {
						app.writeToFiles();
						res.status(200);
						return "200 OK";
					}
				}catch(Exception e) {
					res.status(400);
					return "400 Bad request";
				}
			}
		});
		
		post("Administrator/VMs/addVM/addVM", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
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
						app.writeToFiles();
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
		
		get("Administrator/VMs/addVM/getCategories", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
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
		
		get("Administrator/VMs/addVM/getDiscs", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				ArrayList<DiscDTO> discs = app.getDiscs(app.getCurrentLoggedInUser(req));
				res.status(200);
				return g.toJson(discs);
			}
		});
		
		get("Administrator/Users/viewUsers/getUsers", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				ArrayList<UserDTO> users = app.getUserDTOs(app.getCurrentLoggedInUser(req));
				res.status(200);
				return g.toJson(users);
			}
		});
		
		get("Administrator/VMs/addVM/getOrganization", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				OrganizationDTO dto = app.convertOrgtoOrgDTO(app.findOrgByName(app.getCurrentLoggedInUser(req).getOrganizationName()));
				return g.toJson(dto);
			}
		});
		
		get("Administrator/Organizations/editOrganization/getOrganization", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				OrganizationDTO dto = app.convertOrgtoOrgDTO(app.findOrgByName(app.getCurrentLoggedInUser(req).getOrganizationName()));
				return g.toJson(dto);
			}
		});
		
		get("Administrator/Discs/addDisc/getOrganization", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				OrganizationDTO dto = app.convertOrgtoOrgDTO(app.findOrgByName(app.getCurrentLoggedInUser(req).getOrganizationName()));
				return g.toJson(dto);
			}
		});
		
		get("Administrator/Users/addUser/getOrganization", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				OrganizationDTO dto = app.convertOrgtoOrgDTO(app.findOrgByName(app.getCurrentLoggedInUser(req).getOrganizationName()));
				return g.toJson(dto);
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
					ArrayList<VMDTO> dtos = app.searchVM(dto, app.getCurrentLoggedInUser(req));
					res.status(200);
					return g.toJson(dtos);
				}catch(Exception e) {
					res.status(400);
					return "400 bad request";
				}
			}
		});
		
		post("Administrator/VMs/viewVMs/searchVM", (req, res)->{
			res.type("application/json");
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				try {
					SearchDTO dto = g.fromJson(req.body(), SearchDTO.class);
					ArrayList<VMDTO> dtos = app.searchVM(dto, app.getCurrentLoggedInUser(req));
					res.status(200);
					return g.toJson(dtos);
				}catch(Exception e) {
					res.status(400);
					return "400 bad request";
				}
			}
		});
		
		post("User/VMs/viewVMs/searchVM", (req, res)->{
			res.type("application/json");
			if(app.checkLoggedInUser(req) != 1) {
				res.status(403);
				return "403 Not authorized";
			}else {
				try {
					SearchDTO dto = g.fromJson(req.body(), SearchDTO.class);
					ArrayList<VMDTO> dtos = app.searchVM(dto, app.getCurrentLoggedInUser(req));
					res.status(200);
					return g.toJson(dtos);
				}catch(Exception e) {
					res.status(400);
					return "400 bad request";
				}
			}
		});
		
		get("SuperAdministrator/Users/viewUsers/getUsers", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				ArrayList<UserDTO> dtos = app.getUserDTOs(app.getCurrentLoggedInUser(req));
				res.status(200);
				return g.toJson(dtos);
			}
		});
		
		post("Administrator/Users/editUser/getUserByEmail", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String user = req.body();
				User found = app.findUserByEmail(user);
				if(found == null) {
					res.status(400);
					return "400 Bad request";
				}else {
					UserDTO dto = app.convertUserToUserDTO(found);
					return g.toJson(dto);
				}
			}
		});
		
		get("SuperAdministrator/Profile/editProfile/getUserByEmail", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				UserDTO dto = app.convertUserToUserDTO(app.getCurrentLoggedInUser(req));
				return g.toJson(dto);
			}
		});
		
		get("Administrator/Profile/editProfile/getUserByEmail", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				UserDTO dto = app.convertUserToUserDTO(app.getCurrentLoggedInUser(req));
				return g.toJson(dto);
			}
		});
		
		get("User/Profile/editProfile/getUserByEmail", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 1) {
				res.status(403);
				return "403 Not authorized";
			}else {
				UserDTO dto = app.convertUserToUserDTO(app.getCurrentLoggedInUser(req));
				return g.toJson(dto);
			}
		});
		
		post("SuperAdministrator/Profile/editProfile/editProfile", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 not authorized";
			}else {
				String profile = req.body();
				UserDTO dto = g.fromJson(profile, UserDTO.class);
				//uradi provjere
				int flag = app.editProfile(dto);
				if(flag == 0) {
					res.status(400);
					return "400 bad request";
				}else {
					app.writeToFiles();
					res.status(200);
					return "200 OK";
				}
			}
		});
		
		post("Administrator/Profile/editProfile/editProfile", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 2) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String profile = req.body();
				UserDTO dto = g.fromJson(profile, UserDTO.class);
				//uradi provjere
				int flag = app.editProfile(dto);
				if(flag == 0) {
					res.status(400);
					return "400 bad request";
				}else {
					app.writeToFiles();
					res.status(200);
					return "200 OK";
				}
			}
		});
		
		post("User/Profile/editProfile/editProfile", (req, res)->{
			res.type("application/json");
			
			if(app.checkLoggedInUser(req) != 1) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String profile = req.body();
				UserDTO dto = g.fromJson(profile, UserDTO.class);
				//uradi provjere
				int flag = app.editProfile(dto);
				if(flag == 0) {
					res.status(400);
					return "400 bad request";
				}else {
					app.writeToFiles();
					res.status(200);
					return "200 OK";
				}
			}
		});
		
		post("SuperAdministrator/Users/editUser/getUserByEmail", (req, res)->{
			res.type("application/json");
			if(app.checkLoggedInUser(req) != 3) {
				res.status(403);
				return "403 Not authorized";
			}else {
				String user = req.body();
				User found = app.findUserByEmail(user);
				if(found == null) {
					res.status(400);
					return "400 Bad request";
				}
				UserDTO dto = app.convertUserToUserDTO(found);
				return g.toJson(dto);
			}
		});
		
		
	}

}
