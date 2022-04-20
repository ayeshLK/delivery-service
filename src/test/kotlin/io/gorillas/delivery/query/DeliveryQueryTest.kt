package io.gorillas.delivery.query

import io.gorillas.delivery.GRAPHQL_ENDPOINT
import io.gorillas.delivery.GRAPHQL_MEDIA_TYPE
import io.gorillas.delivery.model.DeliveryStatus
import io.gorillas.delivery.verifyOnlyDataExists
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
class DeliveryQueryTest(@Autowired private val testClient: WebTestClient) {
    @Test
    fun `verify deliveries query`() {
        val query = "deliveries"
        val argument = DeliveryStatus.NOT_RECEIVED
        testClient.post()
                .uri(GRAPHQL_ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(GRAPHQL_MEDIA_TYPE)
                .bodyValue("query { $query(status: $argument) { deliveryId } }")
                .exchange()
                .verifyOnlyDataExists(query)
    }
}
