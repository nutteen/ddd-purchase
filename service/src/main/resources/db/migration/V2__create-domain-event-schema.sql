CREATE TABLE domain_event (
  id VARCHAR(36) PRIMARY KEY,
  `type` VARCHAR(100) NOT NULL,
  serialized_event text NOT NULL
)