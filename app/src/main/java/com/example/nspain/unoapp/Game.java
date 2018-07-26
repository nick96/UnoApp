package com.example.nspain.unoapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Representation of the current game state and provides a way for the app activity to interface
 * with the game in a controlled manor.
 */
public class Game {
    private static final int HAND_SIZE = 7;
    private static final int MIN_CARD_VAL = 0;
    private static final int MAX_CARD_VAL = 9;

    private UserControlledPlayer player = new UserControlledPlayer();
    private RandomPlayer computer = new RandomPlayer();
    private boolean playerTurn = true;
    private Stack<Card> deck = new Stack<>();
    private Stack<Card> pile = new Stack<>();

    public Game() {
        createDeck();
        deal();
    }

    /**
     * Create all the cards in the deck and shuffle them.
     */
    private void createDeck() {
        deck.clear();
        ArrayList<Card> cardList = new ArrayList<>();
        for (CardColour colour: CardColour.values()) {
            for (int value = MIN_CARD_VAL; value <= MAX_CARD_VAL; value++) {
                Card card1 = new Card(colour, value);
                Card card2 = new Card(colour, value);
                cardList.add(card1);
                cardList.add(card2);
            }
        }
        Collections.shuffle(cardList);
        deck.addAll(cardList);
    }

    /**
     * Deal cards to the two players.
     */
    private void deal() {
        for (int i = 0; i < HAND_SIZE; i++) {
            player.addToHand(nextCardInDeck());
            computer.addToHand(nextCardInDeck());
        }
        pile.push(nextCardInDeck());
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    /**
     * Check that it is within the rules for card to be played.
     * @param card Card to be checked.
     * @return True if card is playable, otherwise false.
     */
    public boolean isPlayable(Card card) {
        Card top = pile.peek();
        return top.isCompatible(card);
    }

    public void playerPlayCard(Card card) {
        player.playCard(card);
        pile.push(card);
        playerTurn = !isPlayerTurn();
    }

    /**
     * Create and return an immuatable State object representing the current game state.
     * @return Immutable State object representing the game state.
     */
    public State getState() {
        return new State(deck.size(), pile.peek(), computer.getHand().size(), player.getHand());
    }

    /**
     * Make the player pickup a card and change the turn to the computer.
     */
    public void playerPickUp() {
        player.addToHand(nextCardInDeck());
        playerTurn = !isPlayerTurn();
    }

    /**
     * Get the computer's next card and play it. If the computer can't play a card then it is made
     * to pick up.
     */
    public void computerPlayCard() {
        Card card = computer.playCard(pile.peek());
        if (card != null) {
            pile.push(card);
        } else {
            Card pickUpCard = nextCardInDeck();
            computer.addToHand(pickUpCard);
        }
        playerTurn = !isPlayerTurn();
    }

    public boolean isOver() {
        return player.getHand().size() == 0 || computer.getHand().size() == 0;
    }

    public Player getWinner() {
        if (!isOver()) {
            return null;
        } else if (player.getHand().size() == 0 && computer.getHand().size() != 0) {
                return player;
        } else if (computer.getHand().size() == 0 && player.getHand().size() != 0) {
                return computer;
        }
        return null;
    }

    public UserControlledPlayer getPlayer() {
        return player;
    }

    /**
     * Check if the player is able to play any cards.
     * @return True if the player can player cards in his hand, otherwise false.
     */
    public boolean playerCanPlay() {
        for (Card card: player.getHand()) {
            if (isPlayable(card)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Pickup the next card in the deck. This method is slightly complex because it handles the case
     * where the deck is out of cards. When this happens, we use all but the top card from the pile.
     * Before using the pile cards again we shuffle them.
     * @return Next card in the deck.
     */
    private Card nextCardInDeck() {
        if (deck.size() == 0) {
            Card topPile = pile.pop();

            ArrayList<Card> newDeck = new ArrayList<>();
            while(pile.size() > 0) {
                newDeck.add(pile.pop());
            }
            Collections.shuffle(newDeck);
            deck.addAll(newDeck);
            pile.push(topPile);
        }

        return deck.pop();
    }
}
