import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi

val geoJsonMoshi: Moshi by lazy { Moshi.Builder().add(GeoJsonAdapter()).build() }

fun GeoJson.toJson(indent: String = "", moshi: Moshi = geoJsonMoshi): String {
    val adapter = moshi.adapter(GeoJson::class.java).indent(indent)
    return adapter.toJson(this)
}

@JvmName("toGeoJsonFormat")
fun String.toGeoJson(moshi: Moshi = geoJsonMoshi): GeoJson? = this.toGeoJson<GeoJson>(moshi)

inline fun <reified T : GeoJson> String.toGeoJson(moshi: Moshi = geoJsonMoshi): T? {
    val adapter = moshi.adapter(GeoJson::class.java)
    return adapter.fromJson(this) as? T
}

internal fun expectedGeoJsonTypeOrThrow(expectedType: GeoJsonType, actualType: GeoJsonType?, reader: JsonReader) {
    if (expectedType != actualType) {
        throw JsonDataException("Expected GeoJSON of type '$expectedType' but got '$actualType' at ${reader.path}")
    }
}
