import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.logging.*;

public class Test extends JPanel implements MouseListener, MouseMotionListener {
    
    //instance variables
    private static final int CARD_WIDTH = 73;
    private static final int CARD_HEIGHT = (int)(CARD_WIDTH*1.49137931+.5);
    private static final int MAIN_STACKS_X_DIFF = 35;
    private static final int MAIN_STACKS_Y_DIFF = 30;
    private static final int TOP_STACKS_X_DIFF = 10;
    private static final int TOP_STACKS_Y_DIFF = 10;
    
    private final Dimension cardSize = new Dimension(CARD_WIDTH, CARD_HEIGHT);
    
    private JPanel stack, newStack, wasteStack;
    private JLabel wasteCardLabel, newCardLabel;
    private final List<JPanel> stacks, tStacks;
        
    private Component card;
    private int xAdj, yAdj;
    
    private Card wasteCard;
    private Deck deckOfCards;
    
    private Map<JLabel, Card> cardMap;
        
    //methods
    
    public Test() {
        JFrame gameScreen = new JFrame();
        gameScreen.setTitle("Solitaire");
        gameScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameScreen.add(this);
        
        deckOfCards = new Deck();
        deckOfCards.shuffleDeck();
        cardMap = new HashMap<>();
        
        JPanel topWrapper = new JPanel(); //JPanel for newStack, wasteStack, and topStacks  
        topWrapper.setBackground(new Color(0,153,0));
        
        tStacks = new ArrayList<>(4);
        try {
            topWrapper.add(makeNewStack());
            topWrapper.add(makeWasteStack());
            topWrapper.add(makeTopStacks());
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        gameScreen.add(topWrapper, BorderLayout.NORTH);
        
        stacks = new ArrayList<>(7);
        try {
            gameScreen.add(makeMainStacks());
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setLayout(null);
        gameScreen.setSize(800,600);
        gameScreen.setResizable(false);
        gameScreen.getContentPane().setBackground(new Color(0,153,0));
        gameScreen.setVisible( true );
    }
        
    public JPanel makeMainStacks() throws IOException {
        JPanel mainStacks = new JPanel(new GridLayout(0, 7, MAIN_STACKS_X_DIFF, MAIN_STACKS_Y_DIFF));
        for (int stack = 0; stack < 7; stack++) {

            JPanel mainStack = new JPanel(new OverlapLayout(new Point(0, 22)));
            mainStack.setBounds(10, 0, CARD_WIDTH, CARD_HEIGHT);
            mainStack.setBorder(
                    new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 0, CARD_WIDTH, CARD_HEIGHT))
            );
            mainStack.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT + 250));
            mainStack.setBackground(new Color(0,153,0));
            mainStacks.add(mainStack);

            stacks.add(mainStack);

