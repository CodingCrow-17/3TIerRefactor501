package view;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.PopupMenuListener;

import controller.WriteLogic;
import model.TableType;

import javax.swing.event.PopupMenuEvent;

public class DeleteDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JButton btnOK;
	private JButton btnCancel;
	private JButton btnDelete;
	private JLabel lblName;
	private JComboBox cbbxDeletables;

	private String[] cbbxDeletableValues;
	private boolean deletingCountry;
	private WriteLogic deletingLogic;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args, TableType type,String[] countries, String[] languages, WriteLogic deletingLogic) {
		try {
			DeleteDialog dialog = new DeleteDialog(type,countries,languages, deletingLogic);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @param languages 
	 * @param countries 
	 */
	public DeleteDialog(TableType type, String[] countries, String[] languages,WriteLogic deletingLogic) 
	{
		getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 11));
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setBounds(100, 100, 325, 150);

		adjustForTableType(type, countries, languages);

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
		{
			lblName = new JLabel("Name: ");
			lblName.setHorizontalAlignment(SwingConstants.RIGHT);
			lblName.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			lblName.setBounds(10, 25, 60, 14);
			contentPanel.add(lblName);
		}
		{
			cbbxDeletables = new JComboBox();
			updateCbbxDeletables();
			cbbxDeletables.addPopupMenuListener(new PopupMenuListener() {
				public void popupMenuCanceled(PopupMenuEvent e) {
					setControls();
				}
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) 
				{
					setControls();
				}
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
					setControls();
				}
			});
			cbbxDeletables.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			cbbxDeletables.setBounds(90, 22, 100, 20);
			contentPanel.add(cbbxDeletables);
		}
		{
			btnDelete = new JButton("Delete");
			btnDelete.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if (btnDelete.isEnabled())
					{
						btnDelete_Clicked();
						setControls();
					}
				}
			});
			btnDelete.setFont(new Font("Times New Roman", Font.PLAIN, 11));
			btnDelete.setBounds(210, 21, 89, 23);
			contentPanel.add(btnDelete);
		}
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnOK = new JButton("OK");
				btnOK.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (btnOK.isEnabled())
						{
							btnOK_Clicked();
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
						btnCancelClicked();
					}
				});
				btnCancel.setFont(new Font("Times New Roman", Font.PLAIN, 11));
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
			this.deletingLogic = deletingLogic;
			setControls();
		}
	}
	private void setControls()
	{
		boolean validInput = true;

		if (cbbxDeletables.getSelectedIndex() <= 0)
		{
			validInput = false;
		}

		btnOK.setEnabled(validInput);
		btnDelete.setEnabled(validInput);
	}

	private void adjustForTableType(TableType type, String[] countries, String[] languages)
	{
		switch (type) 
		{
		case COUNTRY:
			setTitle("Delete Country");
			cbbxDeletableValues = countries;
			deletingCountry = true;
			break;
		case LANGUAGE:
			setTitle("Delete Language");
			cbbxDeletableValues = languages;
			deletingCountry = false;
			break;
		}
	}
	private void btnDelete_Clicked()
	{
		delete();
		JOptionPane.showMessageDialog(
				this, 
				"Deleted!",
				"Confirmation", 
				JOptionPane.PLAIN_MESSAGE);
		clear();
		clear();
	}
	private void btnOK_Clicked()
	{
		delete();
		this.dispose();
	}
	private void btnCancelClicked()
	{
		this.dispose();
	}

	private void clear()
	{
		cbbxDeletables.setSelectedIndex(0);
	}
	private void delete()
	{
		if (deletingCountry)
		{
			deletingLogic.deleteInFullCountry(cbbxDeletables.getSelectedItem().toString());
			cbbxDeletableValues = deletingLogic.getCountryNames();
			updateCbbxDeletables();
		}
		else
		{
			deletingLogic.deleteInFullLanguage(cbbxDeletables.getSelectedItem().toString());
			cbbxDeletableValues = deletingLogic.getLanguageNames();
			updateCbbxDeletables();
		}
	}
	private void updateCbbxDeletables()
	{
		cbbxDeletables.removeAllItems();
		cbbxDeletables.addItem("");
		for(int y=0; y < cbbxDeletableValues.length; y++)
		{
			cbbxDeletables.addItem(cbbxDeletableValues[y]);
		}
	}
}
