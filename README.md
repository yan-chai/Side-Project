Side Project
============

### Due - Thursday, December 16, 2021 - 5pm
  
This project is a mobile app that cultivate good habit, user can set
and complete goals to win points

## About This Project

* In this project, we will build an application use a spring boot server.
* Function:  
  - Manage user account: user information contains email, name, and password, you can register new account or login in with an account.
  You can change your personal information after login.  
  - Manage your Habit List: add, delete, edit you habit list. Set the task repetition cycle and get everyday task from your list.  
  - Manage Today Task: see all today's task in home page, mark it as done once you have finished it.  
  - Share your good habits: if you make a Habit as "public", this habit can be seen by other users.  
  - Search for Habit: See other's habits and if you also want them, you can add to you only list.  
  
## Technology stack

* docker/mysql:
  - used docker to run database as a container, easy to deploy and run  
  - used mysql as the database.  
  
* SpringBoot:
  - Built application use springboot.
  - Used built-in timer to execute sql script repeatedly.
  
* Mybatis:
  - a lightweight framework that support custom sql.
  - Design Mapper and XML script to execute sql through a method.  
  
* Shiro:
  - A java security framework that performs authentication, authorization, cryptography, and session management.
  - Set privilege and role for user.
  
* Thymeleaf:
  - A java template engine that render tags to variables sent by server
  
     
     

