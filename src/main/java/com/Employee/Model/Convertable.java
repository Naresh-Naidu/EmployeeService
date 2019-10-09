package com.Employee.Model;

import java.util.List;

public class Convertable {
	
	private List<Employee> employees;
	private List<Department> departments;
	private List<Address> address;
	
	
	public Convertable() {
		
	}
	
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	public List<Department> getDepartments() {
		return departments;
	}
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	public List<Address> getAddress() {
		return address;
	}
	public void setAddress(List<Address> address) {
		this.address = address;
	}
	
	
}
