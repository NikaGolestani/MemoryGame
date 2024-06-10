package MemoryGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.Timer;

public class ShuffleThread extends Thread {
    private List<Card> row;

    public ShuffleThread(List<Card> cards) {
        this.row = cards;
    }

    @Override
    public void run() {
        shuffleUnlockedCards();
        revealCardsTemporarily();
    }

    private void shuffleUnlockedCards() {
        // Separate locked and unlocked cards
        List<Card> unlockedCards = new ArrayList<>();
        for (Card card : row) {
            if (!card.getLock()) {
                unlockedCards.add(card);
            }
        }

        Collections.shuffle(unlockedCards);
        int unlockedIndex = 0;
        for (int i = 0; i < row.size(); i++) {
            if (!row.get(i).getLock()) {
                row.set(i, unlockedCards.get(unlockedIndex++));
            }
        }
    }

    private void revealCardsTemporarily() {
        // Reveal all cards temporarily
        for (Card card : row) {
            card.setHidden(false);
        }

        // Start a timer to hide the cards after a delay
        Timer timer = new Timer(2000, e -> hideUnlockedCards());
        timer.setRepeats(false);
        timer.start();
    }

    private void hideUnlockedCards() {
        for (Card card : row) {
            if (!card.getLock()) {
                card.setHidden(true);
            }
        }
    }
}
