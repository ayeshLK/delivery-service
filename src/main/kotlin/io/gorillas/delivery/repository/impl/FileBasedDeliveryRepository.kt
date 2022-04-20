package io.gorillas.delivery.repository.impl

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.gorillas.delivery.model.Delivery
import io.gorillas.delivery.model.DeliveryStatus
import io.gorillas.delivery.repository.DeliveryRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.File
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Repository
class FileBasedDeliveryRepository(
        @Value("\${custom.config.db.file}") private val dbFile: String): DeliveryRepository {
    private val deliveries: MutableList<Delivery> = mutableListOf()
    private val mapper = jacksonObjectMapper()

    @PostConstruct
    fun init() {
        val jsonString: String = File(javaClass.classLoader.getResource(dbFile).toURI()).readText(Charsets.UTF_8)
        mapper.readValue<List<Delivery>>(jsonString)
                .forEach {
                    deliveries.add(it)
                }
    }

    @PreDestroy
    fun preDestroy() {
        val serialized = mapper.writeValueAsString(deliveries)
        File(javaClass.classLoader.getResource(dbFile).toURI()).writeText(serialized, Charsets.UTF_8)
    }

    override fun getDeliveries(status: DeliveryStatus): Flux<Delivery> {
        val availableDeliveries = deliveries.filter {
            if (DeliveryStatus.NOT_RECEIVED == status) {
                it.status == null || it.status == status
            } else {
                it.status == status
            }
        }
        return Flux.fromIterable(availableDeliveries)
    }

    override fun getDelivery(deliveryId: String): Mono<Delivery> {
        val availableDelivery: Delivery? = deliveries.find { it.deliveryId == deliveryId }
        return Mono.justOrEmpty(availableDelivery)
    }

    override fun updateDeliveryStatus(deliveryId: String, status: DeliveryStatus): Mono<Delivery> {
        val availableDelivery: Delivery? = deliveries.find { it.deliveryId == deliveryId }
        if (availableDelivery is Delivery) {
            availableDelivery.status = status
        }
        return Mono.justOrEmpty(availableDelivery)
    }
}