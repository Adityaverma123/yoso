package com.yoso.ecom.product.services

import com.yoso.ecom.product.models.Product
import org.springframework.stereotype.Service


interface ProductService {
    fun getAllProducts(): List<Product>
    fun getProductById(id: String): Product
    fun getByProductIds(ids: List<String>): List<Product>
}