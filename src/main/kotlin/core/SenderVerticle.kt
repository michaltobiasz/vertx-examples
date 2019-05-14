package core

import io.vertx.core.AbstractVerticle
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory


class SenderVerticle : AbstractVerticle() {

    private companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(SenderVerticle::class.java)
    }

    override fun start() {
        val eventBus = vertx.eventBus()
        val options = DeliveryOptions().addHeader("action", "save")
        eventBus.send<Any>("vertx.example", "Point-to-point example", options) { ar ->
            if (ar.succeeded()) {
                LOGGER.info("Sender: I have received reply: ${ar.result().body()}")
            }
        }
    }
}