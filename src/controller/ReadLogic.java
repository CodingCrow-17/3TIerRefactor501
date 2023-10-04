package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Country;
import model.Language;

public class ReadLogic {
	private DatabaseUtilitiesInterface utilities = null;

	private static ReadLogic instance = null;

	public static ReadLogic getInstance(DatabaseUtilitiesInterface utilities){
		if (instance == null) {
			instance = new ReadLogic(utilities);
		}
		return instance;
	}

	public static ReadLogic getInstance(){
		if (instance == null) {
			System.err.println("have not instatiated the singleton yet");
		}
		return instance;
	}

	private ReadLogic(DatabaseUtilitiesInterface utilities){
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
		String query = "SELECT * FROM Country WHERE ID = " + Integer.parseInt(countryID);
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
		String query = "SELECT * FROM Language WHERE ID = " + Integer.parseInt(languageID);
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
				"FROM Language INNER JOIN CountryLanguageRelationship ON Language.ID = CountryLanguageRelationship.LanguageID " + 
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
		return ArrayHelpers.arrayListToArrayLanguage(returnedLanguagesArrayList, returnedLanguagesArray);
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
		return ArrayHelpers.arrayListToArrayCountries(returnedCountriesArrayList, returnedCountriesArray);
	}

	public boolean doesRelationShipExist(int countryID, int languageID){
		boolean doesExist = false;
		ResultSet resultSet;
		String query = "SELECT * FROM CountryLanguageRelationShip WHERE "
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

    private ResultSet runReturningQuery(String query){
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
}
