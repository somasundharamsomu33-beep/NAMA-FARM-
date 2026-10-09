import { Map, MapControls, MapGeoJSON } from "@/components/ui/map";
import { TAMIL_NADU_CENTER, TAMIL_NADU_BOUNDS } from "@/components/basic-map";

export interface BlankTamilNaduMapProps {
  className?: string;
  geojsonUrl?: string;
}

export function BlankTamilNaduMap({
  className = "h-[450px] w-full overflow-hidden rounded-xl border border-border bg-slate-950/5 dark:bg-slate-900/50 shadow-sm",
  geojsonUrl = "/tamil-nadu.geojson",
}: BlankTamilNaduMapProps) {
  return (
    <div className={className}>
      <Map
        blank
        center={TAMIL_NADU_CENTER}
        zoom={6.8}
        minZoom={6}
        maxZoom={15}
        maxBounds={TAMIL_NADU_BOUNDS}
      >
        <MapControls position="bottom-right" showZoom showCompass showFullscreen />
        <MapGeoJSON
          data={geojsonUrl}
          linePaint={{
            "line-color": "#3b82f6",
            "line-width": 1.2,
            "line-opacity": 0.8,
          }}
          fillPaint={{
            "fill-color": "#3b82f6",
            "fill-opacity": 0.15,
          }}
        />
      </Map>
    </div>
  );
}

// Backward compatibility alias
export const BlankCountriesMap = BlankTamilNaduMap;
