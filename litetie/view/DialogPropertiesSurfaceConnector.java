/**
 * 
 * @author Brent Buffham
 * @category Properties
 * @version 1.0
 * @description Dialog for modifying the Surface Connection Properties.
 * @date 30th December 2013 
 */
package litetie.view;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import litetie.model.Dummy;
import litetie.model.FromToException;
import litetie.model.NegativeNumberException;
import litetie.model.SurfaceConnector;
import litetie.model.ZeroArgumentException;



@SuppressWarnings("serial")
public class DialogPropertiesSurfaceConnector extends JDialog{

	static DecimalFormat decimal0 = new DecimalFormat("#0");

	private JPanel layoutPanel = null;
	private JButton cancel = null;
	
	static String[] ties = new String[] {"Tie 1", "Tie 2", "Tie 3", "Tie 4", "Tie 5", "Tie 6" };
	static SpinnerListModel tieModel = new SpinnerListModel(ties);
	private boolean okPushed;
	private boolean cancelPushed;

	public static String status;
	boolean badValue = false;
	static boolean isShowBoxVisible = false;
	protected Component dialogOwner;

	private static  int delay;
	private static  int fromHID;
	private static  int toHID;
	private static  Dummy from;
	private static  Dummy to;
	private static  Color color;
	private static  int id;

	
	public static  JTextField [] listOfTies = new JTextField[6];
	
	public static SurfaceConnector currentSurfaceConnector;
	
	private static SurfaceConnector oldSurfaceConnector;
	private JPanel panel;
	private JButton ok;
	private static JTextField textFieldDelay;
	private static JTextField textFieldFromHID;
	private static JTextField textFieldToHID;
	private static JPanel colorWell;

	private JLabel labelDelay;
	private JLabel labelColor;
	private JLabel labelFromID;
	private JLabel labelToID;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension dialogSize = new Dimension( 300, 180);
	Rectangle bounds = new Rectangle(
			(int)(screenSize.getWidth()/2-(dialogSize.getWidth()/2)),
			(int) (screenSize.getHeight()/2-(dialogSize.getHeight()/2)), 
			(int)dialogSize.getWidth(), 
			(int)dialogSize.getHeight());
	private Color currentColor;
	private JSpinner spinnerListedTies;
	private JCheckBox checkboxSetToListedTies;
	
	
	public DialogPropertiesSurfaceConnector(JPanel canvasPanel) {
		super();
		initialize();
	}

	private void initialize() {
		this.setResizable(false);
		this.setBounds(bounds);
		this.setTitle("Surface Connection Properties" );
		this.setContentPane(getLayoutPanel());
		
	}
	
