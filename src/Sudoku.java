import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Font;
import java.lang.Math;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Collections;
import java.awt.Dimension;
import javax.swing.text.*;
import java.io.*;
import javax.sound.sampled.*;


// Bespoke main menu window
class MainMenu extends JFrame {
	GraphicsHandler handler; // Need a reference to this so we can use it to open other windows

	private JPanel contentPanel; // Holds mainPanel, quickStartPanel and helpPanel
	GridBagConstraints general;

	private JPanel mainPanel; // Holds startPanel and sliderPanel
	GridBagConstraints mConstraints;

	private JPanel quickStartPanel; // Contains easy/med/hard buttons
	GridBagConstraints qConstraints;

	private JPanel helpPanel;

	private JPanel startPanel;
	private JButton start;

	private JPanel sliderPanel; // Contains slider and sliderHelp
	private JSlider slider;
	private JTextPane sliderHelp;

	private JButton easy;
	private JButton medium;
	private JButton hard;

	Color pastelBlue;
	Color pastelPink;
	Color pastelYellow;
	Color deepBlue;

	// Initialise the menu
	public void createMenu(GraphicsHandler handler) {
		this.handler = handler;

		// Define background colours
		pastelBlue = GraphicsTools.makeColor(175.0, 218.0, 240.0);
		pastelPink = GraphicsTools.makeColor(255.0, 219.0, 240.0);
		pastelYellow = GraphicsTools.makeColor(252.0, 245.0, 151.0);
		deepBlue = GraphicsTools.makeColor(30.0, 57.0, 58.0);

		setTitle("Sudoku - menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close app
		setSize(750, 750);
		setFocusable(true);


		contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		general = new GridBagConstraints();
		contentPanel.setSize(750,750);
		contentPanel.setBackground(Color.WHITE);

		// mainPanel holds slider and custom start button
		mainPanel = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(700,200);
			};
		};
		mainPanel.setLayout(new GridBagLayout());
		mConstraints = new GridBagConstraints();
		mainPanel.setBackground(pastelBlue);

		// quickStartPanel holds easy/medium/hard buttons
		quickStartPanel = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(700,200);
			};
		};
		quickStartPanel.setLayout(new GridBagLayout());
		qConstraints = new GridBagConstraints();
		quickStartPanel.setBackground(pastelPink);

		// helpPanel holds instruction text
		helpPanel = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(700,200);
			};
		};
		helpPanel.setLayout(new GridBagLayout());
		helpPanel.setBackground(pastelYellow);

		add(contentPanel);

		// Arrange in order
		general.gridx = 0;
		general.gridy = 0;
		contentPanel.add(quickStartPanel, general);
		general.gridx = 0;
		general.gridy = 100;
		contentPanel.add(mainPanel, general);
		general.gridx = 0;
		general.gridy = 200;
		contentPanel.add(helpPanel, general);

		createStartButton();
		createSlider();

		createQuickStartButtons();

		createHelp();

	}

	// Add a custom JTextPane containing instructions to helpPanel
	private void createHelp() {
		JTextPane text = new JTextPane() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(650,170);
			};
		};

		text.setEditable(false);
		text.setBackground(pastelYellow);

		// Need different strings for different formatting
		String instructionsTitle = new String("Starting a Game:");
		String instructions = new String("\nTo start, adjust the difficulty using the slider and press ");
		String instructions2 = new String("Custom game");
		String instructions3 = new String("\nAlternatively, press ");
		String instructions4 = new String("Easy");
		String instructions5 = new String(", ");
		String instructions6 = new String("Medium");
		String instructions7 = new String(" or ");
		String instructions8 = new String("Hard");
		String instructions9 = new String(" to use a preset difficulty");
		String controlsTitle = new String("\nGame Controls:");
		String controls = new String("\nClick on a square and type a number to fill it\nPress ");
		String controls2 = new String("0");
		String controls3 = new String(" to erase your answer\nIf you get stuck, press ");
		String controls4 = new String("H");
		String controls5 = new String(" to fill in a hint\nIf you give up, press ");
		String controls6 = new String("S");
		String controls7 = new String(" to view the solution");

		String font = "Helvetica";
		int fontSize = 18;
		int titleFontSize = fontSize + 2;
		// Add all strings to text, with appropriate colour, bold/italic etc.
		GraphicsTools.addStyledText(text, instructionsTitle, 0, font, titleFontSize, Color.BLACK, true, false, false);
		GraphicsTools.addStyledText(text, instructions, 1, font, fontSize, deepBlue, false, false, false);
		GraphicsTools.addStyledText(text, instructions2, 2, font, fontSize, Color.BLACK, false, true, false);
		GraphicsTools.addStyledText(text, instructions3, 3, font, fontSize, deepBlue, false, false, false);
		GraphicsTools.addStyledText(text, instructions4, 4, font, fontSize, Color.BLACK, false, false, false);
		GraphicsTools.addStyledText(text, instructions5, 5, font, fontSize, deepBlue, false, false, false);
		GraphicsTools.addStyledText(text, instructions6, 6, font, fontSize, Color.BLACK, false, false, false);
		GraphicsTools.addStyledText(text, instructions7, 7, font, fontSize, deepBlue, false, false, false);
		GraphicsTools.addStyledText(text, instructions8, 8, font, fontSize, Color.BLACK, false, false, false);
		GraphicsTools.addStyledText(text, instructions9, 9, font, fontSize, deepBlue, false, false, false);
		GraphicsTools.addStyledText(text, controlsTitle, 20, font, titleFontSize, Color.BLACK, true, false, false);
		GraphicsTools.addStyledText(text, controls, 30, font, fontSize, deepBlue, false, false, false);
		GraphicsTools.addStyledText(text, controls2, 32, font, fontSize, Color.RED, true, false, false);
		GraphicsTools.addStyledText(text, controls3, 32, font, fontSize, deepBlue, false, false, false);
		GraphicsTools.addStyledText(text, controls4, 31, font, fontSize, Color.RED, true, false, false);
		GraphicsTools.addStyledText(text, controls5, 32, font, fontSize, deepBlue, false, false, false);
		GraphicsTools.addStyledText(text, controls6, 33, font, fontSize, Color.RED, true, false, false);
		GraphicsTools.addStyledText(text, controls7, 34, font, fontSize, deepBlue, false, false, false);

		helpPanel.add(text);
		helpPanel.setVisible(true);
		text.setVisible(true);
	}

	// Add custom start button to mainPanel
	private void createStartButton() {

		startPanel = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(200,100);
			};
		};
		mConstraints.gridx = 0;
		mConstraints.gridy = 200;
		mainPanel.add(startPanel, mConstraints);

		start = new JButton("start") {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(190,90);
			};
		};

		start.setFocusable(false);

		// Start a game on press
		start.setActionCommand("start");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {

				Grid g = new Grid();

				int blanks, iters;
				boolean relaxUniqueness = false;
				// Number of empty spaces for the user to fill
				blanks = 81 - slider.getValue();

				// For puzzles with many blanks, use less iterations when generating
				// Saves time, marginally increases probability of non-unique solution
				if(blanks > 40) {
					iters = 8;
				} else {
					iters = 16;
				}
				// If there are > 50 blanks, allow puzzles with multiple solutions.
				// Otherwise generation may take > 1 minute
				if(blanks > 50) {
					relaxUniqueness = true;
				}

				Generator.generatePuzzle(g, blanks, iters, relaxUniqueness);

				// Clear existing handler if it exists; prevents bugs
				handler.destroy();
				handler.init();
				handler.createButtons(g);
      }
		});
		startPanel.add(start);

		start.setFont(new Font("Helvetica", Font.ITALIC, 24));
		start.setText("Custom Game");

	}

	// Add number of clues slider and text to sliderPanel
	private void createSlider() {

		sliderPanel = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(500,100);
			};
		};
		mConstraints.gridx = 200;
		mConstraints.gridy = 200;
		mainPanel.add(sliderPanel, mConstraints);

		slider = new JSlider(20, 80) {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(500,50);
			};
		};
		slider.setFont(new Font("Helvetica", Font.BOLD, 16));
		slider.setMinorTickSpacing(1);
		slider.setMajorTickSpacing(5);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setValue(35);

		slider.setFocusable(false);

		sliderPanel.add(slider);

		sliderHelp = new JTextPane() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(500,50);
			};
		};
		sliderHelp.setEditable(false);
		sliderHelp.setBackground(pastelBlue);

		String helpText1 = new String("Number of clues");
		String helpText2 = new String("\n(Choose more clues for an easier puzzle)");

		String font = "Helvetica";
		int fontSize = 16;
		GraphicsTools.addStyledText(sliderHelp, helpText1, 0, font, fontSize, Color.BLACK, true, false, true);
		GraphicsTools.addStyledText(sliderHelp, helpText2, 1, font, fontSize, Color.BLACK, false, false, true);

		sliderPanel.add(sliderHelp);
	}

	// Add easy/medium/hard buttons to quickStartPanel
	public void createQuickStartButtons() {
		int width = 190;
		int height = 90;
		easy = new JButton("easy") {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(width,height);
			};
		};
		easy.setFocusable(false);

		medium = new JButton("medium") {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(width,height);
			};
		};
		medium.setFocusable(false);

		hard = new JButton("hard") {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(width,height);
			};
		};
		hard.setFocusable(false);

		easy.setActionCommand("easy");
		easy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {

				Grid g = new Grid();

				Generator.generatePuzzle(g, 40, 10, false);

				handler.destroy();
				handler.init();
				handler.createButtons(g);
      }
		});

		medium.setActionCommand("medium");
		medium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {

				Grid g = new Grid();

				Generator.generatePuzzle(g, 48, 5, false);

				handler.destroy();
				handler.init();
				handler.createButtons(g);
      }
		});

		hard.setActionCommand("hard");
		hard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {

				Grid g = new Grid();

				Generator.generatePuzzle(g, 56, 3, true);

				handler.destroy();
				handler.init();
				handler.createButtons(g);
      }
		});

		int fontSize = 32;
		String font = "Helvetica";
		quickStartPanel.add(easy);
		easy.setFont(new Font(font, Font.PLAIN, fontSize));
		easy.setText("Easy");

		quickStartPanel.add(medium);
		medium.setFont(new Font(font, Font.PLAIN, fontSize));
		medium.setText("Medium");

		quickStartPanel.add(hard);
		hard.setFont(new Font(font, Font.PLAIN, fontSize));
		hard.setText("Hard");


	}

	// Display all elements. Only needs to be called once as there are no
	// moving parts
	public void updateDisplay() {
		setVisible(true);
		mainPanel.setVisible(true);
		quickStartPanel.setVisible(true);
		startPanel.setVisible(true);
		start.setVisible(true);
		sliderPanel.setVisible(true);
		slider.setVisible(true);
		sliderHelp.setVisible(true);
	}

}

