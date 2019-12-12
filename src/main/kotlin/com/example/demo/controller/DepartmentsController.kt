package com.example.demo.controller

import com.example.demo.repository.DepartmentsRepository
import com.example.demo.repository.EmployerRepository
import com.example.demo.model.Departments
import com.example.demo.model.Employer
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/departments")
class DepartmentsController(var departmentsRepository: DepartmentsRepository, var employerRepository: EmployerRepository){
    @GetMapping
    fun getAllList(): Flux<Departments>{
        return departmentsRepository.findAll()
    }
    @GetMapping("/{id}")
    fun getOneId(@PathVariable id:String):Mono<Departments>{
        return departmentsRepository.findById(id)
    }
    @GetMapping("/add/{iddepartments}/{idemployer}")
    fun addEmployerAndDepartments(@PathVariable iddepartments:String,@PathVariable idemployer:String): Mono<Departments> {
       return departmentsRepository.findById(iddepartments)
       .zipWith(employerRepository.findById(idemployer))
              .flatMap{ t->t.t1.addEmployee(t.t2)
              departmentsRepository.save(t.t1)}
    }
    @PostMapping("/add/{iddepartments}")
    fun addEmployerToDepartmens(@PathVariable id:String,@RequestBody employer: Employer):Mono<Departments>{
        return departmentsRepository.findById(id)
                .zipWith(employerRepository.save(employer))
                .flatMap { t->t.t1.addEmployee(t.t2)
                departmentsRepository.save(t.t1)}
    }
    @PostMapping
    fun createOneList(@RequestBody departmental: Departments): Mono<Departments>{
        return departmentsRepository.save(departmental)
    }
    @PutMapping("/{id}")
    fun updateOneList(@RequestBody departmental: Departments, @PathVariable id:String):Mono<Departments>{
        return departmentsRepository.findById(id).flatMap { t->
            t.id = departmental.id
            t.name = departmental.name
            departmentsRepository.save(t)
        }
    }

    @DeleteMapping("{id}")
    fun deleteOneList(@PathVariable id:String):Mono<String>{
        println(id)
        var r = departmentsRepository.deleteById(id).doOnSuccess{x->"deleted"}.subscribe()
        return Mono.just("delete $id")
    }
}