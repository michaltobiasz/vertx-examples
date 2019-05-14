package rxjava

import io.reactivex.Completable
import io.vertx.core.http.HttpServerOptions
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.reactivex.core.AbstractVerticle

class HttpServerVerticle : AbstractVerticle() {

    private companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(HttpServerVerticle::class.java)
    }

    override fun rxStart(): Completable {
        // Configuring an HTTP server
        val options = HttpServerOptions()
            .setLogActivity(true)

        // Creating an HTTP Server
        val server = vertx.createHttpServer(options)

        // Handling requests
        server.requestHandler { request ->
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
        return server
            .rxListen(8075)
            .doOnSuccess { LOGGER.info("Server is listening!") }
            .doOnError { LOGGER.error("Failed to bind!") }
            .ignoreElement()
    }
}