package adapter

import Feature
import GeoJsonType
import Geometry
import com.squareup.moshi.*
import expectedGeoJsonTypeOrThrow
import java.lang.NullPointerException

internal class FeatureJsonAdapter(moshi: Moshi) : JsonAdapter<Feature>() {

    private val options: JsonReader.Options = JsonReader.Options.of("geometry", "properties", "id", "type")

    private val geometryObjectAdapter: JsonAdapter<Geometry?> =
        moshi.adapter(Geometry::class.java)

    private val mapObjectAdapter: JsonAdapter<Map<*, *>> =
        moshi.adapter(
            Types.newParameterizedType(
                Map::class.java,
                Types.subtypeOf(Any::class.java),
                Types.subtypeOf(Any::class.java)
            )
        )

    private val geoJsonTypeAdapter: JsonAdapter<GeoJsonType> =
        moshi.adapter(GeoJsonType::class.java)

    override fun fromJson(reader: JsonReader): Feature {
        var geometry: Geometry? = null
        var properties: Map<*, *>? = null
        var id: String? = null

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                0 -> geometry = geometryObjectAdapter.fromJson(reader)
                1 -> properties = mapObjectAdapter.fromJson(reader)
                2 -> id = reader.nextString()
                3 -> expectedGeoJsonTypeOrThrow(GeoJsonType.Feature, geoJsonTypeAdapter.fromJson(reader), reader)
                else -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }
        reader.endObject()

        return Feature(
            geometry = geometry,
            properties = properties,
            id = id
        )
    }

    override fun toJson(writer: JsonWriter, value: Feature?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
        }

        writer.beginObject()
        if (value.geometry != null) {
            writer.name("geometry")
            geometryObjectAdapter.toJson(writer, value.geometry)
        }
        writer.name("properties")
        mapObjectAdapter.toJson(writer, value.properties)
        writer.name("id")
        writer.value(value.id)
        writer.name("type")
        geoJsonTypeAdapter.toJson(writer, value.type)
        writer.endObject()
    }
}
