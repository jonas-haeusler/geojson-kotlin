import com.squareup.moshi.JsonDataException
import org.junit.Test
import org.skyscreamer.jsonassert.JSONAssert
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class FeatureCollectionJsonTest {

    @Test
    fun `serialize FeatureCollection to json`() {
        val expectedJson = """
            {
            	"type": "FeatureCollection",
            	"features": [{
            		"type": "Feature",
            		"geometry": {
            			"type": "Point",
            			"coordinates": [102.0, 0.5]
            		},
            		"properties": {
            			"prop0": "value0"
            		}
            	}, {
            		"type": "Feature",
            		"geometry": {
            			"type": "LineString",
            			"coordinates": [
            				[102.0, 0.0],
            				[103.0, 1.0],
            				[104.0, 0.0],
            				[105.0, 1.0]
            			]
            		},
            		"properties": {
            			"prop0": "value0",
            			"prop1": 0.0
            		}
            	}, {
            		"type": "Feature",
            		"geometry": {
            			"type": "Polygon",
            			"coordinates": [
            				[
            					[100.0, 0.0],
            					[101.0, 0.0],
            					[101.0, 1.0],
            					[100.0, 1.0],
            					[100.0, 0.0]
            				]
            			]
            		},
            		"properties": {
            			"prop0": "value0",
            			"prop1": {
            				"this": "that"
            			}
            		}
            	}]
            }
        """.trimIndent()

        val actualJson = FeatureCollection(
            features = listOf(
                Feature(
                    geometry = Point(Position(102.0, 0.5)),
                    properties = mapOf(
                        "prop0" to "value0",
                    )
                ),
                Feature(
                    geometry = LineString(
                        coordinates = listOf(
                            Position(102.0, 0.0),
                            Position(103.0, 1.0),
                            Position(104.0, 0.0),
                            Position(105.0, 1.0),
                        )
                    ),
                    properties = mapOf(
                        "prop0" to "value0",
                        "prop1" to 0.0,
                    )
                ),
                Feature(
                    geometry = Polygon(
                        listOf(
                            listOf(
                                Position(100.0, 0.0),
                                Position(101.0, 0.0),
                                Position(101.0, 1.0),
                                Position(100.0, 1.0),
                                Position(100.0, 0.0),
                            )
                        )
                    ),
                    properties = mapOf(
                        "prop0" to "value0",
                        "prop1" to mapOf(
                            "this" to "that",
                        ),
                    )
                ),
            )
        ).toJson(indent = "  ")

        JSONAssert.assertEquals(expectedJson, actualJson, true)
    }

    @Test
    fun `Deserialize JSON to FeatureCollection`() {
        val expectedFeatureCollection = FeatureCollection(
            features = listOf(
                Feature(
                    geometry = Point(Position(102.0, 0.5)),
                    properties = mapOf(
                        "prop0" to "value0",
                    )
                ),
                Feature(
                    geometry = LineString(
                        coordinates = listOf(
                            Position(102.0, 0.0),
                            Position(103.0, 1.0),
                            Position(104.0, 0.0),
                            Position(105.0, 1.0),
                        )
                    ),
                    properties = mapOf(
                        "prop0" to "value0",
                        "prop1" to 0.0,
                    )
                ),
                Feature(
                    geometry = Polygon(
                        listOf(
                            listOf(
                                Position(100.0, 0.0),
                                Position(101.0, 0.0),
                                Position(101.0, 1.0),
                                Position(100.0, 1.0),
                                Position(100.0, 0.0),
                            )
                        )
                    ),
                    properties = mapOf(
                        "prop0" to "value0",
                        "prop1" to mapOf(
                            "this" to "that",
                        ),
                    )
                ),
            )
        )

        val jsonFeatureCollection = """
            {
            	"type": "FeatureCollection",
            	"features": [{
            		"type": "Feature",
            		"geometry": {
            			"type": "Point",
            			"coordinates": [102.0, 0.5]
            		},
            		"properties": {
            			"prop0": "value0"
            		}
            	}, {
            		"type": "Feature",
            		"geometry": {
            			"type": "LineString",
            			"coordinates": [
            				[102.0, 0.0],
            				[103.0, 1.0],
            				[104.0, 0.0],
            				[105.0, 1.0]
            			]
            		},
            		"properties": {
            			"prop0": "value0",
            			"prop1": 0.0
            		}
            	}, {
            		"type": "Feature",
            		"geometry": {
            			"type": "Polygon",
            			"coordinates": [
            				[
            					[100.0, 0.0],
            					[101.0, 0.0],
            					[101.0, 1.0],
            					[100.0, 1.0],
            					[100.0, 0.0]
            				]
            			]
            		},
            		"properties": {
            			"prop0": "value0",
            			"prop1": {
            				"this": "that"
            			}
            		}
            	}]
            }
        """.trimIndent()
        val actualFeatureCollection = jsonFeatureCollection.toGeoJson()

        assertEquals(expectedFeatureCollection, actualFeatureCollection)
    }

    @Test
    fun `Should fail when deserializing invalid JSON`() {
        val jsonFeatureCollection = """
            {
            	"type": "FeatureCollection",
            	"features": [{
            		"type": "LineString",
            		"geometry": {
            			"type": "Point",
            			"coordinates": [102.0, 0.5]
            		},
            		"properties": {
            			"prop0": "value0"
            		}
            	}, {
            		"type": "Feature",
            		"geometry": {
            			"type": "LineString",
            			"coordinates": [
            				[102.0, 0.0],
            				[103.0, 1.0],
            				[104.0, 0.0],
            				[105.0, 1.0]
            			]
            		},
            		"properties": {
            			"prop0": "value0",
            			"prop1": 0.0
            		}
            	}, {
            		"type": "Feature",
            		"geometry": {
            			"type": "Polygon",
            			"coordinates": [
            				[
            					[100.0, 0.0],
            					[101.0, 0.0],
            					[101.0, 1.0],
            					[100.0, 1.0],
            					[100.0, 0.0]
            				]
            			]
            		},
            		"properties": {
            			"prop0": "value0",
            			"prop1": {
            				"this": "that"
            			}
            		}
            	}]
            }
        """.trimIndent()

        assertFailsWith<JsonDataException> {
            jsonFeatureCollection.toGeoJson()
        }
    }
}