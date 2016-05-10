CatanArchitecture
=================
[![Build Status](https://travis-ci.org/Guardiola31337/CatanArchitecture.svg?branch=master)](https://travis-ci.org/Guardiola31337/CatanArchitecture)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.pguardiola/catanarchitecture/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.pguardiola/catanarchitecture/badge.svg)
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)

![Catan Architecture][1]

CatanArchitecture is a sample repository to illustrate Hexagonal architecture in Android.

This sample was created to support an article explanation:

[What is all this Clean Architecture jibber-jabber about? - Part 3](http://pguardiola.com/blog/clean-architecture-part-3/)

Screenshots
-----------

![Demo Screenshot][2]

Do you want to contribute?
--------------------------

Feel free to report or add any useful feature to the sample.

Keep in mind that your PRs **must** be validated by Travis-CI. Please, run a local build with `./gradlew checkstyle build connectedCheck` before submitting your code.

Libraries used on the sample project
------------------------------------

* [HexGrid][3]
* [JUnit][4]
* [Mockito][5]
* [Retrofit][6]
* [MockWebServer][7]
* [Otto's fork][8]

Developed By
------------

* Pablo Guardiola Sánchez - <guardiola31337@gmail.com>

[![Twitter](https://raw.githubusercontent.com/Guardiola31337/guardiola31337.github.io/master/images/twitter-logo.png)](https://twitter.com/Guardiola31337 "Follow me on Twitter")
[![Linkedin](https://raw.githubusercontent.com/Guardiola31337/guardiola31337.github.io/master/images/linkedin-logo.png)](https://es.linkedin.com/in/pabloguardiola "Add me to Linkedin")

Contributors
------------

* [Pablo Guardiola Sánchez][9]
* [Pedro Vicente Gómez Sánchez][10]

Acknowledgements
----------------

* [Guillermo Gutiérrez][11]
* [Luis Artola][12]

License
-------

    Copyright 2015 Pablo Guardiola Sánchez.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: ./art/catan_architecture.png
[2]: ./art/screenshot.gif
[3]: https://github.com/Guardiola31337/HexGrid
[4]: https://github.com/junit-team/junit
[5]: https://github.com/mockito/mockito
[6]: https://github.com/square/retrofit
[7]: https://github.com/square/okhttp/tree/master/mockwebserver
[8]: https://github.com/Guardiola31337/otto
[9]: https://github.com/Guardiola31337
[10]: https://github.com/pedrovgs
[11]: https://github.com/ggalmazor
[12]: https://twitter.com/artolamola
