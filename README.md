# GeoJSON for Kotlin

`geojson-kotlin` is a Kotlin implementation of the GeoJSON spec according
to [RFC 7946](https://tools.ietf.org/html/rfc7946).

# Usage

Add a dependency on `geojson-kotlin` to start using GeoJSON in your project.

```groovy
implementation("dev.haeusler.geojson-kotlin:geojson:VERSION")
```

```kotlin
val featureCollection: FeatureCollection = FeatureCollection(
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
                    Position(105.0, 1.0)
                )
            ),
            properties = mapOf(
                "prop0" to "value0",
                "prop1" to 0.0,
            )
        )
    )
)
```

## Using geojson-kotlin with Moshi

The `geojson-moshi` artifact features a collection of json adapters to serialize/deserialize GeoJSON using Moshi.

```groovy
implementation("dev.haeusler.geojson-kotlin:geojson-moshi:VERSION")
```

To use, supply an instance of `GeoJsonAdapter` when building your Moshi instance.

```kotlin
val moshiInstance = Moshi.Builder()
    // etc...
    .add(GeoJsonAdapter())
    .build()
```

And then serialize or deserialize your objects:
```kotlin
val adapter = moshiInstance.adapter(Feature::class.java)

val feature = Feature(
    geometry = Point(Position(102.0, 0.5)),
    properties = mapOf("prop0" to "value0")
)

val featureJson = adapter.toJson(feature)

val featureFromJson: Feature? = adapter.fromJson(featureJson)
```

Alternatively, you can also use the convenience extension functions to convert to JSON and back:
```kotlin
val featureJson = feature.toJson(indent = "  ", moshi = moshiInstance)
val featureFromJson = featureJson.toGeoJson(moshi = moshiInstance)
```
If you do not supply your own moshi instance, a new one will be created for you.