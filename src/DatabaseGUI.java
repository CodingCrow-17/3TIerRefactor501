import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.DatabaseUtilities;
import controller.WriteLogic;
import controller.ReadLogic;
import model.TableType;
import view.AddNewCountryDialog;
import view.AddNewLanguageDialog;
import view.DeleteDialog;
import view.ModifyCountryDialog;
import view.ModifyLanguageDialog;
import view.ModifyRelationshipsDialogue;
import view.table.ResultSetTable;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Font;
import java.awt.Dialog.ModalityType;

import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import java.sql.Connection;


public class DatabaseGUI extends JFrame 
{
	private WriteLogic writer = null;
	private ReadLogic reader = null;

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnHelp;
	private JMenu mnCredits; 
	private JMenuItem helpImportCountry;
	private JMenuItem helpImportLanguage;

	private ResultSetTable tblCountry;
	private ResultSetTable tblLanguage;
	private JScrollPane scpnCountry;
	private JScrollPane scpnLanguage;
	private JButton btnAddCountry;
	private JButton btnImportCountries;
	private JButton btnModifyCountry;
	private JButton btnDeleteCountry;
	private JButton btnAddLanguage;
	private JButton btnImportLanguage;
	private JButton btnModifyLanguage;
	private JButton btnDeleteLanguage;
	private JButton btnJoin;
	private JButton btnSplit; 
	private JButton btnResetTablesDisplay;

	private final JFileChooser fileChooser = new JFileChooser();
	private final FileNameExtensionFilter filter =
			new FileNameExtensionFilter("CSV FILES", "csv", "csv");
	private static final String DATABASEFLENAME = "res/LanguageAndCountryDatabase.accdb";

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					DatabaseGUI frame = new DatabaseGUI();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	private void connectToDatabase(){
		DatabaseUtilities utilities = DatabaseUtilities.createDatabaseUtilities();

		writer = WriteLogic.getInstance(utilities);
		reader = ReadLogic.getInstance(utilities);
		writer.linkReadLogic();
	}

	/**
	 * Create the frame.
	 */
	public DatabaseGUI() {
		connectToDatabase();
		setTitle("Country Language database (2019)");
		setResizable(false);
		setFont(new Font("Times New Roman", Font.PLAIN, 12));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 630, 460);

		menuBar = new JMenuBar();
		menuBar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setJMenuBar(menuBar);

		mnHelp = new JMenu("Help");
		mnHelp.setFont(new Font("Times New Roman", Font.PLAIN, 12));

