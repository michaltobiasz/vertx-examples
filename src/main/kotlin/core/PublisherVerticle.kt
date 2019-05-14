package core

import io.vertx.core.AbstractVerticle

class PublisherVerticle : AbstractVerticle() {

    override fun start() {
        val eventBus = vertx.eventBus()
        eventBus.publish("vertx.example", "Publish/Consumer example")
    }
}