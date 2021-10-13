import org.junit.Test
import java.lang.AssertionError
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LineStringTest {

    @Test
    fun `LineString must have two or more positions`() {
        val noPositionError = assertFailsWith<AssertionError> {
            LineString(coordinates = emptyList())
        }
        assertEquals("LineString must consist of at least two positions!", noPositionError.message)

        val onePositionError = assertFailsWith<AssertionError> {
            LineString(coordinates = listOf(Position(0.0, 0.0)))
        }
        assertEquals("LineString must consist of at least two positions!", onePositionError.message)

        LineString(coordinates = listOf(Position(0.0, 0.0), Position(0.0, 0.0)))
    }
}