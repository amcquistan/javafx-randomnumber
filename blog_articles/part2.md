# JavaFX Development with Gradle, Eclipse, SceneBuilder and OpenJDK 11: Java Only Components

### Introduction

In this second article I will be building out the Random Number generator application described in the opening blog post. For this series I will be forgoing the use of FXML views and not using the SceneBuilder design tool to give the reader a taste of how to do so without it or when it simply makes more sense to code things by hand. In the next post I will refactor using SceneBuilder and FXML view files.

__Series Contents__

* JavaFX Development with Gradle, Eclipse, SceneBuilder and OpenJDK 11: Project Setup
* JavaFX Development with Gradle, Eclipse, SceneBuilder and OpenJDK 11: Java only Components
* JavaFX Development with Gradle, Eclipse, SceneBuilder and OpenJDK 11: Refactoring to use FXML

### Closer Look at the Hello World App

To kick the article off I dig into how the JavaFX framework functions from a high level using the simple [HelloFX.java](https://github.com/openjfx/samples/blob/master/HelloFX/Gradle/hellofx/src/main/java/HelloFX.java) sample program from the openjfx GitHub repo. Below is the JavaFX program replicated in the RandomNumberApp.java class I presented in the previous article. The thing that makes this a JavaFX application is the fact that RandomNumberApp class extends the javafx.applicaiton.Application abstract class and provides an implementation of its Application#start method.

```
// RandomNumberApp.java

package com.thecodinginterface.randomnumber;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RandonNumberApp extends Application {

    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
```

The RandomNumberApp#main method serves as the entry point that starts up the JavaFX runtime by calling the Application's launch method. During the JavaFX runtime start up sequence the RandomNumberApp gets constructed and the start method is called passing an instance of the javafx.stage.Stage class. The Stage instance serves as the graphical foundation of JavaFX applications and represents the OS specific parent window.

Inside the RandomNumberApp#start method the Java and JavaFX versions are retrieved via the System class and used to compose a greeting message which is passed to a JavaFX Label control class. In JavaFX applications the graphical display is implemented as a tree data structure with the nodes representing graphical elements. The root node of the tree of graphical elements is a special JavaFX class, javafx.scene.Scene, which serves as the base of what is known as the Scene Graph planted in the aforementioned Stage object. In the above example the Scene Graph consists of just three elements with the root being the Scene object followed by a StackPane layout node serving as the parent to the Label control leaf node.

*** CREATE AND DISPLAY A SCENE GRAPH OF SCENE AND LABEL NODES ***

### The Number Generator Application Design

Now that I've described the basics of how a graphical display is constructed in a JavaFX application I will dive into building the random number generator screen. In building the Random Number application I will be utilizing the Model-View-Controller (MVC) design pattern which fits nicely into the overall architecture of the JavaFX framework.

I will be utilizing a specific flavor of the MVC pattern which utilizes whats known as a Front Controller that coordinates the swapping of primary view contents among other controllers.  From the users prespective this swapping of primary view components is perceived as changing pages.

The two primary use cases for the application are: (i) to generate a random number between a user specified lower and upper bounds and, (ii) provide a view that lists the historical accounting of random numbers a user has generated along with their bounds.

The overall design class diagram looks as shown below.

```

  -------------------------------------------
  | FrontController                          |
  |------------------------------------------|
  | + APP_WIDTH : double                     |
  | + APP_HEIGHT : double                    |
  | - primaryStage : Stage                   |
  | - rootBorderPane : BorderPane            |
  | - INSTANCE : FrontController             |
  |------------------------------------------|
  | - makeNavMenu(void) : HBox               |
  | + getInstance(void) : FrontController    |
  | + setStage(Stage) : void                 |
  | + showNumberGeneratorView(void) : void   |
  | + showNumbersListView(void) : void       |
  | + showStage(void) : void                 |
  --------------------------------------------

```

The collection of packages used to organize the classes of this present in the design class diagram is depicted below in a package diagram.



Next I show a sequence diagram for the random number generation use case.


The number list view is too simple to warrant a design diagram.

### Scafolding Out the App Structure

To being I will add a new package in the project by right clicking on the project's com.thecodinginterface.randomnumber package, selecting New > Package and adding a subpackage named controllers.  Then I similarly add a new Java class inside the controllers package and name it FrontController. The FrontController.java class will contain the single parent layout class node, javafx.scene.layout.BorderPane, which will be a direct descendant of the Scene object.

In this design I have chosen to implement the FrontController class as a singleton which simply means only one instance will ever live in memory. From the design class diagram you can see that the FrontController holds a reference to the Stage that was passed to the RandomNumberApp#start method and set via a setter. A layout node of type BorderPane is also present which serves as the base layout component. The BorderPane layout class is a very flexible and popular choice among JavaFX applications and is composed of 5 sections capable of holding child scene nodes. Below is a depiction highlighting the organization of the BorderPane class.

```

  -------------------------------
  |             Top             |
  |-----------------------------|
  |       |             |       |
  |       |             |       |
  | Left  |    Center   | Right |
  |       |             |       |
  |       |             |       |
  |-----------------------------|
  |           Bottom            |
  -------------------------------

```

For the Random Number application I will only be utilizing the Top and Center sections of the BorderPane class and let the other ones collapse on themselves which is the default behavior when they are not populated with child nodes. I will utilize the Top section to hold a navbar like set of buttons that will direct what is displayed in the Center component, either the random number generator screen or the display of previously generated numbers.

At this point I would like to present the full implementation of the FrontController.java class.

```
// FrontController.java

package com.thecodinginterface.randomnumber.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FrontController {
    public static final double APP_WIDTH = 350;
    public static final double APP_HEIGHT = 400;

    private static final FrontController INSTANCE = new FrontController();

    private Stage primaryStage;
    private BorderPane rootBorderPane;

    private FrontController() {
        rootBorderPane = new BorderPane();
        rootBorderPane.setTop(makeNavMenu());
    }

    public static FrontController getInstance() {
        return INSTANCE;
    }

    private HBox makeNavMenu() {
        HBox hbox = new HBox();

        Button generateNumbers = new Button("Generate Numbers");
        Button viewNumbers = new Button("View Numbers");

        generateNumbers.setOnAction(evt -> showNumberGeneratorView());
        viewNumbers.setOnAction(evt -> showNumbersListView());

        hbox.getChildren().addAll(generateNumbers, viewNumbers);

        return hbox;
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.stage.setScene(new Scene(rootBorderPane, APP_WIDTH, APP_HEIGHT));
    }

    public void showNumberGeneratorView() {
        NumberGeneratorController ctrl = new NumberGeneratorController();
        ctrl.setFrontController(this);
        rootBorderPane.setCenter(ctrl.getContentPane());
    }

    public void showNumbersListView() {
        NumbersViewController ctrl = new NumbersViewController();
        ctrl.setFrontController(this);
        rootBorderPane.setCenter(ctrl.getContentPane());
    }

    public void showStage() {
        if (!primaryStage.isShowing()) {
            primaryStage.show();
        }
    }
}
```


