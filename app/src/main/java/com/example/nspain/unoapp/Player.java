package com.example.nspain.unoapp;

import java.util.ArrayList;

/**
 * Most basic player for the game. This class is what is used to build the two different
 * player types; user controlled and random.
 */
abstract class Player {
    private ArrayList<Card> hand = new ArrayList<>();


    public ArrayList<Card> getHand() {
        return hand;
    }

    public void addToHand(Card card) {
        hand.add(card);
    }

    public Card playCard(Card card) {
        hand.remove(card);
        return card;
    }
}
