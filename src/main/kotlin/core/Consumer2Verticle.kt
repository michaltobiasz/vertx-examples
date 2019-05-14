package core

import io.vertx.core.AbstractVerticle
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory

class Consumer2Verticle : AbstractVerticle() {

    private companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(Consumer2Verticle::class.java)
    }

    override fun start() {
        val eventBus = vertx.eventBus()
        eventBus.consumer<Any>("vertx.example") { message ->
            LOGGER.info("I have received a message: ${message.body()}")
        }
    }
}