package com.yoso.ecom.look.controllers

import com.ecom.controllers.LooksApi
import com.ecom.model.LookDetails
import com.ecom.model.LookRequest
import com.ecom.model.UserLook
import com.yoso.ecom.look.services.LookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class LookController(
    private val lookService: LookService
): LooksApi {
    override fun createLook(lookRequest: LookRequest): ResponseEntity<Unit> {
        lookService.createLook(lookRequest)
        return ResponseEntity(HttpStatus.CREATED)
    }

    override fun updateLook(id: String, lookRequest: LookRequest): ResponseEntity<String> {
        lookService.updateLook(id, lookRequest)
        return ResponseEntity.ok("Look: $id successfully updated")
    }

    override fun getAllLooks(): ResponseEntity<List<LookDetails>> {
        return ResponseEntity.ok(lookService.getAllLooks())
    }

    override fun getLook(id: String): ResponseEntity<LookDetails> {
        return ResponseEntity.ok(lookService.getLook(id))
    }

    override fun getAllEligibleLooks(): ResponseEntity<List<UserLook>> {
        return ResponseEntity.ok(lookService.getUserLooks())
    }
}