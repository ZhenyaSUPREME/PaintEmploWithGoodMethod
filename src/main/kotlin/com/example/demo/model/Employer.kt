package com.example.demo.model


import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Employer(@Id var id:String, var name:String, var salary: Int){
    var organizationsId:Int? = null
    var departmentsId:Int? = null
    constructor(id:String,name:String,salary: Int,organizationsId:Int,departmentsId:Int):this(id,name,salary) {
        this.organizationsId = organizationsId
        this.departmentsId = departmentsId
    }
    constructor(id:String,name:String,salary: Int,organizationsId: Int):this(id,name,salary){
        this.organizationsId = organizationsId
    }
}