	private JPanel getLayoutPanel() {
		if (layoutPanel == null) {
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.gridwidth = 2;
			gridBagConstraints21.gridy = 13;
			
			layoutPanel = new JPanel();
			layoutPanel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			layoutPanel.setMinimumSize(new Dimension(100, 22));
			GridBagLayout gbl_layoutPanel = new GridBagLayout();
			gbl_layoutPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0};
			gbl_layoutPanel.columnWeights = new double[]{1.0, 1.0, 1.0};
			gbl_layoutPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
			layoutPanel.setLayout(gbl_layoutPanel);
			GridBagConstraints gbc_checkboxSetToListedTies = new GridBagConstraints();
			gbc_checkboxSetToListedTies.insets = new Insets(5, 0, 5, 5);
			gbc_checkboxSetToListedTies.gridx = 0;
			gbc_checkboxSetToListedTies.gridy = 0;
			layoutPanel.add(getCheckboxSetToListedTies(), gbc_checkboxSetToListedTies);
			GridBagConstraints gbc_spinnerListedTies = new GridBagConstraints();
			gbc_spinnerListedTies.anchor = GridBagConstraints.WEST;
			gbc_spinnerListedTies.insets = new Insets(5, 0, 5, 1);
			gbc_spinnerListedTies.gridx = 1;
			gbc_spinnerListedTies.gridy = 0;
			layoutPanel.add(getSpinnerListedTies(), gbc_spinnerListedTies);
			GridBagConstraints gbc_labelDelay = new GridBagConstraints();
			gbc_labelDelay.insets = new Insets(0, 0, 5, 5);
			gbc_labelDelay.anchor = GridBagConstraints.EAST;
			gbc_labelDelay.gridx = 0;
			gbc_labelDelay.gridy = 1;
			layoutPanel.add(getLabelDelay(), gbc_labelDelay);
			GridBagConstraints gbc_textFieldDelay = new GridBagConstraints();
			gbc_textFieldDelay.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldDelay.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldDelay.gridx = 1;
			gbc_textFieldDelay.gridy = 1;
			layoutPanel.add(getTextFieldDelay(), gbc_textFieldDelay);
			GridBagConstraints gbc_labelColor = new GridBagConstraints();
			gbc_labelColor.insets = new Insets(0, 0, 5, 0);
			gbc_labelColor.gridx = 2;
			gbc_labelColor.gridy = 1;
			layoutPanel.add(getLabelColor(), gbc_labelColor);
			GridBagConstraints gbc_lblBenchHeight = new GridBagConstraints();
			gbc_lblBenchHeight.insets = new Insets(0, 0, 5, 5);
			gbc_lblBenchHeight.anchor = GridBagConstraints.EAST;
			gbc_lblBenchHeight.gridx = 0;
			gbc_lblBenchHeight.gridy = 2;
			layoutPanel.add(getLabelFromID(), gbc_lblBenchHeight);
			GridBagConstraints  gbc_textFieldFromHID = new GridBagConstraints();
			gbc_textFieldFromHID.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldFromHID.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldFromHID.gridx = 1;
			gbc_textFieldFromHID.gridy = 2;
			layoutPanel.add(getTextFieldFromHID(), gbc_textFieldFromHID);
			GridBagConstraints gbc_colorWell = new GridBagConstraints();
			gbc_colorWell.gridheight = 2;
			gbc_colorWell.insets = new Insets(0, 10, 10, 10);
			gbc_colorWell.gridx = 2;
			gbc_colorWell.gridy = 2;
			layoutPanel.add(getColorWell(), gbc_colorWell);
			GridBagConstraints gbc_lblFloorLevel = new GridBagConstraints();
			gbc_lblFloorLevel.insets = new Insets(0, 0, 5, 5);
			gbc_lblFloorLevel.anchor = GridBagConstraints.EAST;
			gbc_lblFloorLevel.gridx = 0;
			gbc_lblFloorLevel.gridy = 3;
			layoutPanel.add(getLabelToID(), gbc_lblFloorLevel);
			GridBagConstraints gbc_textFieldToHID = new GridBagConstraints();
			gbc_textFieldToHID.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldToHID.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldToHID.gridx = 1;
			gbc_textFieldToHID.gridy = 3;
			layoutPanel.add(getTextFieldToHID(), gbc_textFieldToHID);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.anchor = GridBagConstraints.SOUTH;
			gbc_panel.gridwidth = 3;
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 4;
			layoutPanel.add(getPanel(), gbc_panel);	
		}
		return layoutPanel;
	}
	private JTextField getTextFieldDelay() {
		if (textFieldDelay == null) {
			textFieldDelay = new JTextField();
			textFieldDelay.setPreferredSize(new Dimension(60, 22));
			textFieldDelay.setEnabled(false);
			textFieldDelay.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						int d = Integer.parseInt(getTextFieldDelay().getText());
						getTextFieldDelay().setText(Integer.toString(d));
						getTextFieldDelay().setForeground(Color.black);
						
						return true;
						}
					catch (NumberFormatException nfe){
						getTextFieldDelay().setForeground(Color.red);
						System.out.println("Caught at getTextFieldHoleLength() - NumberFormatException");
						return false;
						}
				}
			});

		}
		return textFieldDelay;
	}
	private JTextField getTextFieldFromHID() {
		if (textFieldFromHID == null) {
			textFieldFromHID = new JTextField();
			textFieldFromHID.setPreferredSize(new Dimension(60, 22));
			textFieldFromHID.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						int d = Integer.parseInt(getTextFieldFromHID().getText());
						getTextFieldFromHID().setText(Integer.toString(d));
						getTextFieldFromHID().setForeground(Color.black);
						
						return true;
						}
					catch (NumberFormatException nfe){
						getTextFieldFromHID().setForeground(Color.red);
						System.out.println("Caught at getTextFieldBenchHeight() - NumberFormatException");
						return false;
						}
				}
			});
		}
		return textFieldFromHID;
	}
	private JTextField getTextFieldToHID() {
		if (textFieldToHID == null) {
			textFieldToHID = new JTextField();
			textFieldToHID.setPreferredSize(new Dimension(60, 22));
			textFieldToHID.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						int d = Integer.parseInt(getTextFieldToHID().getText());
						getTextFieldToHID().setText(Integer.toString(d));
						getTextFieldToHID().setForeground(Color.black);
						
						return true;
						}
					catch (NumberFormatException nfe){
						getTextFieldToHID().setForeground(Color.red);
						System.out.println("Caught at getTextFieldFloorLevel() - NumberFormatException");
						return false;
						}
				}
			});
		}
		return textFieldToHID;
	}


