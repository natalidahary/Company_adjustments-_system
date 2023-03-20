package finalProject;

public class HourlyWageEmployee extends Employee {

	public HourlyWageEmployee(String employeeName, String id, int year, Role myRole, ePreferences preference,
			boolean isWorkingFromHomeToday, double hourelyWage) throws Exception {
		super(employeeName, id, year, myRole, preference, hourelyWage);
		this.salary=0;
	}

	
	@Override
	public void calcSalary() {
		setNumOfHours(finishTime-startTime);
		this.salary=hourlyWage*numOfHours;
	}
	

	

}
