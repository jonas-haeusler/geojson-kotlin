package adapter

import GeoJsonType
import Point
import Position
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.internal.Util
import expectedGeoJsonTypeOrThrow

internal class PointJsonAdapter(moshi: Moshi) : JsonAdapter<Point>() {

    private val options: JsonReader.Options = JsonReader.Options.of("coordinates", "type")

    private val positionAdapter: JsonAdapter<Position> = moshi.adapter(Position::class.java)

    private val geoJsonTypeAdapter: JsonAdapter<GeoJsonType> = moshi.adapter(GeoJsonType::class.java)

    override fun fromJson(reader: JsonReader): Point {
        var coordinates: Position? = null

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                0 -> coordinates = positionAdapter.fromJson(reader)
                    ?: throw Util.unexpectedNull("coordinates", "coordinates", reader)
                1 -> expectedGeoJsonTypeOrThrow(GeoJsonType.Point, geoJsonTypeAdapter.fromJson(reader), reader)
                else -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }
        reader.endObject()

        return Point(
            coordinates = coordinates ?: throw Util.missingProperty("coordinates", "coordinates", reader)
        )
    }

    override fun toJson(writer: JsonWriter, value: Point?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
        }

        writer.beginObject()
        writer.name("coordinates")
        positionAdapter.toJson(writer, value.coordinates)
        writer.name("type")
        geoJsonTypeAdapter.toJson(writer, value.type)
        writer.endObject()
    }
}
