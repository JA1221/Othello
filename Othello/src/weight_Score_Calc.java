public class weight_Score_Calc {
    final int weight[][]= 
    {
        {80,-15,6,4,4,6,-15,80},
        {-15,-29,3,1,1,3,-29,-15},
        {6,3,5,3,3,5,3,6},
        {4,1,3,1,1,3,1,4},
        {4,1,3,1,1,3,1,4},
        {6,3,5,3,3,5,3,6},
        {-15,-29,3,1,1,3,-29,-15},
        {80,-15,6,4,4,6,-15,80}
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
