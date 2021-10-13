import org.junit.Test
import java.lang.AssertionError
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PositionTest {

    @Test
    fun `Should fail when longitude is out of bounds`() {
        val tooBigLongitude = 180.0000000001
        val exceptionTooBig = assertFailsWith<AssertionError> {
            Position(longitude = tooBigLongitude, latitude = 0.0)
        }
        assertEquals("Longitude must be between -180.0 and +180.0 but was $tooBigLongitude!", exceptionTooBig.message)

        val tooSmallLongitude = -181.0
        val exceptionTooSmall = assertFailsWith<AssertionError> {
            Position(longitude = tooSmallLongitude, latitude = 0.0)
        }
        assertEquals("Longitude must be between -180.0 and +180.0 but was $tooSmallLongitude!", exceptionTooSmall.message)
    }

    @Test
    fun `Should fail when latitude is out of bounds`() {
        val tooBigLatitude = 90.0000000001
        val exceptionTooBig = assertFailsWith<AssertionError> {
            Position(longitude = 0.0, latitude = tooBigLatitude)
        }
        assertEquals("Latitude must be between -90.0 and +90.0 but was $tooBigLatitude!", exceptionTooBig.message)

        val tooSmallLatitude = -91.0
        val exceptionTooSmall = assertFailsWith<AssertionError> {
            Position(longitude = 0.0, latitude = tooSmallLatitude)
        }
        assertEquals("Latitude must be between -90.0 and +90.0 but was $tooSmallLatitude!", exceptionTooSmall.message)
    }
}