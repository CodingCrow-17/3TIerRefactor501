package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import model.Country;
import model.Language;
import model.TableType;

public class LogicLayer 
{
	private static final String DATABASEFLENAME = "res/LanguageAndCountryDatabase.accdb";

	private static final String PRIMARYTABLENAME = "Country";
	private static final String SECONDARYTABLENAME = "Language";
	private static final String RELATIONSHIPTABLE = "CountryLanguageRelationShip";

	private CSVReader reader = new CSVReader(this);

	private Connection connection = null;
	private DatabaseUtilitiesInterface utilities = null;

	private static LogicLayer instance = null;

	public static LogicLayer getInstance(MSAccessDatabaseConnectionInterface msConnection, DatabaseUtilitiesInterface utilities){
		if (instance == null) {
			instance = new LogicLayer(msConnection, utilities);
		}
		return instance;
	}

	private LogicLayer(MSAccessDatabaseConnectionInterface msConnection, DatabaseUtilitiesInterface utilities){
		this.connection = msConnection.getConnection();
		this.utilities = utilities;
	}

	public ResultSet getFullCountryTableResultSet()
	{
		String query = "SELECT * From Country ORDER BY Name";

		return runReturningQuery(query);
	}
	public ResultSet getFullLanguageTableResultSet()
	{
		String query =  "SELECT * From Language ORDER BY Name";

		return runReturningQuery(query);
	}

	public ResultSet getCorrespondingCountriesTableResultSet(Integer languageID)
	{
		String query = "SELECT Country.* "
				+ "FROM Country INNER JOIN CountryLanguageRelationShip "
				+ "ON Country.ID = CountryLanguageRelationShip.CountryID "
				+ "WHERE CountryLanguageRelationShip.LanguageID = " + languageID;
		
		return runReturningQuery(query);
	}
	public ResultSet getCorrespondingLanguagesTableResultSet(Integer countryID)
	{
		String query =  "SELECT Language.* "
				+ "FROM Language INNER JOIN CountryLanguageRelationShip "
				+ "ON Language.ID = CountryLanguageRelationShip.LanguageID "
				+ "WHERE CountryLanguageRelationShip.CountryID = " + countryID;

		return runReturningQuery(query);
	}

