scratch-jee-webapp
==============

A very simple webapp that can be used to quickly try out code within a JEE web container.

The webapp can be run with the following command:

    mvn clean package tomee:run

This will start the server which can be accessed at [http://localhost:8080/scratch-jee-webapp/scratch/](http://localhost:8080/scratch-jee-webapp/scratch/ "scratch-jee-webapp")

It is also possible to carry out CRUD operation on simple users:

###### Create
    $ curl -XPOST -H "Content-Type:text/html" http://localhost:8080/scratch-jee-webapp/scratch/users -d '{
        "email": "some.one@there.com",
        "firstName": "Some",
        "lastName": "One",
    }'

###### Retrieve
    $ curl -XGET http://localhost:8080/scratch-jee-webapp/scratch/users
    $ curl -XGET http://localhost:8080/scratch-jee-webapp/scratch/users/1

###### Update
    $ curl -XPUT -H "Content-Type:text/html" http://localhost:8080/scratch-jee-webapp/scratch/users/1 -d '{
        "email": "some.one@there.com",
        "firstName": "Some",
        "lastName": "Two",
    }'

###### Delete
    $ curl -XDELETE http://localhost:8080/scratch-jee-webapp/scratch/users/1


The  webapp contains only two classes:

The controller class that handles the `/scratch-jee-webapp/scratch/`, `/scratch-jee-webapp/scratch/users`, and
`/scratch-jee-webapp/scratch/users/{id}` request mappings.

[`scratch.webapp.jee.controller.ScratchController`](https://github.com/karlbennett/scratch-jee-webapp/blob/master/src/main/java/scratch/webapp/jee/controller/ScratchController.java "ScratchController")

The the domain class that can be persisted into an in memory database using the CRUD endpoints.

[`scratch.webapp.data.User`](https://github.com/karlbennett/scratch-jee-webapp/blob/master/src/main/java/scratch/webapp/jee/data/User.java "User")

There are also four configuration files:

The maven [`pom.xml`](https://github.com/karlbennett/scratch-jee-webapp/blob/master/pom.xml "pom.xml") file, this
contains the TomEE plugin configuration and the dependencies for the project.

The [`persistence.xml`](https://github.com/karlbennett/scratch-jee-webapp/blob/master/src/main/resources/META-INF/persistence.xml "persistence.xml")
file that defines the data source that the users will be persisted to. This just uses the default TomEE default database
which is HSQL.

The [`openejb-jar.xml`](https://github.com/karlbennett/scratch-jee-webapp/blob/master/src/main/webapp/WEB-INF/openejb-jar.xml "openejb-jar.xml")
file that contains some custom TomEE configuration that enables the removal of JSON root nodes in the input and output.

The [`/resources.xml`](https://github.com/karlbennett/scratch-jee-webapp/blob/master/src/main/webapp/WEB-INF/resources.xml "resources.xml")
file that finished off the TomEE custom JSON configuration.

That is the entire project, have fun :)
