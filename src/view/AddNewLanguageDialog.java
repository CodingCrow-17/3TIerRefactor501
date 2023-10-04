package view;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import controller.WriteLogic;
import model.Language;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class AddNewLanguageDialog extends JDialog {
	private JTextField txtName;
	private JTextArea txtarOfficialIn;
	private JLabel lblName;
	private JLabel lblOfficialin;
	private JLabel lblAdd;
	private JLabel lblDelete;
	private JComboBox cbbxAddableCountries;
	private JComboBox cbbxAddedCountries;
	private JPanel buttonPane;
	private JButton btnAdd;
	private JButton btnClear;
	private JButton btnOK;
	private JButton btnCancel;
	private JScrollPane scpnCountries;
	
	private String[] countryNames;
	private WriteLogic addLanguageLogic;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args, WriteLogic addNewLanguageLogic, String[] countries) 
	{
		try {
			AddNewLanguageDialog dialog = new AddNewLanguageDialog(addNewLanguageLogic,countries);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @param arrayOfOtherTypeNames 
	 */
	public AddNewLanguageDialog(WriteLogic addNewLanguageLogic, String[] countries) {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setControls();
			}
		});
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setTitle("New Language");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 420, 220);
		getContentPane().setLayout(null);
		{
			buttonPane = new JPanel();
			buttonPane.setBounds(0, 129, 394, 31);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				btnAdd = new JButton("Add");
				btnAdd.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) 
					{
						if (btnAdd.isEnabled())
						{
							btnAdd_clicked();
							setControls();
						}
					}
				});
				btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 11));
				btnAdd.setActionCommand("OK");
				buttonPane.add(btnAdd);
			}
			{
				btnClear = new JButton("Clear");
				btnClear.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) 
					{
						if (btnAdd.isEnabled())
						{
							btnClear_clicked();
							setControls();
						}
					}
				});
				btnClear.setFont(new Font("Times New Roman", Font.PLAIN, 11));
				btnClear.setActionCommand("OK");
				buttonPane.add(btnClear);
			}
			{
				btnOK = new JButton("OK");
				btnOK.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) 
					{
						if (btnOK.isEnabled())
						{
							btnOK_clicked();
							setControls();
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
					public void mouseClicked(MouseEvent e) 
					{
						btnCancel_clicked();
					}
				});
				btnCancel.setFont(new Font("Times New Roman", Font.PLAIN, 11));
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
		}
		{
			lblName = new JLabel("Name:");
			lblName.setHorizontalAlignment(SwingConstants.LEFT);
			lblName.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			lblName.setBounds(21, 24, 46, 14);
			getContentPane().add(lblName);
		}
		{
			lblOfficialin = new JLabel("Official in:");
			lblOfficialin.setHorizontalAlignment(SwingConstants.LEFT);
			lblOfficialin.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			lblOfficialin.setBounds(21, 50, 71, 14);
			getContentPane().add(lblOfficialin);
		}
		{
			txtName = new JTextField();
			txtName.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					setControls();
				}
			});
			txtName.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent arg0) 
				{
					setControls();
				}
			});
			txtName.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			txtName.setBounds(77, 22, 309, 20);
			getContentPane().add(txtName);
			txtName.setColumns(10);
		}
		{
			
			txtarOfficialIn = new JTextArea(5,20);
			txtarOfficialIn.setEditable(false);
			txtarOfficialIn.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			txtarOfficialIn.setColumns(10);
			scpnCountries = new JScrollPane(txtarOfficialIn,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scpnCountries.setBounds(290, 48, 96, 70);
			getContentPane().add(scpnCountries);
		}
		{
			cbbxAddableCountries = new JComboBox(new String[] {""});
			insertValuesIntoCbbxAddableCountries(countries);
			cbbxAddableCountries.addPopupMenuListener(new PopupMenuListener() {
				public void popupMenuCanceled(PopupMenuEvent e) 
				{
					setControls();
				}
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) 
				{
					cbbxAddableCountries_popupMenuWillBecomeInvisible(e);
					setControls();
				}
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) 
				{
					setControls();
				}
			});
			cbbxAddableCountries.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			cbbxAddableCountries.setBounds(79, 61, 95, 20);
			getContentPane().add(cbbxAddableCountries);
		}
		
		cbbxAddedCountries = new JComboBox(new String[] {""});
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
		cbbxAddedCountries.setBounds(185, 61, 95, 20);
		getContentPane().add(cbbxAddedCountries);
		{
			lblAdd = new JLabel("Add");
			lblAdd.setHorizontalAlignment(SwingConstants.CENTER);
			lblAdd.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			lblAdd.setBounds(79, 47, 95, 14);
			getContentPane().add(lblAdd);
		}
		{
			lblDelete = new JLabel("Delete");
			lblDelete.setHorizontalAlignment(SwingConstants.CENTER);
			lblDelete.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			lblDelete.setBounds(185, 47, 95, 14);
			getContentPane().add(lblDelete);
		}
		countryNames = Arrays.copyOf(countries, countries.length);
		this.addLanguageLogic = addNewLanguageLogic;
		setControls();
	}
	private void setControls()
	{
		boolean validInput = true;
	
		if (txtName.getText().isEmpty())
		{
			validInput = false;
		}
		
		btnAdd.setEnabled(validInput);
		btnOK.setEnabled(validInput);
	}
	
	private void cbbxAddableCountries_popupMenuWillBecomeInvisible(PopupMenuEvent arg0) 
	{
		int countryIndex = cbbxAddableCountries.getSelectedIndex();
		String selectedCountry = "";
		if (countryIndex >0)
		{
			selectedCountry = cbbxAddableCountries.getSelectedItem().toString();
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
	
	private void btnOK_clicked() 
	{
		addNewLanguage();	
		clear();
		this.dispose();
	}

	private void btnCancel_clicked() 
	{
		clear();
		this.dispose();
	}	
	
	private void btnAdd_clicked() 
	{
		addNewLanguage();
		JOptionPane.showMessageDialog(
				this, 
				"And thus, the language of: ,\r\n"
				+txtName.getText()+ " ,\r\n"
				+"was created.", 
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
		insertValuesIntoCbbxAddableCountries(countryNames);
		
		cbbxAddedCountries.removeAllItems();
		cbbxAddedCountries.addItem("");
	}
	
	private void insertValuesIntoCbbxAddableCountries(String[] countries) 
	{
		for (int p = 0; p<countries.length; p++)
		{
			cbbxAddableCountries.addItem(countries[p]);
		}
	}
	
	private void addNewLanguage() 
	{
		String languageName = txtName.getText();
		String countries =  txtarOfficialIn.getText();
		if(!(countries.isEmpty()) || countries.equals("."))
		{
			countries = countries.substring(0, countries.length()-1);
		}
		
		String[] languageCountries = countries.split(",\r\n");
		
		Language newLanguage = new Language(languageName);
		addLanguageLogic.addInFullLanguage(newLanguage, languageCountries);
	}
}
