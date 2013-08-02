jee6-webapp-artifact
====================

Simple JEE6 webapp maven artifact

Mysql DataSource on Jboss AS7
====================
The connector can be downloaded from: http://dev.mysql.com/downloads/connector/j/. The current version at the time of writing is 5.1.17, further sections of this guide assume that version number.

Unzip the connector to your downloads folder and save the location of the main JAR in a variable which we can refer to later:

cd ~/Downloads
unzip mysql-connector-java-5.1.17.zip
export MYSQL_JAR=~/Downloads/mysql-connector-5.1.17/mysql-connector-java-5.1.17-bin.jar
Add a Module to AS 7

AS 7 uses a module system to provide isolation in class loading. We need to create a new module which contains the MySQL Connector J JAR. Move to the the AS installation directory and create the folder structure for the new module:

export JBOSS_HOME=~/Development/jboss-as-web-7.0.0.Final
cd $JBOSS_HOME
mkdir -p modules/com/mysql/main
Copy the driver jar to the new directory and move to that directory:

cp $MYSQL_JAR $JBOSS_HOME/modules/com/mysql/main
cd $JBOSS_HOME/modules/com/mysql/main
Define the module in XML. This is the key part of the process:

vi module.xml
If the version of the jar has changed, remember to update it here:

<?xml version="1.0" encoding="UTF-8"?>

<module xmlns="urn:jboss:module:1.0" name="com.mysql">
  <resources>
    <resource-root path="mysql-connector-java-5.1.17-bin.jar"/>
  </resources>
  <dependencies>
    <module name="javax.api"/>
  </dependencies>
</module>
The new module directory should now have the following contents:

module.xml
mysql-connector-java-5.1.17-bin.jar
Create a Driver Reference

Now the module has been created, we need to make a reference to it from the main application server configuration file:

cd $JBOSS_HOME/standalone/configuration
vi standalone.xml
Find the ‘drivers’ element and add a new driver to it:

<drivers>
    <driver name="mysql" module="com.mysql"/>
    <driver name="h2" module="com.h2database.h2">
        <xa-datasource-class>
            org.h2.jdbcx.JdbcDataSource
        </xa-datasource-class>
    </driver>
</drivers>
The ‘h2? driver is part of the default JBoss configuration. The new driver that needs adding is named ‘mysql’.

Add the Datasource

Go into the configuration directory and open the main configuration file:

cd $JBOSS_HOME/standalone/configuration
vi standalone.xml
Find the datasources element and add a new datasource inside:

<datasource
        jndi-name="java:/mydb" pool-name="my_pool"
        enabled="true" jta="true"
        use-java-context="true" use-ccm="true">
    <connection-url>
        jdbc:mysql://localhost:3306/mydb
    </connection-url>
    <driver>
        mysql
    </driver>
    <security>
        <user-name>
            root
        </user-name>
        <password>

        </password>
    </security>
    <statement>
        <prepared-statement-cache-size>
            100
        </prepared-statement-cache-size>
        <share-prepared-statements/>
    </statement>
</datasource>
