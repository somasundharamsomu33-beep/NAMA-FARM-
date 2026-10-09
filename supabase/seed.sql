-- ==============================================================================
-- UZHAVAN MARKET (NAMA-FARM) INITIAL SEED DATA FOR SUPABASE
-- Run this in Supabase SQL Editor after running schema.sql
-- ==============================================================================

-- 1. Insert Master Commodities
INSERT INTO public.commodities (id, name, tamil_name, category, default_mandi_price, price_range_min, price_range_max, arrival_tonnes, demand, trend, description)
VALUES
('veg_tomato', 'Tomato', 'தக்காளி', 'VEGETABLES', 20.00, 18.00, 24.00, 180, 'High', 'Increasing', 'Fresh and chemical-free juicy tomatoes, harvested directly from local fields.'),
('veg_onion', 'Onion', 'வெங்காயம்', 'VEGETABLES', 16.00, 14.00, 19.00, 310, 'High', 'Stable', 'Pungent and firm medium-sized red onions, cured for good shelf life.'),
('veg_brinjal', 'Brinjal (Eggplant)', 'கத்தரிக்காய்', 'VEGETABLES', 25.00, 22.00, 28.00, 95, 'Moderate', 'Increasing', 'Tender purple brinjal with minimal seeds, freshly picked from field.'),
('veg_potato', 'Potato', 'உருளைக்கிழங்கு', 'VEGETABLES', 22.00, 20.00, 25.00, 420, 'High', 'Stable', 'Solid earthy grade A potatoes suitable for chips, curries, and storage.'),
('veg_carrot', 'Carrot', 'கேரட்', 'VEGETABLES', 35.00, 30.00, 42.00, 120, 'High', 'Increasing', 'Sweet, crunchy Ooty and local variety carrots.'),
('veg_banana', 'Banana (Poovan/Robusta)', 'வாழைப்பழம்', 'VEGETABLES', 28.00, 24.00, 32.00, 150, 'High', 'Stable', 'Naturally ripened farm bananas with rich aroma and sweetness.')
ON CONFLICT (id) DO UPDATE SET
    default_mandi_price = EXCLUDED.default_mandi_price,
    price_range_min = EXCLUDED.price_range_min,
    price_range_max = EXCLUDED.price_range_max;

-- 2. Insert Sample Farmers
INSERT INTO public.farmers (id, name, phone, district, village, rating, review_count, verified)
VALUES
('farmer_ramasamy', 'Ramasamy', '+91 98765 43210', 'Tiruvannamalai', 'Vellore', 4.80, 12, TRUE),
('farmer_selvam', 'Selvam K.', '+91 98765 43211', 'Vellore', 'Katpadi', 4.70, 8, TRUE),
('farmer_muthu', 'Muthu Raman', '+91 98765 43212', 'Dharmapuri', 'Palacode', 4.90, 19, TRUE)
ON CONFLICT (id) DO NOTHING;

-- 3. Insert Sample Product Listings
INSERT INTO public.product_listings (id, commodity_id, commodity_name, farmer_id, farmer_name, farmer_district, farmer_village, farmer_rating, review_count, quantity_kg, price_per_kg, quality_grade, available_from, description, status)
VALUES
('list_1', 'veg_tomato', 'Tomato', 'farmer_ramasamy', 'Ramasamy', 'Tiruvannamalai', 'Vellore', 4.80, 12, 500, 18.00, 'Grade A', 'Today', 'Fresh and chemical-free tomatoes, harvested today.', 'Live'),
('list_2', 'veg_onion', 'Onion', 'farmer_ramasamy', 'Ramasamy', 'Tiruvannamalai', 'Vellore', 4.80, 12, 300, 16.00, 'Grade A', 'Tomorrow', 'Graded Bellary & local pink onions, dry and firm.', 'Live'),
('list_3', 'veg_brinjal', 'Brinjal (Eggplant)', 'farmer_selvam', 'Selvam K.', 'Vellore', 'Katpadi', 4.70, 8, 250, 24.00, 'Grade A', 'Today', 'Dark purple tender brinjals freshly picked.', 'Live'),
('list_4', 'veg_potato', 'Potato', 'farmer_muthu', 'Muthu Raman', 'Dharmapuri', 'Palacode', 4.90, 19, 800, 22.00, 'Grade A', 'Today', 'Clean sorted hill potatoes, high starch quality.', 'Live')
ON CONFLICT (id) DO NOTHING;

-- 4. Insert Mandi Market Benchmark Prices
INSERT INTO public.mandi_prices (market_name, district, state, commodity_name, min_price, max_price, modal_price, arrival_tonnes, arrival_change_percent, distance_km, transport_cost_per_kg, estimated_net_return_per_kg, price_date, is_live)
VALUES
('Tiruvannamalai Uzhavar Sandhai', 'Tiruvannamalai', 'Tamil Nadu', 'Tomato', 18.00, 22.00, 20.00, 180, -26, 12, 0.50, 19.50, CURRENT_DATE, FALSE),
('Vellore APMC Mandi', 'Vellore', 'Tamil Nadu', 'Tomato', 22.00, 26.00, 24.00, 240, -15, 85, 1.80, 22.20, CURRENT_DATE, FALSE),
('Chennai Koyambedu Market', 'Chennai', 'Tamil Nadu', 'Tomato', 25.00, 30.00, 27.00, 620, -10, 195, 2.90, 24.10, CURRENT_DATE, FALSE),
('Bengaluru Yeshwanthpur APMC', 'Bengaluru', 'Karnataka', 'Tomato', 26.00, 32.00, 29.00, 480, 5, 210, 3.40, 25.60, CURRENT_DATE, FALSE);
