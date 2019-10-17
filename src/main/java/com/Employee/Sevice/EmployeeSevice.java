package com.Employee.Sevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Component;

import com.Employee.Model.Address;
import com.Employee.Model.Department;
import com.Employee.Model.Employee;
import com.Employee.Model.EmployeeTo;

@Component
public class EmployeeSevice {

	
	public List<EmployeeTo> convertEmployeeTo(List<Employee> empl, List<Department> depts, List<Address> addrs){
		List<EmployeeTo> list=new ArrayList<EmployeeTo>();
		EmployeeTo emp=null;
		for (Employee employee : empl) {
			emp=new EmployeeTo();
			emp.setId(employee.getId());
			emp.setName(employee.getName());
			for (Department department : depts) {
				if(null !=employee.getDeptid() && employee.getDeptid().equals(Integer.parseInt(department.getId()))) {
					emp.setDepartment(department.getDeptName());
					break;
				}
			}
			for (Address address : addrs) {
				if(null != employee.getAddressid() && employee.getAddressid().equals(address.getId())) {
					emp.setAddress(address.getCityName() +" - "+address.getStateName());
					break;
				}
			}
			list.add(emp);
		}
		return list;
	}
	
}
