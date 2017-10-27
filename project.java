//import java.util.Scanner;
//18.82842712474619 alwaysShortest, might? be the best solution without brute forcing
//18.650281539872886 minimizingDistance, not a solution

public class project {
	static double[][] distance = new double[13][13];
	static String[][] board = new String[7][4];
	
	public static void setupTheProblem(){
		for(int i = 0; i < 7; i++) for(int j = 0; j < 4; j++) board[i][j] = "-";
		board[0][3] = "*";
		board[1][3] = "1";
		board[2][2] = "2";
		board[3][2] = "3";
		board[4][2] = "4";
		board[5][3] = "5";
		board[6][3] = "6";
		board[0][1] = "7";
		board[2][1] = "8";
		board[3][1] = "9";
		board[6][1] = "10";
		board[3][0] = "11";
		board[6][0] = "12";
		
		int[] position1 = new int[2];
		int[] position2 = new int[2];
		for(int i = 0; i < 13; i++){
			position1 = xy_position(i);
			for(int j = 0; j < 13; j++){
				position2 = xy_position(j);
				distance[i][j] = Math.sqrt(Math.pow((position1[0] - position2[0]), 2) + Math.pow((position1[1] - position2[1]), 2));
			}
		}
	}
	
	public static int[] xy_position(int input){
		int[] position = new int[2];
		switch(input){
		case 0:
			position[0] = 0;
			position[1] = 3;
			break;
		case 1:
			position[0] = 1;
			position[1] = 3;
			break;
		case 2:
			position[0] = 2;
			position[1] = 2;
			break;
		case 3:
			position[0] = 3;
			position[1] = 2;
			break;
		case 4:
			position[0] = 4;
			position[1] = 2;
			break;
		case 5:
			position[0] = 5;
			position[1] = 3;
			break;
		case 6:
			position[0] = 6;
			position[1] = 3;
			break;
		case 7:
			position[0] = 0;
			position[1] = 1;
			break;
		case 8:
			position[0] = 2;
			position[1] = 1;
			break;
		case 9:
			position[0] = 3;
			position[1] = 1;
			break;
		case 10:
			position[0] = 6;
			position[1] = 1;
			break;
		case 11:
			position[0] = 3;
			position[1] = 0;
			break;
		case 12:
			position[0] = 6;
			position[1] = 0;
			break;
		}
		return position;
	}
	
