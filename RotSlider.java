package hypercube;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RotSlider extends JSlider {
	
	double angle;
	
	public RotSlider(String toolTip, JTextField textField) {
		super(0,360,0);
		setValue(0);
		setEnabled(false);
		addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				angle = (double)getValue()/180*Math.PI;
				textField.setText(String.format("%d", getValue())+"°");
			}
		});
		
		setToolTipText(toolTip);
		
		textField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setValue(Integer.parseInt(textField.getText()));
				
			}
		});
	}
}