// Static helper methods for drawing graphics
class GraphicsTools {
	// Add the text s to pane in specified format
	public static void addStyledText(JTextPane pane, String s, int id, String font, int fontSize, Color color, boolean bold, boolean italic, boolean center) {
		StyledDocument doc = pane.getStyledDocument();
		SimpleAttributeSet attributeSet = new SimpleAttributeSet();
		String uniqueID = Integer.toString(id);
		Style style = pane.addStyle(uniqueID, null);
		if(center) {
			StyleConstants.setAlignment(attributeSet, StyleConstants.ALIGN_CENTER);
		}
		if(bold) {
			StyleConstants.setBold(style, true);
		}
		if(italic) {
			StyleConstants.setItalic(style, true);
		}
		StyleConstants.setFontFamily(style, font);
		StyleConstants.setFontSize(style, fontSize);
		StyleConstants.setForeground(style, color);

		try {doc.insertString(doc.getLength(), s, style); }
    catch (BadLocationException e){}

		doc.setParagraphAttributes(0, doc.getLength() - 1, attributeSet, false);
	}

	// Return a Color object for rgb triplet specified in [0,255]
	public static Color makeColor(double r, double g, double b) {
		return new Color((float) (r/255.0),(float) (g/255.0),(float) (b/255.0));
	}


	// TODO: Add more sounds
	// Credit to https://www.cs.miami.edu/home/visser/csc329-files/SoundJava.pdf for demonstrating how to play sound
	public static void playSound(String filename) {
		try {
			File tada = new File(filename);
			AudioInputStream stream = AudioSystem.getAudioInputStream(tada);
			AudioFormat format = stream.getFormat();

			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();

		} catch (Exception e) {
			return;
		}
	}
}

// Custom game GUI: holds graphics, tracks the score and does gameover popup
class MyGui extends JFrame {

	private ButtonPanel buttonHolder = new ButtonPanel(this);
	private int score, hints, changedGuesses; // Internal score (0-27), #hints requested, #changed values of filled cells
	private boolean autoSolved; // Whether autoSolver was used -- scores 0
	private long startTime;

	// Construct window
	public void createGUI() {
		setTitle("Sudoku");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(750, 800);
		setFocusable(true);
		score = 0;
		hints = 0;
		changedGuesses = 0;
		autoSolved = false;
		startTime = System.currentTimeMillis();

		add(buttonHolder);

	}

	// Tell buttonHolder to give its buttons a reference to their parent
	// Needed so buttons can hand over input processing
	public void addReferences() {
		buttonHolder.addReferences(buttonHolder);
	}

	// Output and focus
	public void updateDisplay(boolean user) {
		setVisible(true);
		toFront();
		requestFocus();
		buttonHolder.updateDisplay(user);
	}

	// Force every button in the holder to update graphics
	// Useful when many have been modified e.g. by auto-solver
	public void forceFullUpdate(boolean user) {
		buttonHolder.forceFullUpdate(user);
	}

	// Return String telling user how they did, based on their score s
	private String getCustomScoreMessage(int s) {
		if(s == 0) {
			return new String("TERRIBLE: You need to try harder");
		} else if (s < 100) {
			return new String("POOR: Try not to use hints next time");
		} else if (s == 100) {
			return new String("NOT BAD: Finish faster for more points");
		} else if (s < 700) {
			return new String("NICE ONE! Keep up the good work :)");
		} else if (s < 940){
			return new String("EXCELLENT!! Why not try a harder puzzle?");
		} else {
			return new String("AMAZING!!! You must be a pro ;)");
		}
	}