            for (int cardInStack = 0; cardInStack <= stack; cardInStack++) {

                Card placementCard = deckOfCards.drawCard(); //draws card
                placementCard.setIsFaceUp(false);
                //checking if the card is the last card in the stack
                if (stack == cardInStack) {
                    System.out.println(stack + cardInStack);
                    placementCard.setIsFaceUp(true); //card is facing up
                    //sizes the image and sets it to placementCard
                    ImageIcon placementCardFace = new ImageIcon(placementCard.getImagePath());
                    Image placementCardScaled = placementCardFace.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
                    JLabel cardLabel = new JLabel(new ImageIcon(placementCardScaled));
                    cardMap.put(cardLabel, placementCard);
                    mainStack.add(cardLabel);
                } //end of if statement

                //if statement to check whether the card should be revealed or not
                if (!placementCard.getIsFaceUp()) { //hide cardFront
                    //sizes the image and sets it to placementCard
                    ImageIcon placementCardBack = new ImageIcon(Card.getBackImagePath());
                    Image placementCardScaled = placementCardBack.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
                    JLabel cardLabel = new JLabel(new ImageIcon(placementCardScaled));
                    cardLabel.setPreferredSize(cardSize);
                    cardMap.put(cardLabel, placementCard);
                    mainStack.add(cardLabel);
                } //end of if statement
            }
            mainStack.addMouseListener(this);
            mainStack.addMouseMotionListener(this);
        }

        JPanel mainStacksWrapper = new JPanel();
        mainStacksWrapper.add(mainStacks);
        mainStacksWrapper.setBackground(new Color(0, 153, 0));
        mainStacks.setBackground(new Color(0, 153, 0));
        return mainStacksWrapper;
    }
    
    public JPanel makeTopStacks() throws IOException {
        JPanel topStacks = new JPanel(new GridLayout(0, 7, TOP_STACKS_X_DIFF, TOP_STACKS_Y_DIFF));
        
        for (int stack = 0; stack < 4; stack++) {
            JPanel topStack = new JPanel(new OverlapLayout(new Point(0, 22)));
            topStack.setBounds(10, 0, CARD_WIDTH, CARD_HEIGHT);
            topStack.setBorder(
                    new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 0, CARD_WIDTH, CARD_HEIGHT))
            );
            topStack.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
            topStack.setBackground(new Color(0,153,0));
            topStacks.add(topStack);
            topStack.addMouseListener(this);
            topStack.addMouseMotionListener(this);

        }
        
        JPanel topStacksWrapper = new JPanel();
        topStacksWrapper.add(topStacks);
        topStacksWrapper.setBackground(new Color(0, 153, 0));
        topStacks.setBackground(new Color(0, 153, 0));
        return topStacksWrapper;
    }
    
    public JPanel makeNewStack() throws IOException {
        newStack = new JPanel();
        newCardLabel = new JLabel();
        newStack.setBackground(new Color(0,153,0));
        ImageIcon cardBack = new ImageIcon(Card.getBackImagePath());
        Image cardBackScaled = cardBack.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
        newCardLabel.setIcon(new ImageIcon(cardBackScaled));
        newCardLabel.addMouseListener(new newStackOnClick());
        newStack.add(newCardLabel);
        return newStack;
    }
    
    public JPanel makeWasteStack() throws IOException {
        wasteStack = new JPanel();
        wasteCardLabel = new JLabel();
        
        wasteCard = deckOfCards.drawCard(); //new card from pile to possibly add to mainStacks
        wasteCard.setIsFaceUp(true); //sets card facing up     
        
        ImageIcon wasteCardFace = new ImageIcon(wasteCard.getImagePath());
        Image wasteCardScaled = wasteCardFace.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
        wasteCardLabel.setIcon(new ImageIcon(wasteCardScaled));
        cardMap.put(wasteCardLabel, wasteCard);
        
        wasteStack.add(wasteCardLabel);
        wasteStack.addMouseListener(this);
        wasteStack.addMouseMotionListener(this);
        wasteStack.setBackground(new Color(0,153,0));
        
        JPanel wrapper = new JPanel();
        wrapper.add(wasteStack);
        wrapper.setBackground(new Color(0,153,0));
        return wrapper;
    }
    
    
    @Override
    public void mousePressed(MouseEvent e) {
        try {
        stack = (JPanel)e.getComponent();
        card = stack.getComponent(0);
                
        Point stackPos = card.getParent().getLocation();
        xAdj = stackPos.x-e.getX();
        yAdj = stackPos.y-e.getY();
        card.setLocation(e.getX() + xAdj + 35, e.getY() + yAdj);
        
        JLayeredPane lp = getRootPane().getLayeredPane();
        lp.add(card,JLayeredPane.DRAG_LAYER);
        setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        } catch (ArrayIndexOutOfBoundsException err) {
            System.err.println("No card in stack. Cannot execute move.");
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        if (card==null){}
        
        int x = e.getX() + xAdj + 35;
        int y = e.getY() + yAdj;
        card.setLocation(x,y);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        setCursor(null);

        if (card == null) {
            return;
        }

        //  Make sure the card is no longer painted on the layered pane
        card.setVisible(false);

        JPanel stackPanel = null;
        for (JPanel stack : stacks) {
            // Check the bounds of each stack against it's parents
            Point localPoint = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), stack.getParent()); //coordinates context
            if (stack.getBounds().contains(localPoint)) {
                stackPanel = stack;
                break;
            }
        }

        if (stackPanel == null || stackPanel == stack) {
            stack.add(card);
            
        } else if (movableToMainStack((JLabel)card, stackPanel)) {
                if (stack.getComponentCount() > 0) {
                    JLabel nextTopCardLabel = (JLabel)stack.getComponent(0);
                    Card nextTopCard = getCardFromLabel(nextTopCardLabel);
                    nextTopCard.setIsFaceUp(true);
                    ImageIcon nextTopCardFace = new ImageIcon(nextTopCard.getImagePath());
                    Image nextTopCardScaled = nextTopCardFace.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
                    nextTopCardLabel.setIcon(new ImageIcon(nextTopCardScaled));
                }
                stackPanel.add(card);
                stackPanel.revalidate();
                stackPanel.repaint();
        } else if (movableToTopStack((JLabel)card, stackPanel)) {
            if (stack.getComponentCount() > 0) {
                JLabel nextTopCardLabel = (JLabel)stack.getComponent(0);
                Card nextTopCard = getCardFromLabel(nextTopCardLabel);
                nextTopCard.setIsFaceUp(true);
                ImageIcon nextTopCardFace = new ImageIcon(nextTopCard.getImagePath());
                Image nextTopCardScaled = nextTopCardFace.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
                nextTopCardLabel.setIcon(new ImageIcon(nextTopCardScaled));
            }
            stackPanel.add(card);
            stackPanel.revalidate();
            stackPanel.repaint();
            
            
        } else {
            stack.add(card);
            System.err.println("Invalid Move. Cards are not stackable.");
        }

        card.setVisible(true);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    
    public Card getCardFromLabel(JLabel cardLabel) {
        return cardMap.get(cardLabel);
    } //end of getCardFromLabel method
    
    public JLabel getTopMStackCardLabel(JPanel stack) {
        JLabel cardLabel = (JLabel)stack.getComponent(0);
        return cardLabel;
    }
    public Card getTopMStackCard(JPanel stack) {
        JLabel cardLabel = getTopMStackCardLabel(stack);
        if (stack.getComponentCount() > 0) {
            return getCardFromLabel(cardLabel);
        }
        return null;
    }
    private boolean movableToMainStack(JComponent cardComponent, JPanel stack) {
        if (cardComponent instanceof JLabel) {
            JLabel cardLabel = (JLabel)cardComponent;
            Card card = getCardFromLabel(cardLabel);
            if (stack.getComponentCount() > 0) {
                Card topCard = getTopMStackCard(stack);
                System.out.println(card.getRank().ordinal() + ", " + topCard.getRank().ordinal() + ", " + card.stackable(topCard));
                
                return card.getRank().ordinal() == topCard.getRank().ordinal() - 1 && card.stackable(topCard);
            } else {
                return card.getRank() == Rank.KING;
            }
        }
        return false;
    }
    
    public Card getTopTStackCard(JPanel stack) {
        JLabel cardLabel = getTopTStackCardLabel(stack);
        if (stack.getComponentCount() > 0) {
            return getCardFromLabel(cardLabel);
        }
        return null;
    } //end of getTopTStackCard method
    public JLabel getTopTStackCardLabel(JPanel stack) {
        JLabel cardLabel = (JLabel)stack.getComponent(0);
        return cardLabel;
    }
    private boolean movableToTopStack(JComponent cardComponent, JPanel stack) {
        if (cardComponent instanceof JLabel) {
            JLabel cardLabel = (JLabel)cardComponent;
            Card card = getCardFromLabel(cardLabel);
            Card topCard = getTopTStackCard(stack);

            if (topCard != null) {
                System.out.println(card.getSuit().equals(topCard.getSuit()) && card.getRank().ordinal() == topCard.getRank().ordinal() + 1);
                return card.getSuit().equals(topCard.getSuit()) && card.getRank().ordinal() == topCard.getRank().ordinal() + 1;
            } //end of if statement
            System.out.println(card.getRank().ordinal() == 0);
            return card.getRank().ordinal() == 0; //checks for ACE
        }
        return false;
    } //end of movableToTopStack method
    
    private void newStackClick() {
        if(!deckOfCards.isEmpty()) {
            wasteCard = deckOfCards.drawCard();
            System.out.println(wasteCard);
            wasteCard.setIsFaceUp(true);
            
            ImageIcon wasteCardFace = new ImageIcon(wasteCard.getImagePath());
            Image wasteCardScaled = wasteCardFace.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
            wasteCardLabel.setIcon(new ImageIcon(wasteCardScaled));
            cardMap.put(wasteCardLabel, wasteCard);
        } else {
            resetNewStack();
        } //end of if statement
    } //end of newStackClick method
    
    private void resetNewStack() {
        deckOfCards.resetDrawCardIndex();
        deckOfCards.shuffleDeck();
        wasteCard = deckOfCards.drawCard();
        wasteCard.setIsFaceUp(true);
        
        ImageIcon wasteCardFace = new ImageIcon(wasteCard.getImagePath());
        Image wasteCardScaled = wasteCardFace.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
        wasteCardLabel.setIcon(new ImageIcon(wasteCardScaled));
    } //end of resetNewStack method
    
    private class newStackOnClick extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            newStackClick();
        } //end of mouseClicked method
    } //end of newStackOnClick class
    
    public static void main(String[] args) {
        new Test();
    }
}