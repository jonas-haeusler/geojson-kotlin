data class MultiPoint(
    val coordinates: List<Position>
) : Geometry(type = GeoJsonType.MultiPoint)
