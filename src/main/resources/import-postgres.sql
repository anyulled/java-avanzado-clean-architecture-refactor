-- Sample data for PetClinic application (PostgreSQL)

-- Insert sample pets
INSERT INTO pets (name, species, owner_name) VALUES
('Buddy', 'Dog', 'John Smith'),
('Whiskers', 'Cat', 'Jane Doe'),
('Simba', 'Dog', 'Olga'),
('Perchic', 'Cat', 'Arol'),
('Charlie', 'Rabbit', 'David Brown'),
('Bella', 'Hamster', 'Emma Davis'),
('Milo', 'Dog', 'Lili Benitez'),
('Daisy', 'Bird', 'Lisa Garcia'),
('Rocky', 'Dog', 'Carlos Martinez'),
('Mittens', 'Cat', 'Ana Rodriguez');

-- Insert sample appointments
INSERT INTO appointments (pet_id, date, reason) VALUES
(1, '2024-02-15 10:00:00', 'Annual Checkup'),
(2, '2024-02-16 14:30:00', 'Vaccination'),
(3, '2024-02-17 09:00:00', 'Dental Cleaning'),
(1, '2024-02-20 11:00:00', 'Follow-up Visit'),
(4, '2024-02-22 15:00:00', 'Spay Surgery Consultation'),
(5, '2024-02-23 16:30:00', 'Nail Trimming'),
(6, '2024-02-25 08:30:00', 'Behavioral Assessment'),
(7, '2024-02-26 13:00:00', 'Health Check'),
(8, '2024-02-27 10:15:00', 'Wing Clipping'),
(9, '2024-02-28 14:00:00', 'Training Consultation'),
(10, '2024-03-01 09:30:00', 'Grooming Session');