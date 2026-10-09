import { useState } from "react";
import { TamilNaduMap, TN_MANDIS, TAMIL_NADU_CENTER } from "@/components/tamil-nadu-map";
import { BlankTamilNaduMap } from "@/components/blank-countries-map";
import { Layers, Globe, Store, Sun, Moon, MapPin, Wheat } from "lucide-react";

export function App() {
  const [activeTab, setActiveTab] = useState<"tiled" | "blank" | "mandis">("tiled");
  const [selectedMandi, setSelectedMandi] = useState(TN_MANDIS[0]);
  const [isDark, setIsDark] = useState(false);

  const toggleTheme = () => {
    setIsDark(!isDark);
    document.documentElement.classList.toggle("dark");
  };

  return (
    <div className={`min-h-screen ${isDark ? "dark" : ""} bg-background text-foreground transition-colors`}>
      {/* Header */}
      <header className="border-b border-border bg-card/70 backdrop-blur-md sticky top-0 z-20">
        <div className="max-w-6xl mx-auto px-4 sm:px-6 py-3.5 flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="h-10 w-10 rounded-xl bg-emerald-600 text-white flex items-center justify-center font-bold shadow-md">
              <Wheat className="h-5 w-5" />
            </div>
            <div>
              <div className="flex items-center gap-2">
                <h1 className="text-base sm:text-lg font-bold tracking-tight">Uzhavan Market</h1>
                <span className="text-[11px] font-semibold bg-emerald-500/10 text-emerald-600 dark:text-emerald-400 px-2 py-0.5 rounded-full border border-emerald-500/20">
                  Tamil Nadu Only
                </span>
              </div>
              <p className="text-xs text-muted-foreground">Statewide Mandi Intelligence & Agricultural Map</p>
            </div>
          </div>
          <button
            onClick={toggleTheme}
            className="p-2 rounded-lg border border-border hover:bg-muted transition-colors text-muted-foreground hover:text-foreground"
            aria-label="Toggle theme"
          >
            {isDark ? <Sun className="h-4 w-4" /> : <Moon className="h-4 w-4" />}
          </button>
        </div>
      </header>

      {/* Main Content */}
      <main className="max-w-6xl mx-auto px-4 sm:px-6 py-6 space-y-6">
        {/* Navigation Tabs */}
        <div className="flex flex-wrap items-center justify-between gap-4 border-b border-border pb-4">
          <div className="flex flex-wrap gap-2">
            <button
              onClick={() => setActiveTab("tiled")}
              className={`flex items-center gap-2 px-3.5 py-2 rounded-lg text-xs sm:text-sm font-medium transition-colors ${
                activeTab === "tiled"
                  ? "bg-primary text-primary-foreground shadow-sm"
                  : "bg-muted/50 text-muted-foreground hover:bg-muted hover:text-foreground"
              }`}
            >
              <Layers className="h-4 w-4" />
              Tiled TN Map (Streets & Roads)
            </button>
            <button
              onClick={() => setActiveTab("blank")}
              className={`flex items-center gap-2 px-3.5 py-2 rounded-lg text-xs sm:text-sm font-medium transition-colors ${
                activeTab === "blank"
                  ? "bg-primary text-primary-foreground shadow-sm"
                  : "bg-muted/50 text-muted-foreground hover:bg-muted hover:text-foreground"
              }`}
            >
              <Globe className="h-4 w-4" />
              Blank TN Districts Map (&lt;Map blank&gt;)
            </button>
            <button
              onClick={() => setActiveTab("mandis")}
              className={`flex items-center gap-2 px-3.5 py-2 rounded-lg text-xs sm:text-sm font-medium transition-colors ${
                activeTab === "mandis"
                  ? "bg-primary text-primary-foreground shadow-sm"
                  : "bg-muted/50 text-muted-foreground hover:bg-muted hover:text-foreground"
              }`}
            >
              <Store className="h-4 w-4" />
              State Mandi Hubs
            </button>
          </div>

          <div className="text-xs text-muted-foreground">
            Bounds: <span className="font-mono text-foreground font-semibold">Locked to Tamil Nadu</span>
          </div>
        </div>

        {/* Map Display Card */}
        <section className="space-y-4">
          {activeTab === "tiled" && (
            <div className="space-y-3">
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-2">
                <div>
                  <h2 className="text-base font-bold tracking-tight">Tamil Nadu Tiled Basemap with Districts & Mandis</h2>
                  <p className="text-xs text-muted-foreground">
                    Bounded exclusively to Tamil Nadu. Hover over any district to inspect its boundary.
                  </p>
                </div>
              </div>

              <TamilNaduMap mode="tiled" showMandis className="h-[520px]" />

              <div className="rounded-xl border border-border bg-card/60 p-3.5 text-xs font-mono text-muted-foreground">
                <code>{`// Tamil Nadu Tiled Basemap locked to TN Bounding Box
<Map
  center={[78.6569, 11.1271]}
  zoom={6.8}
  minZoom={6}
  maxBounds={[[76.0, 8.0], [80.5, 13.6]]}
>
  <MapControls position="bottom-right" />
  <MapGeoJSON data="/tamil-nadu.geojson" />
</Map>`}</code>
              </div>
            </div>
          )}

          {activeTab === "blank" && (
            <div className="space-y-3">
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-2">
                <div>
                  <h2 className="text-base font-bold tracking-tight">Tamil Nadu Blank / Data-Only Districts Map</h2>
                  <p className="text-xs text-muted-foreground">
                    Using <code className="px-1.5 py-0.5 rounded bg-muted text-xs">&lt;Map blank&gt;</code> with <code className="px-1.5 py-0.5 rounded bg-muted text-xs">&lt;MapGeoJSON data="/tamil-nadu.geojson"&gt;</code>.
                  </p>
                </div>
              </div>

              <BlankTamilNaduMap className="h-[520px]" />

              <div className="rounded-xl border border-border bg-card/60 p-3.5 text-xs font-mono text-muted-foreground">
                <code>{`// Blank Tamil Nadu Map - Only State & District Geometries Rendered
<Map
  blank
  center={[78.6569, 11.1271]}
  zoom={6.8}
  maxBounds={[[76.0, 8.0], [80.5, 13.6]]}
>
  <MapControls position="bottom-right" />
  <MapGeoJSON data="/tamil-nadu.geojson" />
</Map>`}</code>
              </div>
            </div>
          )}

          {activeTab === "mandis" && (
            <div className="grid grid-cols-1 lg:grid-cols-3 gap-4">
              <div className="lg:col-span-2 space-y-3">
                <TamilNaduMap mode="tiled" showMandis className="h-[520px]" />
              </div>

              {/* Mandis List Panel */}
              <div className="rounded-2xl border border-border bg-card p-4 space-y-3 flex flex-col justify-between">
                <div>
                  <h3 className="text-sm font-bold tracking-tight flex items-center gap-2">
                    <Store className="h-4 w-4 text-emerald-600" />
                    Key Agricultural Mandis
                  </h3>
                  <p className="text-xs text-muted-foreground mt-0.5">
                    Click a mandi to view location details in Tamil Nadu.
                  </p>
                  <div className="mt-3 space-y-2 max-h-[380px] overflow-y-auto pr-1">
                    {TN_MANDIS.map((mandi) => (
                      <button
                        key={mandi.name}
                        onClick={() => setSelectedMandi(mandi)}
                        className={`w-full text-left p-2.5 rounded-xl border text-xs transition-colors ${
                          selectedMandi.name === mandi.name
                            ? "border-emerald-500 bg-emerald-500/10 font-semibold"
                            : "border-border hover:bg-muted/60"
                        }`}
                      >
                        <div className="font-semibold text-foreground flex items-center justify-between">
                          <span>{mandi.name}</span>
                          <span className="text-[10px] text-muted-foreground">{mandi.city}</span>
                        </div>
                        <div className="text-[11px] text-muted-foreground mt-1">
                          Produce: <span className="text-emerald-700 dark:text-emerald-400">{mandi.crop}</span>
                        </div>
                      </button>
                    ))}
                  </div>
                </div>

                <div className="rounded-xl border border-border bg-muted/40 p-3 text-[11px] space-y-1">
                  <div className="font-medium text-foreground">Selected: {selectedMandi.name}</div>
                  <div className="text-muted-foreground">City: {selectedMandi.city}</div>
                  <div className="text-muted-foreground">Coords: {selectedMandi.coords.join(", ")}</div>
                </div>
              </div>
            </div>
          )}
        </section>

        {/* Features Summary */}
        <section className="grid grid-cols-1 md:grid-cols-3 gap-4 pt-2">
          <div className="p-4 rounded-xl border border-border bg-card space-y-2">
            <div className="flex items-center gap-2 text-emerald-600 dark:text-emerald-400 font-semibold text-sm">
              <MapPin className="h-4 w-4" />
              <span>Strictly Bounded to TN</span>
            </div>
            <p className="text-xs text-muted-foreground leading-relaxed">
              Enforced with <code className="bg-muted px-1 py-0.5 rounded">maxBounds</code> `[[76.0, 8.0], [80.5, 13.6]]` and `minZoom: 6` so panning cannot wander outside Tamil Nadu.
            </p>
          </div>

          <div className="p-4 rounded-xl border border-border bg-card space-y-2">
            <div className="flex items-center gap-2 text-emerald-600 dark:text-emerald-400 font-semibold text-sm">
              <Globe className="h-4 w-4" />
              <span>All 38 TN Districts GeoJSON</span>
            </div>
            <p className="text-xs text-muted-foreground leading-relaxed">
              Detailed vector boundaries for Tamil Nadu districts bundled locally at <code className="bg-muted px-1 py-0.5 rounded">public/tamil-nadu.geojson</code> with hover interactions.
            </p>
          </div>

          <div className="p-4 rounded-xl border border-border bg-card space-y-2">
            <div className="flex items-center gap-2 text-emerald-600 dark:text-emerald-400 font-semibold text-sm">
              <Store className="h-4 w-4" />
              <span>Uzhavan Agri Mandis</span>
            </div>
            <p className="text-xs text-muted-foreground leading-relaxed">
              Pinpoints prominent APMC mandis including Koyambedu, Oddanchatram, Pollachi, Salem, Madurai, Erode, and Thanjavur with custom marker popups.
            </p>
          </div>
        </section>
      </main>
    </div>
  );
}

export default App;