//SET METHODS START	

	private JTextField setTextFieldDelay() 		{return textFieldDelay;}
	private JTextField setTextFieldFromHID() 	{return textFieldFromHID;}
	private JTextField setTextFieldToHID() 		{return textFieldToHID;}
	private JSpinner setSpinnerListedTies()		{return spinnerListedTies;}
	private JPanel setColorWell()				{return colorWell;}
//SET METHODS END	
	
	public static SurfaceConnector showBox(JPanel frame) throws NumberFormatException, NegativeNumberException, ZeroArgumentException, NullPointerException, FromToException{
		DialogPropertiesSurfaceConnector dialog = new DialogPropertiesSurfaceConnector(frame);
		
		SurfaceConnector sc = new SurfaceConnector(id, delay, fromHID, toHID, color);
				id = 			sc.getSurfaceID();
				delay = 		Integer.parseInt(dialog.getTextFieldDelay().getText());
				fromHID = 		Integer.parseInt(dialog.getTextFieldFromHID().getText());
				toHID = 		Integer.parseInt(dialog.getTextFieldToHID().getText());
				color = 		dialog.getColorWell().getBackground();
		JTextField [] listedTies = listOfTies;
		return showBox(frame, sc, listedTies);
	}

	public static SurfaceConnector showBox(JPanel canvasPanel, SurfaceConnector sc, JTextField [] listedTies) throws ZeroArgumentException, NegativeNumberException, NullPointerException, FromToException {
		DialogPropertiesSurfaceConnector dialog = new DialogPropertiesSurfaceConnector(canvasPanel);
		dialog.setModal(true);
		dialog.setTitle("Surface Connector #"+sc.getSurfaceID()+" Properties");
		dialog.setSpinnerListedTies().setModel(tieModel);
		dialog.getSpinnerListedTies().addChangeListener(dialog.listener);
		try {
			System.out.println(sc.toString());
			id = 		sc.getSurfaceID();
			delay = 	sc.getDelay();
			fromHID = 	sc.getFrom().getHoleID();	
			toHID = 	sc.getTo().getHoleID();	
			color = 	sc.getColor();	
			from = 		sc.getFrom();
			to = 		sc.getTo();

			listOfTies = listedTies;
			
//			listOfTies[0] = listedTies[0];
//			listOfTies[1] = listedTies[1];
//			listOfTies[2] = listedTies[2];
//			listOfTies[3] = listedTies[3];
//			listOfTies[4] = listedTies[4];
//			listOfTies[5] = listedTies[5];

			DialogPropertiesSurfaceConnector.oldSurfaceConnector = new SurfaceConnector(sc.getSurfaceID(), delay, from, to, color);				
			dialog.setTextFieldDelay().setText(Integer.toString(delay));
			dialog.setTextFieldFromHID().setText(Integer.toString(fromHID));
			dialog.setTextFieldToHID().setText(Integer.toString(toHID));
			dialog.setColorWell().setBackground(color);
			currentSurfaceConnector = oldSurfaceConnector;
		} 
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(canvasPanel,
					"One of the values inputed is not valid",
					"HolePropertiesDialog", JOptionPane.ERROR_MESSAGE);
		}
		do {
			dialog.setVisible(true);
			if (dialog.okPushed == true) {
				id = oldSurfaceConnector.getSurfaceID();
				try {
					delay = Integer.parseInt(dialog.getTextFieldDelay().getText()); 
					dialog.getTextFieldDelay().setForeground(Color.black);			
				} 
				catch (NumberFormatException e) {
					dialog.getTextFieldDelay().setForeground(Color.red);
					dialog.badValue = true;
					System.out.println("Delay = Bad Value(s) - showBox() method - NumberFormatException");					
				}

				try {
					fromHID = Integer.parseInt(dialog.getTextFieldFromHID().getText());
					dialog.getTextFieldFromHID().setForeground(Color.black);
				} 
				catch (NumberFormatException e) {
					dialog.getTextFieldFromHID().setForeground(Color.red);
					dialog.badValue = true;
					System.out.println("From HID = Bad Value(s) - showBox() method - NumberFormatException");					
				}
				try {
					toHID = Integer.parseInt(dialog.getTextFieldToHID().getText());			
					dialog.getTextFieldToHID().setForeground(Color.black);			
				} 
				catch (NumberFormatException e) {
					dialog.getTextFieldToHID().setForeground(Color.red);
					dialog.badValue = true;
					System.out.println("From HID = Bad Value(s) - showBox() method - NumberFormatException");					
				}
				try {
					color = dialog.getColorWell().getBackground();
				}
				catch (IllegalArgumentException e) {
					dialog.getColorWell().setBackground(Color.BLACK);
				}
				if (dialog.badValue) continue;
				if (dialog.okPushed == true  && !(dialog.badValue)) {
					return
							currentSurfaceConnector = new SurfaceConnector(sc.getSurfaceID(), delay, fromHID, toHID, color);	
				}
				else {
					dialog.setVisible(true);
				}
				if (dialog.cancelPushed == true){
					System.out.println("Cancel button pushed");
					dialog.setVisible(false);

					return
							null;
				}
			}
		}	
		while(dialog.badValue);
		
		return 
				currentSurfaceConnector = new SurfaceConnector(sc.getSurfaceID(), delay, fromHID, toHID, color);
	}	


	public static void main(String[] args) throws IllegalArgumentException, NegativeNumberException, ZeroArgumentException, NullPointerException, FromToException {
		showBox(null);
		System.out.println(showBox(null));
		System.exit(0);
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			panel.add(getCancel());
			panel.add(getOK());
		}
		return panel;
	}
	private JButton getOK() {

		if (ok == null) {
			ok = new JButton();
			ok.setText("OK");
			ok.setHorizontalAlignment(SwingConstants.RIGHT);
			ok.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					okPushed = true;
					cancelPushed = false;
					if(badValue) {
						setVisible(true);
					}
					else
						setVisible(false);
				}
			});
		}
		return ok;
	}

	private JButton getCancel() {	
		if (cancel == null) {
			cancel = new JButton();
			cancel.setHorizontalAlignment(SwingConstants.RIGHT);
			cancel.setText("Cancel");
			cancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					okPushed = false;
					cancelPushed = true;
					setVisible(false);
				}
			});
		}
		return cancel;
	}
	
	private JLabel getLabelDelay() {
		if (labelDelay == null) {
			labelDelay = new JLabel("Delay");
			labelDelay.setEnabled(false);
		}
		return labelDelay;
	}

	private JLabel getLabelFromID() {
		if (labelFromID == null) {
			labelFromID = new JLabel("From Hole Id");
		}
		return labelFromID;
	}
	private JLabel getLabelToID() {
		if (labelToID == null) {
			labelToID = new JLabel("To Hole Id");
		}
		return labelToID;
	}
	private JPanel getColorWell() {
		if (colorWell == null) {
			colorWell = new JPanel();
			colorWell.setBackground(Color.BLACK);
			colorWell.setPreferredSize(new Dimension(40, 30));	
			colorWell.setEnabled(false);
			colorWell.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPropertiesSurfaceConnector.this , "Change Current Colour", getColorWell().getBackground());
					if (currentColor != null) {
						getColorWell().setBackground(currentColor);
					}
				}
				public void mousePressed(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPropertiesSurfaceConnector.this , "Change Current Colour", getColorWell().getBackground());
					if (currentColor != null) {
						getColorWell().setBackground(currentColor);
					}
				}
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub	
				}
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub	
				}
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub	
				}
			});
		}
		return colorWell;
	}
	private JLabel getLabelColor() {
		if (labelColor == null) {
			labelColor = new JLabel("Colour");
			labelColor.setEnabled(false);
		}
		return labelColor;
	}
	private JSpinner getSpinnerListedTies() {
		if (spinnerListedTies == null) {
			spinnerListedTies = new JSpinner();
			spinnerListedTies.setModel(tieModel);
			spinnerListedTies.setPreferredSize(new Dimension(60, 22));
			
		}
		return spinnerListedTies;
	}

    ChangeListener listener = new ChangeListener() {
    	public void stateChanged(ChangeEvent e) {
    		String value = getSpinnerListedTies().getValue().toString();
    		
    		if(value == ties[0]) {
    			getColorWell().setBackground(listOfTies[0].getBackground());
    			getTextFieldDelay().setText(listOfTies[0].getText());
    		}
    		else if(value == ties[1]) {
    			getColorWell().setBackground(listOfTies[1].getBackground());
    			getTextFieldDelay().setText(listOfTies[1].getText());
    		}
    		else if(value == ties[2]) {
    			getColorWell().setBackground(listOfTies[2].getBackground());
    			getTextFieldDelay().setText(listOfTies[2].getText());
    		}
    		else if(value == ties[3]) {
    			getColorWell().setBackground(listOfTies[3].getBackground());
    			getTextFieldDelay().setText(listOfTies[3].getText());
    		}
    		else if(value == ties[4]) {
    			getColorWell().setBackground(listOfTies[4].getBackground());
    			getTextFieldDelay().setText(listOfTies[4].getText());
    		}
    		else if(value == ties[5]) {
    			getColorWell().setBackground(listOfTies[5].getBackground());
    			getTextFieldDelay().setText(listOfTies[5].getText());
    		}
    	}
    };
	
	private JCheckBox getCheckboxSetToListedTies() {
		if (checkboxSetToListedTies == null) {
			checkboxSetToListedTies = new JCheckBox("Set to Listed Tie");
			checkboxSetToListedTies.setSelected(true);
			ChangeListener checkboxListener = new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if(checkboxSetToListedTies.isSelected()) {
						getSpinnerListedTies().setEnabled(true);
						getTextFieldDelay().setEnabled(false);
						getColorWell().setEnabled(false);
						getLabelDelay().setEnabled(false);
						getLabelColor().setEnabled(false);
					}
					else if(!checkboxSetToListedTies.isSelected()) {
						getSpinnerListedTies().setEnabled(false);
						getTextFieldDelay().setEnabled(true);
						getColorWell().setEnabled(true);
						getLabelDelay().setEnabled(true);
						getLabelColor().setEnabled(true);
					}
				}
			};
			checkboxSetToListedTies.addChangeListener(checkboxListener);

		}
		return checkboxSetToListedTies;
	}
}  //  @jve:decl-index=0:visual-constraint="10,-1"
