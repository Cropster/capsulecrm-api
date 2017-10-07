# Capsule CRM SDK for Java

Unofficial Java SDK for [Capsule CRM's API v2][1].

## Features

> **Warning:** this library is not feature complete. We have only
developed the features we've needed internally. As we need more features
we will add them to this library. We welcome PRs filling in new features.

### What's implemented so far?

* List parties
* Filter parties by tag and type
* Update existing parties
* List tags and fields

## Setup

> **Note:** This artifact isn't published to the public Maven repo yet.
> Interested in using it? Please contact us by opening an issue.

Grab via Maven:
```xml
<dependency>
  <groupId>com.cropster</groupId>
  <artifactId>capsulecrm-api</artifactId>
  <version>0.1</version>
</dependency>
```

or Gradle:

```gradle
compile 'com.cropster:capsulecrm-api:0.1'
```

### Requirements

* Java 8
* Capsule CRM API Credentials

The SDK uses [Retrofit 2][2] under the hood as a REST client, which uses OkHttp as the underlying http client.

## Usage

### Quick Start

Create an instance of the API client. It is immutable and thread safe.

```java
final CapsuleClient capsuleClient = CapsuleClient.builder()
        .accessToken(<access token>)
        .userAgent(<user agent>)
        .logLevel(Logger.Level.FULL)
        .logger(log::info) // log can be an Slf4j or log4j logger
        .build();
```

See the [official capsule docs][4] to learn how to get an access token.

A client can perform various operations on different types of resources (Party, Tag, CustomField, etc). The resources are organized into modules:

```java
capsule().parties() // retrieve the parties module
```

Each module contains a set of methods which can be used to perform various operations on the specified resource type.

Example: Retrieving all parties (synchronously):

```java
List<Party> orgs = capsule()
                  .parties()
                  .all();
```

See the Examples section below for more examples.

### Data Model

This library heavily uses the Immutable Builder pattern to handle data objects.

For example this creates a new party object with only the firstName field:

```java
Party alice = Party.builder().firstName("Alice").build();

// Serializes to the JSON:
{
    "party":  {
        "firstName": "Alice"
    }
}
```

The `alice` object is an immutable instance of `Party`. This means the following does not work:

```java
alice.setLastName("Sanchez")  // DOES NOT WORK:  there are no setters on Party
```

Instead all changes are performed by building a new object. If you want
to give Alice a last name you must use the `withLastName()` method.

```java
Party aliceSanchez = alice.withLastName("Sanchez");

// Serializes to the JSON:
{
    "party":  {
        "firstName": "Alice"
        "lastName": "Sanchez"
    }
}
```


If you want to transform multiple fields at once use the `toBuilder()` method.

```java
Party aliceSanchezCeo = aliceSanchez.toBuilder()
                                 .jobTitle("CEO")
                                 .emailAddress(Email.builder()
                                                .address("alice@sanchez.com")
                                                .type("Work")
                                                .build())
                                 .build();

// Serializes to the JSON:
{
    "party":  {
        "firstName": "Alice"
        "lastName": "Sanchez",
        "jobTitle": "CEO",
        "emailAddresses": [
            {
              "type": "Work",
              "address": "alice@sanchez.com"
            }
      ],
    }
}
```


### Examples

#### Retrieving all parties (synchronously):

```java
List<Party> orgs = capsule()
                  .parties()
                  .all();
```

#### Retrieve all organisations with a certain tag (synchronously):

```java
List<Party> orgs = capsule()
                  .parties()
                  .ofType(PartyType.ORGANISATION)
                  .withTag("SpecialTag")
                  .all();
```

#### Retrieve one party by id (synchronously):

```java
List<Party> orgs = capsule()
                  .parties()
                  .one(50L);
```

#### Add a tag to party (synchronously):

```java
Optional<Party> party = capsule()
                        .parties()
                        .add(party, Tag.builder().id(1234L).build())
```

#### Update a party (synchronously):

Just like the Capsule HTTP API, when updating a value omitted/null fields
are left unchanged.

So if you want to edit the about line of a party and you know its id, then it is as as simple as:

```java
Optional<Party> party = capsule()
                        .parties()
                        .update(Party.builder().id(50L).about("a new about line").build())
```


### License

Licensed under the Apache License, Version 2.0, see [LICENSE][5] for full details.

Copyright (C) 2017 [Cropster GmbH][6].

The architecture of this library was inspired by [Contentful's Management SDK for Java][7].
Some of the OkHTTP Client interceptors are from their project directly,
which licensed under the same license. These files are marked with Apache 2.0 license header.

Mad props to Contentful for the inspiration!


[1]: https://developer.capsulecrm.com/v2
[2]: https://square.github.io/retrofit
[3]: https://square.github.io/okhttp
[4]: https://developer.capsulecrm.com/v2/overview/authentication
[5]: LICENSE
[6]: https://cropster.com
[7]: https://github.com/contentful/contentful-management.java

