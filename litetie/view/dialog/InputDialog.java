package litetie.view.dialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.UIManager;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

@SuppressWarnings("serial")
public class InputDialog extends JDialog {
	// Icons and Attributes
	// UI Elements Declaration
	// Panels
	JPanel southPanel = new JPanel();
	JPanel northPanel = new JPanel();
	// Buttons
	JButton okButton = new JButton();
	JButton cancelButton = new JButton();
	// Labels
	JLabel labelText = new JLabel();
	JLabel labelInformation = new JLabel("Place an \"=\" sign in front of the text to create a calculation.");
	// Text Fields
	JTextField textField = new JTextField();

	// Strings
	String s;

	/**
	 * Default Input Dialog for text and single value inputs.
	 * @param frame
	 * @param title
	 * @param label
	 * @param value
	 */
	public InputDialog(JFrame frame, String title , String label, String value) {
//		setModalityType(ModalityType.APPLICATION_MODAL);
		this.setVisible(true);
		this.setResizable(false);
		if(title != null || label != null){
			this.setTitle(title);
			labelText.setText(label);
			textField.setText(value);
		}
		else{
			this.setTitle("Input Value");
			labelText.setText("Enter Value");
			textField.setText("Type Here...");
		}
		
		this.getContentPane().setLayout(new BorderLayout());
		// Get and Set the GUI Size
		this.setSize(320, 115);
		this.setBounds(100,100,320,115);
		// Content Additions
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		getContentPane().add(northPanel, BorderLayout.NORTH);
		
		// Layout Declarations
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		GridBagLayout buttonBarNorthPanelGridBag = new GridBagLayout();

		// Layout Settings
		northPanel.setLayout(buttonBarNorthPanelGridBag);
		buttonBarNorthPanelGridBag.columnWidths = new int[] { 100, 150 };
		buttonBarNorthPanelGridBag.rowHeights = new int[] { 30, 5 };
		buttonBarNorthPanelGridBag.columnWeights = new double[] { 0.0, 0.0 };
		buttonBarNorthPanelGridBag.rowWeights = new double[] { 0.0,	Double.MIN_VALUE };

		// UI Attributes
		// Texts
		okButton.setText("OK");
		cancelButton.setText("Cancel");

		// Add Buttons to the South Bar
		southPanel.add(cancelButton);
		southPanel.add(okButton);

		// TextField Size
		Dimension textFieldSize = new Dimension(150, 22);
		textField.setPreferredSize(textFieldSize);
		textField.setToolTipText("Type Here... Or use \"=\" to create an equation.\n i.e. =42+17" );

		// Grid Bag Declarations
		GridBagConstraints gbc_labelText = new GridBagConstraints();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		GridBagConstraints gbc_titleLabel = new GridBagConstraints();
		
		// Grid Bag Settings
		
		//Information Label
		gbc_titleLabel.gridwidth = 2;
		gbc_titleLabel.gridx = 0;
		gbc_titleLabel.gridy = 1;
		gbc_titleLabel.insets = new Insets(10, 0, 0, 10);
		labelInformation.setFont(new Font("Dialog", Font.PLAIN, 10));
		labelInformation.setForeground(Color.RED);
		// Label
		gbc_labelText.anchor = GridBagConstraints.EAST;
		gbc_labelText.gridx = 0;
		gbc_labelText.gridy = 0;
		gbc_labelText.insets = new Insets(10, 0, 0, 10);
		//TextField
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		gbc_textField.insets = new Insets(10, 0, 0, 10);

		// Add Buttons to the North Bar
		northPanel.add(labelText, gbc_labelText);
		northPanel.add(textField, gbc_textField);
		northPanel.add(labelInformation, gbc_titleLabel);


		// Set the default button for the dialog to OK
		getRootPane().setDefaultButton(okButton);

		// UI Listener Adding
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Check the code input
				try {
					if (getText().length() > 0) {
						// Check the title input
						
							okPressed = true;
							setVisible(false);
					} else {
						JOptionPane.showMessageDialog(getParent(),"Text is null value.");
						okPressed = false;
						setVisible(false);
					}
				} catch (HeadlessException | UnknownFunctionException
						| UnparsableExpressionException e1) {
					JOptionPane.showMessageDialog(getParent(), e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				okPressed = false;
				setVisible(false);
			}
		});
	}

	public static String showInputDialog(InputDialog input) {
		return (String)showInputDialog(input);
		}
	
	
	public JTextField getTextField() throws NumberFormatException {
		return textField;

	}

	private boolean okPressed;

	public Boolean getOkPressed() {
		return okPressed;
	}

	public String getText() throws UnknownFunctionException, UnparsableExpressionException {
		s = getTextField().getText();
		
		//This code takes Text Values and calculates them the text value returned is the resultant equation.
		if (s.startsWith("=")){
			s = calculateValueFromString(s);
		}
		return s;
	}

	//Native Java Library that replaces the calculateValueForomStingUsingJavaScriptEval()
	private String calculateValueFromString(String newValue) throws UnknownFunctionException, UnparsableExpressionException {
		newValue = newValue.substring(1);
	    Calculable c = new ExpressionBuilder(newValue).build();
	    double dVal = c.calculate();
	    if(dVal%1 == 0){
	    	newValue = Integer.toString((int) dVal);
	    }
	    else{
	    	newValue = Double.toString(dVal);
	    }
	    
		return newValue;
	}
}
