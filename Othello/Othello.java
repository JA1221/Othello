import java.util.Scanner;

public class Othello{
	static Scanner scanner = new Scanner(System.in);

	static byte chessBoard[][] = new byte[8][8];
	static byte strategyTable[][] = new byte[8][8];
	static char chessIcon[] = {'○', '●', '．'};//0黑 1白 2無
	//					  右.右下.下.左下.左.左上.上.右上
	static byte moveX[] = {0, 1, 1 ,1, 0, -1, -1, -1};
	static byte moveY[] = {1, 1, 0, -1, -1, -1, 0, 1};
	static byte alive[] = {0, 0};
	static byte canPut[] = {0, 0};

	static byte player = 0;//0黑 1白
	//***************** 棋盤初始化 ***********************
	static void initialize(){
		for(byte i = 0; i < chessBoard.length; i++){
			for(byte j=0; j < chessBoard[i].length; j++){
				chessBoard[i][j] = 2;
			}
		}
		chessBoard[3][3] = 1;
		chessBoard[3][4] = 0;
		chessBoard[4][3] = 0;
		chessBoard[4][4] = 1;

		analysis();
		showBord();
	}
	//****************************************
	static void newLine(){ System.out.println();}
	static void print(char c){ System.out.print(c);}
	static void print(int n){ System.out.print(n);}
	static void print(String n){ System.out.print(n);}
	static void println(String n){ System.out.println(n);}
	//***************** 顯示棋盤 ***********************
	static void showBord(){
		print('　');//縱坐標
		for(byte i = 0; i < chessBoard[0].length; i++){
			print(' ');
			print((char)('a' + i));
		}
		newLine();

		for(byte i = 0; i < chessBoard.length; i++){
			print(i + 1);//橫坐標
			print(' ');
			for(byte j = 0; j < chessBoard[i].length; j++){
				if(strategyTable[i][j] == player | strategyTable[i][j] == 3)
					print("√");
				else
					print(chessIcon[chessBoard[i][j]]);
			}
			newLine();
		}
	}
	//******************* 單向搜尋 可下哪種棋 *********************
	static byte oneWaySearch(byte x, byte y, byte path){//單向搜尋 0黑棋能下 1白棋能下 2沒棋能下
		x += moveX[path];//走第一步測試
		y += moveY[path];
		
		if(!properPlace(x,y))//越界
			return 2;
		else if(chessBoard[x][y] == 2)//旁邊是空棋
			return 2;
		byte color = chessBoard[x][y];//讀取第一顆旗子顏色
		//***********************************
		do{//Go
			x += moveX[path];
			y += moveY[path];

			if(!properPlace(x,y))//越界
				return 2;
			else if(chessBoard[x][y] == 2)//不連續
				return 2;
		}while(chessBoard[x][y] == color);//敵方子還連續 直到遇到我方棋子

		if(color == 0)
			return 1;
		else
			return 0;
	}
	//***************** 搜尋8個方向 可下哪種棋 ***********************
	static byte checkPossible(byte x, byte y){//8向搜尋 0黑棋能下 1白棋能下 2沒棋能下 3都能下
		boolean black = false, white = false;

		if(chessBoard[x][y] != 2)//沒空位
			return 2;

		for(byte i = 0; i < moveX.length; i++){//搜尋8個方向
			byte ans = oneWaySearch(x,y,i);
			if(ans == 0)
				black = true;
			else if(ans == 1)
				white = true;

			if(white & black == true)//都能下 不用再判斷 跳出
				return 3;
		}

		if(black)
			return 0;
		else if(white)
			return 1;
		else
			return 2;				
	}
	//****************** 放棋子 ture放置成功 false不能下 **********************
	static boolean putChess(byte x, byte y){//下棋
		if(strategyTable[x][y] == player | strategyTable[x][y]==3){//玩家可以下

			for(byte i = 0; i < moveX.length; i++){//8個方位搜尋
				byte ans = oneWaySearch(x,y,i);
				byte tempx = x, tempy =y;

				if(ans == player){
					do{
						chessBoard[x][y] = player;
						x += moveX[i];
						y += moveY[i];						
					}while(chessBoard[x][y]!=player);
				}
				x = tempx;
				y = tempy;
			}
			return true;
		}
		return false;
	}
	//******************** 判斷是否出界 ********************
	static boolean properPlace(byte x, byte y){//合法位置(沒越界)
		if(x >= 0 & y >= 0 & x < chessBoard.length & y < chessBoard.length)
			return true;
		else
			return false;
	}
	//********************** 分析棋盤 ******************
	static void analysis(){//分析剩餘.可下數量
		for(byte i = 0; i < 2; i++){//歸零
			alive[i] = 0;
			canPut[i] = 0;
		}

		for(byte i = 0; i < chessBoard.length; i++){
			for(byte j = 0; j<chessBoard[i].length; j++){

				byte ans = checkPossible(i,j);//取得可能性
				strategyTable[i][j] = ans;

				if(chessBoard[i][j] != 2)
					alive[chessBoard[i][j]]++;//紀錄存活棋數

				if(ans == 0)//計算可能性
					canPut[0]++;
				else if(ans == 1)
					canPut[1]++;
				else if(ans == 3){
					canPut[0]++;
					canPut[1]++;
				}
			}
		}
		System.out.println("　　　黑 白");
		System.out.println("剩餘: " + alive[0] + " " + alive[1]);
		System.out.println("可下: " + canPut[0] + " " + canPut[1]);
	}
	//****************************************
	public static void main(String[] args) {
		initialize();
		
		println("");

		while(canPut[0] > 0 | canPut[1] > 0){//遊玩直到雙方不能下
			if(canPut[player]==0){
				player = (byte)(1 - player);
				println("無子可下，跳過這一局!");
			}

			System.out.print("請輸入座標(" + player + "):");
			String str = scanner.next().toLowerCase();
			if(putChess((byte)(str.charAt(0) - '0' - 1), (byte)(str.charAt(1) - 'a'))){
				player = (byte)(1 - player);
				showBord();
				analysis();
			}else
				println("請輸入正確位置!");

			println("");
		}
		println("遊戲結束");	
		if(alive[0] > alive[1])
			print("黑棋 Win!");
		else if(alive[0] < alive[1])
			print("白棋 Win!");	
		else
			print("平手!");
	}
}