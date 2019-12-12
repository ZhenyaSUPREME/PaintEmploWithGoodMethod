package com.example.demo.repository

import com.example.demo.model.Organizations
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface OrganizationsRepository: ReactiveMongoRepository<Organizations,String>