package net.javaguides.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public List<Employee> getAllEmployees()
	{
		return employeeRepository.findAll();
		
	}

	@Override
	public void saveEmployee(Employee emp) {
		employeeRepository.save(emp);
		
	}

	@Override
	public Employee getEmployeeById(long id)
	{
		Optional<Employee> op=employeeRepository.findById(id);
	    Employee emp;
	    if(op.isEmpty())
	    {
	    	throw new RuntimeException("Not found employee for id : "+id);
	    }
	    else
	    {
	    	emp=op.get();
	    }
	    return emp;
	}

	@Override
	public void deleteEmployeeById(long id)
	{
		employeeRepository.deleteById(id);
		
	}

	public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.employeeRepository.findAll(pageable);
	}

}
