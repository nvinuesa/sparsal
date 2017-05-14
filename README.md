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

Builds are available for scala 2.10.x, 2.11.x and 2.12.2.

### SBT

Simply add sparsal's dependency to your build.sbt if you don't know which version of scala you are using and sbt will automatically detect and search for the appropriate package:
```scala
libraryDependencies += "com.github.underscorenico" %% "sparsal" % "0.2.0"
```
Or either specify the desired target version:
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
### Try it

First initialize your MatchingPursuit (or Orthogonal Matching Pursuit) object with an input and a defined dictionary:
```scala
val input = Array.fill(10)(5)

val dictionary = Gabor(input.length)

val mp =  MatchingPursuit1D(input, dictionary)
```
Then recover the approximation (after the algorithm converges to a certain provided accuracy measure):
```scala
    val result: (List[(Double, Int)], Seq[Double]) = mp.run(SNR(20.0))
```
The result will contain a list of selected atoms from the dictionary. The first element of the touple (type Double) will contain the inner product between the input at Iteration n-1 and the selected atom. 
<br>
The second element contains the index of the atom from the dictionary.

## Contributing

Everyone is welcome to contribute, either by adding features, solving bugs or helping with documentation.
<br>
Sparsal embraces [the open code of conduct][codeofconduct] from the [TODO group][todogroup], therefore all of its channels should respect its guidelines.
<br>
That being said, we are a community and we should not need guidelines for our conduct but leave it to our common sense instead.

[codeofconduct]: http://todogroup.org/opencodeofconduct
[todogroup]: http://todogroup.org
