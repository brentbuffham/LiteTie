package litetie;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JSpinner;
import java.awt.Insets;
import javax.swing.JCheckBox;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Dialog.ModalityType;

public class ChooserZValue extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private JCheckBox checkboxRelativeToCurrent;
	private JTextField textFieldZValue;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ChooserZValue dialog = new ChooserZValue();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ChooserZValue() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setTitle("Adjust Z Value");
		setBounds(100, 100, 330, 120);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewZValue = new JLabel("Input new Z Value");
			GridBagConstraints gbc_lblNewZValue = new GridBagConstraints();
			gbc_lblNewZValue.anchor = GridBagConstraints.EAST;
			gbc_lblNewZValue.insets = new Insets(0, 12, 5, 5);
			gbc_lblNewZValue.gridx = 0;
			gbc_lblNewZValue.gridy = 0;
			contentPanel.add(lblNewZValue, gbc_lblNewZValue);
		}
		{
			textFieldZValue = new JTextField();
			GridBagConstraints gbc_textFieldZValue = new GridBagConstraints();
			gbc_textFieldZValue.gridwidth = 3;
			gbc_textFieldZValue.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldZValue.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldZValue.gridx = 1;
			gbc_textFieldZValue.gridy = 0;
			contentPanel.add(textFieldZValue, gbc_textFieldZValue);
			textFieldZValue.setColumns(10);
		}
		{
			JLabel lblUnitType = new JLabel("reduced level");
			GridBagConstraints gbc_lblUnitType = new GridBagConstraints();
			gbc_lblUnitType.insets = new Insets(0, 0, 5, 0);
			gbc_lblUnitType.gridx = 4;
			gbc_lblUnitType.gridy = 0;
			contentPanel.add(lblUnitType, gbc_lblUnitType);
		}
		{
			checkboxRelativeToCurrent = new JCheckBox("Adjust Relative to Current Value");
			checkboxRelativeToCurrent.setHorizontalAlignment(SwingConstants.TRAILING);
			GridBagConstraints gbc_checkboxRelativeToCurrent = new GridBagConstraints();
			gbc_checkboxRelativeToCurrent.gridwidth = 5;
			gbc_checkboxRelativeToCurrent.insets = new Insets(0, 0, 0, 5);
			gbc_checkboxRelativeToCurrent.gridx = 0;
			gbc_checkboxRelativeToCurrent.gridy = 1;
			contentPanel.add(checkboxRelativeToCurrent, gbc_checkboxRelativeToCurrent);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.setActionCommand("Cancel");
			}
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	
	public JTextField getZValueTextField() {
		return textFieldZValue;
	}

	public JCheckBox getCheckboxRelativeToCurrent() {
		return checkboxRelativeToCurrent;
	}
}
