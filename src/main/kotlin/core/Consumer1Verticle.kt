package core

import io.vertx.core.AbstractVerticle
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory

class Consumer1Verticle : AbstractVerticle() {

    private companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(Consumer1Verticle::class.java)
    }

    override fun start() {
        val eventBus = vertx.eventBus()
        eventBus.consumer<Any>("vertx.example") { message ->
            LOGGER.info("I have received a message: ${message.body()}")
            LOGGER.info("Options: ${message.headers()}")

            vertx.executeBlocking<Any>({ future ->
                // Call some blocking API that takes a significant amount of time to return
                future.complete("How interesting!")
            }, { res ->
                message.reply(res.result())
            })
        }
    }
}