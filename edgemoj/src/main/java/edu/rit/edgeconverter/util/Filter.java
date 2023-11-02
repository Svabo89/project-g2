package edu.rit.edgeconverter.util;

import javax.swing.filechooser.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Filter extends FileFilter {
  private static final Logger log = LogManager.getLogger("edu.rit");

  private Map<String, Filter> filters = new HashMap<>();
  private String description = null;
  private String fullDescription = null;
  private boolean useExtensionsInDescription = true;

  public Filter() {
    log.debug("Filter instance created.");
  }

  public Filter(String extension) {
    this(extension, null);
  }

  public Filter(String extension, String description) {
    this();
    if (extension != null) addExtension(extension);
    if (description != null) setDescription(description);
  }

  public Filter(String[] filters) {
    this(filters, null);
  }

  public Filter(String[] filters, String description) {
    this();
    for (String filter : filters) {
      addExtension(filter);
    }
    if (description != null) setDescription(description);
  }

  public boolean accept(File f) {
    if (f != null) {
      if (f.isDirectory()) {
        log.info("Accepted a directory: {}", f.getAbsolutePath());
        return true;
      }
      String extension = getExtension(f);
      if (extension != null && filters.get(extension) != null) {
        log.info("Accepted a file: {}", f.getName());
        return true;
      }
    }
    log.warn("Rejected file: {}", f != null ? f.getName() : "null");
    return false;
  }

  public String getExtension(File f) {
    if (f != null) {
      String filename = f.getName();
      int i = filename.lastIndexOf('.');
      if (i > 0 && i < filename.length() - 1) {
        return filename.substring(i + 1).toLowerCase();
      }
    }
    return null;
  }

  public void addExtension(String extension) {
    filters.put(extension.toLowerCase(), this);
    fullDescription = null;
    log.debug("Added extension: {}", extension);
  }

  public String getDescription() {
    if (fullDescription == null) {
      StringBuilder sb = new StringBuilder();
      if (description == null || isExtensionListInDescription()) {
        sb.append(description == null ? "(" : description + " (");
        for (String extension : filters.keySet()) {
          sb.append('.').append(extension).append(", ");
        }
        sb.setLength(sb.length() - 2);  // Remove trailing comma and space
        sb.append(')');
        fullDescription = sb.toString();
      } else {
        fullDescription = description;
      }
    }
    return fullDescription;
  }

  public void setExtensionListInDescription(boolean b) {
    useExtensionsInDescription = b;
    fullDescription = null;
    log.debug("Including the list of extensions in the description: {}", b);
  }

  public void setDescription(String description) {
    this.description = description;
    fullDescription = null;
    log.debug("Set description: {}", description);
  }

  public boolean isExtensionListInDescription() {
    log.trace("Verifying if the list of extensions is included in the description: {}", useExtensionsInDescription);
    return useExtensionsInDescription;
  }
}
