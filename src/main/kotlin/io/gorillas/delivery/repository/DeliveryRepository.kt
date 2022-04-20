package io.gorillas.delivery.repository

import io.gorillas.delivery.model.Delivery
import io.gorillas.delivery.model.DeliveryStatus
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * A repository service to retrieve delivery details from underlying database.
 */
interface DeliveryRepository {
    /**
     * Retrieves available delivery details for the given [status]
     *
     * @param[status] delivery status
     * @return available delivery details for the given status
     */
    fun getDeliveries(status: DeliveryStatus): Flux<Delivery>

    /**
     * Retrieves the delivery details for the given [deliveryId]
     *
     * @param[deliveryId] unique Id of the delivery
     * @return delivery details for the given Id
     */
    fun getDelivery(deliveryId: String): Mono<Delivery>

    /**
     * Updates the status of the delivery with given [deliveryId]
     *
     * @param[deliveryId] unique Id for the delivery
     * @param[status] the new status of the delivery
     * @return updated delivery details
     */
    fun updateDeliveryStatus(deliveryId: String, status: DeliveryStatus): Mono<Delivery>
}
