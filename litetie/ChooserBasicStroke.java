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

public class ChooserBasicStroke extends JDialog {

	SpinnerNumberModel lineSpinnerModel = new SpinnerNumberModel(1, 0, 15, 1);
	SpinnerNumberModel dashSpinnerModel = new SpinnerNumberModel(0, 0, 50, 1);
	SpinnerNumberModel spaceSpinnerModel = new SpinnerNumberModel(0, 0, 50, 1);
	
	private final JPanel contentPanel = new JPanel();
	private JSpinner spinnerLineWidth;
	private JSpinner spinnerDashLength;
	private JSpinner spinnerSpaceLength;
	private JPanel panelPreview;
	private JCheckBox checkboxLock;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ChooserBasicStroke dialog = new ChooserBasicStroke();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ChooserBasicStroke() {
		setTitle("Basic Stroke Chooser");
		setBounds(100, 100, 320, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblLineWidth = new JLabel("Line Width");
			GridBagConstraints gbc_lblLineWidth = new GridBagConstraints();
			gbc_lblLineWidth.anchor = GridBagConstraints.EAST;
			gbc_lblLineWidth.insets = new Insets(0, 12, 5, 5);
			gbc_lblLineWidth.gridx = 0;
			gbc_lblLineWidth.gridy = 0;
			contentPanel.add(lblLineWidth, gbc_lblLineWidth);
		}
		{
			spinnerLineWidth = new JSpinner();
			spinnerLineWidth.setPreferredSize(new Dimension(50, 20));
			GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
			gbc_spinner_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinner_1.insets = new Insets(0, 0, 5, 5);
			gbc_spinner_1.gridx = 1;
			gbc_spinner_1.gridy = 0;
			contentPanel.add(spinnerLineWidth, gbc_spinner_1);
		}
		{
			JLabel lblPx = new JLabel("px");
			GridBagConstraints gbc_lblPx = new GridBagConstraints();
			gbc_lblPx.insets = new Insets(0, 0, 5, 5);
			gbc_lblPx.gridx = 2;
			gbc_lblPx.gridy = 0;
			contentPanel.add(lblPx, gbc_lblPx);
		}
		{
			JLabel lblPreview = new JLabel("preview");
			lblPreview.setFont(new Font("Dialog", Font.ITALIC, 10));
			GridBagConstraints gbc_lblPreview = new GridBagConstraints();
			gbc_lblPreview.insets = new Insets(0, 0, 5, 0);
			gbc_lblPreview.gridx = 4;
			gbc_lblPreview.gridy = 0;
			contentPanel.add(lblPreview, gbc_lblPreview);
		}
		{
			panelPreview = new JPanel();
			panelPreview.setBackground(Color.WHITE);
			panelPreview.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panelPreview.setPreferredSize(new Dimension(70, 50));
			panelPreview.setMinimumSize(new Dimension(70, 50));
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.gridheight = 2;
			gbc_panel.gridx = 4;
			gbc_panel.gridy = 1;
			contentPanel.add(panelPreview, gbc_panel);
		}
		{
			JLabel lblDashLength = new JLabel("Dash Length");
			GridBagConstraints gbc_lblDashLength = new GridBagConstraints();
			gbc_lblDashLength.anchor = GridBagConstraints.EAST;
			gbc_lblDashLength.insets = new Insets(0, 12, 5, 5);
			gbc_lblDashLength.gridx = 0;
			gbc_lblDashLength.gridy = 1;
			contentPanel.add(lblDashLength, gbc_lblDashLength);
		}
		{
			spinnerDashLength = new JSpinner();
			spinnerDashLength.setPreferredSize(new Dimension(50, 20));
			GridBagConstraints gbc_spinner_2 = new GridBagConstraints();
			gbc_spinner_2.insets = new Insets(0, 0, 5, 5);
			gbc_spinner_2.gridx = 1;
			gbc_spinner_2.gridy = 1;
			contentPanel.add(spinnerDashLength, gbc_spinner_2);
		}
		{
			JLabel lblPx_1 = new JLabel("px");
			GridBagConstraints gbc_lblPx_1 = new GridBagConstraints();
			gbc_lblPx_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblPx_1.gridx = 2;
			gbc_lblPx_1.gridy = 1;
			contentPanel.add(lblPx_1, gbc_lblPx_1);
		}
		{
			checkboxLock = new JCheckBox("lock");
			GridBagConstraints gbc_chckbxLock = new GridBagConstraints();
			gbc_chckbxLock.gridheight = 2;
			gbc_chckbxLock.insets = new Insets(0, 0, 0, 5);
			gbc_chckbxLock.gridx = 3;
			gbc_chckbxLock.gridy = 1;
			contentPanel.add(checkboxLock, gbc_chckbxLock);
		}
		{
			JLabel lblSpaceLength = new JLabel("Space Length");
			GridBagConstraints gbc_lblSpaceLength = new GridBagConstraints();
			gbc_lblSpaceLength.anchor = GridBagConstraints.EAST;
			gbc_lblSpaceLength.insets = new Insets(0, 12, 0, 5);
			gbc_lblSpaceLength.gridx = 0;
			gbc_lblSpaceLength.gridy = 2;
			contentPanel.add(lblSpaceLength, gbc_lblSpaceLength);
		}
		{
			spinnerSpaceLength = new JSpinner();
			spinnerSpaceLength.setPreferredSize(new Dimension(50, 20));
			GridBagConstraints gbc_spinner = new GridBagConstraints();
			gbc_spinner.insets = new Insets(0, 0, 0, 5);
			gbc_spinner.gridx = 1;
			gbc_spinner.gridy = 2;
			contentPanel.add(spinnerSpaceLength, gbc_spinner);
		}
		{
			JLabel lblPx_2 = new JLabel("px");
			GridBagConstraints gbc_lblPx_2 = new GridBagConstraints();
			gbc_lblPx_2.insets = new Insets(0, 0, 0, 5);
			gbc_lblPx_2.gridx = 2;
			gbc_lblPx_2.gridy = 2;
			contentPanel.add(lblPx_2, gbc_lblPx_2);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public JSpinner getLineWidthSpinner() {
		
		
		
		return spinnerLineWidth;
	}
	public JSpinner getDashLengthSpinner() {
		return spinnerDashLength;
	}
	public JSpinner getSpaceLengthSpinner() {
		return spinnerSpaceLength;
	}
	public JPanel getPreviewPanel() {
		return panelPreview;
	}
	public JCheckBox getLockCheckBox() {
		return checkboxLock;
	}
}