	// Create and display a new window with game statistics (score, time etc.)
	public void gameoverPopup() {
		JFrame window = new JFrame();
		window.setSize(750, 350);
		window.setTitle("Puzzle complete");
		window.setVisible(true);
		window.toFront();

		JPanel panel = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(750,350);
			};
		};

		Color pink = GraphicsTools.makeColor(242.0, 0.0, 147.0);

		panel.setBackground(Color.GREEN);

		window.add(panel);

		JTextPane text = new JTextPane() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(750,350);
			};
		};

		text.setEditable(false);
		text.setBackground(Color.GREEN);
		int finalScore = calcUserScore(true);
		// Different strings so statistics can be highlighted pink and bold
		String message0 = new String("CONGRATULATIONS!");
		String message1 = new String("\nTime used: ");
		String message2 = new String(displayTime());
		String message3 = new String("\nScore: ");
		String message4 = new String(Integer.toString(finalScore));
		String message5 = new String("\n"+getCustomScoreMessage(finalScore));


		String font = "Helvetica";
		int fontSize = 60;
		// Add normal text in black and statistics in bold pink
		GraphicsTools.addStyledText(text, message0, 0, font, fontSize, Color.BLACK, true, false, true);
		GraphicsTools.addStyledText(text, message1, 1, font, fontSize, Color.BLACK, false, false, true);
		GraphicsTools.addStyledText(text, message2, 2, font, fontSize, pink, true, false, true);
		GraphicsTools.addStyledText(text, message3, 3, font, fontSize, Color.BLACK, false, false, true);
		GraphicsTools.addStyledText(text, message4, 4, font, fontSize, pink, true, false, true);
		GraphicsTools.addStyledText(text, message5, 5, font, fontSize, Color.BLACK, false, true, true);

		panel.add(text);
		panel.setVisible(true);
		text.setVisible(true);

		GraphicsTools.playSound("sounds/ta-da.wav");

	}

	// Get score of grid associated with buttonHolder
	private int getScore() {
		return buttonHolder.getGrid().score();
	}

	// Get % of lines/rows/boxes filled, [0-100]
	private int getCompletionPercentage() {
		// Use number of groups of 9 completed, convert to %
		return (int) ((((double) getScore()) / 27.0) * 100.0);
	}

	// Mark that user has pressed hint
	public void recordHint() {
		hints++;
	}

	// Mark that user has 'cheated'
	public void recordAutoSolve() {
		autoSolved = true;
	}

	// Mark that user changed a previous guess
	public void recordChangeGuess() {
		changedGuesses++;
	}

	// Scoring system:
	// 0 points for using solver
	// < 100 points for a solution using hints
	// 100 points for using no hints, taking >=30 minutes
	// 101-1000 points for using no hints and <30 minutes
	private int calcUserScore(boolean includeTime) {

		if(autoSolved) {
			return 0;
		}

		// Award up to 100 points for completion, i.e. fraction of completed lines/rows/boxes
		int s = getCompletionPercentage();

		// Lose 10 points for each hint used. Using any hints disables time bonuses.
		int hintPoints = (hints > 10) ? 10 : hints;
		s -= (hintPoints * 10);


		// Gain 0.5 for every second below 30 minutes / no penalty for taking longer. This gives normal score of 100, max score 1000
		if(includeTime && (hints == 0)) {
			s += 900; // Assume baseline time of 30 minutes
			int seconds = (int) secondsUsed();
			// Don't penalise for anything above 30m
			seconds = (seconds > 1800) ? 1800 : seconds;
			// But subtract 0.5 for every second used within the first 30m
			s -= (seconds / 2);
		}

		// Lose 1 point for each changed answer, but don't make score -ve
		int changes = (changedGuesses > s) ? s : changedGuesses;
		s -= changes;

		return s;
	}

	// Return seconds passed since startTime, rounded down
	private long secondsUsed() {
		long currentTime = System.currentTimeMillis();
		return (currentTime - startTime) / 1000;
	}

	// Return String representation of minutes and seconds passed since startTime
	private String displayTime() {
		long secondsUsed = secondsUsed();
		long minutesUsed = secondsUsed / 60;
		secondsUsed = secondsUsed % 60;

		return timeString(minutesUsed, secondsUsed);
	}

	// Return String representation of # of minutes and seconds
	private String timeString(long m, long s) {
		if(m > 0) {
			 return new String(Long.toString(m) + "m "+ Long.toString(s) + "s");
		}
		return new String(Long.toString(s) + "s");
	}

	// Modify window title to display score and time used
	// Check gameover condition and make gameover popup if needed
	public void updateTitle(boolean user) {

		score = getScore(); // 0-27
		// All 27 lines/rows/boxes complete; game is being played by human user
		if((score == 27) && user) {
			gameoverPopup();
		}

		// Update title
		setTitle("Sudoku | Score: "+calcUserScore(false)+" | Time used: "+displayTime()+" | Progress: "+Integer.toString(getCompletionPercentage())+"%");
	}

	// Initialise game state info; i.e. clashes, complete lines
	public void initInfo() {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				buttonHolder.updateInfo(i, j, false);
			}
		}
	}

	// Add a button at logical coordinates (row,column), graphical coordinates
	// (graphicX, graphicY), holding contents c
	public void addButton(int row, int column, int graphicX, int graphicY, int width, int height, Cell c) {
		this.buttonHolder.addButton(row, column, graphicX, graphicY, width, height, c);
	}
}

// Hold 2D array of buttons; handle input when these buttons are clicked
class ButtonPanel extends JPanel {

	private MyButton[][] buttons;
	private int cursorX = 0;
	private int cursorY = 0;
	// Reference to GUI that created this, so we can tell it to record statistics
	private MyGui guiPointer;

	// Make window and add list of actions performable
	public ButtonPanel(MyGui guiPointer) {
		this.guiPointer = guiPointer;
		// Last row and column are unused; this is a dirty workaround for a
		// graphical bug. TODO: reduce to [9][9] without last button dissapearing
		buttons = new MyButton[10][10];

		setVisible(true);
		setSize(730,730);
		setLayout(new BorderLayout());

		InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
    ActionMap actionMap = getActionMap();

		// Actions for button presses 0-9, which (try to) set the value of
		// the currently selected cell
		Action action0 = new AbstractAction() {
      public void actionPerformed(ActionEvent actionEvent) {
        MyButton selectedButton = getSelectedButton();
				selectedButton.setValue(0, true);
      }
    };
		Action action1 = new AbstractAction() {
      public void actionPerformed(ActionEvent actionEvent) {
        MyButton selectedButton = getSelectedButton();
				selectedButton.setValue(1, true);
      }
    };
		Action action2 = new AbstractAction() {
      public void actionPerformed(ActionEvent actionEvent) {
        MyButton selectedButton = getSelectedButton();
				selectedButton.setValue(2, true);
      }
    };
		Action action3 = new AbstractAction() {
      public void actionPerformed(ActionEvent actionEvent) {
        MyButton selectedButton = getSelectedButton();
				selectedButton.setValue(3, true);
      }
    };
		Action action4 = new AbstractAction() {
      public void actionPerformed(ActionEvent actionEvent) {
        MyButton selectedButton = getSelectedButton();
				selectedButton.setValue(4, true);
      }
    };
		Action action5 = new AbstractAction() {
      public void actionPerformed(ActionEvent actionEvent) {
        MyButton selectedButton = getSelectedButton();
				selectedButton.setValue(5, true);
      }
    };
		Action action6 = new AbstractAction() {
      public void actionPerformed(ActionEvent actionEvent) {
        MyButton selectedButton = getSelectedButton();
				selectedButton.setValue(6, true);
      }
    };
		Action action7 = new AbstractAction() {
      public void actionPerformed(ActionEvent actionEvent) {
        MyButton selectedButton = getSelectedButton();
				selectedButton.setValue(7, true);
      }
    };
		Action action8 = new AbstractAction() {
      public void actionPerformed(ActionEvent actionEvent) {
        MyButton selectedButton = getSelectedButton();
				selectedButton.setValue(8, true);
      }
    };
		Action action9 = new AbstractAction() {
      public void actionPerformed(ActionEvent actionEvent) {
        MyButton selectedButton = getSelectedButton();
				selectedButton.setValue(9, true);
      }
    };

		// Action for asking for a hint. Tries to set value of selected cell
		// to value from solution, if legal
		Action hint = new AbstractAction() {
      public void actionPerformed(ActionEvent actionEvent) {
				MyButton selectedButton = getSelectedButton();
				int value = selectedButton.requestHint();
				if(value >= 0) {
					selectedButton.setValue(value, true);
					guiPointer.recordHint();
				}
      }
    };
		// Action for auto-solving. Tries to set value of all cells to solution
		Action solve = new AbstractAction() {
      public void actionPerformed(ActionEvent actionEvent) {
				guiPointer.recordAutoSolve();
				MyButton selectedButton = getSelectedButton();
				selectedButton.requestSolution();
      }
    };

		// Add keypresses of buttons [0-9], 'H', 'S' to inputMap
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_0, 0, false), "Pressed0");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0, false), "Pressed1");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0, false), "Pressed2");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0, false), "Pressed3");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0, false), "Pressed4");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_5, 0, false), "Pressed5");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_6, 0, false), "Pressed6");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_7, 0, false), "Pressed7");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_8, 0, false), "Pressed8");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_9, 0, false), "Pressed9");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0, false), "hint");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "solve");

		// Associate the keypresses with actions above
		actionMap.put("Pressed0", action0);
		actionMap.put("Pressed1", action1);
		actionMap.put("Pressed2", action2);
		actionMap.put("Pressed3", action3);
		actionMap.put("Pressed4", action4);
		actionMap.put("Pressed5", action5);
		actionMap.put("Pressed6", action6);
		actionMap.put("Pressed7", action7);
		actionMap.put("Pressed8", action8);
		actionMap.put("Pressed9", action9);
		actionMap.put("hint", hint);
		actionMap.put("solve", solve);

	}

	public void recordChangeGuess() {
		guiPointer.recordChangeGuess();
	}

	// Update display of all buttons; update title (checks for gameover)
	public void updateDisplay(boolean user) {
		for(int j=0; j<9; j++) {
			for(int i=0; i<9; i++) {
				buttons[i][j].updateDisplay();
			}
		}
		setVisible(true);
		guiPointer.updateTitle(user);
	}

	// Does work of updateDisplay; additionally updates conflict/completion info
	public void forceFullUpdate(boolean user) {
		for(int j=0; j<9; j++) {
			for(int i=0; i<9; i++) {
				updateInfo(i, j, user);
			}
		}
		guiPointer.updateTitle(user);
	}

	// Gives buttons a reference to this
	public void addReferences(ButtonPanel bp) {
		for(int j=0; j<9; j++) {
			for(int i=0; i<9; i++) {
				buttons[i][j].setParent(bp);
			}
		}
	}

	// Update text colours for all buttons
	public void updateColour() {
		for(int j=0; j<9; j++) {
			for(int i=0; i<9; i++) {
				buttons[i][j].updateColour();
			}
		}
		repaint();
	}

	// Return grid associated with first button/cell, if present
	public Grid getGrid() {
		if(buttons == null) {
			return null;
		}
		return buttons[0][0].getGrid();
	}

	// Update info on conflicts + complete lines after cell (x,y) modified
	// Also trigger graphical update
	public void updateInfo(int x, int y, boolean user) {

		Grid g = getGrid();

		g.updateClash(y, x);

		g.updateCompleteness(x, y);

		updateDisplay(user);

	}

	// Add a button at logical coordinates (row,column), graphical coordinates
	// (graphicX, graphicY), holding contents c
	public void addButton(int row, int column, int graphicX, int graphicY, int width, int height, Cell c) {

		MyButton b = new MyButton(row, column, c);
		b.setBounds(graphicX, graphicY, width, height);

		add(b);

		// Clicking on button updates cursor
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cursorX = b.getRow();
				cursorY = b.getColumn();
				// printCursorLocation();
			}
		});

		buttons[row][column] = b;

	}

	// Return button most recently clicked on (selected)
	public MyButton getSelectedButton() {
		return buttons[cursorX][cursorY];
	}

	// Output cursor position to std.out; for debugging
	public void printCursorLocation() {
		System.out.println(Integer.toString(cursorX)+", "+Integer.toString(cursorY));
	}
}

