package com.dnyanesh.learn.crudjdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dnyanesh.learn.crudjdbc.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
