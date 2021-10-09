sealed class GeoJson(
    val type: GeoJsonType,
    val bbox: List<Position>? = null
)
