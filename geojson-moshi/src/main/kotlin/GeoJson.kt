import adapter.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi

val geoJsonMoshiAdapter = JsonAdapter.Factory { type, _, moshi ->
    when (type) {
        Position::class.java -> PositionJsonAdapter()
        GeoJson::class.java -> GeoJsonObjectJsonAdapter(moshi)
        Feature::class.java -> FeatureJsonAdapter(moshi)
        FeatureCollection::class.java -> FeatureCollectionJsonAdapter(moshi)
        GeometryCollection::class.java -> GeometryCollectionJsonAdapter(moshi)
        LineString::class.java -> LineStringJsonAdapter(moshi)
        MultiLineString::class.java -> MultiLineStringJsonAdapter(moshi)
        MultiPoint::class.java -> MultiPointJsonAdapter(moshi)
        MultiPolygon::class.java -> MultiPolygonJsonAdapter(moshi)
        Point::class.java -> PointJsonAdapter(moshi)
        Polygon::class.java -> PolygonJsonAdapter(moshi)
        else -> null
    }
}

val geoJsonMoshi = Moshi.Builder().add(geoJsonMoshiAdapter).build()

fun GeoJson.toJson(indent: String = "", moshi: Moshi = geoJsonMoshi): String {
    val adapter = moshi.adapter(GeoJson::class.java).indent(indent)
    return adapter.toJson(this)
}

@JvmName("toGeoJsonFormat")
fun String.toGeoJson(moshi: Moshi = geoJsonMoshi): GeoJson? = this.toGeoJson<GeoJson>()

inline fun <reified T : GeoJson> String.toGeoJson(moshi: Moshi = geoJsonMoshi): T? {
    val adapter = moshi.adapter(GeoJson::class.java)
    return adapter.fromJson(this) as? T
}

internal fun expectedGeoJsonTypeOrThrow(expectedType: GeoJsonType, actualType: GeoJsonType?, reader: JsonReader) {
    if (expectedType != actualType) {
        throw JsonDataException("Expected GeoJSON of type '$expectedType' but got '$actualType' at ${reader.path}")
    }
}