	public static boolean check(){
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 4; j++){
				if(board[i][j].equals("*") || board[i][j].equals("-"));
				else return false;
			}
		}
		return true;
	}
	
	public static double minRow(double[][] a, int r){
		double minRow = 100;
		for(int i = 0; i < 13; i++) if(a[r][i] < minRow) minRow = a[r][i];
		return minRow;
	}
	
	public static double minCol(double[][] a, int c){
		double minCol = 100;
		for(int i = 0; i < 13; i++) if(a[i][c] < minCol) minCol = a[i][c];
		return minCol;
	}
	
	public static void minimzingDistanceAlgorithm(){
		double[][] floydWarshallDistance = new double[13][13];
		
		double minRow, minCol, maxVal;
		double totalDistance = 0;
		int maxI = -1, maxJ = -1;
		for(int i = 0; i < 13; i++) for(int j = 0; j < 13; j++) floydWarshallDistance[i][j] = distance[i][j];
		for(int i = 0; i < 13; i++) floydWarshallDistance[i][i] = 100;
		
		int count = 0;
		while(count < 13){
			for(int i = 0; i < 13; i++){
				minRow = minRow(floydWarshallDistance, i);
				if(minRow > 10) minRow = 0;
				for(int j = 0; j < 13; j++) floydWarshallDistance[i][j] = floydWarshallDistance[i][j] - minRow;
			}
			for(int i = 0; i < 13; i++){
				minCol = minCol(floydWarshallDistance, i);
				if(minCol > 10) minCol = 0;
				for(int j = 0; j < 13; j++) floydWarshallDistance[j][i] = floydWarshallDistance[j][i] - minCol;
			}
			
			maxVal = -1;
			for(int i = 0; i < 13; i++){
				for(int j = 0; j < 13; j++){
					if(floydWarshallDistance[i][j] == 0){
						minRow = 100;
						minCol = 100;
						for(int k = 0; k < 13; k++) if(minRow > floydWarshallDistance[i][k] && k != j) minRow = floydWarshallDistance[i][k];
						for(int k = 0; k < 13; k++) if(minCol > floydWarshallDistance[k][j] && k != i) minCol = floydWarshallDistance[k][j];
						if(minRow > 10) minRow = 0;
						if(minCol > 10) minCol = 0;
						if(maxVal < minRow + minCol){
							maxI = i;
							maxJ = j;
							maxVal = minRow + minCol;
						}
					}
				}
			}
			totalDistance += distance[maxI][maxJ];
			
			System.out.println(maxI + " " + maxJ);
			for(int i = 0; i < 13; i++){
				floydWarshallDistance[maxI][i] = 100;
				floydWarshallDistance[i][maxJ] = 100;
			}
			floydWarshallDistance[maxJ][maxI] = 100;
			
			/*for(int i = 0; i < 13; i++){
				for(int j = 0; j < 13; j++) System.out.printf("%.2f\t", floydWarshallDistance[i][j]);
				System.out.print("\n");
			}
			System.out.print("\n");*/
			
			count++;
		}
		System.out.print("Total Distance : " + totalDistance);
	}
	
	public static int alwaysShortestAlgorithms(int c){
		double shortestPath = 100;
		int nextStep = -1;
		int[] p = new int[2];
		for(int i = 0; i < 13; i++){
			if(distance[c][i] < shortestPath){
				p = xy_position(i);
				if(!board[p[0]][p[1]].equals("*") && !board[p[0]][p[1]].equals("-")){
					shortestPath = distance[c][i];
					nextStep = i;
				}	
			}
		}
		return nextStep;
	}
	
	public static void main(String[] agrs){
		setupTheProblem();
		
		/*not the solution, just a heuristic*/
		//minimzingDistanceAlgorithm();
		
		//System.out.println(distance[12][10] + distance[11][9] + distance[10][6] + distance[9][3] + distance[8][2] + distance[7][8] + distance[6][5] + distance[5][4] + distance[4][11] + distance[3][1] + distance[2][0] + distance[1][7] + distance[0][12]);
		//System.out.println(distance[0][1] + distance[1][2] + distance[2][3] + distance[3][4] + distance[4][5] + distance[5][6] + distance[6][10] + distance[10][12] + distance[12][11] + distance[11][9] + distance[9][8] + distance[8][7] + distance[7][0]);
		
		//Scanner stdin = new Scanner(System.in);
		int[] p = new int[2];
		int currentStep = 0;
		double totalDistance = 0;
		
		do{
			for(int j = 3; j >= 0; j--){
				for(int i = 0; i < 7; i++){
					System.out.print(board[i][j] + "\t");
				}
				System.out.print("\n");
			}
			//int nextStep = stdin.nextInt();
			int nextStep = alwaysShortestAlgorithms(currentStep);
			System.out.println("Enter the next position you're gonna go: " + nextStep);
			if(nextStep >= 0 && nextStep <= 12){
				p = xy_position(currentStep);
				board[p[0]][p[1]] = "-";
				p = xy_position(nextStep);
				board[p[0]][p[1]] = "*";
				totalDistance += distance[currentStep][nextStep];
				currentStep = nextStep;
			}
			else System.out.println("The position you're gonna go doesn't exist.");
			System.out.println("The distance now is " + totalDistance);
		}while(!check());
		//stdin.close();
		totalDistance += distance[currentStep][0];
		for(int j = 3; j >= 0; j--){
			for(int i = 0; i < 7; i++){
				System.out.print(board[i][j] + "\t");
			}
			System.out.print("\n");
		}
		System.out.println("\nThe total distance is " + totalDistance);
	}
}
