package com.yoso.ecom.look.services.impl

import com.ecom.model.LookDetails
import com.ecom.model.LookRequest
import com.ecom.model.UserLook
import com.yoso.ecom.look.dao.repositories.LookRepository
import com.yoso.ecom.look.mappers.toLook
import com.yoso.ecom.look.mappers.toLookDetails
import com.yoso.ecom.look.mappers.toUserLook
import com.yoso.ecom.look.services.LookService
import com.yoso.ecom.product.services.ProductService
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class LookServiceImpl(
    private val lookRepository: LookRepository,
    private val productService: ProductService
): LookService {
    override fun createLook(lookRequest: LookRequest) {
        val productIds = lookRequest.products?.mapNotNull { it.productId } ?: emptyList()
        val products = productService.getByProductIds(productIds).associateBy({it.id}, {it})
        lookRepository.insert(lookRequest.toLook(products))
    }

    override fun updateLook(lookId: String, lookRequest: LookRequest): String {
       val look = lookRepository.findById(lookId).orElseThrow{Exception("LookId: $lookId not found")}
        look.name = lookRequest.name
        look.description = lookRequest.description
        look.discountedPrice = lookRequest.discountedPrice
        look.isActive = lookRequest.isActive
        look.products = lookRequest.products
       return lookRepository.save(look).id!!
    }

    override fun getLook(id: String): LookDetails {
        val look = lookRepository.findById(id).getOrElse { throw Exception("Look: $id not found") }
        val productIds = look.products?.mapNotNull { it.productId } ?: emptyList()
        val products = productService.getByProductIds(productIds).associateBy({it.id}, {it})
       return look.toLookDetails(products)
    }

    override fun getAllLooks(): List<LookDetails> {
        val look = lookRepository.findAll()
        val productIds = mutableSetOf<String>()
        look.forEach {
            if (!it.products.isNullOrEmpty()) productIds.addAll(it.products!!.mapNotNull { it.productId })
        }
        val products = productService.getByProductIds(productIds.toList()).associateBy({it.id}, {it})

        return look.map { it.toLookDetails(products) }
    }

    override fun getUserLooks(): List<UserLook> {
        val looks = lookRepository.findActiveLooksWithAvailableUnits()
        val productIds = mutableSetOf<String>()
        looks.forEach {
            if (!it.products.isNullOrEmpty()) productIds.addAll(it.products!!.mapNotNull { it.productId })
        }
        val products = productService.getByProductIds(productIds.toList()).associateBy({it.id}, {it})

        return looks.map { it.toUserLook(products) }
    }
}