package model;

public class Language
{
	private String name = "";

	public Language(String newName)
	{
		this.name = newName;
	}

	public String getName()
	{
		return name;
	}


	public void  setName(String newName)
	{
		this.name = newName;
	}

	public String toString()
	{
		return "name: " + name;

	}
}
