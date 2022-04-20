package io.gorillas.delivery

import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class DeliveryServiceApplication

fun main(args: Array<String>) {
	val app = SpringApplication(DeliveryServiceApplication::class.java)
	app.setBannerMode(Banner.Mode.OFF)
	app.run(*args)
	println("*** Started Delivery Service Application ***")
}
