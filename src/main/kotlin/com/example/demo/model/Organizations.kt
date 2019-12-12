package com.example.demo.model

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Organizations (var id:String, var name:String) {


    var employees: MutableList<Employer> = ArrayList()
    var departments: MutableList<Departments> = ArrayList()

    constructor(id: String, name: String, employees: MutableList<Employer>, departments: MutableList<Departments>) : this(id, name) {
        this.employees.addAll(employees)
        this.departments.addAll(departments)
    }

    constructor(id: String, name: String, employees: MutableList<Employer>) : this(id, name) {
        this.employees.addAll(employees)
    }
    fun addEmployees(employees: MutableList<Employer>): Organizations {
        this.employees.addAll(employees)
        return this
    }
    fun addEmployee(employer: Employer): Organizations {
        this.employees.add(employer)
        return this
    }
    fun addDepartments(departments: MutableList<Departments>): Organizations {
        this.departments.addAll(departments)
        return this
    }
    fun addDepartment(departments: Departments): Organizations {
        this.departments.add(departments)
        return this
    }

}

