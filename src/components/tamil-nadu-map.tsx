import { useState } from "react";
import {
  Map,
  MapControls,
  MapGeoJSON,
  MapMarker,
  MarkerContent,
  MarkerPopup,
  type MapGeoJSONEvent,
} from "@/components/ui/map";
import { Store, MapPin } from "lucide-react";

// Tamil Nadu Bounding Box: [minLng, minLat], [maxLng, maxLat]
export const TAMIL_NADU_BOUNDS: [[number, number], [number, number]] = [
  [76.0, 8.0],   // Southwest: Kanyakumari / Kerala border
  [80.5, 13.6],  // Northeast: Pulicat / Chennai / Andhra border
];

export const TAMIL_NADU_CENTER: [number, number] = [78.6569, 11.1271]; // Tiruchirappalli (Center of TN)

export const TN_MANDIS = [
  {
    name: "Koyambedu Wholesale Market",
    city: "Chennai",
    crop: "Vegetables, Fruits & Flowers",
    coords: [80.1915, 13.0694] as [number, number],
  },
  {
    name: "Oddanchatram Vegetable Market",
    city: "Dindigul",
    crop: "Drumsticks, Chillies & Beans",
    coords: [77.7471, 10.4800] as [number, number],
  },
  {
    name: "Pollachi Agri Market",
    city: "Coimbatore",
    crop: "Coconut, Coir & Jaggery",
    coords: [77.0094, 10.6609] as [number, number],
  },
  {
    name: "Erode Turmeric Mandi",
    city: "Erode",
    crop: "Turmeric & Agricultural Grains",
    coords: [77.7172, 11.341] as [number, number],
  },
  {
    name: "Thanjavur Grain Market",
    city: "Thanjavur",
    crop: "Paddy & Delta Pulses",
    coords: [79.1378, 10.787] as [number, number],
  },
  {
    name: "Salem Sago & Mango Market",
    city: "Salem",
    crop: "Salem Mangoes & Sago",
    coords: [78.146, 11.6643] as [number, number],
  },
  {
    name: "Mattuthavani Flower Market",
    city: "Madurai",
    crop: "Madurai Malli & Vegetables",
    coords: [78.1566, 9.9405] as [number, number],
  },
  {
    name: "Nellai Grain Mandi",
    city: "Tirunelveli",
    crop: "Paddy, Bananas & Spices",
    coords: [77.7567, 8.7139] as [number, number],
  },
];

interface TamilNaduMapProps {
  mode?: "tiled" | "blank";
  showMandis?: boolean;
  className?: string;
}

export function TamilNaduMap({
  mode = "tiled",
  showMandis = true,
  className = "h-[500px] w-full overflow-hidden rounded-2xl border border-border shadow-lg",
}: TamilNaduMapProps) {
  const [hoveredDistrict, setHoveredDistrict] = useState<string | null>(null);

  return (
    <div className={`relative ${className}`}>
      {hoveredDistrict && (
        <div className="absolute top-3 left-3 z-10 rounded-lg border border-border bg-background/90 px-3 py-1.5 text-xs font-semibold shadow-md backdrop-blur-sm">
          District: <span className="text-primary">{hoveredDistrict}</span>
        </div>
      )}

      <Map
        blank={mode === "blank"}
        center={TAMIL_NADU_CENTER}
        zoom={6.8}
        minZoom={6}
        maxZoom={15}
        maxBounds={TAMIL_NADU_BOUNDS}
        className="h-full w-full"
      >
        <MapControls position="bottom-right" showZoom showCompass showFullscreen />

        {/* Tamil Nadu District Boundaries via GeoJSON */}
        <MapGeoJSON
          data="/tamil-nadu.geojson"
          promoteId="district"
          interactive
          linePaint={{
            "line-color": mode === "blank" ? "#3b82f6" : "#2563eb",
            "line-width": 1.2,
            "line-opacity": 0.8,
          }}
          fillPaint={
            mode === "blank"
              ? {
                  "fill-color": "#3b82f6",
                  "fill-opacity": 0.12,
                }
              : {
                  "fill-color": "#2563eb",
                  "fill-opacity": 0.04,
                }
          }
          fillHoverPaint={{
            "fill-color": "#2563eb",
            "fill-opacity": 0.35,
          }}
          onHover={(e: MapGeoJSONEvent | null) => {
            const district = e?.feature?.properties?.district as string | undefined;
            setHoveredDistrict(district ?? null);
          }}
        />

        {/* Agricultural Mandi Markers across Tamil Nadu */}
        {showMandis &&
          TN_MANDIS.map((mandi) => (
            <MapMarker
              key={mandi.name}
              longitude={mandi.coords[0]}
              latitude={mandi.coords[1]}
            >
              <MarkerContent>
                <div className="relative flex items-center justify-center group">
                  <span className="absolute -inset-1 rounded-full bg-emerald-500/40 animate-ping" />
                  <div className="size-6 rounded-full border-2 border-white bg-emerald-600 shadow-md flex items-center justify-center text-white transition-transform group-hover:scale-125">
                    <Store className="size-3.5" />
                  </div>
                </div>
              </MarkerContent>
              <MarkerPopup closeButton>
                <div className="space-y-1.5 p-0.5">
                  <div className="flex items-center gap-1.5 font-semibold text-xs text-foreground">
                    <MapPin className="size-3.5 text-emerald-600 shrink-0" />
                    <span>{mandi.name}</span>
                  </div>
                  <div className="text-[11px] text-muted-foreground">
                    <span className="font-medium text-foreground">{mandi.city}</span>
                  </div>
                  <div className="rounded bg-muted px-2 py-1 text-[10px] text-muted-foreground">
                    Primary: {mandi.crop}
                  </div>
                </div>
              </MarkerPopup>
            </MapMarker>
          ))}
      </Map>
    </div>
  );
}
