import kotlin.test.Test
import kotlin.test.assertFailsWith

class PolygonTest {

    @Test
    fun `First and last position of exterior ring must be the same`() {
        Polygon(
            exteriorRing = listOf(
                Position(0.0, 0.0),
                Position(0.1, 0.0),
                Position(0.1, 0.1),
                Position(0.0, 0.1),
                Position(0.1, 0.0),
                Position(0.0, 0.0),
            )
        )

        assertFailsWith<AssertionError> {
            Polygon(
                exteriorRing = listOf(
                    Position(0.0, 0.0),
                    Position(0.1, 0.0),
                    Position(0.1, 0.1),
                    Position(0.0, 0.1),
                    Position(0.1, 0.0),
                )
            )
        }
    }

    @Test
    fun `First and last position of interior rings must be the same`() {
        Polygon(
            exteriorRing = listOf(
                Position(0.0, 0.0),
                Position(0.1, 0.0),
                Position(0.1, 0.1),
                Position(0.0, 0.1),
                Position(0.1, 0.0),
                Position(0.0, 0.0),
            ),
            listOf(
                Position(0.0, 0.0),
                Position(0.1, 0.0),
                Position(0.1, 0.1),
                Position(0.0, 0.1),
                Position(0.1, 0.0),
                Position(0.0, 0.0),
            ),
            listOf(
                Position(0.0, 0.0),
                Position(0.1, 0.0),
                Position(0.1, 0.1),
                Position(0.0, 0.1),
                Position(0.1, 0.0),
                Position(0.0, 0.0),
            )
        )

        assertFailsWith<AssertionError> {
            Polygon(
                exteriorRing = listOf(
                    Position(0.0, 0.0),
                    Position(0.1, 0.0),
                    Position(0.1, 0.1),
                    Position(0.0, 0.1),
                    Position(0.1, 0.0),
                    Position(0.0, 0.0),
                ),
                listOf(
                    Position(0.0, 0.0),
                    Position(0.1, 0.0),
                    Position(0.1, 0.1),
                    Position(0.0, 0.1),
                    Position(0.1, 0.0),
                ),
                listOf(
                    Position(0.0, 0.0),
                    Position(0.1, 0.0),
                    Position(0.1, 0.1),
                    Position(0.0, 0.1),
                    Position(0.1, 0.0),
                )
            )
        }
    }
}