// Custom button which holds reference to cell, knows its grid position
class MyButton extends JButton {
	private Cell c;
	private int row,column;
	private ButtonPanel parent;

	// Create button at grid position (row, column)
	public MyButton(int row, int column, Cell c) {
		this.c = c;
		this.row = row;
		this.column = column;
		updateText();
		// Ugly hack, for extra "dissapearing" button (TODO: fix)
		if(row>8) {
			setForeground(Color.WHITE);
		}
		updateColour();
		setFont(new Font("Helvetica", Font.BOLD, 32));
		setContentAreaFilled(false);
		setOpaque(true);
	}

	public void setParent(ButtonPanel bp) {
		parent = bp;
	}

	public Grid getGrid() {
		return c.getGrid();
	}

	public ButtonPanel getParent() {
		return parent;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void updateDisplay() {
		updateText();
		updateColour();
		setVisible(true);
	}

	public void updateText() {
		setText(Integer.toString(c.getValue()));
	}

	// Change text colour, and background colour if clashing/complete
	public void updateColour() {
		// Empty cells have invisible text; immutables black; user guesses blue
		if(c.getValue() == 0) {
			setForeground(Color.WHITE);
		} else {
			if(row<9) {
				setForeground(Color.BLUE);
				if(c.getImmutable()) {
					setForeground(Color.BLACK);
				}
			}
		}
		// Clashes (duplicates in row/column/box) have red background
		// Cells in complete row/column/box have green background if none clash
		if(c.getClashing()) {
			setBackground(Color.RED);
		} else {
			if(c.getComplete()) {
				if(c.noClashes()) {
					setBackground(Color.GREEN);
				} else {
					setBackground(Color.WHITE);
				}
			} else {
				setBackground(Color.WHITE);
			}
		}
	}

	// Get value of cell according to solution, if cell is mutable
	public int requestHint() {
		if(!c.getImmutable()) {
			return c.requestHint(row, column);
		} else {
			return -1;
		}
	}

	// Ask cell to ask its grid for solution
	public void requestSolution() {
		c.requestSolution();
	}

	// Update value of cell if possible; update conflicts/completions if necc.
	public void setValue(int v, boolean user) {
		if(user) {
			if((c.getValue() != 0) && (c.getValue() != v)) {
				parent.recordChangeGuess();
			}
		}
		c.setValue(v);
		parent.updateInfo(column, row, user);
	}

}

// Creates menu and game screen, has ability to refresh game screen display
class GraphicsHandler {

	private MyGui myGUI = new MyGui();
	private MainMenu mainMenu = new MainMenu();

	// Initialise the main menu
	public void createMenu(GraphicsHandler handler) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mainMenu.createMenu(handler);
				mainMenu.updateDisplay();
			}
		});
	}

	// Run the game
	public void init() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				myGUI.createGUI();
			}
		});
	}

	// Clear the game screen
	public void destroy() {
		myGUI = new MyGui();
	}

	// Add buttons to myGUI based on cells in grid g
	public void createButtons(Grid g) {

		// Make each box 60x60 pixels, and start 60x60 from top-left corner
		int x = 60;
		int y = 60;
		int width = 60;
		int height = 60;
		// Iterate over cells
		for(int j=0; j<9; j++) {
			for(int i=0; i<9; i++) {
				// Add button for relevant cell
				myGUI.addButton(i, j, x, y, width, height, g.getCell(i, j));

				x += width;
				// Place small gaps after every 3rd column to group them
				if((i % 3) == 2) {
					x += (width / 6);
				}
			}
			x = width;
			y += height;
			// Place small gaps after every 3rd row to group them
			if((j % 3) == 2) {
				y += (height / 6);
			}
		}
		myGUI.addButton(9,9,0,0,0,0,new Cell()); // Ugly hack, need to figure out why last button keeps dissapearing.
		myGUI.addReferences();
		myGUI.initInfo(); // For initial cell highlighting
		myGUI.updateDisplay(false);
	}

	public void updateDisplay(boolean user) {
		myGUI.updateDisplay(user);
	}

	public void forceFullUpdate(boolean user) {
		myGUI.forceFullUpdate(user);
	}

}

// Holds one number in the Grid, plus some meta-info such as immutability,
// whether the value clashes with others etc.
class Cell {
	private int value; // 0-9; 0 := EMPTY
	private boolean immutable; // True for cells filled during puzzle generation
	private boolean clashing; // True if same value exists in row/column/box
	private Grid gPointer; // Reference to parent, for handling input
	private boolean complete; // True if row OR column OR box is complete

	public Cell() {
		this.value = 0;
		this.immutable = false;
		this.clashing = false;
	}

	public int getValue() {
		return value;
	}

	// Return true if part of a Grid with no clashes
	public boolean noClashes() {
		return (gPointer.getNumberOfClashes() == 0);
	}

	// Change value to v, if mutable
	public void setValue(int v) {
		assert ((v >=0) && (v <=9));
		if(!this.immutable) {
			this.value = v;
		}
	}

	// Change value to v, ignoring mutability. USE SPARINGLY!
	public void forceValue(int v) {
		assert ((v >=0) && (v <=9));
		this.value = v;
	}

	public void setImmutable(boolean b) {
		this.immutable = b;
	}

	public boolean getImmutable() {
		return this.immutable;
	}

	public boolean setClashing(boolean b) {
		boolean change = (this.clashing != b);
		this.clashing = b;
		return change;
	}

