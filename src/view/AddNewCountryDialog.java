package view;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.BoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import controller.WriteLogic;
import model.Country;

import java.awt.Font;
import java.util.Arrays;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddNewCountryDialog extends JDialog  {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtName;
	private JTextField txtArea;
	private JTextField txtPopulation;
	private JTextArea txtarOfficialLanguages;
	private JLabel lblName;
	private JLabel lblContinent;
	private JLabel lblPopulation;
	private JLabel lblArea;
	private JLabel lblOfficialLanguages;
	private JLabel lblAdd;
	private JLabel lblRemove;
	private JPanel buttonPane;
	private JButton btnClear; 
	private JButton btnOK; 
	private JButton btnCancel;
	private JButton btnAdd;
	private JScrollPane scpnLanguages;

	private JComboBox cbbxAddableLanguages;
	private JComboBox cbbxContinent;
	private JComboBox cbbxAddedLanguages;

	private final String[] CONTINENTS = {"","Africa","Americas","Asia","Europe","Oceania",};

	private String[] languageNames;
	private WriteLogic addCountryLogic;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 * @param languages 
	 */
	public AddNewCountryDialog(WriteLogic addCountryLogic, String[] languages) 
	{
		getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 11));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setResizable(false);
		setTitle("New Country");
		setBounds(100, 100, 450, 266);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setControls();
			}
		});
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		lblName = new JLabel("Name:");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		lblName.setBounds(24, 25, 46, 14);
		contentPanel.add(lblName);

		lblContinent = new JLabel("Continent:");
		lblContinent.setHorizontalAlignment(SwingConstants.LEFT);
		lblContinent.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		lblContinent.setBounds(24, 52, 61, 14);
		contentPanel.add(lblContinent);

		lblArea = new JLabel("Area (km^2):");
		lblArea.setHorizontalAlignment(SwingConstants.LEFT);
		lblArea.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		lblArea.setBounds(24, 106, 80, 14);
		contentPanel.add(lblArea);

		lblPopulation = new JLabel("Population:");
		lblPopulation.setHorizontalAlignment(SwingConstants.LEFT);
		lblPopulation.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		lblPopulation.setBounds(24, 79, 61, 14);
		contentPanel.add(lblPopulation);

		lblOfficialLanguages = new JLabel("Official Languages:");
		lblOfficialLanguages.setHorizontalAlignment(SwingConstants.LEFT);
		lblOfficialLanguages.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		lblOfficialLanguages.setBounds(24, 131, 106, 14);
		contentPanel.add(lblOfficialLanguages);

		txtName = new JTextField();
		txtName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) 
			{
				setControls();
			}
		});
		txtName.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtName.setHorizontalAlignment(SwingConstants.LEFT);
		txtName.setBounds(126, 23, 308, 20);
		contentPanel.add(txtName);
		txtName.setColumns(10);

		txtArea = new JTextField();
		txtArea.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				setControls();
			}
		});
		txtArea.addInputMethodListener(new InputMethodListener() {
			public void caretPositionChanged(InputMethodEvent event) 
			{
				setControls();
			}
			public void inputMethodTextChanged(InputMethodEvent event)
			{
				setControls();
			}
		});
		txtArea.setHorizontalAlignment(SwingConstants.LEFT);
		txtArea.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtArea.setColumns(10);
		txtArea.setBounds(126, 102, 308, 20);
		contentPanel.add(txtArea);

		txtPopulation = new JTextField();
		txtPopulation.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) 
			{
				setControls();
			}
		});
		txtPopulation.setHorizontalAlignment(SwingConstants.LEFT);
		txtPopulation.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtPopulation.setColumns(10);
		txtPopulation.setBounds(126, 75, 308, 20);
		contentPanel.add(txtPopulation);

		cbbxAddableLanguages = new JComboBox(new String[] {""});
		insertValuesIntoCbbxAddableLanguages(languages);
		cbbxAddableLanguages.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) 
			{
				setControls();
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) 
			{
				cbbxAddableLanguages_popupMenuWillBecomeInvisible(e);
				setControls();
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) 
			{
				setControls();
			}
		});
		cbbxAddableLanguages.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		cbbxAddableLanguages.setBounds(125, 144, 96, 20);
		contentPanel.add(cbbxAddableLanguages);

		cbbxContinent = new JComboBox(CONTINENTS);
		cbbxContinent.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		cbbxContinent.setBounds(126, 50, 308, 20);
		cbbxContinent.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) 
			{
				setControls();
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) 
			{
				setControls();
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) 
			{
				setControls();
			}
		});
		contentPanel.add(cbbxContinent);

		cbbxAddedLanguages = new JComboBox(new String[] {""});
		cbbxAddedLanguages.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0)
			{
				setControls();
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0)
			{
				cbbxAddedLanguage_popupMenuWillBecomeInvisible(arg0);
				setControls();
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) 
			{
				setControls();
			}
		});
		cbbxAddedLanguages.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		cbbxAddedLanguages.setBounds(231, 144, 96, 20);
		contentPanel.add(cbbxAddedLanguages);

		lblAdd = new JLabel("Add");
		lblAdd.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdd.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		lblAdd.setBounds(126, 131, 96, 14);
		contentPanel.add(lblAdd);

		lblRemove = new JLabel("Remove");
		lblRemove.setHorizontalAlignment(SwingConstants.CENTER);
		lblRemove.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		lblRemove.setBounds(231, 131, 96, 14);
		contentPanel.add(lblRemove);
		
		txtarOfficialLanguages = new JTextArea(5, 20);
		txtarOfficialLanguages.setEditable(false);
		txtarOfficialLanguages.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		txtarOfficialLanguages.setColumns(10);
		scpnLanguages = new JScrollPane(txtarOfficialLanguages,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scpnLanguages.setBounds(338, 129, 96, 66);
		contentPanel.add(scpnLanguages);
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			btnAdd = new JButton("Add");
			btnAdd.setEnabled(false);
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
			buttonPane.add(btnAdd);
			btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			btnAdd.setActionCommand("OK");

			btnClear = new JButton("Clear");
			btnClear.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) 
				{
					btnClear_clicked();
					setControls();
				}
			});
			buttonPane.add(btnClear);
			btnClear.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			btnClear.setActionCommand("OK");
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
		languageNames = Arrays.copyOf(languages,languages.length);
		this.addCountryLogic = addCountryLogic;
		setControls();
	}

	private void insertValuesIntoCbbxAddableLanguages(String[] languages) 
	{
		for (int p = 0; p<languages.length; p++)
		{
			cbbxAddableLanguages.addItem(languages[p]);
		}
	}

	private void setControls()
	{
		boolean validInput = true;

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
			validInput = false;
		}

		if (cbbxContinent.getSelectedIndex() <= 0)
		{
			validInput = false;
		}
		btnAdd.setEnabled(validInput);
		btnOK.setEnabled(validInput);
	}

	private void cbbxAddableLanguages_popupMenuWillBecomeInvisible(PopupMenuEvent arg0) 
	{
		int languageIndex = cbbxAddableLanguages.getSelectedIndex();
		String selectedLanguage = "";
		if (languageIndex >0)
		{
			selectedLanguage = cbbxAddableLanguages.getSelectedItem().toString();
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
	}

	private void cbbxAddedLanguage_popupMenuWillBecomeInvisible(PopupMenuEvent arg0) 
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

	private void btnOK_clicked() 
	{
		addCountry();	
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
		addCountry();
		JOptionPane.showMessageDialog(
				this, 
				"And thus, the nation of: ,\r\n"
				+txtName.getText()+ " ,\r\n"
				+"was formed.", 
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
		insertValuesIntoCbbxAddableLanguages(languageNames);

		cbbxAddedLanguages.removeAllItems();
		cbbxAddedLanguages.addItem("");
		cbbxContinent.setSelectedIndex(0);
	}

	private void addCountry()
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
		String[] countryLanguages = languages.split(",\r\n");

		Country newCountry = new Country(countryName, countryPop, countryArea, countryContinent);
		addCountryLogic.addInFullCountry(newCountry, countryLanguages);
	}
}

