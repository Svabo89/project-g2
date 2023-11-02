package edu.rit.edgeconverter.Listeners;

import edu.rit.edgeconverter.model.Field;
import edu.rit.edgeconverter.model.Table;
import edu.rit.edgeconverter.parsers.EdgeFileConverter;
import edu.rit.edgeconverter.parsers.FileParser;
import edu.rit.edgeconverter.parsers.JSONFileParser;
import edu.rit.edgeconverter.parsers.XMLFileParser;
import edu.rit.edgeconverter.view.ConverterGUI;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The MenuListener class is responsible for handling events triggered by menu actions, particularly those associated with file operations.
 * It encapsulates the logic for opening, processing, and displaying the contents of selected files within the application's user interface.
 */
public class MenuListener implements EventHandler<ActionEvent> {

  private ConverterGUI gui;
  private ObservableList<String> itemsTable = FXCollections.observableArrayList();
  private ObservableList<String> itemsField = FXCollections.observableArrayList();

  private static final Logger log = LogManager.getLogger("edu.rit");

  private Map<String, ArrayList<Field>> mappedTable = new HashMap<>();

  public MenuListener(ConverterGUI gui) {
    this.gui = gui;
  }

  /**
   * Handles the ActionEvent triggered by menu actions.
   * This method facilitates the file opening process, including file selection, validation, and content processing.
   * @param event The ActionEvent to be handled.
   */
  @Override
  public void handle(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();

    //opening resource file
    fileChooser.setInitialDirectory(
      new File(System.getProperty("user.dir") + "/src/main/resources/resources")
    );

    fileChooser
      .getExtensionFilters()
      .addAll(
        new FileChooser.ExtensionFilter("Edge Files (*.edg)", "*.edg"),
        new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json"),
        new FileChooser.ExtensionFilter("XML Files (*.xml)", "*.xml"),
        new FileChooser.ExtensionFilter("Save Files (*.sav)", "*.sav")
      );
    // Set the title for the file chooser dialog
    fileChooser.setTitle("Opening File");
    log.info("Choosing a file.");

    // Show the file open dialog and get the selected file
    File selectedFile = fileChooser.showOpenDialog(gui.getStage()); // Replace 'yourStage' with the actual stage
    log.info("Selected a file.");

    // The conditional statement evaluates the validity of the selected file based on its extension.
    if (selectedFile != null) {
            String fileName = selectedFile.getName();
            log.info("Selected a file: " + fileName);

            if (fileName.endsWith(".edg")) {
                runningEdgeFile(selectedFile);
            } else if (fileName.endsWith(".json")) {
                log.info("A JSON file was selected: " + fileName);
                runningJsonFile(selectedFile);
            } else if (fileName.endsWith(".xml")) {
                log.info("An XML file was selected: " + fileName);
                runningXmlFile(selectedFile);
            } else {
                log.warn("Selected file is not a valid .edg, .json, or .xml file.");
            }
        } else {
            log.info("File selection was canceled.");
        }
  }
  private void runningXmlFile(File selectedFile) {
    // Instantiate your XMLFileParser
    XMLFileParser xmlParser = new XMLFileParser(selectedFile);

    // Perform your XML parsing (currently dummy implementation)
    xmlParser.parseFile();

    // Get the success message from the parser
    String successMessage = xmlParser.getSuccessMessage();

    // Use the success message to update the GUI
    gui.displayMessageInTableList(successMessage);
    log.info("XML file processing completed with message: " + successMessage);
}


  private void runningJsonFile(File selectedFile) {
    // Instantiate your JSONFileParser
    JSONFileParser jsonParser = new JSONFileParser(selectedFile);

    // Perform your JSON parsing (currently dummy implementation)
    jsonParser.parseFile();

    // Get the success message from the parser
    String successMessage = jsonParser.getSuccessMessage();

    // Use the success message to update the GUI
    gui.displayMessageInTableList(successMessage);
    log.info("JSON file processing completed with message: " + successMessage);
}

  private void runningEdgeFile(File selectedFile) {
    EdgeFileConverter efc = new EdgeFileConverter(selectedFile);
    logEdgeFileStatistics(efc);
    mapTablesToFields(efc);
    Table[] tables = efc.getTables();
    for (Table table : tables) {
      table.makeArrays();
    }
    this.gui.addTablesFields(tables, efc.getFields());
    addListViewTables(efc.getTables());
  }

  private void logEdgeFileStatistics(EdgeFileConverter efc) {
    log.info("Number of tables: " + efc.getTables().length);
    log.info("Number of fields: " + efc.getFields().length);
  }

  // Map tables to fields and store in mappedTable
  private void mapTablesToFields(EdgeFileConverter efc) {
    for (Table table : efc.getTables()) {
      ArrayList<Field> allFields = getFieldsForTable(efc, table);
      mappedTable.put(table.getTableName(), allFields);
    }
  }

  private ArrayList<Field> getFieldsForTable(
    EdgeFileConverter efc,
    Table table
  ) {
    ArrayList<Field> allFields = new ArrayList<>();
    for (Field field : efc.getFields()) {
      if (table.getNumFigure() == field.getTableID()) {
        allFields.add(field);
      }
    }
    return allFields;
  }

  public void addListViewTables(Table[] tables) {
    gui.getListViewTables().getItems().clear(); // Clear previous items
    for (Table table : tables) {
      itemsTable.add(table.getTableName()); // Add the table names
    }
    gui.getListViewTables().setItems(itemsTable);
  }

  /**
   * adds the ListView component with table names extracted from the provided array of Table objects.
   * @param tables An array of Table objects containing table information to be displayed.
   */
  public void addListViewFieldList(Field[] fields) {
    gui.getListViewFieldList().getItems().clear(); // Clear previous items

    for (Field field : fields) {
      itemsField.add(field.getName()); // Add the field names
    }

    gui.getListViewFieldList().setItems(itemsField);
  }

  public Map<String, ArrayList<Field>> getMappedTable() {
    return this.mappedTable;
  }
} // MenuListener out
