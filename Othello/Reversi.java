import java.lang.*;
import java.util.*;

public class Reversi{//1w_2b

	static int[][] table = new int[10][10];

    public Reversi(){
    }
 
   	public static void tableInitialization(int c){
   		for (int i = 0; i < table.length; i++){
   			table[0][i] = -1;
   			table[table.length-1][i] = -1;
   			table[i][0] = -1;
   			table[i][table[0].length-1] = -1;

   		}
   		switch (c){
   			case 0:
   				table[4][4] = 1;
   				table[5][5] = 1;
   				table[4][5] = 2;
   				table[5][4] = 2;
   				break;
   			case 1:	
   				table[4][4] = 2;
   				table[5][5] = 2;
   				table[4][5] = 1;
   				table[5][4] = 1;
   				break;
   		}
    }

    public static boolean checkL(int c, int tmpX, int tmpY){

    	int cnt = 0;
    	if (c == 1){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX][tmpY-i] == 2)
    				cnt++;
    			else if (table[tmpX][tmpY-i] == 1){
    				if(cnt > 0)
		    			return true;
		    		else
		    			return false;
    			}
    			else
    				return false;
    		}
    	}
    	else if(c == 2){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX][tmpY-i] == 1)
    				cnt++;
    			else if (table[tmpX][tmpY-i] == 2){
    				if(cnt > 0)
		    			return true;
		    		else
		    			return false;
    			}
    			else
    				return false;
    		}
    	}
    	return true;
    }

    public static boolean checkLT(int c, int tmpX, int tmpY){

    	int cnt = 0;
    	if (c == 1){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX-i][tmpY-i] == 2)
    				cnt++;
    			else if (table[tmpX-i][tmpY-i] == 1){
    				if(cnt > 0)
		    			return true;
		    		else
		    			return false;
    			}
    			else
    				return false;
    		}
    	}
    	else if(c == 2){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX-i][tmpY-i] == 1)
    				cnt++;
    			else if (table[tmpX-i][tmpY-i] == 2){
    				if(cnt > 0)
		    			return true;
		    		else
		    			return false;
    			}
    			else
    				return false;
    		}
    	}
    	return true;
    }

   	public static boolean checkT(int c, int tmpX, int tmpY){

    	int cnt = 0;
    	if (c == 1){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX-i][tmpY] == 2)
    				cnt++;
    			else if (table[tmpX-i][tmpY] == 1){
    				if(cnt > 0)
		    			return true;
		    		else
		    			return false;
    			}
    			else
    				return false;
    		}
    	}
    	else if(c == 2){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX-i][tmpY] == 1)
    				cnt++;
    			else if (table[tmpX-i][tmpY] == 2){
    				if(cnt > 0)
		    			return true;
		    		else
		    			return false;
    			}
    			else
    				return false;
    		}
    	}
    	return true;
    }

   	public static boolean checkRT(int c, int tmpX, int tmpY){
    	int cnt = 0;
    	if (c == 1){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX-i][tmpY+i] == 2)
    				cnt++;
    			else if (table[tmpX-i][tmpY+i] == 1){
    				if(cnt > 0)
		    			return true;
		    		else
		    			return false;
    			}
    			else
    				return false;
    		}
    	}
    	else if(c == 2){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX-i][tmpY+i] == 1)
    				cnt++;
    			else if (table[tmpX-i][tmpY+i] == 2){
    				if(cnt > 0)
		    			return true;
		    		else
		    			return false;
    			}
    			else
    				return false;
    		}
    	}
    	return true;
    }

 	public static boolean checkR(int c, int tmpX, int tmpY){
    	int cnt = 0;
    	if (c == 1){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX][tmpY+i] == 2)
    				cnt++;
    			else if (table[tmpX][tmpY+i] == 1){
    				if(cnt > 0)
		    			return true;
		    		else
		    			return false;
    			}
    			else
    				return false;
    		}
    	}
    	else if(c == 2){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX][tmpY+i] == 1)
    				cnt++;
    			else if (table[tmpX][tmpY+i] == 2){
    				if(cnt > 0)
		    			return true;
		    		else
		    			return false;
    			}
    			else
    				return false;
    		}
    	}
    	return true;
    }

 	public static boolean checkRB(int c, int tmpX, int tmpY){
    	int cnt = 0;
    	if (c == 1){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX+i][tmpY+i] == 2)
    				cnt++;
    			else if (table[tmpX+i][tmpY+i] == 1){
    				if(cnt > 0)
		    			return true;
		    		else
		    			return false;
    			}
    			else
    				return false;
    		}
    	}
    	else if(c == 2){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX+i][tmpY+i] == 1)
    				cnt++;
    			else if (table[tmpX+i][tmpY+i] == 2){
    				if(cnt > 0)
		    			return true;
		    		else
		    			return false;
    			}
    			else
    				return false;
    		}
    	}
    	return true;
    }

 	public static boolean checkB(int c, int tmpX, int tmpY){
    	int cnt = 0;
    	if (c == 1){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX+i][tmpY] == 2)
    				cnt++;
    			else if (table[tmpX+i][tmpY] == 1){
    				if(cnt > 0)
		    			return true;
		    		else
		    			return false;
    			}
    			else
    				return false;
    		}
    	}
    	else if(c == 2){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX+i][tmpY] == 1)
    				cnt++;
    			else if (table[tmpX+i][tmpY] == 2){
    				if(cnt > 0)
		    			return true;
		    		else
		    			return false;
    			}
    			else
    				return false;
    		}
    	}
    	return true;
    }

 	public static boolean checkLB(int c, int tmpX, int tmpY){
    	int cnt = 0;
    	if (c == 1){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX+i][tmpY-i] == 2)
    				cnt++;
    			else if (table[tmpX+i][tmpY-i] == 1){
    				if(cnt > 0)
		    			return true;
		    		else
		    			return false;
    			}
    			else
    				return false;
    		}
    	}
    	else if(c == 2){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX+i][tmpY-i] == 1)
    				cnt++;
    			else if (table[tmpX+i][tmpY-i] == 2){
    				if(cnt > 0)
		    			return true;
		    		else
		    			return false;
    			}
    			else
    				return false;
    		}
    	}
    	return true;
    }

    public static void eatChessL(int c, int tmpX, int tmpY){

    	if (c == 1){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX][tmpY-i] == 1)
    				break;
    			else
    				table[tmpX][tmpY-i] = 1;
    		}
    	}
    	else if (c == 2){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX][tmpY-i] == 2)
    				break;
    			else
    				table[tmpX][tmpY-i] = 2;
    		}
    	}
    }

    public static void eatChessLT(int c, int tmpX, int tmpY){

    	if (c == 1){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX-i][tmpY-i] == 1)
    				break;
    			else
    				table[tmpX-i][tmpY-i] = 1;
    		}
    	}
    	else if (c == 2){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX-i][tmpY-i] == 2)
    				break;
    			else
    				table[tmpX-i][tmpY-i] = 2;
    		}
    	}
    }

    public static void eatChessT(int c, int tmpX, int tmpY){

    	if (c == 1){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX-i][tmpY] == 1)
    				break;
    			else
    				table[tmpX-i][tmpY] = 1;
    		}
    	}
    	else if (c == 2){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX-i][tmpY] == 2)
    				break;
    			else
    				table[tmpX-i][tmpY] = 2;
    		}
    	}
    }

    public static void eatChessRT(int c, int tmpX, int tmpY){

    	if (c == 1){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX-i][tmpY+i] == 1)
    				break;
    			else
    				table[tmpX-i][tmpY+i] = 1;
    		}
    	}
    	else if (c == 2){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX-i][tmpY+i] == 2)
    				break;
    			else
    				table[tmpX-i][tmpY+i] = 2;
    		}
    	}
    }

    public static void eatChessR(int c, int tmpX, int tmpY){

    	if (c == 1){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX][tmpY+i] == 1)
    				break;
    			else
    				table[tmpX][tmpY+i] = 1;
    		}
    	}
    	else if (c == 2){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX][tmpY+i] == 2)
    				break;
    			else
    				table[tmpX][tmpY+i] = 2;
    		}
    	}
    }

    public static void eatChessRB(int c, int tmpX, int tmpY){

    	if (c == 1){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX+i][tmpY+i] == 1)
    				break;
    			else
    				table[tmpX+i][tmpY+i] = 1;
    		}
    	}
    	else if (c == 2){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX+i][tmpY+i] == 2)
    				break;
    			else
    				table[tmpX+i][tmpY+i] = 2;
    		}
    	}
    }

    public static void eatChessB(int c, int tmpX, int tmpY){

    	if (c == 1){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX+i][tmpY] == 1)
    				break;
    			else
    				table[tmpX+i][tmpY] = 1;
    		}
    	}
    	else if (c == 2){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX+i][tmpY] == 2)
    				break;
    			else
    				table[tmpX+i][tmpY] = 2;
    		}
    	}
    }

    public static void eatChessLB(int c, int tmpX, int tmpY){

    	if (c == 1){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX+i][tmpY-i] == 1)
    				break;
    			else
    				table[tmpX+i][tmpY-i] = 1;
    		}
    	}
    	else if (c == 2){
    		for(int i = 1; i < 8; i++){
    			if (table[tmpX+i][tmpY-i] == 2)
    				break;
    			else
    				table[tmpX+i][tmpY-i] = 2;
    		}
    	}
    }

    public static void eatChess(int c, int x, int y){
    	if (checkL(c, x, y))
    		eatChessL(c, x, y);
    	if (checkLT(c, x, y))
    		eatChessLT(c, x, y);
    	if (checkT(c, x, y))
    		eatChessT(c, x, y);
    	if (checkRT(c, x, y))
    		eatChessRT(c, x, y);
    	if (checkR(c, x, y))
    		eatChessR(c, x, y);
    	if (checkRB(c, x, y))
    		eatChessRB(c, x, y);
    	if (checkB(c, x, y))
    		eatChessB(c, x, y);
    	if (checkLB(c, x, y))
    		eatChessLB(c, x, y);
    }

    public static boolean checkChess(int c, int x, int y){
    	if (checkL(c, x, y) == true || checkLT(c, x, y) == true || checkT(c, x, y) == true || checkRT(c, x, y) == true || checkR(c, x, y) == true || checkRB(c, x, y) == true || checkB(c, x, y) == true || checkLB(c, x, y) == true){
    		return true;
    	}
    	else{
    		return false;
    	}
    }

    public static int countChess(int c){
    	int cnt = 0;
    	for(int i = 1; i <= 8; i++){
    		for(int j = 1; j <= 8; j++){
    			if(table[i][j] == c)
    				cnt++;
    		}
    	}
    	return cnt;    }

    public static void putChess(int c){
    	Scanner scanner;
    	System.out.print("enter ("+c+"):");
    	scanner = new Scanner(System.in);
    	int x =  scanner.nextInt();
    	int y =  scanner.nextInt();
    	while (checkChess(c, x, y) == false || table[x][y] != 0){
    		System.out.print("error\n");
    		System.out.print("enter ("+c+"):");
    		scanner = new Scanner(System.in);
    		x =  scanner.nextInt();
    		y =  scanner.nextInt();
    	}
    	table[x][y] = c;
    	eatChess(c, x, y);
    }

    public static int availableLocation(int c){
    	int cnt = 0;
    	for(int i = 1; i <= 8; i++){
    		for(int j = 1; j <= 8; j++){
    			if(checkChess(c, i, j) && table[i][j] == 0)
    				cnt++;
    		}
    	}
    	return cnt;
    }

    public static void drawTable(){
    	System.out.print("White : "+availableLocation(1)+","+countChess(1)+"\n");
    	System.out.print("Black : "+availableLocation(2)+","+countChess(2)+"\n");
    	for (int i = 1; i < table.length-1; i++){
    		for (int j = 1; j < table[0].length-1; j++){
    			if(table[i][j] == 0)
    				System.out.printf("%2c",'¡·');
    			if(table[i][j] == 1)
    				System.out.printf("%2c",'¡³');    			
    			if(table[i][j] == 2)
    				System.out.printf("%2c",'¡´');
    		}
    		System.out.print("\n");
    	}
    }

    public static void computer(int c){
    	int x,y;

    	do{
    		x = (int)(Math.random()*8+1);
    		y = (int)(Math.random()*8+1);
    	}while(checkChess(c, x, y) == false || table[x][y] != 0);
    	table[x][y] = c;
    	eatChess(c, x, y);
    }

    public static void main(String [] args)
    {      
    	tableInitialization(0);
    	drawTable();
    	while(true){
       		if (availableLocation(2) > 0){
	    		putChess(2);
	    		drawTable();
    		}
    		if (availableLocation(1) > 0){
	    		computer(1);
	    		drawTable();
    		}
    		if (availableLocation(1) == 0 && availableLocation(2) == 0){
    			System.out.print("Game Over");
    			if(countChess(1) > countChess(2))
    				System.out.print("White Win");
    			else if(countChess(1) < countChess(2))
    				System.out.print("Black Win");
    			else
    				System.out.print("End in a draw");
    			break;
    		}
    	}
    }
}

