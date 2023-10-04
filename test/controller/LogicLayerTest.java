package controller;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Assert.*;

import mocks.MockConnection;
import mocks.MockDatabaseUtilities;
import model.Country;
import model.Language;

public class LogicLayerTest {

    private MockConnection connection = new MockConnection();
    private MockDatabaseUtilities utilities = MockDatabaseUtilities.getInstance();
    private WriteLogic logic = WriteLogic.getInstance(connection, utilities);

    private Country testCountry = new Country("name", 100, 10, "continent");
    private Language testLanguage = new Language("name");

    @Before
    public void setup(){
        utilities.reset();
    }

    @Test
    public void getFullCountryTableResultSet(){
        String expected = "SELECT * From Country ORDER BY Name";
        logic.getFullCountryTableResultSet();
        checkLastSqlUsed(expected);
    }
    
    @Test
    public void getFullLanguageTableResultSet(){
        String expected = "SELECT * From Language ORDER BY Name";
        logic.getFullLanguageTableResultSet();
        checkLastSqlUsed(expected);
    }

    @Test
    public void getCorrespondingCountriesTableResultSet(){
        String expected = "SELECT Country.* FROM Country INNER JOIN CountryLanguageRelationShip ON Country.ID = CountryLanguageRelationShip.CountryID WHERE CountryLanguageRelationShip.LanguageID = " + 12351;
        logic.getCorrespondingCountriesTableResultSet(12351);
        checkLastSqlUsed(expected);
    }

    @Test
    public void getCorrespondingLanguagesTableResultSet(){
        String expected = "SELECT Language.* "
        + "FROM Language INNER JOIN CountryLanguageRelationShip "
        + "ON Language.ID = CountryLanguageRelationShip.LanguageID "
        + "WHERE CountryLanguageRelationShip.CountryID = " + 9818374;
        logic.getCorrespondingLanguagesTableResultSet(9818374);
        checkLastSqlUsed(expected);
    }

    @Test
    public void getCountry(){
        String expected = "SELECT * FROM Country WHERE ID = 12345";
        logic.getCountry("12345");
        checkLastSqlUsed(expected);
    }

    @Test
    public void getLanguage(){
        String expected = "SELECT * FROM Language WHERE ID = 1233";
        logic.getLanguage("1233");
        checkLastSqlUsed(expected);
    }

    @Test
    public void getCountryID(){
        String expected = "SELECT ID FROM Country WHERE NAME = 'abcd'";
        logic.getCountryID("abcd");
        checkLastSqlUsed(expected);
    }

    @Test
    public void getLanguageID(){
        String expected = "SELECT ID FROM Language WHERE NAME = 'abcd'";
        logic.getLanguageID("abcd");
        checkLastSqlUsed(expected);
    }

    @Test
    public void getAllLanguages(){
        String expected = "SELECT Language.ID " + 
        "FROM [Language] INNER JOIN CountryLanguageRelationship ON Language.ID = CountryLanguageRelationship.LanguageID " + 
        "WHERE (((CountryLanguageRelationship.CountryID)='"+12345+"'));";
        logic.getAllLanguages("12345");
        checkLastSqlUsed(expected);
    }

    @Test
    public void getAllCountries(){
        String expected = "SELECT Country.ID "+
        "FROM Country INNER JOIN CountryLanguageRelationship ON Country.ID = CountryLanguageRelationship.CountryID "+
        "WHERE (((CountryLanguageRelationship.LanguageID)='"+790871234+"'));";
        logic.getAllCountries("790871234");
        checkLastSqlUsed(expected);
    }

    @Test
    public void addCountry(){
        String expected = "INSERT INTO "+ "Country" +"("
            + "Name,"
            + "Population,"
            + "Area,"
            + "Continent,"
            + "OfficialLanguages)"
            + "VALUES("
            + "'" + testCountry.getName() + "',"
            + "'" + testCountry.getPopulation().intValue() +"',"
            + "'" + testCountry.getArea().intValue() + "',"
            + "'" + testCountry.getContinent() + "',"
            + "'" +0+ "');";
        logic.addCountry(testCountry);
        checkLastSqlUsed(expected);
    }

    @Test
    public void addLanguage(){
        String expected = "INSERT INTO Language("
            + "Name,"
            + "OfficialIn"
            + ")"
            + "VALUES("
            + "'" + testLanguage.getName() + "',"
            + "'" +0+ "');";
        logic.addLanguage(testLanguage);
        checkLastSqlUsed(expected);
    }

    @Test
    public void deleteCountry(){
        String expected = "DELETE FROM Country WHERE "
        + "Name = '" + testCountry.getName() + "' AND "
        + "Population = '" + testCountry.getPopulation().intValue() + "' AND "
        + "Area = '" + testCountry.getArea().intValue() + "' AND "
        + "Continent = '" + testCountry.getContinent() + "';";
        logic.deleteCountry(testCountry);
        checkLastSqlUsed(expected);
    }

    @Test
    public void deleteLanguage(){
        String expected = "DELETE FROM Language WHERE "
        + "Name = '" + testLanguage.getName() + "';";
        logic.deleteLanguage(testLanguage);
        checkLastSqlUsed(expected);
    }

    @Test
    public void addInFullCountry(){
        ArrayList<String> expected = new ArrayList<>();
        expected.add("SELECT ID FROM Country WHERE NAME = '"+testCountry.getName()+"'");
        expected.add(
            "INSERT INTO "+ "Country" +"("
            + "Name,"
            + "Population,"
            + "Area,"
            + "Continent,"
            + "OfficialLanguages)"
            + "VALUES("
            + "'" + testCountry.getName() + "',"
            + "'" + testCountry.getPopulation().intValue() +"',"
            + "'" + testCountry.getArea().intValue() + "',"
            + "'" + testCountry.getContinent() + "',"
            + "'" +0+ "');"
        );
        expected.add("SELECT ID FROM Country WHERE NAME = '"+testCountry.getName()+"'");
        logic.addInFullCountry(testCountry, new String[] {});
        checkSqlHistory(expected);
    }

