package hypercube;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RotSlider extends JSlider {
	
	private static final long serialVersionUID = 1L;
	
	double angle, speed = 0.02;
	JTextField textField = new JTextField("000");
	JToggleButton playButton = new JToggleButton();
	
	public RotSlider(String toolTip, boolean enabled) {
		super(0,360,0);
		setValue(0);
		setEnabled(enabled);
		textField.setEnabled(enabled);
		playButton.setEnabled(enabled);
		
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
						setValue(Integer.parseInt(textField.getText())%360);
					}
					else {
						setValue(Integer.parseInt(textField.getText()));
					}
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Ile wynosi k¹t "+textField.getText()+"°?", "Podano z³y k¹t", JOptionPane.ERROR_MESSAGE);
				}
			}
		});			
		
		playButton.setToolTipText("W³¹cz animacjê");
		
		playButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
					 
                    @Override
                    protected Void doInBackground() throws Exception {
                    	while(playButton.isSelected()) {
	                    	angle += speed;
	                    	RotSlider.this.setValue(RotSlider.this.getValue()+(int)(speed*180/Math.PI));
	                    	
	                    	if(angle*180/Math.PI > 360) {
	                    		RotSlider.this.setValue(0);
	                    		textField.setText("0");
	                    	}
	                    	Thread.sleep(20);
                    	}
						return null;
                    }
               };
               worker.execute();
			}
		});
	}
	
	public RotSlider(String toolTip) {
		this(toolTip, false);
	}
}
