data class MultiPolygon(
    val coordinates: List<List<List<Position>>>
) : Geometry(type = GeoJsonType.MultiPolygon)