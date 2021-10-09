import adapter.*
import adapter.FeatureCollectionJsonAdapter
import adapter.FeatureJsonAdapter
import adapter.GeoJsonObjectJsonAdapter
import adapter.GeometryCollectionJsonAdapter
import adapter.LineStringJsonAdapter
import adapter.MultiLineStringJsonAdapter
import adapter.MultiPointJsonAdapter
import adapter.MultiPolygonJsonAdapter
import adapter.PointJsonAdapter
import adapter.PolygonJsonAdapter
import adapter.PositionJsonAdapter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type

class GeoJsonAdapter : JsonAdapter.Factory {
    override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
        return when (type) {
            Position::class.java -> PositionJsonAdapter()
            GeoJson::class.java -> GeoJsonObjectJsonAdapter(moshi)
            Feature::class.java -> FeatureJsonAdapter(moshi)
            FeatureCollection::class.java -> FeatureCollectionJsonAdapter(moshi)
            GeometryCollection::class.java -> GeometryCollectionJsonAdapter(moshi)
            LineString::class.java -> LineStringJsonAdapter(moshi)
            MultiLineString::class.java -> MultiLineStringJsonAdapter(moshi)
            MultiPoint::class.java -> MultiPointJsonAdapter(moshi)
            MultiPolygon::class.java -> MultiPolygonJsonAdapter(moshi)
            Point::class.java -> PointJsonAdapter(moshi)
            Polygon::class.java -> PolygonJsonAdapter(moshi)
            else -> null
        }
    }
}