package com.example.nspain.unoapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    Game game = new Game();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPlayerMoveListeners();
        updateScreen();
    }

    /**
     * Update the current screen with the state of the game.
     */
    private void updateScreen() {
        if (game.isOver()) {
            Player winner = game.getWinner();
            if (winner == game.getPlayer()) {
                WinnerGameOverAlert alert = new WinnerGameOverAlert();
                alert.show(getFragmentManager(), "Winner");
            } else {
                LoserGameOverAlert alert = new LoserGameOverAlert();
                alert.show(getFragmentManager(), "Loser");
            }
        }

        State gameState = game.getState();
        setDeckCountText(gameState.deckSize);
        setFaceUpCardText(gameState.faceUpCard);
        setComputerHandSizeText(gameState.computerHandSize);
        setPlayerHandListView(gameState.playerHand);
    }

    private void setDeckCountText(int deckSize) {
        TextView deckCountTV = findViewById(R.id.deckCount);
        String deckCountText = String.format("There are %d cards in the deck", deckSize);
        deckCountTV.setText(deckCountText);
    }

    private void setFaceUpCardText(Card faceUpCard) {
        TextView topDeckTV = findViewById(R.id.topDeck);
        String topDeckText = String.format("The card on the top of the deck is %s",
                faceUpCard.toString());
        topDeckTV.setText(topDeckText);
    }

    private void setComputerHandSizeText(int handSize) {
        TextView oppCountTV = findViewById(R.id.opponentCount);
        String oppCountText = String.format("Your opponent has %d cards remaining", handSize);
        oppCountTV.setText(oppCountText);
    }

    /**
     * Set listens for various player actions.
     */
    private void setPlayerMoveListeners() {
        ListView playerLV = findViewById(R.id.playerCards);
        playerLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (game.isPlayerTurn()) {
                    Card clickedCard = (Card) parent.getAdapter().getItem(position);
                    if (game.isPlayable(clickedCard)) {
                        game.playerPlayCard(clickedCard);
                        if (!game.isOver()) {
                            game.computerPlayCard();
                        }
                        updateScreen();
                    } else {
                        IllegalMoveAlert illegalMoveAlert = new IllegalMoveAlert();
                        illegalMoveAlert.show(getFragmentManager(), "IllegalMove");
                    }
                }
            }
        });

        Button pickUpBtn = findViewById(R.id.pickUpBtn);
        pickUpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!game.playerCanPlay()) {
                    game.playerPickUp();
                    game.computerPlayCard();
                    updateScreen();
                } else {
                    IllegalPickupAlert illegalPickUpAlert = new IllegalPickupAlert();
                    illegalPickUpAlert.show(getFragmentManager(), "IllegalPickup");
                }
            }
        });
    }

    private void setPlayerHandListView(ArrayList<Card> hand) {
        ListView playerLV = findViewById(R.id.playerCards);
        ArrayAdapter handAdapter = new ArrayAdapter<>(this, R.layout.activity_handview, hand);
        playerLV.setAdapter(handAdapter);
    }
}