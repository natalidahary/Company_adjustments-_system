package mvc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import Exception.DigitsException;
import Exception.ElementExistsException;
import Exception.IDException;
import finalProject.CompanyEventsListenable;
import finalProject.CompanyViewListenable;
import finalProject.Department;
import finalProject.Employee;
import finalProject.Employee.ePreferences;
import finalProject.Role;

public class Controller implements CompanyEventsListenable, CompanyViewListenable {
	
	private Model model;
	private View view;

	public Controller(Model model, View view) {
		this.model=model;
		this.view=view;

		//	model.setListener(this);
		view.setListener(this);

	}

	@Override
	public boolean viewSetCompany(String name, int year, double average) {
		return model.setCompany(name, year, average);
	}

	@Override
	public boolean viewAddDepartment(String departmentName,int startTimeDepartment, int finishTimeDepartment, boolean b, boolean change) throws ElementExistsException {
		return model.setDepartment(departmentName, startTimeDepartment, finishTimeDepartment,b, change);
	}

	@Override
	public boolean viewAddRole(String roleName, String depName,int startTimeRole,int finishTimeRole, boolean synco, boolean changeable) throws ElementExistsException {
		return model.setRole(roleName,depName,synco,changeable);
	}

	@Override
	public boolean viewAddEmployee(String name, String iD, int year, String role, ePreferences e,  boolean b, double hourlyWage, String type, double salesPercent) throws ElementExistsException {
		return model.setEmployee(name, iD,  year, role, e, b,  hourlyWage,  type, salesPercent);
	}
	
	@Override
	public boolean setId(String id) throws IDException, DigitsException{
		return model.setId(id);
	}
	
	@Override
	public boolean isNumbers(String num)throws DigitsException {
		return model.isNumbers(num);
	}
	
	@Override
	public Vector<String> viewDepartments() {
		return model.getDepartmentsNames();
	}

	@Override
	public Vector<String> viewRoles() {
		return model.getRolesNames();
	}
	
	@Override
	public boolean setTiming(int i ,int start, int finish, boolean home) {
		return model.setTime(i, start, finish,home);
	}
	
	@Override
	public Vector<Employee> employeesForSchedule() {
		return model.getAllEmployees();
	}

	@Override
	public String printDepartment() {
		return model.printDepartments();
	}

	@Override
	public String printRoles() {
		return model.printRoles();
	}

	@Override
	public String printEmployee() {
		return model.printEmployees();
	}

	@Override
	public String printCompany() {
		return model.printCompany();
	}

	@Override
	public String printDailyProfits() {
		return model.printDailyProfits();
	}

	@Override
	public boolean save() throws FileNotFoundException, IOException {
		return model.save();
		
	}

	@Override
	public boolean read() throws FileNotFoundException, ClassNotFoundException, IOException {
		return model.read();
	}

	@Override
	public Vector<Department> viewSynchronizedDepartments() {
		return model.synchronizedDepartments();
	}

	@Override
	public boolean calcSalery() {
		return model.calSalery();
	}

	@Override
	public Vector<String> viewSyncDepartments() {
		return model.viewSyncDeps();
	}

	@Override
	public Vector<String> viewSyncRoles() {
		return model.viewSyncRoles();
	}

	@Override
	public Vector<String> viewSyncEmployees() {
		return model.viewSyncEmp();
	}

	@Override
	public boolean setNewTimingDepartment(int start, int finish, String name) {
		return model.setNewTimingDepartment(start,finish,name);
	}

	@Override
	public boolean setNewTimingRole(int start, int finish, String name) {
		return model.setNewTimingRole(start,finish,name);
	}

	@Override
	public boolean setNewPreferationEmployee(ePreferences preference, String name) {
		return model.setNewPreferationEmployee(preference,name);
	}







	

}
