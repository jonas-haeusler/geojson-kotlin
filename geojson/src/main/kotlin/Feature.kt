data class Feature(
    val geometry: Geometry? = null,
    val properties: Map<*, *>? = null,
    val id: String? = null
) : GeoJson(type = GeoJsonType.Feature)