package com.yoso.ecom.look.dao.repositories

import com.ecom.model.LookProduct
import com.yoso.ecom.look.dao.documents.Look
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface LookRepository: MongoRepository<Look, String> {
    @Query("{ 'isActive': ?0, 'availableUnits': { \$gt: ?1 } }")
    fun findActiveLooksWithAvailableUnits(isActive: Boolean = true, minUnits: Int = 0): List<Look>
}