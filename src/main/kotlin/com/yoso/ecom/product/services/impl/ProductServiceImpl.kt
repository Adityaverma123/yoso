package com.yoso.ecom.product.services.impl

import com.yoso.ecom.product.models.Product
import com.yoso.ecom.product.services.ProductService
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl: ProductService {
    val products = listOf(
        Product(
            id = "1",
            name = "Jacket",
            priceInInr = 5000,
            availableQuantity = 100,
            isActive = true
        ),
        Product(
            id = "2",
            name = "Socks",
            priceInInr = 200,
            availableQuantity = 200,
            isActive = true
        ),
        Product(
            id = "3",
            name = "Tshirt",
            priceInInr = 1000,
            availableQuantity = 200,
            isActive = true
        ),
        Product(
            id = "4",
            name = "Jeans",
            priceInInr = 2000,
            availableQuantity = 50,
            isActive = true
        )
    )
    override fun getAllProducts(): List<Product> {
        return products
    }

    override fun getProductById(id: String): Product {
        return products.find { it.id == id } ?: throw Exception("Product: $id not found")
    }

    override fun getByProductIds(ids: List<String>): List<Product> {
        return products.filter { it.id in ids }
    }
}