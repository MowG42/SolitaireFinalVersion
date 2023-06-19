/**
 * This is the Card class for the Solitaire game. It handles the creating of cards, assigning their rank and suit, and checking if it is face up. It contains methods to access the variables and change the boolean variable isFaceUp.
 * @author goyal
 */
public class Card {
    //instance variables
    private final Suit SUIT;
    private final Rank RANK;
    private boolean isFaceUp;
    
    //methods
    /**
     * This is the constructor method for the Card class.
     * @param suit - the suit to be assigned to the card.
     * @param rank - the rank to be assigned to the card.
     */
    public Card(Suit suit, Rank rank) {
        this.SUIT = suit;
        this.RANK = rank;
        this.isFaceUp = false;
    } //end of constructor
    
    /**
     * This method gives the user the suit of the Card.
     * @return Suit - the suit of the Card.
     */
    public Suit getSuit() {
        return this.SUIT;
    } //end of getSuit method
    /**
     * This method gives the user the rank of the Card.
     * @return Rank - the rank of the Card.
     */
    public Rank getRank() {
        return this.RANK;
    } //end of getRank method
    
    /**
     * This method gives whether the Card is faced up or not.
     * @return Boolean - true or false based on if the Card is facing up.
     */
    public boolean getIsFaceUp() {
        return this.isFaceUp;
    } //end of getIsFaceUp method
    /**
     * This method sets the isFaceUp variable to true if the card is facing up in the game.
     */
    public void setIsFaceUp(boolean b) {
        this.isFaceUp = b;
    } //end of setIsFaceUp method
    
    /**
     * This method determines if the Card is from a red suit.
     * @return Boolean - true or false if card is red
     */
    public boolean isRed() {
        return this.SUIT == Suit.HEARTS || this.SUIT == Suit.DIAMONDS;
    } //end of isRed method
    /**
     * This method determines if the Card is from a black suit.
     * @return a Boolean true or false if card is black
     */
    public boolean isBlack() {
        return this.SUIT == Suit.CLUBS || this.SUIT == Suit.SPADES;
    } //end of isBlack method
    
    /**
     * This method determines if the card can be stacked on the card behind it.
     * @return a Boolean true or false if it can be stacked
     */
    public boolean stackable(Card card) {
        return this.isRed() != card.isRed();
    } //end of stackable method
    
    /**
     * This method gives the name of each card.
     * @return a string that has two letters- the first letter of the rank and the first letter of the suit.
     */
    public String getName() {
        String number = ""; //variable declaration
        switch(RANK) { //switch to assign rank name
            case ACE -> number = "A"; //case
            case TWO -> number = "2"; //case
            case THREE -> number = "3"; //case
            case FOUR -> number = "4"; //case
            case FIVE -> number = "5"; //case
            case SIX -> number = "6"; //case
            case SEVEN -> number = "7"; //case
            case EIGHT -> number = "8"; //case
            case NINE -> number = "9"; //case
            case TEN -> number = "10"; //case
            case JACK -> number = "J"; //case
            case QUEEN -> number = "Q"; //case
            case KING -> number = "K"; //case
        } //end of switch
        String suitName = ""; //variable declaration
        switch(SUIT) { //switch to assign suit name
            case HEARTS -> suitName = "H"; //case
            case DIAMONDS -> suitName = "D"; //case
            case SPADES -> suitName = "S"; //case
            case CLUBS -> suitName = "C"; //case
        } //end of switch
        return number + suitName; //return statement
    } //end of getName method
    
    /**
     * This method gives the path to a corresponding image.
     * @return String - returns the path of the image corresponding with the card.
     */
    public String getImagePath() {
        return "C:\\ICS3U_SUMMATIVE_SOLITAIRE\\src\\cards\\" + getName().substring(1,getName().length()) + "\\" + getName() + ".jpg"; //return statement
    } //end of getImage method
    /**
     * This method gives the file path to the back of the card image.
     * @return String the file path to the card back image
     */
    public static String getBackImagePath() {
        return "C:\\ICS3U_SUMMATIVE_SOLITAIRE\\src\\CardBack.jpg";
    } //end of getImageBackPath method
    
    @Override
    /**
     * This method returns the description about the instance variables.
     * @return String - rank and suit of card along with image path.
     */
    public String toString() {
        return this.RANK + " of " + this.SUIT + " ---- " + getImagePath();
    }
} //end of Card class
