## sparsal - Sparse Approximation Library

[![Build Status](https://travis-ci.org/underscorenico/sparsal.svg?branch=master)](https://travis-ci.org/underscorenico/sparsal)
[![codecov](https://codecov.io/gh/underscorenico/sparsal/branch/master/graph/badge.svg)](https://codecov.io/gh/underscorenico/sparsal)
[![Chat](https://badges.gitter.im/sparsal/Lobby.svg)](https://gitter.im/sparsal/Lobby)
[![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/underscorenico/sparsal/master/LICENSE.txt)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.underscorenico/sparsal_2.12.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.underscorenico/sparsal_2.12)

## Overview

The Sparse Approximation Library aims to provide easy-to-read, modern and maintainable sparse approximation algorithms written in a functional style.
<br>
At the moment only [the matching pursuit algorithm][mp] is implemented. Future versions will increment the number of algorithms.
<br>
Check [this article][blog] for some basic theoretical background.

[blog]: https://underscorenico.github.io/blog/2017/01/14/about-sparsal
[mp]: https://en.wikipedia.org/wiki/Matching_pursuit

## Using sparsal

Builds are available for Scala 2.12.x.

### SBT

Simply add sparsal's dependency to your build.sbt:
```scala
libraryDependencies += "com.github.underscorenico" % "sparsal_2.12" % "0.2.0"
```

### Maven

If you are using Maven, simply add sparsal to your dependencies to your pom.xml:
```xml
<dependency>
    <groupId>com.github.underscorenico</groupId>
    <artifactId>sparsal_2.12</artifactId>
    <version>0.2.0</version>
</dependency>
```

## Contributing

Everyone is welcome to contribute, either by adding features, solving bugs or helping with documentation.
<br>
Sparsal embraces [the open code of conduct][codeofconduct] from the [TODO group][todogroup], therefore all of its channels should respect its guidelines.
<br>
That being said, we are a community and we should not need guidelines for our conduct but leave it to our common sense instead.

[codeofconduct]: http://todogroup.org/opencodeofconduct
[todogroup]: http://todogroup.org
