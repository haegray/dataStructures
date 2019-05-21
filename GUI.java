import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *  A GUI using the Table class to provide a
 *  color combination demo.
 *  
 *  @author Raven Russell
 */
public class GUI extends JFrame {
	/**
	 *  The size (in pixels) of the color squares.
	 */
	private static int SIZE = 50;
	
	/**
	 *  A table which combines two integer values into a
	 *  color with a color combiner.
	 */
	private Table<Integer,Integer,Color,ColorComb> table;
	
	/**
	 *  The layout manager for the main GUI window
	 */
	private GridLayout grid;
	
	/**
	 *  A main method to kick everything off.
	 *  @param args not used
	 */
	public static void main(String[] args) {
		 (new GUI()).display();
	}
	
	/**
	 *  Makes a new table with an Red-Green combiner
	 *  0 in the first row and first column,
	 *  and 255 in the second row and second column.
	 */
	public GUI() {
		table = new Table<>(new ColorRG());
		table.addRow(0,0);
		table.addRow(1,255);
		table.addCol(0,0);
		table.addCol(1,255);
	}
	
	/**
	 *  Prompts the user (repeatedly if necessary) to enter
	 *  a color between 0 and 255.
	 *  @param current the current color values
	 *  @return the new color value
	 */
	private int getColorFromPrompt(int current) {
		while(true) {
			try {
				int color = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter a number from 0-255",current));
				if(color > -1 && color <= 255) return color;
			}
			catch(NumberFormatException e2) { }
		}
	}
	
	/**
	 *  Styles buttons to look nice(r).
	 *  @param b the button to style
	 */
	private void styleButton(JButton b) {
		b.setPreferredSize(new Dimension(SIZE/2, SIZE/2));
		b.setBackground(new Color(240,240,240));
		b.setMargin(new Insets(0,0,0,0));
		b.setFont(new Font("Arial", Font.PLAIN, 10));
	}
	
	/**
	 *  Prompts the user for the color combiner they would like to use
	 *  and applies that combiner to the table they have setup.
	 */
	private void changeTableType() {
		String[] possibilities = {"Red-Green", "Red-Blue", "Green-Blue"};
		String s = (String)JOptionPane.showInputDialog(
					this,
					null,
					"Select Colors",
					JOptionPane.PLAIN_MESSAGE,
					null,
					possibilities,
					"Red-Green");
		
		switch(s) {
			default:
			case "Red-Green":	table.setOp(new ColorRG()); break;
			case "Red-Blue":	table.setOp(new ColorRB()); break;
			case "Green-Blue":	table.setOp(new ColorGB()); break;
		}
	}
	
	/**
	 *  Displays the GUI.
	 */
	public void display() {
		//clear out anything that was there and make a new layout
		this.getContentPane().removeAll();
		this.grid = new GridLayout(table.getSizeRow()+2,table.getSizeCol()+2);
		this.setLayout(grid);
		
		//for each header and cell, draw the appropriate thing...
		for(int y = 0; y < this.grid.getRows(); y++) {
			for(int x = 0; x < this.grid.getColumns(); x++) {
				final int currentX = x-1;
				final int currentY = y-1;
				
				//top corner
				if(y == 0 && x == 0) {
					JButton rgbButton = new JButton("RGB");
					rgbButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							changeTableType();
							display();
						}

					});
					styleButton(rgbButton);

					this.add(rgbButton);
				}
				//edges
				else if(y == this.grid.getRows()-1 || x == this.grid.getColumns()-1) {
					//last add button at end of top row
					if(y == 0 && x == this.grid.getColumns()-1) {
						if(x == this.grid.getColumns()-1) {
							JButton addButton = new JButton("+");
							addButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									table.addCol(currentX,getColorFromPrompt(255));
									display();
								}

							});
							styleButton(addButton);

