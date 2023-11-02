package edu.rit.edgeconverter.view;

import edu.rit.edgeconverter.Listeners.DDLButtonListener;//NEWSHIT
import edu.rit.edgeconverter.Listeners.FieldListListener;
import edu.rit.edgeconverter.Listeners.MenuListener;
import edu.rit.edgeconverter.model.Table;
import edu.rit.edgeconverter.model.Field;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This is where the magic happens, folks! 🎩✨ The ConverterGUI is like the command center
 * for our Edge Converter application. Here, you'll find all the widgets, buttons, and
 * doodads that make our app tick.
 */
public class ConverterGUI {

    private Stage stage;
    private Table[] tables;//NEWSHIT
    private Field[] fields;
    // UI elements for the converter GUI
    private ListView<String> listViewTables = new ListView<>();
    private ListView<String> listViewFieldList = new ListView<>();
    private MenuItem menuOpenFile = new MenuItem("Open");
    private MenuItem menuSave = new MenuItem("Save");
    private MenuItem menuSaveAs = new MenuItem("Save as");
    private MenuItem menuExit = new MenuItem("Exit");
    private MenuItem menuShowDatabase = new MenuItem("Show Database Products Available");
    private Button buttonCreateDDL = new Button("Create DDL");
private DDLButtonListener DDLButtonListener = new DDLButtonListener(this);
    // Listeners for handling events
    private MenuListener menuListener = new MenuListener(this);
    private FieldListListener fieldListListener = new FieldListListener(this, menuListener);

    public Table[] getTables(){ //NEWSHIT
        return this.tables;
    }
    public Field[] getFields(){//NEWSHIT
        return this.fields;
    }
     public DDLButtonListener getDDLButtonListener(){
        return this.DDLButtonListener;
    }
    public void addTablesFields(Table[] tables, Field[] fields){
        this.tables=tables;
        this.fields=fields;
    }

   public ConverterGUI(Stage primaryStage) {
        this.stage = primaryStage;
    }
    public void displayMessageInTableList(String message) {
    listViewTables.getItems().clear(); // Clear existing items if necessary
    listViewTables.getItems().add(message); // Add the message to the list view
}



