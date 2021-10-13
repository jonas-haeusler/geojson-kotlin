import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import org.junit.Test
import org.skyscreamer.jsonassert.JSONAssert
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

class PointJsonTest {

    @Test
    fun `Serialize Point to JSON`() {
        val expectedJson = """{"type": "Point", "coordinates": [100.0, 0.0]}"""

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

        val exception = assertFailsWith<JsonDataException> {
            jsonPointMissingLongitude.toGeoJson()
        }
        assertEquals("Required value for latitude missing at \$.coordinates[1]", exception.message)
    }
}
