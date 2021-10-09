data class MultiLineString(
    val coordinates: List<List<Position>>
) : Geometry(type = GeoJsonType.MultiLineString)
