import java.awt.*;
import java.awt.Button;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Reversi2 extends JFrame{
    
    static JButton mmp[][] = new JButton[8][8];
    static int chess[][] = new int[8][8];
    static int dir[][] = {{0,1},{1,0},{-1,0},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};
    static int color = 2;
    static int check(int x,int y,int color){
        if(chess[x][y] != 1 && chess[x][y] != 2){
            for(int i = 0;i < 8;i ++){
                int tx = x + dir[i][0];
                int ty = y + dir[i][1];
                if(tx >= 0 && tx < 8 && ty >= 0 && ty < 8 && chess[tx][ty] > 0 && chess[tx][ty] != color){
                    return 1;
                }
            }
        }
        return 0;    
    }

    static void Print(){
        for(int i = 0;i < 8;i ++){
            for(int j = 0;j < 8;j ++){
                if(chess[i][j] == 1){mmp[i][j].setBackground(Color.WHITE);}
                else if(chess[i][j] == 2){mmp[i][j].setBackground(Color.BLACK);}
            }
        }
        return ;
    }
    
    static int hasZero(String p) {
        if(p.length() == 0) {return 0;}
        return p.indexOf("0");
    }
    
    static int change(int x,int y,int color){
        int isChange = 0;
        int diff = 3 - color;
        String p = "";
        int f = 0;
        for(int i = x - 1;i >= 0;i --) {//west
            if(chess[i][y] == color) {f = 1;break;}
            p += String.valueOf(chess[i][y]);
        }
        if(hasZero(p) == -1 && f == 1) {
            isChange = 1;
            for(int i = x - 1;i >= 0;i --) {
                if(chess[i][y] == color) {break;}
                chess[i][y] = color;
            }
        }//System.out.println(f);
        f = 0;p = "";
        for(int i = x + 1;i < 8;i ++) {//east
            if(chess[i][y] == color) {f = 1;break;}
            p += String.valueOf(chess[i][y]);
        }
        if(hasZero(p) == -1 && f == 1) {
            isChange = 1;
             for(int i = x + 1;i < 8;i ++) {
                if(chess[i][y] == color) {break;}
                chess[i][y] = color;
            }
        }//System.out.println(f);
        f = 0;p = "";
        for(int i = y - 1;i >= 0;i --) {//north
            if(chess[x][i] == color) {f = 1;break;}
            p += String.valueOf(chess[x][i]);
        }
        if(hasZero(p) == -1 && f == 1) {
            isChange = 1;
             for(int i = y - 1;i >= 0;i --) {
                if(chess[x][i] == color) {break;}
                chess[x][i] = color;
            }
        }//System.out.println(f);
        f = 0;p = "";
        for(int i = y + 1;i < 8;i ++) {//south
            if(chess[x][i] == color) {f = 1;break;}
            p += String.valueOf(chess[x][i]);
        }
        if(hasZero(p) == -1 && f == 1) {
            isChange = 1;
            for(int i = y + 1;i < 8;i ++) {
                if(chess[x][i] == color) {break;}
                chess[x][i] = color;
            }
        }//System.out.println(f);
        f = 0;p = "";
        for(int i = 1;i <= 8;i ++) {//east-north
            int tx = x + i;
            int ty = y - i;
            if(tx >= 0 && tx < 8 && ty >= 0 && ty < 8) {
                if(chess[tx][ty] == color) {f = 1;break;}
                p += String.valueOf(chess[tx][ty]);
            }
        }
        if(hasZero(p) == -1 && f == 1) {
            isChange = 1;
            for(int i = 1;i <= 8;i ++) {
                int tx = x + i;
                int ty = y - i;
                if(tx >= 0 && tx < 8 && ty >= 0 && ty < 8) {
                    if(chess[tx][ty] == color) {break;}
                    chess[tx][ty] = color;
                }
            }
        }
        f = 0;p = "";
        for(int i = 1;i <= 8;i ++) {//east-south
            int tx = x + i;
            int ty = y + i;
            if(tx >= 0 && tx < 8 && ty >= 0 && ty < 8) {
                if(chess[tx][ty] == color) {f = 1;break;}
                p += String.valueOf(chess[tx][ty]);
            }
        }
        if(hasZero(p) == -1 && f == 1) {
            isChange = 1;
            for(int i = 1;i <= 8;i ++) {
                int tx = x + i;
                int ty = y + i;
                if(tx >= 0 && tx < 8 && ty >= 0 && ty < 8) {
                    if(chess[tx][ty] == color) {break;}
                    chess[tx][ty] = color;
                }
            }
        }
        f = 0;p = "";
        for(int i = 1;i <= 8;i ++) {//west-north
            int tx = x - i;
            int ty = y - i;
            if(tx >= 0 && tx < 8 && ty >= 0 && ty < 8) {
                if(chess[tx][ty] == color) {f = 1;break;}
                p += String.valueOf(chess[tx][ty]);
            }
        }
        if(hasZero(p) == -1 && f == 1) {
            isChange = 1;
            for(int i = 1;i <= 8;i ++) {
                int tx = x - i;
                int ty = y - i;
                if(tx >= 0 && tx < 8 && ty >= 0 && ty < 8) {
                    if(chess[tx][ty] == color) {break;}
                    chess[tx][ty] = color;
                }
            }
        }
        f = 0;p = "";
        for(int i = 1;i <= 8;i ++) {//west-south
            int tx = x - i;
            int ty = y + i;
            if(tx >= 0 && tx < 8 && ty >= 0 && ty < 8) {
                if(chess[tx][ty] == color) {f = 1;break;}
                p += String.valueOf(chess[tx][ty]);
            }
        }
        if(hasZero(p) == -1 && f == 1) {
            isChange = 1;
            for(int i = 1;i <= 8;i ++) {
                int tx = x - i;
                int ty = y + i;
                if(tx >= 0 && tx < 8 && ty >= 0 && ty < 8) {
                    if(chess[tx][ty] == color) {break;}
                    chess[tx][ty] = color;
                }
            }
        }
        f = 0;p = "";
        if(isChange == 1) {chess[x][y] = color;Print();}
        
        return isChange;
    } 
    
    public Reversi2(){
        JFrame window = new JFrame();
        Container con = window.getContentPane();
        con.setLayout(null);  
        window.setVisible(true);
        window.setSize(800,600);
        for(int i = 0;i < 8;i ++){
            for(int j = 0;j < 8;j ++){
                int ii = i;
                int jj = j;
                chess[i][j] = 0;
                mmp[i][j] = new JButton();
                mmp[i][j].setVisible(true);
                mmp[i][j].setBounds(40 + 40 * i,40 + 40 * j,40,40);
                mmp[i][j].setBackground(Color.gray);
                mmp[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        if(check(ii,jj,color) == 1) {
                            if(change(ii,jj,color) == 1) {
                                color = 3 - color;
                            }
                        }
                    }
                });    
                window.add(mmp[i][j]);
            }
        }
        
        chess[3][3] = 1;
        chess[4][4] = 1;
        mmp[3][3].setBackground(Color.WHITE);
        mmp[4][4].setBackground(Color.WHITE);
        chess[3][4] = 2;
        chess[4][3] = 2;
        mmp[3][4].setBackground(Color.BLACK);
        mmp[4][3].setBackground(Color.BLACK);
        window.setSize(800,500);
        window.setTitle("Reversi By-J_Coder");
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        new Reversi2();
    }
    
}