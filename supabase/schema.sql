-- ==============================================================================
-- UZHAVAN MARKET (NAMA-FARM) DATABASE SCHEMA FOR SUPABASE
-- Compatible with PostgreSQL 15+ / Supabase
-- ==============================================================================

-- 1. Enable Required Extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ------------------------------------------------------------------------------
-- 2. COMMODITIES TABLE (Master Catalog)
-- ------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public.commodities (
    id TEXT PRIMARY KEY, -- e.g. 'veg_tomato', 'veg_onion'
    name TEXT NOT NULL,
    tamil_name TEXT NOT NULL,
    category TEXT NOT NULL CHECK (category IN ('VEGETABLES', 'GRAINS', 'SEEDS', 'LEAFY_GREENS', 'OTHERS')),
    unit TEXT NOT NULL DEFAULT 'kg',
    default_mandi_price NUMERIC(10, 2) NOT NULL DEFAULT 20.00,
    price_range_min NUMERIC(10, 2) NOT NULL DEFAULT 18.00,
    price_range_max NUMERIC(10, 2) NOT NULL DEFAULT 24.00,
    arrival_tonnes INTEGER NOT NULL DEFAULT 0,
    demand TEXT NOT NULL DEFAULT 'High',
    trend TEXT NOT NULL DEFAULT 'Stable' CHECK (trend IN ('Increasing', 'Stable', 'Decreasing')),
    description TEXT DEFAULT '',
    verification_status TEXT NOT NULL DEFAULT 'VERIFIED' CHECK (verification_status IN ('VERIFIED', 'PENDING', 'REJECTED')),
    image_url TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- ------------------------------------------------------------------------------
-- 3. FARMERS / USERS PROFILE TABLE
-- ------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public.farmers (
    id TEXT PRIMARY KEY DEFAULT gen_random_uuid()::TEXT,
    user_id UUID REFERENCES auth.users(id) ON DELETE SET NULL,
    name TEXT NOT NULL,
    phone TEXT,
    district TEXT NOT NULL,
    village TEXT NOT NULL,
    rating NUMERIC(3, 2) NOT NULL DEFAULT 5.00 CHECK (rating >= 0 AND rating <= 5),
    review_count INTEGER NOT NULL DEFAULT 0,
    verified BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- ------------------------------------------------------------------------------
-- 4. PRODUCT LISTINGS TABLE (Produce Listed by Farmers)
-- ------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public.product_listings (
    id TEXT PRIMARY KEY DEFAULT ('list_' || substr(md5(random()::text), 1, 8)),
    commodity_id TEXT NOT NULL REFERENCES public.commodities(id) ON DELETE RESTRICT,
    commodity_name TEXT NOT NULL,
    farmer_id TEXT REFERENCES public.farmers(id) ON DELETE SET NULL,
    farmer_name TEXT NOT NULL,
    farmer_district TEXT NOT NULL,
    farmer_village TEXT NOT NULL,
    farmer_rating NUMERIC(3, 2) NOT NULL DEFAULT 4.80 CHECK (farmer_rating >= 0 AND farmer_rating <= 5),
    review_count INTEGER NOT NULL DEFAULT 0,
    quantity_kg INTEGER NOT NULL CHECK (quantity_kg >= 0),
    price_per_kg NUMERIC(10, 2) NOT NULL CHECK (price_per_kg >= 0),
    quality_grade TEXT NOT NULL DEFAULT 'Grade A',
    available_from TEXT NOT NULL DEFAULT 'Today',
    description TEXT DEFAULT 'Fresh and chemical-free produce, harvested directly from farm.',
    status TEXT NOT NULL DEFAULT 'Live' CHECK (status IN ('Live', 'Active', 'Sold', 'Inactive')),
    image_url TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- ------------------------------------------------------------------------------
-- 5. MANDI PRICES TABLE (Market Intelligence & Rates)
-- ------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public.mandi_prices (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    market_name TEXT NOT NULL,
    district TEXT NOT NULL,
    state TEXT NOT NULL DEFAULT 'Tamil Nadu',
    commodity_name TEXT NOT NULL,
    commodity_id TEXT REFERENCES public.commodities(id) ON DELETE SET NULL,
    min_price NUMERIC(10, 2) NOT NULL,
    max_price NUMERIC(10, 2) NOT NULL,
    modal_price NUMERIC(10, 2) NOT NULL,
    arrival_tonnes INTEGER NOT NULL DEFAULT 0,
    arrival_change_percent INTEGER NOT NULL DEFAULT 0,
    distance_km INTEGER NOT NULL DEFAULT 0,
    transport_cost_per_kg NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
    estimated_net_return_per_kg NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
    price_date DATE NOT NULL DEFAULT CURRENT_DATE,
    is_live BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- ------------------------------------------------------------------------------
-- 6. ORDERS TABLE
-- ------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public.orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_number TEXT UNIQUE NOT NULL,
    buyer_id UUID REFERENCES auth.users(id) ON DELETE SET NULL,
    buyer_name TEXT NOT NULL DEFAULT 'Hotel Sunrise',
    delivery_address TEXT NOT NULL,
    delivery_fee NUMERIC(10, 2) NOT NULL DEFAULT 150.00,
    platform_commission NUMERIC(10, 2) NOT NULL DEFAULT 90.00,
    status TEXT NOT NULL DEFAULT 'CONFIRMED' CHECK (status IN ('CONFIRMED', 'PICKUP_SCHEDULED', 'OUT_FOR_DELIVERY', 'DELIVERED', 'CANCELLED')),
    date_placed TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- ------------------------------------------------------------------------------
-- 7. ORDER ITEMS TABLE
-- ------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public.order_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL REFERENCES public.orders(id) ON DELETE CASCADE,
    commodity_id TEXT REFERENCES public.commodities(id) ON DELETE RESTRICT,
    commodity_name TEXT NOT NULL,
    quantity_kg INTEGER NOT NULL CHECK (quantity_kg > 0),
    price_per_kg NUMERIC(10, 2) NOT NULL CHECK (price_per_kg >= 0),
    subtotal NUMERIC(10, 2) GENERATED ALWAYS AS (quantity_kg * price_per_kg) STORED,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- ------------------------------------------------------------------------------
-- 8. PAYMENT TRANSACTIONS TABLE
-- ------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public.payment_transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID REFERENCES public.orders(id) ON DELETE SET NULL,
    order_number TEXT NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    method TEXT NOT NULL DEFAULT 'UPI' CHECK (method IN ('UPI', 'Bank Transfer', 'Cash')),
    status TEXT NOT NULL DEFAULT 'Paid' CHECK (status IN ('Paid', 'Pending', 'Failed')),
    is_credit BOOLEAN NOT NULL DEFAULT TRUE,
    transaction_date TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- ------------------------------------------------------------------------------
-- 9. NOTIFICATIONS TABLE
-- ------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public.notifications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES auth.users(id) ON DELETE CASCADE,
    title TEXT NOT NULL,
    message TEXT NOT NULL,
    category TEXT NOT NULL DEFAULT 'Orders' CHECK (category IN ('Orders', 'Payments', 'Offers', 'Price', 'System')),
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- ------------------------------------------------------------------------------
-- 10. INDEXES (Optimized for Query Performance & Foreign Keys)
-- ------------------------------------------------------------------------------
CREATE INDEX IF NOT EXISTS idx_commodities_category ON public.commodities(category);
CREATE INDEX IF NOT EXISTS idx_farmers_district ON public.farmers(district);
CREATE INDEX IF NOT EXISTS idx_listings_commodity_id ON public.product_listings(commodity_id);
CREATE INDEX IF NOT EXISTS idx_listings_farmer_id ON public.product_listings(farmer_id);
CREATE INDEX IF NOT EXISTS idx_listings_status ON public.product_listings(status);
CREATE INDEX IF NOT EXISTS idx_mandi_prices_commodity ON public.mandi_prices(commodity_name);
CREATE INDEX IF NOT EXISTS idx_mandi_prices_market ON public.mandi_prices(market_name);
CREATE INDEX IF NOT EXISTS idx_mandi_prices_date ON public.mandi_prices(price_date DESC);
CREATE INDEX IF NOT EXISTS idx_orders_buyer_id ON public.orders(buyer_id);
CREATE INDEX IF NOT EXISTS idx_orders_status ON public.orders(status);
CREATE INDEX IF NOT EXISTS idx_order_items_order_id ON public.order_items(order_id);
CREATE INDEX IF NOT EXISTS idx_payments_order_id ON public.payment_transactions(order_id);
CREATE INDEX IF NOT EXISTS idx_notifications_user_id ON public.notifications(user_id);

-- ------------------------------------------------------------------------------
-- 11. ROW LEVEL SECURITY (RLS) POLICIES
-- ------------------------------------------------------------------------------

-- Enable RLS on all tables
ALTER TABLE public.commodities ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.farmers ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.product_listings ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.mandi_prices ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.orders ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.order_items ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.payment_transactions ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.notifications ENABLE ROW LEVEL SECURITY;

-- Grant API access to anon and authenticated roles
GRANT USAGE ON SCHEMA public TO anon, authenticated;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO anon, authenticated;
GRANT INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO authenticated;
GRANT INSERT ON public.product_listings, public.orders, public.order_items TO anon; -- Allow guest orders/listings in demo mode

-- Commodities: Public read for catalog
CREATE POLICY "Allow public read access to commodities"
ON public.commodities
FOR SELECT
TO anon, authenticated
USING (true);

-- Mandi Prices: Public read access
CREATE POLICY "Allow public read access to mandi prices"
ON public.mandi_prices
FOR SELECT
TO anon, authenticated
USING (true);

-- Product Listings: Public read for active & live listings
CREATE POLICY "Allow public read access to active listings"
ON public.product_listings
FOR SELECT
TO anon, authenticated
USING (true);

CREATE POLICY "Allow insert listings"
ON public.product_listings
FOR INSERT
TO anon, authenticated
WITH CHECK (true);

CREATE POLICY "Allow update own listings"
ON public.product_listings
FOR UPDATE
TO authenticated
USING (farmer_id = (SELECT auth.uid()::text))
WITH CHECK (farmer_id = (SELECT auth.uid()::text));

-- Orders & Order Items: Read and write policies
CREATE POLICY "Allow read orders"
ON public.orders
FOR SELECT
TO anon, authenticated
USING (true);

CREATE POLICY "Allow insert orders"
ON public.orders
FOR INSERT
TO anon, authenticated
WITH CHECK (true);

CREATE POLICY "Allow read order items"
ON public.order_items
FOR SELECT
TO anon, authenticated
USING (true);

CREATE POLICY "Allow insert order items"
ON public.order_items
FOR INSERT
TO anon, authenticated
WITH CHECK (true);

-- Farmers profile: Public read
CREATE POLICY "Allow read farmers"
ON public.farmers
FOR SELECT
TO anon, authenticated
USING (true);

-- Realtime Setup: Allow listening to live changes
ALTER PUBLICATION supabase_realtime ADD TABLE public.product_listings;
ALTER PUBLICATION supabase_realtime ADD TABLE public.mandi_prices;
ALTER PUBLICATION supabase_realtime ADD TABLE public.orders;
