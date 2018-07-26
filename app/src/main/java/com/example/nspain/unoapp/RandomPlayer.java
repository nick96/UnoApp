package com.example.nspain.unoapp;

import java.util.ArrayList;
import java.util.Random;

/**
 * Representation of a player that randomly chooses moves. This is what is used as the
 * NPC opponenent for the user.
 */
class RandomPlayer extends Player {

    /**
     * Play a card randomly selected from all cards in the hand which can be legally
     * played.
     * @param faceUp Card which is face up on the pile.
     * @return Card chosen to be played.
     */
    public Card playCard(final Card faceUp) {
        ArrayList<Card> playableCards = new ArrayList<>();
        for (Card card : getHand()) {
            if (card.isCompatible(faceUp)) {
                playableCards.add(card);
            }
        }

        if (playableCards.size() == 0) {
            return null;
        }

        Random random = new Random();
        int index = random.nextInt(playableCards.size());
        Card card = playableCards.get(index);
        super.playCard(card);
        return card;
    }
}
