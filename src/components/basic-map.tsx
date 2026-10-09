import { Map, MapControls, MapGeoJSON } from "@/components/ui/map";

export const TAMIL_NADU_CENTER: [number, number] = [78.6569, 11.1271];
export const TAMIL_NADU_BOUNDS: [[number, number], [number, number]] = [
  [76.0, 8.0],
  [80.5, 13.6],
];

export interface BasicMapProps {
  className?: string;
  center?: [number, number];
  zoom?: number;
}

export function BasicMap({
  className = "h-[450px] w-full overflow-hidden rounded-xl border border-border shadow-sm",
  center = TAMIL_NADU_CENTER,
  zoom = 7,
}: BasicMapProps) {
  return (
    <div className={className}>
      <Map
        center={center}
        zoom={zoom}
        minZoom={6}
        maxZoom={15}
        maxBounds={TAMIL_NADU_BOUNDS}
      >
        <MapControls position="bottom-right" showZoom showCompass showFullscreen />
        {/* Tamil Nadu district boundary outline */}
        <MapGeoJSON
          data="/tamil-nadu.geojson"
          linePaint={{
            "line-color": "#2563eb",
            "line-width": 1.2,
            "line-opacity": 0.8,
          }}
          fillPaint={{
            "fill-color": "#2563eb",
            "fill-opacity": 0.05,
          }}
        />
      </Map>
    </div>
  );
}
