package adapter

import GeoJson
import GeoJsonType
import com.squareup.moshi.*
import com.squareup.moshi.internal.Util

internal class GeoJsonObjectJsonAdapter(val moshi: Moshi) : JsonAdapter<GeoJson>() {

    private val labelKey: String = "type"
    private val labelOption: JsonReader.Options = JsonReader.Options.of(labelKey)

    private val adapters: List<JsonAdapter<out GeoJson>> = listOf(
        FeatureCollectionJsonAdapter(moshi),
        PointJsonAdapter(moshi),
        MultiPointJsonAdapter(moshi),
        LineStringJsonAdapter(moshi),
        MultiLineStringJsonAdapter(moshi),
        PolygonJsonAdapter(moshi),
        MultiPolygonJsonAdapter(moshi),
        GeometryCollectionJsonAdapter(moshi),
        FeatureJsonAdapter(moshi),
    )

    private val types = listOf(
        GeoJsonType.FeatureCollection.name,
        GeoJsonType.Point.name,
        GeoJsonType.MultiPoint.name,
        GeoJsonType.LineString.name,
        GeoJsonType.MultiLineString.name,
        GeoJsonType.Polygon.name,
        GeoJsonType.MultiPolygon.name,
        GeoJsonType.GeometryCollection.name,
        GeoJsonType.Feature.name,
    )

    private val subtypes = JsonReader.Options.of(*types.toTypedArray())

    override fun fromJson(reader: JsonReader): GeoJson? {
        val peeked = reader.peekJson()
        peeked.setFailOnUnknown(false)

        val index = peeked.use { peekedd ->
            selectTypeIndex(peekedd)
        }

        return adapters[index].fromJson(reader)
    }

    private fun selectTypeIndex(reader: JsonReader): Int {
        reader.beginObject()
        while (reader.hasNext()) {
            if (reader.selectName(labelOption) == -1) {
                reader.skipName()
                reader.skipValue()
                continue
            }

            val type = reader.selectString(subtypes)
            if (type == -1) {
                throw JsonDataException("Expected one of $subtypes for key '$labelKey' but found ${reader.nextString()}")
            }
            return type
        }
        throw Util.missingProperty("type", "type", reader)
    }

    override fun toJson(writer: JsonWriter, value: GeoJson?) {
        val type = value?.type?.name
        val adapter = adapters.getOrElse(types.indexOf(type)) {
            throw IllegalArgumentException("Expected one of $subtypes but found $type, a ${type?.javaClass?.name}")
        } as JsonAdapter<in GeoJson>

        writer.beginObject()
        val flattenToken = writer.beginFlatten()
        adapter.toJson(writer, value)
        writer.endFlatten(flattenToken)
        writer.endObject()
    }
}