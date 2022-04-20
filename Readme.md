# Delivery Service Application

Service to retrieve and update delivery details.

## Deployment Setup
- This application is using logging. Please set up the logging path which could be found in `logback.xml` before running the application.

### Running locally

To build the project execute following command.

```shell script
./gradlew build
```

Then to start the server:

* Run `DeliveryServiceApplication.kt` directly from your IDE
* Alternatively you can also use the spring boot plugin from the command line.

```shell script
./gradlew bootRun
```

Once the app has started you can explore the example schema by opening the GraphQL Playground endpoint at http://localhost:9090/playground.

#### Query for Current Delivery Information
- Go to GraphQL playground URL from your browser.
- Run following query:
```
query {
  deliveries(status: NOT_RECEIVED) {
    deliveryId
    status
  }
}
```

#### Update the status of the delivery information
- Go to GraphQL playground URL from your browser.
- Run following mutation:
```
mutation {
  updateDeliveryStatus(deliveryId: "101", status: RECEIVED) {
    deliveryId
    status
  }
}
```
