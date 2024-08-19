# RNA Technical Test

## Overview

This repository contains 2 Spring Boot applications with a MySQL database. Docker Compose is used to manage the deployment and configuration of the applications and its dependencies.

## Prerequisites

Ensure you have the following installed before starting the containers:

- **Docker**: [Install Docker](https://docs.docker.com/get-docker/)
- **Docker Compose**: [Install Docker Compose](https://docs.docker.com/compose/install/)
- **Git** (optional): [Install Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
- **Postman**: [Install Postman](https://www.postman.com/downloads/)
## Getting Started

### 1. Clone the Repository

First, clone the repository to your local machine:
```bash
git clone https://github.com/raziel2057/rna-technical-test.git
cd rna-technical-test
```
### 2. Configuration
The repository contains a docker-compose.yml file that defines the services and configurations for the application and MySQL database.

### 3. Build Docker Images
Build the Docker images using Docker Compose:
```bash
docker-compose build
```
This step is typically optional as Docker Compose will build images automatically when starting the containers. However, you may use it if you want to ensure images are built or if you have made changes to the Dockerfiles.

### 4. Start the Containers
Start the application and database containers:
```bash
docker-compose up
```
To run the containers in the background (detached mode), use:
```bash
docker-compose up -d
```
### 5. Verify Everything is Running
Check the status of the containers:
```bash
docker-compose ps
```
You should see a list of containers with their current status.

### 6. Access the Application
By default, 
- The Spring Boot application for clients is accessible at http://localhost:6869. You can modify the port in the **.env** file in the variable **SPRING_LOCAL_PORT_CLI** if necessary.
- The Spring Boot application for accounts and movements is accessible at http://localhost:6868. You can modify the port in the **.env** file in the **variable SPRING_LOCAL_PORT_ACC** if necessary.
- To access the MySQL database, connect to localhost:3307 using a MySQL client. You can modify the port in the **.env** file in the variable **MYSQLDB_LOCAL_PORT** if necessary. The default credentials also are in .env file in the variables **MYSQLDB_USER** and **MYSQLDB_ROOT_PASSWORD**.

### 7. Validate endpoints with Postman
To validate the endpints use the **test-rna-ms.postman_collection.json** file. This file was created with Postman 11.8.1.

### 8. Stopping the Containers
To stop and remove the containers, use:
```bash
docker-compose down
```
To also remove the associated volumes, use:
```bash
docker-compose down -v
```
### 9. Viewing Logs
To view logs for a specific service, use:
```bash
docker-compose logs [service_name]
```
Replace [service_name] with mysqldb or app_cli or app_acc depending on which service you want to view logs for.

## Troubleshooting
If you encounter issues:

* Ensure Docker and Docker Compose are up-to-date.
* Check the configuration in the docker-compose.yml file for any misconfigurations.
* Review the logs using docker-compose logs for error messages.

## Contributing
To contribute to this project:

* Fork the repository and create a new branch.
* Make your changes and test them thoroughly.
* Submit a pull request with a description of your changes.
