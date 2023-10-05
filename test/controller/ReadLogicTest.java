package controller;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import mocks.MockDatabaseUtilities;
public class ReadLogicTest {
    private MockDatabaseUtilities utilities = MockDatabaseUtilities.getInstance();
    private ReadLogic logic = ReadLogic.getInstance(utilities);

    @Before
    public void setup(){
        utilities.reset();
    }

    @Test
    public void getFullCountryTableResultSet(){
        String expected = "SELECT * FROM Country ORDER BY Name";
        logic.getFullCountryTableResultSet();
        checkLastSqlUsed(expected);
    }
    
    @Test
    public void getFullLanguageTableResultSet(){
        String expected = "SELECT * FROM Language ORDER BY Name";
        logic.getFullLanguageTableResultSet();
        checkLastSqlUsed(expected);
    }

        private void checkLastSqlUsed(String expected){
        assertEquals(expected, utilities.getLastSqlUsed());
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
        "FROM Language INNER JOIN CountryLanguageRelationship ON Language.ID = CountryLanguageRelationship.LanguageID " + 
        "WHERE (((CountryLanguageRelationship.CountryID)='"+12345+"'));";
        logic.getAllLanguages("12345");
        checkLastSqlUsed(expected);
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
    public void getAllCountries(){
        String expected = "SELECT Country.ID "+
        "FROM Country INNER JOIN CountryLanguageRelationship ON Country.ID = CountryLanguageRelationship.CountryID "+
        "WHERE (((CountryLanguageRelationship.LanguageID)='"+790871234+"'));";
        logic.getAllCountries("790871234");
        checkLastSqlUsed(expected);
    }

    @Test
    public void doesRelationShipExist(){
        String expected = "SELECT * FROM CountryLanguageRelationShip WHERE "
            + "CountryID = '" + 792387 +"' AND "
            + "LanguageID = '" + 129834589 +"';";
        logic.doesRelationShipExist(792387, 129834589);
        checkLastSqlUsed(expected);
    }
}
