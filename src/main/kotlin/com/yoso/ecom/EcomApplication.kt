package com.yoso.ecom

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EcomApplication

fun main(args: Array<String>) {
	runApplication<EcomApplication>(*args)
}
