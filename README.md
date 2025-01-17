# Kappa

> This project is a task for my Programing class.

Kappa is a simple 100% Java console app that simulates simple management software for e-shop administrator.

## Requirements
- Java 22
- MySQL 8.0 or MariaDB 10.6

## Installation

1. Clone the repository
```bash
git clone https://Trup10ka/Kappa.git
cd Kappa
./gradlew build
```
OR
### Download the latest release from the [releases](https://github.com/Trup10ka/Kappa/releases) page.

## Description

The app consists of simple tasks:

- Manipulating with customers
- Manipulating with products
- Manipulating with orders

## How to run

```bash
java -jar ./Kappa-1.0-SNAPSHOT.jar
```

When you run your app for the first time, a template config file will be created and the app will close.

You edit the config file with the proper values and run the app again.

## CLI commands

The most important command is:

```bash
help 
```
**prints out all the commands available**

```bash
help [command]
```
**prints out the description of the command**
