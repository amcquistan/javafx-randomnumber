# JavaFX Development with Gradle, Eclipse, SceneBuilder and OpenJDK 11: Project Setup

### Sub Intro

The web is a preferred choice for software due to the ubiquitity and accessibility of browsers but, exceptions exist necessitating the need for a quality desktop app platform such as the need to lockdown data to a few geographically close computers or desire to restrict internet connectivity from a particular app. Suprisingly, this can be common for high-tech fields like science and medicine which rely on the exceptional Java platform and JavaFX framework for quality desktop software

### Introduction

For many project's the web is the appropriate choice to build software on due to the ubiquitity and accessibility of browsers as a distribution channel for applications. However, important exceptions exist which necessitate the need for a quality desktop applications platform such as the need to keep data locked down to one or few geographically local computers plus desire to restrict or exclude internet connectivity from a particular application or set of computers. Suprisingly, this can be particularly common among highly technologically adept fields like science, medicine, government agencies, and the like which rely on the exceptional Java platform and JavaFX framework to build quality desktop as well as mobile software.

In this series I demonstrate how to build a multiscreen JavaFX desktop application which generates and displays random numbers from scratch, your welcome in advance lottery playing community. I guide the reader through the minutia of setting up a development environment using popular, production grade, tools such as Eclipse, Gradle, and Gluon's SceneBuilder followed by a gentle scratch at the surface of the JavaFX desktop application framework.

__Series Contents__

* JavaFX Development with Gradle, Eclipse, SceneBuilder and OpenJDK 11: Project Setup
* JavaFX Development with Gradle, Eclipse, SceneBuilder and OpenJDK 11: Java Code only Approach
* JavaFX Development with Gradle, Eclipse, SceneBuilder and OpenJDK 11: Refactoring to use FXML


### Development Tools

In this section I want to help get the reader off on the right foot so he or she is able to follow along with the example or even just get going hacking away on their own Java / JavaFX project.

__Downloading and Installing OpenJDK 11+__

For this project I will be using OpenJDK 11. So if you haven't already done so please download it from the [OpenJDK website](https://openjdk.java.net/) and follow the install instructions described in my earlier article, [High-Level Introduction to Java for Developers](https://thecodinginterface.com/blog/intro-to-java-for-devs/).

__Installing Eclipse__

I will also be using the Eclipse IDE for this article so if you don't have that please install it from the [Eclipse website](https://www.eclipse.org/downloads/packages/release/oxygen/3a/eclipse-ide-java-developers) and follow their install instructions.

__Installing SceneBuilder and Configuring Eclipse for JavaFX Development__

Before diving into project setup I will also show how to install the JavaFX Eclipse plugin as well as Gluon's awesome SceneBuilder app then configure it in Eclipse.

