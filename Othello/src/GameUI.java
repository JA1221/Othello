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
        
        for(int i = 0; i < chessLb.length; i++){
            int ii = i;
            for(int j = 0; j < chessLb[i].length; j++){
                int jj = j;
                //建立棋子Label 物件
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
        for(int i = 0; i < chessBoard.length; i++){
                for(int j=0; j < chessBoard[i].length; j++){
                        chessBoard[i][j] = 2;
                }
        }
        chessBoard[3][3] = 1;
        chessBoard[3][4] = 0;
        chessBoard[4][3] = 0;
        chessBoard[4][4] = 1;
        player = 0;//黑手先
        
        showBord();
        showInfo();
    }

    //***************** 顯示棋盤 ***********************
    private void showBord(){
        analysis();
        for(int i = 0; i < chessLb.length; i++){
            for(int j = 0; j < chessLb[i].length; j++){
       
                if(chessBoard[i][j]==2){//空位
                    if(putHint & putCheck(i, j, player, false))
                        chessLb[i][j].setIcon(chessImg[2]);
                    else
                        chessLb[i][j].setIcon(null);
                }else
                    chessLb[i][j].setIcon(chessImg[chessBoard[i][j]]);
            }
        }
        playIcon.setIcon(chessImg[player]);
    }
    
    //******************* 單向搜尋 *********************
    private int oneWaySearch(int x, int y, int path, int color){//回傳可以吃幾顆 0=不能下
        int opponent = 0;
        
        do{//走下一步
            x += moveX[path];
            y += moveY[path];
            
            if(!properPlace(x, y))//出界
                return 0;
            
            if(chessBoard[x][y] == 2) //遇到空格
                return 0;
            else if(chessBoard[x][y] != color)//遇到敵手 累計數量
                opponent ++;
        }while(chessBoard[x][y] == color);//遇到自己 跳出
        
        return opponent;
    }
    
    //***************** 搜尋8個方向 ***********************
    private boolean putCheck(int x, int y, int color, boolean eat){
            boolean flag = false;
        
            for(int i = 0; i < moveX.length; i++){
                int num = oneWaySearch(x, y, i, color);
                
                if(num != 0){//可下
                    flag = true;
                    
                    if(eat)//需要執行吃棋
                        eatChess(x, y, i, color, num);
                    else//回傳可下
                        return true;
                }
            }
            
            return flag;
    }
    
    //********************** 吃棋 *************************
    private void eatChess(int x, int y, int path, int color, int count){
        for(int i = 0; i < count; i++){
            x += moveX[path];
            y += moveY[path];
            
            chessBoard[x][y] = color;
        }
    }
    
    //****************** 放棋子 ture放置成功 false不能下 **********************
    private boolean putChess(int x, int y){//下棋            
        System.out.println(x + " " + y);
        
        if(chessBoard[x][y]!=2)//不是空位 不能下
            return false;
        else if(putCheck(x, y, player,true)){//可下
            chessBoard[x][y] = player;
            player = 1 - player;
            System.out.println("GameUI.putChess()");
            return true;
        }else//不可下
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
            player = 1 - player;
            showMessage("無子可下，跳過這一局!");
            showBord();
            return false;
        }
        
        return false;
    }
    //******************** 判斷是否出界 ********************
    private boolean properPlace(int x, int y){//合法位置(沒越界)
        if(x >= 0 & y >= 0 & x < chessBoard.length & y < chessBoard.length)
                return true;
        else
                return false;
    }
    //********************** 分析棋盤 ******************
    private void analysis(){//分析剩餘.可下數量
        for(int i = 0; i < 2; i++){//歸零
                alive[i] = 0;
                canPut[i] = 0;
        }

        for(int i = 0; i < chessBoard.length; i++){
                for(int j = 0; j<chessBoard[i].length; j++){
                    //紀錄存活棋數
                    if(chessBoard[i][j] != 2)
                            alive[chessBoard[i][j]]++;
                    //記錄可下數
                    for(int k = 0; k < 2; k++){
                        if(putCheck(i, j, k, false))
                            canPut[k]++;
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
    private final ImageIcon [] chessImg = new ImageIcon[3];//黑 白 建議
    private int chessBoard[][] = new int[8][8];
    private int strategyTable[][] = new int[8][8];
    //                      右.右下.下.左下.左.左上.上.右上
    private int moveX[] = {0, 1, 1 ,1, 0, -1, -1, -1};
    private int moveY[] = {1, 1, 0, -1, -1, -1, 0, 1};
    private int alive[] = {0, 0};
    private int canPut[] = {0, 0};
    private int player = 0;//0黑 1白
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
