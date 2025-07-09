CREATE TABLE document (
                          document_id BIGSERIAL PRIMARY KEY,
                          document_title VARCHAR(255) NOT NULL,
                          file_path VARCHAR(500) NOT NULL,
                          uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          user_id BIGINT
);

CREATE TABLE summary (
                         summary_id BIGSERIAL PRIMARY KEY,
                         document_id BIGINT NOT NULL REFERENCES document(document_id) ON DELETE CASCADE,
                         keyword VARCHAR(255),
                         summary_content TEXT,
                         summary_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         section_reference TEXT
);
