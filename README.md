# jASM: Java-built ASM emulator and debugger 
[![License][license-image]][license]

[license-image]: https://img.shields.io/badge/License-GPLv3-blue.svg?style=flat
[license]: https://www.gnu.org/licenses/gpl-3.0-standalone.html

## Usage

This project uses [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), so be sure to download it.

To build it, just run `make` (you'll need to have Maven installed and the `mvn` binary in your path).

Run it with `java -jar target/jASM-1.0-SNAPSHOT.jar -h` to view the usage options. Be sure to take a look at the [examples](examples/) and the syntax [documentation](doc/Syntax.md).

Running `java -jar target/jASM-1.0-SNAPSHOT.jar -m interactive` will start an interactive prompt where you will be able to enter your program.

#### Please note that these screenshots may be out of date, but they should give a good idea of the general look and features

![Cli example1](images/cliscreenshot.png)

![Cli example2](images/cliscreenshot-commands.png)

To start the GUI, run it with the `-m window` option. Make sure to provide a valid asm source file. Running `java -jar target/jASM-1.0-SNAPSHOT.jar -m window -a examples/loopdec.asm ` should show you the following:

![Screenshot](images/screenshot.png)

## Contributing

Interested in contributing? Check out the [TODO](TODO.md) and the [coding style](doc/CodingStyle.md). Or, if you prefer it,
jump ahead and read some of the [example](examples/) files.

## Licensing

This software is distributed under the GPLv3 license. For more information, please check the [License](LICENSE).

This software uses the [Apache Commons CLI Library](http://commons.apache.org/proper/commons-cli/), distributed under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt).

Graphical Interface Icons by [Daniele De Santis](http://www.danieledesantis.net/) distributed under the [CC Attribution 4.0](http://creativecommons.org/licenses/by/4.0/legalcode) license.

Additional Icon work by [IconsMind](https://www.iconsmind.com).


