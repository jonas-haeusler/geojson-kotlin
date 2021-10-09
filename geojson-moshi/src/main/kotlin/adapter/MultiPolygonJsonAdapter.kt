package adapter

import GeoJsonType
import MultiPolygon
import Position
import com.squareup.moshi.*
import com.squareup.moshi.internal.Util
import expectedGeoJsonTypeOrThrow

internal class MultiPolygonJsonAdapter(moshi: Moshi) : JsonAdapter<MultiPolygon>() {

    private val options: JsonReader.Options = JsonReader.Options.of("coordinates", "type")

    private val listOfListOfListOfPositionAdapter: JsonAdapter<List<List<List<Position>>>> =
        moshi.adapter(
            Types.newParameterizedType(
                List::class.java,
                Types.newParameterizedType(
                    List::class.java,
                    Types.newParameterizedType(List::class.java, Position::class.java)
                )
            )
        )

    private val geoJsonTypeAdapter: JsonAdapter<GeoJsonType> = moshi.adapter(GeoJsonType::class.java)

    override fun fromJson(reader: JsonReader): MultiPolygon {
        var coordinates: List<List<List<Position>>> = emptyList()

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                0 -> coordinates = listOfListOfListOfPositionAdapter.fromJson(reader)
                    ?: throw Util.unexpectedNull("coordinates", "coordinates", reader)
                1 -> expectedGeoJsonTypeOrThrow(GeoJsonType.MultiPolygon, geoJsonTypeAdapter.fromJson(reader), reader)
                else -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }
        reader.endObject()

        return MultiPolygon(
            coordinates = coordinates
        )
    }

    override fun toJson(writer: JsonWriter, value: MultiPolygon?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
        }

        writer.beginObject()
        writer.name("coordinates")
        listOfListOfListOfPositionAdapter.toJson(writer, value.coordinates)
        writer.name("type")
        geoJsonTypeAdapter.toJson(writer, value.type)
        writer.endObject()
    }
}
