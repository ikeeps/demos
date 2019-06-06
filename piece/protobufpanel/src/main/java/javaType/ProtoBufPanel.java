package javaType;

import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Descriptors.FieldDescriptor;

@SuppressWarnings("serial")
public class ProtoBufPanel extends JPanel {
  public ProtoBufPanel(GeneratedMessage message) {
    super(new SpringLayout());
    Map<FieldDescriptor, Object> allFields = message.getAllFields();
	for (Entry<FieldDescriptor, Object> key : allFields.entrySet()) {
      JLabel l = new JLabel(key.getKey().getName() + ":", JLabel.TRAILING);
      add(l);
      JTextField t = new JTextField(10);
      l.setLabelFor(t);
      t.setText(key.getValue().toString());
      add(t);
    }
    
    //Lay out the panel.
    SpringUtilities.makeCompactGrid(this,
                                    allFields.size(), 2, //rows, cols
                                    6, 6,        //initX, initY
                                    6, 6);       //xPad, yPad
  }
}
