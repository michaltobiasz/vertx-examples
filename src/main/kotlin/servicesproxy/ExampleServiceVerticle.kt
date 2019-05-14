package servicesproxy

import io.vertx.core.AbstractVerticle
import io.vertx.core.eventbus.MessageConsumer
import io.vertx.core.json.JsonObject
import io.vertx.serviceproxy.ServiceBinder

class ExampleServiceVerticle : AbstractVerticle() {

    lateinit var messageConsumer : MessageConsumer<JsonObject>

    override fun start() {
        val exampleService = ExampleService.ExampleServiceFactory.create(vertx)

        messageConsumer = ServiceBinder(vertx)
            .setAddress("example.service")
            .register(ExampleService::class.java, exampleService)
    }

    override fun stop() {
        ServiceBinder(vertx).unregister(messageConsumer)
    }
}
