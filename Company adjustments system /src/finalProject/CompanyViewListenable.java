package finalProject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import Exception.DigitsException;
import Exception.ElementExistsException;
import Exception.IDException;
import finalProject.Employee.ePreferences;

public interface CompanyViewListenable {

	boolean viewSetCompany(String name, int year, double average);
	boolean viewAddDepartment(String departmentName, int startTimeDepartment, int finishTimeDepartment, boolean b , boolean change)throws ElementExistsException;
	boolean viewAddRole(String roleName, String depName,int startTimeRole, int finishTimeRole, boolean synco, boolean changeable)throws ElementExistsException;
	Vector <String> viewDepartments();
	Vector <String> viewRoles();
	boolean viewAddEmployee(String name, String iD, int year, String role, ePreferences earlyStart, boolean b, double salary, String type, double salesPercent)throws IDException,ElementExistsException;
	boolean setId(String id)throws IDException,DigitsException;
	boolean isNumbers(String num)throws DigitsException;
	boolean setTiming(int i, int start, int finish, boolean home);
	Vector <Employee> employeesForSchedule();
	String printDepartment();
	String printRoles();
	String printEmployee();
	String printCompany();
	String printDailyProfits();
	boolean save() throws FileNotFoundException, IOException;
	boolean read() throws FileNotFoundException, ClassNotFoundException, IOException;
	Vector <Department> viewSynchronizedDepartments();
	boolean calcSalery();
	Vector <String> viewSyncDepartments();
	Vector <String> viewSyncRoles();
	Vector <String> viewSyncEmployees();
	boolean setNewTimingDepartment(int start,int finish,String name);
	boolean setNewTimingRole(int start,int finish,String name);
	boolean setNewPreferationEmployee(ePreferences preference,String name);
	
}
