package view;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.PopupMenuListener;

import controller.ReadLogic;
import controller.WriteLogic;
import model.Country;
import model.Language;

import javax.swing.event.PopupMenuEvent;

public class ModifyRelationshipsDialogue extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox cbbxCountry;
	private JLabel lblCountry;
	private JButton btnModify;
	private JLabel lblLanguage;
	private JComboBox cbbxLanguage;
	private JPanel buttonPane;
	private JButton btnOK;
	private JButton btnCancel ;
	private JButton btnClear;

	String[] countryNames;
	String[] languageNames;
	WriteLogic relationShipLogic;
	ReadLogic readLogic = ReadLogic.getInstance();

	String title;
	String btnModifyTag;

	boolean isAddingRelationships;
	boolean countrySelected = false;
	boolean languageSelected = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args, WriteLogic logic,String[] countries, String[] languages, boolean isAddingRelationships) 
	{
		try {
			ModifyRelationshipsDialogue dialog = new ModifyRelationshipsDialogue(logic,countries,languages,isAddingRelationships);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ModifyRelationshipsDialogue(WriteLogic logic, String[] countries, String[] languages, boolean isAddingRelationships) {
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		adjustForTypeOfModification(isAddingRelationships);
		setTitle(title);
		setBounds(100, 100, 450, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setControls();
			}
		});
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		cbbxCountry = new JComboBox(new String[]{""});
		insertItemsIntoCbbx(cbbxCountry,countries);
		cbbxCountry.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
				setControls();
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
				cbbxCountry_popupMenuWillBecomeInvisible(arg0);
				setControls();
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				setControls();
			}
		});
		cbbxCountry.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		cbbxCountry.setBounds(10, 26, 141, 20);
		contentPanel.add(cbbxCountry);


		lblCountry = new JLabel("Country");
		lblCountry.setHorizontalAlignment(SwingConstants.CENTER);
		lblCountry.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		lblCountry.setBounds(10, 11, 141, 14);
		contentPanel.add(lblCountry);


		btnModify = new JButton(btnModifyTag);
		btnModify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnModify.isEnabled())
				{
					btnModify_clicked();
				}
				setControls();
			}
		});	
		btnModify.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnModify.setBounds(161, 24, 89, 23);
		contentPanel.add(btnModify);


		lblLanguage = new JLabel("Language");
		lblLanguage.setHorizontalAlignment(SwingConstants.CENTER);
		lblLanguage.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		lblLanguage.setBounds(260, 11, 141, 14);
		contentPanel.add(lblLanguage);


		cbbxLanguage = new JComboBox(new String[]{""});
		insertItemsIntoCbbx(cbbxLanguage,languages);
		cbbxLanguage.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
				setControls();
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				cbbxLanguage_popupMenuWillBecomeInvisible(e);
				setControls();
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				setControls();
			}
		});
		cbbxLanguage.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		cbbxLanguage.setBounds(260, 26, 141, 20);
		contentPanel.add(cbbxLanguage);


		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnOK = new JButton("OK");
		btnOK.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnOK.isEnabled())
				{
					btnOK_clicked();
				}
				setControls();
			}
		});
		btnOK.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnOK.setActionCommand("OK");
		buttonPane.add(btnOK);
		getRootPane().setDefaultButton(btnOK);


		btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnCancel_clicked();
			}
		});

		btnClear = new JButton("Clear");
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnClear_clicked();
				setControls();
			}
		});
		btnClear.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnClear.setEnabled(true);
		btnClear.setActionCommand("OK");
		buttonPane.add(btnClear);
		btnCancel.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnCancel.setActionCommand("Cancel");
		buttonPane.add(btnCancel);

		this.isAddingRelationships = isAddingRelationships;
		countryNames = countries;
		languageNames = languages;
		relationShipLogic = logic;
		setControls();
	}

	private void setControls()
	{
		boolean validInput = true;

		if (cbbxLanguage.getSelectedIndex() <= 0)
		{
			validInput = false;
		}
		if (cbbxLanguage.getSelectedIndex() <= 0)
		{
			validInput = false;
		}

		btnModify.setEnabled(validInput);
		btnOK.setEnabled(validInput);
	}

	private void cbbxCountry_popupMenuWillBecomeInvisible(PopupMenuEvent arg0) 
	{
		int countryIndex = cbbxCountry.getSelectedIndex();
		String selectedCountry = "";
		int selectedID = 0;
		if (countryIndex >0)
		{
			selectedCountry = cbbxCountry.getSelectedItem().toString();
			selectedID = readLogic.getCountryID(selectedCountry);
			countrySelected = true;
		}
		else
		{
			countrySelected = false;
			languageSelected = false;
			cbbxLanguage.removeAllItems();
			cbbxLanguage.addItem("");
			insertItemsIntoCbbx(cbbxLanguage, languageNames);
			cbbxLanguage.setSelectedIndex(0);
		}
		if(languageSelected == false && countrySelected)
		{
			if(isAddingRelationships)
			{
				removeLanguagesFromCbbx(cbbxLanguage, readLogic.getAllLanguages(String.valueOf(selectedID)));
			}
			else
			{
				cbbxLanguage.removeAllItems();
				cbbxLanguage.addItem("");
				insertLanguagesIntoCbbx(cbbxLanguage, readLogic.getAllLanguages(String.valueOf(selectedID)));
			}
		}
	}

	private void cbbxLanguage_popupMenuWillBecomeInvisible(PopupMenuEvent arg0) 
	{
		int languageIndex = cbbxLanguage.getSelectedIndex();
		String selectedLanguage = "";
		int selectedID = 0;
		if (languageIndex >0)
		{
			selectedLanguage = cbbxLanguage.getSelectedItem().toString();
			selectedID = readLogic.getLanguageID(selectedLanguage);
			languageSelected = true;
		}
		else
		{
			countrySelected = false;
			languageSelected = false;
			cbbxCountry.removeAllItems();
			cbbxCountry.addItem("");
			insertItemsIntoCbbx(cbbxCountry, countryNames);
			cbbxCountry.setSelectedIndex(0);
		}
		if(countrySelected == false && languageSelected)
		{
			if(isAddingRelationships)
			{
				removeCountriesFromCbbx(cbbxCountry, readLogic.getAllCountries(String.valueOf(selectedID)));
			}
			else
			{
				cbbxCountry.removeAllItems();
				cbbxCountry.addItem("");
				insertCountriesIntoCbbx(cbbxCountry, readLogic.getAllCountries(String.valueOf(selectedID)));
			}
		}
	}

	private void btnOK_clicked() 
	{
		modifyRelationShip();	
		clear();
		this.dispose();
	}

	private void btnCancel_clicked() 
	{
		clear();
		this.dispose();
	}	

	private void btnModify_clicked() 
	{
		modifyRelationShip();	
		JOptionPane.showMessageDialog(
				this, 
				"The relationship has been modified!",
				"Confirmation", 
				JOptionPane.PLAIN_MESSAGE);
		clear();
		clear();
	}

	private void btnClear_clicked() 
	{
		clear();
	}

	private void clear() 
	{
		cbbxCountry.removeAllItems();
		cbbxCountry.addItem("");
		insertItemsIntoCbbx(cbbxCountry, countryNames);
		cbbxCountry.setSelectedIndex(0);
		countrySelected = false;

		cbbxLanguage.removeAllItems();
		cbbxLanguage.addItem("");
		insertItemsIntoCbbx(cbbxLanguage, languageNames);
		cbbxLanguage.setSelectedIndex(0);
		languageSelected = false;
	}
	private void modifyRelationShip()
	{
		int countryID = readLogic.getCountryID(cbbxCountry.getSelectedItem().toString());
		int languageID = readLogic.getLanguageID(cbbxLanguage.getSelectedItem().toString());
		if(isAddingRelationships)
		{
			relationShipLogic.addRelationship(String.valueOf(countryID), String.valueOf(languageID));
		}
		else
		{
			relationShipLogic.deleteRelationship(String.valueOf(countryID), String.valueOf(languageID));
		}
	}

	private void insertItemsIntoCbbx(JComboBox cbbx,String[] items) 
	{
		for (int p = 0; p<items.length; p++)
		{
			cbbx.addItem(items[p]);
		}
	}
	private void insertCountriesIntoCbbx(JComboBox cbbx,Country[] items) 
	{
		for (int p = 0; p<items.length; p++)
		{
			cbbx.addItem(items[p].getName());
		}
	}
	private void insertLanguagesIntoCbbx(JComboBox cbbx,Language[] items) 
	{
		for (int p = 0; p<items.length; p++)
		{
			cbbx.addItem(items[p].getName());
		}
	}
	private void removeLanguagesFromCbbx(JComboBox cbbx, Language[] items) 
	{
		for (int p = 0; p<items.length; p++)
		{
			cbbx.removeItem(items[p].getName());
		}
	}
	private void removeCountriesFromCbbx(JComboBox cbbx, Country[] items) 
	{
		for (int p = 0; p<items.length; p++)
		{
			cbbx.removeItem(items[p].getName());
		}
	}
	private void adjustForTypeOfModification(boolean isAdding)
	{
		if (isAdding)
		{
			title = "Join";
			btnModifyTag = "Join";
		}
		else
		{
			title = "Split";
			btnModifyTag = "Split";
		}
	}

}
