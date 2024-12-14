package com.yoso.ecom.look.dao.documents

import com.ecom.model.LookProduct
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Look(
    @Id
    val id: String? = null,
    @Indexed(unique = true)
    var name: String? = null,
    var isActive: Boolean? = null,
    var description: String? = null,
    var availableUnits: Int? = null,
    var discountedPrice: Int? = null,
    var products: List<LookProduct>? = null
)