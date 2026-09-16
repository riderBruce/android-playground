-- Test seed data for the log_record table.
-- Run after MoveLogDB.onCreate() has created the table.

INSERT INTO log_record
    (type, date, duration_minute, distance_km, intensity, note)
VALUES
    ('Walking',   '2026-07-01', 15,  1.0,  'Low',    'Morning walk'),
    ('Running',   '2026-07-02', 30,  5.0,  'Medium', 'Easy run'),
    ('Biking',    '2026-07-03', 45, 10.0,  'High',   'Park cycling'),
    ('Swimming',  '2026-07-04', 30,  1.5,  'Medium', 'Pool workout'),
    ('StandUp',   '2026-07-05', 15,  0.0,  'Low',    'Standing break'),
    ('Walking',   '2026-07-06', 60,  3.0,  'Medium', 'Long walk'),
    ('Running',   '2026-07-07', 45,  8.0,  'High',   'Interval training'),
    ('Biking',    '2026-07-08', 60, 20.0,  'High',   'Long bike ride'),
    ('Swimming',  '2026-07-09', 45,  2.0,  'Medium', 'Endurance laps'),
    ('Walking',   '2026-07-10', 30,  2.5,  'Low',    'Evening walk'),
    ('Running',   '2026-07-11', 15,  2.0,  'High',   'Short sprint'),
    ('Biking',    '2026-07-12', 30,  7.0,  'Medium', 'City ride'),
    ('StandUp',   '2026-07-13', 15,  0.0,  'Low',    'Desk break'),
    ('Swimming',  '2026-07-14', 60,  3.0,  'High',   'Full swim session'),
    ('Walking',   '2026-07-15', 45,  3.5,  'Medium', 'Trail walk'),
    ('Running',   '2026-07-16', 60, 10.0,  'High',   'Long-distance run'),
    ('Biking',    '2026-07-17', 15,  3.0,  'Low',    'Quick ride'),
    ('Swimming',  '2026-07-18', 30,  1.0,  'Low',    'Recovery swim'),
    ('Walking',   '2026-07-19', 90,  6.0,  'Medium', 'Weekend hike'),
    ('Running',   '2026-07-20', 30,  5.0,  'Medium', 'Steady run');
