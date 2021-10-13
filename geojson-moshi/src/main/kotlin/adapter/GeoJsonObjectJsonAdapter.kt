package adapter

import Feature
import FeatureCollection
import GeoJson
import GeoJsonType
import GeometryCollection
import LineString
import MultiLineString
import MultiPoint
import MultiPolygon
import Point
import Polygon
import com.squareup.moshi.*
import com.squareup.moshi.internal.Util

internal class GeoJsonObjectJsonAdapter(val moshi: Moshi) : JsonAdapter<GeoJson>() {

    private val labelKey: String = "type"
    private val labelOption: JsonReader.Options = JsonReader.Options.of(labelKey)
    
    private val featureCollectionJsonAdapter = moshi.adapter(FeatureCollection::class.java)
    private val pointJsonAdapter = moshi.adapter(Point::class.java)
    private val multiPointJsonAdapter = moshi.adapter(MultiPoint::class.java)
    private val lineStringJsonAdapter = moshi.adapter(LineString::class.java)
    private val multiLineStringJsonAdapter = moshi.adapter(MultiLineString::class.java)
    private val polygonJsonAdapter = moshi.adapter(Polygon::class.java)
    private val multiPolygonJsonAdapter = moshi.adapter(MultiPolygon::class.java)
    private val geometryCollectionJsonAdapter = moshi.adapter(GeometryCollection::class.java)
    private val featureJsonAdapter = moshi.adapter(Feature::class.java)

    private val subtypes = JsonReader.Options.of(*GeoJsonType.values().map { it.name }.toTypedArray())

    override fun fromJson(reader: JsonReader): GeoJson? {
        val type = reader.peekJson().use { peeked ->
            peeked.setFailOnUnknown(false)
            selectTypeIndex(peeked)
        }

        return when (type) {
            GeoJsonType.Point -> pointJsonAdapter.fromJson(reader)
            GeoJsonType.MultiPoint -> multiPointJsonAdapter.fromJson(reader)
            GeoJsonType.LineString -> lineStringJsonAdapter.fromJson(reader)
            GeoJsonType.MultiLineString -> multiLineStringJsonAdapter.fromJson(reader)
            GeoJsonType.Polygon -> polygonJsonAdapter.fromJson(reader)
            GeoJsonType.MultiPolygon -> multiPolygonJsonAdapter.fromJson(reader)
            GeoJsonType.GeometryCollection -> geometryCollectionJsonAdapter.fromJson(reader)
            GeoJsonType.Feature -> featureJsonAdapter.fromJson(reader)
            GeoJsonType.FeatureCollection -> featureCollectionJsonAdapter.fromJson(reader)
        }
    }

    private fun selectTypeIndex(reader: JsonReader): GeoJsonType {
        reader.beginObject()
        while (reader.hasNext()) {
            if (reader.selectName(labelOption) == -1) {
                reader.skipName()
                reader.skipValue()
                continue
            }

            val typeIndex = reader.selectString(subtypes)
            if (typeIndex == -1) {
                throw JsonDataException("Expected one of $subtypes for key '$labelKey' but found ${reader.nextString()}")
            }
            return GeoJsonType.valueOf(subtypes.strings()[typeIndex])
        }
        throw Util.missingProperty("type", "type", reader)
    }

    override fun toJson(writer: JsonWriter, value: GeoJson?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
        }

        writer.beginObject()
        val flattenToken = writer.beginFlatten()
        when (value) {
            is Point -> pointJsonAdapter.toJson(writer, value)
            is MultiPoint -> multiPointJsonAdapter.toJson(writer, value)
            is LineString -> lineStringJsonAdapter.toJson(writer, value)
            is MultiLineString -> multiLineStringJsonAdapter.toJson(writer, value)
            is Polygon -> polygonJsonAdapter.toJson(writer, value)
            is MultiPolygon -> multiPolygonJsonAdapter.toJson(writer, value)
            is GeometryCollection -> geometryCollectionJsonAdapter.toJson(writer, value)
            is Feature -> featureJsonAdapter.toJson(writer, value)
            is FeatureCollection -> featureCollectionJsonAdapter.toJson(writer, value)
        }
        writer.endFlatten(flattenToken)
        writer.endObject()
    }
}