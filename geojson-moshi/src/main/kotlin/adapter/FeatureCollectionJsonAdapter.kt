package adapter

import Feature
import FeatureCollection
import GeoJsonType
import com.squareup.moshi.*
import com.squareup.moshi.internal.Util
import expectedGeoJsonTypeOrThrow

internal class FeatureCollectionJsonAdapter(moshi: Moshi) : JsonAdapter<FeatureCollection>() {

    private val options: JsonReader.Options = JsonReader.Options.of("features", "type")

    private val listOfFeatureAdapter: JsonAdapter<List<Feature>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, Feature::class.java))

    private val geoJsonTypeAdapter: JsonAdapter<GeoJsonType> = moshi.adapter(GeoJsonType::class.java)

    override fun fromJson(reader: JsonReader): FeatureCollection {
        var features: List<Feature> = emptyList()

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                0 -> features = listOfFeatureAdapter.fromJson(reader)
                    ?: throw Util.unexpectedNull("features", "features", reader)
                1 -> expectedGeoJsonTypeOrThrow(GeoJsonType.FeatureCollection, geoJsonTypeAdapter.fromJson(reader), reader)
                else -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }
        reader.endObject()

        return FeatureCollection(
            features = features
        )
    }

    override fun toJson(writer: JsonWriter, value: FeatureCollection?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
        }

        writer.beginObject()
        writer.name("features")
        listOfFeatureAdapter.toJson(writer, value.features)
        writer.name("type")
        geoJsonTypeAdapter.toJson(writer, value.type)
        writer.endObject()
    }
}
