data class Point(
    val coordinates: Position
) : Geometry(type = GeoJsonType.Point)