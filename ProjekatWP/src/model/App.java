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
		User u2 = new User("mico2@super.com", "milorad123", "ime", "prezime", "org2", UserType.User);
		User u3 = new User("mico3@super.com", "milorad123", "ime", "prezime", "org2", UserType.User);
		User u4 = new User("mico4@super.com", "milorad123", "ime", "prezime", "org1", UserType.User);
		User u5 = new User("mico5@super.com", "milorad123", "ime", "prezime", "org2", UserType.User);
		User u6 = new User("mico6@super.com", "milorad123", "ime", "prezime", "org3", UserType.User);

		this.users.add(u);
		this.users.add(u2);
		this.users.add(u3);
		this.users.add(u4);
		this.users.add(u5);
		this.users.add(u6);
		
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
		ArrayList<String> userEmails = new ArrayList<String>();
		ArrayList<String> userEmails2 = new ArrayList<String>();
		ArrayList<String> userEmails3 = new ArrayList<String>();
		ArrayList<String> userEmails4 = new ArrayList<String>();
		ArrayList<String> userEmails5 = new ArrayList<String>();
		userEmails.add(u4.getEmail());
		userEmails2.add(u2.getEmail());
		userEmails2.add(u3.getEmail());
		userEmails2.add(u5.getEmail());
		userEmails3.add(u6.getEmail());
		
		Organization o1 = new Organization("org1", "opis1", "logo1", userEmails, resources1);
		Organization o2 = new Organization("org2", "opis2", "logo2", userEmails2, resources2);
		Organization o3 = new Organization("org3", "opis3", "logo3", userEmails3, resources3);
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
	
	public int deleteUser(String userEmail) {
		int found = 0;
		
		for(User u : this.getUsers()) {
			if(u.getEmail().equals(userEmail)) {
				this.getUsers().remove(u);
				found = 1;
				break;
			}
		}
		
		if(found == 0) {
			return 0;
		}
		
		for(Organization o : this.getOrganizations()) {
			if(o.getUsersEmails().contains(userEmail)) {
				found = 1;
				o.getUsersEmails().remove(userEmail);
				break;
			}
		}
		
		return found;
	}
	
	public int deleteVM(String vmName) {
		
		int found = 0;
		for(VM vm : this.getVms()) {
			if(vm.getResourceName().equals(vmName)) {
				this.getVms().remove(vm);
				found = 1;
				break;
			}
		}
		if(found == 0) {
			return found;
		}
		
		for(Disc d : this.getDiscs()) {
			if(d.getVmName().equals(vmName)) {
				found = 1;
				d.setVmName("");
			}
		}
		if(found == 0) {
			return found;
		}
		for(Organization o : this.getOrganizations()) {
			if(o.getResourcesNames().contains(vmName)) {
				found = 1;
				o.getResourcesNames().remove(vmName);
			}
		}
		return found;
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
			for(Organization o : this.getOrganizations()) {
				if(o.getUsersEmails().contains(currentLoggedInUser.getEmail())) {
					for(VM vm : this.getVms()) {
						if(o.getResourcesNames().contains(vm.getResourceName())) {
							VMDTO dto = new VMDTO();
							CategoryVM c = this.findCatByName(vm.getCategoryName());
							dto.setGPU(c.getGPU());
							dto.setNumberOfCores(c.getNumberOfCores());
							dto.setOrganizationName(vm.getOrganizationName());
							dto.setRAM(c.getRAM());
							dto.setResourceName(vm.getResourceName());
							vmdto.add(dto);
						}
					}
				}
			}
			return vmdto;
		}
	}

	public int editUser(UserDTO dto) {
		int found = 0;
		for(User u : this.getUsers()) {
			if(u.getEmail().equals(dto.getEmail())) {
				found = 1;
				Organization o = this.findOrgByName(dto.getOrganizationName());
				if(o == null) {
					return 0;
				}
				if(dto.getUserType().equals("Administrator")) {
					u.setUserType(UserType.Admin);
				}else if(dto.getUserType().equals("User")) {
					u.setUserType(UserType.User);
				}else {
					return 0;
				}
				u.setName(dto.getName());
				u.setOrganizationName(o.getOrgName());
				u.setPassword(dto.getPassword());
				u.setSurname(dto.getSurname());
			}
		}
		return found;
	}
	
	public int editVM(VMDTO vmdto) {
		
		int found = 0;
		for(VM vm : this.getVms()) {
			if(vm.getResourceName().equals(vmdto.getOldResourceName())) {
				found = 1;
				CategoryVM c = this.findCatByName(vmdto.getCategoryName());
				if(c == null) {
					return 0;
				}
				ArrayList<Activity> activities = new ArrayList<Activity>();
				try {
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
				}catch(Exception e) {
					return 0;
				}
				vm.setCategoryName(vmdto.getCategoryName());
				vm.setConnectedDiscs(vmdto.getConnectedDiscs());
				vm.setResourceName(vmdto.getResourceName());
				vm.setOrganizationName(vmdto.getOrganizationName());
				
			}
		}
		if(found == 0) {
			return found;
		}
		
		for(Disc d : this.getDiscs()) {
			if(d.getVmName().equals(vmdto.getOldResourceName())) {
				
				d.setVmName(vmdto.getResourceName());
			}
		}
		
		found = 0;
		for(Organization o : this.getOrganizations()) {
			for(String name : o.getResourcesNames()) {
				if(name.equals(vmdto.getOldResourceName())) {
					found = 1;
					int index = o.getResourcesNames().indexOf(name);
					o.getResourcesNames().remove(index);
					o.getResourcesNames().add(index, vmdto.getResourceName());
					break;
				}
			}
		}
		return found;
		
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

	public int addUser(UserDTO dto) {
		int flag = 0;
		User u = new User();
		u.setEmail(dto.getEmail());
		u.setName(dto.getName());
		u.setOrganizationName(dto.getOrganizationName());
		u.setPassword(dto.getPassword());
		u.setSurname(dto.getSurname());
		if(dto.getUserType().equals("Administrator")) {
			u.setUserType(UserType.Admin);
		}else {
			u.setUserType(UserType.User);
		}
		User u2 = this.findUserByEmail(dto.getEmail());
		if(u2 == null) {
			this.users.add(u);
			flag = 1;
		}else {
			return 0;
		}
		flag = 0;
		for(Organization o : this.organizations) {
			if(o.getOrgName().equals(dto.getOrganizationName())) {
				flag = 1;
				o.getUsersEmails().add(dto.getEmail());
				break;
			}
		}
		return flag;
	}
	
	public int addVM(VMDTO dto) {
		int flag = 0;
		VM vm = new VM();
		vm.setResourceName(dto.getResourceName());
		vm.setOrganizationName(dto.getOrganizationName());
		vm.setCategoryName(dto.getCategoryName());
		vm.setConnectedDiscs(dto.getConnectedDiscs());
		ArrayList<Activity> activities = new ArrayList<Activity>();
		vm.setActivities(activities);
		
		
		for(Organization o : this.organizations) {
			if(o.getOrgName().equals(dto.getOrganizationName())) {
				flag = 1;
				o.getResourcesNames().add(dto.getResourceName());
				break;
			}
		}
		if(flag == 0) {
			return flag;
		}
		for(Disc d : this.discs) {
			if(dto.getConnectedDiscs().contains(d.getResourceName())) {
				d.setVmName(dto.getResourceName());
			}
		}
		
		this.vms.add(vm);
		return flag;
	}
	
	public int addDisc(DiscDTO dto) {
		int flag = 0;
		Disc disc = new Disc();
		disc.setResourceName(dto.getResourceName());
		disc.setOrganizationName(dto.getOrganizationName());
		disc.setCapacity(dto.getCapacity());
		disc.setVmName(dto.getVmName());
		if(dto.getType().equals("SSD")) {
			disc.setType(DiscType.SSD);
		}
		else {
			disc.setType(DiscType.HDD);
		}
		disc.setCreated(LocalDateTime.now());
		
		for(Organization o : this.organizations) {
			if(o.getOrgName().equals(dto.getOrganizationName())) {
				flag = 1;
				o.getResourcesNames().add(dto.getResourceName());
				break;
			}
		}
		if(flag == 0) {
			return flag;
		}
		
		
		this.discs.add(disc);
		return flag;
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

	public ArrayList<VMDTO> searchVM(SearchDTO dto, User user) {
		ArrayList<String> dtoName = this.searchVMByName(dto.getVmName(), user);
		ArrayList<String> dtoCore = this.searchVMByNumOfCores(dto.getCoresFrom(), dto.getCoresTo(), user);
		ArrayList<String> dtoRAM = this.searchVMByRAM(dto.getRamFrom(), dto.getRamTo(), user);
		ArrayList<String> dtoGPU = this.searchVMByGPU(dto.getGpuFrom(), dto.getGpuTo(), user);		
		
		
		ArrayList<VMDTO> result = new ArrayList<VMDTO>();
		
		for(String vmname : dtoName) {
			if(dtoCore.contains(vmname) && dtoRAM.contains(vmname) && dtoGPU.contains(vmname)) {
				VMDTO vmdto = this.convertVMtoVMDTO(this.findVMByName(vmname));
				result.add(vmdto);
			}
		}
		
		return result;
	}

	private ArrayList<String> searchVMByGPU(int gpuFrom, int gpuTo, User user) {
		ArrayList<String> result = new ArrayList<String>();
		
		if(gpuFrom <= 0 && gpuTo <= 0) {
			if(user.getUserType() == UserType.SuperAdmin) {
				for(VM vm : this.vms) {
					VMDTO dto = this.convertVMtoVMDTO(vm);
					result.add(dto.getResourceName());
				}
			}else {
				Organization o = this.findOrgByName(user.getOrganizationName());
				for(VM vm: this.vms) {
					if(o.getResourcesNames().contains(vm.getResourceName())) {
						VMDTO dto = this.convertVMtoVMDTO(vm);
						result.add(dto.getResourceName());
					}
				}
			}
		}else if(gpuFrom <= 0 && gpuTo >= 0) {
			if(user.getUserType() == UserType.SuperAdmin) {
				for(VM vm : this.vms) {
					CategoryVM c = this.findCatByName(vm.getCategoryName());
					if(c.getGPU() <= gpuTo) {
						VMDTO dto = this.convertVMtoVMDTO(vm);
						result.add(dto.getResourceName());
					}
				}
			}else {
				Organization o = this.findOrgByName(user.getOrganizationName());
				for(VM vm : this.vms) {
					if(o.getResourcesNames().contains(vm.getResourceName())) {
						CategoryVM c = this.findCatByName(vm.getCategoryName());
						if(c.getGPU() <= gpuTo) {
							VMDTO dto = this.convertVMtoVMDTO(vm);
							result.add(dto.getResourceName());
						}
					}
				}
			}
		}else if(gpuFrom >= 0 && gpuTo <= 0) {
			if(user.getUserType() == UserType.SuperAdmin) {
				for (VM vm : this.vms) {
					CategoryVM c = this.findCatByName(vm.getCategoryName());
					if(c.getGPU() >= gpuFrom) {
						VMDTO dto = this.convertVMtoVMDTO(vm);
						result.add(dto.getResourceName());
					}
				}
			}else {
				Organization o = this.findOrgByName(user.getOrganizationName());
				for(VM vm : this.getVms()) {
					if(o.getResourcesNames().contains(vm.getResourceName())) {
						CategoryVM c = this.findCatByName(vm.getCategoryName());
						if(c.getGPU() >= gpuFrom) {
							VMDTO dto = this.convertVMtoVMDTO(vm);
							result.add(dto.getResourceName());
						}
					}
				}
			}
		}else {
			if(user.getUserType() == UserType.SuperAdmin) {
				for(VM vm : this.vms) {
					CategoryVM c = this.findCatByName(vm.getCategoryName());
					if(c.getGPU() >= gpuFrom && c.getGPU() <= gpuTo) {
						VMDTO dto = this.convertVMtoVMDTO(vm);
						result.add(dto.getResourceName());
					}
				}
			}else {
				Organization o = this.findOrgByName(user.getOrganizationName());
				for(VM vm :  this.vms) {
					if(o.getResourcesNames().contains(vm.getResourceName())) {
						CategoryVM c = this.findCatByName(vm.getCategoryName());
						if(c.getGPU() >= gpuFrom && c.getGPU() <= gpuTo) {
							VMDTO dto = this.convertVMtoVMDTO(vm);
							result.add(dto.getResourceName());
						}
					}
				}
			}
		}
		
		return result;
	}

	private ArrayList<String> searchVMByRAM(int ramFrom, int ramTo, User user) {
		ArrayList<String> result = new ArrayList<String>();
		
		if(ramFrom <= 0 && ramTo <= 0) {
			if(user.getUserType() == UserType.SuperAdmin) {
				for(VM vm : this.vms) {
					VMDTO dto = this.convertVMtoVMDTO(vm);
					result.add(dto.getResourceName());
				}
			}else {
				Organization o = this.findOrgByName(user.getOrganizationName());
				for(VM vm: this.vms) {
					if(o.getResourcesNames().contains(vm.getResourceName())) {
						VMDTO dto = this.convertVMtoVMDTO(vm);
						result.add(dto.getResourceName());
					}
				}
			}
		}else if(ramFrom <= 0 && ramTo >= 0) {
			if(user.getUserType() == UserType.SuperAdmin) {
				for(VM vm : this.vms) {
					CategoryVM c = this.findCatByName(vm.getCategoryName());
					if(c.getRAM() <= ramTo) {
						VMDTO dto = this.convertVMtoVMDTO(vm);
						result.add(dto.getResourceName());
					}
				}
			}else {
				Organization o = this.findOrgByName(user.getOrganizationName());
				for(VM vm : this.vms) {
					if(o.getResourcesNames().contains(vm.getResourceName())) {
						CategoryVM c = this.findCatByName(vm.getCategoryName());
						if(c.getRAM() <= ramTo) {
							VMDTO dto = this.convertVMtoVMDTO(vm);
							result.add(dto.getResourceName());
						}
					}
				}
			}
		}else if(ramFrom >= 0 && ramTo <= 0) {
			if(user.getUserType() == UserType.SuperAdmin) {
				for (VM vm : this.vms) {
					CategoryVM c = this.findCatByName(vm.getCategoryName());
					if(c.getRAM() >= ramFrom) {
						VMDTO dto = this.convertVMtoVMDTO(vm);
						result.add(dto.getResourceName());
					}
				}
			}else {
				Organization o = this.findOrgByName(user.getOrganizationName());
				for(VM vm : this.getVms()) {
					if(o.getResourcesNames().contains(vm.getResourceName())) {
						CategoryVM c = this.findCatByName(vm.getCategoryName());
						if(c.getRAM() >= ramFrom) {
							VMDTO dto = this.convertVMtoVMDTO(vm);
							result.add(dto.getResourceName());
						}
					}
				}
			}
		}else {
			if(user.getUserType() == UserType.SuperAdmin) {
				for(VM vm : this.vms) {
					CategoryVM c = this.findCatByName(vm.getCategoryName());
					if(c.getRAM() >= ramFrom && c.getRAM() <= ramTo) {
						VMDTO dto = this.convertVMtoVMDTO(vm);
						result.add(dto.getResourceName());
					}
				}
			}else {
				Organization o = this.findOrgByName(user.getOrganizationName());
				for(VM vm :  this.vms) {
					if(o.getResourcesNames().contains(vm.getResourceName())) {
						CategoryVM c = this.findCatByName(vm.getCategoryName());
						if(c.getRAM() >= ramFrom && c.getRAM() <= ramTo) {
							VMDTO dto = this.convertVMtoVMDTO(vm);
							result.add(dto.getResourceName());
						}
					}
				}
			}
		}
		
		return result;
	}

	private ArrayList<String> searchVMByNumOfCores(int coresFrom, int coresTo, User user) {
		ArrayList<String> result = new ArrayList<String>();
		
		if(coresFrom <= 0 && coresTo <= 0) {
			if(user.getUserType() == UserType.SuperAdmin) {
				for(VM vm : this.vms) {
					VMDTO dto = this.convertVMtoVMDTO(vm);
					result.add(dto.getResourceName());
				}
			}else {
				Organization o = this.findOrgByName(user.getOrganizationName());
				for(VM vm: this.vms) {
					if(o.getResourcesNames().contains(vm.getResourceName())) {
						VMDTO dto = this.convertVMtoVMDTO(vm);
						result.add(dto.getResourceName());
					}
				}
			}
		}else if(coresFrom <= 0 && coresTo >= 0) {
			if(user.getUserType() == UserType.SuperAdmin) {
				for(VM vm : this.vms) {
					CategoryVM c = this.findCatByName(vm.getCategoryName());
					if(c.getNumberOfCores() <= coresTo) {
						VMDTO dto = this.convertVMtoVMDTO(vm);
						result.add(dto.getResourceName());
					}
				}
			}else {
				Organization o = this.findOrgByName(user.getOrganizationName());
				for(VM vm : this.vms) {
					if(o.getResourcesNames().contains(vm.getResourceName())) {
						CategoryVM c = this.findCatByName(vm.getCategoryName());
						if(c.getNumberOfCores() <= coresTo) {
							VMDTO dto = this.convertVMtoVMDTO(vm);
							result.add(dto.getResourceName());
						}
					}
				}
			}
		}else if(coresFrom >= 0 && coresTo <= 0) {
			if(user.getUserType() == UserType.SuperAdmin) {
				for (VM vm : this.vms) {
					CategoryVM c = this.findCatByName(vm.getCategoryName());
					if(c.getNumberOfCores() >= coresFrom) {
						VMDTO dto = this.convertVMtoVMDTO(vm);
						result.add(dto.getResourceName());
					}
				}
			}else {
				Organization o = this.findOrgByName(user.getOrganizationName());
				for(VM vm : this.getVms()) {
					if(o.getResourcesNames().contains(vm.getResourceName())) {
						CategoryVM c = this.findCatByName(vm.getCategoryName());
						if(c.getNumberOfCores() >= coresFrom) {
							VMDTO dto = this.convertVMtoVMDTO(vm);
							result.add(dto.getResourceName());
						}
					}
				}
			}
		}else {
			if(user.getUserType() == UserType.SuperAdmin) {
				for(VM vm : this.vms) {
					CategoryVM c = this.findCatByName(vm.getCategoryName());
					if(c.getNumberOfCores() >= coresFrom && c.getNumberOfCores() <= coresTo) {
						VMDTO dto = this.convertVMtoVMDTO(vm);
						result.add(dto.getResourceName());
					}
				}
			}else {
				Organization o = this.findOrgByName(user.getOrganizationName());
				for(VM vm :  this.vms) {
					if(o.getResourcesNames().contains(vm.getResourceName())) {
						CategoryVM c = this.findCatByName(vm.getCategoryName());
						if(c.getNumberOfCores() >= coresFrom && c.getNumberOfCores() <= coresTo) {
							VMDTO dto = this.convertVMtoVMDTO(vm);
							result.add(dto.getResourceName());
						}
					}
				}
			}
		}
		
		return result;
	}

	private ArrayList<String> searchVMByName(String vmName, User user) {
		ArrayList<String> result = new ArrayList<String>();
		
		if(vmName.equals("")) {
			if(user.getUserType() == UserType.SuperAdmin) {
				for(VM vm : this.vms) {
					VMDTO dto = this.convertVMtoVMDTO(vm);
					result.add(dto.getResourceName());
				}
			}else {
				Organization o = this.findOrgByName(user.getOrganizationName());
				for(VM vm : this.vms) {
					if(o.getResourcesNames().contains(vm.getResourceName())) {
						VMDTO dto = this.convertVMtoVMDTO(vm);
						result.add(dto.getResourceName());
					}
				}
			}
		}else {
			if(user.getUserType() == UserType.SuperAdmin) {
				for(VM vm : this.vms) {
					if(vm.getResourceName().equals(vmName)) {
						VMDTO dto = this.convertVMtoVMDTO(vm);
						result.add(dto.getResourceName());
					}
				}
			}else {
				Organization o = this.findOrgByName(user.getOrganizationName());
				for(VM vm : this.vms) {
					if(o.getResourcesNames().contains(vm.getResourceName()) && vm.getResourceName().equals(vmName)) {
						VMDTO dto = this.convertVMtoVMDTO(vm);
						result.add(dto.getResourceName());
					}
				}
			}
		}
	
		return result;
	}

	public ArrayList<DiscDTO> getDiscs(User currentLoggedInUser) {
		Organization o = this.findOrgByName(currentLoggedInUser.getOrganizationName());
		ArrayList<DiscDTO> dtos = new ArrayList<DiscDTO>();
		for(Disc d : this.discs) {
			if(o.getResourcesNames().contains(d.getResourceName())) {
				DiscDTO dto = this.convertDisctoDiscDTO(d);
				dtos.add(dto);
			}
		}
		return dtos;
	}

	public UserDTO convertUserToUserDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.setEmail(user.getEmail());
		dto.setName(user.getName());
		dto.setOrganizationName(user.getOrganizationName());
		dto.setPassword(user.getPassword());
		dto.setSurname(user.getSurname());
		if(user.getUserType() == UserType.SuperAdmin) {
			dto.setUserType("Superadministrator");
		}else if(user.getUserType() == UserType.Admin) {
			dto.setUserType("Administrator");
		}else {
			dto.setUserType("User");
		}
		return dto;
	}
	
	public ArrayList<UserDTO> getUserDTOs(User currentLoggedInUser) {
		ArrayList<UserDTO> dtos = new ArrayList<UserDTO>();
		if(currentLoggedInUser.getUserType() == UserType.SuperAdmin) {
			for(User u : this.users) {
				if(u.getEmail().equals(currentLoggedInUser.getEmail()) == false && (u.getUserType() == UserType.Admin || u.getUserType() == UserType.User)) {
					UserDTO dto = this.convertUserToUserDTO(u);
					dtos.add(dto);
				}
			}
		}else {
			Organization o = this.findOrgByName(currentLoggedInUser.getOrganizationName());
			for(User u : this.users) {
				if(u.getOrganizationName().equals(o.getOrgName()) && u.getEmail().equals(currentLoggedInUser.getEmail())) {
					UserDTO dto = this.convertUserToUserDTO(u);
					dtos.add(dto);
				}
			}
		}
		return dtos;
	}

	public boolean checkStringLetters(String name) {
		String check = name.toLowerCase();
		char[] charArray = check.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
	         char ch = charArray[i];
	         if (!(ch >= 'a' && ch <= 'z')) {
	            return false;
	         }
	      }
		return true;
		
	}

	
	
}
