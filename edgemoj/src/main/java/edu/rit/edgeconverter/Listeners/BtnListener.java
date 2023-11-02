package edu.rit.edgeconverter.Listeners;

import edu.rit.edgeconverter.model.Field;
import edu.rit.edgeconverter.util.ActionUtils;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A class to handle button click events for setting data types and default values.
 */
public class BtnListener implements ActionListener {

    private Field field;
    private TextField tfVarcharLength;
    private Button setVarcharLengthButton;
    private TextField tfDefaultValue;
    private boolean dataSaved;

    /**
     * Constructor to initialize the BtnListener with necessary UI components and field data.
     *
     * @param field The field data model.
     * @param tfVarcharLength The text field for varchar length input.
     * @param setVarcharLengthButton The button to set varchar length.
     * @param tfDefaultValue The text field for default value input.
     * @param dataSaved A flag indicating if the data is saved.
     */
    public BtnListener(Field field, TextField tfVarcharLength, Button setVarcharLengthButton, TextField tfDefaultValue, boolean dataSaved){
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
