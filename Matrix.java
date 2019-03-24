package hypercube;

public class Matrix { 
	public static double[][] MatMulMat(double[][] m1, double[][] m2){
		int c1=m1[0].length;
		int r1=m1.length;
		int c2=m2[0].length;
		int r2=m2.length;
		double[][] product = new double[r1][c2];
		for(int a = 0; a<r1; a++)
			for(int b = 0; b<c2;b++)
				product[a][b]=0;
		
		if(c1==r2) {
			for(int i = 0; i < r1; i++) {
				for(int j = 0; j < c2; j++) {
					for(int k=0; k < c1; k++)
						product[i][j]+=m1[i][k]*m2[k][j];
				}
			}
		}	
		return product;
	}
	
	public static double[] MatMulVec(double[][] m,double[] v) {
		double [] product = new double[m.length];
		double [][] transV = new double[v.length][1]; 
		for(int i = 0;i<v.length;i++)
			transV[i][0]=v[i];
		double [][] s = MatMulMat(m,transV);	
		for(int j = 0; j<s.length;j++)
			product[j]=s[j][0];
		return product;
	}
	
	static double[][] rotationXY(double angle){
		return new double[][]{
			{Math.cos(angle),-Math.sin(angle),0,0},
			{Math.sin(angle),Math.cos(angle),0,0},
			{0,0,1,0},
			{0,0,0,1}
		};
	}
	
	static double[][] rotationYZ(double angle) {
		return new double[][]{
			{1,0,0,0},
			{0,Math.cos(angle),-Math.sin(angle),0},
			{0,Math.sin(angle),Math.cos(angle),0},
			{0,0,0,1}
		};
	}
	
	static double[][] rotationXZ(double angle) {
		return new double[][]{
			{Math.cos(angle),0,-Math.sin(angle),0},
			{0,1,0,0},
			{Math.sin(angle),0,Math.cos(angle),0},
			{0,0,0,1}
		};
	}
	
	static double[][] rotationXW(double angle) {
		return new double[][]{
			{Math.cos(angle),0,0,-Math.sin(angle)},
			{0,1,0,0},
			{0,0,1,0},
			{Math.sin(angle),0,0,Math.cos(angle)}
		};
	}
	
	static double[][] rotationYW(double angle) {
		return new double[][]{
			{1,0,0,0},
			{0,Math.cos(angle),0,-Math.sin(angle)},
			{0,0,1,0},
			{0,Math.sin(angle),0,Math.cos(angle)}
		};
	}
	
	static double[][] rotationZW(double angle) {
		return new double[][]{
			{1,0,0,0},
			{0,1,0,0},
			{0,0,Math.cos(angle),-Math.sin(angle)},
			{0,0,Math.sin(angle),Math.cos(angle)}
		};
	}
	
	static double[][] projectionFrom4DTo3D(double distance,double w){
		return new double[][] {
			{distance/(distance-w),0,0,0},
			{0,distance/(distance-w),0,0},
			{0,0,distance/(distance-w),0}
		};
	}
	
	static double[][] projectionFrom3DTo2D(double distance,double z){
		return new double[][] {
			{distance/(distance-z),0,0},
			{0,distance/(distance-z),0}
		};
	}
}
