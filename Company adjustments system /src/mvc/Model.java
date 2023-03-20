
//SHALEV SHARABI 313287823
//NATALI DAHARI  205871049
package mvc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JOptionPane;

import Exception.DigitsException;
import Exception.ElementExistsException;
import Exception.IDException;
import finalProject.BaseWageEmployee;
import finalProject.Company;
import finalProject.CompanyEventsListenable;
import finalProject.Department;
import finalProject.Employee;
import finalProject.Role;
import finalProject.Employee.ePreferences;
import finalProject.IsChangeable;
import finalProject.IsSynchronizable;

public class Model implements Serializable{
	public static Vector <CompanyEventsListenable> listeners;
	private static Company c1;


	public boolean setCompany(String name, int year, double average) {
		Company c= new Company(name, year, average);
		this.c1=c;
		return true;
	}

	public boolean setDepartment(String departmentName, int startTimeDepartment, int finishTimeDepartment, boolean sync, boolean change) throws ElementExistsException {
		try {
			if(c1.checkIfDepExists(departmentName)==true) {
				throw new ElementExistsException("Department is already Exists!");
			}
			else {
				Department d;

				d = new Department(departmentName, startTimeDepartment, finishTimeDepartment);

				if(sync == true) {
					d.synchronize();	
				}
				if(change == true) {
					d.makeChangeable();
				}
				c1.addDepartment(d);
				return true;
			} 
		}catch (ElementExistsException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return false;

	}

	public boolean setRole(String roleName, String department, boolean synco, boolean changeable) throws ElementExistsException {
		if(c1.checkIfRoleExists(roleName)==true) {
			throw new ElementExistsException("Role is already Exists!");
		}
		else {
			for(int i=0; i<c1.getAllDepartments().size(); i++) {
				if(c1.getAllDepartments().elementAt(i).getDepartmentName()==department) {
					Role r;
					try {
						r = new Role(roleName,c1.getAllDepartments().elementAt(i));
						if(synco == true) {
							r.synchronize();	
						}
						if(changeable == true) {
							r.makeChangeable();
						}
						this.c1.getAllDepartments().elementAt(i).addRole(r);
						return true;

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return false;

	}


	public Vector <String> getDepartmentsNames(){
		Vector <String> departments = new Vector <String>();
		if(!c1.getAllDepartments().isEmpty()) {
			for(int i=0; i<c1.getAllDepartments().size(); i++) {
				departments.add(c1.getAllDepartments().elementAt(i).getDepartmentName());
			}
			return departments;
		}
		else
			return null;
	}


	public Vector<String> getRolesNames() {
		Vector <String> roles = new Vector<String>();
		if(!c1.getAllDepartments().isEmpty()) {
			for(int i=0; i<c1.getAllDepartments().size() ; i++) {
				if(!c1.getAllDepartments().elementAt(i).getAllRolles().isEmpty()) {
					for(int j=0; j<c1.getAllDepartments().elementAt(i).getAllRolles().size() ; j++) {
						roles.add(c1.getAllDepartments().elementAt(i).getAllRolles().elementAt(j).getRoleName());
					}
				}

			}
			return roles;
		}
		return null;
	}

	public boolean setEmployee(String name, String iD, int year, String role, ePreferences e,  boolean b, double hourlyWage, String type, double salesPercent) throws ElementExistsException {
		if(c1.checkIfIdEx(iD)==true) {
			throw new ElementExistsException("Employee is already registered!");
		}
		else {
			try {
				this.c1.addEmployee(name,iD,year,role,e,b,hourlyWage,type,salesPercent);
				return true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}

	public boolean setId(String id) throws IDException, DigitsException {
		if(id.length()<9 ||id.length()>9 || isNumbers(id)==false) {
			throw new IDException("ID has to contain 9 digits");
		}
		return true;
	}

	public boolean isNumbers(String num)throws DigitsException {
		for(int i=0; i<num.length(); i++) {
			if(num.charAt(i)< '0' || num.charAt(i) > '9') {
				throw new DigitsException("Some fields has to contain digits only");
			}
		}
		return true;
	}

	public boolean setTime(int i, int start, int finish, boolean home) {
		if(!c1.getAllEmployees().isEmpty() && i<c1.getAllEmployees().size()) {
			c1.getAllEmployees().elementAt(i).setStartTime(start);
			c1.getAllEmployees().elementAt(i).setFinishTime(finish);
			c1.getAllEmployees().elementAt(i).setWorkingFromHomeToday(home);
			return true;
		}
		return false;
	}


	public Vector<Employee> getAllEmployees() {
		return c1.getAllEmployees();
	}

	public String printDepartments() {
		return c1.printDepartments();
	}

	public String printRoles() {
		return c1.printRoles();
	}

	public String printEmployees() {
		return c1.printEmployees();
	}

	public String printCompany() {
		return c1.toString();
	}

	public String printDailyProfits() {
		if(!c1.getAllEmployees().isEmpty()) {
			c1.setEmployeesEfficiency(c1.getAllEmployees());
		}
		return c1.printDailyProfits();
	}

	public boolean save() throws FileNotFoundException, IOException {
		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("Company.date"));
		outFile.writeObject(c1);
		outFile.close();
		return true;
	}

	public boolean read() throws FileNotFoundException, ClassNotFoundException, IOException {
		ObjectInputStream inFile= new ObjectInputStream(new FileInputStream("Company.date"));
		Company c = (Company)inFile.readObject();
		Model.c1=c;
		inFile.close();
		return true;
	}

	public Vector<Department> synchronizedDepartments() {
		Vector <Department> synchronizedDepartments=new Vector<Department>();
		if(!c1.getAllDepartments().isEmpty()) {
			for(int i=0; i<c1.getAllDepartments().size();i++) {
				if(c1.getAllDepartments().elementAt(i) instanceof IsSynchronizable) {
					synchronizedDepartments.add(c1.getAllDepartments().elementAt(i));
				}
			}
		}
		return synchronizedDepartments;
	}

	public boolean calSalery() {
		c1.calcSalaries();
		return true;
	}

	public Vector<String> viewSyncDeps() {
		Vector <String> deps=new Vector <String>();
		if(!c1.getAllDepartments().isEmpty()) {
			for(int i=0; i<c1.getAllDepartments().size();i++) {
				if(c1.getAllDepartments().elementAt(i).isIsChangeable()==true) {
					deps.add(c1.getAllDepartments().elementAt(i).getDepartmentName());
				}	
			}
		}
		return deps;
	}

	public Vector<String> viewSyncRoles() {
		Vector <String> roles=new Vector <String>();
		if(!c1.getAllDepartments().isEmpty()) {
			for(int i=0; i<c1.getAllDepartments().size();i++) {
				if(!c1.getAllDepartments().elementAt(i).getAllRolles().isEmpty()) {
					for(int j=0; j<c1.getAllDepartments().elementAt(i).getAllRolles().size();j++) {
						if(c1.getAllDepartments().elementAt(i).getAllRolles().elementAt(j).isIsChangeable()==true) {
							roles.add(c1.getAllDepartments().elementAt(i).getAllRolles().elementAt(j).getRoleName());
						}
					}
				}	
			}
		}
		return roles;
	}

	public Vector<String> viewSyncEmp() {
		Vector <String> emps=new Vector <String>();
		if(!c1.getAllEmployees().isEmpty()) {
			for(int i=0; i<c1.getAllEmployees().size();i++) {
				emps.add(c1.getAllEmployees().elementAt(i).getId());
			}	
		}
		return emps;
	}

	public boolean setNewTimingDepartment(int start, int finish, String name) {
		if(!c1.getAllDepartments().isEmpty()) {
			for(int i=0;i<c1.getAllDepartments().size();i++) {
				if(c1.getAllDepartments().elementAt(i).getDepartmentName().equals(name)) {
					c1.getAllDepartments().elementAt(i).setStartTimeDepartment(start);
					c1.getAllDepartments().elementAt(i).setFinishTimeDepartment(finish);
					return true;
				}
			}
		}
		return false;
	}

	public boolean setNewTimingRole(int start, int finish, String name) {
		if(!c1.getAllDepartments().isEmpty()) {
			for(int i=0;i<c1.getAllDepartments().size();i++) {
				if(!c1.getAllDepartments().elementAt(i).getAllRolles().isEmpty()) {
					for(int j=0;j<c1.getAllDepartments().elementAt(i).getAllRolles().size();j++) {
						if(c1.getAllDepartments().elementAt(i).getAllRolles().elementAt(j).getRoleName().equals(name)) {
							c1.getAllDepartments().elementAt(i).getAllRolles().elementAt(j).setStartTimeRole(start);
							c1.getAllDepartments().elementAt(i).getAllRolles().elementAt(j).setFinishTimeRole(finish);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean setNewPreferationEmployee(ePreferences preference, String id) {
		if(!c1.getAllEmployees().isEmpty()) {
			for(int i=0; i<c1.getAllEmployees().size();i++) {
				if(c1.getAllEmployees().elementAt(i).getId().equals(id)) {
					c1.getAllEmployees().elementAt(i).setPreference(preference);
					return true;
				}
			}
		}
		return false;
	}


}
