package litetie;
import java.awt.Component;
import java.awt.Container;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import litetie.model.Pattern;

@SuppressWarnings("serial")
public  class PatternTreeTRIAL extends JPanel implements TreeSelectionListener{

	private JTree treePattern;
	private double burden;
	private double spacing;
	Pattern pattern = new Pattern(burden, spacing);
	
	
	public PatternTreeTRIAL(LinkedList<Pattern> patList){
		DefaultMutableTreeNode first = new DefaultMutableTreeNode("Canvas");
		DefaultMutableTreeNode second = new DefaultMutableTreeNode("Pattern" + patList.indexOf(pattern));
		createNodes(patList, first, second);
		
		treePattern = new JTree(first);
		treePattern.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		treePattern.addTreeSelectionListener(this);
	}
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePattern.getLastSelectedPathComponent(); 
		
		if (node == null) return;
		Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            
        }
        else
             System.out.print("BLA");
        	
	}
	
	private void createNodes(LinkedList<Pattern> patList, DefaultMutableTreeNode first, DefaultMutableTreeNode second){
	
				DefaultMutableTreeNode patternNode = null;
				DefaultMutableTreeNode holeNode = null;
				patternNode = new DefaultMutableTreeNode("Pattern "
						+ patList.indexOf(pattern));
				first.add(patternNode);
				patternNode = new DefaultMutableTreeNode("Hole ID "
						+ pattern.getDummyList());
				second.add(holeNode);
	
		
		
	}


	
	
}
