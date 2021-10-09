package adapter

import GeoJson
import GeoJsonType
import GeometryCollection
import com.squareup.moshi.*
import com.squareup.moshi.internal.Util
import expectedGeoJsonTypeOrThrow

internal class GeometryCollectionJsonAdapter(moshi: Moshi) : JsonAdapter<GeometryCollection>() {

    private val options: JsonReader.Options = JsonReader.Options.of("geometries", "type")

    private val listOfGeoJsonAdapter: JsonAdapter<List<GeoJson>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, GeoJson::class.java))

    private val geoJsonTypeAdapter: JsonAdapter<GeoJsonType> = moshi.adapter(GeoJsonType::class.java)

    override fun fromJson(reader: JsonReader): GeometryCollection {
        var geometries: List<GeoJson> = emptyList()

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                0 -> geometries = listOfGeoJsonAdapter.fromJson(reader)
                    ?: throw Util.unexpectedNull("geometries", "geometries", reader)
                1 -> expectedGeoJsonTypeOrThrow(GeoJsonType.GeometryCollection, geoJsonTypeAdapter.fromJson(reader), reader)
                else -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }
        reader.endObject()

        return GeometryCollection(
            geometries = geometries
        )
    }

    override fun toJson(writer: JsonWriter, value: GeometryCollection?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
        }

        writer.beginObject()
        writer.name("geometries")
        listOfGeoJsonAdapter.toJson(writer, value.geometries)
        writer.name("type")
        geoJsonTypeAdapter.toJson(writer, value.type)
        writer.endObject()
    }
}
