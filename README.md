School registration system

Design and implement simple school registration system
- Assuming you already have a list of students
- Assuming you already have a list of courses
- A student can register to multiple courses
- A course can have multiple students enrolled in it.
- A course has 50 students maximum
- A student can register to 5 course maximum

Provide the following REST API:
- Create students CRUD
- Create courses CRUD
- Create API for students to register to courses
- Create abilities for user to view all relationships between students and courses
+ Filter all students with a specific course
+ Filter all courses for a specific student
+ Filter all courses without any students // one endpoint
+ Filter all students without any courses
-----------------------------------------------------------------------

2. Wrap everything in docker-compose and update README.md with following details:
   • Endpoints and payloads
   • How to setup project

3. Technology stack:
   • Java/Groovy
   • Gradle/Maven
   • Spring Boot
   • Docker (docker-compose + dockerFile)
   • JUnit
   • MySQL
   • Other technologies or frameworks which can help you.

4. Provide unit tests and integrations test
5. Project can be stored under any version control system like GitHub, GitLab etc.