package io.gorillas.delivery.query

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import io.gorillas.delivery.model.DeliveryStatus
import io.gorillas.delivery.service.DeliveryService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component

@Component
class DeliveryQuery(private val deliveryService: DeliveryService): Query {
    @GraphQLDescription("Returns the available deliveries for given status")
    suspend fun deliveries(status: DeliveryStatus) = coroutineScope {
        deliveryService.getDeliveries(status).collectList().awaitSingle()
    }
}
