package rxjava.servicesproxy

import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.reactivex.core.AbstractVerticle

class ExampleServiceUsageVerticle : AbstractVerticle() {

    private companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(ExampleServiceUsageVerticle::class.java)
    }

    override fun start() {
        val exampleService = ExampleService.ExampleServiceFactory.createProxy(vertx, "example.service")
        val data = Data(3, "test")
        exampleService.rxUpdateData(data)
            .subscribe(
                { result ->
                    LOGGER.info("Received: $result")
                },
                { error ->
                    LOGGER.error("Something went wrong ${error.message}")
                })
    }
}