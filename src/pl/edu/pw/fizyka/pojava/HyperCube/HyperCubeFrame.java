package pl.edu.pw.fizyka.pojava.HyperCube;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;


public class HyperCubeFrame extends JFrame {
	
	private static final long serialVersionUID = -1784095983031052573L;
	
	//internacjonalizacja
	
	static Locale[] supportedLocales = {
		    new Locale("pl"),
		    new Locale("en")
		};
	static Locale locale = supportedLocales[1];
	ResourceBundle lang = ResourceBundle.getBundle("LanguageBundle",locale);
	
	//slidery
	
	RotSlider xzSlider = new RotSlider(lang.getString("sxz"));
	RotSlider yzSlider = new RotSlider(lang.getString("syz"));
	RotSlider xySlider = new RotSlider(lang.getString("sxy"),true);
	RotSlider xwSlider = new RotSlider(lang.getString("sxw"));
	RotSlider ywSlider = new RotSlider(lang.getString("syw"));
	RotSlider zwSlider = new RotSlider(lang.getString("szw"));
	
	RotSlider[] sliders = {yzSlider, xzSlider, xySlider, xwSlider, ywSlider, zwSlider};
	String[] sliderNames = {"YZ", "XZ", "XY", "XW", "YW", "ZW"};
	
	//panele
	
	JPanel uselessPanel = new JPanel();
	JPanel menuPanel = new JPanel();
	ProjectionPanel projectionPanel = new ProjectionPanel(sliders);
	JPanel bottomPanel = new JPanel();
	JPanel slidersPanel = new JPanel();
	JPanel valuesPanel = new JPanel();
	
	//menu
	
	HyperCubeMenuBar menuBar = new HyperCubeMenuBar(projectionPanel);
	
	public HyperCubeFrame() {

		
		JMenuItem changeLanguage = new JMenuItem(lang.getString("m24"));
		changeLanguage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(locale.equals(supportedLocales[0]))
					locale=supportedLocales[1];
				else
					locale=supportedLocales[0];
			
				lang = ResourceBundle.getBundle("LanguageBundle",locale);
				
				xzSlider.setToolTipText(lang.getString("sxz"));
				xySlider.setToolTipText(lang.getString("sxy"));
				yzSlider.setToolTipText(lang.getString("syz"));
				xwSlider.setToolTipText(lang.getString("sxw"));
				zwSlider.setToolTipText(lang.getString("szw"));
				ywSlider.setToolTipText(lang.getString("syw"));
				
				for(int i = 0;i<6;i++)
					sliders[i].playButton.setToolTipText(lang.getString("ptip"));
				
				menuBar.fileMenu.setText(lang.getString("m1"));
				menuBar.save.setText(lang.getString("m11"));
				menuBar.settingsMenu.setText(lang.getString("m2"));
				menuBar.changeBG.setText(lang.getString("m21"));
				menuBar.changeLine.setText(lang.getString("m22"));
				menuBar.changeThickness.setText(lang.getString("m23"));
				changeLanguage.setText(lang.getString("m24"));
				menuBar.parametersMenu.setText(lang.getString("m3"));
				menuBar.changeSize.setText(lang.getString("m31"));
				menuBar.changeDistance.setText(lang.getString("m32"));
				menuBar.settingsMenu.setText(lang.getString("m2"));
				menuBar.parametersMenu.setText(lang.getString("m3"));
				menuBar.changeSize.setText(lang.getString("m31"));
				menuBar.changeDistance.setText(lang.getString("m32"));
				projectionPanel.perspectiveButton.setText(lang.getString("p1"));
				projectionPanel.paralellButton.setText(lang.getString("p2"));
				projectionPanel.fourDButton.setToolTipText(lang.getString("p3"));
				projectionPanel.perspectiveButton.setToolTipText(lang.getString("p4"));
				projectionPanel.paralellButton.setToolTipText(lang.getString("p5"));
				
			}
		});
		
		menuBar.settingsMenu.add(changeLanguage);
		setJMenuBar(menuBar);
		
		
		setLayout(new BorderLayout());
		bottomPanel.setLayout(new BorderLayout());
		slidersPanel.setLayout(new GridLayout(12,1));
		valuesPanel.setLayout(new GridLayout(6,3));
		menuPanel.setLayout(new GridLayout(8,1));
		
				
		menuPanel.add(projectionPanel.twoDButton);
		menuPanel.add(projectionPanel.threeDButton);
		menuPanel.add(projectionPanel.fourDButton);
		menuPanel.add(new JLabel());
		menuPanel.add(projectionPanel.paralellButton);
		menuPanel.add(projectionPanel.perspectiveButton);
		menuPanel.add(new JLabel());
		menuPanel.add(projectionPanel.resetButton);

		
		for(int i = 0;i<6;i++) {
			slidersPanel.add(new JLabel(sliderNames[i]));
			slidersPanel.add(projectionPanel.sliders[i]);
			valuesPanel.add(sliders[i].playButton);
			valuesPanel.add(sliders[i].textField);
			valuesPanel.add(new JLabel("°"));
		}
		
		bottomPanel.add(slidersPanel,BorderLayout.CENTER);
		bottomPanel.add(valuesPanel,BorderLayout.EAST);
		
		
		uselessPanel.setBackground(null);
		add(uselessPanel,BorderLayout.EAST);		
		add(projectionPanel,BorderLayout.CENTER);
		add(menuPanel,BorderLayout.WEST);
		add(bottomPanel,BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("HyperCubeProjector");
		setSize(1024, 768);
	}
	
	public static void main(String[] args) {
				HyperCubeFrame frame = new HyperCubeFrame();
				frame.setVisible(true);
	}
}
