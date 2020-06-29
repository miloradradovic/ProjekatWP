package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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
	public static Gson g;

	
	public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	
	public App() {
		super();
		this.users = new ArrayList<User>();
		this.vms = new ArrayList<VM>();
		this.discs = new ArrayList<Disc>();
		this.organizations = new ArrayList<Organization>();
		this.categories = new ArrayList<CategoryVM>();
		readFiles();
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
	
	public void readFiles() {
		g = new Gson();
		try {
			this.setUsers(g.fromJson(new FileReader("./files/users.json"), new TypeToken<ArrayList<User>>(){}.getType()));
			this.setCategories(g.fromJson(new FileReader("./files/categories.json"), new TypeToken<ArrayList<CategoryVM>>(){}.getType()));
			this.setVms(g.fromJson(new FileReader("./files/vms.json"), new TypeToken<ArrayList<VM>>(){}.getType()));
			this.setOrganizations(g.fromJson(new FileReader("./files/organizations.json"), new TypeToken<ArrayList<Organization>>(){}.getType()));
			this.setDiscs(g.fromJson(new FileReader("./files/discs.json"), new TypeToken<ArrayList<Disc>>(){}.getType()));

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeToFiles() {
	    g = new GsonBuilder().setPrettyPrinting().create();
		try {
			
			Writer user = new FileWriter("./files/users.json");
			Writer vm = new FileWriter("./files/vms.json");
			Writer disc = new FileWriter("./files/discs.json");
			Writer category = new FileWriter("./files/categories.json");
			Writer organization = new FileWriter("./files/organizations.json");
			
			g.toJson(this.users, user);
			g.toJson(this.categories, category);
			g.toJson(this.vms, vm);
			g.toJson(this.discs, disc);
			g.toJson(this.organizations, organization);
			user.close();
			vm.close();
			category.close();
			disc.close();
			organization.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
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
	
public int deleteDisc(String dName) {
		
		int found = 0;
		for(Disc d : this.getDiscs()) {
			if(d.getResourceName().equals(dName)) {
				this.getDiscs().remove(d);
				found = 1;
				break;
			}
		}
		if(found == 0) {
			return found;
		}
		
		for(Organization o : this.getOrganizations()) {
			if(o.getResourcesNames().contains(dName)) {
				found = 1;
				o.getResourcesNames().remove(dName);
			}
		}
		
		for(VM vm : this.getVms()) {
			if(vm.getConnectedDiscs().contains(dName)) {
				vm.getConnectedDiscs().remove(dName);
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
	
public int editDisc(DiscDTO discdto) {
	
		int found = 0;
		
		for(Disc d : this.getDiscs()) {
			if(d.getResourceName().equals(discdto.getOldResourceName())) {
				
				found = 1;
				d.setResourceName(discdto.getResourceName());
				d.setCapacity(discdto.getCapacity());
				d.setOrganizationName(discdto.getOrganizationName());
				if (discdto.getType().equals("SSD")) {
					d.setType(DiscType.SSD);
				}
				else {
					d.setType(
							DiscType.HDD);
				}
				if(discdto.getVmName() == null) {
					d.setVmName("");
				}else {
					d.setVmName(discdto.getVmName());			
				}
			}
		}
		if(found == 0) {
			return found;
		}
		
		found = 0;
		
		for(Organization o : this.getOrganizations()) {
			for(String name : o.getResourcesNames()) {
				if(name.equals(discdto.getOldResourceName())) {
					found = 1;
					int index = o.getResourcesNames().indexOf(name);
					o.getResourcesNames().remove(index);
					o.getResourcesNames().add(index, discdto.getResourceName());
					break;
				}
			}
		}
		
		for(VM vm : this.getVms()) {
			if(vm.getConnectedDiscs().contains(discdto.getOldResourceName())) {
				vm.getConnectedDiscs().remove(discdto.getOldResourceName());
			}
			if(vm.getResourceName().equals(discdto.getVmName())) {
				vm.getConnectedDiscs().add(discdto.getResourceName());
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
	
	public ArrayList<DiscDTO> getDiscDTOs(User currentLoggedInUser) {
		ArrayList<DiscDTO> discdto = new ArrayList<DiscDTO>();
		if(currentLoggedInUser.getUserType() == UserType.SuperAdmin) {
			for(Disc d : this.getDiscs()) {
				DiscDTO dto = new DiscDTO();
				dto.setCapacity(d.getCapacity());
				if(d.getType() == DiscType.SSD) {
					dto.setType("SSD");
				}
				else {
					dto.setType("HDD");
				}
				dto.setOrganizationName(d.getOrganizationName());
				dto.setResourceName(d.getResourceName());
				dto.setVmName(d.getVmName());
				discdto.add(dto);
			}
			return discdto;
		}else {
			for(Organization o : this.getOrganizations()) {
				if(o.getUsersEmails().contains(currentLoggedInUser.getEmail())) {
					for(Disc d : this.getDiscs()) {
						if(o.getResourcesNames().contains(d.getResourceName())) {
							DiscDTO dto = new DiscDTO();
							dto.setCapacity(d.getCapacity());
							if(d.getType() == DiscType.SSD) {
								dto.setType("SSD");
							}
							else {
								dto.setType("HDD");
							}
							dto.setOrganizationName(d.getOrganizationName());
							dto.setResourceName(d.getResourceName());
							dto.setVmName(d.getVmName());
							discdto.add(dto);
						}
					}
				}
			}
			return discdto;
		}
	}
	
	public ArrayList<VMDTO> getAvailableVMs(OrganizationDTO dto) {
		ArrayList<VMDTO> dtos = new ArrayList<VMDTO>();
		for(VM vm : this.getVms()) {
			if(dto.getOrgName().equals(vm.getOrganizationName())) {
				VMDTO vmdto = this.convertVMtoVMDTO(vm);
				dtos.add(vmdto);
			}
		}
		return dtos;
	}
	
	public int addDisc(DiscDTO dto) {
		int flag = 0;
		Disc disc = new Disc();
		disc.setResourceName(dto.getResourceName());
		disc.setOrganizationName(dto.getOrganizationName());
		disc.setCapacity(dto.getCapacity());
		if(dto.getVmName() == null) {
			disc.setVmName("");
		}else {
			disc.setVmName(dto.getVmName());
		}
		if(dto.getType().equals("SSD")) {
			disc.setType(DiscType.SSD);
		}
		else {
			disc.setType(DiscType.HDD);
		}
		disc.setCreated(LocalDateTime.now());
		
		Disc d = this.findDiscByName(dto.getResourceName());
		if(d != null) {
			return 0;
		}
		
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
		
		if(dto.getVmName().equals("") == false) {
			for(VM vm : this.vms) {
				if(vm.getResourceName().equals(dto.getVmName())) {
					vm.getConnectedDiscs().add(dto.getResourceName());
					break;
				}
			}
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

	public DiscDTO convertDisctoDiscDTO(Disc d) {
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
		dto.setOldEmail(user.getEmail());
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

	public int editProfile(UserDTO dto) {
		int flag = 0;
		
		for(User u : this.users) {
			if(u.getEmail().equals(dto.getOldEmail())) {
				u.setEmail(dto.getEmail());
				u.setName(dto.getName());
				u.setPassword(dto.getPassword());
				u.setSurname(dto.getSurname());
				flag = 1;
				break;
			}
		}
		
		if(flag == 0) {
			return 0;
		}
		
		if(dto.getUserType().equals("Superadministrator") == false) {
			flag = 0;
			
			for(Organization o : this.organizations) {
				if(o.getUsersEmails().contains(dto.getOldEmail())) {
					int index = o.getUsersEmails().indexOf(dto.getOldEmail());
					o.getUsersEmails().remove(index);
					o.getUsersEmails().add(index, dto.getEmail());
					flag = 1;
					break;
				}
			}
		}
		
		return flag;
	}

	
	
}
