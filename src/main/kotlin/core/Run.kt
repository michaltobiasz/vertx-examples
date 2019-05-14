package core

import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.core.eventbus.EventBusOptions
import io.vertx.core.json.JsonObject

fun main(args: Array<String>) {
    // create an instance of Vert.x object
    val vertx = Vertx.vertx()

    // The VertxOptions object has many settings and allows you to configure things like clustering, high availability,
    // pool sizes and various other settings.
    val vertxOptions = VertxOptions()

    // Configure Event Bus
    vertxOptions.eventBusOptions = EventBusOptions()

    // Passing configuration to a verticle
    // Configuration in the form of JSON can be passed to a verticle at deployment time:
    val config = JsonObject().put("name", "Boden")
    val options = DeploymentOptions().setConfig(config)

    // Deploying verticles

    // 1. Simple Verticle Example
    vertx.deployVerticle(BasicVerticle(), options)

    // 2. Publish Consumer Example
    vertx.deployVerticle(Consumer1Verticle()) { consumer1AsyncResult ->
        if (consumer1AsyncResult.succeeded()) {
            vertx.deployVerticle(Consumer2Verticle()) { consumer2AsyncResult ->
                if (consumer2AsyncResult.succeeded()) {
                    vertx.deployVerticle(PublisherVerticle())

//                     3. Point-to-point Example
                    vertx.deployVerticle(SenderVerticle())
                }
            }
        }

    }

    // 3. HTTP Server and Client
    vertx.deployVerticle(HttpServerVerticle()) { httpServerResult ->
        if (httpServerResult.succeeded()) {
            vertx.deployVerticle(HttpClientVerticle())
        }
    }

    // Exit Vert.x
//    vertx.close()
}