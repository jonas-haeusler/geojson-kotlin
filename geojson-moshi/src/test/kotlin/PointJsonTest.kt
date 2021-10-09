import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import org.junit.Test
import org.skyscreamer.jsonassert.JSONAssert
import kotlin.test.assertEquals
import kotlin.test.assertFails

class PointJsonTest {

    @Test
    fun `Serialize Point to JSON`() {
        val expectedJson = """{"type": "Point", "coordinates": [100.0, 0.0]}""".trimIndent()

        val point = Point(Position(100.0, 0.0))
        val actualJson = point.toJson()

        JSONAssert.assertEquals(expectedJson, actualJson, true)
    }

    @Test
    fun `Serialize Point with altitude to JSON`() {
        val expectedJson = """{"type": "Point", "coordinates": [100.0, 0.0, 50.0]}"""

        val point = Point(Position(100.0, 0.0, 50.0))
        val actualJson = point.toJson()

        JSONAssert.assertEquals(expectedJson, actualJson, true)
    }

    @Test
    fun `Deserialize JSON to Point`() {
        val expectedPoint = Point(Position(100.0, 0.0))

        val jsonPoint = """{"type": "Point", "coordinates": [100.0, 0.0]}"""
        val actualPoint = jsonPoint.toGeoJson()

        assertEquals(expectedPoint, actualPoint)
    }

    @Test
    fun `Deserialize JSON with altitude to Point`() {
        val expectedPoint = Point(Position(100.0, 0.0, 50.0))

        val jsonPoint = """{"type": "Point", "coordinates": [100.0, 0.0, 50.0]}"""
        val actualPoint = jsonPoint.toGeoJson()

        assertEquals(expectedPoint, actualPoint)
    }

    @Test
    fun `Should fail when latitude missing`() {
        val jsonPointMissingLongitude = """{"type": "Point", "coordinates": [50.0]}"""

        assertFails {
            jsonPointMissingLongitude.toGeoJson()
        }
    }

    fun test() {
        val moshiInstance = Moshi.Builder()
            .add(GeoJsonAdapter())
            .build()
        val feature = Feature(
            geometry = Point(Position(102.0, 0.5)),
            properties = mapOf("prop0" to "value0")
        )


        val adapter = moshiInstance.adapter(Feature::class.java)
        val featureJson = adapter.toJson(feature)
        val featureFromJson: Feature? = adapter.fromJson(featureJson)

        val featureJson2 = feature.toJson(indent = "  ", moshi = moshiInstance)
        val featureFromJson2 = featureJson2.toGeoJson(moshi = moshiInstance)
    }
}
