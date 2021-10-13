data class Polygon(
    val coordinates: List<List<Position>>
) : Geometry(type = GeoJsonType.Polygon) {

    constructor(exteriorRing: List<Position>, vararg interiorRing: List<Position>) : this(listOf(exteriorRing, *interiorRing))

    init {
        assert(exteriorRing?.first() == exteriorRing?.last()) {
            "First and last position of the exterior ring must be equivalent and contain identical values! " +
                    "first='${exteriorRing?.first()}', last='${exteriorRing?.last()}'"
        }

        interiorRings?.forEachIndexed { index, interiorRing ->
            assert(interiorRing.first() == interiorRing.last()) {
                "First and last position of the interior ring [$index] must be equivalent and contain identical values! " +
                        "first='${interiorRing.first()}', last='${interiorRing.last()}'"
            }
        }
    }

    val exteriorRing: List<Position>?
        get() = if (coordinates.isEmpty()) null else coordinates[0]

    val interiorRings: List<List<Position>>?
        get() = if (coordinates.size > 1) coordinates.drop(1) else null
}