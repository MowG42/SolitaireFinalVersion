/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author goyal
 */

//imports
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    
    //instance variables
    private final ArrayList<Card> cards;
    private int drawCardIndex;
    
    //methods
    /**
     * This constructor for the Deck class creates all 52 cards and adds them to cards List.
     */
    public Deck() {
        cards = new ArrayList<>(); //creating new deck
        
        for (Suit suit: Suit.values()) { //loops through suits
            for (Rank rank: Rank.values()) { //loops through ranks
                cards.add(new Card(suit, rank)); //adds a unique Card to cards
            }
        }
        drawCardIndex = 0; //initializes index
    } //end of constructor
    
    /**
     * This method returns the size of the deck.
     * @return int - size of deck
     */
    public int getSize() {
        return cards.size();
    } //end of getSize method
    
    /**
     * This method returns a drawn card in the deck of cards.
     * @return Card - a new drawn card in the deck of cards.
     */
    public Card drawCard() {
        if (drawCardIndex<cards.size()) { //checks if all of the cards have been drawn
            Card card = cards.get(drawCardIndex); //draws a card
            drawCardIndex++; //increments
            return card; //return statement
        }
        return null; //returns null if all of the cards have already been drawn
    } //end of drawCard method
    
    /**
     * This method shuffles the deck of cards.
     */
    public void shuffleDeck() {
        Collections.shuffle(cards); //mixes ArrayList
        resetDrawCardIndex(); //resets the drawCardIndex variable
    } //end of shuffleDeck method
    
    /**
     * This method resets the drawCardIndex.
     */
    public void resetDrawCardIndex() {
        drawCardIndex = 0;
    } //end of resetDrawCardIndex method
    
    /**
     * This method removes a card from the deck.
     * @param card the card to be removed from the deck
     */
    public void removeCardFromDeck(Card card) {
        //checks if the stack is empty
        cards.remove(card); //removes the card from the deck
    } //end of removeCardFromStack method
    
    /**
     * This method checks if all of the cards have been drawn.
     * @return Boolean true or false based on if all the cards have been drawn or not.
     */
    public boolean isEmpty() {
        return cards.size() <= drawCardIndex;
    } //end of isEmpty method
    
} //end of Deck class
