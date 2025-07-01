CREATE TABLE categories (
    id INTEGER PRIMARY KEY,
    name TEXT
);

CREATE TABLE items (
    id INTEGER PRIMARY KEY,
    category_id INTEGER,
    name TEXT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);