Start by downloading and installing [Gluon's SceneBuilder](https://gluonhq.com/products/scene-builder/) tool. Make note of where you choose to install SceneBuilder at as this will be needed shortly.

Follow that up by opening Eclipse, clicking the help menu then the Eclipse Marketplace submenu.

*** EclipseMarketPlaceHelp.jpg ***

In the search field type javafx and a plugin named e(fx)clipse x.x.x will be returned. Install it and let Eclipse restart.

*** EclipseJavaFXPlugin.jpg ***

After Eclipse has restarted open the preferences menu dialog at Window > Preferences then select the JavaFX section from the menu on the left. It is here that you need to browse to the SceneBuilder executable previously installed so Eclipse will use it to open FXML files. Don't worry about the JavaFX SDK bit, that can be left empty. Just select Apply and Close.

*** SceneBuilderPath.jpg ***

### Project Setup

To make my life simpler and give the reader a realistic and quality example I will utilize the Gradle build system and project structure.

To start I create a new project in Eclipse by clicking file > New > Project

*** StartNewProj1.JPG ***

In the resulting dialog expand Gradle and select Gradle Project

*** StartNewProj2.jpg ***

Name the project randomnumber and click finish

*** StartNewProj3.jpg ***

Afterwards I pop open the build.gradle file and make a few changes specific for a JavaFX project like specifying the java application and javafx plugins, switch the repositories to point to Maven Central then configure the JavaFX version to use version 12 along with including the javafx.controls module. I finish with pointing the mainClassName field to a class I'll add shortly named AppLauncher. The updated build.gradle file is shown below.

```
plugins {
    id 'eclipse'
    id 'application'
    id 'org.openjfx.javafxplugin' version '0.0.7'
}

repositories {
    mavenCentral()
}

sourceCompatibility = 11
targetCompatibility = 11

javafx {
    version = "12"
    modules = [ 'javafx.controls' ]
}

dependencies {
    testImplementation 'junit:junit:4.12'
}

mainClassName = "com.thecodinginterface.randomnumber.AppLauncher"
```

By default a package named randomnumber was created in the project. As you can see above I have referenced the AppLauncher class from another package named com.thecodinginterface.randomnumber (which is more consistent with the reverse domain name common to idiomatic Java) so, I will need to introduce this new package by expanding the src/main/java source in the Project Explorer, right clicking and selecting New > Package and naming it com.thecodinginterface.randomnumber. The randomnumber package and it's contents should also be deleted from src/test/java as well.

*** BetterPackageName.jpg ***

Next I add the AppLauncher and RandomNumberApp Java classes source files to the new package. I start with right clicking the com.thecodinginterface.randomnumber package, selecting New > Class then name it RandomNumberApp and check the box to include the main method then click finish.

*** AddRandomNumberAppDialog.jpg ***

Inside the newly added class I update it to extend the abstract Application class from the JavaFX framework then add some code that I lifted from the JavaFX community site's Hello World example from [GitHub](https://github.com/openjfx/samples/blob/master/HelloFX/Gradle/hellofx/src/main/java/HelloFX.java). I highly recommend all readers to have a look at the linked repo and the [OpenJFX community site](https://openjfx.io/index.html).

*** HelloWorldAppWithErrors.JPG ***

Don't worry about the errors being displayed in Eclipse, I will address those soon.

Moving on I add the AppLauncher class file in the same package.

*** AppLauncher.jpg ***

The code inside AppLauncher#main simply calls the static method of RandomNumberApp#main passing it args parameter.

```
// AppLauncher.java

package com.thecodinginterface.randomnumber;

public class AppLauncher {
    public static void main(String[] args) {
        RandomNumberApp.main(args);
    }
}
```

Alright, now I can clear up those Eclipse warnings and errors by right clicking on the randomnumber project, opening the Gradle submenu and clicking Refresh Gradle Project.

*** InitialGradleProjectRefresh.jpg ***

At this point the errors and warning go away. YAY!!!

To give the project a test run I need to configure the Gradle run task by right clicking the randomnumber project then going down to Run As > Run Configurations to open the config dialog.  In the dialog I select Gradle Project on the left, click the plus sign to create a new run configuration, name it gradle-run, then type run in the Gradle Tasks text area and select the randomnumber project as the workspace. Click apply then Run.

*** GradleRunTaskConfig.jpg ***

A JavaFX application window will now open as shown below.

*** HelloWorldSampleApp.jpg ***

### Demo Application Requirements

This demo application will generate a random number within lower and upper bounds specified as two user inputs fields. The user will then click a button to trigger the app to generate and display a number plus save it in a collection of randomly generated numbers.  The user may choose to view their previously generated numbers as well as the lower and upper boundaries entered.

Below is a mockup of the number generator screen.

__Random Number Generator View__

```
--------------------------------------------
| New Number | My Numbers                  |
|------------------------------------------|
|                                          |
|             ################             |
|                                          |
|         Random Number Generator          |
|               -------------              |
|         Min   |           |              |
|               -------------              |
|               -------------              |
|         Max   |           |              |
|               -------------              |
|                                          |
|               ____________               |
|              |            |              |
|              |  Generate  |              |
|              |____________|              |
|                                          |
|                                          |
--------------------------------------------
```

__Numbers List Screen__

Here is another mockup, this time of the table of previously generated numbers.

```
--------------------------------------------
| New Number | My Numbers                  |
|------------------------------------------|
|                                          |
|               Random Numbers             |
|     --------------------------------     |
|     | Created | Number | Min | Max |     |
|     --------------------------------     |
|     |         |        |     |     |     |
|     |         |        |     |     |     |
|     |         |        |     |     |     |
|     |         |        |     |     |     |
|     |         |        |     |     |     |
|     --------------------------------     |
|                                          |
|                                          |
--------------------------------------------
```


### Conclusion

In this introductory article in a series on building desktop applications using Java (OpenJDK 11) along with JavaFX I have covered how to setup a development environment utilizing Gradle, Eclipse and, Gluon's SceneBuilder as well as described the Random Number demo application I will be building in this series. In the next article I will be getting into how to build the application using the JavaFX framework.


