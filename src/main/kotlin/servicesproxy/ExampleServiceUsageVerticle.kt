package servicesproxy

import io.vertx.core.AbstractVerticle
import io.vertx.core.Handler
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory

class ExampleServiceUsageVerticle : AbstractVerticle() {

    private companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(ExampleServiceUsageVerticle::class.java)
    }

    override fun start() {
        val exampleService = ExampleService.ExampleServiceFactory.createProxy(vertx, "example.service")
        val data = Data(3, "test")
        exampleService.updateData(data, Handler { res ->
            if (res.succeeded()) {
                LOGGER.info("Received: ${res.result()}")
            }
        })
    }
}