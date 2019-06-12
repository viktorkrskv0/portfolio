package il.ac.tau.cs.sw1.trivia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TriviaGUI {

	private static final int MAX_ERRORS = 3;
	private Shell shell;
	private Label scoreLabel;
	private Composite questionPanel;
	private Font boldFont;
	private String lastAnswer = "";
	private static int j; // question number counter
	public static String rightAnswer;
	public static String line;
	public static int errorCounter = 0;

	public void open() {
		createShell();
		runApplication();
	}

	/**
	 * Creates the widgets of the application main window
	 */
	private void createShell() {
		Display display = Display.getDefault();
		shell = new Shell(display);
		shell.setText("Trivia");

		// window style
		Rectangle monitor_bounds = shell.getMonitor().getBounds();
		shell.setSize(new Point(monitor_bounds.width / 3, monitor_bounds.height / 4));
		shell.setLayout(new GridLayout());

		FontData fontData = new FontData();
		fontData.setStyle(SWT.BOLD);
		boldFont = new Font(shell.getDisplay(), fontData);

		// create window panels
		createFileLoadingPanel();
		createScorePanel();
		createQuestionPanel();
	}

	/**
	 * Creates the widgets of the form for trivia file selection
	 */
	private void createFileLoadingPanel() {
		final Composite fileSelection = new Composite(shell, SWT.NULL);
		fileSelection.setLayoutData(GUIUtils.createFillGridData(1));
		fileSelection.setLayout(new GridLayout(4, false));

		final Label label = new Label(fileSelection, SWT.NONE);
		label.setText("Enter trivia file path: ");

		// text field to enter the file path
		final Text filePathField = new Text(fileSelection, SWT.SINGLE | SWT.BORDER);
		filePathField.setLayoutData(GUIUtils.createFillGridData(1));

		// "Browse" button
		final Button browseButton = new Button(fileSelection, SWT.PUSH);
		browseButton.setText("Browse");
		browseButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					FileDialog dlg = new FileDialog(shell, SWT.OPEN);

					// DirectoryDialog dlg = new DirectoryDialog(shell);

					// Set the initial filter path according
					// to anything they've selected or typed in
					dlg.setFilterPath(filePathField.getText());

					// Change the title bar text
					dlg.setText("SWT's File Dialog");

					// Customizable message displayed in the dialog
					// dlg.setMessage("Select a directory");

					// Calling open() will open and run the dialog.
					// It will return the selected directory, or
					// null if user cancels
					String dir = dlg.open();
					if (dir != null) {
						// Set the text box to the new selection
						filePathField.setText(dir);
					}

				}
			}
		});

		// "Play!" button
		final Button playButton = new Button(fileSelection, SWT.PUSH);
		playButton.setText("Play!");
		playButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					String dir = filePathField.getText();
					File f = new File(dir);
					BufferedReader bR = null;
					try {
						bR = new BufferedReader(new FileReader(f));
					} catch (FileNotFoundException e2) {
						System.out.println("file not found exception");
						e2.printStackTrace();
					}

					List<String> qANDa = new ArrayList<String>();// questions
																	// and
																	// answers

					String lineSaving;
					try {
						while ((lineSaving = bR.readLine()) != null) {
							qANDa.add(lineSaving);
						}
					} catch (IOException e1) {
						System.out.println("IOException");
						e1.printStackTrace();
					}

					Collections.shuffle(qANDa);
					scoreLabel.setText("0");

					j = 0;
					gameLoopManager(qANDa);

				}

			}

		});
	}

	/**
	 * the game loop, goes over the lines of the text file and updates the
	 * question to be the next line
	 */
	public void gameLoopManager(List<String> qANDa) {
		String[] temp = null;
		if (j < qANDa.size() && errorCounter < 3) {
			temp = qANDa.get(j).split("	");
			List<String> answers = new ArrayList<String>();
			for (int i = 1; i <= 4; i++) {
				answers.add(temp[i]);
			}
			rightAnswer = answers.get(0);
			Collections.shuffle(answers);
			updateQuestionPanel(temp[0], answers, qANDa);
		} else{
			GUIUtils.showInfoDialog(shell,"GAME OVER","you final score is "+scoreLabel.getText()+" after "+ (j+1)+" questions");
			shell.dispose();
		}

	}

	/**
	 * Creates the panel that displays the current score
	 */
	private void createScorePanel() {
		Composite scorePanel = new Composite(shell, SWT.BORDER);
		scorePanel.setLayoutData(GUIUtils.createFillGridData(1));
		scorePanel.setLayout(new GridLayout(2, false));

		final Label label = new Label(scorePanel, SWT.NONE);
		label.setText("Total score: ");

		// The label which displays the score; initially empty
		scoreLabel = new Label(scorePanel, SWT.NONE);
		scoreLabel.setLayoutData(GUIUtils.createFillGridData(1));
	}

	/**
	 * Creates the panel that displays the questions, as soon as the game
	 * starts. See the updateQuestionPanel for creating the question and answer
	 * buttons
	 */
	private void createQuestionPanel() {
		questionPanel = new Composite(shell, SWT.BORDER);
		questionPanel.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		questionPanel.setLayout(new GridLayout(2, true));

		// Initially, only displays a message
		Label label = new Label(questionPanel, SWT.NONE);
		label.setText("No question to display, yet.");
		label.setLayoutData(GUIUtils.createFillGridData(2));
	}

	/**
	 * Serves to display the question and answer buttons
	 */
	private void updateQuestionPanel(String question, List<String> answers, List<String> qANDa) {

		// clear the question panel
		Control[] children = questionPanel.getChildren();
		for (Control control : children) {
			control.dispose();
		}

		// create the instruction label
		Label instructionLabel = new Label(questionPanel, SWT.CENTER | SWT.WRAP);
		instructionLabel.setText(lastAnswer + "Answer the following question:");
		instructionLabel.setLayoutData(GUIUtils.createFillGridData(2));

		// create the question label
		Label questionLabel = new Label(questionPanel, SWT.CENTER | SWT.WRAP);
		questionLabel.setText(question);
		questionLabel.setFont(boldFont);
		questionLabel.setLayoutData(GUIUtils.createFillGridData(2));

		// create the answer buttons
		for (int i = 0; i < 4; i++) {
			Button answerButton = new Button(questionPanel, SWT.PUSH | SWT.WRAP);
			answerButton.setText(answers.get(i));
			answerButton.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event e) {
					switch (e.type) {
					case SWT.Selection:
						String answerValue = answerButton.getText();
						int score = Integer.parseInt(scoreLabel.getText());
						if (TriviaGUI.rightAnswer == answerValue) {
							score = score + 3;
							scoreLabel.setText(Integer.toString(score));
							j++;// update the question number counter
							gameLoopManager(qANDa);
						} else {
							score = score - 1;
							scoreLabel.setText(Integer.toString(score));
							j++;
							errorCounter++;
							gameLoopManager(qANDa);

						}
					}
				}

			});
			GridData answerLayoutData = GUIUtils.createFillGridData(1);
			answerLayoutData.verticalAlignment = SWT.FILL;
			answerButton.setLayoutData(answerLayoutData);
		}

		// create the "Pass" button to skip a question
		Button passButton = new Button(questionPanel, SWT.PUSH);
		passButton.addListener(SWT.Selection, new Listener(){

			@Override
			public void handleEvent(Event e) {
				switch(e.type){
				case SWT.Selection:
					j++;
					gameLoopManager(qANDa);
				}
			}
			
		});
		passButton.setText("Pass");
		GridData data = new GridData(GridData.CENTER, GridData.CENTER, true, false);
		data.horizontalSpan = 2;
		passButton.setLayoutData(data);

		// two operations to make the new widgets display properly
		questionPanel.pack();
		questionPanel.getParent().layout();
	}

	/**
	 * Opens the main window and executes the event loop of the application
	 */
	private void runApplication() {
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		boldFont.dispose();
	}
}
