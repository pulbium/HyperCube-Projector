package hypercube;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HyperCubeFrame extends JFrame {
	
	private static final long serialVersionUID = -1784095983031052573L;
	
	//slidery
	
	RotSlider xySlider = new RotSlider("Obraca względem płaszczyzny XY");
	RotSlider yzSlider = new RotSlider("Obraca względem płaszczyzny YZ");
	RotSlider xzSlider = new RotSlider("Obraca względem płaszczyzny XZ");
	RotSlider xwSlider = new RotSlider("Obraca względem płaszczyzny XW");
	RotSlider ywSlider = new RotSlider("Obraca względem płaszczyzny YW");
	RotSlider zwSlider = new RotSlider("Obraca względem płaszczyzny ZW");
	
	RotSlider[] sliders = {yzSlider, xzSlider, xySlider, xwSlider, ywSlider, zwSlider};
	String[] sliderNames = {"YZ", "XZ", "XY", "XW", "YW", "ZW"};
	
	//panele
	
	JPanel menuPanel = new JPanel();
	ProjectionPanel projectionPanel = new ProjectionPanel(sliders);
	JPanel bottomPanel = new JPanel();
	JPanel slidersPanel = new JPanel();
	JPanel valuesPanel = new JPanel();
	
	HyperCubeMenuBar menuBar = new HyperCubeMenuBar(projectionPanel);
	
	
	public HyperCubeFrame() {
				
		projectionPanel.sliders[2].setEnabled(true);
		setJMenuBar(menuBar);
		
		
		setLayout(new BorderLayout());
		bottomPanel.setLayout(new BorderLayout());
		slidersPanel.setLayout(new GridLayout(12,1));
		valuesPanel.setLayout(new GridLayout(6,3));
		menuPanel.setLayout(new GridLayout(7,1));
		
				
		menuPanel.add(projectionPanel.twoDButton);
		menuPanel.add(projectionPanel.threeDButton);
		menuPanel.add(projectionPanel.fourDButton);
		menuPanel.add(new JLabel());
		menuPanel.add(projectionPanel.paralellButton);
		menuPanel.add(projectionPanel.perspectiveButton);

		
		for(int i = 0;i<6;i++) {
			slidersPanel.add(new JLabel(sliderNames[i]));
			slidersPanel.add(projectionPanel.sliders[i]);
			valuesPanel.add(sliders[i].playButton);
			valuesPanel.add(sliders[i].textField);
			valuesPanel.add(new JLabel("°"));
		}
		
		bottomPanel.add(slidersPanel,BorderLayout.CENTER);
		bottomPanel.add(valuesPanel,BorderLayout.EAST);
		
		add(projectionPanel,BorderLayout.CENTER);
		add(menuPanel,BorderLayout.WEST);
		add(bottomPanel,BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("HyperCubeProjector");
		setSize(1000, 800);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				HyperCubeFrame frame = new HyperCubeFrame();
				frame.setVisible(true);
				ExecutorService exec = Executors.newSingleThreadExecutor();
				exec.shutdown();
			}
		});
	}
}
