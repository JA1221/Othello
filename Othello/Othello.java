import java.util.Scanner;

public class Othello{
	static Scanner scanner = new Scanner(System.in);

	static byte chessBoard[][] = new byte[8][8];
	static byte strategyTable[][] = new byte[8][8];
	static char chessIcon[] = {'��', '��', '�D'};//0�� 1�� 2�L
	//					  �k.�k�U.�U.���U.��.���W.�W.�k�W
	static byte moveX[] = {0, 1, 1 ,1, 0, -1, -1, -1};
	static byte moveY[] = {1, 1, 0, -1, -1, -1, 0, 1};
	static byte alive[] = {0, 0};
	static byte canPut[] = {0, 0};

	static byte player = 0;//0�� 1��
	//***************** �ѽL��l�� ***********************
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
	//***************** ��ܴѽL ***********************
	static void showBord(){
		print('�@');//�a����
		for(byte i = 0; i < chessBoard[0].length; i++){
			print(' ');
			print((char)('a' + i));
		}
		newLine();

		for(byte i = 0; i < chessBoard.length; i++){
			print(i + 1);//���
			print(' ');
			for(byte j = 0; j < chessBoard[i].length; j++){
				if(strategyTable[i][j] == player | strategyTable[i][j] == 3)
					print("��");
				else
					print(chessIcon[chessBoard[i][j]]);
			}
			newLine();
		}
	}
	//******************* ��V�j�M �i�U���ش� *********************
	static byte oneWaySearch(byte x, byte y, byte path){//��V�j�M 0�´ѯ�U 1�մѯ�U 2�S�ѯ�U
		x += moveX[path];//���Ĥ@�B����
		y += moveY[path];
		
		if(!properPlace(x,y))//�V��
			return 2;
		else if(chessBoard[x][y] == 2)//����O�Ŵ�
			return 2;
		byte color = chessBoard[x][y];//Ū���Ĥ@���X�l�C��
		//***********************************
		do{//Go
			x += moveX[path];
			y += moveY[path];

			if(!properPlace(x,y))//�V��
				return 2;
			else if(chessBoard[x][y] == 2)//���s��
				return 2;
		}while(chessBoard[x][y] == color);//�Ĥ�l�ٳs�� ����J��ڤ�Ѥl

		if(color == 0)
			return 1;
		else
			return 0;
	}
	//***************** �j�M8�Ӥ�V �i�U���ش� ***********************
	static byte checkPossible(byte x, byte y){//8�V�j�M 0�´ѯ�U 1�մѯ�U 2�S�ѯ�U 3����U
		boolean black = false, white = false;

		if(chessBoard[x][y] != 2)//�S�Ŧ�
			return 2;

		for(byte i = 0; i < moveX.length; i++){//�j�M8�Ӥ�V
			byte ans = oneWaySearch(x,y,i);
			if(ans == 0)
				black = true;
			else if(ans == 1)
				white = true;

			if(white & black == true)//����U ���ΦA�P�_ ���X
				return 3;
		}

		if(black)
			return 0;
		else if(white)
			return 1;
		else
			return 2;				
	}
	//****************** ��Ѥl ture��m���\ false����U **********************
	static boolean putChess(byte x, byte y){//�U��
		if(strategyTable[x][y] == player | strategyTable[x][y]==3){//���a�i�H�U

			for(byte i = 0; i < moveX.length; i++){//8�Ӥ��j�M
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
	//******************** �P�_�O�_�X�� ********************
	static boolean properPlace(byte x, byte y){//�X�k��m(�S�V��)
		if(x >= 0 & y >= 0 & x < chessBoard.length & y < chessBoard.length)
			return true;
		else
			return false;
	}
	//********************** ���R�ѽL ******************
	static void analysis(){//���R�Ѿl.�i�U�ƶq
		for(byte i = 0; i < 2; i++){//�k�s
			alive[i] = 0;
			canPut[i] = 0;
		}

		for(byte i = 0; i < chessBoard.length; i++){
			for(byte j = 0; j<chessBoard[i].length; j++){

				byte ans = checkPossible(i,j);//���o�i���
				strategyTable[i][j] = ans;

				if(chessBoard[i][j] != 2)
					alive[chessBoard[i][j]]++;//�����s���Ѽ�

				if(ans == 0)//�p��i���
					canPut[0]++;
				else if(ans == 1)
					canPut[1]++;
				else if(ans == 3){
					canPut[0]++;
					canPut[1]++;
				}
			}
		}
		System.out.println("�@�@�@�� ��");
		System.out.println("�Ѿl: " + alive[0] + " " + alive[1]);
		System.out.println("�i�U: " + canPut[0] + " " + canPut[1]);
	}
	//****************************************
	public static void main(String[] args) {
		initialize();
		
		println("");

		while(canPut[0] > 0 | canPut[1] > 0){//�C���������褣��U
			if(canPut[player]==0){
				player = (byte)(1 - player);
				println("�L�l�i�U�A���L�o�@��!");
			}

			System.out.print("�п�J�y��(" + player + "):");
			String str = scanner.next().toLowerCase();
			if(putChess((byte)(str.charAt(0) - '0' - 1), (byte)(str.charAt(1) - 'a'))){
				player = (byte)(1 - player);
				showBord();
				analysis();
			}else
				println("�п�J���T��m!");

			println("");
		}
		println("�C������");	
		if(alive[0] > alive[1])
			print("�´� Win!");
		else if(alive[0] < alive[1])
			print("�մ� Win!");	
		else
			print("����!");
	}
}