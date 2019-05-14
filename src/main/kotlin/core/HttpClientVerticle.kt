package core

import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpClientOptions
import io.vertx.core.http.RequestOptions
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory

class HttpClientVerticle : AbstractVerticle() {

    private companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(HttpClientVerticle::class.java)
    }

    override fun start() {
        // Configure options for the client
        val options = HttpClientOptions()
            .setLogActivity(true)

        // Creating an HTTP client
        val client = vertx.createHttpClient(options)

        val requestOptions = RequestOptions()
            .setHost("localhost")
            .setPort(8075)

        client.get(requestOptions) { httpClientResponse ->

            httpClientResponse.bodyHandler { totalBuffer ->
                LOGGER.info("Client received response: $totalBuffer")
            }

            httpClientResponse.endHandler {  }

        }
            .putHeader("Test", "test")
            .end()

        client.post(requestOptions) { httpClientResponse ->

            httpClientResponse.bodyHandler { totalBuffer ->
                LOGGER.info("Client received response: $totalBuffer")
            }
        }
            .putHeader("Test", "test")
            .end("POST test")

    }
}