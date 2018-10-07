# PowerHigh
PowerHigh is a 2D game library using a component-system, it is also very customizable via various drawing calls. And custom-doublebuffering, with (at next version) Android support.

## Getting started

[![Build Status](https://travis-ci.org/zenith391/PowerHigh.svg)](https://travis-ci.org/zenith391/PowerHigh)

### Prerequisites
You must atleast have Java 9 installed, or a more recent version (Java 10 / 11)
1. Go to PowerHigh releases on the [Releases](https://github.com/zenith391/PowerHigh/releases) tab.
2. Download powerhigh-core.jar and, since you're on PC, powerhigh-swing.jar (powerhigh-opengl.jar might be available for 1.0)
2. Add the JARs as dependencies to your project, using your favorite IDE.

### Running the test.
To run the test and ensure PowerHigh works goodly,
open a terminal and write following commands:
```sh
cd <directory where you installed PowerHigh>
java -cp powerhigh-core.jar;powerhigh-swing.jar;powerhigh-test.jar test.org.powerhigh.LGGLTest
```
If you see a window opened with stuff on it, everything works and the JAR is ready to be used.

PowerHigh is built with Maven.

## Contributing

If you want to contribute, first of all, thanks you! I can't work on this library full time and contributions
are really helpful and keep knowing that people uses and help me on this project is motivating.
But there is some rules/tips for contributing:
First of all, the issue/pull request must have things that goes with the library. For example, i will not accept 3D because
this library has been made for 2D.
Also, if you're posting an bug issue and think you can be able to post a little pull request / commits to fix it, it would be very nice to do it.

## License
The project is released under MIT license - more details in [LICENSE](https://github.com/DigitalSnakeSoftware/PowerHigh/blob/master/LICENSE) file.
