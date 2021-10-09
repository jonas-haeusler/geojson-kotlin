data class FeatureCollection(
    val features: List<Feature>
) : GeoJson(type = GeoJsonType.FeatureCollection)