package edu.rit.edgeconverter.parsers;

import edu.rit.edgeconverter.model.Field;
import edu.rit.edgeconverter.model.Table;
import java.io.*;
import java.util.*;

public abstract class FileParser {

      protected int numFields;
    protected int numTables;
    protected ArrayList<?> allFields;
    protected String tableName;
    protected String fieldName;
    protected Table[] tables;
    protected Field[] fields;


  public abstract void parseSaveFile();

  public abstract Field[] getFields();

  public abstract Table[] getTables();

  public abstract boolean isTableDup(String tableName);

  public abstract void openFile(File file);

  public abstract void makeArrays();

  public abstract void parseFile() throws IOException;

  public abstract void resolveConnectors();
}
