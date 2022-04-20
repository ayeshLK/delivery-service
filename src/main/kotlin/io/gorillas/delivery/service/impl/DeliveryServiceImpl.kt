package io.gorillas.delivery.service.impl

import io.gorillas.delivery.exception.ValidationException
import io.gorillas.delivery.model.Delivery
import io.gorillas.delivery.model.DeliveryStatus
import io.gorillas.delivery.repository.DeliveryRepository
import io.gorillas.delivery.service.DeliveryService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class DeliveryServiceImpl(private val deliveryRepo: DeliveryRepository) : DeliveryService {
    private val logger: Logger = LoggerFactory.getLogger(DeliveryServiceImpl::class.java)

    override fun getDeliveries(status: DeliveryStatus): Flux<Delivery> {
        logger.info("Retrieving deliveries for status [${status}]")
        return deliveryRepo.getDeliveries(status)
    }

    override fun updateDeliveryStatus(deliveryId: String, status: DeliveryStatus): Mono<Delivery> {
        logger.info("Updating delivery details for deliveryId [${deliveryId}] with status [${status}]")
        return deliveryRepo.getDelivery(deliveryId)
                .flatMap { _ -> deliveryRepo.updateDeliveryStatus(deliveryId, status) }
                .switchIfEmpty { Mono.error {
                    ValidationException("Could not find the delivery details for Id [$deliveryId]")
                } }
                .doOnSuccess {
                    if (logger.isDebugEnabled) {
                        logger.debug("Delivery details updated for delivery [$it]")
                    }
                }
    }
}