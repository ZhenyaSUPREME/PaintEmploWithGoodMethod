package com.example.demo.repository

import com.example.demo.model.Departments
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.repository.reactive.ReactiveCrudRepository

@Document
interface DepartmentsRepository: ReactiveCrudRepository<Departments, String> {

}