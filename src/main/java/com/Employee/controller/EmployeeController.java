package com.Employee.controller;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.Employee.Model.Address;
import com.Employee.Model.Convertable;
import com.Employee.Model.Department;
import com.Employee.Model.Employee;
import com.Employee.Model.EmployeeTo;
import com.Employee.Model.ParserConvertable;
import com.Employee.Sevice.EmployeeSevice;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class EmployeeController {

	@Autowired
	private EmployeeSevice service;
	
	@Autowired
	private RestTemplate template;
	
	private List<Employee> employeeList=new ArrayList<Employee>();
	private Set<Employee> employeeSet=null;
	
	@GetMapping(value = "getAllEmployee", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EmployeeTo> getAllEmployee(){
		//List<Department> departments = (List<Department>) template.getForObject("http://localhost:8081/getAllDepartment", Department.class); 
		
		ResponseEntity<List<Department>> deptResponse = template.exchange("http://department-service/getAllDepartment",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Department>>() {
				}); 
		
		List<Department> departments=deptResponse.getBody();
		// List<Employee> employees=getEmployees();
		ResponseEntity<List<Address>> addressResponse = template.exchange("http://address-service/getAllAddress",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Address>>() {
				}); 
		List<Address> addressList=addressResponse.getBody();
		
		return convertEmployeeTo(createEmployees(), departments, addressList);
		
		
		//return employees;
		
	}
	
	@GetMapping("/")
	public String getHello() {
		return "hello world";
	}
	
	public List<Employee> createEmployees(){
		employeeList.addAll(Arrays.asList(
				new Employee(1, "Naresh", 11, 22),
				new Employee(2, "Ramesh", 11, 23),
				new Employee(3, "Suresh", 13, 21)
				));
		
		employeeSet=new HashSet<Employee>(employeeList);
		employeeList.clear();
		 employeeList.addAll(employeeSet);
		return employeeList;		
	}
	
	public List<EmployeeTo> convertEmployeeTo(List<Employee> empl, List<Department> depts, List<Address> addrs){
		return service.convertEmployeeTo(empl, depts, addrs);
	}
	
	/*
	 * @Scheduled(fixedRate = //1800000 60000 ) public void addAddress() { Employee
	 * e =new Employee(employeeList.size()+1, "xyz", 21, 23); employeeList.add(e); }
	 * 
	 * 
	 * public void addEmployee() { Employee e =new Employee(employeeList.size()+1,
	 * "xyz", 21, 23); employeeList.add(e); }
	 */
	
	@PostMapping("addEmployee")
	public Employee addEmployee(@RequestBody Employee emp) {
		employeeList.add(emp);
		return emp;
	}
	
	@GetMapping(value = "employees" , produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getEmployees(){
		template.exchange("http://employee-Service/getAllEmployee", HttpMethod.GET, null, new ParameterizedTypeReference<List<EmployeeTo>>() {}); 
		return employeeList;
	}
	
	@GetMapping(value =  "convert/{convertable}",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EmployeeTo> getRequestConvert(@PathVariable("convertable") String convertable){
		System.out.println("calling to employee service");
		System.out.println(convertable);
		/*
		 * ParserConvertable con=null; try { System.out.println("Converting......");
		 * con= new ObjectMapper().readValue(obj, ParserConvertable.class);
		 * System.out.println("Converted"); } catch (JsonParseException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch (JsonMappingException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		
		List<EmployeeTo> li=new ArrayList<EmployeeTo>();
		 return li;
	}
	
	@GetMapping("employeeByDept/{deptid}")
	public List<Employee> getEmployeesBYdept(
			@PathVariable("deptid") Integer deptid
			) {
		//Integer deptId=Integer.parseInt(deptid);
		//template.exchange("http://employee-Service/getAllEmployee", HttpMethod.GET, null, new ParameterizedTypeReference<List<EmployeeTo>>() {});
		getAllEmployee();
		List<Employee> empList=new ArrayList<Employee>();
		for (Employee employee : employeeList) {
			if(employee.getDeptid().equals(deptid)) {
				empList.add(employee);
			}
		}
		
		return empList;
		
	}
}
