package com.yoso.ecom.look.services

import com.ecom.model.LookDetails
import com.ecom.model.LookRequest
import com.ecom.model.UserLook

interface LookService {
    fun createLook(lookRequest: LookRequest)
    fun updateLook(lookId: String, lookRequest: LookRequest): String
    fun getLook(id: String): LookDetails
    fun getAllLooks(): List<LookDetails>
    fun getUserLooks(): List<UserLook>
}