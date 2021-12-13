package statsProject;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class mainClass implements KeyListener, ActionListener {
	JFrame frame;
	JTextField[] textArray;
	JPanel panel;
	JLabel enterInputText;
	JButton selectThree;
	JButton selectFour;
	JButton selectSix;
	JButton selectAll;
	JButton start;
	JButton Enter;
	JLabel values;
	JLabel randomD;
	JLabel guessD;
	int numRight = 0;
	JLabel numLabel;
	Timer t = new Timer(1000 / 3, this);
	Timer z;
	int current = 1;
	int currentState = 0;
	int currentScreen = -1;
	int[] random;
	int testSize;

	public static void main(String[] args) {
		new mainClass().start();
	}

	void start() {
		numRight = 0;
		current = 1;
		currentState = 0;
		currentScreen = -1;
		random = setRandom();
		frame = new JFrame();
		frame.setVisible(true);
		panel = new JPanel();
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t.start();
	}

	public void selectTest() {
		panel.removeAll();
		frame.remove(panel);
		selectThree = new JButton();
		selectThree.addActionListener(this);
		selectThree.setText("Groups of 3");
		panel.add(selectThree);
		selectFour = new JButton();
		selectFour.addActionListener(this);
		selectFour.setText("Groups of 4");
		panel.add(selectFour);
		selectSix = new JButton();
		selectSix.addActionListener(this);
		selectSix.setText("Groups of 6");
		panel.add(selectSix);
		selectAll = new JButton();
		selectAll.addActionListener(this);
		selectAll.setText("Groups of 12");
		panel.add(selectAll);
		frame.add(panel);
		frame.setSize(500, 150);
	}

	public void conductExperiment(int testSize, int screen) {
		this.testSize = testSize;
		Font font = new Font("Arial", Font.BOLD, 40);
		if (screen == -1) {
			panel.removeAll();
			frame.remove(panel);
			start = new JButton();
			panel.add(start);
			start.addActionListener(this);
			start.setText("START");
			frame.add(panel);
			frame.pack();
			frame.setSize(500, 150);
		} else {
			if (testSize * screen >= 12) {
				z.stop();
				currentState++;
			} else {
				if (screen == 0) {
					panel.removeAll();
					frame.remove(panel);
					z = new Timer(12000 * testSize / 12, this);
					z.start();
					values = new JLabel();
					values.setFont(font);
					for (int i = 0; i < testSize; i++) {
						values.setText(values.getText() + random[i]);
					}
					panel.add(values);
					frame.add(panel);
					frame.pack();
					frame.setSize(500, 150);
				} else {
					values.setText("");
					values.setFont(font);
					for (int i = testSize * screen; i < testSize * screen + testSize; i++) {
						values.setText(values.getText() + random[i]);
					}
				}
			}
		}
	}

	public int[] setRandom() {
		int[] random = new int[12];
		for (int i = 0; i < 12; i++) {
			int x = new Random().nextInt(10);
			random[i] = x;
		}
		for (int i = 0; i < 12; i++) {
		}
		return random;
	}

	public void createInput() {
		panel.removeAll();
		frame.remove(panel);
		enterInputText = new JLabel();
		enterInputText.setText(
				"                               Enter as many values as you can (order matters).                               ");
		panel.add(enterInputText);
		textArray = new JTextField[12];
		for (int i = 0; i < textArray.length; i++) {
			textArray[i] = new JTextField();
			textArray[i].setFocusable(true);
			textArray[i].setVisible(true);
			textArray[i].setColumns(1);
			textArray[i].addKeyListener(this);
			panel.add(textArray[i]);
		}
		Enter = new JButton();
		Enter.addActionListener(this);
		Enter.setText("ENTER");
		panel.add(Enter);
		frame.add(panel);
		textArray[0].grabFocus();
		frame.pack();
		frame.setSize(500, 150);
	}

	public void showResults() {
		System.out.println("after");
		panel.removeAll();
		frame.remove(panel);
		randomD = new JLabel();
		randomD.setText("Actual List: ");
		for (int i = 0; i < 12; i++) {
			randomD.setText(randomD.getText() + " " + random[i]);
		}
		randomD.setText("                                      " + randomD.getText()
				+ "                                      ");
		guessD = new JLabel();
		guessD.setText("Your Guess: ");
		for (int i = 0; i < 12; i++) {
			if (textArray[i].getText().length() == 0) {
				textArray[i].setText("10");
				guessD.setText(guessD.getText() + " []");
			} else {
				guessD.setText(guessD.getText() + " " + textArray[i].getText());
			}
		}
		for (int i = 0; i < 12; i++) {
			if (random[i] == Integer.parseInt(textArray[i].getText())) {
				numRight++;
			}
		}
		numLabel = new JLabel();
		numLabel.setText(" Number Correct: " + numRight);
		panel.add(randomD);
		panel.add(guessD);
		panel.add(numLabel);
		frame.add(panel);
		frame.pack();
		frame.setSize(500, 150);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if (currentState == 4) {
			if (e.getKeyChar() != 8) {
				System.out.println(e.getKeyChar());
				if (textArray[getCurrentFocus(textArray)].getText().length() >= 1) {
					if (current < 12) {
						textArray[current].grabFocus();
						textArray[current].setText(e.getKeyChar() + "");
						
						if (current < 11) {
							current++;
						} else {
							current = 0;
						}
					}
					e.consume();
				}
				
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public int getCurrentFocus(JTextField[] textArray) {
		for (int i = 0; i < textArray.length; i++) {
			if (textArray[i].isFocusOwner()) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start && currentScreen == -1) {
			currentScreen = 0;
			conductExperiment(testSize, currentScreen);
		}
		// TODO Auto-generated method stub
		if (currentState == 0) {
			selectTest();
			currentState++;
		}
		if (currentState == 3) {
			createInput();
			currentState++;
		}
		if (currentState == 5) {
			showResults();
			currentState++;
		}
		if (currentState == 7) {
			panel.removeAll();
			frame.removeAll();
			start();
		}
		if (e.getSource() == selectThree) {
			conductExperiment(3, currentScreen);
			currentState++;
		}
		if (e.getSource() == selectFour) {
			conductExperiment(4, currentScreen);
			currentState++;
		}
		if (e.getSource() == selectSix) {
			conductExperiment(6, currentScreen);
			currentState++;
		}
		if (e.getSource() == selectAll) {
			conductExperiment(12, currentScreen);
			currentState++;
		}
		if (e.getSource() == z) {
			currentScreen++;
			conductExperiment(testSize, currentScreen);
		}
		if (e.getSource() == Enter) {
			currentState++;
		}
	}
}
