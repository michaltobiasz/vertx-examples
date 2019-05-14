package rxjava

import io.vertx.reactivex.core.Vertx
import rxjava.servicesproxy.ExampleServiceUsageVerticle
import rxjava.servicesproxy.ExampleServiceVerticle

fun main(args: Array<String>) {
    // create an instance of Vert.x object
    val vertx = Vertx.vertx()

    vertx.rxDeployVerticle(HttpServerVerticle())
        .doOnSuccess { vertx.deployVerticle(WebClientVerticle()) }
        .subscribe()

    vertx.rxDeployVerticle(ExampleServiceVerticle())
        .doOnSuccess { vertx.deployVerticle(ExampleServiceUsageVerticle()) }
        .subscribe()
}