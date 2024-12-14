package com.yoso.ecom.product.models

data class Product(
    val id: String,
    var name: String,
    var priceInInr: Int,
    var availableQuantity: Int,
    var isActive: Boolean
)
