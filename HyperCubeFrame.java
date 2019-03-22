package hypercube;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class HyperCubeFrame extends JFrame {
	
	private static final long serialVersionUID = -1784095983031052573L;
	
	ArrayList<Point4D> points = new ArrayList<Point4D>();
	
	JPanel menuPanel = new JPanel();
	JPanel projectionPanel = new JPanel() {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.clearRect(0, 0, getWidth(), getHeight());
			g2.setStroke(new BasicStroke(lineThickness));
			g2.setColor(lineColor);
			g2.translate(getWidth()/2, getHeight()/2);
			
			double[][] rotation = new double[4][4];
			
			if(twoDButton.isSelected()) {
				points.removeAll(points);
				points.add(new Point4D(-size,-size));
				points.add(new Point4D(size,-size));
				points.add(new Point4D(size,size));
				points.add(new Point4D(-size,size));
					
				double[][] rotated = new double[4][4];
				
				for(int i=0;i<4;i++) {
					rotation = Matrix.rotationXY(xySlider.angle);
					
					rotated[i]=Matrix.MatMulVec(rotation,points.get(i).toVec(4));
				}
				
				for(int j=0;j<4;j++) {
					g2.drawLine((int)rotated[j][0], (int)rotated[j][1], (int)rotated[(j+1)%4][0], (int)rotated[(j+1)%4][1]);
				}
			}
			else if(threeDButton.isSelected()) {
				points.removeAll(points);
				points.add(new Point4D(-size,-size,-size));
				points.add(new Point4D(size,-size,-size));
				points.add(new Point4D(size,size,-size));
				points.add(new Point4D(-size,size,-size));
				points.add(new Point4D(-size,-size,size));
				points.add(new Point4D(size,-size,size));
				points.add(new Point4D(size,size,size));
				points.add(new Point4D(-size,size,size));

				double[][] rotated = new double[8][4];
				double[][] projected2D = new double[8][4];
				
				for(int i=0;i<8;i++) {
					rotation = Matrix.MatMulMat(Matrix.MatMulMat(Matrix.rotationXY(xySlider.angle), Matrix.rotationYZ(yzSlider.angle)),	Matrix.rotationXZ(xzSlider.angle));	
	
					rotated[i]=Matrix.MatMulVec(rotation,points.get(i).toVec(4));
					double z = rotated[i][2];
					if(perspectiveButton.isSelected())
						projected2D[i] = Matrix.MatMulVec(Matrix.projectionFrom3DTo2D(200, z), rotated[i]);
					else {
						projected2D[i] = Matrix.MatMulVec(Matrix.projectionFrom3DTo2D(1, 0), rotated[i]);
					}
				}
				
				for(int j=0;j<4;j++) {
					g2.drawLine((int)projected2D[j][0],  (int)projected2D[j][1],  (int)projected2D[(j+1)%4][0],  (int)projected2D[(j+1)%4][1]);
					g2.drawLine((int)projected2D[j+4][0],(int)projected2D[j+4][1],(int)projected2D[(j+1)%4+4][0],(int)projected2D[(j+1)%4+4][1]);
					g2.drawLine((int)projected2D[j][0],  (int)projected2D[j][1],  (int)projected2D[j+4][0],      (int)projected2D[j+4][1]);
				}
			}
			else if(fourDButton.isSelected()) {
				
			}
			repaint();
		}
	};
	JPanel bottomPanel = new JPanel();
	JPanel slidersPanel = new JPanel();
	JPanel valuesPanel = new JPanel();
	
	//textfieldy przy sliderach
	
	JTextField xyValue = new JTextField("000°");
	JTextField yzValue = new JTextField("000°");
	JTextField xzValue = new JTextField("000°");
	JTextField xwValue = new JTextField("000°");
	JTextField ywValue = new JTextField("000°");
	JTextField zwValue = new JTextField("000°");
	
	//guziczki
	
	JRadioButton twoDButton = new JRadioButton("2D");
	JRadioButton threeDButton = new JRadioButton("3D");
	JRadioButton fourDButton = new JRadioButton("4D");
	
	JRadioButton paralellButton = new JRadioButton("Rzut równoleg³y");
	JRadioButton perspectiveButton = new JRadioButton("Rzut perspektywiczny");
	
	//slidery

	RotSlider xySlider = new RotSlider("Obraca wzglêdem p³aszczyzny XY", xyValue);
	RotSlider yzSlider = new RotSlider("Obraca wzglêdem p³aszczyzny YZ", yzValue);
	RotSlider xzSlider = new RotSlider("Obraca wzglêdem p³aszczyzny XZ", xzValue);
	RotSlider xwSlider = new RotSlider("Obraca wzglêdem p³aszczyzny XW", xwValue);
	RotSlider ywSlider = new RotSlider("Obraca wzglêdem p³aszczyzny YW", ywValue);
	RotSlider zwSlider = new RotSlider("Obraca wzglêdem p³aszczyzny ZW", zwValue);
	
	//menu
	
	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu = new JMenu("Plik");
	JMenuItem save=new JMenuItem("Zapisz obraz");
	JMenuItem load=new JMenuItem("Dodaj obiekt");
	JMenu colorMenu = new JMenu("Obraz");
	JMenuItem changeBG = new JMenuItem("Zmieñ kolor t³a");
	JMenuItem changeLine = new JMenuItem("Zmieñ kolor linii");
	JMenuItem changeThickness = new JMenuItem("Zmieñ gruboœæ linii");
	JMenu optionsMenu = new JMenu("Opcje");
	JMenu change = new JMenu("Ustaw...");
	JMenuItem changeSize = new JMenuItem("Rozmiar hiperszeœcianu");
	JMenuItem changeDistance = new JMenuItem("Odleg³oœæ obserwatora");
	
	Color lineColor = Color.black;
	int lineThickness=2, size=50, distance=0;
	
	public HyperCubeFrame() {
				
		xySlider.setEnabled(true);
		setJMenuBar(menuBar);
		
		//dzialanie menu
		
		changeBG.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				projectionPanel.setBackground(JColorChooser.showDialog(null, "Wybierz kolor", null));
			}
		});
		
		changeLine.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				lineColor = JColorChooser.showDialog(null, "Wybierz kolor", null);
			}
		});
		
		changeThickness.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				lineThickness = Integer.parseInt(JOptionPane.showInputDialog("Podaj gruboœæ linii", 1));
			 
			}
		});
		
		changeSize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				size = Integer.parseInt(JOptionPane.showInputDialog("Podaj d³ugoœæ krawêdzi:", 100));
			}
		});
		
		changeDistance.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				distance = Integer.parseInt(JOptionPane.showInputDialog("Podaj odleg³oœæ obserwatora:", 2));
			}
		});
		
		fileMenu.add(save);
		fileMenu.add(load);
		colorMenu.add(changeBG);
		colorMenu.add(changeLine);
		colorMenu.add(changeThickness);
		optionsMenu.add(change);
		change.add(changeSize);
		change.add(changeDistance);
		
		menuBar.add(fileMenu);
		menuBar.add(colorMenu);
		menuBar.add(optionsMenu);
		
		
		
		setLayout(new BorderLayout());
		bottomPanel.setLayout(new BorderLayout());
		slidersPanel.setLayout(new GridLayout(12,1));
		valuesPanel.setLayout(new GridLayout(6,1));
		menuPanel.setLayout(new GridLayout(7,1));
		
		//dzialanie guziczkow
		
		twoDButton.setSelected(true);
		twoDButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				yzSlider.setValue(0);
				xzSlider.setValue(0);
				xwSlider.setValue(0);
				ywSlider.setValue(0);
				zwSlider.setValue(0);
				
				paralellButton.setSelected(true);
				perspectiveButton.setEnabled(false);
				
				yzSlider.setEnabled(false);
				yzValue.setText("000°");
				xzSlider.setEnabled(false);
				xzValue.setText("000°");
				xwSlider.setEnabled(false);
				xwValue.setText("000°");
				ywSlider.setEnabled(false);
				ywValue.setText("000°");
				zwSlider.setEnabled(false);
				zwValue.setText("000°");
			}
		});
		
		threeDButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				xwSlider.setValue(0);
				ywSlider.setValue(0);
				zwSlider.setValue(0);
				
				perspectiveButton.setEnabled(true);
				
				yzSlider.setEnabled(true);
				xzSlider.setEnabled(true);
				xwSlider.setEnabled(false);
				xwValue.setText("000°");
				ywSlider.setEnabled(false);
				ywValue.setText("000°");
				zwSlider.setEnabled(false);
				zwValue.setText("000°");
			}
		});
		
		fourDButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				perspectiveButton.setEnabled(false);
				
				yzSlider.setEnabled(true);
				xzSlider.setEnabled(true);
				xwSlider.setEnabled(true);
				ywSlider.setEnabled(true);
				zwSlider.setEnabled(true);
			}
		});
		
		fourDButton.setToolTipText("WCHODZIMY W CZWARTY WYMIAR!!!");
		
		
		ButtonGroup dimensionButtons = new ButtonGroup();
		dimensionButtons.add(twoDButton);
		dimensionButtons.add(threeDButton);
		dimensionButtons.add(fourDButton);
		
		paralellButton.setSelected(true);
		
		ButtonGroup projectionButtons = new ButtonGroup();
		projectionButtons.add(perspectiveButton);
		perspectiveButton.setToolTipText("To, co jest bli¿ej, jest wiêksze, a to, co dalej, mniejsze");
		perspectiveButton.setEnabled(false);
		projectionButtons.add(paralellButton);
		paralellButton.setToolTipText("Wszystkie odleg³oœci s¹ sobie równe niezale¿nie od odleg³oœci od obserwatora");
		
		menuPanel.add(twoDButton);
		menuPanel.add(threeDButton);
		menuPanel.add(fourDButton);
		menuPanel.add(new JLabel());
		menuPanel.add(paralellButton);
		menuPanel.add(perspectiveButton);
		

				
		slidersPanel.add(new JLabel("XY"));
		slidersPanel.add(xySlider);
		slidersPanel.add(new JLabel("YZ"));
		slidersPanel.add(yzSlider);
		slidersPanel.add(new JLabel("XZ"));
		slidersPanel.add(xzSlider);
		slidersPanel.add(new JLabel("XW"));
		slidersPanel.add(xwSlider);
		slidersPanel.add(new JLabel("YW"));
		slidersPanel.add(ywSlider);
		slidersPanel.add(new JLabel("ZW"));
		slidersPanel.add(zwSlider);
				
		valuesPanel.add(xyValue);
		valuesPanel.add(yzValue);
		valuesPanel.add(xzValue);
		valuesPanel.add(xwValue);
		valuesPanel.add(ywValue);
		valuesPanel.add(zwValue);
		
		bottomPanel.add(slidersPanel,BorderLayout.CENTER);
		bottomPanel.add(valuesPanel,BorderLayout.EAST);
		
		add(projectionPanel,BorderLayout.CENTER);
		add(menuPanel,BorderLayout.WEST);
		add(bottomPanel,BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("HyperCubeProjector");
		setSize(800, 600);
	}
	
	
	
	public static void main(String[] args) {
		HyperCubeFrame frame = new HyperCubeFrame();
		frame.setVisible(true);
	}
}
