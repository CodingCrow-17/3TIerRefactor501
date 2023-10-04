package model;


public enum TableType 
{	
	// private static final int countryColumnCount = 6;
	// private static final int languageColumnCount = 3;
	COUNTRY(6),
	LANGUAGE(3);

	private final int columnCount;

	TableType(int columnCount)
	{
		this.columnCount = columnCount;
	}

	public int getColumnCount()
	{
		return this.columnCount;
	}


}
