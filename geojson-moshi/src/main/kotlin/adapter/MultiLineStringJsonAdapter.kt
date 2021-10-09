package adapter

import GeoJsonType
import LineString
import MultiLineString
import Position
import com.squareup.moshi.*
import com.squareup.moshi.internal.Util
import expectedGeoJsonTypeOrThrow

internal class MultiLineStringJsonAdapter(moshi: Moshi) : JsonAdapter<MultiLineString>() {

    private val options: JsonReader.Options = JsonReader.Options.of("coordinates", "type")

    private val listOfListOfPositionAdapter: JsonAdapter<List<List<Position>>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, Types.newParameterizedType(List::class.java, Position::class.java)))

    private val geoJsonTypeAdapter: JsonAdapter<GeoJsonType> = moshi.adapter(GeoJsonType::class.java)

    override fun fromJson(reader: JsonReader): MultiLineString {
        var coordinates: List<List<Position>> = emptyList()

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                0 -> coordinates = listOfListOfPositionAdapter.fromJson(reader)
                    ?: throw Util.unexpectedNull("coordinates", "coordinates", reader)
                1 -> expectedGeoJsonTypeOrThrow(GeoJsonType.MultiLineString, geoJsonTypeAdapter.fromJson(reader), reader)
                else -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }
        reader.endObject()

        return MultiLineString(
            coordinates = coordinates
        )
    }

    override fun toJson(writer: JsonWriter, value: MultiLineString?) {
        if (value == null) {
            throw NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.")
        }

        writer.beginObject()
        writer.name("coordinates")
        listOfListOfPositionAdapter.toJson(writer, value.coordinates)
        writer.name("type")
        geoJsonTypeAdapter.toJson(writer, value.type)
        writer.endObject()
    }
}
