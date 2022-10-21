package quizlet;
public class Country
{
    String name;
    String capital;
    public Country(String theName)
    {
        name = theName;
    }

    public Country (String theName, String theCapital)
    {
        name = theName;
        capital = theCapital;
    }

    public void setName(String theName)
    {
        name = theName;
    }

    public void setCapital(String theCapital)
    {
        capital = theCapital;
    }

    public String getName()
    {
        return name;
    }

    public String getCapital()
    {
        if (capital != null)
        {
            return capital;
        }
        return "there is no capital given";
    }
}
