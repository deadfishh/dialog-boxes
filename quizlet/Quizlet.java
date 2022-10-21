package quizlet;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Quizlet
{
    HashMap<String, String> items = new HashMap<String, String>();
    String[] options = {"dewey decimal system", "states and their capitals"};
    String word = "";
    String file = "";
    double right = 0;
    double wrong = 0;
    int[] sections = new int[10];
    int count = 0;
    String picFile = "";
    ImageIcon icon;
    JOptionPane bitches = new JOptionPane();

public void practice()
    {
        //JOptionPane eva = new JOptionPane("hello", 2, 2, icon);
        String topic = (String)JOptionPane.showInputDialog(null, "what do you want to practice today?", "rachel's quizlet", 3, null, options, options[0]);
        file = getTopic(topic);
        icon = new ImageIcon(picFile);
        if (topic.equals(options[0]))
        {
            getChecks();
        }
        makeMap(file);
        JOptionPane.showMessageDialog(null, "you will get three tries before i tell you the answer.", "rachel's quizlet", 3, icon);
        while (items.size() > 0)
        {
            Object[] keyRing = items.keySet().toArray();
            int randomNum = (int)(Math.random() * keyRing.length) ;
            Object key = keyRing[randomNum];
            String answer = JOptionPane.showInputDialog(null,  key + "\nyou have " + items.size() + " more " + word + "s left");
            determineCorrectness((String)key, answer);
        }
        JOptionPane.showMessageDialog(null, "congrats! you got all of them!", "rachel's quizlet", 3, icon);
        double score = right / (right + wrong) * 100;
        JOptionPane.showMessageDialog(null, "you're score is " + Math.round(score) + "%");
    }

    public void makeMap(String file)
    {
        try
        {
            FileReader reader = new FileReader(file);
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNext())
            {
                String line = scanner.nextLine();
                String key = getKey(line);
                String value = getValue(line);
                if (file.equals("quizlet/txt/dewey.txt"))
                {
                    int temp = Integer.parseInt(value.substring(0, 1));
                    if (sections[temp] == 1)
                    {
                        items.put(key, value);
                    }
                }
                else
                {
                    items.put(key, value);
                }
            }
            reader.close();
            scanner.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }

    }

    public String getKey(String line)
    {
        int comma = line.indexOf(", ");
        return line.substring(0, comma);
    }

    public String getValue(String line)
    {
        int comma = line.indexOf(", ");
        return line.substring(comma + 2);
    }

    public String getTopic(String topic)
    {
        switch (topic)
        {
            case "dewey decimal system":
                word = "genre";
                picFile = "quizlet/pics/books.png";
                return "quizlet/txt/dewey.txt";
            case "states and their capitals":
                word = "state";
                picFile = "quizlet/pics/flag.png";
                return "quizlet/txt/capitals.txt";
            case "phone numbers":
                word = "phone number";
                picFile = "quizlet/pics/phone.png";
                return "quizlet/txt/numbers.txt";
            default:
                return "";
        }
    }

    public void determineCorrectness(String key, String answer)
    {
        boolean giveUp = false;
        if (count == 2)
        {
            giveUp = true;
        }
        if (items.get(key).equals(answer))
        { 
            items.remove(key);
            if (count == 0)
            {
                right++;
            }
            count = 0;
        }
        else if (giveUp || answer.equals("give up"))
        {
            JOptionPane.showMessageDialog(null, "the answer is " + items.get(key), "rachel's quizlet", 3, icon);
            items.remove(key);
            if (count == 0)
            {
                wrong++;
            }
            count = 0;
        }
        else
        {
            //if (items.containsValue(answer))
            //{
                //JOptionPane.showMessageDialog(null, items.get(key) + " is the answer");
            //}
            //else
            {
                JOptionPane.showMessageDialog(null, "that's incorrect, try again", "error message", 3, icon);
            }
            if (count == 0)
            {
                wrong++;
            }
            count++;
            String newAnswer = JOptionPane.showInputDialog(null,  key + "\nyou have " + items.size() + " more " + word + "s left", "rachel's quizlet", 3);
            determineCorrectness(key, newAnswer);
        }
    }

    public int[] getSections()
    {
        int[] answer = {4};
        return answer;
    }

    public void getChecks()
    {
        JCheckBox box0 = new JCheckBox("000");
        JCheckBox box1 = new JCheckBox("100");
        JCheckBox box2 = new JCheckBox("200");
        JCheckBox box3 = new JCheckBox("300");
        JCheckBox box4 = new JCheckBox("400");
        JCheckBox box5 = new JCheckBox("500");
        JCheckBox box6 = new JCheckBox("600");
        JCheckBox box7 = new JCheckBox("700");
        JCheckBox box8 = new JCheckBox("800");
        JCheckBox box9 = new JCheckBox("900");
        Object[] params = {"rachel", box0, box1, box2, box3, box4, box5, box6, box7, box8, box9};
        JOptionPane.showMessageDialog(null, params, "selecting sections", JOptionPane.YES_NO_OPTION, icon);
        for (int i = 0; i < params.length; i++)
        {
            if (params[i] instanceof JCheckBox )
            {
                JCheckBox temp = (JCheckBox)params[i];
                if (temp.isSelected())
                {
                    sections[i - 1] = 1;
                }
                else
                {
                    sections[i - 1] = 0;
                }
            }
        }
    }
}
