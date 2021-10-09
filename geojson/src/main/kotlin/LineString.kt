data class LineString(
    val coordinates: List<Position>
) : Geometry(type = GeoJsonType.LineString)