	public boolean getClashing() {
		return this.clashing;
	}

	public void setGrid(Grid g) {
		this.gPointer = g;
	}

	public Grid getGrid() {
		return this.gPointer;
	}

	public boolean setComplete(boolean b) {
		boolean change = (this.complete != b);
		this.complete = b;
		return change;
	}

	public boolean getComplete() {
		return this.complete;
	}

	// Ask parent for hint and return it
	public int requestHint(int row, int column) {
		return gPointer.hint(row, column);
	}

	// Ask parent to solve itself
	public void requestSolution() {
		gPointer.autoSolve();
	}
}

// Hold two sets of coordinates, for two cells in same box/row/col which clash
class Clash {
	// (x1, y1) is first cell, (x2, y2) is second cell
	public int x1;
	public int y1;
	public int x2;
	public int y2;

	public Clash(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	// Return true if (x, y) is one of the two clashing cells
	public boolean containsCell(int x, int y) {
		return ((x == x1) && (y == y1)) || ((x == x2) && (y == y2));
	}

}

// Hold a list of clashes
class ClashList {
	private ArrayList<Clash> list;

	public ClashList() {
		list = new ArrayList<Clash>();
	}

	public int numberOfClashes() {
		return list.size();
	}

	// Return true if a clash of the same two cells is already recorded
	public boolean existing(int x1, int y1, int x2, int y2) {
		for(Clash c : list) {
			if(c.containsCell(x1, y1) && c.containsCell(x2, y2)) {
				return true;
			}
		}
		return false;
	}

	// Record clash of two cells, if not already recorded
	public void addClash(int x1, int y1, int x2, int y2) {
		if(!existing(x1, y1, x2, y2)) {
			list.add(new Clash(x1, y1, x2, y2));
		}
	}

	// Remove all clashes featuring (x, y). Used when value of (x, y) changes
	public void removeClashes(int x, int y) {
		ArrayList<Clash> removals = new ArrayList<Clash>();
		// Build up list of clashes containing (x, y)
		for(Clash c : list) {
			if(c.containsCell(x, y)) {
				removals.add(c);
			}
		}
		// Then remove them all
		list.removeAll(removals);
	}

	// Update clash attributes of cells; return true if any changed
	public boolean updateCells(Grid g) {
		boolean change = false;
		boolean[][] trues = new boolean[9][9];
		// Iterate over all clashes; handle clashing cells
		for(Clash c : list) {
			// If setCellClashing ever returns true, change will stay true
			change = g.setCellClashing(c.x1, c.y1, true) || change;
			change = g.setCellClashing(c.x2, c.y2, true) || change;
			// Record clashing cells in trues
			trues[c.x1][c.y1] = true;
			trues[c.x2][c.y2] = true;
		}
		// Handle non-clashing cells
		for(int y=0; y<9; y++) {
			for(int x=0; x<9; x++) {
				if(!trues[x][y]) {
					change = g.setCellClashing(x, y, false) || change;
				}
			}
		}
		return change;
	}

}


class Grid {
	private Cell[][] cells;

	int completions; // 81 iff puzzle is complete

	private boolean[] columnCompletionMatrix;
	private boolean[] rowCompletionMatrix;
	private boolean[][] boxCompletionMatrix;

	// private int[][] clashMatrix; // True where cell has same value as one in its row OR column OR box
	int clashes; // 0 iff clashMatrix is false EVERYWHERE

	private ClashList clashList;

	public Grid() {
		cells = new Cell[9][9];
		for(int j=0; j<9; j++) {
			for(int i=0; i<9; i++) {
				cells[j][i] = new Cell();
			}
		}
		// completionMatrix = new int[9][9]; // Initially false everywhere
		completions = 0;

		rowCompletionMatrix = new boolean[9];
		columnCompletionMatrix = new boolean[9];
		boxCompletionMatrix = new boolean[3][3];

		// clashMatrix = new int[9][9]; // Initialise false everywhere
		clashes = 0;
		clashList = new ClashList();
		addReferences(this);
	}

