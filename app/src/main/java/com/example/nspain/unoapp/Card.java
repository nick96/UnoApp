package com.example.nspain.unoapp;

/**
 * Representation of the cards used in this game.
 */
class Card {

    private CardColour colour;
    private int value;

    public Card(CardColour colour, int value) {
        this.colour = colour;
        this.value = value;
    }

    @Override
    public String toString() {
        String colourString;
        switch (colour) {
            case RED:
                colourString = "Red";
                break;
            case BLUE:
                colourString = "Blue";
                break;
            case GREEN:
                colourString = "Green";
                break;
            case YELLOW:
                colourString = "Yellow";
                break;
            default:
                colourString = "Unknown";
        }
        return String.format("%s %d", colourString, value);
    }

    public CardColour getColour() {
        return colour;
    }

    public int getValue() {
        return value;
    }

    /**
     * Check if this card can be player whilst the other is on the top of the pile and vice versa.
     * @param other Card to check this one against.
     * @return True if the cards can be played together, otherwise false.
     */
    public boolean isCompatible(Card other) {
        return other.getValue() == getValue() || other.getColour() == getColour();
    }
}
