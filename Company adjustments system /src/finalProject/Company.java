package finalProject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Vector;

import Exception.ElementExistsException;
import finalProject.Employee.ePreferences;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Company implements Serializable {

	protected String companyName;
	protected int establishedYear;
	protected Vector <Department> allDepartments;
	protected Vector <Employee> allEmployees;
	protected double averageProffitSingleEmployeePerHour;
	protected Vector <Integer> earlyStart;
	protected Vector <Integer> lateStart;
	protected Vector <Integer> notPreferred;
	protected int bufferDefaultHours;
	protected int bufferHomeWorkingHours;
	protected int bufferProperHours;
	protected double totalProfitsPerDay;


	public Company(String companyName, int establishedYear, double averageProffitSingleEmployeePerHour) {
		this.companyName = companyName;
		this.establishedYear = establishedYear;
		this.allDepartments = new Vector<Department>();
		this.allEmployees = new Vector<Employee>();
		this.averageProffitSingleEmployeePerHour = averageProffitSingleEmployeePerHour;
		this.earlyStart = new Vector <Integer>(8);
		for(int i=0;i<8;i++) {
			earlyStart.add(0);
		}
		this.lateStart =  new Vector <Integer>(24);
		for(int i=0;i<24;i++) {
			lateStart.add(0);
		}
		this.notPreferred =  new Vector <Integer>(15);
		for(int i=0;i<15 ;i++) {
			notPreferred.add(0);
		}
		this.bufferDefaultHours=0;
		this.bufferHomeWorkingHours=0;
		this.bufferProperHours = 0;
		this.totalProfitsPerDay=0;
	}




	public boolean checkIfIdEx(String id) {
		for(int i=0; i<allEmployees.size(); i++) {
			if(allEmployees.get(i).getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	public void addDepartment(Department d) {
		this.allDepartments.add(d);
	}

	//{EARLY_START , LATE_START, DEFAULT, HOME}
	public void setEmployeesEfficiency(Vector <Employee> emp) {
		int amountOfEarlyHours = 0,amountOfLateHours = 0, amountRest=0;
		resetProfitData();
		if(!emp.isEmpty()) {
			for(int i=0; i<emp.size(); i++) {
				///check if proper hours
				if(emp.elementAt(i).getStartTime()>=8 && emp.elementAt(i).getFinishTime()<=17 && emp.elementAt(i).isWorkingFromHomeToday()==false) {
					amountRest=emp.elementAt(i).getFinishTime()-emp.elementAt(i).getStartTime();
					if(emp.elementAt(i).getPreference()== ePreferences.DEFAULT) {
						this.bufferDefaultHours=bufferDefaultHours+amountRest;
					}
					else {
						this.bufferProperHours=bufferProperHours+amountRest;
					}
				}
				///check if start earlier than 8
				else if(emp.elementAt(i).getStartTime()>=0 && emp.elementAt(i).getStartTime()<8 && emp.elementAt(i).isWorkingFromHomeToday()==false) {
					amountOfEarlyHours= 8-(emp.elementAt(i).getStartTime());
					amountRest=emp.elementAt(i).getFinishTime()-emp.elementAt(i).getStartTime()-amountOfEarlyHours;
					if(emp.elementAt(i).getPreference()== ePreferences.EARLY_START) {
						this.earlyStart.setElementAt(earlyStart.elementAt(amountOfEarlyHours)+1, amountOfEarlyHours);
						this.bufferProperHours=bufferProperHours+amountRest;
					}
					else {
						this.notPreferred.setElementAt(notPreferred.elementAt(amountOfEarlyHours)+1, amountOfEarlyHours);
						this.bufferProperHours=bufferProperHours+amountRest;
					}
				}
				///check if start later than 8
				else if(emp.elementAt(i).getStartTime()>8 && emp.elementAt(i).getFinishTime()<24 && emp.elementAt(i).isWorkingFromHomeToday()==false) {
					amountOfLateHours= (emp.elementAt(i).getStartTime())-8;
					amountRest=emp.elementAt(i).getFinishTime()-emp.elementAt(i).getStartTime()-amountOfLateHours;
					if(emp.elementAt(i).getPreference()== ePreferences.LATE_START) {
						this.lateStart.setElementAt(lateStart.elementAt(amountOfLateHours)+1, amountOfLateHours);
						this.bufferProperHours=bufferProperHours+amountRest;
					}
					else {
						this.notPreferred.setElementAt(notPreferred.elementAt(amountOfLateHours)+1, amountOfLateHours);
						this.bufferProperHours=bufferProperHours+amountRest;
					}
				}
				else if(emp.elementAt(i).isWorkingFromHomeToday()==true) {
					amountRest=(emp.elementAt(i).getFinishTime())-(emp.elementAt(i).getStartTime());
					this.bufferHomeWorkingHours=bufferHomeWorkingHours+amountRest;
				}
			}
		}
	}

	public void calcProffitsPerDay() {
		this.totalProfitsPerDay=0;
		double specificHoursMultEmployees=0;
		// calculates profits of employees wanted to start early and were signed to
		for(int i=0; i<earlyStart.size(); i++) {
			specificHoursMultEmployees= (double)(earlyStart.elementAt(i) * i * 1.2);
			this.totalProfitsPerDay += (double)specificHoursMultEmployees * averageProffitSingleEmployeePerHour;
		}
		// calculates profits of employees wanted to start late and were signed to
		for(int i=0; i<lateStart.size(); i++) {
			specificHoursMultEmployees= (double)(lateStart.elementAt(i) * i * 1.2);
			this.totalProfitsPerDay += (double)(specificHoursMultEmployees * averageProffitSingleEmployeePerHour);
		}
		// calculates profits of employees who did not want to start early/late and were signed to
		for(int i=0; i<notPreferred.size(); i++) {
			specificHoursMultEmployees = (double)(notPreferred.elementAt(i) * i * (0.8));
			this.totalProfitsPerDay += (double)(specificHoursMultEmployees * averageProffitSingleEmployeePerHour);
		}
		// calculates profits of employees wanted to stay at default hours and were signed to
		specificHoursMultEmployees = (double)(bufferDefaultHours * 1.2);
		this.totalProfitsPerDay += (double)(specificHoursMultEmployees * averageProffitSingleEmployeePerHour);

		// calculates profits of employees wanted to work from home and were signed to
		specificHoursMultEmployees = (double)(bufferHomeWorkingHours * 1.1);
		this.totalProfitsPerDay += (double)(specificHoursMultEmployees * averageProffitSingleEmployeePerHour);

		// calculates profits of employees from regular/proper hours
		this.totalProfitsPerDay += (double)(bufferProperHours * averageProffitSingleEmployeePerHour);

	}

	//"Base waged" ,"Hourly waged", "Based + sales"
	public boolean addEmployee(String name,String iD,int year,String role,ePreferences e,boolean b,double hourlyWage,String type,double salesPercent) throws Exception {
		Role r=null;
		int a1=0, a2=0;
		if(!allDepartments.isEmpty()) {
			for(int i=0; i<allDepartments.size(); i++) {
				if(!allDepartments.elementAt(i).allRolles.isEmpty()) {
					for(int j=0; j<allDepartments.elementAt(i).getAllRolles().size(); j++) {
						if(allDepartments.elementAt(i).getAllRolles().elementAt(j).getRoleName()==role) {
							r=allDepartments.elementAt(i).getAllRolles().elementAt(j);
							a1=i;
							a2=j;
						}
					}
				}
			}
		}

		if(type=="Base waged") {
			BaseWageEmployee emp=new BaseWageEmployee(name, iD, year, r, e, b, hourlyWage);
			SetSynchronizations(r, emp);
			allEmployees.addElement(emp);
			allDepartments.elementAt(a1).getAllRolles().elementAt(a2).addRoleEmployee(emp);
			return true;
		}
		else if(type=="Hourly waged") {
			HourlyWageEmployee emp=new HourlyWageEmployee(name, iD, year, r, e, b, hourlyWage);
			SetSynchronizations(r, emp);
			allEmployees.addElement(emp);
			allDepartments.elementAt(a1).getAllRolles().elementAt(a2).addRoleEmployee(emp);
			return true;
		}
		else if(type=="Based + sales"){
			BaseWageNSalesEmployee emp=new BaseWageNSalesEmployee(name, iD, year, r, e, b, hourlyWage);
			SetSynchronizations(r, emp);
			double sales=(double)(3000*Math.random());
			emp.setPercentageOfSales(salesPercent);
			emp.setSales(sales);
			allEmployees.addElement(emp);
			allDepartments.elementAt(a1).getAllRolles().elementAt(a2).addRoleEmployee(emp);
			return true;
		}
		return false;

	}

	public void calcSalaries() {
		if(!allEmployees.isEmpty()) {
			for(int i=0; i<allEmployees.size(); i++) {
				allEmployees.elementAt(i).calcSalary();
			}
		}
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public int getEstablishedYear() {
		return establishedYear;
	}

	public void setEstablishedYear(int establishedYear) {
		this.establishedYear = establishedYear;
	}


	public Vector<Department> getAllDepartments() {
		return allDepartments;
	}


	public void setAllDepartments(Vector<Department> allDepartments) {
		this.allDepartments = allDepartments;
	}


	public Vector<Employee> getAllEmployees() {
		return allEmployees;
	}


	public void setAllEmployees(Vector<Employee> allEmployees) {
		this.allEmployees = allEmployees;
	}


	public double getAverageProffitSingleEmployeePerHour() {
		return averageProffitSingleEmployeePerHour;
	}


	public void setAverageProffitSingleEmployeePerHour(double averageProffitSingleEmployeePerHour) {
		this.averageProffitSingleEmployeePerHour = averageProffitSingleEmployeePerHour;
	}


	public Vector<Integer> getEarlyStart() {
		return earlyStart;
	}


	public void setEarlyStart(Vector<Integer> earlyStart) {
		this.earlyStart = earlyStart;
	}


	public Vector<Integer> getLateStart() {
		return lateStart;
	}


	public void setLateStart(Vector<Integer> lateStart) {
		this.lateStart = lateStart;
	}


	public Vector<Integer> getNotPreferred() {
		return notPreferred;
	}


	public void setNotPreferred(Vector<Integer> notPreferred) {
		this.notPreferred = notPreferred;
	}


	public int getBufferProperHours() {
		return bufferProperHours;
	}


	public void setBufferProperHours(int bufferProperHours) {
		this.bufferProperHours = bufferProperHours;
	}

	//***//
	//check if elements exists
	/*public <T> boolean checkIfElementExists(Vector <T> allElements,String str) {
		if(!allElements.isEmpty()) {
			for(int i=0; i<allElements.size();i++) {
				if(((Company)allElements.elementAt(i)).getAllDepartments().elementAt(i).getDepartmentName()==str) {
					return true;
				}
			}
		}
		return false;
	}*/

	public boolean checkIfIDExists(String id) {
		if(!allEmployees.isEmpty()) {
			for(int i=0; i<allEmployees.size();i++) {
				if(allEmployees.elementAt(i).getId().equals(id)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkIfDepExists(String name) {
		if(!allDepartments.isEmpty()) {
			for(int i=0; i<allDepartments.size();i++) {
				if(allDepartments.elementAt(i).getDepartmentName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkIfRoleExists(String name) {
		if(!allDepartments.isEmpty()) {
			for(int i=0; i<allDepartments.size();i++) {
				if(!allDepartments.elementAt(i).getAllRolles().isEmpty()) {
					for(int j=0;j<allDepartments.elementAt(i).getAllRolles().size();j++) {
						if(allDepartments.elementAt(i).getAllRolles().elementAt(j).getRoleName().equals(name)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public Employee SetSynchronizations(Role r, Employee emp) {
		if(!(r==null)) {
			if(r.getDep() instanceof IsSynchronizable) {
				emp.setStartTime(r.getDep().getStartTimeDepartment());
				emp.setFinishTime(r.getDep().getFinishTimeDepartment());
			}
			if(r instanceof IsSynchronizable) {
				emp.setStartTime(r.getStartTimeRole());
				emp.setFinishTime(r.getFinishTimeRole());
			}
		}
		return emp;
	}

	public boolean checkRoleSynchronization(Role r) {
		if(!(r==null)) {
			if(r instanceof IsSynchronizable) {
				return true;
			}
		}
		return false;
	}

	public void resetProfitData() {
		this.earlyStart.clear();
		this.lateStart.clear();
		this.notPreferred.clear();
		this.earlyStart = new Vector <Integer>(8);
		for(int i=0;i<8;i++) {
			earlyStart.add(0);
		}
		this.lateStart =  new Vector <Integer>(24);
		for(int i=0;i<24;i++) {
			lateStart.add(0);
		}
		this.notPreferred =  new Vector <Integer>(15);
		for(int i=0;i<15 ;i++) {
			notPreferred.add(0);
		}
		this.bufferDefaultHours=0;
		this.bufferHomeWorkingHours=0;
		this.bufferProperHours = 0;
		this.totalProfitsPerDay=0;
	}

	public String printDepartments() {
		StringBuffer stringBuffer = new StringBuffer();
		if(!allDepartments.isEmpty()) {
			for(int i=0; i<allDepartments.size();i++) {
				stringBuffer.append(allDepartments.elementAt(i)+"\n\n");
			}
		}
		return stringBuffer.toString();
	}

	public String printRoles() {
		StringBuffer stringBuffer = new StringBuffer();
		if(!allDepartments.isEmpty()) {
			for(int i=0; i<allDepartments.size();i++) {
				if(!allDepartments.elementAt(i).getAllRolles().isEmpty()) {
					for(int j=0; j<allDepartments.elementAt(i).getAllRolles().size();j++) {
						stringBuffer.append(allDepartments.elementAt(i).getAllRolles().elementAt(j)+"\n\n");
					}
				}

			}
		}
		return stringBuffer.toString();

	}

	public String printEmployees() {
		StringBuffer stringBuffer = new StringBuffer();
		if(!allEmployees.isEmpty()) {
			for(int i=0; i<allEmployees.size();i++) {
				stringBuffer.append(allEmployees.elementAt(i) + "\n\n");
			}
		}
		return stringBuffer.toString();
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(companyName +" Company\n Established year: " + establishedYear + "\nDepartments:\n"
				+ allDepartments + "\nEmployees:\n" + allEmployees + "\nAverage Proffit Single Employee Per Hour:"
				+ averageProffitSingleEmployeePerHour);
		stringBuffer.append("\nEmployees hours distribution:\n------------------------\n");
		if(!earlyStart.isEmpty()) {
			stringBuffer.append("\nEmployees asked to start early and were signed to:");
			for(int i=0; i<earlyStart.size();i++) {
				if(earlyStart.elementAt(i)>0)
					stringBuffer.append("By " + i + " hours: " + earlyStart.elementAt(i) + "employees");
			}
		}
		if(!lateStart.isEmpty()) {
			stringBuffer.append("\nEmployees asked to start late and were signed to:");
			for(int i=0; i<lateStart.size();i++) {
				if(lateStart.elementAt(i)>0)
					stringBuffer.append("By " + i + " hours: " + lateStart.elementAt(i) + "employees");
			}
		}
		if(bufferDefaultHours>0) {
			stringBuffer.append("\nEmployees asked to start and finish default hours and were signed to, total of: " +bufferDefaultHours + " hours");
		}
		if(bufferHomeWorkingHours>0) {
			stringBuffer.append("\nEmployees asked to work from home and were signed to, total of: " + bufferHomeWorkingHours + " hours");
		}
		return stringBuffer.toString();
	}

	public String printDailyProfits() {
		calcProffitsPerDay();
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Total daily profit: " + totalProfitsPerDay + "\n-------------------------\n\n");
		stringBuffer.append("\nEmployees hours distribution:\n------------------------\n");

		if(!earlyStart.isEmpty()) {
			stringBuffer.append("\nEmployees asked to start early and were signed to:");
			for(int i=0; i<earlyStart.size();i++) {
				if(earlyStart.elementAt(i)>0)
					stringBuffer.append("By " + i + " hours: " + earlyStart.elementAt(i) + "employees");
			}
		}
		if(!lateStart.isEmpty()) {
			stringBuffer.append("\nEmployees asked to start late and were signed to:");
			for(int i=0; i<lateStart.size();i++) {
				if(lateStart.elementAt(i)>0)
					stringBuffer.append("By " + i + " hours: " + lateStart.elementAt(i) + "employees");
			}
		}
		if(bufferDefaultHours>0) {
			stringBuffer.append("\nEmployees asked to start and finish default hours and were signed to, total of: " +bufferDefaultHours + " hours");
		}
		if(bufferHomeWorkingHours>0) {
			stringBuffer.append("\nEmployees asked to work from home and were signed to, total of: " +bufferHomeWorkingHours + " hours");
		}
		return stringBuffer.toString();
	}




}
