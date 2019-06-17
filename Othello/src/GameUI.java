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
                        putChess(chessBoard, ii, jj);
                        showBord();
                        showInfo();
                        endJudgment();
//                        if(endJudgment())
//                            computer();
                    }
                });                
            }       
        }
        
    }
    
    //棋子對齊棋盤
    private void chessLocate(){ 
        for(int i = 0; i < chessLb.length; i++){
            for(int j = 0; j < chessLb[i].length; j++){
                 int x = 2 + board.getX() + 50*j;
                 int y = 5 + board.getY() + 50*i;

                 chessLb[i][j].setLocation(x, y); 
            }
        }
    }
    
    //初始化棋盤 黑白白黑 顯示
    private void initialize(){
        for(int i = 0; i < 8; i++){
                for(int j=0; j < 8; j++){
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
        coordinate_Show();
    }
    
    private void coordinate_Show(int player, int x, int y){
        coordinate_Show.setText(((player==0) ? "黑" : "白") + (char)('A'+y) + (x+1));
    }
    
    private void coordinate_Show(){
        coordinate_Show.setText("");
    }
    
    //***************** 顯示棋盤 ***********************
    private void showBord(){
        analysis();
        for(int i = 0; i < chessLb.length; i++){
            for(int j = 0; j < chessLb[i].length; j++){
       
                if(chessBoard[i][j]==2){//空位
                    if(putHint & putCheck(chessBoard, i, j, player, false))
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
    private int oneWaySearch(int board[][], int x, int y, int path, int color){//回傳可以吃幾顆 0=不能下
        int opponent = 0;
        
        do{//走下一步
            x += moveX[path];
            y += moveY[path];
            
            if(!properPlace(x, y))//出界
                return 0;
            
            if(board[x][y] == 2) //遇到空格
                return 0;
            else if(board[x][y] != color)//遇到敵手 累計數量
                opponent ++;
        }while(board[x][y] != color);//遇到自己 跳出
        
        return opponent;
    }
    
    //***************** 搜尋8個方向 ***********************
    private boolean putCheck(int board[][], int x, int y, int color, boolean eat){
            boolean flag = false;
        
            for(int i = 0; i < 8; i++){
                int num = oneWaySearch(board, x, y, i, color);
                
                if(num != 0){//可下
                    flag = true;
                    
                    if(eat)//需要執行吃棋
                        eatChess(board, x, y, i, color, num);
                    else//回傳可下
                        return true;
                }
            }
            
            return flag;
    }
    
    //********************** 吃棋 *************************
    private void eatChess(int board[][], int x, int y, int path, int color, int count){
        for(int i = 0; i < count; i++){
            x += moveX[path];
            y += moveY[path];
            
            board[x][y] = color;
        }
    }
    
    //****************** 放棋子 ture放置成功 false不能下 **********************
    private boolean putChess(int board[][], int x, int y){//下棋            
        System.out.println(((player==0) ? "黑" : "白") + (char)('A'+y) + (x+1));
        
        if(board[x][y]!=2)//不是空位 不能下
            return false;
        else if(putCheck(board, x, y, player,true)){//可下
            board[x][y] = player;
            coordinate_Show(player, x, y);
            player = 1 - player;
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
            return true;
        }
        
        return true;
    }
    //******************** 判斷是否出界 ********************
    private boolean properPlace(int x, int y){//合法位置(沒越界)
        if(x >= 0 & y >= 0 & x < 8 & y < 8)
                return true;
        else
                return false;
    }
    //********************** 分析棋盤 ******************
    private void analysis(){//分析剩餘.可下數量
        alive = analysis_Alive(chessBoard);
        canPut = analysis_CanPut(chessBoard);
        
        System.out.println("　　　黑 白");
        System.out.println("剩餘: " + alive[0] + " " + alive[1]);
        System.out.println("可下: " + canPut[0] + " " + canPut[1]);
        
//        AIcomputer(chessBoard);
    }
    
    int[] analysis_Alive(int board[][]){
        int alive[] = new int[2];
        
        for(int i = 0; i < 8; i++){
                for(int j = 0; j<8; j++){
                    if(board[i][j] != 2)
                            alive[board[i][j]]++;
                }
        }
        
        return alive;
    }
    
    int[] analysis_CanPut(int board[][]){
        int canPut[] = new int[2];
        
        for(int i = 0; i < 8; i++){
                for(int j = 0; j<8; j++){
                    if(board[i][j] == 2){
                        for(int k = 0; k < 2; k++){
                            if(putCheck(board, i, j, k, false))
                                canPut[k]++;
                        }
                    }  
                }
        }
        
        return canPut;
    }
    //****************************************
    public void showMessage(String s){
        JOptionPane.showMessageDialog(null, s, "注意", JOptionPane.WARNING_MESSAGE);
    }
    
    public void computer(int board[][]){
        int max = Integer.MIN_VALUE , x = 0, y = 0;
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j]!=2)continue;
                
                int eatNum = 0;
                
                for(int k = 0; k < 8; k++){
                    eatNum += oneWaySearch(board, i, j, k, player);
                }
                
                if(eatNum > max){
                    max = eatNum;
                    x = i;
                    y = j;
                }
            }
        }
        putChess(board, x, y);
        showBord();
        showInfo();
        endJudgment();
    }
    
    public void computer2(int board[][]){
        int max = Integer.MIN_VALUE , x = 0, y = 0;
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j]!=2) {//不是空格
                } else if(putCheck(board, i, j, player, false)){//空格 且可下
                    int score = next_Step_Score(board, i, j, player);

                    if( score> max){
                        max = score;
                        x = i;
                        y = j;
                    }
                }           
            }
        }
        System.out.println("alive_Score:" + alive_Score(board , player));
        System.out.println("mobility_Score:" + mobility_Score(board, player));
        System.out.println("weight_Score:" + weight_Score(board, player)); 
        
        
        putChess(board, x, y);
        showBord();
        showInfo();
        endJudgment();
        System.out.println(max);
    }
    
    public void AIcomputer(int board[][], int depth, int alpha, int beta, boolean minimaxFlag){
        if(depth==0)

        System.out.println("alive_Score:" + alive_Score(board , player));
        System.out.println("mobility_Score:" + mobility_Score(board, player));
        System.out.println("weight_Score:" + weight_Score(board, player));      
        
    }
    
    int sore(int board[][], int player){
        return 2*alive_Score(board, player) + 1*mobility_Score(board, player) + 3*weight_Score(board, player);
    }
    
    int alive_Score(int board[][], int player){
        int alive[] = analysis_Alive(board);
        
        return alive[player];
    }
    
    int mobility_Score(int board[][], int player){
        int canPut[] = analysis_CanPut(board);
        
        return canPut[player];
    }
    
    int weight_Score(int board[][], int player){
        weight_Score_Calc calc = new weight_Score_Calc();
        
        return calc.Score(board, player);
    }
    
    int[][] copy_Array(int board[][]){
        int array[][] = new int[8][8];
        
        for(int i = 0; i < 8; i ++){
            System.arraycopy(board[i], 0, array[i], 0, 8);
        }
        
        return array;
    }
    
    int next_Step_Score(int board[][], int x, int y, int player){     
        board = copy_Array(board);
        
        putChess(board, x, y);
        this.player = player;
        
        return sore(board, player);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        computer = new javax.swing.JButton();
        coordinateX = new javax.swing.JLabel();
        coordinateY = new javax.swing.JLabel();
        coordinate_Show = new javax.swing.JLabel();
        info = new javax.swing.JLabel();
        restartBt = new javax.swing.JLabel();
        hint = new javax.swing.JCheckBox();
        playIcon = new javax.swing.JLabel();
        board = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("黑白棋");
        setMinimumSize(new java.awt.Dimension(700, 500));
        setResizable(false);
        setSize(new java.awt.Dimension(700, 470));
        getContentPane().setLayout(null);

        computer.setText("電腦下棋");
        computer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                computerActionPerformed(evt);
            }
        });
        getContentPane().add(computer);
        computer.setBounds(540, 310, 97, 23);

        coordinateX.setFont(new java.awt.Font("Times New Roman", 0, 42)); // NOI18N
        coordinateX.setText("<html> <body>1<br>2<br>3<br>4<br>5<br>6<br>7<br>8<body> </html> ");
        getContentPane().add(coordinateX);
        coordinateX.setBounds(10, 40, 30, 400);

        coordinateY.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        coordinateY.setText("A   B   C   D   E   F   G   H");
        getContentPane().add(coordinateY);
        coordinateY.setBounds(50, 10, 390, 30);

        coordinate_Show.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        coordinate_Show.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        coordinate_Show.setText("黑：A1");
        getContentPane().add(coordinate_Show);
        coordinate_Show.setBounds(550, 200, 80, 27);

        info.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        info.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        info.setText("黑棋：0 可下：0 白棋：0 可下：0");
        info.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(info);
        info.setBounds(40, 440, 403, 30);

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
        hint.setOpaque(false);
        hint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hintActionPerformed(evt);
            }
        });
        getContentPane().add(hint);
        hint.setBounds(530, 230, 110, 40);

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
        board.setBounds(40, 40, 403, 403);

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

    private void computerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_computerActionPerformed
        // TODO add your handling code here:
        computer2(chessBoard);
    }//GEN-LAST:event_computerActionPerformed

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
    private javax.swing.JButton computer;
    private javax.swing.JLabel coordinateX;
    private javax.swing.JLabel coordinateY;
    private javax.swing.JLabel coordinate_Show;
    private javax.swing.JCheckBox hint;
    private javax.swing.JLabel info;
    private javax.swing.JLabel playIcon;
    private javax.swing.JLabel restartBt;
    // End of variables declaration//GEN-END:variables

}
