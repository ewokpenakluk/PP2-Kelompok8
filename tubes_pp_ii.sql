-- 1. Tabel User_Manajemen
CREATE TABLE User_Manajemen (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nama_lengkap VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    last_login DATETIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Tabel Kategori (sebelumnya Jenis)
CREATE TABLE Kategori (
    id_kategori INT AUTO_INCREMENT PRIMARY KEY,
    nama_kategori VARCHAR(100) NOT NULL
);

-- 3. Tabel Sampah
CREATE TABLE Sampah (
    id_sampah INT AUTO_INCREMENT PRIMARY KEY,
    id_kategori INT NOT NULL,
    nama_sampah VARCHAR(100) NOT NULL,
    berat FLOAT NOT NULL,
    poin INT NOT NULL,
    FOREIGN KEY (id_kategori) REFERENCES Kategori(id_kategori)
);

-- 4. Tabel Lokasi
CREATE TABLE Lokasi (
    id_lokasi INT AUTO_INCREMENT PRIMARY KEY,
    alamat_lokasi TEXT NOT NULL
);

-- 5. Tabel DropBox
CREATE TABLE DropBox (
    id_dropbox INT AUTO_INCREMENT PRIMARY KEY,
    nama_dropbox VARCHAR(100) NOT NULL,
    alamat TEXT NOT NULL
);

-- 6. Tabel Kurir
CREATE TABLE Kurir (
    id_kurir INT AUTO_INCREMENT PRIMARY KEY,
    nama_kurir VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    no_telepon VARCHAR(20) NOT NULL
);

-- 7. Tabel Masyarakat
CREATE TABLE Masyarakat (
    id_masyarakat INT AUTO_INCREMENT PRIMARY KEY,
    nama_lengkap VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    no_telepon VARCHAR(20) NOT NULL,
    alamat TEXT NOT NULL
);

-- 8. Tabel HistoryPenjemputan
CREATE TABLE HistoryPenjemputan (
    id_history INT AUTO_INCREMENT PRIMARY KEY,
    id_masyarakat INT NOT NULL,
    id_kurir INT NOT NULL,
    id_sampah INT NOT NULL,
    id_lokasi INT NOT NULL,
    id_dropbox INT,
    tanggal_penjemputan DATE NOT NULL,
    total_berat FLOAT NOT NULL,
    total_poin INT NOT NULL,
    FOREIGN KEY (id_masyarakat) REFERENCES Masyarakat(id_masyarakat),
    FOREIGN KEY (id_kurir) REFERENCES Kurir(id_kurir),
    FOREIGN KEY (id_sampah) REFERENCES Sampah(id_sampah),
    FOREIGN KEY (id_lokasi) REFERENCES Lokasi(id_lokasi),
    FOREIGN KEY (id_dropbox) REFERENCES DropBox(id_dropbox)
);

-- DATA DUMMY

-- Insert data ke Kategori
INSERT INTO Kategori (nama_kategori) VALUES
('Perangkat Komputer'),
('Perangkat Mobile'),
('Perangkat Cetak'),
('Aksesori Elektronik'),
('Perangkat Audio/Video'),
('Perangkat Jaringan'),
('Perangkat Penyimpanan'),
('Peralatan Rumah Tangga'),
('Perangkat Gaming'),
('Komponen Elektronik'),
('Perangkat Fotografi'),
('Peralatan Kantor'),
('Baterai & Power Supply'),
('Kabel & Konnektor'),
('Perangkat Keamanan');

-- Insert data ke Sampah
INSERT INTO Sampah (id_kategori, nama_sampah, berat, poin) VALUES
-- Perangkat Komputer
(1, 'Laptop Bekas', 2.5, 50),
(1, 'Monitor LCD', 4.0, 40),
(1, 'CPU Desktop', 5.0, 45),
-- Perangkat Mobile
(2, 'Smartphone Rusak', 0.2, 25),
(2, 'Tablet Bekas', 0.5, 30),
(2, 'Smartwatch', 0.1, 20),
-- Perangkat Cetak
(3, 'Printer Inkjet', 5.0, 35),
(3, 'Scanner', 3.0, 30),
(3, 'Printer Laser', 7.0, 40),
-- Aksesori Elektronik
(4, 'Keyboard Rusak', 0.8, 15),
(4, 'Mouse', 0.2, 10),
(4, 'Webcam', 0.3, 15),
-- Perangkat Audio/Video
(5, 'Speaker Bluetooth', 0.5, 20),
(5, 'Headphone', 0.3, 15),
(5, 'Microphone', 0.2, 15);

-- Insert data ke Lokasi
INSERT INTO Lokasi (alamat_lokasi) VALUES
('Jl. Pasir Kaliki No. 15, Pasir Kaliki, Bandung'),
('Jl. Dipatiukur No. 35, Coblong, Bandung'),
('Jl. Dago No. 84, Dago, Bandung'),
('Jl. Riau No. 50, Citarum, Bandung'),
('Jl. Merdeka No. 27, Sumur Bandung, Bandung'),
('Jl. Asia Afrika No. 65, Braga, Bandung'),
('Jl. Gatot Subroto No. 42, Lengkong, Bandung'),
('Jl. Buah Batu No. 155, Buah Batu, Bandung'),
('Jl. Setiabudi No. 73, Setiabudi, Bandung'),
('Jl. Sukajadi No. 131, Sukajadi, Bandung'),
('Jl. Lengkong No. 45, Lengkong, Bandung'),
('Jl. Cihampelas No. 160, Cihampelas, Bandung'),
('Jl. Pasirkaliki No. 25, Pasir Kaliki, Bandung'),
('Jl. Terusan Jakarta No. 77, Antapani, Bandung'),
('Jl. Supratman No. 91, Cibeunying, Bandung');

-- Insert data ke DropBox
INSERT INTO DropBox (nama_dropbox, alamat) VALUES
('E-Waste Center Pasir Kaliki', 'Jl. Pasir Kaliki No. 20, Pasir Kaliki, Bandung'),
('E-Waste Center Dago', 'Jl. Dago No. 100, Dago, Bandung'),
('E-Waste Center Riau', 'Jl. Riau No. 55, Citarum, Bandung'),
('E-Waste Center Merdeka', 'Jl. Merdeka No. 30, Sumur Bandung, Bandung'),
('E-Waste Center Asia Afrika', 'Jl. Asia Afrika No. 70, Braga, Bandung'),
('E-Waste Center Gatot Subroto', 'Jl. Gatot Subroto No. 45, Lengkong, Bandung'),
('E-Waste Center Buah Batu', 'Jl. Buah Batu No. 160, Buah Batu, Bandung'),
('E-Waste Center Setiabudi', 'Jl. Setiabudi No. 75, Setiabudi, Bandung'),
('E-Waste Center Sukajadi', 'Jl. Sukajadi No. 135, Sukajadi, Bandung'),
('E-Waste Center Lengkong', 'Jl. Lengkong No. 50, Lengkong, Bandung'),
('E-Waste Center Cihampelas', 'Jl. Cihampelas No. 165, Cihampelas, Bandung'),
('E-Waste Center Pasirkaliki', 'Jl. Pasirkaliki No. 30, Pasir Kaliki, Bandung'),
('E-Waste Center Jakarta', 'Jl. Terusan Jakarta No. 80, Antapani, Bandung'),
('E-Waste Center Supratman', 'Jl. Supratman No. 95, Cibeunying, Bandung'),
('E-Waste Center Dipatiukur', 'Jl. Dipatiukur No. 40, Coblong, Bandung');

-- Insert data ke Kurir
INSERT INTO Kurir (nama_kurir, email, no_telepon) VALUES
('Asep Hidayat', 'asep.hidayat@gmail.com', '081234567801'),
('Budi Santoso', 'budi.santoso@gmail.com', '081234567802'),
('Cecep Supriyadi', 'cecep.supriyadi@gmail.com', '081234567803'),
('Dedi Kurniawan', 'dedi.kurniawan@gmail.com', '081234567804'),
('Eko Prasetyo', 'eko.prasetyo@gmail.com', '081234567805'),
('Fajar Ramadhan', 'fajar.ramadhan@gmail.com', '081234567806'),
('Gunawan Wibowo', 'gunawan.wibowo@gmail.com', '081234567807'),
('Hendi Setiawan', 'hendi.setiawan@gmail.com', '081234567808'),
('Irfan Maulana', 'irfan.maulana@gmail.com', '081234567809'),
('Joko Susilo', 'joko.susilo@gmail.com', '081234567810'),
('Kurniawan Putra', 'kurniawan.putra@gmail.com', '081234567811'),
('Lukman Hakim', 'lukman.hakim@gmail.com', '081234567812'),
('Muhammad Rizki', 'muhammad.rizki@gmail.com', '081234567813'),
('Nanda Pratama', 'nanda.pratama@gmail.com', '081234567814'),
('Oscar Saputra', 'oscar.saputra@gmail.com', '081234567815');

-- Insert data ke Masyarakat
INSERT INTO Masyarakat (nama_lengkap, email, no_telepon, alamat) VALUES
('Putri Wulandari', 'putri.wulandari@gmail.com', '081234567901', 'Jl. Pasir Kaliki No. 15, Bandung'),
('Ratna Sari', 'ratna.sari@gmail.com', '081234567902', 'Jl. Dipatiukur No. 35, Bandung'),
('Siti Nurhaliza', 'siti.nurhaliza@gmail.com', '081234567903', 'Jl. Dago No. 84, Bandung'),
('Tono Sudrajat', 'tono.sudrajat@gmail.com', '081234567904', 'Jl. Riau No. 50, Bandung'),
('Udin Permana', 'udin.permana@gmail.com', '081234567905', 'Jl. Merdeka No. 27, Bandung'),
('Vina Panduwinata', 'vina.panduwinata@gmail.com', '081234567906', 'Jl. Asia Afrika No. 65, Bandung'),
('Wawan Setiawan', 'wawan.setiawan@gmail.com', '081234567907', 'Jl. Gatot Subroto No. 42, Bandung'),
('Xaverius Wijaya', 'xaverius.wijaya@gmail.com', '081234567908', 'Jl. Buah Batu No. 155, Bandung'),
('Yanto Sudirman', 'yanto.sudirman@gmail.com', '081234567909', 'Jl. Setiabudi No. 73, Bandung'),
('Zainal Abidin', 'zainal.abidin@gmail.com', '081234567910', 'Jl. Sukajadi No. 131, Bandung'),
('Ani Suryani', 'ani.suryani@gmail.com', '081234567911', 'Jl. Lengkong No. 45, Bandung'),
('Bambang Hermawan', 'bambang.hermawan@gmail.com', '081234567912', 'Jl. Cihampelas No. 160, Bandung'),
('Citra Dewi', 'citra.dewi@gmail.com', '081234567913', 'Jl. Pasirkaliki No. 25, Bandung'),
('Dian Sastro', 'dian.sastro@gmail.com', '081234567914', 'Jl. Terusan Jakarta No. 77, Bandung'),
('Edi Sulistyo', 'edi.sulistyo@gmail.com', '081234567915', 'Jl. Supratman No. 91, Bandung');

-- Insert data ke HistoryPenjemputan
INSERT INTO HistoryPenjemputan (id_masyarakat, id_kurir, id_sampah, id_lokasi, id_dropbox, tanggal_penjemputan, total_berat, total_poin) VALUES
(1, 1, 1, 1, 1, '2024-01-15', 2.5, 50),  -- Laptop Bekas
(2, 2, 2, 2, 2, '2024-01-16', 4.0, 40),  -- Monitor LCD
(3, 3, 3, 3, 3, '2024-01-17', 5.0, 45),  -- CPU Desktop
(4, 4, 4, 4, 4, '2024-01-18', 0.2, 25),  -- Smartphone Rusak
(5, 5, 5, 5, 5, '2024-01-19', 0.5, 30),  -- Tablet Bekas
(6, 6, 6, 6, 6, '2024-01-20', 0.1, 20),  -- Smartwatch
(7, 7, 7, 7, 7, '2024-01-21', 5.0, 35),  -- Printer Inkjet
(8, 8, 8, 8, 8, '2024-01-22', 3.0, 30),  -- Scanner
(9, 9, 9, 9, 9, '2024-01-23', 7.0, 40),  -- Printer Laser
(10, 10, 10, 10, 10, '2024-01-24', 0.8, 15), -- Keyboard Rusak
(11, 11, 11, 11, 11, '2024-01-25', 0.2, 10), 
(12, 12, 12, 12, 12, '2024-01-26', 0.3, 15), 
(13, 13, 13, 13, 13, '2024-01-27', 0.5, 20), 
(14, 14, 14, 14, 14, '2024-01-28', 0.3, 15), 
(15, 15, 15, 15, 15, '2024-01-29', 0.2, 15); 

