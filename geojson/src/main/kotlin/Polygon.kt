data class Polygon(
    val coordinates: List<List<Position>>
) : Geometry(type = GeoJsonType.Polygon) {

    val exteriorRing: List<Position>?
        get() = if (coordinates.isEmpty()) null else coordinates[0]

    val interiorRings: List<List<Position>>?
        get() = if (coordinates.size > 1) coordinates.drop(1) else null
}