package adapter

import GeoJsonType
import LineString
import Position
import com.squareup.moshi.*
import com.squareup.moshi.internal.Util
import expectedGeoJsonTypeOrThrow
import java.lang.reflect.Type

internal class LineStringJsonAdapter(moshi: Moshi) : JsonAdapter<LineString>() {

    private val options: JsonReader.Options = JsonReader.Options.of("coordinates", "type")

    private val listOfPositionAdapter: JsonAdapter<List<Position>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, Position::class.java))

    private val geoJsonTypeAdapter: JsonAdapter<GeoJsonType> = moshi.adapter(GeoJsonType::class.java)

    override fun fromJson(reader: JsonReader): LineString {
        var coordinates: List<Position> = emptyList()

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                0 -> coordinates = listOfPositionAdapter.fromJson(reader)
                    ?: throw Util.unexpectedNull("coordinates", "coordinates", reader)
                1 -> expectedGeoJsonTypeOrThrow(GeoJsonType.LineString, geoJsonTypeAdapter.fromJson(reader), reader)
                else -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }
        reader.endObject()

        return LineString(
            coordinates = coordinates
        )
    }

    override fun toJson(writer: JsonWriter, value: LineString?) {
        if (value == null) {
            throw NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.")
        }

        writer.beginObject()
        writer.name("coordinates")
        listOfPositionAdapter.toJson(writer, value.coordinates)
        writer.name("type")
        geoJsonTypeAdapter.toJson(writer, value.type)
        writer.endObject()
    }
}
