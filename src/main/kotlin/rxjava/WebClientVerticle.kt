package rxjava

import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.client.WebClientOptions
import io.vertx.reactivex.core.AbstractVerticle
import io.vertx.reactivex.ext.web.client.WebClient

class WebClientVerticle : AbstractVerticle() {

    private companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(WebClientVerticle::class.java)
    }

    override fun start() {
        // Configuring web client
        val webClientOptions = WebClientOptions()

        // Creating a web client
        val webClient = WebClient.create(vertx, webClientOptions)

        webClient.getAbs("http://localhost:8075")
            .putHeader("content-type", "application/json")
            .addQueryParam("param", "param_value")
            .rxSend()
            .subscribe(
                { result ->
                    LOGGER.info("Web Client received response: ${result.body()}")
                },
                { error ->
                    LOGGER.error("Something went wrong ${error.message}")
                })

    }
}