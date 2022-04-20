package io.gorillas.delivery.mutation

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import io.gorillas.delivery.model.DeliveryStatus
import io.gorillas.delivery.service.DeliveryService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Component

@Component
class DeliveryMutation(private val deliveryService: DeliveryService): Mutation {
    @GraphQLDescription("Updates the status of a particular delivery")
    suspend fun updateDeliveryStatus(deliveryId: String, status: DeliveryStatus) = coroutineScope {
        deliveryService.updateDeliveryStatus(deliveryId, status).awaitSingle()
    }
}
