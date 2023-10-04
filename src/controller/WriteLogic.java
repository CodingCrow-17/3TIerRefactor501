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

public class WriteLogic 
{
	public static final String DATABASEFLENAME = "res/LanguageAndCountryDatabase.accdb";

	public static final String COUNTRYTABLENAME = "Country";
	public static final String LANGUAGETABLENAME = "Language";
	public static final String RELATIONSHIPTABLE = "CountryLanguageRelationShip";

	private CSVReader reader = null;
	private DatabaseUtilitiesInterface utilities = null;

	private static WriteLogic instance = null;

	private ReadLogic readLogic;

	public static WriteLogic getInstance(DatabaseUtilitiesInterface utilities){
		if (instance == null) {
			instance = new WriteLogic(utilities);
		}
		return instance;
	}

	public static WriteLogic getInstance(){
		if (instance == null) {
			System.err.println("have not instatiated the singleton yet");
		}
		return instance;
	}

	public void linkReadLogic(){
		readLogic = ReadLogic.getInstance();
		reader = new CSVReader(this, readLogic);
	}

	private WriteLogic(DatabaseUtilitiesInterface utilities){
		this.utilities = utilities;
	}	

	public void addCountry(Country newCountry)
	{
		String query;
		int potentialNewCountryID = readLogic.getCountryID(newCountry.getName());
		if (potentialNewCountryID == 0)
		{
			query = "INSERT INTO "+ COUNTRYTABLENAME +"("
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
		int potentialNewLanguageID = readLogic.getLanguageID(newLanguage.getName());
		if (potentialNewLanguageID == 0)
		{
			query = "INSERT INTO "+ LANGUAGETABLENAME +"("
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
		String query = "DELETE FROM " + COUNTRYTABLENAME + " WHERE "
				+ "Name = '" + country.getName() + "' AND "
				+ "Population = '" + country.getPopulation().intValue() + "' AND "
				+ "Area = '" + country.getArea().intValue() + "' AND "
				+ "Continent = '" + country.getContinent() + "';";
		runEditingQuery(query);
	}
	public void deleteLanguage(Language language)
	{
		String query = "DELETE FROM " + LANGUAGETABLENAME + " WHERE "
				+ "Name = '" + language.getName() + "';";
		runEditingQuery(query);
	}

	public void addInFullCountry(Country newCountry, String[] languages) 
	{
		addCountry(newCountry);
		addAllRelationshipsByCountry(String.valueOf(readLogic.getCountryID(newCountry.getName())),languages );
	}
	public void addInFullLanguage(Language newLanguage, String[] countries) 
	{
		addLanguage(newLanguage);
		addAllRelationshipsByLanguage(String.valueOf(readLogic.getLanguageID(newLanguage.getName())),countries);
	}

	public void deleteInFullCountry(String countryName) 
	{
		Country countryToDelete = readLogic.getCountry(String.valueOf(readLogic.getCountryID(countryName)));
		Language[] languages = readLogic.getAllLanguages(String.valueOf(readLogic.getCountryID(countryName)));
		String[] languageNames = ArrayHelpers.convertLanguageToStringArray(languages);
		deleteAllRelationshipsByCountry(String.valueOf(readLogic.getCountryID(countryName)), languageNames);
		deleteCountry(countryToDelete);
	}
	public void deleteInFullLanguage(String languageName) 
	{
		Language languageToDelete = readLogic.getLanguage(String.valueOf(readLogic.getLanguageID(languageName)));
		Country[] countries = readLogic.getAllCountries(String.valueOf(readLogic.getLanguageID(languageName)));
		String[] countryNames = ArrayHelpers.convertCountryToStringArray(countries);
		deleteAllRelationshipsByLanguage(String.valueOf(readLogic.getCountryID(languageName)), countryNames);
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

	private void addAllRelationshipsByCountry(String countryID, String[] newLanguages)
	{
		for(int v = 0; v<newLanguages.length; v++)
		{
			addRelationship(countryID, String.valueOf(readLogic.getLanguageID(newLanguages[v])));
		}
	}
	private void addAllRelationshipsByLanguage(String languageID, String[] newCountries)
	{
		for(int v = 0; v<newCountries.length; v++)
		{
			addRelationship(String.valueOf(readLogic.getCountryID(newCountries[v])), languageID);
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
			updateOfficialInCount(String.valueOf(readLogic.getLanguageID(oldLanguages[k])));
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
			updateOfficialLanguageCount(String.valueOf(readLogic.getCountryID(oldCountries[k])));
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
		return ArrayHelpers.arrayListToArrayString(countryNamesArrayList, countryNamesArray);
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
		return ArrayHelpers.arrayListToArrayString(languageNamesArrayList, languageNamesArray);
	}

	public void updateCountry(int countryID, Country changedCountry, String[] newLanguages, boolean changingRelationships)
	{
		if (changingRelationships)
		{
			Language[] oldLanguages = readLogic.getAllLanguages(String.valueOf(countryID));
			String[] oldLanguageNames = ArrayHelpers.convertLanguageToStringArray(oldLanguages);
			
			deleteAllRelationshipsByCountry(String.valueOf(countryID), oldLanguageNames);
			addAllRelationshipsByCountry(String.valueOf(countryID), newLanguages);
		}
		String query = "UPDATE " + COUNTRYTABLENAME + " SET "
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
			Country[] oldCountries = readLogic.getAllCountries(String.valueOf(languageID));
			String[] oldCountryNames = ArrayHelpers.convertCountryToStringArray(oldCountries);
			deleteAllRelationshipsByLanguage(String.valueOf(languageID),oldCountryNames);
			addAllRelationshipsByLanguage(String.valueOf(languageID), newCountries);//check me, all else seems good
		}
		String query = "UPDATE " + LANGUAGETABLENAME + " SET "
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
			resultSet = utilities.query(query);
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
			utilities.execute(query);
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
	}
}
