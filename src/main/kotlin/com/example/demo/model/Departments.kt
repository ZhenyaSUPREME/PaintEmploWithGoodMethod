package com.example.demo.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Departments(@Id var id:String?, var name:String?, var organizationsId: Int?){
    var employees:MutableList<Employer> = ArrayList()
    constructor(id:String,name:String,organizationsId: Int?,employees:MutableList<Employer>):this(id,name,organizationsId){
        this.employees.addAll(employees)
    }
    fun addEmployees(employees: MutableList<Employer>): Departments {
        this.employees.addAll(employees)
        return this
    }
    fun addEmployee(employer: Employer): Departments {
        this.employees.add(employer)
        return this
    }
}