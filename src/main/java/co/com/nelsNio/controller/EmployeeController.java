/**
 * 
 */
package co.com.nelsNio.controller;


import java.time.LocalDate;

import org.springframework.stereotype.Service;

import co.com.nelsNio.model.Employee;
import co.com.nelsNio.model.Salary;

/**
 * @author andresnino
 *
 */
@Service
public class EmployeeController {
	
	/**
	 * 
	 * @param employee
	 * @return
	 */
	public double salary(Employee employee,Salary salary) {
		
		if (!isWorking(employee, salary)){
			return 0;
		}
		else {
			// Caso para liquidar de nuevo ingreso
			if(salary.getMonth()==employee.getAdmissionDate().getMonthValue()
					&&salary.getYear()==employee.getAdmissionDate().getYear()) {
				return (employee.getBaseSalary().doubleValue()/30)*(30-employee.getAdmissionDate().getDayOfMonth());
			}else {
				// empleado vigente
				return employee.getBaseSalary().doubleValue();
			}
					
					
		}		
	}
	
	/**
	 * 
	 * @param employee
	 * @param year
	 * @param month
	 * @return
	 */
	private boolean isWorking(Employee employee,Salary salary) {
		LocalDate datePay = LocalDate.of(salary.getYear(), salary.getMonth(), 30);
		boolean isWorking= false;
		
		isWorking=(employee.getAdmissionDate().isBefore(datePay) 
				&&(employee.getRetirementDate()==null
						||employee.getRetirementDate().isAfter(datePay)));
		return isWorking;

	}
	
	
	 

}
