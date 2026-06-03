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

-- ====================================================================
-- 4. SEED USERS
-- Passwords are encrypted using BCrypt for password: "password123"
-- ====================================================================
INSERT INTO users (id, email, password_hash, first_name, last_name, role_id) VALUES
-- Administrator
(1, 'admin@academy.com', '$2a$10$rOmYvG06C383N6FmP9XG2unh6O6R8aY/v0PZ0Z.fFvGv3zF7qX7I2', 'Chief', 'Executive', 1),
-- Coach
(2, 'coach.david@academy.com', '$2a$10$rOmYvG06C383N6FmP9XG2unh6O6R8aY/v0PZ0Z.fFvGv3zF7qX7I2', 'David', 'Ancelotti', 2),
-- Parent
(3, 'parent.smith@mail.com', '$2a$10$rOmYvG06C383N6FmP9XG2unh6O6R8aY/v0PZ0Z.fFvGv3zF7qX7I2', 'Richard', 'Smith', 4)
ON CONFLICT (id) DO NOTHING;

-- ====================================================================
-- 5. SEED PLAYER PROFILES & ROSTER RELATIONSHIPS
-- ====================================================================
INSERT INTO player_profiles (id, user_id, parent_id, date_of_birth, position, jersey_number, dominant_foot) VALUES
(1, null, 3, '2013-05-14', 'Striker', 9, 'Right'),
(2, null, 3, '2010-11-22', 'Central Midfielder', 8, 'Ambidextrous')
ON CONFLICT (id) DO NOTHING;

-- Map Players to their respective eligible Age Group Squads
INSERT INTO player_age_groups (player_id, age_group_id) VALUES
(1, 1), -- Striker assigned to U11
(2, 2)  -- Midfielder assigned to U14
ON CONFLICT DO NOTHING;

-- ====================================================================
-- 6. SEED SAMPLE MATCHDAY FIXTURES (Term 1)
-- ====================================================================
INSERT INTO fixtures (id, opponent_name, match_date, venue, status, academy_score, opponent_score, term_id) VALUES
(1, 'Metropolitan Youth FC', '2026-09-18', 'Home', 'PLAYED', 4, 2, 1),
(2, 'Rover United Academy', '2026-10-05', 'Away', 'PLAYED', 1, 1, 1),
(3, 'City Rovers Junior Club', '2026-11-12', 'Home', 'SCHEDULED', 0, 0, 1)
ON CONFLICT (id) DO NOTHING;

-- ====================================================================
-- 7. SEED FINANCIAL TRANSACTIONS LEDGER (Term 1)
-- ====================================================================
INSERT INTO transactions (id, transaction_type, category, amount, transaction_date, payment_method, reference_number, description, term_id) VALUES
(1, 'INFLOW', 'TUITION_FEES', 1500.00, '2026-09-02', 'BANK_TRANSFER', 'REF-99812', 'Term 1 registration fee - Registration Batch A', 1),
(2, 'INFLOW', 'KIT_SALES', 350.00, '2026-09-05', 'CARD', 'REF-00192', 'Squad match kit package distributions', 1),
(3, 'OUTFLOW', 'FIELD_RENTAL', 600.00, '2026-09-10', 'BANK_TRANSFER', 'TXN-8821', 'September stadium facility block lease fee', 1),
(4, 'OUTFLOW', 'COACH_SALARY', 850.00, '2026-09-28', 'BANK_TRANSFER', 'TXN-8822', 'Monthly coaching staff performance allowance', 1)
ON CONFLICT (id) DO NOTHING;