	@Override
	public boolean equals(Object o) {
	  if (this == o) {
	    return true;
		}
	  if (o == null) {
	    return false;
		}
	  if (getClass() != o.getClass()) {
	    return false;
		}
		Grid g = (Grid) o;
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(getCellValue(i, j) != g.getCellValue(i, j)) {
					return false;
				}
			}
		}
		return true;
	}

	public void copyTo(Grid g) {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				g.forceCellValue(i, j, getCellValue(i, j));
			}
		}
	}

	public void copyToWithImmutables(Grid g) {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				g.forceCellValue(i, j, getCellValue(i, j));
				g.setCellImmutability(i, j, getCellImmutability(i, j));
			}
		}
	}

	public void makeAllImmutable(boolean b) {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(getCellValue(i,j) != 0) {
					cells[i][j].setImmutable(b);
				}
			}
		}
	}

	// Populate grid with values. Use immutable to make the results unchangeable by user, and force to override existing immutable values.
	public void setupGrid(int[][] values, boolean immutable, boolean force) {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(force) {
					cells[i][j].forceValue(values[i][j]);
				} else {
					cells[i][j].setValue(values[i][j]);
				}
				if(immutable && (values[i][j] != 0)) {
					cells[i][j].setImmutable(true);
				}
			}
		}
	}

	public void addReferences(Grid g) {
		for(int j=0; j<9; j++) {
			for(int i=0; i<9; i++) {
				cells[i][j].setGrid(g);
			}
		}
	}

	public int getNumberOfClashes() {
		return clashList.numberOfClashes();
	}

	public Cell getCell(int x, int y) {
		return cells[x][y];
	}

	public int getCellValue(int x, int y) {
		return cells[x][y].getValue();
	}

	public void setCellValue(int x, int y, int v) {
		cells[x][y].setValue(v);
	}

	public void forceCellValue(int x, int y, int v) {
		cells[x][y].forceValue(v);
	}

	public void setCellImmutability(int x, int y, boolean b) {
		cells[x][y].setImmutable(b);
	}

	public boolean getCellImmutability(int x, int y) {
		return cells[x][y].getImmutable();
	}

	public boolean getCellClashing(int x, int y) {
		return cells[x][y].getClashing();
		// return clashMatrix[x][y];
	}

	// public void setCellClashing(int x, int y, boolean b) {
	// 	cells[x][y].setClashing(b);
	// 	clashMatrix[x][y] = b;
	// }

	public boolean setCellClashing(int x, int y, boolean b) {
		return cells[x][y].setClashing(b);
		// boolean change = (clashMatrix[x][y] != b);
		// clashMatrix[x][y] = b;
		// if(change) {
		// 	if(b) {
		// 		clashes += 1;
		// 	} else {
		// 		clashes -= 1;
		// 	}
		// }
		// return change;
	}


	public boolean getCellComplete(int x, int y) {
		return cells[x][y].getComplete();
		// return completionMatrix[x][y];
	}

	// public void setCellComplete(int x, int y, boolean b) {
	// 	cells[x][y].setComplete(b);
	// 	completionMatrix[x][y] = b;
	// }

	public boolean setCellComplete(int x, int y, boolean b) {
		boolean change = cells[x][y].setComplete(b);
		// boolean change = (completionMatrix[x][y] != b);
		// completionMatrix[x][y] = b;
		if(change) {
			if(b) {
				completions += 1;
			} else {
				completions -= 1;
			}
		}
		return change;
	}


	// Run AFTER updateClash, since clashes needs to be correct
	public boolean updateCompleteness(int y, int x) {
		boolean change = false;

		if(clashes > 0) {
			// Everything considered incomplete if solution is invalid
			change = (completions != 0);
			completions = 0;
			for(int i=0; i<9; i++) {
				rowCompletionMatrix[i] = false;
				columnCompletionMatrix[i] = false;
			}
			for(int j=0; j<3; j++) {
				for(int i=0; i<3; i++) {
					boxCompletionMatrix[i][j] = false;
				}
			}
		} else {
			// Solution is valid; completeness is possible

			int z; // Takes on values of cells
			// boolean rowComplete = true;
			// boolean columnComplete = true;
			// boolean boxComplete = true;
			// boolean rowWasComplete = rowCompletionMatrix[y];
			// boolean columnWasComplete = columnCompletionMatrix[x];
			// boolean boxWasComplete = boxCompletionMatrix[x][y];

			rowCompletionMatrix[y] = true;
			for(int col=0; col<9; col++) {
				z = getCellValue(col, y);
				if(z == 0) {
					rowCompletionMatrix[y] = false;
					break;
				}
			}

			columnCompletionMatrix[x] = true;
			for(int row=0; row<9; row++) {
				z = getCellValue(x, row);
				// System.out.println(Integer.toString(z));
				if(z == 0) {
					columnCompletionMatrix[x] = false;
					// System.out.println("Column: "+Integer.toString(x)+" incomplete. Empty in row "+Integer.toString(row));
					break;
				}
			}


			// Find top left coordinate of 3x3 box
			int topLeftX = x - (x % 3);
			int topLeftY = y - (y % 3);
			boxCompletionMatrix[topLeftX/3][topLeftY/3] = true;
			for(int col=topLeftX; col<topLeftX+3; col++) {
				for(int row=topLeftY; row<topLeftY+3; row++) {
					z = getCellValue(col, row);
					if(z == 0) {
						boxCompletionMatrix[topLeftX/3][topLeftY/3] = false;
						break;
					}
				}
			}
		}

		// We know whether each row, column and box is complete. Now go over each cell and update the individual completeness value, which will be true wherever ONE OF these is true. setCellComplete will tell us whether these updates change anything, i.e. whether cell colours will need updating.

		for(int row=0; row<9; row++) {
			for(int col=0; col<9; col++) {
				change = setCellComplete(col, row, (rowCompletionMatrix[row] || columnCompletionMatrix[col] || boxCompletionMatrix[col/3][row/3])) || change; // Change starts off false, update if it becomes true
			}
		}

		return change;

	}

	// Returns number of clashes
	public int updateClash(int x, int y) {

		int z;
		int value = getCellValue(x, y);

		// First remove any clashes featuring the changed cell. If the change hasn't fixed the clashes they'll be added again below anyway.
		clashList.removeClashes(x, y);

		// Go over row/column of changed cell
		for(int i=0; i<9; i++) {
			// Interpreting i as column, moving along fixed row
			z = getCellValue(i, y);
			if((i != x) && (z == value) && (value != 0)) {
				clashList.addClash(x, y, i, y);
			}

			// Interpresting i as row, moving down fixed column
			z = getCellValue(x, i);
			if((i != y) && (z == value) && (value != 0)) {
				clashList.addClash(x, y, x, i);
			}
		}

		// Go through box of changed cell
		int topLeftX = x - (x % 3);
		int topLeftY = y - (y % 3);
		for(int col=topLeftX; col<topLeftX+3; col++) {
			for(int row=topLeftY; row<topLeftY+3; row++) {
				z = getCellValue(col, row);
				if(((col != x) || (row != y)) && (z == value) && (value != 0)) {
					clashList.addClash(x, y, col, row);
				}
			}
		}

		// Update cells based on new clash list
		clashList.updateCells(this);

		return clashList.numberOfClashes();

	}

	public boolean isSolution() {
		ArrayList<Integer> rowPresent = new ArrayList<Integer>();
		ArrayList<Integer> columnPresent = new ArrayList<Integer>();
		int value, value2;
		// Check rows and columns
		for(int i=0; i<9; i++) {
			rowPresent.clear();
			columnPresent.clear();
			for(int j=0; j<9; j++) {
				value = getCellValue(i, j);
				value2 = getCellValue(j, i);
				// Only need to check one value var, since it goes over all cells
				if((value < 1) || (value > 9)) {
					return false;
				}
				if(rowPresent.contains(value) || columnPresent.contains(value2)) {
					return false;
				} else {
					rowPresent.add(value);
					columnPresent.add(value2);
				}
			}
		}
		// Check boxes: the first 2 fors are analagous to the outer loop above, the second 2 fors are analagous to the inner loop
		for(int topLeftX=0; topLeftX<9; topLeftX+=3) {
			for(int topLeftY=0; topLeftY<9; topLeftY+=3) {
				rowPresent.clear(); // view this as boxPresent

				for(int i=topLeftX; i<topLeftX+3; i++) {
					for(int j=topLeftY; j<topLeftY+3; j++) {

						value = getCellValue(i, j);
						// No need to check if it's 1-9 again
						if(rowPresent.contains(value)) {
						 return false;
					 	} else {
							rowPresent.add(value);
					 	}

					}
				}

			}
		}
		// If we fall through there are no conflicts
		return true;
	}

	public void clear() {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				setCellImmutability(i, j, false);
				setCellValue(i, j, 0);
			}
		}
	}

	public void clearNonImmutables() {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(!getCellImmutability(i, j)) {
					setCellValue(i, j, 0);
				}
			}
		}
	}

	public int hint(int row, int column) {
		// Make a copy to solve on
		Grid gCopy = new Grid();
		copyToWithImmutables(gCopy);


		// Ignore user's guesses as they may make solution impossible
		gCopy.clearNonImmutables();


		// Solve
		Solver s = new Solver();
		s.solve(gCopy, 0, false, 0);

		// GraphicsHandler handler = new GraphicsHandler();
		// handler.init();
		// handler.createButtons(gCopy);

		// Can't help
		if(!gCopy.isSolution()) {
			return -2;
		}

		// Pass requested value from solution
		return gCopy.getCellValue(row, column);

	}

	public void autoSolve() {

		Grid gCopy = new Grid();
		copyToWithImmutables(gCopy);

		// Ignore user's guesses as they may make solution impossible
		gCopy.clearNonImmutables();

		Solver s = new Solver();
		s.solve(gCopy, 0, false, 30000);

		GraphicsHandler handler = new GraphicsHandler();
		handler.init();
		handler.createButtons(gCopy);

	}

	public int score() {
		if(clashList.numberOfClashes() > 0) {
			return 0;
		}
		int s = 0;
		for(int i=0; i<9; i++) {
			if(rowCompletionMatrix[i]) {
				s++;
			}
			if(columnCompletionMatrix[i]) {
				s++;
			}
			if(boxCompletionMatrix[i/3][i%3]) {
				s++;
			}
		}
		return s;
	}

}

class Utilities {

	public static boolean clash(Grid g, int x, int y, int value) {
		return (Utilities.rowClash(g, x, y, value) || Utilities.colClash(g, x, y, value) || Utilities.boxClash(g, x, y, value));
	}

	public static boolean rowClash(Grid g, int x, int y, int value) {
		int z;
		for(int col=0; col<9; col++) {
			z = g.getCellValue(col, y);
			if((col != x) && (z == value)) {
				return true;
			}
		}
		return false;
	}

	public static boolean colClash(Grid g, int x, int y, int value) {
		int z;
		for(int row=0; row<9; row++) {
			z = g.getCellValue(x, row);
			if((row != y) && (z == value)) {
				return true;
			}
		}
		return false;
	}

