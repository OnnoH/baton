# baton

CLI for Camunda Conductors to generate workers based on a BPMN

## PicoCLI

https://github.com/remkop/picocli

and the creation of a configuration for resource files: https://github.com/remkop/picocli/tree/main/picocli-codegen#maven-3

## GraalVM

To make better use of the PicoCLI library, native compilation is advised. Here you can read up on GraalVM and native images: https://www.graalvm.org/latest/reference-manual/native-image/

## AsciiArt

Creation of boot banners is done with some type of AsciiArt, or as Figlet states: making large letters out of ordinary text.

http://www.figlet.org/

The Java implementation used can be found here : https://github.com/dtmo/jfiglet or https://github.com/lalyos/jfiglet
The fonts need to be added as resources. The section that generates the GraalVM configuration can be found in the `pom.xml`

Some of the AsciiArt implementations use AWT (https://en.wikipedia.org/wiki/Abstract_Window_Toolkit). Currently, the Native GraalVM releases
do not have AWT support. If you want to use it, Bellsoft provides a Native Image Kit which can be downloaded here: https://bell-sw.com/pages/downloads/native-image-kit/#nik-23-(jdk-21)

## Curl

https://github.com/libetl/curl

## Git

## Templates

## Scripting

blaze : https://github.com/fizzed/blaze

## Alternatives

Yeoman - https://yeoman.io/learning/
Maven Archetypes - https://maven.apache.org/guides/introduction/introduction-to-archetypes.html
Custom Initializr - https://docs.spring.io/initializr/docs/current/reference/html/
Bash Script(s) - https://dev.azure.com/raboweb/Insurance-Pension/_git/core-toolbox
Spring Shell - https://spring.io/projects/spring-shell/
