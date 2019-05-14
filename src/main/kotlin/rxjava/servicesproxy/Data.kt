package rxjava.servicesproxy

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject

@DataObject
class Data(val id : Int, val message: String) {

    private companion object {
        const val ID = "id"
        const val MESSAGE = "message"
    }

    constructor(json: JsonObject) : this(json.getInteger(ID), json.getString(MESSAGE))

    fun toJson(): JsonObject {
        val json = JsonObject()
        json.put(ID, id)
        json.put(MESSAGE, this.message)
        return json
    }
}