	public static boolean boxClash(Grid g, int x, int y, int value) {
		int z;
		int topLeftX = x - (x % 3);
		int topLeftY = y - (y % 3);
		for(int col=topLeftX; col<topLeftX+3; col++) {
			for(int row=topLeftY; row<topLeftY+3; row++) {
				z = g.getCellValue(col, row);
				if(((col != x) || (row != y)) && (z == value)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean rowComplete(Grid g, int y) {
		int z;
		for(int col=0; col<9; col++) {
			z = g.getCellValue(col, y);
			if((z == 0) || g.getCellClashing(col, y)) {
				return false;
			}
		}
		return true;
	}

	public static boolean colComplete(Grid g, int x) {
		int z;
		for(int row=0; row<9; row++) {
			z = g.getCellValue(x, row);
			if((z == 0) || g.getCellClashing(x, row)) {
				return false;
			}
		}
		return true;
	}

	public static boolean boxComplete(Grid g, int x, int y) {
		int z;
		int topLeftX = x - (x % 3);
		int topLeftY = y - (y % 3);
		for(int col=topLeftX; col<topLeftX+3; col++) {
			for(int row=topLeftY; row<topLeftY+3; row++) {
				z = g.getCellValue(col, row);
				if((z == 0) || g.getCellClashing(x, y)) {
					return false;
				}
			}
		}
		return true;
	}

	public static void setRowCompleteness(Grid g, int row, boolean value) {
		for(int col=0; col<9; col++) {
			g.setCellComplete(col, row, value);
		}
	}

	public static void setColCompleteness(Grid g, int col, boolean value) {
		for(int row=0; row<9; row++) {
			g.setCellComplete(col, row, value);
		}
	}

	public static void setBoxCompleteness(Grid g, int x, int y, boolean value) {
		int topLeftX = x - (x % 3);
		int topLeftY = y - (y % 3);
		for(int col=topLeftX; col<topLeftX+3; col++) {
			for(int row=topLeftY; row<topLeftY+3; row++) {
				g.setCellComplete(col, row, value);
			}
		}
	}


}

class Sudoku {


	private static void runTestGame() {

		Grid g = new Grid();

		Generator.setupTrivialPuzzle(g);

		GraphicsHandler handler = new GraphicsHandler();
		handler.init();
		handler.createButtons(g);

	}

	private static void game(int blanks) {
		Grid g = new Grid();
		Generator.generatePuzzle(g, blanks, 10, (blanks > 50));

		GraphicsHandler handler = new GraphicsHandler();
		handler.init();
		handler.createButtons(g);
	}

	private static void launchMenu() {
		GraphicsHandler handler = new GraphicsHandler();
		handler.createMenu(handler);

	}

	private static void testGenerator(int blanks) {

		Grid g = new Grid();

		Generator.generatePuzzle(g, blanks, 10, blanks > 50);


		// for(int i=0; i<80; i++) {
		//
		// 	Generator.clearRandomCell(g);
		//
		// 	System.out.println(Generator.hasUniqueSolution(g, 10));
		//
		// }

		Solver s = new Solver();
		s.solve(g, 15, true, 0);

		// GraphicsHandler handler = new GraphicsHandler();
		// handler.init();
		// handler.createButtons(g);


	}

	private static void testSolver(int blanks) {

		Solver s = new Solver();

		Grid g = new Grid();
		Generator.generateEasyGrid(g, blanks);

		s.solve(g, 300, true, 0);

		System.out.println(g.isSolution());

		// GraphicsHandler handler = new GraphicsHandler();
		// handler.init();
		// handler.createButtons(g);

	}

	private static int parseNumericArg(String[] args, int position) {
		int n;
		if (args.length > 0) {
	    try {
    		n = Integer.parseInt(args[position]);
				return n;
	    } catch (NumberFormatException e) {
	      System.err.println("Argument" + args[position] + " must be an integer.");
	      System.exit(1);
	    }
		}
		return -1;
	}

	public static void main(String[] args) {
		int blanks = parseNumericArg(args, 0);
		// runTestGame();
		// testGenerator(blanks);
		// game(blanks);
		launchMenu();
		// testSolver(blanks);
	}
}

class Generator {

	public static boolean hasUniqueSolution(Grid g, int iterations) {
		assert (iterations >= 0);
		if(g.isSolution()) {
			return true;
		}

		boolean solvable;

		Solver s = new Solver();

		Grid solution1 = new Grid();
		Grid solution2;
		g.copyTo(solution1);

		solution1.makeAllImmutable(true);

		// Find the first solution
		solvable = s.solve(solution1, 0, false, 30000);
		if(!solvable) {
			// System.out.println("Failed to solve first time");
			return false;
		}

		// Look for a different solution
		for(int i=0; i<iterations; i++) {
			solution2 = new Grid();
			g.copyTo(solution2);
			solution2.makeAllImmutable(true);

			solvable = s.solve(solution2, 0, false, 30000);
			if(!solvable) {
				// System.out.println("Failed to solve");
				return false;
			}
			if(!solution2.isSolution()) {
				System.out.println("WARNING: PRODUCED INVALID SOLUTION");
				continue;
			}
			if(!solution1.equals(solution2)) {
				return false;
			}
		}
		return true;

	}

	public static int[] shift(int[] row, int offset) {
		int[] newRow = new int[9];
		// Deep copy, sub 1 to go from [1,9] to [0,8]
		// Add offset, take mod 9 to get back to [0,8]
		// Add 1 back to return to [1,9]
		for(int i=0; i<9; i++) {
			newRow[i] = (((row[i] - 1) + offset) % 9) + 1;
		}
		return newRow;
	}

	// Based on https://gamedev.stackexchange.com/questions/56149/how-can-i-generate-sudoku-puzzles/138228#138228
	public static void generateSolvedGrid(Grid g, boolean immutable) {
		int[][] values = new int[9][9];

		for(int i=0; i<9; i++) {
			values[0][i] = i+1;
		}
		// values[0] = IntStream.range(1,10).toArray(); // 1-9
		values[1] = shift(values[0], 3);
		values[2] = shift(values[1], 3);

		values[3] = shift(values[2], 1);
		values[4] = shift(values[3], 3);
		values[5] = shift(values[4], 3);

		values[6] = shift(values[5], 1);
		values[7] = shift(values[6], 3);
		values[8] = shift(values[7], 3);

		g.setupGrid(values, immutable, false);

	}

	public static void generatePuzzle(Grid g, int blanks, int runs, boolean relaxUniqueness) {
		assert ((blanks >= 0) && (blanks <= 64));

		generateSolvedGrid(g, true);
		obscure(g, 100);

		int x, y, value, iterations;
		boolean uniqueSolution;

		for(int i=0; i<blanks; i++) {
			iterations = 0;
			do {


				do {
					x = ThreadLocalRandom.current().nextInt(0, 9);
					y = ThreadLocalRandom.current().nextInt(0, 9);
					value = g.getCellValue(x, y);
				} while(value == 0);

				g.setCellImmutability(x, y, false);
				g.forceCellValue(x, y, 0);

				uniqueSolution = hasUniqueSolution(g, runs);

				if((!uniqueSolution) && relaxUniqueness && (i > 50)) {
					System.out.println("RELAXING UNIQUENESS REQUIREMENT");
					uniqueSolution = true;
				}

				// Backtrack
				if(!uniqueSolution) {
					g.forceCellValue(x, y, value);
					g.setCellImmutability(x, y, true);
				}

				iterations++;
				if(iterations > 100) {
					System.out.println("FAILED TO GENERATE");
					g.clear();
					generatePuzzle(g, blanks, runs, relaxUniqueness);
					return;
				}

			} while(!uniqueSolution);

		}

	}

	public static void generateEasyGrid(Grid g, int blanks) {
		assert (blanks >= 0 && blanks <= 81);
		generateSolvedGrid(g, true);
		obscure(g, 25);
		for(int i=0; i<blanks; i++) {
			clearRandomCell(g);
		}
	}

	private static void swapColumns(Grid g, int col1, int col2) {
		int temp;
		// Iterate over column, swapping each value individually
		for(int i=0; i<9; i++) {
			temp = g.getCellValue(col1, i);
			g.forceCellValue(col1, i, g.getCellValue(col2, i));
			g.forceCellValue(col2, i, temp);
		}
	}

	private static void swapRows(Grid g, int row1, int row2) {
		int temp;
		// Iterate over row, swapping each value individually
		for(int i=0; i<9; i++) {
			temp = g.getCellValue(i, row1);
			g.forceCellValue(i, row1, g.getCellValue(i, row2));
			g.forceCellValue(i, row2, temp);
		}
	}

	// Swap two columns within a "chunk", e.g. columns 0 and 2, or columns 7 and 8. Chunks are: | 0 1 2 | 3 4 5 | 6 7 8 |
	// This preserves the validity of the grid
	private static void swapRandomColumns(Grid g, int n) {
		int c1, c2, chunk;
		chunk = ThreadLocalRandom.current().nextInt(0, 3);
		chunk *= 3; // will be 0, 3 or 6
		for(int i=0; i<n; i++) {
			c1 = ThreadLocalRandom.current().nextInt(chunk, chunk+3);
			do {
				c2 = ThreadLocalRandom.current().nextInt(chunk, chunk+3);
			} while (c1 == c2);
			swapColumns(g, c1, c2);
		}
	}

	// Same as swapRandomColumns but for rows
	private static void swapRandomRows(Grid g, int n) {
		int r1, r2, chunk;
		chunk = ThreadLocalRandom.current().nextInt(0, 3);
		chunk *= 3; // will be 0, 3 or 6
		for(int i=0; i<n; i++) {
			r1 = ThreadLocalRandom.current().nextInt(chunk, chunk+3);
			do {
				r2 = ThreadLocalRandom.current().nextInt(chunk, chunk+3);
			} while (r1 == r2);
			swapRows(g, r1, r2);
		}
	}

	private static void obscure(Grid g, int swaps) {
		shuffleDigits(g);
		swapRandomColumns(g, swaps);
		swapRandomRows(g, swaps);
	}

	// Randomly swap roles of digits, e.g. 1s becomes 7s, 5s become 3s
	private static void shuffleDigits(Grid g) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i=1; i<10; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		int value;
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				value = g.getCellValue(i, j);
				if(value != 0) {
					g.forceCellValue(i, j, list.get(value-1));
				}
			}
		}
	}

	public static void clearRandomCell(Grid g) {
		int x,y;
		// If we can't find one in 500 tries the grid's probably empty
		for(int i=0; i<500; i++) {
			x = ThreadLocalRandom.current().nextInt(0, 9);
			y = ThreadLocalRandom.current().nextInt(0, 9);
			if(g.getCellValue(x, y) == 0) {
				continue;
			}
			g.setCellImmutability(x, y, false);
			g.setCellValue(x, y, 0);
			break;
		}
	}

	public static void fillRandomCell(Grid g) {
		int x,y,value;
		while(true) {
			x = ThreadLocalRandom.current().nextInt(0, 9);
			y = ThreadLocalRandom.current().nextInt(0, 9);
			value = ThreadLocalRandom.current().nextInt(1, 10);
			if(g.getCellValue(x, y) != 0) {
				continue;
			}
			if(!Utilities.rowClash(g, x, y, value) && !Utilities.colClash(g, x, y, value) && !Utilities.boxClash(g, x, y, value)) {
				g.setCellValue(x, y, value);
				g.setCellImmutability(x, y, true);
				break;
			}
		}
	}

	public static void setupTrivialPuzzle(Grid g) {
		for(int i=0; i<20; i++) {
			fillRandomCell(g);
		}
	}

}

class History {

