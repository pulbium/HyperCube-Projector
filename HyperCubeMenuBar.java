package pl.edu.pw.fizyka.pojava.HyperCube;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class HyperCubeMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	JMenu fileMenu = new JMenu(ResourceBundle.getBundle("LanguageBundle",HyperCubeFrame.locale).getString("m1"));
	JMenuItem save = new JMenuItem(ResourceBundle.getBundle("LanguageBundle",HyperCubeFrame.locale).getString("m11"));
	
	JMenu settingsMenu = new JMenu(ResourceBundle.getBundle("LanguageBundle",HyperCubeFrame.locale).getString("m2"));
	JMenuItem changeBG = new JMenuItem(ResourceBundle.getBundle("LanguageBundle",HyperCubeFrame.locale).getString("m21"));
	JMenuItem changeLine = new JMenuItem(ResourceBundle.getBundle("LanguageBundle",HyperCubeFrame.locale).getString("m22"));
	JMenuItem changeThickness = new JMenuItem(ResourceBundle.getBundle("LanguageBundle",HyperCubeFrame.locale).getString("m23"));
	
	JMenu parametersMenu = new JMenu(ResourceBundle.getBundle("LanguageBundle",HyperCubeFrame.locale).getString("m3"));
	JMenuItem changeSize = new JMenuItem(ResourceBundle.getBundle("LanguageBundle",HyperCubeFrame.locale).getString("m31"));
	JMenuItem changeDistance = new JMenuItem(ResourceBundle.getBundle("LanguageBundle",HyperCubeFrame.locale).getString("m32"));
	
	public HyperCubeMenuBar(ProjectionPanel projectionPanel) {
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = jfc.showSaveDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					BufferedImage image = new BufferedImage(projectionPanel.getWidth(),projectionPanel.getHeight(),BufferedImage.TYPE_USHORT_555_RGB);
					Graphics2D g2 = image.createGraphics();
					projectionPanel.paintComponent(g2);
					try {
						ImageIO.write(image,"png", jfc.getSelectedFile());
					} catch(Exception e1){
						e1.printStackTrace();
					}
				}
			}
		});
		
		
		changeBG.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				projectionPanel.bgColor = JColorChooser.showDialog(null, "", null);
			}
		});
				
		changeLine.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				projectionPanel.lineColor = JColorChooser.showDialog(null, "", null);
			}
		});
				
		changeThickness.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				projectionPanel.lineThickness = Integer.parseInt(JOptionPane.showInputDialog("", 1));				 
			}
		});
				
		changeSize.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				projectionPanel.size = Integer.parseInt(JOptionPane.showInputDialog("", 100))/2;
			}
		});
				
		changeDistance.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				projectionPanel.distance = Integer.parseInt(JOptionPane.showInputDialog("", 200));
			}
		});
		
		fileMenu.add(save);
		settingsMenu.add(changeBG);
		settingsMenu.add(changeLine);
		settingsMenu.add(changeThickness);
		parametersMenu.add(changeSize);
		parametersMenu.add(changeDistance);
		
		add(fileMenu);
		add(settingsMenu);
		add(parametersMenu);
	}
}