		helpImportCountry = new JMenuItem("Importing Countries",
				KeyEvent.VK_T);
		helpImportCountry.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				helpImportCountry_mousePressed();
			}
		});
		helpImportCountry.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		mnHelp.add(helpImportCountry);

		helpImportLanguage = new JMenuItem("Importing Languages",
				KeyEvent.VK_T);
		helpImportLanguage.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				helpImportLanguage_mousePressed();
			}
		});
		helpImportLanguage.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		mnHelp.add(helpImportLanguage);

		menuBar.add(mnHelp);

		mnCredits = new JMenu("Credits");
		mnCredits.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				mnCredits_mousePressed();
			}
		});
		mnCredits.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		menuBar.add(mnCredits);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		try 
		{
			tblCountry = new ResultSetTable(reader.getFullCountryTableResultSet(), TableType.COUNTRY);
			tblCountry.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					tableClicked(e, TableType.COUNTRY);
				}
			});

			tblCountry.setToolTipText("");
			tblCountry.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tblCountry.setShowVerticalLines(true);
			tblCountry.setSurrendersFocusOnKeystroke(false);
			tblCountry.setShowHorizontalLines(true);
			tblCountry.setRowSelectionAllowed(true);
			tblCountry.setColumnSelectionAllowed(false);
			tblCountry.setCellSelectionEnabled(true);
			tblCountry.setFillsViewportHeight(false);
			tblCountry.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			scpnCountry = new JScrollPane(tblCountry, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scpnCountry.setBounds(10, 11, 490, 165);

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		contentPane.setLayout(null);
		contentPane.add(scpnCountry);

		try {
			tblLanguage = new ResultSetTable(reader.getFullLanguageTableResultSet(), TableType.LANGUAGE);
			tblLanguage.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e)
				{
					tableClicked(e, TableType.LANGUAGE);
				}
			});
			tblLanguage.setRowSelectionAllowed(true);
			tblLanguage.setShowHorizontalLines(true);
			tblLanguage.setShowVerticalLines(true);
			tblLanguage.setSurrendersFocusOnKeystroke(true);
			tblLanguage.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			tblLanguage.setFillsViewportHeight(true);
			tblLanguage.setColumnSelectionAllowed(false);
			tblLanguage.setCellSelectionEnabled(false);
			scpnLanguage = new JScrollPane(tblLanguage, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scpnLanguage.setBounds(10, 217, 490, 165);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		contentPane.add(scpnLanguage);

		btnAddCountry = new JButton("Add");
		btnAddCountry.setBounds(510, 30, 89, 23);
		btnAddCountry.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnAddCountry.setVerticalAlignment(SwingConstants.BOTTOM);
		btnAddCountry.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnAddCountry_mouseClicked(e);
			}
		});
		contentPane.add(btnAddCountry);

		btnImportCountries = new JButton("Import");
		btnImportCountries.setBounds(510, 64, 89, 23);
		btnImportCountries.setVerticalAlignment(SwingConstants.BOTTOM);
		btnImportCountries.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnImportCountries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnImportCountries_mouseClicked(e);
			}
		});
		contentPane.add(btnImportCountries);

		btnModifyCountry = new JButton("Modify");
		btnModifyCountry.setBounds(510, 98, 89, 23);
		btnModifyCountry.setVerticalAlignment(SwingConstants.BOTTOM);
		btnModifyCountry.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnModifyCountry.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnModifyCountry_mouseClicked(e);
			}
		});
		contentPane.add(btnModifyCountry);

		btnDeleteCountry = new JButton("Delete");
		btnDeleteCountry.setBounds(510, 132, 89, 23);
		btnDeleteCountry.setVerticalAlignment(SwingConstants.BOTTOM);
		btnDeleteCountry.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnDeleteCountry.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnDeleteCountry_mouseClicked(e);
			}
		});
		contentPane.add(btnDeleteCountry);

		btnAddLanguage = new JButton("Add");
		btnAddLanguage.setBounds(510, 239, 89, 23);
		btnAddLanguage.setVerticalAlignment(SwingConstants.BOTTOM);
		btnAddLanguage.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnAddLanguage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnAddLanguage_mouseClicked(e);
			}
		});
		contentPane.add(btnAddLanguage);

		btnImportLanguage = new JButton("Import");
		btnImportLanguage.setBounds(510, 273, 89, 23);
		btnImportLanguage.setVerticalAlignment(SwingConstants.BOTTOM);
		btnImportLanguage.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnImportLanguage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnImportLanguage_mouseClicked(e);
			}
		});
		contentPane.add(btnImportLanguage);

		btnModifyLanguage = new JButton("Modify");
		btnModifyLanguage.setBounds(510, 307, 89, 23);
		btnModifyLanguage.setVerticalAlignment(SwingConstants.BOTTOM);
		btnModifyLanguage.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnModifyLanguage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnModifyLanguage_mouseClicked(e);
			}
		});
		contentPane.add(btnModifyLanguage);

		btnDeleteLanguage = new JButton("Delete");
		btnDeleteLanguage.setBounds(510, 341, 89, 23);
		btnDeleteLanguage.setVerticalAlignment(SwingConstants.BOTTOM);
		btnDeleteLanguage.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnDeleteLanguage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnDeleteLanguage_mouseClicked(e);
			}
		});
		contentPane.add(btnDeleteLanguage);

		btnJoin = new JButton("Join");
		btnJoin.setBounds(312, 183, 89, 23);
		btnJoin.setVerticalAlignment(SwingConstants.BOTTOM);
		btnJoin.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnJoin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnJoin_mouseClicked(e);
			}
		});
		contentPane.add(btnJoin);

		btnSplit = new JButton("Split");
		btnSplit.setBounds(411, 183, 89, 23);
		btnSplit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnSplit_mouseClicked(arg0);
			}
		});
		btnSplit.setVerticalAlignment(SwingConstants.BOTTOM);
		btnSplit.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		contentPane.add(btnSplit);

		btnResetTablesDisplay = new JButton("Reset Tables Display");
		btnResetTablesDisplay.setBounds(10, 183, 139, 23);
		btnResetTablesDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				btnResetTablesDisplay_clicked();
			}
		});
		btnResetTablesDisplay.setVerticalAlignment(SwingConstants.BOTTOM);
		btnResetTablesDisplay.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		fileChooser.setFileFilter(filter);
		contentPane.add(btnResetTablesDisplay);
	}


	private void tableClicked(MouseEvent arg0, TableType type) 
	{
		ResultSet temp;
		int ID;
		switch (type)
		{
		case COUNTRY:
			try
			{
				ID = tblCountry.getSelectedID();
				temp = reader.getCorrespondingLanguagesTableResultSet(ID);
				tblLanguage.updateTable(temp);
				break;
			}
			catch (Exception e)
			{
			}

			break;
		case LANGUAGE:
			try
			{
				ID = tblLanguage.getSelectedID();
				temp = reader.getCorrespondingCountriesTableResultSet(ID);
				tblCountry.updateTable(temp);
				break;
			}
			catch (Exception e)
			{
			}
			break;
		}
	}

	private void btnAddCountry_mouseClicked(MouseEvent e) 
	{
		if (! this.btnAddCountry.isEnabled()) 
		{
			return;
		}
		else
		{
			try
			{
				openAddDialog(TableType.COUNTRY);
			}
			catch(Exception error)
			{
				error.printStackTrace();
			}
		}
	}
	private void btnAddLanguage_mouseClicked(MouseEvent e) 
	{
		if (! this.btnAddLanguage.isEnabled()) 
		{
			return;
		}
		else
		{
			try
			{
				openAddDialog(TableType.LANGUAGE);
			}
			catch(Exception error)
			{
			}
		}
	}	
	private void openAddDialog(TableType type)
	{
		String[] arrayOfOtherTypeNames;
		switch (type)
		{
		case COUNTRY:
			arrayOfOtherTypeNames = writer.getLanguageNames();
			AddNewCountryDialog addCountryMenu = 
					new AddNewCountryDialog(writer,arrayOfOtherTypeNames);
			addCountryMenu.setLocationRelativeTo(this);
			addCountryMenu.setModalityType(ModalityType.APPLICATION_MODAL);
			addCountryMenu.setVisible(true);
			break;

		case LANGUAGE:
			arrayOfOtherTypeNames = writer.getCountryNames();
			AddNewLanguageDialog addLanguageMenu = 
					new AddNewLanguageDialog(writer,arrayOfOtherTypeNames);
			addLanguageMenu.setLocationRelativeTo(this);
			addLanguageMenu.setModalityType(ModalityType.APPLICATION_MODAL);
			addLanguageMenu.setVisible(true);
			break;
		}
		updateTables();
	}

	private void btnImportCountries_mouseClicked(MouseEvent e) 
	{
		if (! this.btnImportCountries.isEnabled()) 
		{
			return;
		}
		else
		{
			try
			{
				openImportDialogue(TableType.COUNTRY);
			}
			catch(Exception error)
			{
			}
		}
	}
	private void btnImportLanguage_mouseClicked(MouseEvent e) 
	{
		if (! this.btnImportLanguage.isEnabled()) 
		{
			return;
		}
		else
		{
			try
			{
				openImportDialogue(TableType.LANGUAGE);
			}
			catch(Exception error)
			{
			}
		}
	}
	private void openImportDialogue(TableType type)
	{

		File selectedFile;
		int returnedValue;
		boolean actionTaken = false;
		while (actionTaken == false)
		{
			returnedValue = fileChooser.showOpenDialog(contentPane);
			if (returnedValue == JFileChooser.APPROVE_OPTION)
			{
				selectedFile = fileChooser.getSelectedFile();
				try 
				{
					writer.importData(selectedFile,type);
					JOptionPane.showMessageDialog(this,"We did it, YAAAY!",
							"Succesful Import Complete",JOptionPane.PLAIN_MESSAGE);
					actionTaken = true;
				}
				catch (FileNotFoundException e) 
				{
					JOptionPane.showMessageDialog(this,"I couldn't find the file, sorry... ",
							"File Not Found",JOptionPane.ERROR_MESSAGE);
				}
				catch (NumberFormatException | ArrayIndexOutOfBoundsException e)
				{
					JOptionPane.showMessageDialog(this,"This table isn't configured right, sorry...",
							"Improper table",JOptionPane.ERROR_MESSAGE);
				}
				catch (IllegalArgumentException e)
				{
					JOptionPane.showMessageDialog(this,"I'm afraid one or more of the countries has an invalid continent",
							"Incomplete import ",JOptionPane.ERROR_MESSAGE);
				}
			}
			else if (returnedValue == JFileChooser.ERROR_OPTION)
			{
				JOptionPane.showMessageDialog(this,"Something horrible has gone wrong and I have no idea!",
						"ERROR",JOptionPane.ERROR_MESSAGE);
				actionTaken = true;
			}
			else
			{
				actionTaken = true;
			}
		}

		updateTables();
	}

	private void btnModifyCountry_mouseClicked(MouseEvent e) 
	{
		if (! this.btnModifyCountry.isEnabled()) 
		{
			return;
		}
		else
		{
			try
			{
				openModifyDialogue(TableType.COUNTRY);
			}
			catch(Exception error)
			{
			}
		}
	}
	private void btnModifyLanguage_mouseClicked(MouseEvent e) 
	{
		if (! this.btnModifyLanguage.isEnabled()) 
		{
			return;
		}
		else
		{
			try
			{
				openModifyDialogue(TableType.LANGUAGE);
			}
			catch(Exception error)
			{
			}
		}
	}
	private void openModifyDialogue(TableType type)
	{
		String[] countryNames = writer.getCountryNames();
		String[] languageNames = writer.getLanguageNames();
		switch (type)
		{
		case COUNTRY:
			ModifyCountryDialog modifyCountryMenu = 
			new ModifyCountryDialog(writer, languageNames, countryNames);
			modifyCountryMenu.setLocationRelativeTo(this);
			modifyCountryMenu.setModalityType(ModalityType.APPLICATION_MODAL);
			modifyCountryMenu.setVisible(true);
			break;

		case LANGUAGE:

			ModifyLanguageDialog modifyLanguageMenu = 
			new ModifyLanguageDialog(writer, languageNames, countryNames);
			modifyLanguageMenu.setLocationRelativeTo(this);
			modifyLanguageMenu.setModalityType(ModalityType.APPLICATION_MODAL);
			modifyLanguageMenu.setVisible(true);
			break;
		}
		updateTables();
	}

	private void btnDeleteCountry_mouseClicked(MouseEvent e) 
	{
		if (! this.btnDeleteCountry.isEnabled()) 
		{
			return;
		}
		else
		{
			try
			{
				openDeleteDialogue(TableType.COUNTRY);
			}
			catch(Exception error)
			{
			}
		}
	}
	private void btnDeleteLanguage_mouseClicked(MouseEvent e) 
	{
		if (! this.btnDeleteLanguage.isEnabled()) 
		{
			return;
		}
		else
		{
			try
			{
				openDeleteDialogue(TableType.LANGUAGE);
			}
			catch(Exception error)
			{
			}
		}
	}
	private void openDeleteDialogue(TableType type)
	{
		DeleteDialog deleteMenu = new DeleteDialog(type,writer.getCountryNames(),writer.getLanguageNames(), writer);
		deleteMenu.setLocationRelativeTo(this);
		deleteMenu.setModalityType(ModalityType.APPLICATION_MODAL);
		deleteMenu.setVisible(true);
		updateTables();
	}

	private void btnJoin_mouseClicked(MouseEvent e) 
	{
		openModifyRelationshipsDialogue(true);
	}
	private void btnSplit_mouseClicked(MouseEvent e) 
	{
		openModifyRelationshipsDialogue(false);
	}
	private void openModifyRelationshipsDialogue(boolean isAdding)
	{
		String[] countryNames = writer.getCountryNames();
		String[] languageNames = writer.getLanguageNames();

		ModifyRelationshipsDialogue modifyRelationshipsMenu = new ModifyRelationshipsDialogue(writer,countryNames,languageNames,isAdding);
		modifyRelationshipsMenu.setLocationRelativeTo(this);
		modifyRelationshipsMenu.setModalityType(ModalityType.APPLICATION_MODAL);
		modifyRelationshipsMenu.setVisible(true);
		updateTables();
	}

	private void helpImportCountry_mousePressed()
	{
		String howToImport;
		howToImport = "How to import countries:\n"
				+ "The imported CSV should only consist of the values, (no headers)"
				+ "\n"
				+ "The format is: \n"
				+ "Name^, Population, Area, List of languages in the country*, Continent** \n"
				+ "\n"
				+ "*Only prexisting languages will be added, and they must be seperated \n"
				+ "by semicolons (;)\n"
				+ "** If the continent is not valid, the country won't be added \n"
				+ "^ If the name matches a preexisting name, that record will be updated instead.";
		try
		{	
			JOptionPane.showMessageDialog(this,
					howToImport,
					"Import Country Help",
					JOptionPane.INFORMATION_MESSAGE);

		}
		catch(Exception error)
		{
		}
	}
	private void helpImportLanguage_mousePressed()
	{
		String howToImport;
		howToImport = "How to import languages:\n"
				+ "The imported CSV should only consist of the values, no headers \n"
				+ "The format is: \n"
				+ "Name^, list of countries that speak the language* \n"
				+ "\n"
				+ "*Only prexisting countries will be added, and they must be \n"
				+ "seperated by semicolons (;)\n"
				+ "^ If the name matches a pre-existing name, that record will \n"
				+ "be updated instead.";
		try
		{	
			JOptionPane.showMessageDialog(this,
					howToImport,
					"Import Language Help",
					JOptionPane.INFORMATION_MESSAGE);
		}
		catch(Exception error)
		{
		}
	}
	private void mnCredits_mousePressed()
	{
		String credits;
		credits = "Credits: \n"
				+ "\n"
				+ "Programmer, Researcher, \n"
				+ "Debugger, and Tester: \n"
				+ "-Ibrahim Sultan"
				+ "\n"
				+ "Special Thanks:\n"
				+ "-Mr. Wehnes \n"
				+ "-All the wonderful people \n"
				+ "at StackOverflow.com";
		try
		{	
			JOptionPane.showMessageDialog(this,
					credits,
					"Import Language Help",
					JOptionPane.INFORMATION_MESSAGE);
		}
		catch(Exception error)
		{
		}
	}

	private void btnResetTablesDisplay_clicked() 
	{
		updateTables();
	}

	private void updateTables()
	{
		try 
		{
			tblCountry.updateTable(reader.getFullCountryTableResultSet());
			tblLanguage.updateTable(reader.getFullLanguageTableResultSet());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
