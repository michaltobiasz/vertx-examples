package core

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory

class BasicVerticle : AbstractVerticle() {

    private companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(BasicVerticle::class.java)
    }

    // Called when verticle is deployed
    override fun start() {
        LOGGER.info("Hello ${config().getString("name")}")
    }

    // Optional - called when verticle is undeployed
    override fun stop() {
    }

    // Asynchronous Verticle start and stop
    override fun start(startFuture: Future<Void>?) {
        super.start(startFuture)
    }

    override fun stop(stopFuture: Future<Void>?) {
        super.stop(stopFuture)
    }
}