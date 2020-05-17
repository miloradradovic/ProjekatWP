package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import dto.UserDTO;
import dto.VMDTO;
import spark.Request;

public class App {
	
	private ArrayList<User> users;
	private ArrayList<VM> vms;
	private ArrayList<Disc> discs;
	private ArrayList<Organization> organizations;
	private ArrayList<CategoryVM> categories;
	
	public App() {
		super();
		this.users = new ArrayList<User>();
		this.vms = new ArrayList<VM>();
		this.discs = new ArrayList<Disc>();
		this.organizations = new ArrayList<Organization>();
		this.categories = new ArrayList<CategoryVM>();
		//TODO napraviti metode za citanje i upis u json fajlove
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public ArrayList<VM> getVms() {
		return vms;
	}

	public void setVms(ArrayList<VM> vms) {
		this.vms = vms;
	}

	public ArrayList<Disc> getDiscs() {
		return discs;
	}

	public void setDiscs(ArrayList<Disc> discs) {
		this.discs = discs;
	}

	public ArrayList<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(ArrayList<Organization> organizations) {
		this.organizations = organizations;
	}

	public ArrayList<CategoryVM> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<CategoryVM> categories) {
		this.categories = categories;
	}

	public User getCurrentLoggedInUser(Request req) {
		User current = req.session().attribute("user");
		return current;
	}
	
	public int checkLoggedInUser(Request req) {
		
		User current = this.getCurrentLoggedInUser(req);
		if(current == null) {
			return 0;
		}else if(current.getUserType() == UserType.User) {
			return 1;
		}else if(current.getUserType() == UserType.Admin) {
			return 2;
		}else {
			return 3;
		}
	}

	public VM findVMByName(String vmName) {
		for(VM vm : this.getVms()) {
			if(vm.getResourceName().equals(vmName)) {
				return vm;
			}
		}
		return null;
	}
	
	public Organization findOrgByName(String orgName) {
		for(Organization o : this.getOrganizations()) {
			if(o.getOrgName().equals(orgName)) {
				return o;
			}
		}
		return null;
	}
	
	public CategoryVM findCatByName(String catName) {
		for(CategoryVM c : this.getCategories()) {
			if(c.getCategoryName().equals(catName)) {
				return c;
			}
		}
		return null;
	}
	
	public Disc findDiscByName(String discName) {
		for(Disc d : this.getDiscs()) {
			if(d.getResourceName().equals(discName)) {
				return d;
			}
		}
		return null;
	}
	
	public DiscType findDiscTypeByName(String disctype) {
		if(disctype.equals("SSD")) {
			return DiscType.SSD;
		}else {
			return DiscType.HDD;
		}
	}
	
	public User findUserByEmail(String email) {
		for(User u : this.getUsers()) {
			if(u.getEmail().equals(email)) {
				return u;
			}
		}
		return null;
	}
	
	public UserType findUserTypeByName(String name) {
		if(name.equals("SuperAdmin")) {
			return UserType.SuperAdmin;
		}else if(name.equals("Admin")) {
			return UserType.Admin;
		}else {
			return UserType.User;
		}
	}
	
	public User findUserLogIn(UserDTO userdto) {
		for(User u : this.getUsers()) {
			if(u.getEmail().equals(userdto.getEmail()) &&  u.getPassword().equals(userdto.getPassword())) {
				return u;
			}
		}
		return null;
	}

	
	public void deleteVM(String vmName) {
		
		for(VM vm : this.getVms()) {
			if(vm.getResourceName().equals(vmName)) {
				this.getVms().remove(vm);
				break;
			}
		}
		
		for(Disc d : this.getDiscs()) {
			if(d.getVmName().equals(vmName)) {
				d.setVmName("");
			}
		}
		
		for(Organization o : this.getOrganizations()) {
			if(o.getResourcesNames().contains(vmName)) {
				o.getResourcesNames().remove(vmName);
			}
		}
		
	}


	public ArrayList<VMDTO> getVMDTOs(User currentLoggedInUser) {
		ArrayList<VMDTO> vmdto = new ArrayList<VMDTO>();
		if(currentLoggedInUser.getUserType() == UserType.SuperAdmin) {
			for(VM vm : this.getVms()) {
				VMDTO dto = new VMDTO();
				CategoryVM c = this.findCatByName(vm.getCategoryName());
				dto.setGPU(c.getGPU());
				dto.setNumberOfCores(c.getNumberOfCores());
				dto.setOrganizationName(vm.getOrganizationName());
				dto.setRAM(c.getRAM());
				dto.setResourceName(vm.getResourceName());
				vmdto.add(dto);
			}
			return vmdto;
		}else {
			for(VM vm : this.getVms()) {
				VMDTO dto = new VMDTO();
				CategoryVM c = this.findCatByName(vm.getCategoryName());
				dto.setGPU(c.getGPU());
				dto.setNumberOfCores(c.getNumberOfCores());
				dto.setRAM(c.getRAM());
				dto.setResourceName(vm.getResourceName());
				vmdto.add(dto);
			}
			return vmdto;
		}
	}

	public void editVM(VMDTO vmdto) {
		
		for(VM vm : this.getVms()) {
			if(vm.getResourceName().equals(vmdto.getOldResourceName())) {
				vm.setCategoryName(vmdto.getCategoryName());
				vm.setConnectedDiscs(vmdto.getConnectedDiscs());
				vm.setResourceName(vmdto.getResourceName());
				ArrayList<Activity> activities = new ArrayList<Activity>();
				for(String od : vmdto.getAktivnostOD()) {
					int indeks = vmdto.getAktivnostOD().indexOf(od);
					Activity a = new Activity();
					a.setFrom(LocalDateTime.parse(vmdto.getAktivnostOD().get(indeks)));
					if(vmdto.getAktivnostDO().get(indeks).equals("")) {
						a.setTo(null);
					}else {
						a.setTo(LocalDateTime.parse(vmdto.getAktivnostDO().get(indeks)));
					}
					activities.add(a);
				}
				vm.setActivities(activities);
				
			}
		}
		
		for(Disc d : this.getDiscs()) {
			if(d.getVmName().equals(vmdto.getOldResourceName())) {
				d.setVmName(vmdto.getResourceName());
			}
		}
		
		for(Organization o : this.getOrganizations()) {
			for(String name : o.getResourcesNames()) {
				if(name.equals(vmdto.getOldResourceName())) {
					name = vmdto.getOldResourceName();
				}
			}
		}
		
	}


}
