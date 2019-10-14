package com.Employee.Repository;

import org.springframework.stereotype.Repository;

import com.Employee.Model.Employee;
import com.microsoft.azure.spring.data.cosmosdb.repository.DocumentDbRepository;

@Repository
public interface EmployeeRepository extends DocumentDbRepository<Employee, Integer>
{

	
}
