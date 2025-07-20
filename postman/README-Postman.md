# PetClinic API - Postman Collection

This directory contains a comprehensive Postman collection for testing the PetClinic REST API with detailed console logging similar to the Spring PetClinic reference implementation.

## 📋 Contents

- `PetClinic-API.postman_collection.json` - Main collection with all API endpoints and tests
- `PetClinic-Local.postman_environment.json` - Environment variables for local development
- `README-Postman.md` - This documentation file

## 🚀 Quick Start

### 1. Import Collection and Environment

1. Open Postman
2. Click **Import** button
3. Select and import both files:
   - `PetClinic-API.postman_collection.json`
   - `PetClinic-Local.postman_environment.json`

### 2. Set Environment

1. In Postman, click the environment dropdown (top right)
2. Select **"PetClinic Local"**
3. Verify the `baseUrl` is set to `http://localhost:8080`

### 3. Start the Application

Before running tests, make sure your PetClinic application is running:

```bash
# Start the Spring Boot application
mvn spring-boot:run

# Or if you have the JAR built
java -jar target/petclinic-simple-1.0.0.jar
```

The application should be accessible at `http://localhost:8080`

### 4. Run Tests

You can run requests individually or execute the entire collection:

#### Individual Requests
- Click on any request in the collection
- Click **Send**
- Check the **Console** tab for detailed test results

#### Run Entire Collection
1. Click on the collection name **"PetClinic API"**
2. Click **Run** button
3. Select all requests
4. Click **Run PetClinic API**
5. Watch the results and check console output

## 📊 Test Structure

### Pet Operations

#### 1. Create Pet (`POST /pets`)
- **Purpose**: Creates a new pet with random data
- **Tests**: Status 201, response structure, ID generation
- **Console Output**: Creation details and validation results

#### 2. Get All Pets (`GET /pets`)
- **Purpose**: Retrieves all pets in the system
- **Tests**: Status 200, array response, created pet verification
- **Console Output**: List of all pets with details

### Appointment Operations

#### 3. Create Appointment - Success (`POST /appointments`)
- **Purpose**: Creates appointment for existing pet
- **Tests**: Status 201, data validation, pet association
- **Console Output**: Appointment creation details

#### 4. Create Appointment - Invalid Pet (`POST /appointments`)
- **Purpose**: Tests error handling for non-existent pet
- **Tests**: Status 400, error message validation
- **Console Output**: Error handling verification

#### 5. Get All Appointments (`GET /appointments`)
- **Purpose**: Retrieves all appointments
- **Tests**: Status 200, structure validation
- **Console Output**: List of appointments with pet associations

## 🔧 Console Logging Features

The collection provides comprehensive console logging similar to Spring PetClinic:

### Request Logging
- Request method and URL
- Request timing information
- Pre-request data generation logs

### Response Logging
- Status codes with explanations
- Response times
- Full response body details
- Structured data presentation

### Test Results
- ✅ Success indicators for passed tests
- ❌ Clear failure messages
- 📋 Detailed data summaries
- 💾 Variable storage notifications

### Business Logic Logging
- Pet creation with generated data
- Appointment scheduling details
- Error handling demonstrations
- Data relationship validations

## 🎯 Test Scenarios Covered

### Happy Path
- Create pet → Get pets → Create appointment → Get appointments
- Data persistence verification
- Relationship validation (pet-appointment)

### Error Handling
- Invalid pet ID in appointment creation
- Proper error messages and status codes
- Business rule enforcement

### Performance
- Response time validation (<500ms)
- Concurrent request handling (in-memory storage)

## 📝 Sample Console Output

When running the complete collection, expect console output like:

```
🚀 Starting PetClinic API request to: http://localhost:8080/pets
📍 Method: POST
🐾 Creating pet with data: {name: "Buddy", species: "Dog", ownerName: "John Smith"}

=== CREATE PET TEST RESULTS ===
✅ Status: 201 Created
✅ Response time: 45ms
📋 Response body: {id: 1, name: "Buddy", species: "Dog", ownerName: "John Smith"}
✅ Response structure is valid
✅ Pet ID generated: 1
✅ Pet data matches request
💾 Stored pet ID: 1 for future requests
=== END CREATE PET TEST ===
```

## 🔄 Variables and Data Flow

The collection uses dynamic variables to create realistic test scenarios:

### Collection Variables
- `petId` - Generated from Create Pet, used in appointments
- `appointmentId` - Generated from Create Appointment
- Random pet names, species, and owners
- Random appointment reasons and future dates

### Environment Variables
- `baseUrl` - API base URL (http://localhost:8080)
- Connection details (protocol, host, port)

## 🎉 Advanced Features

### Random Data Generation
- Pet names from predefined list
- Various species and owners
- Future appointment dates
- Realistic appointment reasons

### Test Dependencies
- Appointments require existing pets
- Error scenarios test business rules
- Data verification across requests

### Comprehensive Validation
- HTTP status codes
- Response structure validation
- Business logic verification
- Performance testing

## 🔍 Troubleshooting

### Application Not Running
If tests fail with connection errors:
1. Verify application is running: `curl http://localhost:8080/pets`
2. Check application logs for startup issues
3. Ensure port 8080 is not in use by another service

### Test Failures
- Check console output for specific error details
- Verify test sequence (Create Pet before Create Appointment)
- Ensure environment is properly selected

### Performance Issues
- Monitor response times in console output
- Check application resource usage
- Verify in-memory storage isn't full (restart app to clear)

## 📚 Additional Resources

- [Postman Documentation](https://learning.postman.com/)
- [Spring Boot Testing Guide](https://spring.io/guides/gs/testing-web/)
- [REST API Testing Best Practices](https://blog.postman.com/rest-api-testing-best-practices/)

---

**Happy Testing! 🧪**