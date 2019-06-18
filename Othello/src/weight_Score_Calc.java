public class weight_Score_Calc {
    int weight[][]= 
    {
        {500,-100,10,6,6,10,-100,500},
        {-100,-150,3,1,1,3,-150,-100},
        {10,3,5,3,3,5,3,10},
        {6,1,3,1,1,3,1,6},
        {6,1,3,1,1,3,1,6},
        {10,3,5,3,3,5,3,10},
        {-100,-150,3,1,1,3,-150,-100},
        {500,-100,10,6,6,10,-100,500}
    };
    
    int Score(int board[][], int player){
        int score = 0;
        
        if(board[0][0]==player){
            weight[0][1] = 20;
            weight[1][0] = 20;
        }
        if(board[0][7]==player){
            weight[0][6] = 20;
            weight[1][7] = 20;
        }
        if(board[7][0]==player){
            weight[7][1] = 20;
            weight[6][0] = 20;
        }
        if(board[7][7]==player){
            weight[6][7] = 20;
            weight[7][6] = 20;
        }
        
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j] == player)
                    score += weight[i][j];
            }
        }
        
        
        return score;
    }
    
}
