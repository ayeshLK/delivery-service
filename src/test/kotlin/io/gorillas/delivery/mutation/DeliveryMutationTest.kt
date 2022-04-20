package io.gorillas.delivery.mutation

import io.gorillas.delivery.*
import io.gorillas.delivery.model.DeliveryStatus
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeliveryMutationTest(@Autowired private val testClient: WebTestClient) {
    @Test
    fun `verify updateDeliveryStatus query`() {
        val query = "updateDeliveryStatus"
        val deliveryId = "101"
        val newStatus = DeliveryStatus.RECEIVED
        val expected = "{\"deliveryId\": \"101\", \"status\": \"RECEIVED\"}"
        testClient.post()
                .uri(GRAPHQL_ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(GRAPHQL_MEDIA_TYPE)
                .bodyValue("mutation { $query(deliveryId: \"$deliveryId\", status: $newStatus) { deliveryId, status } }")
                .exchange()
                .verifyData(query,expected)
    }

    @AfterAll
    fun afterTest() {
        val query = "updateDeliveryStatus"
        val deliveryId = "101"
        val newStatus = DeliveryStatus.NOT_RECEIVED
        testClient.post()
                .uri(GRAPHQL_ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(GRAPHQL_MEDIA_TYPE)
                .bodyValue("mutation { $query(widget: { deliveryId: $deliveryId, status: $newStatus }) }")
                .exchange()
                .verifyOnlyDataExists(query)
    }
}