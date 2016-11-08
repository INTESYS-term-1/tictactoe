import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import java.awt.GridLayout;
import java.awt.Point;

import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;

public class Gui extends JFrame implements ActionListener {

	GuiCell[][] guiCells;

	public static int BOARDROW = 4;
	public static int BOARDCOLUMN = 8;
	JPanel leftPanel = new JPanel();
	JPanel rightPanel = new JPanel();

	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(leftPanel),
			new JScrollPane(rightPanel));

	DefaultTableModel model = new DefaultTableModel() {

		@Override
		public boolean isCellEditable(int row, int column) {
			// Only the third column
			return false;
		}
	};

	JTable table;
	private final JLabel lblBattlesheepDashboard = new JLabel("Battlesheep dashboard:");
	private final JLabel lblTotalSheepPerPlayer = new JLabel("Total sheep per player:");
	private final JLabel totalSheep = new JLabel("n");
	private final JLabel lblPlayerDashboard = new JLabel("Player dashboard");
	private final JLabel lblSheepAtHandLabel = new JLabel("Sheep at hand:");
	private final JLabel lblSheepAtHand = new JLabel("n");
	private final JLabel lblFromXCoordinate = new JLabel("From x coordinate:");
	private final JLabel lblFromYCoordinate = new JLabel("From y coordinate:");
	private final JLabel lblxCoordinate = new JLabel("x");
	private final JLabel lblyCoordinate = new JLabel("y");
	private final JButton btnSubmit = new JButton("Done");

	int numberOfSheepsPerPlayer = 0;
	Boolean isHolding = false;
	int holding = 0;

	int player = -1;
	int free = 0;
	int ai = 1;

	public Gui() {
		rightPanel.setLayout(new MigLayout("", "[185.00][33.00]", "[][][][][][][][][][][][][][][]"));
		lblBattlesheepDashboard.setFont(new Font("Tahoma", Font.BOLD, 13));

		rightPanel.add(lblBattlesheepDashboard, "cell 0 0");

		rightPanel.add(lblTotalSheepPerPlayer, "cell 0 1");

		rightPanel.add(totalSheep, "cell 1 1,aligny baseline");
		lblPlayerDashboard.setFont(new Font("Tahoma", Font.BOLD, 12));

		rightPanel.add(lblPlayerDashboard, "cell 0 3");

		rightPanel.add(lblSheepAtHandLabel, "cell 0 4");

		rightPanel.add(lblSheepAtHand, "cell 1 4,aligny baseline");

		rightPanel.add(lblFromXCoordinate, "cell 0 5");

		rightPanel.add(lblxCoordinate, "cell 1 5,alignx left");

		rightPanel.add(lblFromYCoordinate, "cell 0 6");

		rightPanel.add(lblyCoordinate, "cell 1 6");
		btnSubmit.setVerticalAlignment(SwingConstants.BOTTOM);

		rightPanel.add(btnSubmit, "cell 0 14,growx");

		this.setSize(1000, 700);

		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		this.setTitle("Battlesheep (Alejandria, Cardano, Matias)");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);

		getContentPane().add(splitPane);

		splitPane.setSize(1000, 650);
		splitPane.setVisible(true);
		splitPane.setDividerLocation(730);

		initializeTable();
		initializeTableLooks();

		// ask number sheep per player store it in numberOfSheepsPerPlayer
		numberOfSheepsPerPlayer = Integer
				.parseInt(JOptionPane.showInputDialog(this, "Total number of sheeps per player", "Welcome", 2));
		totalSheep.setText(Integer.toString(numberOfSheepsPerPlayer));

		// scan initial board
		scanBoard();

		// ask where player wants to put all his sheep (where to put stack)
		initializePlayerSheep();

		leftPanel.add(table);

		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {

					int row = table.getSelectedRow();
					int column = table.getSelectedColumn();

					if (guiCells[row][column].getOwner() == player && isHolding == false) {
						holding = Integer.parseInt(
								JOptionPane.showInputDialog("How many sheeps would you like to get from here?", "0"));

						if (Integer.parseInt((String) model.getValueAt(row, column)) - holding > 0 && holding > -1) {
							model.setValueAt(
									Integer.toString(
											Integer.parseInt((String) model.getValueAt(row, column)) - holding),
									row, column);

							isHolding = true;

							lblxCoordinate.setText(Integer.toString(row));
							lblyCoordinate.setText(Integer.toString(column));
							lblSheepAtHand.setText(Integer.toString(holding));

						} else {
							isHolding = false; // false parin namansya tho
							holding = 0;
							JOptionPane.showInputDialog("Please select a valid value next time.",
									"Press OK and try again");

						}

					}

					// pag place ngholding sheep
					if (guiCells[row][column].getOwner() == free && isHolding == true) {

						int prevX = Integer.parseInt(lblxCoordinate.getText());
						int prevY = Integer.parseInt(lblyCoordinate.getText());

						// 1. left diagonal up 2. left diag down 3. right diag
						// up 4. rright diag down 5. horitzontal
						if (prevX - row == prevY - column || row - prevX == prevY - column
								|| prevX - row == column - prevY || row - prevX == column - prevY || prevX == row) {
							model.setValueAt(Integer.toString(holding), row, column);
							guiCells[row][column] = new GuiCell(row, column, holding, player);

							isHolding = false;
							holding = 0;

							lblxCoordinate.setText("none");
							lblyCoordinate.setText("none");
							lblSheepAtHand.setText("none");
						}
					}

					System.out.println(row);
					System.out.println(column);
				}
			}
		});

	}

	public void initializePlayerSheep() {
		int initialX = Integer.parseInt(
				JOptionPane.showInputDialog(this, "X coordinate:", "Where do you want to put all your sheep?", 2));
		int initialY = Integer.parseInt(
				JOptionPane.showInputDialog(this, "Y coordinate:", "Where do you want to put all your sheep?", 2));

		model.setValueAt(Integer.toString(numberOfSheepsPerPlayer), initialX, initialY);

		guiCells[initialX][initialY] = new GuiCell(initialX, initialY, numberOfSheepsPerPlayer, player);

		// printGuiCells();
	}

	public void scanBoard() {
		guiCells = new GuiCell[BOARDROW * 2][BOARDCOLUMN];

		for (int i = 0; i < BOARDROW * 2; i++) {
			for (int j = 0; j < BOARDCOLUMN; j++) {
				if (!table.getValueAt(i, j).toString().equals("0")) {
					guiCells[i][j] = new GuiCell(i, j, -99, -99, false);
				} else if (table.getValueAt(j, i).toString().equals("0")) {
					guiCells[i][j] = new GuiCell(i, j, 0, 0, true);
				}
			}
		}

		// printGuiCells();

	}

	public void printGuiCells() {
		for (int i = 0; i < BOARDROW * 2; i++) {
			for (int j = 0; j < BOARDCOLUMN; j++) {
				guiCells[i][j].print();
			}
		}
	}

	public void initializeTableLooks() {
		table.setRowHeight(45);
		TableColumn column = null;

		for (int i = 0; i < 8; i++) {
			column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(45);
		}
		for (int i = 0; i < 8; i++) {
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

	}

	public void initializeTable() {
		// <----><----><----><---->---------table parts

		for (int i = 0; i < BOARDCOLUMN; i++) {
			model.addColumn("*");
		}

		for (int j = 0; j < BOARDROW * 2; j++) {
			if (j % 2 == 0)
				model.addRow(new Object[] { "0", "<---->", "0", "<---->", "0", "<---->", "0", "|||||" });
			else
				model.addRow(new Object[] { "|||||", "0", "<---->", "0", "<---->", "0", "<---->", "0" });
		}

		// center all data in table

		table = new JTable(model);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
