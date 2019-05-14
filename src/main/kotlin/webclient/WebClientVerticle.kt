package webclient

import io.vertx.core.AbstractVerticle
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions

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
            .send { asyncResult ->
                if (asyncResult.succeeded()) {
                    LOGGER.info("GET Web Client received response: ${asyncResult.result().body()}")
                } else {
                    LOGGER.info("Something went wrong ${asyncResult.cause().message}")
                }
            }

        webClient.postAbs("http://localhost:8075")
            .putHeader("content-type", "application/json")
            .addQueryParam("param", "param_value")
            .sendJson(JsonObject().put("key", "value")) { asyncResult ->
                if (asyncResult.succeeded()) {
                    LOGGER.info("POST Web Client received response: ${asyncResult.result().body()}")
                } else {
                    LOGGER.info("Something went wrong ${asyncResult.cause().message}")
                }
            }
    }
}