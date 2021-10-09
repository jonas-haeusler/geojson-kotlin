data class GeometryCollection(
    val geometries: List<Geometry>
) : Geometry(type = GeoJsonType.GeometryCollection)