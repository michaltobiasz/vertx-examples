package web

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.http.HttpServerOptions
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.TemplateHandler
import io.vertx.ext.web.templ.handlebars.HandlebarsTemplateEngine

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

        val router = Router.router(vertx)

        router.route("/test/path/:paramName")
            .handler { routingContext ->
                val response = routingContext.response()
                LOGGER.info("Received request from ${routingContext.request().path()}")
                response.isChunked = true
                response.write("handler 1\n")

                routingContext.put("handler1Data", "DATA FROM HANDLER 1")
                routingContext.next()
            }
            .handler { routingContext ->
                val response = routingContext.response()
                LOGGER.info("Received request from ${routingContext.request().path()}")
                LOGGER.info("Received data from handler 1: ${routingContext.get<String>("handler1Data")}")
                response.write("handler 2 \n")
                response.write("paramName: ${routingContext.request().getParam("paramName")}")
                response.end()
            }

        router.route().pathRegex("/test2/(?<pathName>path[1-3])")
            .handler { routingContext ->
                routingContext.response()
                    .end(routingContext.request().getParam("pathName"))
            }

        // Using blocking handlers
        router.route().path("/test3/blocking/*")
            .blockingHandler { routingContext ->
                // Do something that might take some time synchronously

                routingContext.response()
                    .end("Using blocking handler for path: ${routingContext.request().path()} and query: ${routingContext.request().query()}")
            }

        // Sub-routers
        val subRouter = Router.router(vertx)

        subRouter.route().handler(BodyHandler.create())


        subRouter.put("/products/:productID").handler { rc ->
            rc.response().end("Update product ${rc.bodyAsJson}")
        }

        subRouter.get("/products/:productID").handler { rc ->
            rc.fail(403)
        }

        subRouter.get("/products/*")
            .failureHandler { failureRoutingContext ->
                val statusCode = failureRoutingContext.statusCode()
                failureRoutingContext.response()
                    .setStatusCode(statusCode)
                    .end("Sorry! Not today")
            }

        router.mountSubRouter("/productsApi", subRouter)

        // Templates
        val templateEngine = HandlebarsTemplateEngine.create(vertx)
        val templateHandler = TemplateHandler.create(templateEngine)

        // use path /template/test.hbs
        router.get("/template/*").handler { rc ->
            rc.put("data", JsonObject().put("key", "testKey").put("value", "testValue"))
            rc.next()
        }

        router.get("/template/*").handler(templateHandler)

        // Start the Server Listening
        server.requestHandler(router)
            .listen(8076) { result ->
                if (result.succeeded()) {
                    LOGGER.info("Vert.x Web: Server is listening!")
                    startFuture.complete()
                } else {
                    startFuture.fail("Failed to bind!")
                }
            }

    }
}