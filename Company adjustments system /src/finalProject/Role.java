package finalProject;

import java.io.Serializable;
import java.util.Vector;

public class Role implements RestrictedHours , Serializable, IsSynchronizable, IsChangeable{
	protected boolean IsSynchronizable;
	protected boolean IsChangeable;
	String roleName;
	Vector <Employee> roleEmployees;
	Department dep;
	protected int startTimeRole;
	protected int finishTimeRole;

	public Role(String roleName, Department dep) {
		this.roleName = roleName;
		this.roleEmployees = new Vector<Employee>();
		this.dep= dep;
		this.startTimeRole=0;
		this.finishTimeRole=0;
		this.IsSynchronizable=false;
		this.IsChangeable=false;
	}

	public void addRoleEmployee(Employee emp) {
		this.roleEmployees.add(emp);
	}


	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Vector<Employee> getRoleEmployees() {
		return roleEmployees;
	}


	public Department getDep() {
		return dep;
	}

	public int getStartTimeRole() {
		return startTimeRole;
	}

	public void setStartTimeRole(int startTimeRole) {
		this.startTimeRole = startTimeRole;
	}

	public int getFinishTimeRole() {
		return finishTimeRole;
	}

	public void setFinishTimeRole(int finishTimeRole) {
		this.finishTimeRole = finishTimeRole;
	}

	@Override
	public void isRestricted() {
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

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Role name: " + roleName +"\nDepartment: " + dep.departmentName);
		if(!roleEmployees.isEmpty()) {
			stringBuffer.append("Employees:");
			for(int i=0; i<roleEmployees.size();i++) {
				stringBuffer.append(roleEmployees.elementAt(i)+"\n");
			}
		}
		return stringBuffer.toString();
	}




}
