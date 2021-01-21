package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int[] gameState = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
    int[][] winningPositions = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6},

    };
    int activePlayer = 0;
    boolean gameActive = true;

    public void setCoin(View view){
        ImageView tileImage = (ImageView) view;
        int position = Integer.parseInt(tileImage.getTag().toString());
        if(gameState[position] == -1 && gameActive) {
            tileImage.setTranslationY(-1500);
            gameState[position] = activePlayer;
            if (activePlayer == 0) {
                tileImage.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                tileImage.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            tileImage.animate().translationYBy(1500).setDuration(500);
            int winner = determineWinner();
            Log.i("INFO", "Winner:" + winner);
            if(winner == -1){
                boolean allBoxesFilled = true;
                for(int i=0;i< gameState.length;i++){
                    if(gameState[i] == -1){
                        allBoxesFilled = false;
                        break;
                    }
                }
                if(allBoxesFilled){
                    gameActive = false;
                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                    playAgainButton.setVisibility(View.VISIBLE);
                    Toast.makeText(this,"No Winner!", Toast.LENGTH_SHORT).show();
                }
            }else if(winner != -1) {
                gameActive = false;

                Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                playAgainButton.setVisibility(View.VISIBLE);
                if (winner == 0) {
                    Toast.makeText(this,"Winner:Yellow", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"Winner:Red", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void resetGame(View view){
        gameState = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};
        gameActive = true;
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        playAgainButton.setVisibility(View.INVISIBLE);
        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout);
        for(int i = 0; i < grid.getChildCount();i++ ){
            ImageView counter = (ImageView) grid.getChildAt(i);
            counter.setImageDrawable(null);
        }

    }

    private int determineWinner(){
        for(int i=0; i< winningPositions.length;i++){
            if(gameState[winningPositions[i][0]] != -1
                    && gameState[winningPositions[i][0]] == gameState[winningPositions[i][1]]
                    && gameState[winningPositions[i][1]] == gameState[winningPositions[i][2]])  {
                return gameState[winningPositions[i][0]];
            }
        }
        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}