							this.add(addButton);
						}
					}
					//last add button at end first column
					else if (x == 0 && y == this.grid.getRows()-1) {
						//last "add" button at end
						JButton addButton = new JButton("+");
						addButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								table.addRow(currentY,getColorFromPrompt(255));
								display();
							}
						});
						styleButton(addButton);
						
						this.add(addButton);
					}
					//blanks
					else {
						JLabel l = new JLabel(" ");
						l.setBackground(Color.WHITE);
						this.add(l);
					}
				}
				//top row of buttons
				else if(y == 0) {
					//insert, change, and delete
					JButton insertButton = new JButton("+");
					insertButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							table.addCol(currentX,getColorFromPrompt(255));
							display();
						}
					});
					styleButton(insertButton);

					JButton changeButton = new JButton(""+table.getColHead(currentX));
					changeButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							//table.setToColHead(currentX,getColorFromPrompt(table.getColHead(currentX)));
							table.setCol(currentX,getColorFromPrompt(table.getColHead(currentX)));
							display();
						}
					});
					styleButton(changeButton);

					JButton deleteButton = new JButton("x");
					deleteButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							table.removeCol(currentX);
							display();
						}
					});
					styleButton(deleteButton);

					GridBagLayout g = new GridBagLayout();
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.HORIZONTAL;
					JPanel p = new JPanel(g);
					
					c.gridx = 0;
					c.weightx = 0.5;
					p.add(insertButton, c);
					
					c.gridx = 1;
					p.add(deleteButton, c);
					if(table.getSizeCol() == 1)
						deleteButton.setEnabled(false);
					
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridwidth = 3;
					c.weightx = 1;
					c.gridx = 0;
					c.gridy = 1;
					p.add(changeButton, c);
					
					this.add(p);
				}
				//first column of buttons
				else if(x == 0) {
					//insert, change, and delete
					JButton insertButton = new JButton("+");
					insertButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							table.addRow(currentY,getColorFromPrompt(255));
							display();
						}
					});
					styleButton(insertButton);

					JButton changeButton = new JButton(""+table.getRowHead(currentY));
					changeButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							//table.setToRowHead(currentY,getColorFromPrompt(table.getRowHead(currentY)));
							table.setRow(currentY,getColorFromPrompt(table.getRowHead(currentY)));
							display();
						}
					});
					styleButton(changeButton);

					JButton deleteButton = new JButton("x");
					deleteButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							table.removeRow(currentY);
							display();
						}
					});
					styleButton(deleteButton);

					GridBagLayout g = new GridBagLayout();
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.HORIZONTAL;
					JPanel p = new JPanel(g);
					
					c.gridx = 0;
					c.weightx = 0.5;
					p.add(insertButton, c);
					
					c.gridx = 1;
					p.add(deleteButton, c);
					if(table.getSizeRow() == 1)
						deleteButton.setEnabled(false);
					
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridwidth = 3;
					c.weightx = 1;
					c.gridx = 0;
					c.gridy = 1;
					p.add(changeButton, c);
					
					this.add(p);
				}
				//normal cell
				else {
					this.add(new ColorSquare(table.getCell(currentY, currentX), SIZE));
				}
			}
		}
		
		//display the window properly
		this.setSize((table.getSizeRow()+1)*SIZE, (table.getSizeRow()+1)*SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
}

/**
 *  A class to make child classes from.
 *  @author Raven Russell
 */
abstract class ColorComb implements Combiner<Integer,Integer,Color> { }

/**
 *  A color combiner for Red-Green. First operand is green, second
 *  is red.
 *  @author Raven Russell
 */
class ColorRG extends ColorComb {
	@Override
	public Color combine(Integer operand1, Integer operand2) {
		return new Color(operand2, operand1, 0);
	}
}

/**
 *  A color combiner for Red-Blue. First operand is blue, second
 *  is red.
 *  @author Raven Russell
 */
class ColorRB extends ColorComb {
	@Override
	public Color combine(Integer operand1, Integer operand2) {
		return new Color(operand2, 0, operand1);
	}
}

/**
 *  A color combiner for Green-Blue. First operand is blue, second
 *  is greed.
 *  @author Raven Russell
 */
class ColorGB extends ColorComb {
	@Override
	public Color combine(Integer operand1, Integer operand2) {
		return new Color(0, operand2, operand1);
	}
}

/**
 *  Makes colored squares.
 *  @author Raven Russell
 */
class ColorSquare extends JPanel {
	/**
	 *  The color of the square.
	 */
	private Color c;
	
	/**
	 *  The size of the square to draw (in pixels).
	 */
	private int size;
	
	/**
	 *  Creates a new color square of a given color and
	 *  size.
	 *  @param c the chosen color
	 *  @param size the size (in pixels)
	 */
	public ColorSquare(Color c, int size) {
		this.c = c;
		this.size = size;
		this.setPreferredSize(new Dimension(this.size, this.size));
	}
	
	/**
	 *  Set the color to something else.
	 *  @param c the new color
	 */
	public void setColor(Color c) {
		this.c = c;
		this.repaint();
	}
	
	/**
	 *  Draw the panel with the given color.
	 *  @param g the graphics reference for drawing
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(c);
		g2.fillRect(0, 0, size, size);

	}
}

/**
 *  This class is not used, but kinda cool so
 *  I'm leaving it here. This makes a JButton
 *  with rotated text.
 *  @author Raven Russell
 */
class RotatedJButton extends JButton {
	/**
	 *  Makes a JButton with a given label.
	 *  @param label the text on the JButton
	 */
	public RotatedJButton(String label) {
		super(label);
	}
	
	/**
	 *  This rotates the drawing canvas, draws on
	 *  the canvas, then rotates it back.
	 *  @param g the graphics reference for drawing
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int w = getWidth() / 2;
        int h = getHeight() / 2;
        g2.rotate(-Math.PI / 2, w, h);
		super.paintComponent(g2);
        g2.rotate(Math.PI / 2, w, h);
	}
}