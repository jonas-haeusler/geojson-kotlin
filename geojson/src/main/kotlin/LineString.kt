data class LineString(
    val coordinates: List<Position>
) : Geometry(type = GeoJsonType.LineString) {
    init {
        assert(coordinates.size >= 2) { "LineString must consist of at least two positions!" }
    }
}