package com.example.demo.controller

import com.example.demo.repository.EmployerRepository
import com.example.demo.model.Employer
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/api/employer")
class EmployerController(var employerRepository: EmployerRepository){
    @GetMapping
    fun getAllEmployer(): Flux<Employer> {
        return employerRepository.findAll()
    }
    @GetMapping("/{id}")
    fun getOneId(@PathVariable id:String):Mono<Employer>{
        return employerRepository.findById(id)
    }
    @PostMapping
    fun createEmployee(@RequestBody employer: Employer): Mono<Employer> {
        return employerRepository.save(employer)
    }
        @PutMapping("/{id}")
        fun updateOneList(@RequestBody employer: Employer, @PathVariable id:String):Mono<Employer>{
            return employerRepository.findById(id).flatMap {t ->
               t.salary = employer.salary
                t.name = employer.name
               employerRepository.save(t)
            }
        }
    @DeleteMapping("/{id}")
    fun deleteAllList(@PathVariable id:String): Mono<String> {
        println(id)
       var d = employerRepository.deleteById(id).doOnSuccess { x->"deleted" }.subscribe()
        return Mono.just("deleted $id")
    }
}