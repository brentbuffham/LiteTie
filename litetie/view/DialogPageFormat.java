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
import java.awt.print.PageFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class DialogPageFormat extends JDialog {

	private JPanel layoutPanel = null;
	private JButton cancel = null;

	private boolean okPushed;
	private boolean cancelPushed;

	boolean badValue = false;

	protected Component dialogOwner;

	private int orientationInt = PageFormat.LANDSCAPE;
	private JPanel panel;
	private JButton ok;
	private JRadioButton radioButtonPortrait;
	private JRadioButton radioButtonLandscape;
	private JLabel lblOrientation;

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension dialogSize = new Dimension(250, 160);
	Rectangle bounds = new Rectangle(
			(int) (screenSize.getWidth() / 2 - (dialogSize.getWidth() / 2)),
			(int) (screenSize.getHeight() / 2 - (dialogSize.getHeight() / 2)),
			(int) dialogSize.getWidth(), (int) dialogSize.getHeight());
	private JPanel panel_1;

	public DialogPageFormat(JPanel canvasPanel) {
		super();
		initialize();
	}

	private void initialize() {
		this.setResizable(false);
		this.setBounds(bounds);
		this.setTitle("Paper Orientation");
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
			gbl_layoutPanel.rowWeights = new double[] { 1.0, 1.0 };
			gbl_layoutPanel.columnWeights = new double[] { 1.0 };
			gbl_layoutPanel.rowHeights = new int[] { 0, 0 };
			layoutPanel.setLayout(gbl_layoutPanel);
			GridBagConstraints gbc_panel_1 = new GridBagConstraints();
			gbc_panel_1.fill = GridBagConstraints.BOTH;
			gbc_panel_1.insets = new Insets(10, 10, 10, 10);
			gbc_panel_1.gridx = 0;
			gbc_panel_1.gridy = 0;
			layoutPanel.add(getPanel_1(), gbc_panel_1);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.fill = GridBagConstraints.HORIZONTAL;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 1;
			layoutPanel.add(getPanel(), gbc_panel);
		}
		return layoutPanel;
	}

	public static int showBox(JPanel frame) throws IllegalArgumentException {
		DialogPageFormat dialog = new DialogPageFormat(frame);
		int i = dialog.orientationInt;
		return showBox(frame, i);
	}

	public static int showBox(JPanel canvasPanel, int pageOrientation) {
		DialogPageFormat dialog = new DialogPageFormat(canvasPanel);
		dialog.setModal(true);

		if (pageOrientation == PageFormat.PORTRAIT) {
			dialog.getRadioButtonPortrait().setSelected(true);
			dialog.getRadioButtonLandscape().setSelected(false);
		}
		if (pageOrientation == PageFormat.LANDSCAPE) {
			dialog.getRadioButtonPortrait().setSelected(false);
			dialog.getRadioButtonLandscape().setSelected(true);
		}

		do {
			dialog.setVisible(true);
			if (dialog.okPushed == true) {

				if (dialog.getRadioButtonPortrait().isSelected()) {
					pageOrientation = PageFormat.PORTRAIT;
				}
				if (dialog.getRadioButtonLandscape().isSelected()) {
					pageOrientation = PageFormat.LANDSCAPE;
				}

				if (dialog.badValue)
					continue;
				if (dialog.okPushed == true && !(dialog.badValue)) {
					return pageOrientation;
				} else {
					dialog.setVisible(true);
				}
				if (dialog.cancelPushed == true) {
					System.out.println("Cancel button pushed");
					dialog.setVisible(false);
					// return
					// (Integer) null;
				}
			}
		} while (dialog.badValue);

		// return (Integer) null;

		return pageOrientation;
	}

	public static void main(String[] args) throws IllegalArgumentException {
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
					if (badValue) {
						setVisible(true);
					} else
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

	private JRadioButton getRadioButtonPortrait() {
		if (radioButtonPortrait == null) {
			radioButtonPortrait = new JRadioButton("Portrait");
			radioButtonPortrait.setBackground(new Color(225, 225, 225));

			radioButtonPortrait
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							if (getRadioButtonPortrait().isSelected()) {
								getRadioButtonPortrait().setSelected(true);
								getRadioButtonLandscape().setSelected(false);
								;
								orientationInt = PageFormat.PORTRAIT;
							} else if (getRadioButtonLandscape().isSelected()) {
								getRadioButtonPortrait().setSelected(false);
								getRadioButtonLandscape().setSelected(true);

								orientationInt = PageFormat.LANDSCAPE;
							}

						}
					});
		}
		return radioButtonPortrait;
	}

	private JRadioButton getRadioButtonLandscape() {
		if (radioButtonLandscape == null) {
			radioButtonLandscape = new JRadioButton("Landscape");
			radioButtonLandscape.setBackground(new Color(225, 225, 225));

			radioButtonLandscape
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							if (getRadioButtonPortrait().isSelected()) {
								getRadioButtonPortrait().setSelected(false);
								getRadioButtonLandscape().setSelected(true);

								orientationInt = PageFormat.PORTRAIT;
							} else if (getRadioButtonLandscape().isSelected()) {
								getRadioButtonPortrait().setSelected(false);
								getRadioButtonLandscape().setSelected(true);

								orientationInt = PageFormat.LANDSCAPE;
							}

						}
					});
		}
		return radioButtonLandscape;
	}

	private JLabel getLblOrientation() {
		if (lblOrientation == null) {
			lblOrientation = new JLabel("Orientation");
		}
		return lblOrientation;
	}

	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
					null, null));
			panel_1.setBackground(new Color(225, 225, 225));
			GridBagLayout gbl_panel_1 = new GridBagLayout();
			gbl_panel_1.columnWidths = new int[] { 0, 0, 0 };
			gbl_panel_1.rowHeights = new int[] { 0, 0, 0 };
			gbl_panel_1.columnWeights = new double[] { 0.0, 0.0,
					Double.MIN_VALUE };
			gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
			panel_1.setLayout(gbl_panel_1);
			GridBagConstraints gbc_lblOrientation = new GridBagConstraints();
			gbc_lblOrientation.gridheight = 2;
			gbc_lblOrientation.insets = new Insets(0, 20, 5, 20);
			gbc_lblOrientation.gridx = 0;
			gbc_lblOrientation.gridy = 0;
			panel_1.add(getLblOrientation(), gbc_lblOrientation);
			GridBagConstraints gbc_radioButtonPortrait = new GridBagConstraints();
			gbc_radioButtonPortrait.anchor = GridBagConstraints.WEST;
			gbc_radioButtonPortrait.insets = new Insets(0, 0, 5, 0);
			gbc_radioButtonPortrait.gridx = 1;
			gbc_radioButtonPortrait.gridy = 0;
			panel_1.add(getRadioButtonPortrait(), gbc_radioButtonPortrait);
			GridBagConstraints gbc_radioButtonLandscape = new GridBagConstraints();
			gbc_radioButtonLandscape.anchor = GridBagConstraints.WEST;
			gbc_radioButtonLandscape.gridx = 1;
			gbc_radioButtonLandscape.gridy = 1;
			panel_1.add(getRadioButtonLandscape(), gbc_radioButtonLandscape);
		}
		return panel_1;
	}
} // @jve:decl-index=0:visual-constraint="10,-1"
