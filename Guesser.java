import javax.swing.*;

public class Guesser
{
    public static void main(String[] args)
    {
        System.out.println("okay, you are going to try and guess the number now");
        int number = (int)(Math.random() * 100) + 1;
        int guess = 0;
        int count = 1;
        while (guess != number)
        {
            String response = JOptionPane.showInputDialog(null, "Enter a guess between 1 and 100", "Guessing Game", 3);
            guess = Integer.parseInt(response);
            JOptionPane.showMessageDialog(null, determineGuess(guess, number, count));
            count++;
        }
    }

    public static String determineGuess(int userAnswer, int computerNumber, int count)
    {

        if (userAnswer <= 0 || userAnswer > 100)
        {
            return "redo it, that's not in the range";
        }        
        else if (userAnswer == computerNumber)
        {
            return "correct! only after " + count + " guesses!";
        }        
        else if (userAnswer > computerNumber)
        {
            return "your guess is too high, try again.\ntry number: " + count;
        }
        else if (userAnswer < computerNumber)
        {
            return "your guess is too low, try again.\ntry number: " + count;
        }
        else
        {
            return "your guess is incorrect.\ntry number: " + count;
        }
    }
}

