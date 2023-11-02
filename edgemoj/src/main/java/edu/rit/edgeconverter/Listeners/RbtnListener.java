package edu.rit.edgeconverter.Listeners;

import edu.rit.edgeconverter.model.Field;
import edu.rit.edgeconverter.util.ActionUtils;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RbtnListener implements ActionListener {

  private Field field;
  private TextField tfVarcharLength;
  private Button setVarcharLengthButton;
  private TextField tfDefaultValue;
  private boolean dataSaved;

  public RbtnListener(
    Field field,
    TextField tfVarcharLength,
    Button setVarcharLengthButton,
    TextField tfDefaultValue,
    boolean dataSaved
  ) {
    this.field = field;
    this.tfVarcharLength = tfVarcharLength;
    this.setVarcharLengthButton = setVarcharLengthButton;
    this.tfDefaultValue = tfDefaultValue;
    this.dataSaved = dataSaved;
  }

  public void actionPerformed(ActionEvent e) {
    Button button = (Button) e.getSource();
    ActionUtils.handleAction(field, button, tfVarcharLength, setVarcharLengthButton, tfDefaultValue);
    dataSaved = false;
  }
}