    @Test
    public void addInFullLanguage(){
        ArrayList<String> expected = new ArrayList<>();
        expected.add("SELECT ID FROM Language WHERE NAME = '"+testLanguage.getName()+"'");
        expected.add(
            "INSERT INTO Language("
            + "Name,"
            + "OfficialIn"
            + ")"
            + "VALUES("
            + "'" + testLanguage.getName() + "',"
            + "'" +0+ "');"
        );
        expected.add("SELECT ID FROM Language WHERE NAME = '"+testLanguage.getName()+"'");
        logic.addInFullLanguage(testLanguage, new String[] {});
        checkSqlHistory(expected);
    }

    @Test
    public void addRelationship(){
        ArrayList<String> expected = new ArrayList<>();
        expected.add("INSERT INTO CountryLanguageRelationship ("
            + "CountryID,"
            + "LanguageID)"
            + "VALUES("
            + "'" + 123 + "',"
            + "'" + 12 + "');");
        expected.add("SELECT Count(CountryLanguageRelationship.CountryID) AS CountOfCountryID " + 
            "FROM Country INNER JOIN CountryLanguageRelationship ON Country.ID = CountryLanguageRelationship.CountryID " + 
            "GROUP BY Country.ID " + 
            "HAVING (((Country.ID)="+123+"));");
        expected.add("UPDATE Country SET "
            + "OfficialLanguages = '" + 0 + "' "
            + "WHERE ID = " + 123 + ";");
        expected.add("SELECT Count(CountryLanguageRelationship.CountryID) AS CountOfCountryID\r\n" + 
            "FROM [Language] INNER JOIN CountryLanguageRelationship ON Language.ID = CountryLanguageRelationship.LanguageID\r\n" + 
            "GROUP BY Language.ID\r\n" + 
            "HAVING (((Language.ID)="+12+"));");
        expected.add("UPDATE Language SET "
        + "OfficialIn = '" + 0 + "' "
        + "WHERE ID = " + 12 + ";");
        logic.addRelationship("123", "12");
        checkSqlHistory(expected);
    }

    @Test
    public void deleteRelationship(){
        ArrayList<String> expected = new ArrayList<>();
        expected.add("DELETE FROM " + "CountryLanguageRelationShip" + " WHERE "
            + "CountryID = '" + 123 + "' AND "
            + "LanguageID = '" + 12 + "';");
        expected.add("SELECT Count(CountryLanguageRelationship.CountryID) AS CountOfCountryID " + 
            "FROM Country INNER JOIN CountryLanguageRelationship ON Country.ID = CountryLanguageRelationship.CountryID " + 
            "GROUP BY Country.ID " + 
            "HAVING (((Country.ID)="+123+"));");
        expected.add("UPDATE Country SET "
            + "OfficialLanguages = '" + 0 + "' "
            + "WHERE ID = " + 123 + ";");
        expected.add("SELECT Count(CountryLanguageRelationship.CountryID) AS CountOfCountryID\r\n" + 
            "FROM [Language] INNER JOIN CountryLanguageRelationship ON Language.ID = CountryLanguageRelationship.LanguageID\r\n" + 
            "GROUP BY Language.ID\r\n" + 
            "HAVING (((Language.ID)="+12+"));");
        expected.add("UPDATE Language SET "
        + "OfficialIn = '" + 0 + "' "
        + "WHERE ID = " + 12 + ";");
        logic.deleteRelationship("123", "12");
        checkSqlHistory(expected);
    }

    @Test
    public void getCountryNames(){
        String expected = "SELECT Name FROM Country ORDER BY Name";
        logic.getCountryNames();
        checkLastSqlUsed(expected);
    }

    @Test
    public void getLanguageNames(){
        String expected = "SELECT Name FROM Language ORDER BY Name";
        logic.getLanguageNames();
        checkLastSqlUsed(expected);
    }

    @Test
    public void updateCountry(){
        String expected = "UPDATE " + "Country" + " SET "
            + "Name = '" + testCountry.getName() + "',"
            + "Population = '" + testCountry.getPopulation().intValue() + "',"
            + "Area = '" + testCountry.getArea().intValue() + "',"
            + "Continent = '" + testCountry.getContinent() + "', "
            + "OfficialLanguages = '" +0+"' "
            + "WHERE ID = " + 0 + ";";
        logic.updateCountry(0, testCountry, new String[] {}, false);
        checkLastSqlUsed(expected);
    }

    @Test
    public void updateLanguage(){
        String expected = "UPDATE " + "Language" + " SET "
        + "Name = '" + testLanguage.getName() + "', "
        + "OfficialIn = '" + 0 +"' "
        + "WHERE ID = " + 0  + ";";
        logic.updateLanguage(0, testLanguage, new String[] {}, false);
        checkLastSqlUsed(expected);
    }

    private void checkLastSqlUsed(String expected){
        assertEquals(expected, utilities.getLastSqlUsed());
    }

    private void checkSqlHistory(ArrayList<String> expected){
        System.out.println("hi " +utilities.getSqlHistory().get(0));
        assertEquals(expected.size(), utilities.getSqlHistory().size());
        for(int i = 0; i<expected.size(); i++){
            assertEquals(expected.get(i), utilities.getSqlHistory().get(i));
        }
    }
}

