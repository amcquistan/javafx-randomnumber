package com.thecodinginterface.randomnumber.controllers;

import javafx.scene.layout.AnchorPane;

public abstract class BaseController {
    FrontController frontController;
    
    abstract AnchorPane getContentPane();
    
    public void setFrontController(FrontController frontController) {
        this.frontController = frontController;
    }
}
