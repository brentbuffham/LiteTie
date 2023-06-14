/**
 * 
 */
package litetie.view.dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import java.awt.Font;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

/**
 * @author brentbuffham
 *
 */
public class SeparatedValuesImportDialog  extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dimension dim30 = new Dimension(30,25);
	private Dimension dim60 = new Dimension(60,25);
	private JTable table;
	private final Font DEFAULTFONT = new Font("Dialog", Font.PLAIN, 12);
	
	public SeparatedValuesImportDialog(JFrame frame){
		setTitle("Separated Value File Import");

		JPanel panel = new JPanel();
		panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnNewButton.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(btnNewButton);
		
		JButton btnOk = new JButton("OK");
		btnOk.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(btnOk);
		
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.NORTH);
		
		JLabel lblDelimeter = new JLabel("Delimeter");
		panel_2.add(lblDelimeter);
		lblDelimeter.setFont(DEFAULTFONT);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText(",");
		panel_2.add(textField);
		textField.setPreferredSize(dim30);
		
		
		JLabel lblCommentLines = new JLabel();
		lblCommentLines.setText("Comment Lines");
		lblCommentLines.setFont(DEFAULTFONT);
		panel_2.add(lblCommentLines);
		
		SpinnerNumberModel commentSpinModel = new SpinnerNumberModel(0,0,99,1);
		JSpinner spinner = new JSpinner(commentSpinModel);
		spinner.setPreferredSize(dim60);
		panel_2.add(spinner);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable(dataModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.add(table, BorderLayout.CENTER);
		

	}
	
    TableModel dataModel = new AbstractTableModel() {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int getColumnCount() { return 10; }
        public int getRowCount() { return 10;}
        public Object getValueAt(int row, int col) { return new Integer(row*col); }
    };
    private JTextField textField;
}
