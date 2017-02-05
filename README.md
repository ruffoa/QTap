# QTap
QTap

This is a build of QTAP for Android devices.  The initial release is planned to have a similar feature set to QTAP for iOS, with additional features planned for later release.

______________________________________________________________________________________________________________________


**NOTE** When developing the database:
-create a package with the name of the table (plural), within the database package
-in that package make a class defining the schema of the table, with the table name (singular) as its class name
-also in that package make a manager for the other class that holds the methods for working with the database
 
 e.g. in Houses: House, HouseManager
 -House holds Strings defining columns (street, province, number etc.), fields for each (e.g. string for street, int for number) and getters/setters/constructor
 -HouseManager holds getRow, insertRow, getTable etc.
 -each manager is essentially a copy-paste, but change variables to match the table it is managing
 
-for EACH table, a CREATE_SQL and DELETE_SQL string needs to be made in DBHelper
-for EACH table, add a execSQL for the above 2 strings in onCreate and onUpgrade
-for **ANY** change to the schema (add table, change column name...ANYTHING), increment DATABASE_VERSION in DBHelper, otherwise it will crash
-after you've incrememented and run the database, you can decrement it to avoid changes to version for simple test changes and the like
______________________________________________________________________________________________________________________
QTAP Team:

Alex Ruffo, 
Lachlan Devir, 
Michael Wang, 
Carson Cook

©2016
