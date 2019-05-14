package rxjava.servicesproxy

import io.vertx.codegen.annotations.Fluent
import io.vertx.codegen.annotations.GenIgnore
import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.reactivex.core.Vertx
import rxjava.reactivex.servicesproxy.ExampleService as RxJavaExampleService

@ProxyGen
@VertxGen
interface ExampleService {

    @Fluent
    fun updateData(data: Data, resultHandler: Handler<AsyncResult<Int>>): ExampleService

    @GenIgnore
    object ExampleServiceFactory {
        fun create(vertx: Vertx): ExampleService = ExampleServiceImpl(vertx)

        fun createProxy(vertx: Vertx, address: String): RxJavaExampleService =
            RxJavaExampleService(ExampleServiceVertxEBProxy(vertx.delegate, address))
    }
}