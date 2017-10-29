import java.util.Arrays;
import java.util.LinkedList;

//18.82842712474619 alwaysShortest, might? be the best solution without brute forcing
//18.650281539872886 minimizingDistance, not a solution

public class nearestNeighbour {
	static double[][] distance = new double[13][13];
	static String[][] board = new String[7][4];
	static String added = "0,1,2,3,4,5,6,7,8,9,10,11,12,";
	
	public static void setupTheProblem(){
		for(int i = 0; i < 7; i++) for(int j = 0; j < 4; j++) board[i][j] = "-";
		board[0][3] = "A";
		board[1][3] = "B";
		board[2][2] = "C";
		board[3][2] = "D";
		board[4][2] = "E";
		board[5][3] = "F";
		board[6][3] = "G";
		board[0][1] = "H";
		board[2][1] = "I";
		board[3][1] = "J";
		board[6][1] = "K";
		board[3][0] = "L";
		board[6][0] = "M";
		
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
		
		int[] p = new int[2];
		int currentStep;
		int shortestStart = -1;
		double totalDistance = 0, shortestDistance = 999999999;
		LinkedList<String> path;
		LinkedList<String> shortestPath = new LinkedList<String>();
		
		for(int start = 0; start < 13; start++){
			setupTheProblem();
			currentStep = start;
			totalDistance = 0;
			path = new LinkedList<String>();
			p = xy_position(currentStep);
			path.add(board[p[0]][p[1]]);
			board[p[0]][p[1]] = "*";
			do{
				int nextStep = alwaysShortestAlgorithms(currentStep);
				if(nextStep >= 0 && nextStep <= 12){
					p = xy_position(currentStep);
					board[p[0]][p[1]] = "-";
					p = xy_position(nextStep);
					path.add(board[p[0]][p[1]]);
					board[p[0]][p[1]] = "*";
					totalDistance += distance[currentStep][nextStep];
					currentStep = nextStep;
				}
			}while(!check());
			totalDistance += distance[currentStep][start];
			System.out.printf("The distance of the path starts at point %c is %g", ('A' + start), totalDistance);
			System.out.println("");
			if(totalDistance < shortestDistance){
				shortestPath = path;
				shortestStart = start;
				shortestDistance = totalDistance;
			}
		}
		System.out.println("\nThe path start at " + (char)('A' + shortestStart) + " : " + Arrays.toString(shortestPath.toArray()));
		System.out.println("The shortest distance of all the distances is " + shortestDistance);
		
		/*setupTheProblem();
		currentStep = shortestStart;
		totalDistance = 0;
		System.out.printf("\nPath of %c the shortest distance", ('A' + shortestStart));
		System.out.println("");
		for(int j = 3; j >= 0; j--){
			for(int i = 0; i < 7; i++){
				System.out.print(board[i][j] + "\t");
			}
			System.out.print("\n");
		}
		System.out.println("");
		do{
			int nextStep = alwaysShortestAlgorithms(currentStep);
			System.out.println("The next position you need to go is: "+ nextStep);
			if(nextStep >= 0 && nextStep <= 12){
				p = xy_position(currentStep);
				board[p[0]][p[1]] = "-";
				p = xy_position(nextStep);
				board[p[0]][p[1]] = "*";
				totalDistance += distance[currentStep][nextStep];
				currentStep = nextStep;
			}
			for(int j = 3; j >= 0; j--){
				for(int i = 0; i < 7; i++){
					System.out.print(board[i][j] + "\t");
				}
				System.out.print("\n");
			}
			System.out.println("The distance now is " + totalDistance);
			System.out.println("");
		}while(!check());
		totalDistance += distance[currentStep][shortestStart];
		System.out.println("And back to the original point\nThe shortest distance is " + totalDistance);*/
	}
}
