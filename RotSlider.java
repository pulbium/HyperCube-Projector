package hypercube;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RotSlider extends JSlider {
	
	private static final long serialVersionUID = 1L;
	
	double angle;
	JTextField textField = new JTextField("000");
	
	public RotSlider(String toolTip) {
		super(0,360,0);
		setValue(0);
		setEnabled(false);
		addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				angle = (double)getValue()/180*Math.PI;
				textField.setText(String.format("%d", getValue()));
				
			}
		});
		
		setToolTipText(toolTip);
		
		textField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(Integer.parseInt(textField.getText())>360) {
						int n=Integer.parseInt(textField.getText())/360;
						setValue(Integer.parseInt(textField.getText())-n*360);
					}
					else {
						setValue(Integer.parseInt(textField.getText()));
					}
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Ile wynosi k¹t "+textField.getText()+"°?", "Podano z³y k¹t", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
