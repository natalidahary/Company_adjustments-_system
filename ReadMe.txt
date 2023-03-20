Employee Shift Management System

The code for this project is built using a Model-View-Controller (MVC) architecture. The model contains the business logic and data for the system, including employee and shift information. The controller handles user input and communicates with the model and GUI

The system allows employees to log in with a username and password to access their account. Once logged in, employees can fill their shifts for the given month and view their monthly salary. Additionally, they can change their password within the system.

The shift manager user has access to the system with their own username and password. They can add and remove employees from the system, update employee salaries, and change passwords. Any changes made are synchronized between the Java program and the MySQL database.

The database stores all user information for the employees in the shift management system. Information is presented in an orderly and organized manner, making it efficient for both employees and the shift manager to synchronize all information.

The system is designed for employees to submit their shifts in an orderly manner and view their shifts and personal salary. The shift manager has access to view the shifts and salaries of all employees in the system.

Each user has a user object within the system. The person department contains features such as name, last name, and ID, which are inherited by the employee department. Users can be searched for within the system by their ID or username.