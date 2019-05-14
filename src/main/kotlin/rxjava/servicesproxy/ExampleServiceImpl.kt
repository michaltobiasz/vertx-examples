package rxjava.servicesproxy

import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.reactivex.core.Vertx
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory

class ExampleServiceImpl(vertx: Vertx) : ExampleService {

    private companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(ExampleServiceImpl::class.java)
    }

    override fun updateData(data: Data, resultHandler: Handler<AsyncResult<Int>>): ExampleService {
        LOGGER.info(data.toJson())
        //Future.failedFuture()
        resultHandler.handle(Future.succeededFuture(data.id))
        return this
    }
}