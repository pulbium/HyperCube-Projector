package hypercube;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;
public class ProjectionPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	ArrayList<Point4D> points = new ArrayList<Point4D>();
	Color lineColor = Color.black, bgColor = Color.white;
	int lineThickness = 2, distance = 200, size = 50;
	RotSlider[] sliders;
	JRadioButton twoDButton = new JRadioButton("2D");
	JRadioButton threeDButton = new JRadioButton("3D");
	JRadioButton fourDButton = new JRadioButton("4D");
	BufferedImage buffer;
	
	JRadioButton perspectiveButton = new JRadioButton("Rzut perspektywiczny");
	JRadioButton paralellButton = new JRadioButton("Rzut równoleg³y");
	
	
	public ProjectionPanel(RotSlider[] sliders) {
		super();
		this.sliders=sliders;
		twoDButton.setSelected(true);
		twoDButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				paralellButton.setSelected(true);
				perspectiveButton.setEnabled(false);
				
				for(int i = 0;i!=2 && i<6;i++) {
					sliders[i].setValue(0);
					sliders[i].setEnabled(false);
					sliders[i].playButton.setEnabled(false);
					sliders[i].textField.setText("000");
				}
			}
		});
		
		threeDButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				perspectiveButton.setEnabled(true);
				
				sliders[0].setEnabled(true);
				sliders[0].playButton.setEnabled(true);
				sliders[1].setEnabled(true);
				sliders[1].playButton.setEnabled(true);
				sliders[2].setEnabled(true);
				sliders[2].playButton.setEnabled(true);
				for(int i = 3;i<6;i++) {
					sliders[i].setValue(0);
					sliders[i].setEnabled(false);
					sliders[i].playButton.setEnabled(false);
					sliders[i].textField.setText("000");
				}
				
			}
		});
		
		fourDButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				perspectiveButton.setEnabled(true);
				for(int i = 0;i<6;i++) {
					sliders[i].setEnabled(true);
					sliders[i].playButton.setEnabled(true);
				}
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

	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(bgColor);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setStroke(new BasicStroke(lineThickness));
		g2.setColor(lineColor);
		g2.translate(getWidth()/2, getHeight()/2);
		
		double[][] rotation = new double[4][4];
		rotation = 
				Matrix.MatMulMat(
					Matrix.MatMulMat(
						Matrix.MatMulMat(
							Matrix.MatMulMat(
								Matrix.MatMulMat(
									Matrix.rotationMatrix(0,1,sliders[2].angle), 
									Matrix.rotationMatrix(1,2,sliders[0].angle)
												), 
								Matrix.rotationMatrix(0,2,sliders[1].angle)
										), 
							Matrix.rotationMatrix(0,3,sliders[3].angle)
								), 
						Matrix.rotationMatrix(1,3,sliders[4].angle)
						), 
					Matrix.rotationMatrix(2,3,sliders[5].angle)
				);
			
		if(twoDButton.isSelected()) {
			points.removeAll(points);
			points.add(new Point4D(-size,-size));
			points.add(new Point4D(size,-size));
			points.add(new Point4D(size,size));
			points.add(new Point4D(-size,size));
				
			double[][] rotated = new double[4][4];
			
			for(int i=0;i<4;i++) {				
				rotated[i]=Matrix.MatMulVec(rotation,points.get(i).toVec());
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
			double[][] projected2D = new double[8][2];
			
			for(int i=0;i<8;i++) {
				
				rotated[i]=Matrix.MatMulVec(rotation,points.get(i).toVec());
				double z = rotated[i][2];
				if(perspectiveButton.isSelected())
					projected2D[i] = Matrix.MatMulVec(Matrix.projectionFrom4DTo2D(distance, z), rotated[i]);
				else {
					projected2D[i][0] = rotated[i][0];
					projected2D[i][1] = rotated[i][1];
				}
			}
			
			for(int j=0;j<4;j++) {
				g2.drawLine((int)projected2D[j][0],  (int)projected2D[j][1],  (int)projected2D[(j+1)%4][0],  (int)projected2D[(j+1)%4][1]);
				g2.drawLine((int)projected2D[j+4][0],(int)projected2D[j+4][1],(int)projected2D[(j+1)%4+4][0],(int)projected2D[(j+1)%4+4][1]);
				g2.drawLine((int)projected2D[j][0],  (int)projected2D[j][1],  (int)projected2D[j+4][0],      (int)projected2D[j+4][1]);
			}
		}
		else if(fourDButton.isSelected()) {
			points.removeAll(points);
			points.add(new Point4D(-size,-size,-size,size));
			points.add(new Point4D(size,-size,-size,size));
			points.add(new Point4D(size,size,-size,size));
			points.add(new Point4D(-size,size,-size,size));
			points.add(new Point4D(-size,-size,size,size));
			points.add(new Point4D(size,-size,size,size));
			points.add(new Point4D(size,size,size,size));
			points.add(new Point4D(-size,size,size,size));

			points.add(new Point4D(-size,-size,-size,-size));
			points.add(new Point4D(size,-size,-size,-size));
			points.add(new Point4D(size,size,-size,-size));
			points.add(new Point4D(-size,size,-size,-size));
			points.add(new Point4D(-size,-size,size,-size));
			points.add(new Point4D(size,-size,size,-size));
			points.add(new Point4D(size,size,size,-size));
			points.add(new Point4D(-size,size,size,-size));

			double[][] rotated = new double[16][4];
			double[][] projected3D = new double[16][3];
			double[][] projected2D = new double[16][2];
			
			
			for(int i = 0;i<16;i++) {
				rotated[i] = Matrix.MatMulVec(rotation, points.get(i).toVec());
				if(perspectiveButton.isSelected()) {
					double w = rotated[i][3];
					projected3D[i] = Matrix.MatMulVec(Matrix.projectionFrom4DTo3D(distance, w), rotated[i]);
					double z = projected3D[i][2];
					projected2D[i] = Matrix.MatMulVec(Matrix.projectionFrom3DTo2D(distance, z), projected3D[i]);
				}
				else {
					projected2D[i][0] = rotated[i][0];
					projected2D[i][1] = rotated[i][1];
					
				}
			}
			
			for(int j = 0;j<4;j++) {
				
				g2.drawLine((int)projected2D[j][0],  (int)projected2D[j][1],  (int)projected2D[(j+1)%4][0],  (int)projected2D[(j+1)%4][1]);
				g2.drawLine((int)projected2D[j+4][0],(int)projected2D[j+4][1],(int)projected2D[(j+1)%4+4][0],(int)projected2D[(j+1)%4+4][1]);
				g2.drawLine((int)projected2D[j][0],  (int)projected2D[j][1],  (int)projected2D[j+4][0],      (int)projected2D[j+4][1]);

				g2.drawLine((int)projected2D[j+8][0],  (int)projected2D[j+8][1],  (int)projected2D[(j+1)%4+8][0],  (int)projected2D[(j+1)%4+8][1]);
				g2.drawLine((int)projected2D[j+4+8][0],(int)projected2D[j+4+8][1],(int)projected2D[(j+1)%4+4+8][0],(int)projected2D[(j+1)%4+4+8][1]);
				g2.drawLine((int)projected2D[j+8][0],  (int)projected2D[j+8][1],  (int)projected2D[j+4+8][0],      (int)projected2D[j+4+8][1]);
				
				g2.drawLine((int)projected2D[j][0],  (int)projected2D[j][1],  (int)projected2D[j+8][0], (int)projected2D[j+8][1]);
				g2.drawLine((int)projected2D[j+4][0],(int)projected2D[j+4][1],(int)projected2D[j+12][0],(int)projected2D[j+12][1]);

			}
		}
		repaint();
	}
}
