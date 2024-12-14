package com.yoso.ecom.look.mappers

import com.ecom.model.*
import com.yoso.ecom.look.dao.documents.Look
import com.yoso.ecom.product.models.Product

fun LookRequest.toLook(product: Map<String, Product>): Look {
    val products = this.products?.map { it.toLookProductDetails(product) }
    return Look(
        name = this.name,
        description = this.description,
        isActive = this.isActive,
        discountedPrice = this.discountedPrice,
        availableUnits = calculateAvailableUnits(products),
        products = this.products
    )
}

fun Look.toLookDetails(product: Map<String, Product>): LookDetails {
    val products = this.products?.map { it.toLookProductDetails(product) }
    return LookDetails(
        id = this.id,
        name = this.name,
        isActive = this.isActive,
        description = this.description,
        discountedPrice = this.discountedPrice,
        products = products,
        availableUnits = this.availableUnits ?: calculateAvailableUnits(products)
    )
}

fun calculateAvailableUnits(lookProduct: List<LookProductDetails>?): Int {
    var availableUnits = Integer.MAX_VALUE
    lookProduct?.forEach { availableUnits = Math.min(it.availableUnits!!.div(it.minQuantity!!), availableUnits) }
    return availableUnits
}

fun LookProduct.toLookProductDetails(productMap: Map<String, Product>): LookProductDetails {
    val product = productMap.get(this.productId)
    return LookProductDetails(
        productId = this.productId,
        minQuantity = this.minQuantity,
        availableUnits =  product?.availableQuantity
    )
}

fun Look.toUserLook(productMap: Map<String, Product>): UserLook {
    var totalPrice = 0
    this.products?.forEach {
        val product = productMap[it.productId]
        totalPrice+=product?.priceInInr ?: 0
    }
    val discountedPrice = totalPrice - (this.discountedPrice ?: 0)
    return UserLook(
        name = this.name,
        description = this.description,
        totalPrice = totalPrice,
        discountedPrice = discountedPrice,
        products = this.products?.map { it.toProductDetails(productMap) }
    )
}

fun LookProduct.toProductDetails(productMap: Map<String, Product>): ProductDetails {
    val product = productMap[this.productId]
    return ProductDetails(
        id = product?.id,
        name =  product?.name,
        availableUnits = product?.availableQuantity,
        priceInInr = product?.priceInInr
    )
}