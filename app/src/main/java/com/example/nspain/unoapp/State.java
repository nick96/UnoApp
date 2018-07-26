package com.example.nspain.unoapp;

import java.util.ArrayList;

/**
 * Class for immutable state objects.
 */
class State {
    public final int deckSize;
    public final Card faceUpCard;
    public final int computerHandSize;
    public final ArrayList<Card> playerHand;

    public State(int deckSize, Card faceUpCard, int computerHandSize, ArrayList<Card> playerHand) {
        this.deckSize = deckSize;
        this.faceUpCard = faceUpCard;
        this.computerHandSize = computerHandSize;
        this.playerHand = playerHand;
    }
}
