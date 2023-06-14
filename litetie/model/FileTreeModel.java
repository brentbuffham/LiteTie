package litetie.model;

import javax.swing.*;  
import javax.swing.tree.TreeModel;  
import javax.swing.tree.TreePath;  
import javax.swing.event.TreeModelListener;  
import javax.swing.event.TreeModelEvent;  
import javax.swing.event.EventListenerList;  
import java.util.List;  
import java.util.ArrayList;  
import java.util.Map;  
import java.util.HashMap;  
import java.io.File;  
import java.io.Serializable;  



public class FileTreeModel  implements TreeModel, Serializable, Cloneable  
{  
	protected EventListenerList listeners;  


	private Map map;  

	private File root;  


	public FileTreeModel(File root)  
	{  
		if (!root.exists())  
			throw new IllegalArgumentException(String.valueOf(root));  

		this.root = root;  

		this.listeners = new EventListenerList();  

		this.map = new HashMap();  
	}  


	public Object getRoot()  
	{  
		return root;  
	}  

	public boolean isLeaf(Object node)  
	{  
		return !((File)node).isDirectory();  
	}  

	public int getChildCount(Object node)  
	{  
		List children = children(node);  

		if (children == null)  
			return 0;  

		return children.size();  
	}  

	public Object getChild(Object parent, int index)  
	{  
		return children(parent).get(index);  
	}  

	public int getIndexOfChild(Object parent, Object child)  
	{  
		return children(parent).indexOf(child);  
	}  


	public void valueForPathChanged(TreePath path, Object value)  
	{  
	}  


	protected List children(Object node)  
	{  
		File f = (File)node;  

		if (!f.isDirectory())  
			return null;  

		List children = (List)map.get(f);  

		if (children == null)  
		{  
			File[] c = f.listFiles();  

			if (c == null)  
				return null;  

			children = new ArrayList(c.length);  

			for (int len = c.length, i = 0; i < len; i++)  
				children.add(c[i]);  

			map.put(f, children);  
		}  

		return children;  
	}  


	public Object clone()  
	{  
		try  
		{  
			FileTreeModel clone = (FileTreeModel)super.clone();  

			clone.listeners = new EventListenerList();  

			return clone;  
		}  
		catch (CloneNotSupportedException e)  
		{  
			throw new InternalError();  
		}  
	}  

	public void addTreeModelListener(TreeModelListener l)  
	{  
		listeners.add(TreeModelListener.class, l);  
	}  

	public void removeTreeModelListener(TreeModelListener l)  
	{  
		listeners.remove(TreeModelListener.class, l);  
	}  



	public static void main(String[] args)  
	{  
		if (args.length != 1)  
		{  
			System.err.println("Usage: java FileTreeModel path");  
			System.exit(1);  
		}  

		File root = new File(args[0]);  

		if (!root.exists())  
		{  
			System.err.println(root+ ": No such file or directory");  
			System.exit(2);  
		}  

		JTree t = new JTree(new FileTreeModel(root));  

		JFrame f = new JFrame(root.toString());  

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  

		f.getContentPane().add(new JScrollPane(t));  

		f.pack();  
		f.setVisible(true);  
	}  
}  

