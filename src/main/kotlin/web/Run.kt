package web

import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx

fun main(args: Array<String>) {
    // create an instance of Vert.x object
    val vertx = Vertx.vertx()

    // Vert.x Web
    vertx.deployVerticle("web.HttpServerVerticle", DeploymentOptions().setInstances(4))
}