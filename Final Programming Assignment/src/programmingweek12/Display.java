package programmingweek12;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Display extends JFrame {

	private JPanel panel = new JPanel();
	private JTable table;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane2;
	private int max;
	private int length;
	private JMenuBar menuBar = new JMenuBar();
	private String langCode;
	private String cntryCode;
	private JList<String> list;
	public String listData[] = new String[3];
	private ArrayList<Long> longList;
	private ArrayList<String> stringList;
	private ArrayList<Double> doubleList;
	private TokenCategorizer tc;

	public Display(ArrayList<Long> longList, ArrayList<Double> doubleList,
			ArrayList<String> stringList, String langCode, String cntryCode, TokenCategorizer tc)
			throws IOException {
		this.longList = longList;
		this.doubleList = doubleList;
		this.stringList = stringList;
		this.langCode = langCode;
		this.cntryCode = cntryCode;
        this.tc = tc;
	}

	public void createDisplay() {//creates the display 

		//check to see which list is the biggest, max will be the length of the columns
		if (stringList.size() > doubleList.size()
				&& stringList.size() > longList.size()) {
			max = stringList.size();
		} else if (doubleList.size() > stringList.size()
				&& doubleList.size() > longList.size()) {
			max = doubleList.size();
		} else {
			max = longList.size();
		}

		setTitle("Display Table");
		setSize(800, 400);
		setBackground(Color.white);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(new BorderLayout());
		getContentPane().add(panel);
		String columnNames[] = { "Integral Numbers", "Real Numbers",
				"Other Tokens" };//column names

		String tableElements[][] = new String[max][3];//2d array to store values of table
		for (int i = 0; i < 3; i++) {
			if (i == 0) {//if i is 0 it is on the first column
				length = longList.size();//makes the length of the first column equal to size of longlist
			}
			if (i == 1) {
				length = doubleList.size();
			}
			if (i == 2) {
				length = stringList.size();
			}
			for (int j = 0; j < length; j++) {//populates the table
				if (i == 0) {//if on column 1
					tableElements[j][i] = (longList.get(j)).toString();//fill the column with longList elements
				}
				if (i == 1) {
					tableElements[j][i] = (doubleList.get(j)).toString();
				}
				if (i == 2) {
					tableElements[j][i] = (stringList.get(j)).toString();
				}
			}
		}

		table = new JTable(tableElements, columnNames);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//cannot select more than one row

		table.addMouseListener(new MouseAdapter() {//for clicking on the table
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					DefaultListModel<String> lList = new DefaultListModel<String>();//create a default list to store
					                                                                //contents of the jlist
					JTable target = (JTable) e.getSource();
					int row = target.getSelectedRow();
					
					String array[] = new String[3];

					String line = "";
					for (int i = 0; i < 3; i++) {//gets the three values from the selected row

						if (table.getValueAt(row, i) == null) {
						} else {
							line += table.getValueAt(row, i) + " , ";
							array[i] = (String) table.getValueAt(row, i);
						}

					}

					int counter = 0;//if there is a null counter increases
					for (int i = 0; i < 3; i++) {
						if (array[i] == null)
							counter++;
					}

					if (counter == 0) {//if there are no nulls
						for (int i = 0; i < 6; i++) {

							//algorithm to determine the possible permutations of a row with no nulls
							String lines = "";
							int x = i / 2;
							int y = ((3 + i) / 2) % 3;
							int z = (5 - i) % 3;

							if (array[x] != null)
								lines += array[x] + " , ";
							if (array[y] != null)
								lines += array[y] + " , ";
							if (array[z] != null)
								lines += array[z] + " , ";

							lList.addElement(lines);//adds the lines to the list

						}
					}
					if (counter == 1) {//if there is one null
						//algorithm to determine the possible permutations of a row with one null
						for (int i = 0; i < 2; i++) {
							int x = 0;
							int y = 0;
							int z = 0;
							String lines = "";
							if (array[0] == null) {
								y = i + 1;
								z = 2 - i;
								lines += array[y] + " , ";
								lines += array[z] + " , ";
							} else if (array[1] == null) {
								x = i * 2;
								z = 2 - (i * 2);
								lines += array[x] + " , ";
								lines += array[z] + " , ";
							} else {
								x = i;
								y = 1 - i;
								lines += array[x] + " , ";
								lines += array[y] + " , ";
							}

							lList.addElement(lines);

						}
					}

					if (counter == 2) {//if there is 2 nulls there is only one possible token
						//algorithm 
						String lines = "";
						if (array[0] == null & array[1] == null) {
							lines = array[2];
						} else if (array[0] == null && array[2] == null) {
							lines = array[1];
						} else
							lines = array[0];

						lList.addElement(lines);
					}

					list.setModel(lList);//sets the model with the elements on the list
				}

			}

		});

		list = new JList<String>(listData);//creates the list
		
		list.setFixedCellHeight(30);//sets the height and width of the list
		list.setFixedCellWidth(200);
		JMenu file = new JMenu("File");//creates the menu 
		JMenuItem open = new JMenuItem("Open");
		JMenuItem quit = new JMenuItem("Quit");

		menuBar.add(file);//adds the file to the menu bar
		file.add(open);//adds open to file
		file.add(quit);//adds quit to file
		scrollPane = new JScrollPane(table);//adds a scrollpanel to the table
		scrollPane2 = new JScrollPane(list);
		panel.add(scrollPane, BorderLayout.CENTER);//adds the table and scrolling pane to the panel
		panel.add(scrollPane2, BorderLayout.EAST);
		panel.add(menuBar, BorderLayout.NORTH);//adds the menu bar to the panel
		panel.validate();

		final Display dp = this;//gets a reference of the display object
		open.addActionListener(new ActionListener() {//when the user clicks open
			@Override
			public void actionPerformed(ActionEvent e) {
				Action action = new Action(panel, tc, dp);//creates an action object
				Thread t = new Thread(action);//creates a thread and passes a reference of action through
				t.start();//starts the thread
				
				
			}
			
					
				
			
		});
		
		quit.addActionListener(new ActionListener() {//when user clicks quit

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);//quits the program
			}

		});

	}

	public void updateTable() {//method that updates the table with new values
		

		table.setSize(800, 400);
		if (stringList.size() > doubleList.size()
				&& stringList.size() > longList.size()) {
			max = stringList.size();
		} else if (doubleList.size() > stringList.size()
				&& doubleList.size() > longList.size()) {
			max = doubleList.size();
		} else {
			max = longList.size();
		}

		String tableElements[][] = new String[max][3];
		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				length = longList.size();
			}
			if (i == 1) {
				length = doubleList.size();
			}
			if (i == 2) {
				length = stringList.size();
			}
			for (int j = 0; j < length; j++) {
				if (i == 0) {
					tableElements[j][i] = (longList.get(j)).toString();
					
				}
				if (i == 1) {
					tableElements[j][i] = (doubleList.get(j)).toString();
					
				}
				if (i == 2) {
					tableElements[j][i] = (stringList.get(j)).toString();
					
				}
			}
		}
		

		String columnNames[] = { "Integral Numbers", "Real Numbers",
				"Other Tokens" };
		  DefaultTableModel dtm = new DefaultTableModel(tableElements, columnNames);//passes the new values to the default table model

	    table.setModel(dtm);//sets this model and refreshes the table
			
			
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					DefaultListModel<String> lList = new DefaultListModel<String>();
					
					JTable target = (JTable) e.getSource();
					int row = target.getSelectedRow();
					
					String array[] = new String[3];

					String line = "";
					for (int i = 0; i < 3; i++) {

						if (table.getValueAt(row, i) == null) {
						} else {
							line += table.getValueAt(row, i) + " , ";
							array[i] = (String) table.getValueAt(row, i);
						}

					}

					int counter = 0;
					for (int i = 0; i < 3; i++) {
						if (array[i] == null)
							counter++;
					}

					if (counter == 0) {
						for (int i = 0; i < 6; i++) {

							String lines = "";
							int x = i / 2;
							int y = ((3 + i) / 2) % 3;
							int z = (5 - i) % 3;

							if (array[x] != null)
								lines += array[x] + " , ";
							if (array[y] != null)
								lines += array[y] + " , ";
							if (array[z] != null)
								lines += array[z] + " , ";

							lList.addElement(lines);

						}
					}
					if (counter == 1) {
						for (int i = 0; i < 2; i++) {
							int x = 0;
							int y = 0;
							int z = 0;
							String lines = "";
							if (array[0] == null) {
								y = i + 1;
								z = 2 - i;
								lines += array[y] + " , ";
								lines += array[z] + " , ";
							} else if (array[1] == null) {
								x = i * 2;
								z = 2 - (i * 2);
								lines += array[x] + " , ";
								lines += array[z] + " , ";
							} else {
								x = i;
								y = 1 - i;
								lines += array[x] + " , ";
								lines += array[y] + " , ";
							}

							lList.addElement(lines);

						}
					}

					if (counter == 2) {
						String lines = "";
						if (array[0] == null & array[1] == null) {
							lines = array[2];
						} else if (array[0] == null && array[2] == null) {
							lines = array[1];
						} else
							lines = array[0];

						lList.addElement(lines);
					}

					list.setModel(lList);
				}

			}

		});

	}

	public String getLangCode() {
		return langCode;
	}

	public String getCntryCode() {
		return cntryCode;
	}

	public JTable getTable() {
		return table;
	}

}
