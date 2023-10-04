package model;
public class Country
{
	private String name = "";

	private Integer population;
	private Integer area;
	private String continent;

	public Country(String newName,
			Integer newPopulation, Integer newArea, String newContinent)
	{
		this.name = newName;

		this.population = newPopulation;
		this.area = newArea;
		this.continent = newContinent;
	}

	public String getName()
	{
		return name;
	}
	public Integer getPopulation()
	{
		return population;
	}
	public Integer getArea()
	{
		return area;
	}
	public String getContinent()
	{
		return continent;
	}

	public void setName(String newName)
	{
		this.name = newName;
	}

	public void setPopulation(Integer newPopulation)
	{
		this.population = newPopulation;
	}
	public void setArea(Integer newArea)
	{
		this.area = newArea;
	}
	public void setContinent(String newContinent)
	{
		this.continent = newContinent;
	}

	public String toString()
	{
		return "name is: " + name + "\n" + "Continent is: " + continent + "\n"
				+ "area is: " + area + "population is: " + population + "\n";
	}
}
