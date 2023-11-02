package edu.rit.edgeconverter.util;

import edu.rit.edgeconverter.model.Field;
import java.awt.Button;
import java.awt.TextField;

public class ActionUtils {

    public static void handleAction(Field field, Button button, TextField tfVarcharLength, Button setVarcharLengthButton, TextField tfDefaultValue) {
        for (DataType dataType : DataType.values()) {
            if (button.getName().equals(dataType.getValue())) {
                field.setDataType(dataType);
                break;
            }
        }
        if (button.getName().equals(DataType.VARCHAR.getValue())) {
            tfVarcharLength.setText(Integer.toString(DataType.VARCHAR.getDefaultLength()));
            setVarcharLengthButton.setEnabled(true);
        } else {
            tfVarcharLength.setText("");
            setVarcharLengthButton.setEnabled(false);
        }
        tfDefaultValue.setText("");
        field.setDefaultValue("");
    }
}
