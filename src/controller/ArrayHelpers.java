package controller;

import java.util.ArrayList;

import model.Country;
import model.Language;

public class ArrayHelpers {
    public static String[] arrayListToArrayString(ArrayList<String> arrayList, String[] array)
	{
		for(int i=0; i<arrayList.size(); i++)
		{
			array[i] = arrayList.get(i);
		}
		return array;
	}
	public static Language[] arrayListToArrayLanguage(ArrayList<Language> arrayList, Language[] array)
	{
		for(int i=0; i<arrayList.size(); i++)
		{
			array[i] = arrayList.get(i);
		}
		return array;
	}
	public static Country[] arrayListToArrayCountries(ArrayList<Country> arrayList, Country[] array) 
	{
		for(int i=0; i<arrayList.size(); i++)
		{
			array[i] = arrayList.get(i);
		}
		return array;
	}
	public static String[] convertLanguageToStringArray(Language[] languages)
	{
		String[] returnedArray = new String[languages.length];
		for (int l = 0; l < returnedArray.length; l++)
		{
			returnedArray[l] = languages[l].getName();
		}
		return returnedArray;
	}
	public static String[] convertCountryToStringArray(Country[] countries)
	{
		String[] returnedArray = new String[countries.length];
		for (int l = 0; l < returnedArray.length; l++)
		{
			returnedArray[l] = countries[l].getName();
		}
		return returnedArray;
	}
}
