package controller;
import java.util.Arrays;
import java.util.Scanner;

import model.Country;
import model.Language;
import model.TableType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CSVReader 
{
	private Scanner inputReader;
	private TableType type;
	private boolean invalidContinentEntered = false;

	private LogicLayer importLogic;
	private final String[] CONTINENTS = {"Africa","Americas","Asia","Europe","Oceania",};

	public CSVReader(LogicLayer logic)
	{
		importLogic = logic;
	}

	public void close() throws IOException 
	{
		if(inputReader != null)
		{
			inputReader.close();
		}
	}

	public void readCSV(File fileToRead, TableType type) throws FileNotFoundException, 
	NumberFormatException, ArrayIndexOutOfBoundsException,IllegalArgumentException
	{
		this.type = type;
		inputReader = new Scanner(fileToRead);
		while (inputReader.hasNextLine())
		{
			readInput(inputReader.nextLine());
		}
		try
		{
			this.close();
		}
		catch (IOException e)
		{

		}
		if (invalidContinentEntered)
		{
			throw new IllegalArgumentException();
		}
	}

	private void readInput(String nextLine) throws NumberFormatException, 
	ArrayIndexOutOfBoundsException
	{
		String[] values;
		int valuesNeededCount = type.getColumnCount()-1;
		int nextLineValueCount = nextLine.split(",").length;
		if (nextLineValueCount == valuesNeededCount)
		{
			values = nextLine.split(",");
			createObject(values);
		}
		else if (nextLineValueCount == (valuesNeededCount-1))
		{
			values = nextLine.split(",");
			values = Arrays.copyOf(values, values.length+1);
			createObject(values);
		}
		else
		{
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	private void createObject(String[] values)
	{
		switch (type)
		{
		case COUNTRY:
			if (checkIfContinent(values[3]))
			{
				Country tempCountry = new Country(values[0],Integer.valueOf(values[1]),
						Integer.valueOf(values[2]),values[3]);
				importLogic.addCountry(tempCountry);
				if (values[4] != null)
				{
					String[] officialLanguagesArray = values[4].split(";");
					for (int i = 0; i < officialLanguagesArray.length; i++)
					{
						if(importLogic.getLanguageID(officialLanguagesArray[i]) != 0 &&
								!(importLogic.doesRelationShipExist(
										importLogic.getCountryID(values[0]),
										importLogic.getLanguageID(officialLanguagesArray[i]))))
						{
							importLogic.addRelationship(
									String.valueOf(importLogic.getCountryID(values[0])),
									String.valueOf(importLogic.getLanguageID(officialLanguagesArray[i])));
						}
					}
				}
			}
			else
			{
				invalidContinentEntered = true;
			}
			break;
		case LANGUAGE:
			Language tempLanguage = new Language(values[0]);
			importLogic.addLanguage(tempLanguage);
			if (values[1] != null)
			{
				String[] officialInsArray = values[1].split(";");
				for (int i = 0; i < officialInsArray.length; i++)
				{	
					if(importLogic.getCountryID(officialInsArray[i]) != 0 &&
							!(importLogic.doesRelationShipExist(
									importLogic.getCountryID(officialInsArray[i]),
									importLogic.getLanguageID(values[0]))))
					{
						importLogic.addRelationship(
								String.valueOf(importLogic.getCountryID(officialInsArray[i])),
								String.valueOf(importLogic.getLanguageID(values[0])));
					}
				}
			}
			break;
		}

	}

	private boolean checkIfContinent(String input)
	{
		boolean isContinent=false;
		for (int b = 0; b < CONTINENTS.length; b++)
		{
			if (CONTINENTS[b].equals(input))
			{
				isContinent = true;
				break;
			}
		}
		return isContinent;
	}
}