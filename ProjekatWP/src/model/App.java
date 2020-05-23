package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import dto.CategoryDTO;
import dto.DiscDTO;
import dto.OrganizationDTO;
import dto.SearchDTO;
import dto.UserDTO;
import dto.VMDTO;
import spark.Request;

public class App {
	
	private ArrayList<User> users;
	private ArrayList<VM> vms;
	private ArrayList<Disc> discs;
	private ArrayList<Organization> organizations;
	private ArrayList<CategoryVM> categories;
	
	public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	
	public App() {
		super();
		this.users = new ArrayList<User>();
		this.vms = new ArrayList<VM>();
		this.discs = new ArrayList<Disc>();
		this.organizations = new ArrayList<Organization>();
		this.categories = new ArrayList<CategoryVM>();
		//TODO napraviti metode za citanje i upis u json fajlove
		generateTests();
	}

	private void generateTests() {
		User u = new User("mico@super.com", "milorad123", "ime", "prezime", "", UserType.SuperAdmin);
		this.users.add(u);
		Disc d1 = new Disc("d1", "org1", DiscType.HDD, 10, "", LocalDateTime.parse("11-11-2019 12:15", dtf));
		Disc d2 = new Disc("d2", "org1", DiscType.HDD, 10, "", LocalDateTime.parse("11-11-2019 12:15", dtf));
		Disc d3 = new Disc("d3", "org2", DiscType.SSD, 10, "", LocalDateTime.parse("11-11-2019 12:15", dtf));
		Disc d4 = new Disc("d4", "org3", DiscType.SSD, 10, "", LocalDateTime.parse("11-11-2019 12:15", dtf));
		Disc d5 = new Disc("d5", "org4", DiscType.HDD, 10, "", LocalDateTime.parse("11-11-2019 12:15", dtf));
		this.discs.add(d1);
		this.discs.add(d2);
		this.discs.add(d3);
		this.discs.add(d4);
		this.discs.add(d5);
		VM vm1 = new VM("vm1", "org1", "cat1", new ArrayList<String>(), new ArrayList<Activity>());
		VM vm2 = new VM("vm2", "org1", "cat2", new ArrayList<String>(), new ArrayList<Activity>());
		VM vm3 = new VM("vm3", "org2", "cat3", new ArrayList<String>(), new ArrayList<Activity>());
		VM vm4 = new VM("vm4", "org2", "cat4", new ArrayList<String>(), new ArrayList<Activity>());
		VM vm5 = new VM("vm5", "org1", "cat5", new ArrayList<String>(), new ArrayList<Activity>());
		this.vms.add(vm1);
		this.vms.add(vm2);
		this.vms.add(vm3);
		this.vms.add(vm4);
		this.vms.add(vm5);
		CategoryVM c1 = new CategoryVM("cat1", 1, 1, 1);
		CategoryVM c2 = new CategoryVM("cat2", 2, 2, 2);
		CategoryVM c3 = new CategoryVM("cat3", 3, 3, 3);
		CategoryVM c4 = new CategoryVM("cat4", 4, 4, 4);
		CategoryVM c5 = new CategoryVM("cat5", 5, 5, 5);
		this.categories.add(c1);
		this.categories.add(c2);
		this.categories.add(c3);
		this.categories.add(c4);
		this.categories.add(c5);
		ArrayList<String> resources1 = new ArrayList<String>();
		resources1.add(d1.getResourceName());
		resources1.add(d2.getResourceName());
		resources1.add(vm1.getResourceName());
		resources1.add(vm2.getResourceName());
		resources1.add(vm5.getResourceName());
		ArrayList<String> resources2 = new ArrayList<String>();
		resources2.add(d3.getResourceName());
		resources2.add(vm3.getResourceName());
		resources2.add(vm4.getResourceName());
		ArrayList<String> resources3 = new ArrayList<String>();
		resources3.add(d4.getResourceName());
		ArrayList<String> resources4 = new ArrayList<String>();
		resources4.add(d5.getResourceName());
		ArrayList<String> resources5 = new ArrayList<String>();
		
		Organization o1 = new Organization("org1", "opis1", "logo1", new ArrayList<String>(), resources1);
		Organization o2 = new Organization("org2", "opis2", "logo2", new ArrayList<String>(), resources2);
		Organization o3 = new Organization("org3", "opis3", "logo3", new ArrayList<String>(), resources3);
		Organization o4 = new Organization("org4", "opis4", "logo4", new ArrayList<String>(), resources4);
		Organization o5 = new Organization("org5", "opis5", "logo5", new ArrayList<String>(), resources5);
		this.organizations.add(o1);
		this.organizations.add(o2);
		this.organizations.add(o3);
		this.organizations.add(o4);
		this.organizations.add(o5);
		


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
				
				for(int i = 0; i < vmdto.getActivityFROM().size(); i++) {
					Activity a = new Activity();
					a.setFrom(LocalDateTime.parse(vmdto.getActivityFROM().get(i), dtf));
					if(vmdto.getActivityTO().get(i).equals("")) {
						a.setTo(null);
					}else {
						a.setTo(LocalDateTime.parse(vmdto.getActivityTO().get(i), dtf));
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

	public VMDTO convertVMtoVMDTO(VM vm) {
		VMDTO dto = new VMDTO();
		dto.setCategoryName(vm.getCategoryName());
		dto.setConnectedDiscs(vm.getConnectedDiscs());
		dto.setGPU(this.findCatByName(vm.getCategoryName()).getGPU());
		dto.setNumberOfCores(this.findCatByName(vm.getCategoryName()).getNumberOfCores());
		dto.setOldResourceName(vm.getResourceName());
		dto.setOrganizationName(vm.getOrganizationName());
		dto.setRAM(this.findCatByName(vm.getCategoryName()).getRAM());
		dto.setResourceName(vm.getResourceName());
		for(Activity a : vm.getActivities()) {
			dto.getActivityFROM().add(a.getFrom().format(dtf));
			if(a.getTo() != null) {
				dto.getActivityTO().add(a.getTo().format(dtf));
			}
		}
		return dto;
	}

	public CategoryDTO convertCattoCatDTO(CategoryVM cvm) {
		CategoryDTO dto = new CategoryDTO();
		dto.setCategoryName(cvm.getCategoryName());
		dto.setGPU(cvm.getGPU());
		dto.setNumberOfCores(cvm.getNumberOfCores());
		dto.setRAM(cvm.getRAM());
		return dto;
	}

	public void addVM(VMDTO dto) {
		VM vm = new VM();
		vm.setResourceName(dto.getResourceName());
		vm.setOrganizationName(dto.getOrganizationName());
		vm.setCategoryName(dto.getCategoryName());
		vm.setConnectedDiscs(dto.getConnectedDiscs());
		ArrayList<Activity> activities = new ArrayList<Activity>();
		vm.setActivities(activities);
		
		this.vms.add(vm);
		
		for(Organization o : this.organizations) {
			if(o.getOrgName().equals(dto.getOrganizationName())) {
				o.getResourcesNames().add(dto.getResourceName());
				break;
			}
		}
		
		for(Disc d : this.discs) {
			if(dto.getConnectedDiscs().contains(d.getResourceName())) {
				d.setVmName(dto.getResourceName());
			}
		}
	}


	public OrganizationDTO convertOrgtoOrgDTO(Organization o) {
		
		OrganizationDTO dto = new OrganizationDTO();
		dto.setDescription(o.getDescription());
		dto.setLogo(o.getLogo());
		dto.setOrgName(o.getOrgName());
		dto.setResourcesNames(o.getResourcesNames());
		dto.setUsersEmails(o.getResourcesNames());
		return dto;
	}


	public ArrayList<DiscDTO> getAvailableDiscs(OrganizationDTO dto) {
		ArrayList<DiscDTO> dtos = new ArrayList<DiscDTO>();
		for(Disc d : this.getDiscs()) {
			if(dto.getOrgName().equals(d.getOrganizationName()) && d.getVmName().equals("")) {
				DiscDTO ddto = this.convertDisctoDiscDTO(d);
				dtos.add(ddto);
			}
		}
		return dtos;
	}

	private DiscDTO convertDisctoDiscDTO(Disc d) {
		DiscDTO dto = new DiscDTO();
		dto.setCapacity(d.getCapacity());
		dto.setCreated(d.getCreated().format(dtf));
		dto.setOldResourceName(d.getResourceName());
		dto.setOrganizationName(d.getOrganizationName());
		dto.setResourceName(d.getResourceName());
		if(d.getType() == DiscType.SSD) {
			dto.setType("SSD");
		}else {
			dto.setType("HDD");
		}
		dto.setVmName(d.getVmName());
		return dto;
	}

	public ArrayList<VMDTO> searchVM(SearchDTO dto) {
		ArrayList<String> dtoName = this.searchVMByName(dto.getVmName());
		ArrayList<String> dtoCore = this.searchVMByNumOfCores(dto.getCoresFrom(), dto.getCoresTo());
		ArrayList<String> dtoRAM = this.searchVMByRAM(dto.getRamFrom(), dto.getRamTo());
		ArrayList<String> dtoGPU = this.searchVMByGPU(dto.getGpuFrom(), dto.getGpuTo());		
		
		ArrayList<VMDTO> result = new ArrayList<VMDTO>();
		
		for(String vmname : dtoName) {
			if(dtoCore.contains(vmname) && dtoRAM.contains(vmname) && dtoGPU.contains(vmname)) {
				VMDTO vmdto = this.convertVMtoVMDTO(this.findVMByName(vmname));
				result.add(vmdto);
			}
		}
		
		return result;
	}

	private ArrayList<String> searchVMByGPU(int gpuFrom, int gpuTo) {
		ArrayList<String> result = new ArrayList<String>();
		
		if(gpuFrom <= 0 && gpuTo <= 0) {
			for(VM vm : this.vms) {
				VMDTO dto = this.convertVMtoVMDTO(vm);
				result.add(dto.getResourceName());
			}
		}else if(gpuFrom <= 0 && gpuTo >= 0) {
			for(VM vm : this.vms) {
				CategoryVM c = this.findCatByName(vm.getCategoryName());
				if(c.getNumberOfCores() <= gpuTo) {
					VMDTO dto = this.convertVMtoVMDTO(vm);
					result.add(dto.getResourceName());
				}
			}
		}else if(gpuFrom >= 0 && gpuTo <= 0) {
			for (VM vm : this.vms) {
				CategoryVM c = this.findCatByName(vm.getCategoryName());
				if(c.getNumberOfCores() >= gpuFrom) {
					VMDTO dto = this.convertVMtoVMDTO(vm);
					result.add(dto.getResourceName());
				}
			}
		}else {
			for(VM vm : this.vms) {
				CategoryVM c = this.findCatByName(vm.getCategoryName());
				if(c.getNumberOfCores() >= gpuFrom && c.getNumberOfCores() <= gpuTo) {
					VMDTO dto = this.convertVMtoVMDTO(vm);
					result.add(dto.getResourceName());
				}
			}
		}
		
		return result;
	}

	private ArrayList<String> searchVMByRAM(int ramFrom, int ramTo) {
		ArrayList<String> result = new ArrayList<String>();
		
		if(ramFrom <= 0 && ramTo <= 0) {
			for(VM vm : this.vms) {
				VMDTO dto = this.convertVMtoVMDTO(vm);
				result.add(dto.getResourceName());
			}
		}else if(ramFrom <= 0 && ramTo >= 0) {
			for(VM vm : this.vms) {
				CategoryVM c = this.findCatByName(vm.getCategoryName());
				if(c.getNumberOfCores() <= ramTo) {
					VMDTO dto = this.convertVMtoVMDTO(vm);
					result.add(dto.getResourceName());
				}
			}
		}else if(ramFrom >= 0 && ramTo <= 0) {
			for (VM vm : this.vms) {
				CategoryVM c = this.findCatByName(vm.getCategoryName());
				if(c.getNumberOfCores() >= ramFrom) {
					VMDTO dto = this.convertVMtoVMDTO(vm);
					result.add(dto.getResourceName());
				}
			}
		}else {
			for(VM vm : this.vms) {
				CategoryVM c = this.findCatByName(vm.getCategoryName());
				if(c.getNumberOfCores() >= ramFrom && c.getNumberOfCores() <= ramTo) {
					VMDTO dto = this.convertVMtoVMDTO(vm);
					result.add(dto.getResourceName());
				}
			}
		}
		
		return result;
	}

	private ArrayList<String> searchVMByNumOfCores(int coresFrom, int coresTo) {
		ArrayList<String> result = new ArrayList<String>();
		
		if(coresFrom <= 0 && coresTo <= 0) {
			for(VM vm : this.vms) {
				VMDTO dto = this.convertVMtoVMDTO(vm);
				result.add(dto.getResourceName());
			}
		}else if(coresFrom <= 0 && coresTo >= 0) {
			for(VM vm : this.vms) {
				CategoryVM c = this.findCatByName(vm.getCategoryName());
				if(c.getNumberOfCores() <= coresTo) {
					VMDTO dto = this.convertVMtoVMDTO(vm);
					result.add(dto.getResourceName());
				}
			}
		}else if(coresFrom >= 0 && coresTo <= 0) {
			for (VM vm : this.vms) {
				CategoryVM c = this.findCatByName(vm.getCategoryName());
				if(c.getNumberOfCores() >= coresFrom) {
					VMDTO dto = this.convertVMtoVMDTO(vm);
					result.add(dto.getResourceName());
				}
			}
		}else {
			for(VM vm : this.vms) {
				CategoryVM c = this.findCatByName(vm.getCategoryName());
				if(c.getNumberOfCores() >= coresFrom && c.getNumberOfCores() <= coresTo) {
					VMDTO dto = this.convertVMtoVMDTO(vm);
					result.add(dto.getResourceName());
				}
			}
		}
		
		return result;
	}

	private ArrayList<String> searchVMByName(String vmName) {
		ArrayList<String> result = new ArrayList<String>();
		
		if(vmName.equals("")) {
			for(VM vm : this.vms) {
				VMDTO dto = this.convertVMtoVMDTO(vm);
				result.add(dto.getResourceName());
			}
		}else {
			for(VM vm : this.vms) {
				if(vm.getResourceName().equals(vmName)) {
					VMDTO dto = this.convertVMtoVMDTO(vm);
					result.add(dto.getResourceName());
				}
			}
		}
	
		return result;
	}
	
}
