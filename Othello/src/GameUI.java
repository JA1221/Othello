import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Robin
 */
public class GameUI extends javax.swing.JFrame {
    public GameUI() {
        chessComponents();
        initComponents();
        chessLocate();
        initialize();
    }
    private void chessComponents(){
        chessImg[0] = new ImageIcon(getClass().getResource("/images/black.png"));
        chessImg[1] = new ImageIcon(getClass().getResource("/images/white.png"));
        chessImg[2] = new ImageIcon(getClass().getResource("/images/ok.png"));
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Cover.png")));
        
        for(byte i = 0; i < chessLb.length; i++){
            byte ii = i;
            for(byte j = 0; j < chessLb[i].length; j++){
                byte jj = j;
                //建立棋子
                chessLb[i][j] = new JLabel();
                chessLb[i][j].setSize(50, 50);
                getContentPane().add(chessLb[i][j]);
                 //設定棋子監聽
                chessLb[i][j].addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        putChess(ii, jj);
                        showBord();
                        showInfo();
                        endJudgment();
                    }
                });                
            }       
        }
        
    }
    //棋子對齊棋盤
    private void chessLocate(){ 
        for(int i = 0; i < chessLb.length; i++){
            for(int j = 0; j < chessLb[i].length; j++){
                 int x = 2 + board.getX() + 50*i;
                 int y = 5 + board.getY() + 50*j;

                 chessLb[i][j].setLocation(x, y); 
            }
        }
    }
    //初始化棋盤 黑白白黑 顯示
    private void initialize(){
        for(byte i = 0; i < chessBoard.length; i++){
                for(byte j=0; j < chessBoard[i].length; j++){
                        chessBoard[i][j] = 2;
                }
        }
        chessBoard[3][3] = 1;
        chessBoard[3][4] = 0;
        chessBoard[4][3] = 0;
        chessBoard[4][4] = 1;
        player = 0;
        
        showBord();
        showInfo();
    }

    //***************** 顯示棋盤 ***********************
    private void showBord(){
        analysis();
        for(int i = 0; i < chessLb.length; i++){
            for(int j = 0; j < chessLb[i].length; j++){
                if(chessBoard[i][j]==2){
                    if(putHint & (strategyTable[i][j] == player | strategyTable[i][j]==3))
                        chessLb[i][j].setIcon(chessImg[2]);
                    else
                        chessLb[i][j].setIcon(null);
                }else
                    chessLb[i][j].setIcon(chessImg[chessBoard[i][j]]);
            }
        }
        playIcon.setIcon(chessImg[player]);
    }
    //******************* 單向搜尋 可下哪種棋 *********************
    private byte oneWaySearch(byte x, byte y, byte path){//單向搜尋 0黑棋能下 1白棋能下 2沒棋能下
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
    private byte checkPossible(byte x, byte y){//8向搜尋 0黑棋能下 1白棋能下 2沒棋能下 3都能下
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
    private boolean putChess(byte x, byte y){//下棋            
        System.out.println(x + " " + y);
        
        if(strategyTable[x][y] == player | strategyTable[x][y]==3){//玩家可以下
            for(byte i = 0; i < moveX.length; i++){
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
            player = (byte)(1 - player);
            return true;
        }
        return false;
    }
    private void showInfo(){
        info.setText("黑棋：" + alive[0] + " 可下："  + canPut[0] + " 白棋：" + alive[1] + " 可下：" + canPut[1]);
    }
    private boolean endJudgment(){
        if(canPut[0] ==0 & canPut[1] == 0){//都不能下 遊戲結束
            if(alive[0] > alive[1])
                showMessage("黑棋 Win!");
            else if(alive[0] < alive[1])
                showMessage("白棋 Win!");	
            else
                showMessage("平手!");
            return false;
        }else if(canPut[player]==0){//無子可下換人
            player = (byte)(1 - player);
            showMessage("無子可下，跳過這一局!");
            showBord();
            return false;
        }
        
        return false;
    }
    //******************** 判斷是否出界 ********************
    private boolean properPlace(byte x, byte y){//合法位置(沒越界)
        if(x >= 0 & y >= 0 & x < chessBoard.length & y < chessBoard.length)
                return true;
        else
                return false;
    }
    //********************** 分析棋盤 ******************
    private void analysis(){//分析剩餘.可下數量
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
    public void showMessage(String s){
        JOptionPane.showMessageDialog(null, s, "注意", JOptionPane.WARNING_MESSAGE);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        info = new javax.swing.JLabel();
        restartBt = new javax.swing.JLabel();
        hint = new javax.swing.JCheckBox();
        playIcon = new javax.swing.JLabel();
        board = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("黑白棋");
        setMinimumSize(new java.awt.Dimension(730, 530));
        setSize(new java.awt.Dimension(700, 470));
        getContentPane().setLayout(null);

        info.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        info.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        info.setText("黑棋：0 可下：0 白棋：0 可下：0");
        info.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(info);
        info.setBounds(40, 430, 403, 30);

        restartBt.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        restartBt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        restartBt.setText("restart");
        restartBt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                restartBtMousePressed(evt);
            }
        });
        getContentPane().add(restartBt);
        restartBt.setBounds(590, 440, 100, 30);

        hint.setFont(new java.awt.Font("標楷體", 1, 36)); // NOI18N
        hint.setText("提示");
        hint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hintActionPerformed(evt);
            }
        });
        getContentPane().add(hint);
        hint.setBounds(530, 210, 110, 40);

        playIcon.setFont(new java.awt.Font("標楷體", 1, 36)); // NOI18N
        playIcon.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        playIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/black.png"))); // NOI18N
        playIcon.setText("玩家");
        playIcon.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(playIcon);
        playIcon.setBounds(510, 130, 170, 60);

        board.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/board.png"))); // NOI18N
        board.setLabelFor(board);
        board.setMinimumSize(new java.awt.Dimension(500, 507));
        getContentPane().add(board);
        board.setBounds(40, 30, 403, 403);

        background.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        background.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background.jpg"))); // NOI18N
        getContentPane().add(background);
        background.setBounds(0, 0, 1000, 600);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hintActionPerformed
        putHint = hint.isSelected();
        showBord();
    }//GEN-LAST:event_hintActionPerformed

    private void restartBtMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_restartBtMousePressed
        initialize();
    }//GEN-LAST:event_restartBtMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameUI().setVisible(true);
            }
        });
    }
    //**************************************************************************
    private final JLabel [][] chessLb = new JLabel[8][8];
    private final ImageIcon [] chessImg = new ImageIcon[3];//黑白 建議
    private byte chessBoard[][] = new byte[8][8];
    private byte strategyTable[][] = new byte[8][8];
    //                      右.右下.下.左下.左.左上.上.右上
    private byte moveX[] = {0, 1, 1 ,1, 0, -1, -1, -1};
    private byte moveY[] = {1, 1, 0, -1, -1, -1, 0, 1};
    private byte alive[] = {0, 0};
    private byte canPut[] = {0, 0};
    private byte player = 0;//0黑 1白
    private boolean putHint = false;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLabel board;
    private javax.swing.JCheckBox hint;
    private javax.swing.JLabel info;
    private javax.swing.JLabel playIcon;
    private javax.swing.JLabel restartBt;
    // End of variables declaration//GEN-END:variables

}
