package io.gorillas.delivery.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription

@GraphQLDescription("Delivery contains the details regarding a particular delivery")
data class Delivery(
        val deliveryId: String,
        val product: String,
        val supplier: String,
        val quantity: Int,
        val expectedDate: String,
        val expectedWarehouse: String,
        var status: DeliveryStatus?
)

@GraphQLDescription("Current status of the delivery")
enum class DeliveryStatus {
    RECEIVED, NOT_RECEIVED
}
