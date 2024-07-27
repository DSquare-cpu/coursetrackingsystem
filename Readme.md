# Course Tracking System
### Overview
The Course Tracking System is designed to help the Head of the Department (HOD) of Computer Science at the University of Guyana to track the progress of courses during an academic semester. The system provides updates on course start dates, assignment and test dates, assignment and test statuses, final exam or project dates, and the status of final projects or exams and marksheets.

### Database Setup
1. **Create Database:** Create a PostgreSQL database named course_tracking.
2. **Run SQL Script:** Setup the database using the schema provide in db.sql file.
3. **Database Configuration:** The DatabaseConfig.java file contains the configuration for connecting to the PostgreSQL database. Update the `URL`, `USER` and `PASSWORD` based on your database.

### Running the Application
1. **Clone the Repository:** Clone the project repository.
    ```shell
    git clone https://github.com/DSquare-cpu/coursetrackingsystem.git
    cd coursetrackingsystem
   ```
2. **Build the Project:** Use Maven to build the project.
    ```shell
   ./mvnw clean install
   ```
3. **Run the Application:** Run the main application class `App.java`.

### Additional Notes
1. **JavaFX**: Ensure you have JavaFX set up in your development environment to run the JavaFX front-end.
2. **Dependencies**: The `pom.xml` file includes all necessary dependencies. Make sure they are correctly configured in Maven repository.

---
