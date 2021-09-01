# c3p0_close_poc
Demonstration for not closing c3p0 connections after junit tests

## Versions affected
Up to c3p0 Version 0.9.0.4 close() worked as expected.  
Since c3p0 Version 0.9.1 connections do not get closed.

## Techstack
This uses Java8, Springframework 2.5.6 and Hibernate 3.  
Yes, this is out-dated, but its only to demonstrate the issue with the close().

## Database

The example expects a MariaDB at localhost:3306 with the following settings:  
Database: exampledb  
Username: exampleuser  
Password: examplepassword  

This can be modified in the file `src/main/resources/applicationContext.xml`

Database and user can be created with the following SQL statements:

    CREATE DATABASE `exampledb`
    CREATE USER IF NOT EXISTS 'exampleuser'@'localhost' IDENTIFIED BY 'examplepassword';
    GRANT ALL ON `exampledb`.* TO 'exampleuser'@'localhost';

## Usage
Run the JUnit Test `ExampleTest` via `mvn test` or in the IDE of your choice.  
View the MariaDB Logfile, it shows something like:

    Aborted connection 70 to db: 'exampledb' user: 'exampleuser' host: 'localhost' (Got an error reading communication packets)
    Aborted connection 71 to db: 'exampledb' user: 'exampleuser' host: 'localhost' (Got an error reading communication packets)
    Aborted connection 72 to db: 'exampledb' user: 'exampleuser' host: 'localhost' (Got an error reading communication packets)

## Reason
BasicResourcePool spawns a Thread for cleanup:

    new Thread("Resource Destroyer in BasicResourcePool.close()")

In `private void destroyResource(final Object resc, boolean synchronous, final boolean checked_out)` a `class DestroyResourceTask implements Runnable` shall be instantiated and executed, but this never happens. And so, the connections get not closed cleanly, leading to the MariaDB log.

