public class weight_Score_Calc {
    final int weight[][]= 
    {
        {150,-40,10,6,6,10,-40,150},
        {-40,-60,3,1,1,3,-60,-40},
        {10,3,5,3,3,5,3,10},
        {6,1,3,1,1,3,1,6},
        {6,1,3,1,1,3,1,6},
        {10,3,5,3,3,5,3,10},
        {-40,-60,3,1,1,3,-60,-40},
        {150,-40,10,6,6,10,-40,150}
    };
    
    int Score(int board[][], int player){
        int score = 0;
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j] == player)
                    score += weight[i][j];
            }
        }
        
        return score;
    }
    
}
