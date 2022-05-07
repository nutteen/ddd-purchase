CREATE TABLE purchase_order(
    id VARCHAR (36) PRIMARY KEY,
    company_id VARCHAR(36) NOT NULL,
    limit_amount DECIMAL(17, 2) NOT NULL,
    total_amount DECIMAL(17, 2) NOT NULL,
    version BIGINT
);

CREATE INDEX company_idx ON purchase_order(company_id);

CREATE TABLE purchase_order_line (
    id VARCHAR(36) NOT NULL,
    po_id VARCHAR(36) NOT NULL,
    unit INT NOT NULL,
    unit_price DECIMAL(17, 2) NOT NULL,
    part_id VARCHAR(36) NOT NULL,
    PRIMARY KEY(po_id, id)
);