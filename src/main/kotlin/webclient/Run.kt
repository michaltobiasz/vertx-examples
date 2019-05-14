package webclient

import core.HttpServerVerticle
import io.vertx.core.Vertx

fun main(args: Array<String>) {
    // create an instance of Vert.x object
    val vertx = Vertx.vertx()

    // HTTP Server and Client
    vertx.deployVerticle(HttpServerVerticle()) { httpServerResult ->
        if (httpServerResult.succeeded()) {
            vertx.deployVerticle(WebClientVerticle())
        }
    }
}