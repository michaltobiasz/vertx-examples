package servicesproxy

import io.vertx.codegen.annotations.Fluent
import io.vertx.codegen.annotations.GenIgnore
import io.vertx.codegen.annotations.ProxyGen
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.Vertx

@ProxyGen
interface ExampleService {

    @Fluent
    fun updateData(data: Data, resultHandler: Handler<AsyncResult<Int>>): ExampleService

    @GenIgnore
    object ExampleServiceFactory {
        fun create(vertx: Vertx): ExampleService = ExampleServiceImpl(vertx)

        fun createProxy(vertx: Vertx, address: String): ExampleService =
            ExampleServiceVertxEBProxy(vertx, address)
    }
}