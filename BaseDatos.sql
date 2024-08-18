use rna_test_db;

CREATE TABLE IF NOT EXISTS person (
    identification BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL UNIQUE,
    gender VARCHAR(32),
    age INT NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS client (
    client_id BIGINT PRIMARY KEY,
    password VARCHAR(64) NOT NULL,
    state BOOLEAN NOT NULL,
	identification BIGINT NOT NULL,
    CONSTRAINT FK_Client_Person FOREIGN KEY (identification) REFERENCES person(identification) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS account (
    account_number BIGINT PRIMARY KEY,
    account_type VARCHAR(32) NOT NULL,
    initial_balance DECIMAL(15, 2) NOT NULL,
    state BOOLEAN NOT NULL,
    identification BIGINT NOT NULL,
    CONSTRAINT FK_Account_Person FOREIGN KEY (identification) REFERENCES person(identification) ON DELETE CASCADE
);

-- Crear la tabla Movimiento solo si no existe
CREATE TABLE IF NOT EXISTS movement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movement_date DATE NOT NULL,
    movement_type VARCHAR(32) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL,
	state VARCHAR(32) NOT NULL,
    account_number BIGINT,
    CONSTRAINT FK_Movement_Account FOREIGN KEY (account_number) REFERENCES account(account_number) ON DELETE CASCADE
);
