package view;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.PopupMenuListener;

import controller.LogicLayer;
import model.Country;
import model.Language;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class ModifyCountryDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JButton btnOK;
	private JButton btnCancel;
	private JButton btnModify;
	private JButton btnClear;
	private JTextField txtName;
	private JTextField txtArea;
	private JTextField txtPopulation;
	private JTextArea txtarOfficialLanguages;
	private JLabel lblCountry;
	private JLabel lblName;
	private JLabel lblContinent;
	private JLabel lblPopulation;
	private JLabel lblArea;
	private JLabel lblOfficialLanguages;
	private JLabel lblAdd;
	private JLabel lblRemove;
	private JComboBox cbbxCountrySelection;
	private JComboBox cbbxAddableLanguages;
	private JScrollPane scpnLanguages;
	private JComboBox cbbxAddedLanguages;
	private JComboBox cbbxContinent;

	private final String[] CONTINENTS = {"","Africa","Americas","Asia","Europe","Oceania",};

	private String[] languageNames;
	private LogicLayer modifyCountryLogic;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, LogicLayer modifyCountryLogic, String[] languages, String[] countries) {
		try {
			ModifyCountryDialog dialog = new ModifyCountryDialog(modifyCountryLogic,languages,countries);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ModifyCountryDialog(LogicLayer modifyCountryLogic, String[] languages, String[] countries) 
	{
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setTitle("Modify Country");
		setBounds(100, 100, 457, 304);
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

		lblCountry = new JLabel("Country:");
		lblCountry.setBounds(20, 28, 46, 14);
		lblCountry.setHorizontalAlignment(SwingConstants.LEFT);
		lblCountry.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		contentPanel.add(lblCountry);

		lblName = new JLabel("Name:");
		lblName.setBounds(20, 50, 46, 14);
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		contentPanel.add(lblName);

		lblContinent = new JLabel("Continent:");
		lblContinent.setBounds(20, 75, 61, 14);
		lblContinent.setHorizontalAlignment(SwingConstants.LEFT);
		lblContinent.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		contentPanel.add(lblContinent);

		lblArea = new JLabel("Area (km^2):");
		lblArea.setBounds(20, 100, 80, 14);
		lblArea.setHorizontalAlignment(SwingConstants.LEFT);
		lblArea.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		contentPanel.add(lblArea);

		lblPopulation = new JLabel("Population:");
		lblPopulation.setBounds(20, 125, 61, 14);
		lblPopulation.setHorizontalAlignment(SwingConstants.LEFT);
		lblPopulation.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		contentPanel.add(lblPopulation);

		lblOfficialLanguages = new JLabel("Official Languages:");
		lblOfficialLanguages.setBounds(20, 157, 106, 14);
		lblOfficialLanguages.setHorizontalAlignment(SwingConstants.LEFT);
		lblOfficialLanguages.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		contentPanel.add(lblOfficialLanguages);

		txtName = new JTextField();
		txtName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				setControls();
			}
		});
		txtName.setBounds(125, 50, 307, 20);
		txtName.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtName.setHorizontalAlignment(SwingConstants.LEFT);
		contentPanel.add(txtName);
		txtName.setColumns(10);

		txtArea = new JTextField();
		txtArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				setControls();
			}
		});
		txtArea.setBounds(125, 100, 307, 20);
		txtArea.setHorizontalAlignment(SwingConstants.LEFT);
		txtArea.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtArea.setColumns(10);
		contentPanel.add(txtArea);

		txtPopulation = new JTextField();
		txtPopulation.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				setControls();
			}
		});
		txtPopulation.setBounds(125, 125, 307, 20);
		txtPopulation.setHorizontalAlignment(SwingConstants.LEFT);
		txtPopulation.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtPopulation.setColumns(10);
		contentPanel.add(txtPopulation);

		cbbxCountrySelection = new JComboBox(new String[] {""});
		cbbxCountrySelection.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
				setControls();
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
				cbbxCountrySelection_popupMenuWillBecomeInvisible(arg0);
				setControls();
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				clear();
				setControls();
			}
		});
		insertItemsIntoCbbx(cbbxCountrySelection,countries);
		cbbxCountrySelection.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		cbbxCountrySelection.setBounds(125, 25, 307, 20);
		contentPanel.add(cbbxCountrySelection);

		cbbxAddableLanguages = new JComboBox(new Object[]{});
		cbbxAddableLanguages.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
				setControls();
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				cbbxAddableLanguages_popupMenuWillBecomeInvisible(e);
				setControls();
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				setControls();
			}
		});
		cbbxAddableLanguages.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		cbbxAddableLanguages.setBounds(125, 172, 96, 20);
		contentPanel.add(cbbxAddableLanguages);

		lblAdd = new JLabel("Add");
		lblAdd.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdd.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		lblAdd.setBounds(125, 157, 96, 14);
		contentPanel.add(lblAdd);

		cbbxAddedLanguages = new JComboBox(new Object[]{});
		cbbxAddedLanguages.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
				setControls();
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				cbbxAddedLanguages_popupMenuWillBecomeInvisible(e);
				setControls();
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				setControls();
			}
		});
		cbbxAddedLanguages.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		cbbxAddedLanguages.setBounds(230, 172, 96, 20);
		contentPanel.add(cbbxAddedLanguages);
		
		txtarOfficialLanguages = new JTextArea(5, 20);
		txtarOfficialLanguages.setEditable(false);
		txtarOfficialLanguages.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtarOfficialLanguages.setColumns(10);
		scpnLanguages = new JScrollPane(txtarOfficialLanguages,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scpnLanguages.setBounds(336, 155, 96, 66);
		contentPanel.add(scpnLanguages);

		lblRemove = new JLabel("Remove");
		lblRemove.setHorizontalAlignment(SwingConstants.CENTER);
		lblRemove.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		lblRemove.setBounds(230, 157, 96, 14);
		contentPanel.add(lblRemove);

		cbbxContinent = new JComboBox(CONTINENTS);
		cbbxContinent.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
				setControls();
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				setControls();
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				setControls();
			}
		});
		cbbxContinent.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		cbbxContinent.setBounds(126, 75, 306, 20);
		contentPanel.add(cbbxContinent);
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			btnModify = new JButton("Modify");
			btnModify.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if (btnModify.isEnabled())
					{
						btnModify_clicked();
						setControls();
					}
				}
			});
			btnModify.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			btnModify.setEnabled(false);
			btnModify.setActionCommand("OK");
			buttonPane.add(btnModify);

			btnClear = new JButton("Clear");
			btnClear.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					btnClear_clicked();
					setControls();
				}
			});
			btnClear.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			btnClear.setActionCommand("OK");
			buttonPane.add(btnClear);
			{
				btnOK = new JButton("OK");
				btnOK.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if(btnOK.isEnabled())
						{
							btnOK_clicked();
						}
					}
				});
				btnOK.setFont(new Font("Times New Roman", Font.PLAIN, 11));
				btnOK.setActionCommand("OK");
				buttonPane.add(btnOK);
				getRootPane().setDefaultButton(btnOK);
			}
			{
				btnCancel = new JButton("Cancel");
				btnCancel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						btnCancel_clicked();
					}
				});
				btnCancel.setFont(new Font("Times New Roman", Font.PLAIN, 11));
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
		}
		languageNames = languages;
		this.modifyCountryLogic = modifyCountryLogic;
		setControls();
	}
	private void setControls()
	{
		boolean validInput = true;
		boolean validSelection = true;

		if (cbbxCountrySelection.getSelectedIndex() == 0)
		{
			validSelection = false;
		}

		try
		{
			Integer.parseInt(txtArea.getText());
		}
		catch (Exception e)
		{
			validInput = false;
		}

		try
		{
			Integer.parseInt(txtPopulation.getText());
		}
		catch (Exception e)
		{
			validInput = false;
		}

		if (txtName.getText().isEmpty())
		{
			//display a warning icon
			validInput = false;
		}

		if (cbbxContinent.getSelectedIndex() <= 0)
		{
			//display a warning icon
			validInput = false;
		}

		txtName.setEnabled(validSelection);
		txtArea.setEnabled(validSelection);
		txtPopulation.setEnabled(validSelection);

		cbbxAddableLanguages.setEnabled(validSelection);
		cbbxAddedLanguages.setEnabled(validSelection);
		cbbxContinent.setEnabled(validSelection);

		btnModify.setEnabled(validInput && validSelection);
		btnOK.setEnabled(validInput && validSelection);
	}

	private void cbbxAddableLanguages_popupMenuWillBecomeInvisible(PopupMenuEvent arg0) 
	{
		int languageIndex = cbbxAddableLanguages.getSelectedIndex();
		String selectedLanguage = "";
		if (languageIndex >0)
		{
			selectedLanguage = cbbxAddableLanguages.getSelectedItem().toString();
			addToTxtarOfficialLanguages(selectedLanguage);
		}
	}

	private void cbbxAddedLanguages_popupMenuWillBecomeInvisible(PopupMenuEvent arg0) 
	{
		int languageIndex = cbbxAddedLanguages.getSelectedIndex();
		String selectedLanguage = "";
		if (languageIndex >0)
		{
			selectedLanguage = cbbxAddedLanguages.getSelectedItem().toString();
			if (txtarOfficialLanguages.getText().startsWith(selectedLanguage +",\r\n") ||
					txtarOfficialLanguages.getText().startsWith(selectedLanguage + "."))
			{
				txtarOfficialLanguages.setText(txtarOfficialLanguages.getText().replaceFirst(selectedLanguage, ""));
			}
			else if (!(txtarOfficialLanguages.getText().endsWith(selectedLanguage + ".")))
			{
				txtarOfficialLanguages.setText(txtarOfficialLanguages.getText().replaceFirst(",\r\n"+selectedLanguage+",\r\n", ",\r\n"));	
			}
			else
			{
				txtarOfficialLanguages.setText(txtarOfficialLanguages.getText().substring(
						0, txtarOfficialLanguages.getText().length() - (selectedLanguage.length() + 4)));
				txtarOfficialLanguages.setText(txtarOfficialLanguages.getText() +".");
			}
			
			cbbxAddedLanguages.removeItemAt(languageIndex);
			cbbxAddableLanguages.addItem(selectedLanguage);
			cbbxAddedLanguages.setSelectedIndex(0);
			if (txtarOfficialLanguages.getText().startsWith(",\r\n") || txtarOfficialLanguages.getText().startsWith(".\r\n"))
			{
				txtarOfficialLanguages.setText(txtarOfficialLanguages.getText().substring(3));
			}
			if (txtarOfficialLanguages.getText().endsWith(",\r\n"))
			{
				txtarOfficialLanguages.setText(
						txtarOfficialLanguages.getText().substring
						(0, txtarOfficialLanguages.getText().length()-3));
			}
			txtarOfficialLanguages.setText(txtarOfficialLanguages.getText().replaceAll(",\r\n,\r\n", ",\r\n"));
		}
	}

	private void cbbxCountrySelection_popupMenuWillBecomeInvisible(PopupMenuEvent arg0)
	{
		if (cbbxCountrySelection.getSelectedIndex() != 0)
			{
			String countryName = cbbxCountrySelection.getSelectedItem().toString();
			
			Country thisCountry = modifyCountryLogic.getCountry(String.valueOf(modifyCountryLogic.getCountryID(countryName)));
			
			Language[] alreadyOfficialLanguages = 
					modifyCountryLogic.getAllLanguages(String.valueOf(modifyCountryLogic.getCountryID(countryName)));
	
			insertItemsIntoCbbx(cbbxAddableLanguages, languageNames);
	
			for(int d = 0; d<alreadyOfficialLanguages.length;d++)
			{
				addToTxtarOfficialLanguages(alreadyOfficialLanguages[d].getName());
			}
			
			txtName.setText(thisCountry.getName());
			txtArea.setText(String.valueOf(thisCountry.getArea()));
			txtPopulation.setText(String.valueOf(thisCountry.getPopulation()));
			cbbxContinent.setSelectedItem(thisCountry.getContinent());
			}
		else
		{
			clear();
		}
		
		txtarOfficialLanguages.getText().split(",\r\n");
	}

	private void addToTxtarOfficialLanguages(String selectedLanguage)
	{
		if (txtarOfficialLanguages.getText().endsWith("."))
		{
			txtarOfficialLanguages.setText(txtarOfficialLanguages.getText().substring(0, txtarOfficialLanguages.getText().length()-1));
		}
		txtarOfficialLanguages.setText(txtarOfficialLanguages.getText()+",\r\n"+selectedLanguage);
		cbbxAddableLanguages.removeItem(selectedLanguage);
		cbbxAddedLanguages.addItem(selectedLanguage);
		cbbxAddableLanguages.setSelectedIndex(0);
		if (txtarOfficialLanguages.getText().startsWith(","))
		{
			txtarOfficialLanguages.setText(txtarOfficialLanguages.getText().substring(3));
		}
		if (!(txtarOfficialLanguages.getText().endsWith(".")))
		{
			txtarOfficialLanguages.setText(txtarOfficialLanguages.getText()+".");
		}
	}

	private void btnOK_clicked() 
	{
		modifyCountry();	
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
		modifyCountry();	
		JOptionPane.showMessageDialog(
				this, 
				"The Country has been modified",
				"Confirmation", 
				JOptionPane.PLAIN_MESSAGE);
		clear();
	}

	private void btnClear_clicked() 
	{
		clear();
	}

	private void clear() 
	{
		txtName.setText("");
		txtArea.setText("");
		txtPopulation.setText("");
		txtarOfficialLanguages.setText("");

		cbbxAddableLanguages.removeAllItems();
		cbbxAddableLanguages.addItem("");

		cbbxAddedLanguages.removeAllItems();
		cbbxAddedLanguages.addItem("");

		cbbxContinent.setSelectedIndex(0);
		cbbxCountrySelection.setSelectedIndex(0);
	}

	private void modifyCountry()
	{
		String countryName = txtName.getText();
		String countryContinent = cbbxContinent.getSelectedItem().toString();
		int countryArea = Integer.parseInt(txtArea.getText());
		int countryPop = Integer.parseInt(txtPopulation.getText());
		
		String languages =  txtarOfficialLanguages.getText();
		if (!(languages.isEmpty()) || languages.equals("."))
		{
			languages = languages.substring(0, languages.length()-1);
		}
		String[] newCountryLanguages = languages.split(",\r\n");
		
		Country newCountry = new Country(countryName, countryPop, countryArea, countryContinent);

		int countryID = modifyCountryLogic.getCountryID(cbbxCountrySelection.getSelectedItem().toString());

		modifyCountryLogic.updateCountry(countryID, newCountry, newCountryLanguages, true);
	}

	private void insertItemsIntoCbbx(JComboBox cbbx,String[] items) 
	{
		for (int p = 0; p<items.length; p++)
		{
			cbbx.addItem(items[p]);
		}
	}
}
