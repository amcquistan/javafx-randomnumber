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

public class RandomNumberApp extends Application {

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

The RandomNumberApp#main method serves as the entry point that starts up the JavaFX runtime by calling the Application's launch method. During the JavaFX runtime start up sequence the RandomNumberApp gets constructed and the start method is called and passed an instance of the javafx.stage.Stage class. The Stage instance serves as the graphical foundation of JavaFX applications and represents the OS specific parent window.

Inside the RandomNumberApp#start method the Java and JavaFX versions are retrieved via the System class and used to compose a greeting message which is passed to a Label class referred to as a contol in JavaFX. In JavaFX applications the graphical display is implemented as a tree data structure with the nodes representing graphical elements. The root node of the tree of graphical elements is a special JavaFX class, javafx.scene.Scene, which serves as the base of what is known as the Scene Graph planted in the aforementioned Stage object. In the above example the Scene Graph consists of just three elements with the root being the Scene object followed by a StackPane layout node serving as the parent to the Label leaf node.

*** CREATE AND DISPLAY A SCENE GRAPH OF SCENE AND LABEL NODES ***

### The Number Generator Application Design

Now that I've described the basics of how a graphical display is constructed in JavaFX I will dive into explaining the design I will be using. In building the Random Number app I will utilize the Model-View-Controller (MVC) design pattern which fits nicely into the overall architecture of the JavaFX framework.

Specificly, I will use a flavor of the MVC pattern which utilizes whats known as a Front Controller that coordinates the swapping of main view contents among other controllers.  From the users prespective this swapping of primary view components is perceived as changing pages. Below is a class diagram for the controllers to be implemented off of.






The two primary interactions with the application are: (i) to generate a random number between a user specified lower and upper bounds and, (ii) provide a view that lists the historical accounting of random numbers a user has generated along with their bounds. To maintain state in the application I will utilize a RandomNumber class to hold the bounds, random number, and date it was create. The instances of RandomNumber will be managed by an implementation of a Data Access Object interface named RandomNumberDAO which simply maintains a list of the instances. Right now RandomNumberDAO will be implemented as an in memory list of objects by a class named LocalRandomNumberDAO, perhaps later that could be SQLiteRandomNumberDAO or FileRandomNumberDAO but, I'll leave those to a later post. Additionally, I will utilize a special class known as a View Model which utilizes JavaFX properties to display the data in RandomNumber. Below is the class diagram for the remaining classes.





### Scafolding Out the App Structure

To begin I will add a new package in the project by right clicking on the project's com.thecodinginterface.randomnumber package, selecting New > Package and adding a subpackage named controllers.  Then I similarly add the new Java class controllers inside the controllers package named FrontController, BaseController, NumberGeneratorController and, NumbersViewController. 

I'll begin building out the FrontController.java class. In this design I have chosen to implement the FrontController class as a singleton and, from the class diagram you can see that the FrontController holds a reference to the Stage that was passed to the RandomNumberApp#start method during the launch process.  FrontController also contains the single parent layout class node, javafx.scene.layout.BorderPane, which will be the root layout node for the Scene object. BorderPane is a very flexible and popular choice among JavaFX applications and is composed of 5 sections capable of holding child nodes. Below is a depiction highlighting the organization of the BorderPane class.

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

Below is the implementation for the FrontController class. 
```
// FrontController.java

package com.thecodinginterface.randomnumber.controllers;

import java.net.URL;

import com.thecodinginterface.randomnumber.RandomNumberApp;
import com.thecodinginterface.randomnumber.repository.RandomNumberDAO;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FrontController {
    public static final double APP_WIDTH = 500;
    public static final double APP_HEIGHT = 350;

    private static final FrontController INSTANCE = new FrontController();

    private RandomNumberDAO randomNumberDAO;

    private Stage primaryStage;
    private BorderPane rootBorderPane;

    private FrontController() {
        rootBorderPane = new BorderPane();
        rootBorderPane.setTop(makeNavBar());
    }

    public static FrontController getInstance() {
        return INSTANCE;
    }

    private HBox makeNavBar() {
        var generateNumbersBtn = new ToggleButton("Generate Number");
        var viewNumbersBtn = new ToggleButton("View Numbers");
        var toggleGroup = new ToggleGroup();
        generateNumbersBtn.setToggleGroup(toggleGroup);
        viewNumbersBtn.setToggleGroup(toggleGroup);
        generateNumbersBtn.setSelected(true);
        
        generateNumbersBtn.getStyleClass().add("navbar-btn");
        viewNumbersBtn.getStyleClass().add("navbar-btn");
        generateNumbersBtn.setOnAction(evt -> showNumberGeneratorView());
        viewNumbersBtn.setOnAction(evt -> showNumbersListView());
        
        var imageView = new ImageView(new Image(
            RandomNumberApp.class.getResourceAsStream("images/TCI-Logo.png")));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(58);
        var logoLbl = new Label("", imageView);
        logoLbl.getStyleClass().add("navbar-logo");
        var hbox = new HBox(logoLbl, generateNumbersBtn, viewNumbersBtn);
        hbox.getStyleClass().add("navbar");
        
        return hbox;
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        var scene = new Scene(rootBorderPane, APP_WIDTH, APP_HEIGHT);
        URL url = RandomNumberApp.class.getResource("styles/styles.css");
        scene.getStylesheets().add(url.toExternalForm());
        this.primaryStage.setScene(scene);
    }

    public void setRandomNumberDAO(RandomNumberDAO randomNumberDAO) {
        this.randomNumberDAO = randomNumberDAO;
    }

    public RandomNumberDAO getRandomNumberDAO() {
        return randomNumberDAO;
    }

    public void showNumberGeneratorView() {
        updateContent(new NumberGeneratorController());
    }

    public void showNumbersListView() {
        updateContent(new NumbersViewController());
    }
    
    private void updateContent(BaseController ctrl) {
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

I'd like to begin by focusing on the FrontController#setStage(Stage) method since this is basically going to serve as the entry point where the RandomNumberApp class hands over control of the app to the FrontController. 

```
public void setStage(Stage primaryStage) {
    this.primaryStage = primaryStage;
    var scene = new Scene(rootBorderPane, APP_WIDTH, APP_HEIGHT);
    URL url = RandomNumberApp.class.getResource("styles/styles.css");
    scene.getStylesheets().add(url.toExternalForm());
    this.primaryStage.setScene(scene);
}
```

Clearly, the reference to Stage is set to the class followed by creation of Scene object which is given the root node named BorderPane with the dimensions (nothing too crazy there). However, the next lines that follow are worth talking more about. A URL instance is created by referencing a CSS style sheet resource (yes, CSS for a desktop app, pretty cool right?!) and attached to the Scene instance that was just created. This styles/styles.css file lives in src/main/resources directory along with a images/TCI-Logo.png image as shown below. Note that both these are within the root package com.thecodinginterface.randomnumber which enables resources to be built using RandomNumberApp.class.getResource(...).

*** RandomNumber-Resources.png ***

Next let me focus on the FrontController#makeNavBar method which is used in the private constructor to return an HBox node representing a navbar like view component assigning it to the top section of the BorderPane.

```
private HBox makeNavBar() {
    var generateNumbersBtn = new ToggleButton("Generate Number");
    var viewNumbersBtn = new ToggleButton("View Numbers");
    var toggleGroup = new ToggleGroup();
    generateNumbersBtn.setToggleGroup(toggleGroup);
    viewNumbersBtn.setToggleGroup(toggleGroup);
    generateNumbersBtn.setSelected(true);
    
    generateNumbersBtn.getStyleClass().add("navbar-btn");
    viewNumbersBtn.getStyleClass().add("navbar-btn");
    generateNumbersBtn.setOnAction(evt -> showNumberGeneratorView());
    viewNumbersBtn.setOnAction(evt -> showNumbersListView());
    
    var imageView = new ImageView(new Image(
        RandomNumberApp.class.getResourceAsStream("images/TCI-Logo.png")));
    imageView.setPreserveRatio(true);
    imageView.setFitWidth(58);
    var logoLbl = new Label("", imageView);
    logoLbl.getStyleClass().add("navbar-logo");
    var hbox = new HBox(logoLbl, generateNumbersBtn, viewNumbersBtn);
    hbox.getStyleClass().add("navbar");
    
    return hbox;
}
```

The first thing you see is the creation of two ToggleButton instances which represent navigable controls for transitioning between the two view pages. The ToggleButtons are associated with a ToggleGroup which enforces that only one can be in a selected state at a time which by default will be the random number generatoring view controlled by the generateNumbersBtn ToggleButton.

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


