package programmingweek12;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Action implements Runnable{//implements runnable for use of threads
	
	private JPanel panel;
	private TokenCategorizer tc;
	private Display dp;
	
	public Action(JPanel panel, TokenCategorizer tc, Display dp ){
		this.panel=panel;
		this.tc=tc;
		this.dp=dp;
	}
	
	public void run(){//run thread
		JFileChooser chooser = new JFileChooser();//create file chooser
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"", "csv", "txt");//filter the files allowed
		chooser.setFileFilter(filter);//set the filter
		int returnVal = chooser.showOpenDialog(panel);//shows the open file chooser dialogue
		if (returnVal == JFileChooser.APPROVE_OPTION) {//if approved
			System.out.println("\nYou chose to open this file: "
					+ chooser.getSelectedFile().getName());

			try {
                tc.readFile(chooser.getSelectedFile().getAbsolutePath());//passes the absolute filepath of the
                                                                         //+ selected file to open
				dp.updateTable();//call the update table method

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
