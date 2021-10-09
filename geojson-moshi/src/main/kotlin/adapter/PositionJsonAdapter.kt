package adapter

import Position
import com.squareup.moshi.*
import com.squareup.moshi.internal.Util

internal class PositionJsonAdapter : JsonAdapter<Position>() {

    override fun fromJson(reader: JsonReader): Position {
        val longitude: Double
        val latitude: Double
        var altitude: Double? = null

        reader.beginArray()
        if (reader.hasNext()) longitude = reader.nextDouble() else throw JsonDataException("Required value for longitude missing at ${reader.path}")
        if (reader.hasNext()) latitude = reader.nextDouble() else throw JsonDataException("Required value for latitude missing at ${reader.path}")
        if (reader.hasNext()) altitude = reader.nextDouble()
        reader.endArray()

        return Position(
            longitude = longitude,
            latitude = latitude,
            altitude = altitude
        )
    }

    override fun toJson(writer: JsonWriter, value: Position?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
        }

        writer.beginArray()
        writer.value(value.longitude)
        writer.value(value.latitude)
        if (value.altitude != null) {
            writer.value(value.altitude)
        }
        writer.endArray()
    }

}
