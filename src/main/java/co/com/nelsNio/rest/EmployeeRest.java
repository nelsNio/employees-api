package co.com.nelsNio.rest;


import co.com.nelsNio.controller.EmployeeController;
import co.com.nelsNio.model.Employee;
import co.com.nelsNio.model.Salary;
import co.com.nelsNio.repo.IEmployeeRepo;
import net.minidev.json.JSONObject;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeRest extends ResponseEntityExceptionHandler {
	
	
    @Autowired
    private IEmployeeRepo iEmployeeRepo;
    
    @Autowired
    private EmployeeController empController;

    /**
     *
     * @return list Employees
     */
    @GetMapping
    public ResponseEntity<List<Employee>> list(){
        System.out.println("LISTAR");
        return ResponseEntity.ok(iEmployeeRepo.findAll());
    }

    @GetMapping(value = "/{id}")
    public Optional<Employee> retrieve(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return iEmployeeRepo.findById(id);
    }

    /**
     *
     * @param employee
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> insert(@Valid @RequestBody Employee employee){
        
            Employee newEmployee= iEmployeeRepo.save(employee);
            return  ResponseEntity.ok(newEmployee);
    }
    

    /**
     * Method to calculate salary 
     * @param id
     * @param employee
     * @return
     */
    @PostMapping(value = "/pay/")
    public ResponseEntity<Object> pay(  @RequestBody Salary salary){
    	
    	Optional<Employee> employee = iEmployeeRepo.findById(salary.getIdEmployee());
    	if (employee.isPresent()) {
    		JSONObject jsonObject = new JSONObject();
        	jsonObject.put("salary", empController.salary(employee.get(),salary));
            return  ResponseEntity.ok(jsonObject);
    	}else {
    		return ResponseEntity.notFound().build();
    	}
    	
    	
    	
    }
    
    /**
     * 
     * @param id
     * @param employee
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Employee> put( @PathVariable("id") Long id, @RequestBody Employee employee){
    	Optional<Employee> employeeOld = iEmployeeRepo.findById(id);
    	if (employeeOld.isPresent()) {
    		
    		Employee newEmployee= iEmployeeRepo.save(employee);
    		return  ResponseEntity.ok(newEmployee);
    	}else {
    		return ResponseEntity.notFound().build();
    	}

    }
    
    
    /***
     * 
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{idEmployee}")
	public ResponseEntity<Void> delete(@PathVariable("idEmployee") Long id) {
    	Optional<Employee> employeeOld = iEmployeeRepo.findById(id);
    	if (employeeOld.isPresent()) {
    		iEmployeeRepo.deleteById(id);
    		return  ResponseEntity.ok(null);

    	}else {
    		return ResponseEntity.notFound().build();
    	}
	}



}
