package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 1;
    int[] played = {0,0,0,0,0,0,0,0,0};
    int[][] winningPos = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view){
        GridLayout grid = findViewById(R.id.grid);
        ImageView piece = (ImageView) view;
        int tappedPos = Integer.parseInt(piece.getTag().toString())-1;

        if (!flag) {
            if (played[tappedPos] == 0) {
                played[tappedPos] = activePlayer;
                piece.setTranslationY(-1000f);
                if (activePlayer == 1) {
                    piece.setImageResource(R.drawable.yellow);
                    activePlayer = 2;
                } else {
                    piece.setImageResource(R.drawable.red);
                    activePlayer = 1;
                }
                piece.animate().translationYBy(1000f).setDuration(750);

                for (int[] i : winningPos) {
                    if (played[i[0]] != 0 && played[i[0]] == played[i[1]] && played[i[1]] == played[i[2]]) {
                        System.out.println(played[i[0]]);
                        flag = true;
                    }
                }

                boolean gameDraw = true;

                for (int j: played){
                    if (j==0){
                        gameDraw = false;
                        break;
                    }
                }

                if (gameDraw||flag){
                    ConstraintLayout popLayout =  findViewById(R.id.playPop);
                    ImageView popImageView = findViewById(R.id.popImageView);
                    popLayout.animate().alpha(1f).setDuration(2000);
                    popLayout.setVisibility(View.VISIBLE);
                    TextView winMsg = findViewById(R.id.winMessage);
                    if (flag){
                        String winner = "Red";
                        if (activePlayer == 2) {
                            winner = "Yellow";
                        }
                        winMsg.setText(winner+" has won the game!");
                        popImageView.setImageResource(R.drawable.congrats);
                    }
                    else if (gameDraw){
                        popImageView.setImageResource(R.drawable.blnt);
                        winMsg.setText(R.string.draw_message);
                    }
                    grid.animate().alpha(0f).setDuration(2000);
                }
            } else {
                Toast.makeText(MainActivity.this, "Please choose an empty Position.", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(MainActivity.this, "Game Over", Toast.LENGTH_LONG).show();
        }
    }

    public void playAgain(View view){
        flag = false;
        ConstraintLayout popLayout =  findViewById(R.id.playPop);
        popLayout.setVisibility(View.INVISIBLE);
        activePlayer = 1;
        for (int i=0 ; i<9 ; i++){
            played[i] = 0;
        }
        GridLayout grid = findViewById(R.id.grid);
        for (int i=0 ; i<grid.getChildCount() ; i++){
            ((ImageView) grid.getChildAt(i)).setImageResource(0);
        }
        grid.animate().alpha(1f).setDuration(2000);
    }
}
