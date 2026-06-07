-- 1. Roles Table
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL -- 'ADMIN', 'COACH', 'PLAYER', 'PARENT'
);

-- 2. Terms Table (For dynamic 3-term tracking per session)
CREATE TABLE terms (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL, -- e.g., 'Term 1 2026', 'Term 2 2026'
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    academic_year VARCHAR(9) NOT NULL, -- e.g., '2025/2026'
    is_active BOOLEAN DEFAULT FALSE,
    CONSTRAINT chk_dates CHECK (end_date > start_date)
);

-- 3. Users Table (Core Auth)
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20),
    role_id INT REFERENCES roles(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Player Profiles Table (Extends Users for Player-specific metadata)
CREATE TABLE player_profiles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    date_of_birth DATE NOT NULL,
    jersey_number INT,
    position VARCHAR(30), -- e.g., 'Striker', 'Goalkeeper'
    dominant_foot VARCHAR(10), -- 'Left', 'Right', 'Ambidextrous'
    height_cm DECIMAL(5,2),
    weight_kg DECIMAL(5,2),
    parent_id BIGINT REFERENCES users(id) ON DELETE SET NULL
);

-- 5. Age Groups Table (e.g., U10, U13)
CREATE TABLE age_groups (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL, -- 'U10', 'U13', 'U15'
    min_age INT NOT NULL,
    max_age INT NOT NULL
);

-- 6. Junction Table: Player-Group Mapping (Handles Many-to-Many relationship)
CREATE TABLE player_groups (
    player_profile_id BIGINT REFERENCES player_profiles(id) ON DELETE CASCADE,
    age_group_id INT REFERENCES age_groups(id) ON DELETE CASCADE,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (player_profile_id, age_group_id)
);

-- 1. Attendance Table
CREATE TABLE attendance (
    id BIGSERIAL PRIMARY KEY,
    player_profile_id BIGINT REFERENCES player_profiles(id) ON DELETE CASCADE,
    term_id INT REFERENCES terms(id),
    date DATE NOT NULL,
    status VARCHAR(15) NOT NULL, -- 'PRESENT', 'ABSENT', 'EXCUSED'
    is_punctual BOOLEAN DEFAULT TRUE,
    notes TEXT,
    created_by BIGINT REFERENCES users(id), -- Coach who logged it
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_player_date UNIQUE (player_profile_id, date)
);

-- 2. Performance Metric Metrics Logs (For soft-skills like discipline, work ethic, leadership)
CREATE TABLE metrics_logs (
    id BIGSERIAL PRIMARY KEY,
    player_profile_id BIGINT REFERENCES player_profiles(id) ON DELETE CASCADE,
    term_id INT REFERENCES terms(id) ON DELETE CASCADE,
    date DATE NOT NULL,
    discipline_score INT CHECK (discipline_score BETWEEN 1 AND 5),
    work_ethic_score INT CHECK (work_ethic_score BETWEEN 1 AND 5),
    leadership_score INT CHECK (leadership_score BETWEEN 1 AND 5),
    coach_feedback TEXT,
    logged_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 1. Match Fixtures & Results Table
CREATE TABLE fixtures (
    id BIGSERIAL PRIMARY KEY,
    term_id INT REFERENCES terms(id) ON DELETE CASCADE,
    opponent_name VARCHAR(100) NOT NULL,
    match_date TIMESTAMP NOT NULL,
    venue VARCHAR(155) NOT NULL, -- 'Home' or 'Away'
    status VARCHAR(20) DEFAULT 'SCHEDULED', -- 'SCHEDULED', 'PLAYED', 'CANCELLED'
    academy_score INT DEFAULT 0,
    opponent_score INT DEFAULT 0,
    notes TEXT
);

-- 2. Individual Player Match Statistics Table (For Golden Boot & Performance Awards)
CREATE TABLE player_match_stats (
    id BIGSERIAL PRIMARY KEY,
    fixture_id BIGINT REFERENCES fixtures(id) ON DELETE CASCADE,
    player_profile_id BIGINT REFERENCES player_profiles(id) ON DELETE CASCADE,
    minutes_played INT NOT NULL DEFAULT 0,
    goals INT NOT NULL DEFAULT 0,
    assists INT NOT NULL DEFAULT 0,
    yellow_cards INT NOT NULL DEFAULT 0,
    red_cards INT NOT NULL DEFAULT 0,
    clean_sheet BOOLEAN DEFAULT FALSE, -- Primarily for Goalkeepers/Defenders
    coach_rating DECIMAL(3,1) CHECK (coach_rating BETWEEN 1.0 AND 10.0),
    CONSTRAINT unique_player_fixture UNIQUE (fixture_id, player_profile_id)
);

-- 1. Financial Transactions Table
CREATE TABLE financial_transactions (
    id BIGSERIAL PRIMARY KEY,
    term_id INT REFERENCES terms(id) ON DELETE SET NULL,
    player_profile_id BIGINT REFERENCES player_profiles(id) ON DELETE SET NULL, -- Nullable for general academy expenses
    transaction_type VARCHAR(10) NOT NULL, -- 'INFLOW' or 'OUTFLOW'
    category VARCHAR(50) NOT NULL, -- 'TUITION_FEES', 'KIT_SALE', 'RENT', 'SALARY', etc.
    amount DECIMAL(12,2) NOT NULL CHECK (amount > 0),
    transaction_date DATE NOT NULL,
    payment_method VARCHAR(30) NOT NULL, -- 'CASH', 'BANK_TRANSFER', 'CARD'
    reference_number VARCHAR(100) UNIQUE,
    description TEXT,
    recorded_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- ====================================================================
-- 1. SEED SYSTEM ROLES
-- ====================================================================
INSERT INTO roles (id, name) VALUES
                                 (1, 'ADMIN'),
                                 (2, 'COACH'),
                                 (3, 'PLAYER'),
                                 (4, 'PARENT')
    ON CONFLICT (id) DO NOTHING;

-- ====================================================================
-- 2. SEED ACADEMY EVALUATION TERMS
-- ====================================================================
INSERT INTO terms (id, name, start_date, end_date, academic_year, is_active) VALUES
                                                                                 (1, 'Term 1 (Autumn)', '2026-09-01', '2026-12-15', '2026/2027', true),
                                                                                 (2, 'Term 2 (Spring)', '2027-01-05', '2027-04-02', '2026/2027', false),
                                                                                 (3, 'Term 3 (Summer Evaluation)', '2027-04-20', '2027-07-15', '2026/2027', false)
    ON CONFLICT (id) DO NOTHING;

-- ====================================================================
-- 3. SEED AGE CATEGORIES / SQUADS
-- ====================================================================
INSERT INTO age_groups (id, name, min_age, max_age) VALUES
                                                        (1, 'Under 11 (U11)', 9, 11),
                                                        (2, 'Under 14 (U14)', 12, 14),
                                                        (3, 'Under 17 (U17)', 15, 17)
    ON CONFLICT (id) DO NOTHING;


-- Map Players to their respective eligible Age Group Squads
INSERT INTO player_age_groups (player_id, age_group_id) VALUES
                                                            (1, 1), -- Striker assigned to U11
                                                            (2, 2)  -- Midfielder assigned to U14
    ON CONFLICT DO NOTHING;
