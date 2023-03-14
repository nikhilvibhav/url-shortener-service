package com.example.task

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.example.task"])
class UrlShortenerServiceApplication

fun main(args: Array<String>) {
	runApplication<UrlShortenerServiceApplication>(*args)
}
