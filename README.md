<h1> Book Rating - REST API </h1>

A Restful application using CRUD operations to manage books, ratings and users.

## Technology stack
* Spring Boot
* Spring Security (Basic Authentication)
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* JUnit
* Mockito

## Setup

1. Clone the application

> git clone https://github.com/vitoorsm/springboot-rest-api.git

2. Create PostgreSQL database

> create database bookrating

## Explore Rest APIs

The app defines following CRUD APIs.

*Since Spring Security is using httpBasic there's no need to a login page.*

### Users -

Method    | URL
--------- | ------
POST      | /register
POST      | /update-password

##### CREATE User - /register


```json
{
    "username":"vitor",
    "password":"123"
}
```

##### UPDATE User password - /update-password

```json
{
    "password":"abc"
}
```

### Ratings -

*A user can't rate twice the same book*\
*Only logged users can rate*

Method    | URL
--------- | ------
POST      | /books/rate
GET       | /books/top

##### RATE Books - /books/rate

```json
{
    "book_id":"3",
    "rate":"4"
}
```
```json
{
    "book_id":"2",
    "rate":"3"
}
```
##### GET Best rated books - /books/top

```json
[
    {
        "id": 3,
        "name": "Harry Potter and the Philosopher's Stone",
        "author": "J.K. Rowling",
        "isbn10": "1594130000",
        "ratingsavg": 4.0
    },
    {
        "id": 2,
        "name": "Mistborn: The Final Empire",
        "author": "Brandon Sanderson",
        "isbn10": "0765350386",
        "ratingsavg": 3.0
    },
    {
        "id": 10,
        "name": "Dune",
        "author": "Frank Herbert",
        "isbn10": "0340960191",
        "ratingsavg": 0.0
    },
    {
        "id": 9,
        "name": "Fire & Blood",
        "author": "George R. R. Martin",
        "isbn10": "0593357531",
        "ratingsavg": 0.0
    },
    {
        "id": 8,
        "name": "The Lost Hero",
        "author": "Rick Riordan",
        "isbn10": "1423113462",
        "ratingsavg": 0.0
    }
]
```

### Books - 

*By default ten books are add to the database when the application starts.*\
*Only admin authorities can add, update or delete books.*\
*Delete operations doesn't need JSON body, only url.*

Method    | URL
--------- | ------
POST      | /books/add
GET       | /books
GET       | /books/releases  
GET       | /books/{id}
UPDATE    | /books/update/{id}  
DELETE    | /books/delete/{id}
DELETE    | /books/delete-all

#### CREATE book - /books/add

```json
{
    "name":"The Way of Kings",
    "author":"Brandon Sanderson",
    "isbn10":"0765376679"
}
```

#### GET Books - /books
```json
[
    {
        "id": 1,
        "name": "The Hobbit",
        "author": "J. R. R. Tolkien",
        "isbn10": "0261103342",
        "ratingsavg": 4.0
    },
    {
        "id": 2,
        "name": "Mistborn: The Final Empire",
        "author": "Brandon Sanderson",
        "isbn10": "0765350386",
        "ratingsavg": 3.0
    },
    {
        "id": 3,
        "name": "Harry Potter and the Philosopher's Stone",
        "author": "J.K. Rowling",
        "isbn10": "1594130000",
        "ratingsavg": 0.0
    },
    {
        "id": 4,
        "name": "The Lightning Thief",
        "author": "Rick Riordan",
        "isbn10": "0786856297",
        "ratingsavg": 0.0
    },
    {
        "id": 5,
        "name": "A Game of Thrones: A Song of Ice and Fire",
        "author": "George R. R. Martin",
        "isbn10": "0553103547",
        "ratingsavg": 0.0
    },
    {
        "id": 6,
        "name": "The Name of the Wind",
        "author": "Patrick Rothfuss",
        "isbn10": "0756404746",
        "ratingsavg": 0.0
    },
    {
        "id": 7,
        "name": "The Lord of the Rings",
        "author": "J. R. R. Tolkien",
        "isbn10": "0544273443",
        "ratingsavg": 0.0
    },
    {
        "id": 8,
        "name": "The Lost Hero",
        "author": "Rick Riordan",
        "isbn10": "1423113462",
        "ratingsavg": 0.0
    },
    {
        "id": 9,
        "name": "Fire & Blood",
        "author": "George R. R. Martin",
        "isbn10": "0593357531",
        "ratingsavg": 0.0
    },
    {
        "id": 10,
        "name": "Dune",
        "author": "Frank Herbert",
        "isbn10": "0340960191",
        "ratingsavg": 0.0
    },
    {
        "id": 11,
        "name": "The Way of Kings",
        "author": "Brandon Sanderson",
        "isbn10": "0765376679",
        "ratingsavg": 0.0
    }
]
```

#### GET Books releases - /books/releases
```json
[
    {
        "id": 11,
        "name": "The Way of Kings",
        "author": "Brandon Sanderson",
        "isbn10": "0765376679",
        "ratingsavg": 0.0
    },
    {
        "id": 10,
        "name": "Dune",
        "author": "Frank Herbert",
        "isbn10": "0340960191",
        "ratingsavg": 0.0
    },
    {
        "id": 9,
        "name": "Fire & Blood",
        "author": "George R. R. Martin",
        "isbn10": "0593357531",
        "ratingsavg": 0.0
    },
    {
        "id": 8,
        "name": "The Lost Hero",
        "author": "Rick Riordan",
        "isbn10": "1423113462",
        "ratingsavg": 0.0
    },
    {
        "id": 7,
        "name": "The Lord of the Rings",
        "author": "J. R. R. Tolkien",
        "isbn10": "0544273443",
        "ratingsavg": 0.0
    }
]
```
#### GET Book by id - /books/4
```json
{
    "id": 4,
    "name": "The Lightning Thief",
    "author": "Rick Riordan",
    "isbn10": "0786856297",
    "ratingsavg": 0.0
}
```
#### UPDATE Book by id - /books/update/1
```json
{
    "name":"The Hobbit: Or, There and Back Again",
    "author":"J. R. R. Tolkien",
    "isbn10":"0008376131"
}
```

#### UPDATED Book
```json
 {
     "id": 1,
     "name": "The Hobbit: Or, There and Back Again",
     "author": "J. R. R. Tolkien",
     "isbn10": "0008376131",
     "ratingsavg": 4.0
 }
 ```
