package finalProject;

import java.io.Serializable;
import java.util.Vector;

public class Department implements Serializable, IsSynchronizable, IsChangeable {
	protected boolean IsSynchronizable;
	protected boolean IsChangeable;
	protected String departmentName;
	protected Vector <Role> allRolles;
	protected int startTimeDepartment;
	protected int finishTimeDepartment;
	
	public Department(String departmentName, int startTimeDepartment, int finishTimeDepartment) {
		this.departmentName = departmentName;
		this.allRolles =new Vector<Role>();
		this.startTimeDepartment= startTimeDepartment;
		this.finishTimeDepartment= finishTimeDepartment;
		this.IsSynchronizable=false;
		this.IsChangeable=false;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}


	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}


	public Vector<Role> getAllRolles() {
		return allRolles;
	}


	public void addRole(Role r) {
		this.allRolles.add(r);
	}
	


	public int getStartTimeDepartment() {
		return startTimeDepartment;
	}


	public void setStartTimeDepartment(int startTimeDepartment) {
		this.startTimeDepartment = startTimeDepartment;
	}


	public int getFinishTimeDepartment() {
		return finishTimeDepartment;
	}


	public void setFinishTimeDepartment(int finishTimeDepartment) {
		this.finishTimeDepartment = finishTimeDepartment;
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Department name: " + departmentName + "\n" + "Synchronized? " +IsSynchronizable + "\nChangeable? " + IsChangeable + "\nStart time: " + startTimeDepartment + "\nFinish time" + finishTimeDepartment);
		if(!allRolles.isEmpty()) {
			stringBuffer.append("\nallRolles:\n---------\n" );
			for(int i=0; i<allRolles.size();i++) {
				stringBuffer.append(allRolles.elementAt(i)+"\n");
			}	
		}
		stringBuffer.append("\n--------------------------------");
		return stringBuffer.toString();

	}

	@Override
	public void synchronize() {
		this.IsSynchronizable=true;
	}
	@Override
	public void makeChangeable() {
		this.IsChangeable=true;
	}

	public boolean isIsChangeable() {
		return IsChangeable;
	}
	
	

}
