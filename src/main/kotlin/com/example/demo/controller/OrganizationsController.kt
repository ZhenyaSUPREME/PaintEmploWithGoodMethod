package com.example.demo.controller

import com.example.demo.repository.DepartmentsRepository
import com.example.demo.repository.EmployerRepository
import com.example.demo.repository.OrganizationsRepository
import com.example.demo.model.Departments
import com.example.demo.model.Employer
import com.example.demo.model.Organizations
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/organizations")
class OrganizationsController(var organizationsRepository: OrganizationsRepository, var employerRepository: EmployerRepository, var departmentsRepository: DepartmentsRepository){
    @GetMapping
    fun getAllList(): Flux<Organizations> {
        return organizationsRepository.findAll()
    }
    @GetMapping("/{id}")
    fun getOneId(@PathVariable id:String):Mono<Organizations>{
        return organizationsRepository.findById(id)
    }
    @GetMapping("/addd/{idorganzations}/{idemployer}")
    fun employerTakeToOrganizations(@PathVariable idorganzations:String,@PathVariable idemployer:String):Mono<Organizations> {
        return organizationsRepository.findById(idorganzations)
             .zipWith(employerRepository.findById(idemployer))
              .flatMap { t -> t.t1.addEmployee(t.t2)
                   organizationsRepository.save(t.t1)
               }
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
    @PostMapping("/add/{idorganizations}")
    fun addEmployerInOrganizations(@PathVariable id:String,@RequestBody employer: Employer):Mono<Organizations>{
        return organizationsRepository.findById(id)
                .zipWith(employerRepository.save(employer))
                .flatMap { t->t.t1.addEmployee(t.t2)
        organizationsRepository.save(t.t1)}
    }
        @PostMapping
        fun createOneClass(@RequestBody organizations: Organizations): Mono<Organizations> {
            return organizationsRepository.save(organizations)
        }

        @PutMapping("/{id}")
        fun updateOneList(@RequestBody organizations: Organizations, @PathVariable id: String): Mono<Organizations> {
            return organizationsRepository.findById(id).flatMap { t ->
                t.id = organizations.id
                t.name = organizations.name
                organizationsRepository.save(t)
            }
        }

        @DeleteMapping("/{id}")
        fun deleteOneClass(@PathVariable id: String): Mono<String> {
            println(id)
            var c = organizationsRepository.deleteById(id).doOnSuccess { x -> "deleted" }.subscribe()
            return Mono.just("deleted $id")
        }
    }