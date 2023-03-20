package finalProject;

import java.io.Serializable;

import Exception.IDException;

public class Employee implements Serializable{

	protected String employeeName;
	protected String id;
	protected int serialNumber;
	protected static int serialGenerator=1000;
	protected int year;
	protected Role myRole;
	public enum ePreferences {EARLY_START , LATE_START, DEFAULT, HOME}
	protected ePreferences preference;
	protected int numOfHours;
	protected int startTime;
	protected int finishTime;
	protected boolean isWorkingFromHomeToday;
	public double hourlyWage;
	protected double salary;

	public Employee(String employeeName, String id, int year, Role myRole, ePreferences preference, double hourlyWage) throws Exception {
		this.employeeName = employeeName;
		this.id = id;
		this.serialNumber = serialGenerator++;
		this.year = year;
		this.myRole = myRole;
		this.preference = preference;
		this.numOfHours = 0;
		this.startTime = 8;
		this.finishTime = 17;
		this.isWorkingFromHomeToday = false;
		this.hourlyWage=hourlyWage;
		this.salary=0;
	}

	public void calcSalary() {
		setNumOfHours(finishTime-startTime);
		this.salary=hourlyWage*numOfHours;
	}


	public boolean isWorkingFromHomeToday() {
		return isWorkingFromHomeToday;
	}


	public void setWorkingFromHomeToday(boolean isWorkingFromHomeToday) {
		this.isWorkingFromHomeToday = isWorkingFromHomeToday;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getId() {
		return id;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public static int getSerialGenerator() {
		return serialGenerator;
	}

	public int getYear() {
		return year;
	}

	public Role getMyRole() {
		return myRole;
	}

	public void setMyRole(Role myRole) {
		this.myRole = myRole;
	}

	public ePreferences getPreference() {
		return preference;
	}

	public void setPreference(ePreferences preference) {
		this.preference = preference;
	}

	public int getNumOfHours() {
		return numOfHours;
	}

	public void setNumOfHours(int numOfHours) {
		this.numOfHours = numOfHours;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}

	public String showPreference() {
		if(preference.equals(ePreferences.DEFAULT)){
			return "Default working hours";
		}
		else if(preference.equals(ePreferences.EARLY_START)) {
			return "Early start";
		}
		else if(preference.equals(ePreferences.LATE_START)) {
			return "Late start";
		}
		else
			return "Work from home";
	}

	@Override
	public String toString() {
		return "\nName: " + employeeName + "\nID: " + id + "\nserialNumber: " + serialNumber + "\nYear of birth: "
				+ year + "\nRole: " + myRole.roleName + "\nPreferring: " + preference + "\nAmount of hours: " + numOfHours
				+"\nStart time: " + startTime + "\nFinish time: " + finishTime
				+ "\nIs working from home today? " + isWorkingFromHomeToday + "\nHourlyWage: " + hourlyWage + "\nsalary:" +salary;
	} 


}
