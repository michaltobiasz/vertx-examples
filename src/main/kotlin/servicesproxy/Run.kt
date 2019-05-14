package servicesproxy

import io.vertx.core.Vertx

fun main(args: Array<String>) {
    // create an instance of Vert.x object
    val vertx = Vertx.vertx()

    vertx.deployVerticle(ExampleServiceVerticle()) { ar ->
        if (ar.succeeded()) {
            vertx.deployVerticle(ExampleServiceUsageVerticle())
        }
    }
}