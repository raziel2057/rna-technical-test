use rna_test_db;

-- Crear la tabla Persona solo si no existe
CREATE TABLE IF NOT EXISTS persona (
    identificacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    genero VARCHAR(50),
    edad INT NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(50)
);

-- Crear la tabla Cliente solo si no existe
CREATE TABLE IF NOT EXISTS cliente (
    cliente_id BIGINT PRIMARY KEY,
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL,
	identificacion BIGINT NOT NULL,
    CONSTRAINT FK_Cliente_Persona FOREIGN KEY (identificacion) REFERENCES persona(identificacion) ON DELETE CASCADE
);

-- Crear la tabla Cuenta solo si no existe
CREATE TABLE IF NOT EXISTS cuenta (
    numero_cuenta BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_cuenta VARCHAR(50) NOT NULL,
    saldo_inicial DECIMAL(15, 2) NOT NULL,
    estado BOOLEAN NOT NULL,
    nombre_persona VARCHAR(255) NOT NULL,
    CONSTRAINT FK_Cuenta_Persona FOREIGN KEY (nombre_persona) REFERENCES persona(nombre) ON DELETE CASCADE
);

-- Crear la tabla Movimiento solo si no existe
CREATE TABLE IF NOT EXISTS movimiento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    tipo_movimiento VARCHAR(50) NOT NULL,
    valor DECIMAL(15, 2) NOT NULL,
    saldo DECIMAL(15, 2) NOT NULL,
    numero_cuenta BIGINT,
    CONSTRAINT FK_Movimiento_Cuenta FOREIGN KEY (numero_cuenta) REFERENCES cuenta(numero_cuenta) ON DELETE CASCADE
);