    public void initializeUI() {
        stage.setTitle("File Converter");
        Pane root = new Pane();
        root.setPrefSize(900, 500);

        setupMenuBar(root);
        setupListViews(root);
        setupButtons(root);
        setupLabels(root);
        setupRadioButtons(root);
        setupTextFields(root);
        setupCheckBoxes(root);
        setupEventHandlers();

        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("../style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void setupMenuBar(Pane root) {
        MenuBar menuBar = new MenuBar();
        menuBar.setPrefWidth(902);
        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(menuOpenFile, menuSave, menuSaveAs, menuExit);
        Menu optionsMenu = new Menu("Options");
        optionsMenu.getItems().addAll(new MenuItem("Show Output File Definition Location"), menuShowDatabase);
        Menu helpMenu = new Menu("Help");
        helpMenu.getItems().addAll(new MenuItem("About"));
        menuBar.getMenus().addAll(fileMenu, optionsMenu, helpMenu);
        root.getChildren().add(menuBar);
    }

    private void setupListViews(Pane root) {
        listViewTables.setLayoutX(14);
        listViewTables.setLayoutY(65);
        listViewTables.setPrefHeight(354);
        listViewTables.setPrefWidth(276);
        listViewFieldList.setLayoutX(312);
        listViewFieldList.setLayoutY(65);
        listViewFieldList.setPrefHeight(354);
        listViewFieldList.setPrefWidth(276);
        root.getChildren().addAll(listViewTables, listViewFieldList);
    }

    private void setupButtons(Pane root) {
        Button buttonDefineRelations = new Button("Define Relations");
        buttonDefineRelations.setLayoutX(80);
        buttonDefineRelations.setLayoutY(444);
        buttonDefineRelations.setPrefHeight(20);
        buttonDefineRelations.setPrefWidth(150);
        buttonDefineRelations.setDisable(true);
        buttonCreateDDL.setLayoutX(369);
        buttonCreateDDL.setLayoutY(444);
        buttonCreateDDL.setPrefHeight(20);
        buttonCreateDDL.setPrefWidth(150);
        buttonCreateDDL.setDisable(true);
        root.getChildren().addAll(buttonDefineRelations, buttonCreateDDL);
    }

    private void setupLabels(Pane root) {
        Label labelAllTables = new Label("All Tables");
        labelAllTables.setLayoutX(127);
        labelAllTables.setLayoutY(39);
        Label labelFieldList = new Label("Field List");
        labelFieldList.setLayoutX(428);
        labelFieldList.setLayoutY(39);
        root.getChildren().addAll(labelAllTables, labelFieldList);
    }

    private void setupRadioButtons(Pane root) {
        RadioButton radioButtonVarchar = new RadioButton("Varchar");
        radioButtonVarchar.setLayoutX(618);
        radioButtonVarchar.setLayoutY(133);
        RadioButton radioButtonBoolean = new RadioButton("Boolean");
        radioButtonBoolean.setLayoutX(618);
        radioButtonBoolean.setLayoutY(206);
        RadioButton radioButtonInteger = new RadioButton("Integer");
        radioButtonInteger.setLayoutX(618);
        radioButtonInteger.setLayoutY(277);
        RadioButton radioButtonDouble = new RadioButton("Double");
        radioButtonDouble.setLayoutX(618);
        radioButtonDouble.setLayoutY(351);
        root.getChildren().addAll(radioButtonVarchar, radioButtonBoolean, radioButtonInteger, radioButtonDouble);
    }

    private void setupTextFields(Pane root) {
        TextField textFieldVarcharLength = new TextField();
        textFieldVarcharLength.setId("textFieldVarcharLength");
        textFieldVarcharLength.setLayoutX(717);
        textFieldVarcharLength.setLayoutY(65);
        textFieldVarcharLength.setPrefHeight(67);
        textFieldVarcharLength.setPrefWidth(171);
        textFieldVarcharLength.setStyle("-fx-alignment: center;");
        TextField textFieldDefaultValue = new TextField();
        textFieldDefaultValue.setId("textFieldDefaultValue");
        textFieldDefaultValue.setLayoutX(715);
        textFieldDefaultValue.setLayoutY(327);
        textFieldDefaultValue.setPrefHeight(67);
        textFieldDefaultValue.setPrefWidth(171);
        textFieldDefaultValue.setStyle("-fx-alignment: center;");
        root.getChildren().addAll(textFieldVarcharLength, textFieldDefaultValue);
    }

    private void setupCheckBoxes(Pane root) {
        CheckBox checkBoxPrimaryKey = new CheckBox("Primary Key");
        checkBoxPrimaryKey.setLayoutX(755);
        checkBoxPrimaryKey.setLayoutY(207);
        CheckBox checkBoxDisallowNull = new CheckBox("Disallow Null");
        checkBoxDisallowNull.setLayoutX(755);
        checkBoxDisallowNull.setLayoutY(261);
        root.getChildren().addAll(checkBoxPrimaryKey, checkBoxDisallowNull);
    }

   private void setupEventHandlers() {
        menuOpenFile.setOnAction(menuListener);
        listViewTables.getSelectionModel().selectedItemProperty().addListener(fieldListListener);  // Corrected this line
    }


  // Getter and setter methods for the UI elements and stage

  public ListView<String> getListViewTables() {
    return listViewTables;
  }

  public void setListViewTables(ListView<String> listViewTables) {
    this.listViewTables = listViewTables;
  }

  public ListView<String> getListViewFieldList() {
    return listViewFieldList;
  }

  public void setListViewFieldList(ListView<String> listViewFieldList) {
    this.listViewFieldList = listViewFieldList;
  }

  public MenuItem getMenuOpenFile() {
    return menuOpenFile;
  }

  public void setMenuOpenEdgeFile(MenuItem menuOpenFile) {
    this.menuOpenFile = menuOpenFile;
  }

  public MenuItem getMenuSave() {
    return menuSave;
  }

  public void setMenuSave(MenuItem menuSave) {
    this.menuSave = menuSave;
  }

  public MenuItem getMenuSaveAs() {
    return menuSaveAs;
  }

  public void setMenuSaveAs(MenuItem menuSaveAs) {
    this.menuSaveAs = menuSaveAs;
  }

  public MenuItem getMenuExit() {
    return menuExit;
  }

  public void setMenuExit(MenuItem menuExit) {
    this.menuExit = menuExit;
  }

  public MenuItem getMenuShowDatabase() {
    return menuShowDatabase;
  }

  public void setMenuShowDatabase(MenuItem menuShowDatabase) {
    this.menuShowDatabase = menuShowDatabase;
  }

  public Button getButtonCreateDDL() {
    return buttonCreateDDL;
  }

  public void setButtonCreateDDL(Button buttonCreateDDL) {
    this.buttonCreateDDL = buttonCreateDDL;
  }

  public Stage getStage() {
    return this.stage;
  }
}
