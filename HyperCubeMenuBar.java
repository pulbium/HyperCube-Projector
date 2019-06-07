package hypercube;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

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

	JMenu fileMenu = new JMenu("Plik");
	JMenuItem save = new JMenuItem("Zapisz obraz");
	
	JMenu colorMenu = new JMenu("Obraz");
	JMenuItem changeBG = new JMenuItem("Zmieñ kolor t³a");
	JMenuItem changeLine = new JMenuItem("Zmieñ kolor linii");
	JMenuItem changeThickness = new JMenuItem("Zmieñ gruboœæ linii");
	
	JMenu optionsMenu = new JMenu("Parametry");
	JMenuItem changeSize = new JMenuItem("Rozmiar hiperszeœcianu");
	JMenuItem changeDistance = new JMenuItem("Odleg³oœæ obserwatora");

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
				projectionPanel.bgColor = JColorChooser.showDialog(null, "Wybierz kolor", null);
			}
		});
				
		changeLine.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				projectionPanel.lineColor = JColorChooser.showDialog(null, "Wybierz kolor", null);
			}
		});
				
		changeThickness.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				projectionPanel.lineThickness = Integer.parseInt(JOptionPane.showInputDialog("Podaj gruboœæ linii", 1));				 
			}
		});
				
		changeSize.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				projectionPanel.size = Integer.parseInt(JOptionPane.showInputDialog("Podaj d³ugoœæ krawêdzi:", 100))/2;
			}
		});
				
		changeDistance.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				projectionPanel.distance = Integer.parseInt(JOptionPane.showInputDialog("Podaj odleg³oœæ obserwatora:", 200));
			}
		});
		
		fileMenu.add(save);
		colorMenu.add(changeBG);
		colorMenu.add(changeLine);
		colorMenu.add(changeThickness);
		optionsMenu.add(changeSize);
		optionsMenu.add(changeDistance);
		
		add(fileMenu);
		add(colorMenu);
		add(optionsMenu);
	}
}
