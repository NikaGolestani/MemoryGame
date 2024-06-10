package MemoryGame;

public class Delay extends Thread {
    @Override
    public void run() {
        try {
            // Sleep for 5000 milliseconds (5 seconds)
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // If interrupted while sleeping, print an error message
            System.err.println("An error occurred at delay.");
        }
    }
}
