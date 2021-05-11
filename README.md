Serenity Day 1
Serenity BDD is an open source library that aims to make the idea of living documentation a reality.

Here is the link for simple intro

Steps to create a project

1.Create a maven project called B21SerenityProject
2.under pom.xml
  add below into property section <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
3.Add dependencies
<dependencies>
<!--        This is for base support for anything we do with serenity-->
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-core</artifactId>
            <version>2.4.4</version>
        </dependency>
<!--        this is the dependency that wrap up rest assured with additional serenity support-->
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-rest-assured</artifactId>
            <version>2.4.4</version>
        </dependency>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.7.1</version>
            <scope>test</scope>
        </dependency>
        <!--Junit 5 support for serenity -->
        <dependency>
            <groupId>io.github.fabianlinz</groupId>
            <artifactId>serenity-junit5</artifactId>
            <version>1.6.0</version>
        </dependency>

    </dependencies>
4.Add Build plugins
<build>
<plugins>
<plugin>
<groupId>org.apache.maven.plugins</groupId>
<artifactId>maven-compiler-plugin</artifactId>
<version>3.8.1</version>
<configuration>
<source>8</source>
<target>8</target>
</configuration>
</plugin>
<!--            This is where the report is being generated after the test run -->
<plugin>
<groupId>net.serenity-bdd.maven.plugins</groupId>
<artifactId>serenity-maven-plugin</artifactId>
<version>2.4.4</version>
<executions>
<execution>
<id>serenity-reports</id>
<phase>post-integration-test</phase>
<goals>
<goal>aggregate</goal>
</goals>
</execution>
</executions>
</plugin>
<!--         We want to run all the tests then generate one report -->
<plugin>
<groupId>org.apache.maven.plugins</groupId>
<artifactId>maven-surefire-plugin</artifactId>
<version>3.0.0-M5</version>
<configuration>
<testFailureIgnore>true</testFailureIgnore>
</configuration>
</plugin>
</plugins>
</build>
5.Create a package called b21 under src/test/java

   a.under this package create a package called github
   b.Create a class called GitHubUserTest
6.Write A simple Get single user request test with RestAssured you already know. This is how it looks like

7.This is just a regular test , in order to make it recognized by serenity report

    a.Add annotation add class level : @SerenityTest
    b.It's coming from this import import net.serenitybdd.junit5.SerenityTest;
8.Add a properties file with exact name serenity.properties right under project level

Add below two line to specify report name and test root folder
serenity.project.name=B21 Awesome Report
serenity.test.root=b21
9.In order to generate serenity report, we need use maven goal

  if you are using command line : mvn clean verify
  if you are using intelliJ buttons - first click on clean then click on verify
  Your Report will be generated under target folder as HTML Report
10.We were able to generate test report , However there are no details about the request and response. In order to see the details then we need to use the given() when() then() methods coming from Serenity.
Here is how :
  Instead of importing rest assured given import below import static net.serenitybdd.rest.SerenityRest.*;
  From this point on , all details will be picked up serenity report
The way that assert the Response and show it as a steps in Serenity report is by using Ensure class (from import net.serenitybdd.rest.Ensure)

Ensure.that("description goes here", validatableResponse-> validatableResponse.allThenAssertions Can go here

Everytime we use one Ensure.that method it will become one step in the report

Ensure.that("request ran successfully",   thenSection -> thenSection.statusCode(200)  ) ;
Ensure.that("login field value is CybertekSchool",
thenSection -> thenSection.body("login",  is("CybertekSchool") )   ) ;
Ensure.that("name field value is 'Cybertek School'",
vRes -> vRes.body("name", is("Cybertek School")) ;



      Serenity Day 2


We did Parameterized Test using Junit 5 @ParameterizedTest using Value Source and CSVSource.

Here is the full example

Serenity Properties
There are two special file that used by serenity for properties.

Both of them can be accessible using the ConfigReader Class by providing property name.

serenity.properties
This file can either be under

  root directory
  src/test/resources
It's a key-value pair like regular properties file only difference is , all key value pairs will be added as system env variable at run time


      serenity.conf

The structure looks as below , we can store environment specific data and run the project against different environment by selecting environment in maven command

environments {
default {
base.url = "http://library1.cybertekschool.com"
}
library1 {
base.url = "http://library1.cybertekschool.com"
}
library2 {
base.url = "http://library2.cybertekschool.com"
}
library3 {
base.url = "http://library3.cybertekschool.com"
}
all{
base.path = "/rest/v1"
}
}
It starts with environments :

default : for default values if no envronment specified
custom environment name like library1 library2
key value pair under that environment
common value for all environment goes to all section
Selecting Environment can be achieved as below using maven command

mvn clean verify -Denvironment=YourEnvironmentNameGoesHere
Actual code we wrote to access these key value pair is using getProperty method of ConfigReader class

        System.out.println( ConfigReader.getProperty("base.url")  );
        System.out.println( ConfigReader.getProperty("librarian.username")  );
Above code will generate different result according to the environment you selected.

This will enable running same set of tests against different environment.