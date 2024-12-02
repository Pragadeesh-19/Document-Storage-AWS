# AWS S3 Document Storage Service

A Spring Boot application that provides a secure and scalable document storage solution using Amazon S3. This service allows users to upload, download, and search for documents in their personal storage space.

## Features

- **User-specific Storage**: Each user gets their own storage space
- **File Operations**:
  - Upload documents
  - Download documents
  - Search documents by filename
- **Secure Access**: AWS credentials management using environment variables
- **RESTful API**: Clean and well-structured API endpoints

## Technologies

- Java 17
- Spring Boot 3.x
- AWS SDK for Java v2
- JUnit 5
- Mockito
- Lombok

## Prerequisites

- Java 17 or higher
- Maven
- AWS Account with S3 access
- AWS S3 Bucket

## Configuration

### Set the following environment variables:

```Properties:
AWS.S3.BUCKET-NAME=your-bucket-name
AWS.S3.ACCESS-KEY=your-aws-access-key
AWS.S3.SECRET-KEY=your-aws-secret-key
```

### Application Properties

```Properties:
server.port=8080
aws.s3.region=ap-south-1
```

## API Endpoints:

### Search Files:

**Endpoints:** `GET /api/v1/storage/search`

**Parameters:**
- `username`: User's unique identifier
- `searchTerm`: Term to search files

**Example Request:**
```http
GET /api/v1/storage/search?username=johndoe&searchTerm=report
```

### Download Files:

**Endpoints:** `GET /api/v1/storage/download`

**Parameters:** 
- `username`: Users's unique identifier
- `fileName`: Name of the file to download

**Example Request:**
```http
GET /api/v1/storage/download?username=johndoe&fileName=monthly_report.pdf
```

### Upload File:

**Endpoints:** `POST /api/v1/storage/upload?username={username}`

**Content-type:** `multipart/form-data`

### Api Documentation

- Access the Swagger UI documentation at: http://localhost:8080/swagger-ui.html

## Building and Running

1. Clone the repository:
    ```
    https://github.com/pragadeesh-19/Document-Storage-AWS.git
    ```

2. Navigate To Project Directory:
    ```
    cd Document-Storage-AWS
    ```

3. Build the Project:
    ```
    mvn clean install
    ```

## Testing

- The project includes unit tests for the S3 service layer. Run tests using:

```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request
