import java.util.*;
import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.Duration;

public class Listing
{
    public int count = 0;
    public ArrayList<String> list = new ArrayList<String>();
    public ArrayList<String> original = new ArrayList<String>();
    public boolean continuePlaying = true;
    public String[] options = {"practice", "countries", "presidents", "states"};
    String word = "";

    public void play()
    {
        String file = "";
        String topic = (String)JOptionPane.showInputDialog(null, "what do you want to guess today?", "guessing game", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        file = getTopic(topic);
        makeList(list, file);
        makeList(original, file);
        LocalTime startTime = java.time.LocalTime.now();
        while (count < list.size())
        {
            String guess = JOptionPane.showInputDialog(null,  "enter a " + word + "\nyou have " + list.size() + " more " + word + "s left", "guessing game", 3);
            determineGuess(guess);
        }
        LocalTime endTime = java.time.LocalTime.now();
        JOptionPane.showMessageDialog(null, "you win! slay!");
        int time = (int)Duration.between(startTime, endTime).getSeconds();
        JOptionPane.showMessageDialog(null, "this took you " + formatTime(time));
        String name = JOptionPane.showInputDialog(null, "write down your name so i can add it to the scoreboard", "guessing game", 3);
        Score newScore = new Score(name, time, topic);
        fixFile(newScore);
        JOptionPane.showMessageDialog(null, scoreboard(topic));
    }

    public void makeList(ArrayList<String> nick, String file)
    {
        try
        {
            FileReader reader = new FileReader(file);
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNext())
            {
                String temp = scanner.nextLine();
                nick.add(temp);
            }
            reader.close();
            scanner.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }

    // writing in the file to add the latest score
    public void fixFile(Score score)
    {
        try
        {
            FileWriter writer = new FileWriter("txt/scores.txt", true);
            writer.write(score.toString());
            writer.close();

            FileReader reader = new FileReader("txt/scores.txt");
            Scanner scanner = new Scanner(reader);

            ArrayList<Score> scores = new ArrayList<Score>();

            while (scanner.hasNext())
            {
                String line = scanner.nextLine();
                String name = findName(line);
                int time = findTime(line);
                String game = findGame(line);
                Score s = new Score(name, time, game);
                scores.add(s);
            }
            scanner.close();

            Collections.sort(scores);

            FileWriter writer2 = new FileWriter("txt/scores.txt");
            for (Score s : scores)
            {
                writer2.write(s.toString());
            }
            writer2.close();
            
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }

    public String scoreboard(String game)
    {
        String result = "scoreboard:\n";
        try
        {
            FileReader reader = new FileReader("txt/scores.txt");
            Scanner scanner = new Scanner(reader);
            int count = 0;
            while (scanner.hasNext())
            {
                String line = scanner.nextLine();
                if (findGame(line).equals(game))
                {
                    result += findName(line) + ": " + formatTime(findTime(line)) + "\n";
                    count++;
                }
                if (count >= 3)
                {
                    break;
                }

            }
            reader.close();
            scanner.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        return result;
    }

    // parsing the time from the scores.txt file
    public int findTime(String line)
    {
        int comma = line.indexOf(", ");
        String str = line.substring(0, comma);
        return Integer.parseInt(str);
    }

    // finding the name from the scores.txt file
    public String findName(String line)
    {
        int firstComma = line.indexOf(", ");
        int secondComma = line.indexOf(", ", firstComma + 1);
        return line.substring(firstComma + 2, secondComma);
    }

    // finding the game played in the scores.txt file
    public String findGame(String line)
    {
        int firstComma = line.indexOf(", ");
        int secondComma = line.indexOf(", ", firstComma + 1);
        return line.substring(secondComma + 2);
    }

    // setting the txt file to match the chosen topic
    public String getTopic(String topic)
    {
        switch (topic)
        {
            case "countries":
                word = "country";
                return "txt/countries.txt";
            case "presidents":
                word = "president";
                return "txt/presidents.txt";
            case "states":
                word = "state";
                return "txt/states.txt";
            case "practice":
                word = "name";
                return "txt/practice.txt";
            default:
                JOptionPane.showMessageDialog(null, "you didn't select anything, dipshit.");
                return "";
        }
    }

    // turning the amount of seconds into a string that gets displayed
    public String formatTime(int time)
    {
        String result = "";
        if (time < 60)
        {
            result = time + " seconds";
        }
        else
        {
            int minutes = time / 60;
            int seconds = time % 60;
            result += minutes + ":";
            if (seconds < 10)
            {
                result += "0";
            }
            result += seconds;
        }
        return result;
    }

    // determining if the guess is valid
    public void determineGuess(String guess)
    {
        if (guess.equals("quit")) 
        {
            JOptionPane.showMessageDialog(null, "you have quit.");
            JOptionPane.getRootFrame().dispose();
            count += 10;
            continuePlaying = false;
        }
        else if (guess.equals("give up"))
        {
            JOptionPane.showMessageDialog(null, "here are the rest of the " + word + "s");
            String rest = "";
            for (String piece : list)
            {
                rest += piece + "\n";
            }
            JOptionPane.showMessageDialog(null, rest);
            count += 10;
            continuePlaying = false;
        }
        else if (list.indexOf(guess) == -1)
        {
            if (original.indexOf(guess) != -1)
            {
                JOptionPane.showMessageDialog(null, "you already guessed that one!");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "that is not a valid " + word + ".");
            }
        }
        else
        {
            list.remove(guess);
        }
    }
}
