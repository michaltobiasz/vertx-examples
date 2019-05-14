package core

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.http.HttpServerOptions
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory

class HttpServerVerticle : AbstractVerticle() {

    private companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(HttpServerVerticle::class.java)
    }

    override fun start(startFuture: Future<Void>) {
        // Configuring an HTTP server
        val options = HttpServerOptions()
            .setLogActivity(true)

        // Creating an HTTP Server
        val server = vertx.createHttpServer(options)

        // Handling requests
        server.requestHandler { request ->
            LOGGER.info("Request headers: ")
            LOGGER.info(request.headers())
            // Writing HTTP responses
            request.response()
                .putHeader("content-type", "text/html")
                .putHeader("other-header", "test")
                .end("Hello from HTTP server")

            // Reading Data from the Request Body
            request.bodyHandler { totalBuffer ->
                LOGGER.info("Full body received, length = ${totalBuffer.length()}")
            }
        }

        // Start the Server Listening
        server.listen(8075) { result ->
            if (result.succeeded()) {
                LOGGER.info("Server is listening!")
                startFuture.complete()
            } else {
                startFuture.fail("Failed to bind!")
            }
        }

    }
}