	class Node {
		public int location;
		public int value;
		public Node parent;
		public ArrayList<Node> children;

		public Node(int location, int value, Node parent) {
			this.location = location;
			this.value = value;
			this.parent = parent;
			this.children = new ArrayList<Node>();
		}

		public void addChild(Node n) {
			children.add(n);
		}

		public ArrayList<Node> getChildren() {
			return children;
		}

	}

	private Node root;
	private Node ptr;

	public History() {
		root = new Node(-1, -1, null);
		ptr = root;
	}

	public void addNode(int location, int value) {
		Node n = new Node(location, value, ptr);
		ptr.addChild(n);
		ptr = n;

		// System.out.println("Adding (loc: "+Integer.toString(n.location)+", val="+Integer.toString(n.value)+") as child of "+Integer.toString((n.parent).location)+", val="+Integer.toString((n.parent).value)+")");
	}

	public void moveUp() {
		// System.out.println("Backtracking from: "+Integer.toString(ptr.location)+" to "+Integer.toString((ptr.parent).location));
		ptr = ptr.parent;
	}

	public ArrayList<Integer> previousGuesses(int loc) {
		// while(ptr.location != loc) {
		// 	moveUp();
		// }
		// Get to parent of relevant cell, e.g. root node for cell 0
		moveUp();

		ArrayList<Integer> guesses = new ArrayList<Integer>();

		ArrayList<Node> brothers = ptr.getChildren();
		for(Node n : brothers) {
			guesses.add(n.value);
		}

		// System.out.println("Previous values of"+Integer.toString((ptr.location))+" are: ");
		// for(Integer i: guesses) {
		// 	System.out.print(Integer.toString(i)+" ");
		// }
		// System.out.println();

		return guesses;

	}
}

class Solver {
	private int pointer;
	private int steps;

	public Solver() {
		pointer = 0;
		steps = 0;
	}

	private Cell cellAtPointer(Grid g) {
		// System.out.println(Integer.toString(pointer));
		return g.getCell(pointer / 9, pointer % 9);
	}

	private Cell findFirstBlank(Grid g) {
		Cell c;
		while(pointer < 81) {
			c = cellAtPointer(g);
			if(c.getValue() == 0) {
				return c;
			}
			pointer++;
		}
		pointer = 0;
		return null;
	}

	private Cell findNextBlank(Grid g) {
		steps++;
		Cell c;
		while(pointer < 80) {
			pointer++;
			c = cellAtPointer(g);
			if(c.getValue() == 0) {
				return c;
			}
		}
		pointer = 0;
		return null;
	}

	private Cell backtrack(Grid g) {
		steps++;
		Cell c;
		while(pointer > 0) {
			pointer--;
			c = cellAtPointer(g);
			if(!c.getImmutable()) {
				return c;
			}
		}
		return null;
	}


	// Find a solution using backtracking
	public boolean solve(Grid g, int pauses, boolean graphics, int stepLimit) {
		GraphicsHandler handler = new GraphicsHandler();
		if(graphics) {
			handler.init();
			handler.createButtons(g);

			handler.forceFullUpdate(false);
		}

		History hist = new History();
		steps = 0;

		Cell c = findFirstBlank(g);
		if(c == null) {
			if(g.isSolution()) {
				return true;
			} else {
				System.out.println("Couldn't find first blank");
				return false;
			}
		}

		int guess;
		int clashes = g.getNumberOfClashes();
		ArrayList<Integer> guesses = new ArrayList<Integer>();

		while(!g.isSolution()) {

			// Try all numbers 1-9 as values of cell, until we avoid clash

			if(stepLimit > 0) {
				if(steps == stepLimit) {
					// System.out.println(Integer.toString(steps)+" steps reahced");
					return false;
				}
			}

			do {
				if(guesses.size() == 9) {
					break;
				}
				// Find a new guess, at random
				do {
					guess = ThreadLocalRandom.current().nextInt(1, 10);
				} while(guesses.contains(guess));

				guesses.add(guess);
				// Fill the guess in
				c.setValue(guess);
				g.updateClash(pointer / 9, pointer % 9);

				if(graphics) {
					g.updateCompleteness(pointer / 9, pointer % 9);
					handler.forceFullUpdate(false);
					try {
					    Thread.sleep(pauses);
					}
					catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
				}

				// See if it conflicts

				clashes = g.getNumberOfClashes();

			} while(clashes > 0);

			guesses.clear();

			if(clashes > 0) {
				// All guesses bad, have to backtrack

				c.setValue(0);
				g.updateClash(pointer / 9, pointer % 9);

				if(graphics) {
					g.updateCompleteness(pointer / 9, pointer % 9);
					handler.forceFullUpdate(false);
					try {
					    Thread.sleep(pauses);
					}
					catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
				}

				c = backtrack(g);
				guesses = hist.previousGuesses(pointer);
				if(c == null) {
					System.out.println("Failed to backtrack");
					return false;
				}


			} else {
				hist.addNode(pointer, c.getValue());

				c = findNextBlank(g);
				// System.out.println(Integer.toString(pointer)+": "+(firstBlank.getValue()));
			}

			if((c == null) && !(g.isSolution())) {
				// Couldn't find another cell, puzzle is unsolvable
				System.out.println("Couldn't find next blank");
				return false;
			}
		}

		return true;
	}


}
