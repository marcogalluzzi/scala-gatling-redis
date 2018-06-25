# Load test using Gatling and Redis

This is an example load test using Gatling and Redis.
* __Gatling__ is an open-source _load testing_ library written in _Scala_. Using Gatling we can simulate many different users sending HTTP requests to a server. https://gatling.io/
* __Redis__ is an open-source _in-memory database_ for key-value storage. It can also store lists that can be manipulated using the _push_ and _pop_ commands, which can be helpful to simulate a queue. https://redis.io/

Other libraries used are:
* __Circe__, a JSON library for Scala powered by Cats. https://circe.github.io/circe/
* __Purescript__, a Scala library for loading configuration files. https://pureconfig.github.io/

## Load test description

This load test wants to simulate the behaviour of mobile users of a specific message-reading mobile app when a Push Notification is received with a new message to read.

The actions simulated are:
1. A Push Notification is received about a new message to read, so the server is notified back.
2. The user read the message, so we tell the server that we are reading the message and we get the image associated with the message.
3. The user stops reading the message and go back to the main list of organizations he/she is following. We ask the server for the list of organizations.

## How is Redis used?

In order to simulate real different users stored in the application database we use Redis as a queue.

In the server, when a new message is created, instead of calling the Firebase Cloud Messaging service to create real Push Notifications, the information about the users being notified together with the message identification is pushed into a Redis list (command `rpush`). Each element of the list will thus contain a user identifier plus the message id and the image to download.

From the Gatling application, for each user we are simulating we use the info from one element taken from the Redis list (command `lpop`).

The Gatling simulation is set up in such a way that it only performs requests if it can take a value from the Redis list.

## Project structure

The main simulation file `MobileUsersSimulation.scala` is located in the `src/test/` folder, while the remaining files of the project are in the `src/main/` folder.

## How to run?

Just run the `sbt` command `gatling:test`.