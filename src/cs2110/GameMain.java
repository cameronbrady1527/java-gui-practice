package cs2110;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * Main class for Click-a-Dot game. Creates window with game board, score label, start button, and
 * sliders for target size and speed.
 */
public class GameMain {

    /**
     * Start the application.
     */
    public static void main(String[] args) {
        // Creation of window must occur on Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    /**
     * Create application window.
     * <ul>
     * <li>Window title is "Click-a-Dot"
     * <li>Game board is in center of window, expands to fill window size
     * <li>Score label is at top; text is centered
     * <li>Start button is at bottom
     * <li>Size slider is at right
     * <li>Speed slider is at left
     * </ul>
     * Window should be disposed when closed, and all game tasks stopped. This should be sufficient
     * for application to shut down gracefully.
     */
    private static void createAndShowGUI() {
        // Create frame.
        JFrame frame = new JFrame("Click-a-Dot");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create and add game board.
        GameComponent game = new GameComponent();
        frame.add(game);

        // Create and add score label.
        JLabel scoreLabel = new JLabel("Score: " + game.getScore(),
                SwingConstants.CENTER);
        scoreLabel.setFont(scoreLabel.getFont().deriveFont(24.0f));
        frame.add(scoreLabel, BorderLayout.PAGE_START);

        // Create and add start button.
        JButton startButton = new JButton("Start");
        startButton.setFont(startButton.getFont().deriveFont(20.0f));
        frame.add(startButton, BorderLayout.PAGE_END);

        // Create and add vertical size slider.
        // Allowed target radii are 1..50 (query game board for initial radius).
        JSlider sizeSlider = new JSlider(JSlider.VERTICAL, 1, 50,
                game.getTargetRadius());
        addSliderLabels(sizeSlider, "Small", "Large");
        // Place slider in panel with label and padding.
        frame.add(makeSliderPanel(sizeSlider, "Size"), BorderLayout.WEST);

        // Create and add vertical speed slider.
        // Allowed target durations are 250..2000 ms (query game board for
        // initial duration).
        JSlider speedSlider = new JSlider(JSlider.VERTICAL, 250, 2000,
                game.getTargetTimeMillis());
        addSliderLabels(speedSlider, "Fast", "Slow");
        speedSlider.setInverted(true);
        // Place slider in panel with label and padding.
        frame.add(makeSliderPanel(speedSlider, "Speed"), BorderLayout.EAST);

        // Add menu bar
        JMenuItem saveItem = new JMenuItem("Save score");
        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");

        file.add(saveItem);
        file.add(exitItem);

        menuBar.add(file);

        frame.setJMenuBar(menuBar);


        ////////////////
        // Controller
        ////////////////

        // When the start button is clicked, start a new game.
        startButton.addActionListener((e) -> game.startGame());

        // When the game's score changes, update the score label.
        game.addPropertyChangeListener("GameScore", (e) ->
                scoreLabel.setText("Score: " + game.getScore()));


        // When size slider is adjusted, update target radius in game.
        sizeSlider.addChangeListener((e) -> game.setTargetRadius(sizeSlider.getValue()));


        // When speed slider is adjusted, update target duration in game.
        speedSlider.addChangeListener((e) -> game.setTargetTimeMillis(speedSlider.getValue()));

        // When "Save" menu item is activated, open file dialog and append score
        // to chosen file.
        saveItem.addActionListener((ActionEvent ae) -> saveScore(frame, game.getScore()));

        // When "Exit" menu item is activated, dispose of the JFrame.
        exitItem.addActionListener((ActionEvent ae) -> frame.dispose());

        // Stop game when window is closed to ensure that game background tasks
        // do not hold up application shutdown.
        // Use an anonymous subclass of WindowAdapter to avoid having to handle
        // other window events.
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                game.stopGame();
            }
        });

        // Compute ideal window size and show window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Label `slider`'s minimum value with `minLabel` and its maximum value with `maxLabel`.
     */
    private static void addSliderLabels(JSlider slider, String minLabel,
            String maxLabel) {
        Hashtable<Integer, JLabel> labels = new Hashtable<>();

        labels.put(slider.getMinimum(), new JLabel(minLabel));
        labels.put(slider.getMaximum(), new JLabel(maxLabel));
        slider.setLabelTable(labels);

        slider.setPaintLabels(true);
    }

    /**
     * Place `slider` in a new padded panel with top label `title` and return the panel.
     */
    private static JComponent makeSliderPanel(JSlider slider, String title) {
        JPanel sliderPanel =  new JPanel(new BorderLayout());

        sliderPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        JLabel sliderTitle = new JLabel(title);
        sliderTitle.setFont(sliderTitle.getFont().deriveFont(16.0f));
        sliderPanel.add(sliderTitle, BorderLayout.PAGE_START);

        sliderPanel.add(slider, BorderLayout.CENTER);

        return sliderPanel;
    }

    /**
     * Append a line containing `score` to a user-selected file, using `frame` as the parent of any
     * dialogs. Show an error dialog if a problem occurs when writing the file.
     */
    private static void saveScore(JFrame frame, int score) {
        final JFileChooser fc = new JFileChooser();

        int returnVal = fc.showSaveDialog(fc);
        File file = fc.getSelectedFile();

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter out =
                    new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {

                out.println(score);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame,
                        e, e.getClass().toString(),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
