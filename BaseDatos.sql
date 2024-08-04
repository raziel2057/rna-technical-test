use rna_test_db;

-- Crear la tabla Persona solo si no existe
CREATE TABLE IF NOT EXISTS Persona (
    identificacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    genero VARCHAR(50),
    edad INT NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(50)
);

-- Crear la tabla Cliente solo si no existe
CREATE TABLE IF NOT EXISTS Cliente (
    clienteId BIGINT PRIMARY KEY,
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL,
    CONSTRAINT FK_Cliente_Persona FOREIGN KEY (clienteId) REFERENCES Persona(identificacion) ON DELETE CASCADE
);

-- Crear la tabla Cuenta solo si no existe
CREATE TABLE IF NOT EXISTS Cuenta (
    numeroCuenta BIGINT PRIMARY KEY,
    tipoCuenta VARCHAR(50) NOT NULL,
    saldoInicial DECIMAL(15, 2) NOT NULL,
    estado BOOLEAN NOT NULL,
    nombrePersona VARCHAR(255) NOT NULL,
    CONSTRAINT FK_Cuenta_Persona FOREIGN KEY (nombrePersona) REFERENCES Persona(nombre) ON DELETE CASCADE
);

-- Crear la tabla Movimiento solo si no existe
CREATE TABLE IF NOT EXISTS Movimiento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    tipoMovimiento VARCHAR(50) NOT NULL,
    valor DECIMAL(15, 2) NOT NULL,
    saldo DECIMAL(15, 2) NOT NULL,
    cuentaNumero BIGINT,
    CONSTRAINT FK_Movimiento_Cuenta FOREIGN KEY (cuentaNumero) REFERENCES Cuenta(numeroCuenta) ON DELETE CASCADE
);
