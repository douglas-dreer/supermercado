CREATE TABLE products (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          name VARCHAR(200) NOT NULL,
                          description VARCHAR(1000),
                          barcode VARCHAR(50) NOT NULL UNIQUE,
                          price DECIMAL(10,2) NOT NULL CHECK (price > 0),
                          stock_quantity INTEGER NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
                          min_stock_quantity INTEGER NOT NULL DEFAULT 0 CHECK (min_stock_quantity >= 0),
                          brand_id UUID NOT NULL,
                          category VARCHAR(100) NOT NULL,
                          active BOOLEAN NOT NULL DEFAULT true,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                          CONSTRAINT fk_products_brand_id FOREIGN KEY (brand_id) REFERENCES brands(id) ON DELETE CASCADE
);

CREATE INDEX idx_products_name ON products(name);
CREATE INDEX idx_products_barcode ON products(barcode);
CREATE INDEX idx_products_brand_id ON products(brand_id);
CREATE INDEX idx_products_category ON products(category);
CREATE INDEX idx_products_active ON products(active);
CREATE INDEX idx_products_created_at ON products(created_at);
CREATE INDEX idx_products_stock_quantity ON products(stock_quantity);

-- Add trigger for updated_at on brands
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_brands_updated_at
    BEFORE UPDATE ON brands
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_products_updated_at
    BEFORE UPDATE ON products
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();