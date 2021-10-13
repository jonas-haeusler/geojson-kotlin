data class Position(
    val longitude: Double,
    val latitude: Double,
    val altitude: Double? = null
) {
    private val minLongitude = -180.0
    private val maxLongitude = 180.0

    private val minLatitude = -90.0
    private val maxLatitude = 90.0

    init {
        assert(longitude in minLongitude..maxLongitude) {
            "Longitude must be between -180.0 and +180.0 but was $longitude!"
        }

        assert(latitude in minLatitude..maxLatitude) {
            "Latitude must be between -90.0 and +90.0 but was $latitude!"
        }
    }
}
