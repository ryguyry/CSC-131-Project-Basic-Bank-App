To connect to a MySQL database a .jar file is required from: https://dev.mysql.com/downloads/connector/j/
Select Platform Independent -> download .zip
Open the .zip and extract the folder or just the .jar file
Open Eclipse and select File -> Properties -> Java Build Path -> Libraries -> Classpath -> Add External JARS.. find the .jar file and your done!
Apply the settings and our program will connect to the database as long as it's running from IP 127.0.0.1 and port 3306 (default)
if you have a different setup please edit the bbaDatabase.java file to the correct login credentials 