package view.table;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import model.TableType;

/**
 * A JTable used to display a SQL ResultSet.
 * @author fahdshariff
 * http://fahdshariff.blogspot.com/2010/02/display-any-resultset-in-jtable.html
 *
 */
public class ResultSetTable extends JTable
{

	private DefaultTableModel dataModel;
	private final TableType type;
	private static final int IDINDEX = 0;

	private Integer selectedID;

	public ResultSetTable(ResultSet rs, TableType type) throws SQLException
	{
		super();
		this.type = type;
		dataModel = new ResultSetTableModel();
		ResultSetColorCellRenderer myRenderer = new ResultSetColorCellRenderer();
		setModel(dataModel);

		try 
		{
			//create an array of column names
			ResultSetMetaData mdata = rs.getMetaData();
			int colCount = mdata.getColumnCount();
			String[] colNames = new String[colCount];
			for (int i = 1; i <= colCount; i++) 
			{
				colNames[i - 1] = mdata.getColumnName(i);
			}
			dataModel.setColumnIdentifiers(colNames);

			//create header row
			String[] headerData = new String[colCount];
			for (int i = 1; i <= colCount; i++)
			{
				headerData[i - 1] = mdata.getColumnName(i);
			}
			dataModel.addRow(headerData);

			//now populate the data	
			int temp = 0;
			while (rs.next())
			{
				String[] rowData = new String[colCount];
				for (int i = 1; i <= colCount; i++) 
				{
					rowData[i - 1] = rs.getString(i);
				}
				dataModel.addRow(rowData);
				temp++;
			}
			for (int i = 0; i < colCount; i++) 
			{
				this.getColumnModel().getColumn(i).setCellRenderer(myRenderer);
			}
		}
		finally
		{
			try 
			{
				rs.close();
			}
			catch (SQLException ignore) {
			}
		}
	}

	public void updateTable(ResultSet newResultSet) throws SQLException
	{
		ResultSetColorCellRenderer myRenderer = new ResultSetColorCellRenderer();
		dataModel = new ResultSetTableModel();
		setModel(dataModel);

		try 
		{
			//create an array of column names
			ResultSetMetaData mdata = newResultSet.getMetaData();
			int colCount = mdata.getColumnCount();
			String[] colNames = new String[colCount];
			for (int i = 1; i <= colCount; i++) 
			{
				colNames[i - 1] = mdata.getColumnName(i);
			}
			dataModel.setColumnIdentifiers(colNames);

			//create header row
			String[] headerData = new String[colCount];
			for (int i = 1; i <= colCount; i++)
			{
				headerData[i - 1] = mdata.getColumnName(i);
			}
			dataModel.addRow(headerData);

			//now populate the data	
			int temp = 0;
			while (newResultSet.next())
			{
				String[] rowData = new String[colCount];
				for (int i = 1; i <= colCount; i++) 
				{
					rowData[i - 1] = newResultSet.getString(i);
				}
				dataModel.addRow(rowData);
				temp++;
			}
			for (int i = 0; i < colCount; i++) 
			{
				this.getColumnModel().getColumn(i).setCellRenderer(myRenderer);
			}
		}
		finally
		{
			try 
			{
				newResultSet.close();
			}
			catch (SQLException ignore) {
			}
		}
	}
	public boolean editCellAt(int row,int column,EventObject e)
	{
		if(row>0)
		{
			Object[] rowValue = getRow(row);
			selectedID = Integer.parseInt((String) rowValue[IDINDEX]);
		}

		return false;
	}

	private Object[] getRow(int row)
	{
		int length = type.getColumnCount();
		Object[] rowValues = new Object[length];
		for(int i = 0; i<length; i++)
		{
			rowValues[i] = super.getValueAt(row, i);
		}
		return rowValues;
	}

	public Integer getSelectedID()
	{
		try
		{
			return selectedID;
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