	public Country getCountry(String countryID)
	{
		Country returnedCountry = null;
		String query = "SELECT * FROM " + PRIMARYTABLENAME + " WHERE ID = " + Integer.parseInt(countryID);
		ResultSet resultSet = runReturningQuery(query);
		try 
		{
			if (resultSet.next())
			{
				returnedCountry = new Country(resultSet.getString(2),
						resultSet.getInt(3), resultSet.getInt(4),
						resultSet.getString(5));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return returnedCountry;
	}
	public Language getLanguage(String languageID)
	{
		Language returnedLanguage = null;
		String query = "SELECT * FROM " + SECONDARYTABLENAME + " WHERE ID = " + Integer.parseInt(languageID);
		ResultSet resultSet = runReturningQuery(query);
		try 
		{
			if (resultSet.next())
			{
				returnedLanguage = new Language(resultSet.getString(2));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return returnedLanguage;
	}

	public int getCountryID(String countryName)
	{
		int id = 0;
		String query = "SELECT ID FROM Country WHERE NAME = '"+ countryName +"'";
		ResultSet resultSet = runReturningQuery(query);
		try 
		{
			if (resultSet.next())
			{
				id = resultSet.getInt(1);
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return id;
	}
	public int getLanguageID(String languageName)
	{
		int id = 0;
		String query = "SELECT ID FROM Language WHERE NAME = '"+ languageName +"'";
		ResultSet resultSet = runReturningQuery(query);
		try 
		{
			if (resultSet.next())
			{
				id = resultSet.getInt(1);
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return id;
	}

	public Language[] getAllLanguages(String countryID)
	{
		ArrayList<Language> returnedLanguagesArrayList = new ArrayList<Language>();
		Language[] returnedLanguagesArray = null;
		String query = "SELECT Language.ID " + 
				"FROM [Language] INNER JOIN CountryLanguageRelationship ON Language.ID = CountryLanguageRelationship.LanguageID " + 
				"WHERE (((CountryLanguageRelationship.CountryID)='"+countryID+"'));";
		ResultSet resultSet = runReturningQuery(query);
		try 
		{
			while(resultSet.next())
			{
				returnedLanguagesArrayList.add(getLanguage(resultSet.getString(1)));
			}
			returnedLanguagesArray = new Language[returnedLanguagesArrayList.size()];
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return arrayListToArrayLanguage(returnedLanguagesArrayList, returnedLanguagesArray);
	}
	public Country[] getAllCountries(String languageID)
	{
		ArrayList<Country> returnedCountriesArrayList = new ArrayList<Country>();
		Country[] returnedCountriesArray = null;
		String query = "SELECT Country.ID "+
				"FROM Country INNER JOIN CountryLanguageRelationship ON Country.ID = CountryLanguageRelationship.CountryID "+
				"WHERE (((CountryLanguageRelationship.LanguageID)='"+languageID+"'));";
		ResultSet resultSet = runReturningQuery(query);
		try 
		{
			while(resultSet.next())
			{
				returnedCountriesArrayList.add(getCountry(resultSet.getString(1)));
			}
			returnedCountriesArray = new Country[returnedCountriesArrayList.size()];
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return arrayListToArrayCountries(returnedCountriesArrayList, returnedCountriesArray);
	}	

	public void addCountry(Country newCountry)
	{
		String query;
		int potentialNewCountryID = getCountryID(newCountry.getName());
		if (potentialNewCountryID == 0)
		{
			query = "INSERT INTO "+ PRIMARYTABLENAME +"("
					+ "Name,"
					+ "Population,"
					+ "Area,"
					+ "Continent,"
					+ "OfficialLanguages)"
					+ "VALUES("
					+ "'" + newCountry.getName() + "',"
					+ "'" + newCountry.getPopulation().intValue() +"',"
					+ "'" + newCountry.getArea().intValue() + "',"
					+ "'" + newCountry.getContinent() + "',"
					+ "'" +0+ "');";
			runEditingQuery(query);
		}
		else
		{
			updateCountry(potentialNewCountryID, newCountry, null, false);
		}
	}
	public void addLanguage(Language newLanguage)
	{
		String query;
		int potentialNewLanguageID = getLanguageID(newLanguage.getName());
		if (potentialNewLanguageID == 0)
		{
			query = "INSERT INTO "+ SECONDARYTABLENAME +"("
					+ "Name,"
					+ "OfficialIn"
					+ ")"
					+ "VALUES("
					+ "'" + newLanguage.getName() + "',"
					+ "'" +0+ "');";
			runEditingQuery(query);
		}
		else
			updateLanguage(potentialNewLanguageID, newLanguage,null,true);
	}

	public void deleteCountry(Country country)
	{
		String query = "DELETE FROM " + PRIMARYTABLENAME + " WHERE "
				+ "Name = '" + country.getName() + "' AND "
				+ "Population = '" + country.getPopulation().intValue() + "' AND "
				+ "Area = '" + country.getArea().intValue() + "' AND "
				+ "Continent = '" + country.getContinent() + "';";
		runEditingQuery(query);
	}
	public void deleteLanguage(Language language)
	{
		String query = "DELETE FROM " + SECONDARYTABLENAME + " WHERE "
				+ "Name = '" + language.getName() + "';";
		runEditingQuery(query);
	}

	public void addInFullCountry(Country newCountry, String[] languages) 
	{
		addCountry(newCountry);
		addAllRelationshipsByCountry(String.valueOf(getCountryID(newCountry.getName())),languages );
	}
	public void addInFullLanguage(Language newLanguage, String[] countries) 
	{
		addLanguage(newLanguage);
		addAllRelationshipsByLanguage(String.valueOf(getLanguageID(newLanguage.getName())),countries);
	}

	public void deleteInFullCountry(String countryName) 
	{
		Country countryToDelete = getCountry(String.valueOf(getCountryID(countryName)));
		Language[] languages = getAllLanguages(String.valueOf(getCountryID(countryName)));
		String[] languageNames = convertLanguageToStringArray(languages);
		deleteAllRelationshipsByCountry(String.valueOf(getCountryID(countryName)), languageNames);
		deleteCountry(countryToDelete);
	}
	public void deleteInFullLanguage(String languageName) 
	{
		Language languageToDelete = getLanguage(String.valueOf(getLanguageID(languageName)));
		Country[] countries = getAllCountries(String.valueOf(getLanguageID(languageName)));
		String[] countryNames = convertCountryToStringArray(countries);
		deleteAllRelationshipsByLanguage(String.valueOf(getCountryID(languageName)), countryNames);
		deleteLanguage(languageToDelete);
	}

	public void addRelationship(String countryID, String languageID)
	{
		if (!(countryID.equals("0") || languageID.equals("0")))
		{
			String query = "INSERT INTO CountryLanguageRelationship ("
					+ "CountryID,"
					+ "LanguageID)"
					+ "VALUES("
					+ "'" + countryID + "',"
					+ "'" + languageID + "');";
			runEditingQuery(query);
		}
		updateOfficialLanguageCount(countryID);
		updateOfficialInCount(languageID);
	}
	public void deleteRelationship(String countryID, String languageID)
	{
		String query = "DELETE FROM " + RELATIONSHIPTABLE + " WHERE "
				+ "CountryID = '" + countryID + "' AND "
				+ "LanguageID = '" + languageID + "';";
		runEditingQuery(query);
		updateOfficialLanguageCount(countryID);
		updateOfficialInCount(languageID);
	}

	public boolean doesRelationShipExist(int countryID, int languageID)
	{
		boolean doesExist = false;
		ResultSet resultSet;
		String query = "SELECT * FROM "+RELATIONSHIPTABLE + " WHERE "
				+ "CountryID = '" + countryID +"' AND "
				+ "LanguageID = '" + languageID +"';";
		resultSet = runReturningQuery(query);
		try 
		{
			if (resultSet.next())
			{
				if (resultSet.getInt(1) != 0)
				{
					doesExist = true;
				}
			}

		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return doesExist;
	}

	private void addAllRelationshipsByCountry(String countryID, String[] newLanguages)
	{
		for(int v = 0; v<newLanguages.length; v++)
		{
			addRelationship(countryID, String.valueOf(getLanguageID(newLanguages[v])));
		}
	}
	private void addAllRelationshipsByLanguage(String languageID, String[] newCountries)
	{
		for(int v = 0; v<newCountries.length; v++)
		{
			addRelationship(String.valueOf(getCountryID(newCountries[v])), languageID);
		}
	}
	
	private void deleteAllRelationshipsByCountry(String countryID, String[] oldLanguages)
	{
		String query = "DELETE FROM " + RELATIONSHIPTABLE + " WHERE "
				+ "CountryID = '" + countryID + "';";
		runEditingQuery(query);
		updateOfficialLanguageCount(countryID);
		for (int k = 0; k < oldLanguages.length; k++)
		{
			updateOfficialInCount(String.valueOf(getLanguageID(oldLanguages[k])));
		}	
	}
	private void deleteAllRelationshipsByLanguage(String languageID, String[] oldCountries)
	{
		String query = "DELETE FROM " + RELATIONSHIPTABLE + " WHERE "
				+ "LanguageID = '" + languageID + "';";
		runEditingQuery(query);
		updateOfficialInCount(languageID);
		for (int k = 0; k < oldCountries.length; k++)
		{
			updateOfficialLanguageCount(String.valueOf(getCountryID(oldCountries[k])));
		}	
	}	

	private int getOfficialLanguageCount(String countryID)
	{
		String query = "SELECT Count(CountryLanguageRelationship.CountryID) AS CountOfCountryID " + 
				"FROM Country INNER JOIN CountryLanguageRelationship ON Country.ID = CountryLanguageRelationship.CountryID " + 
				"GROUP BY Country.ID " + 
				"HAVING (((Country.ID)="+countryID+"));";
		int count = 0;
		ResultSet resultSet = runReturningQuery(query);
		try 
		{
			if (resultSet.next())
			{
				count = resultSet.getInt(1);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return count;
	}
	private int getOfficialInCount(String languageID)
	{
		String query = "SELECT Count(CountryLanguageRelationship.CountryID) AS CountOfCountryID\r\n" + 
				"FROM [Language] INNER JOIN CountryLanguageRelationship ON Language.ID = CountryLanguageRelationship.LanguageID\r\n" + 
				"GROUP BY Language.ID\r\n" + 
				"HAVING (((Language.ID)="+languageID+"));";
		int count = 0;
		ResultSet resultSet = runReturningQuery(query);
		try 
		{
			if(resultSet.next())
			{
				count = resultSet.getInt(1);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return count;
	}

	private void updateOfficialLanguageCount(String countryID)
	{
		String query = "UPDATE Country SET "
				+ "OfficialLanguages = '" +  getOfficialLanguageCount(String.valueOf(countryID))+ "' "
				+ "WHERE ID = " + countryID + ";";
		
		runEditingQuery(query);
	}
	private void updateOfficialInCount(String languageID)
	{
		String query = "UPDATE Language SET "
				+ "OfficialIn = '" +  getOfficialInCount(String.valueOf(languageID))+ "' "
				+ "WHERE ID = " + languageID + ";";
		
		runEditingQuery(query);
	}
	
	public String[] getCountryNames()
	{
		ArrayList<String> countryNamesArrayList = new ArrayList<String>();
		String[] countryNamesArray;
		String query = "SELECT Name FROM Country ORDER BY Name";

		ResultSet resultSet = runReturningQuery(query);
		try 
		{
			while (resultSet.next())
			{
				countryNamesArrayList.add(resultSet.getString(1));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		countryNamesArray = new String[countryNamesArrayList.size()];
		return arrayListToArrayString(countryNamesArrayList, countryNamesArray);
	}
	public String[] getLanguageNames()
	{
		ArrayList<String> languageNamesArrayList = new ArrayList<String>();
		String[] languageNamesArray;
		String query = "SELECT Name FROM Language ORDER BY Name";

		ResultSet resultSet = runReturningQuery(query);
		try 
		{
			while (resultSet.next())
			{
				languageNamesArrayList.add(resultSet.getString(1));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		languageNamesArray = new String[languageNamesArrayList.size()];
		return arrayListToArrayString(languageNamesArrayList, languageNamesArray);
	}

	public void updateCountry(int countryID, Country changedCountry, String[] newLanguages, boolean changingRelationships)
	{
		if (changingRelationships)
		{
			Language[] oldLanguages = getAllLanguages(String.valueOf(countryID));
			String[] oldLanguageNames = convertLanguageToStringArray(oldLanguages);
			
			deleteAllRelationshipsByCountry(String.valueOf(countryID), oldLanguageNames);
			addAllRelationshipsByCountry(String.valueOf(countryID), newLanguages);
		}
		String query = "UPDATE " + PRIMARYTABLENAME + " SET "
				+ "Name = '" + changedCountry.getName() + "',"
				+ "Population = '" + changedCountry.getPopulation().intValue() + "',"
				+ "Area = '" + changedCountry.getArea().intValue() + "',"
				+ "Continent = '" + changedCountry.getContinent() + "', "
				+ "OfficialLanguages = '" +getOfficialLanguageCount(String.valueOf(countryID))+"' "
				+ "WHERE ID = " + countryID  + ";";
		runEditingQuery(query);
	}	
	public void updateLanguage(int languageID, Language changedLanguage, String[] newCountries,boolean changingRelationships)
	{
		if (changingRelationships)
		{	
			Country[] oldCountries = getAllCountries(String.valueOf(languageID));
			String[] oldCountryNames = convertCountryToStringArray(oldCountries);
			deleteAllRelationshipsByLanguage(String.valueOf(languageID),oldCountryNames);
			addAllRelationshipsByLanguage(String.valueOf(languageID), newCountries);//check me, all else seems good
		}
		String query = "UPDATE " + SECONDARYTABLENAME + " SET "
				+ "Name = '" + changedLanguage.getName() + "', "
				+ "OfficialIn = '" + getOfficialInCount(String.valueOf(languageID))+"' "
				+ "WHERE ID = " + languageID  + ";";
		runEditingQuery(query);
	}
	
	public void importData(File fileToRead, TableType type) throws FileNotFoundException, 
	NumberFormatException, ArrayIndexOutOfBoundsException, IllegalArgumentException
	{
		reader.readCSV(fileToRead, type);
	}

	private ResultSet runReturningQuery(String query)
	{
		ResultSet resultSet = null;
		try 
		{
			resultSet = utilities.runQuery(connection, query);
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		return resultSet;
	}	
	private void runEditingQuery(String query)
	{
		try 
		{
			utilities.execute(connection, query);
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
	}

	private String[] arrayListToArrayString(ArrayList<String> arrayList, String[] array)
	{
		for(int i=0; i<arrayList.size(); i++)
		{
			array[i] = arrayList.get(i);
		}
		return array;
	}
	private Language[] arrayListToArrayLanguage(ArrayList<Language> arrayList, Language[] array)
	{
		for(int i=0; i<arrayList.size(); i++)
		{
			array[i] = arrayList.get(i);
		}
		return array;
	}
	private Country[] arrayListToArrayCountries(ArrayList<Country> arrayList, Country[] array) 
	{
		for(int i=0; i<arrayList.size(); i++)
		{
			array[i] = arrayList.get(i);
		}
		return array;
	}
	private String[] convertLanguageToStringArray(Language[] languages)
	{
		String[] returnedArray = new String[languages.length];
		for (int l = 0; l < returnedArray.length; l++)
		{
			returnedArray[l] = languages[l].getName();
		}
		return returnedArray;
	}
	private String[] convertCountryToStringArray(Country[] countries)
	{
		String[] returnedArray = new String[countries.length];
		for (int l = 0; l < returnedArray.length; l++)
		{
			returnedArray[l] = countries[l].getName();
		}
		return returnedArray;
	}
}
