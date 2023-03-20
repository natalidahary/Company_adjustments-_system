package finalProject;

public class BaseWageNSalesEmployee extends Employee {
	
	protected double sales;
	protected double percentageOfSales;
	
	

	public BaseWageNSalesEmployee(String employeeName, String id, int year, Role myRole, ePreferences preference,
			boolean isWorkingFromHomeToday, double hourelyWage) throws Exception {
		super(employeeName, id, year, myRole, preference, hourelyWage);
		this.sales = 0;
		this.percentageOfSales=0;
		this.salary=0;
	}

	public double getSales() {
		return sales;
	}

	public void setSales(double sales) {
		this.sales = sales;
	}
	
	
	public double getPercentageOfSales() {
		return percentageOfSales;
	}

	public void setPercentageOfSales(double percentageOfSales) {
		this.percentageOfSales = percentageOfSales;
	}

	@Override
	public void calcSalary() {
		setNumOfHours(finishTime-startTime);
		this.salary=(hourlyWage*numOfHours)+(sales*percentageOfSales)/100;
	}

	@Override
	public String toString() {
		return "\nName: " + employeeName + "\nID: " + id + "\nserialNumber: " + serialNumber + "\nYear of birth: "
				+ year + "\nRole: " + myRole.roleName + "\nPreferring: " + preference + "\nAmount of hours: " + numOfHours
				+"\nStart time: " + startTime + "\nFinish time: " + finishTime
				+ "\nIs working from home today? " + isWorkingFromHomeToday + "\nSales: " + sales + "\nPercentage of sales:" + percentageOfSales+ "%" + "\nHourlyWage: " + hourlyWage + "\nsalary:" +salary;
	//	return "\nBase Wage sales employee: " + sales + "\nPercentage of sales" + percentageOfSales;
	}
	
	

	

	

	
	

}
