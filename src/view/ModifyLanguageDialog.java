package view;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.PopupMenuListener;

import controller.ReadLogic;
import controller.WriteLogic;
import model.Country;
import model.Language;

import javax.swing.event.PopupMenuEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class ModifyLanguageDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JButton btnOK;
	private JButton btnCancel;
	private JButton btnModify; 
	private JButton btnClear; 
	private JTextField txtName;
	private JLabel lblName;
	private JLabel lblOfficialin;
	private JLabel lblLanguage;
	private JComboBox cbbxLanguageSelection;
	private JComboBox cbbxAddedCountries;
	private JLabel lblDelete;
	private JComboBox cbbxAddableCountries;
	private JLabel lblAdd;
	private JTextArea txtarOfficialIn;
	private Component scpnCountries;

	private String[] countryNames;
	private WriteLogic modifyLanguageLogic;
	private ReadLogic readLogic = ReadLogic.getInstance();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, WriteLogic modifyLanguageLogic, String[] languages, String[] countries)
	{
		try {
			ModifyLanguageDialog dialog = new ModifyLanguageDialog(modifyLanguageLogic, languages,countries);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ModifyLanguageDialog(WriteLogic modifyLanguageLogic, String[] languages, String[] countries) {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setControls();
			}
		});
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setTitle("Modify Language");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 420, 240);
		getContentPane().setLayout(null);
		{
			buttonPane = new JPanel();
			buttonPane.setBounds(-39, 160, 445, 31);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(null);

			btnOK = new JButton("OK");
			btnOK.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (btnOK.isEnabled())
					{
						btnOK_clicked();
						setControls();
					}
				}
			});
			btnOK.setBounds(295, 5, 66, 21);
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
			btnCancel.setBounds(371, 5, 68, 21);
			btnCancel.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			btnCancel.setActionCommand("Cancel");
			buttonPane.add(btnCancel);


			btnModify = new JButton("Modify");
			btnModify.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (btnModify.isEnabled())
					{
						btnModify_clicked();
						setControls();
					}
				}
			});
			btnModify.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			btnModify.setEnabled(true);
			btnModify.setActionCommand("OK");
			btnModify.setBounds(129, 5, 74, 21);
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
			btnClear.setBounds(213, 5, 72, 21);
			buttonPane.add(btnClear);


			lblName = new JLabel("Name:");
			lblName.setHorizontalAlignment(SwingConstants.LEFT);
			lblName.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			lblName.setBounds(20, 52, 46, 14);
			getContentPane().add(lblName);


			lblOfficialin = new JLabel("Official in:");
			lblOfficialin.setHorizontalAlignment(SwingConstants.LEFT);
			lblOfficialin.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			lblOfficialin.setBounds(20, 81, 71, 14);
			getContentPane().add(lblOfficialin);


			txtName = new JTextField();
			txtName.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			txtName.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					setControls();
				}
			});

			txtName.setBounds(86, 50, 306, 20);
			getContentPane().add(txtName);
			txtName.setColumns(10);
			
			txtarOfficialIn = new JTextArea(5,20);
			txtarOfficialIn.setEditable(false);
			txtarOfficialIn.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			txtarOfficialIn.setColumns(10);
			scpnCountries = new JScrollPane(txtarOfficialIn,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scpnCountries.setBounds(296, 79, 96, 70);
			getContentPane().add(scpnCountries);

			lblLanguage = new JLabel("Language:");
			lblLanguage.setHorizontalAlignment(SwingConstants.LEFT);
			lblLanguage.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			lblLanguage.setBounds(20, 25, 46, 14);
			getContentPane().add(lblLanguage);


			cbbxLanguageSelection = new JComboBox(new String[] {""});
			insertItemsIntoCbbx(cbbxLanguageSelection, languages);
			cbbxLanguageSelection.addPopupMenuListener(new PopupMenuListener() {
				public void popupMenuCanceled(PopupMenuEvent e) {
					setControls();
				}
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
					cbbxSelectedLanguage_popupMenuWillBecomeInvisible(e);
					setControls();
				}
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
					clear();
					setControls();
				}
			});
			cbbxLanguageSelection.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			cbbxLanguageSelection.setBounds(86, 22, 306, 20);
			getContentPane().add(cbbxLanguageSelection);

			cbbxAddedCountries = new JComboBox(new Object[]{});
			cbbxAddedCountries.addPopupMenuListener(new PopupMenuListener() {
				public void popupMenuCanceled(PopupMenuEvent e) {
					setControls();
				}
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
					cbbxAddedCountries_popupMenuWillBecomeInvisible(e);
					setControls();
				}
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
					setControls();
				}
			});
			cbbxAddedCountries.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			cbbxAddedCountries.setBounds(191, 95, 95, 20);
			getContentPane().add(cbbxAddedCountries);


			lblDelete = new JLabel("Delete");
			lblDelete.setHorizontalAlignment(SwingConstants.CENTER);
			lblDelete.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			lblDelete.setBounds(191, 81, 95, 14);
			getContentPane().add(lblDelete);


			cbbxAddableCountries = new JComboBox(new Object[]{});
			cbbxAddableCountries.addPopupMenuListener(new PopupMenuListener() {
				public void popupMenuCanceled(PopupMenuEvent e) {
					setControls();
				}
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
					cbbxAddableCountries_popupMenuWillBecomeInvisible(e);
				}
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
					setControls();
				}
			});
			cbbxAddableCountries.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			cbbxAddableCountries.setBounds(86, 95, 95, 20);
			getContentPane().add(cbbxAddableCountries);


			lblAdd = new JLabel("Add");
			lblAdd.setHorizontalAlignment(SwingConstants.CENTER);
			lblAdd.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			lblAdd.setBounds(86, 81, 95, 14);
			getContentPane().add(lblAdd);
		}
		countryNames = countries;
		this.modifyLanguageLogic = modifyLanguageLogic;
		setControls();
	}
	private void setControls()
	{
		boolean validInput = true;
		boolean validSelection = true;

		if (cbbxLanguageSelection.getSelectedIndex() == 0)
		{
			validSelection = false;
		}

		if (txtName.getText().isEmpty())
		{
			validInput = false;
		}
		txtName.setEnabled(validSelection);

		cbbxAddableCountries.setEnabled(validSelection);
		cbbxAddedCountries.setEnabled(validSelection);

		btnModify.setEnabled(validInput && validSelection);
		btnOK.setEnabled(validInput && validSelection);
	}

	private void cbbxAddableCountries_popupMenuWillBecomeInvisible(PopupMenuEvent arg0) 
	{
		int countryIndex = cbbxAddableCountries.getSelectedIndex();
		String selectedCountry = "";
		if (countryIndex >0)
		{
			selectedCountry = cbbxAddableCountries.getSelectedItem().toString();
			addToTxtarOfficialIn(selectedCountry);
		}
	}
	private void cbbxAddedCountries_popupMenuWillBecomeInvisible(PopupMenuEvent arg0) 
	{
		int countryIndex = cbbxAddedCountries.getSelectedIndex();
		String selectedCountry = "";
		if (countryIndex >0)
		{
			selectedCountry = cbbxAddedCountries.getSelectedItem().toString();
			if (txtarOfficialIn.getText().startsWith(selectedCountry + ",\r\n") ||
					txtarOfficialIn.getText().startsWith(selectedCountry + "."))
			{
				txtarOfficialIn.setText(txtarOfficialIn.getText().replaceFirst(selectedCountry, ""));
			}
			else if (!(txtarOfficialIn.getText().endsWith(selectedCountry + ".")))
			{
				txtarOfficialIn.setText(txtarOfficialIn.getText().replaceFirst(",\r\n"+selectedCountry+",\r\n", ",\r\n"));
			}
			else
			{
				txtarOfficialIn.setText(txtarOfficialIn.getText().substring(
						0, txtarOfficialIn.getText().length() - (selectedCountry.length() + 4)));
				txtarOfficialIn.setText(txtarOfficialIn.getText() +".");
			}
			cbbxAddedCountries.removeItemAt(countryIndex);
			cbbxAddableCountries.addItem(selectedCountry);
			cbbxAddedCountries.setSelectedIndex(0);
			if (txtarOfficialIn.getText().startsWith(",\r\n") || txtarOfficialIn.getText().startsWith(".\r\n"))
			{
				txtarOfficialIn.setText(txtarOfficialIn.getText().substring(3));
			}
			if (txtarOfficialIn.getText().endsWith(",\r\n"))
			{
				txtarOfficialIn.setText(
						txtarOfficialIn.getText().substring
						(0, txtarOfficialIn.getText().length()-3));
			}
			txtarOfficialIn.setText(txtarOfficialIn.getText().replaceAll(",\r\n,\r\n", ",\r\n"));
		}
	}

	private void cbbxSelectedLanguage_popupMenuWillBecomeInvisible(PopupMenuEvent arg0)
	{
		if (cbbxLanguageSelection.getSelectedIndex() != 0)
		{

			String languageName = cbbxLanguageSelection.getSelectedItem().toString();
			
			Country[] alreadyOfficialIn = 
					readLogic.getAllCountries(String.valueOf(readLogic.getLanguageID(languageName)));
	
			Language thisLanguage = 
					readLogic.getLanguage(String.valueOf(readLogic.getLanguageID(languageName)));
			
			insertItemsIntoCbbx(cbbxAddableCountries, countryNames);
	
			for(int d = 0; d<alreadyOfficialIn.length;d++)
			{
				addToTxtarOfficialIn(alreadyOfficialIn[d].getName());
			}
			txtName.setText(thisLanguage.getName());
		}
		else
		{
			clear();
		}
		
		txtarOfficialIn.getText().split(",\r\n");
	}

	private void addToTxtarOfficialIn(String selectedCountry)
	{
		if (txtarOfficialIn.getText().endsWith("."))
		{
			txtarOfficialIn.setText(txtarOfficialIn.getText().substring(0, txtarOfficialIn.getText().length()-1));
		}
		txtarOfficialIn.setText(txtarOfficialIn.getText()+",\r\n"+selectedCountry);
		cbbxAddableCountries.removeItem(selectedCountry);
		cbbxAddedCountries.addItem(selectedCountry);
		cbbxAddableCountries.setSelectedIndex(0);
		if (txtarOfficialIn.getText().startsWith(","))
		{
			txtarOfficialIn.setText(txtarOfficialIn.getText().substring(3));
		}
		if (!(txtarOfficialIn.getText().endsWith(".")))
		{
			txtarOfficialIn.setText(txtarOfficialIn.getText()+".");
		}
	}

	private void btnOK_clicked() 
	{
		modifyLanguage();	
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
		modifyLanguage();	
		JOptionPane.showMessageDialog(
				this, 
				"The Language has been modified",
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
		txtarOfficialIn.setText("");

		cbbxAddableCountries.removeAllItems();
		cbbxAddableCountries.addItem("");

		cbbxAddedCountries.removeAllItems();
		cbbxAddedCountries.addItem("");

		cbbxLanguageSelection.setSelectedIndex(0);
	}

	private void insertItemsIntoCbbx(JComboBox cbbx,String[] items) 
	{
		for (int p = 0; p<items.length; p++)
		{
			cbbx.addItem(items[p]);
		}
	}

	private void modifyLanguage() 
	{
		String languageName = txtName.getText();
		String countries =  txtarOfficialIn.getText();
		if(!(countries.isEmpty()) || countries.equals("."))
		{
			countries = countries.substring(0, countries.length()-1);
		}
		
		String[] newLanguageCountries = countries.split(",\r\n");

		int languageID = readLogic.getLanguageID(cbbxLanguageSelection.getSelectedItem().toString());

		Language newLanguage = new Language(languageName);

		modifyLanguageLogic.updateLanguage(languageID, newLanguage, newLanguageCountries, true);
	}
}
