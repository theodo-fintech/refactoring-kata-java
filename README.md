# Refactoring Kata Test

## Introduction

We have a service that expose a route which give the amount a customer should pay given its cart
and its type.

This route was created ages ago, and nobody really knows who coded it or how it really works.
Nonetheless, as the business changes frequently, this class has already been modified many times,
making it harder to understand at each step.

Today, once again, the PO wants to add some new stuff to it and add the management for young customers.
But this class is already complex enough and just adding a new behaviour to it won't work this time.

Your mission, should you decide to accept it, is to **refactor `ShoppingController` to make it
understandable by the next developer** and easy to change afterwards. Now is the time for you to
show your exceptional skills and make this implementation better, extensible, and ready for future
features.

Sadly for you, this route is called everywhere, and **you can't change its signature**.

This exercise **should not last longer than 1,5 hour** (but this can be too short to do it all and
you can take longer if you want).

You can run the example file to see the method in action.

## Deliverables
What do we expect from you:
- the link of the git repository
- several commits, with an explicit message each time
- a file / message / email explaining your process and principles you've followed

## Prerequisite

- Java 11

## How to run the code

- You can start the application using maven: `./mvnw`. It will expose a route POST accessible at `http://localhost:8080/shopping
- You can launch the tests with `./mvnw test`

**Good luck!**
