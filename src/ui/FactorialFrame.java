package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;

import factorialCalc.FactorialArraySizeSetter;
import factorialCalc.FactorialCalc;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.prefs.Preferences;

public class FactorialFrame extends JFrame {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField xInput;
    private JTextPane resultPane;
    private JButton btnCalculate, btnBack, btnPausePlay, btnStop, btnPrefs,
            btnSave, btnCopy, btnSound;
    private StyledDocument eventDoc;
    private SimpleAttributeSet attr;
    private static ImageIcon pauseIcon, resumeIcon, stopIcon, prefsIcon,
            saveIcon, appIcon, copyIcon, soundOnIcon, soundOffIcon;

    private String saveLoc = "SAVELOC";
    private String openPrefs = "PREFSOPEN";
    private String alwaysAskForSaveLoc = "ALWAYSASKFORSAVELOC";
    private String soundOn = "SOUNDON";

    private String startSoundVol = "SOUNDVOLSTART";
    private String finishSoundVol = "SOUNDVOLFINISH";
    private String pauseSoundVol = "SOUNDVOLPAUSE";
    private String resumeSoundVol = "SOUNDVOLRESUME";
    private String stopSoundVol = "SOUNDVOLSTOP";
    private String errorSoundVol = "SOUNDVOLERROR";

    private String startSound = "/ui/Start Sound.wav";
    private String finishSound = "/ui/Finish Sound.wav";
    private String pauseSound = "/ui/Pause Sound.wav";
    private String resumeSound = "/ui/Resume Sound.wav";
    private String stopSound = "/ui/Stop Sound.wav";
    private String errorSound = "/ui/Error Sound.wav";

    private Preferences prefs = Preferences
            .userNodeForPackage(ui.ExponentFrame.class);
    private MyChooser fileChooser;

    private Clip clip;

    private static int arraySize = 0;
    private static boolean isStarted = false;
    private static boolean isStopped = false;
    private static boolean isPaused = false;
    private static long calcStartTime = 0;
    private static long calcEndTime = 0;
    private static long calcRunTime = 0;
    private static int x;

    private PrintWriter out;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FactorialFrame frame = new FactorialFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public FactorialFrame() {

        appIcon = new ImageIcon(
                FactorialFrame.class.getResource("/ui/PowArray Icon.png"));

        setResizable(false);
        setIconImage(appIcon.getImage());
        setTitle("PowArray v1.01 - Factorial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 700);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblEnterAnInteger = new JLabel("Enter an Integer:");
        lblEnterAnInteger
                .setToolTipText("Enter a positive integer less than or equal to 2000000"
                        + ", and then either press enter or click on calculate.");
        lblEnterAnInteger.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblEnterAnInteger.setHorizontalAlignment(SwingConstants.CENTER);
        lblEnterAnInteger.setBounds(140, 20, 160, 33);
        contentPane.add(lblEnterAnInteger);

        resultPane = new JTextPane();

        resultPane.setEditable(false);
        resultPane.setBounds(52, 210, 337, 365);
        resultPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JScrollPane scrollPane = new JScrollPane(resultPane);
        scrollPane.setBounds(52, 210, 337, 365);
        DefaultCaret caret = (DefaultCaret) resultPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        contentPane.add(scrollPane);

        eventDoc = resultPane.getStyledDocument();
        attr = new SimpleAttributeSet();
        StyleConstants.setBold(attr, true);

        btnCalculate = new JButton("Calculate");
        btnCalculate
                .setToolTipText("Enter a positive integer less than or equal to 2000000"
                        + ", and then either press enter or click on calculate.");

        // JTextPane Output Stream
        // PrintStream oldOut = System.out;
        PrintStream printStream = new PrintStream(new OutputStream() {

            @Override
            public void write(int b) throws IOException {

                try {

                    Document doc = resultPane.getDocument();

                    doc.insertString(doc.getLength(), String.valueOf((char) b),
                            null);

                } catch (BadLocationException e) {

                    e.printStackTrace();
                }

            }

        });
        System.setOut(printStream);

        xInput = new JTextField();
        xInput.setToolTipText("Enter a positive integer less than or equal to 2000000"
                + ", and then either press enter or click on calculate.");

        xInput.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                int location = e.getKeyCode();

                if (location == KeyEvent.VK_ENTER) {

                    Calculate();

                }

            }
        });
        xInput.setBounds(162, 59, 116, 22);
        contentPane.add(xInput);
        xInput.setColumns(10);

        JLabel lblResult = new JLabel("Result:");
        lblResult.setHorizontalAlignment(SwingConstants.CENTER);
        lblResult.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblResult.setBounds(175, 170, 90, 33);
        contentPane.add(lblResult);

        btnCalculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Calculate();

            }
        });
        btnCalculate.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnCalculate.setBounds(160, 110, 120, 35);
        contentPane.add(btnCalculate);

        btnBack = new JButton("Back");
        btnBack.setToolTipText("Return to the main window");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                MainGUI MainMenu = new MainGUI();
                MainMenu.frmPowerfulCalculator.setVisible(true);
                dispose();

            }
        });
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnBack.setBounds(170, 592, 100, 35);
        contentPane.add(btnBack);

        stopIcon = new ImageIcon(
                FactorialFrame.class.getResource("/ui/Stop Icon.png"));

        btnStop = new JButton("Stop");
        btnStop.setMargin(new Insets(1, 6, 0, 9));
        btnStop.setIcon(stopIcon);
        btnStop.setEnabled(false);
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                FactorialCalc.Stop();
                isStopped = true;

            }
        });
        btnStop.setToolTipText("Abort the calculation while it is in progress");
        btnStop.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnStop.setBounds(282, 592, 100, 35);
        contentPane.add(btnStop);

        pauseIcon = new ImageIcon(
                FactorialFrame.class.getResource("/ui/Pause Icon.png"));
        resumeIcon = new ImageIcon(
                FactorialFrame.class.getResource("/ui/ResumePlay Icon.png"));

        btnPausePlay = new JButton("Pause");
        btnPausePlay.setMargin(new Insets(2, 6, 0, 9));
        btnPausePlay.setIcon(pauseIcon);
        btnPausePlay.setEnabled(false);
        btnPausePlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (isStarted) {

                    if (!isPaused) {

                        if (prefs.getBoolean(soundOn, true)) {
                            Runnable r = new Runnable() {
                                public void run() {
                                    btnPausePlay.setEnabled(false);
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    btnPausePlay.setEnabled(true);
                                }
                            };
                            Thread timer = new Thread(r);
                            timer.start();
                        }

                        btnPausePlay.setIcon(resumeIcon);
                        btnPausePlay.setMargin(new Insets(0, 6, 0, 7));
                        btnPausePlay.setFont(new Font("Tahoma", Font.BOLD, 14));
                        btnPausePlay.setText("Resume");
                        btnPausePlay.setToolTipText("Resume the calculation.");
                        isPaused = true;
                        btnStop.setEnabled(false);

                        FactorialCalc.Pause();
                        // Play Pause Sound
                        if (prefs.getBoolean(soundOn, true)) {
                            playClip(pauseSound, 2);
                        }

                        StyleConstants.setForeground(attr,
                                Color.decode("#DBB91F"));
                        try {
                            eventDoc.insertString(
                                    eventDoc.getLength(),
                                    "Calculation paused at: "
                                            + System.getProperty("line.separator")
                                            + new java.sql.Timestamp(Calendar
                                                    .getInstance().getTime()
                                                    .getTime())
                                            + System.getProperty("line.separator")
                                            + System.getProperty("line.separator"),
                                    attr);
                        } catch (BadLocationException e1) {
                            e1.printStackTrace();
                        }

                    } else {

                        if (prefs.getBoolean(soundOn, true)) {
                            Runnable r = new Runnable() {
                                public void run() {
                                    btnPausePlay.setEnabled(false);
                                    btnStop.setEnabled(false);
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    btnPausePlay.setEnabled(true);
                                    btnStop.setEnabled(true);
                                }
                            };
                            Thread timer = new Thread(r);
                            timer.start();
                        }

                        btnPausePlay.setIcon(pauseIcon);
                        btnPausePlay.setMargin(new Insets(2, 6, 0, 9));
                        btnPausePlay.setFont(new Font("Tahoma", Font.BOLD, 16));
                        btnPausePlay.setText("Pause");
                        btnPausePlay
                                .setToolTipText("Pause the calculation so that it can be resumed later.");
                        isPaused = false;

                        FactorialCalc.Resume();
                        // Play Resume Sound
                        if (prefs.getBoolean(soundOn, true)) {
                            playClip(resumeSound, 3);
                        }

                        try {
                            eventDoc.insertString(
                                    eventDoc.getLength(),
                                    "Calculation resumed at: "
                                            + System.getProperty("line.separator")
                                            + new java.sql.Timestamp(Calendar
                                                    .getInstance().getTime()
                                                    .getTime())
                                            + System.getProperty("line.separator")
                                            + System.getProperty("line.separator"),
                                    attr);
                        } catch (BadLocationException e1) {
                            e1.printStackTrace();
                        }

                    }

                }

            }
        });
        btnPausePlay
                .setToolTipText("Pause the calculation so that it can be resumed later.");
        btnPausePlay.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnPausePlay.setBounds(58, 592, 100, 35);
        contentPane.add(btnPausePlay);

        prefsIcon = new ImageIcon(
                FactorialFrame.class.getResource("/ui/Preferences Icon.png"));

        btnPrefs = new JButton(prefsIcon);
        btnPrefs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (!prefs.getBoolean(openPrefs, false)) {
                    prefs.putBoolean(openPrefs, true);
                    PreferencesFrame prefsFrame = new PreferencesFrame();
                    prefsFrame.setVisible(true);
                }

            }
        });

        btnPrefs.setToolTipText("View and set preferences");
        btnPrefs.setMargin(new Insets(1, 0, 1, -2));
        btnPrefs.setBounds(335, 37, 40, 40);
        contentPane.add(btnPrefs);

        saveIcon = new ImageIcon(
                FactorialFrame.class.getResource("/ui/Save Icon.png"));

        btnSave = new JButton(saveIcon);
        btnSave.setEnabled(false);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                chooseFile();

            }
        });
        btnSave.setToolTipText("Save the current result to a text file");
        btnSave.setMargin(new Insets(1, 0, 1, 0));
        btnSave.setBounds(335, 90, 40, 40);
        contentPane.add(btnSave);

        copyIcon = new ImageIcon(
                ExponentFrame.class.getResource("/ui/Copy Icon.png"));

        btnCopy = new JButton(copyIcon);
        btnCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String output = resultPane.getText();
                StringSelection stringSelection = new StringSelection(output);
                Clipboard clipboard = Toolkit.getDefaultToolkit()
                        .getSystemClipboard();
                clipboard.setContents(stringSelection, null);

            }
        });
        btnCopy.setToolTipText("Copy the current result to your operating system's clipboard");
        btnCopy.setMargin(new Insets(1, 0, 1, 0));
        btnCopy.setEnabled(false);
        btnCopy.setBounds(335, 143, 40, 40);
        contentPane.add(btnCopy);

        soundOnIcon = new ImageIcon(
                ExponentFrame.class.getResource("/ui/Sound Unmute Icon.png"));

        soundOffIcon = new ImageIcon(
                ExponentFrame.class.getResource("/ui/Sound Mute Icon.png"));

        if (prefs.getBoolean(soundOn, true)) {
            btnSound = new JButton(soundOnIcon);
            btnSound.setToolTipText("Click to mute sound notifications (sound is currently on)");
        } else {
            btnSound = new JButton(soundOffIcon);
            btnSound.setToolTipText("Click to unmute sound notifications (sound is currently off)");
        }

        btnSound.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (prefs.getBoolean(soundOn, true)) {

                    prefs.putBoolean(soundOn, false);
                    btnSound.setIcon(soundOffIcon);
                    btnSound.setToolTipText("Click to unmute sound notifications (sound is currently off)");

                } else {

                    prefs.putBoolean(soundOn, true);
                    btnSound.setIcon(soundOnIcon);
                    btnSound.setToolTipText("Click to mute sound notifications (sound is currently on)");

                }

            }
        });
        btnSound.setMargin(new Insets(1, 0, 1, -2));
        btnSound.setBounds(66, 37, 40, 40);
        contentPane.add(btnSound);

    }

    public void Calculate() {

        try {

            x = Integer.parseInt(xInput.getText());
            resultPane.setText("");

            StyleConstants.setForeground(attr, Color.decode("#15AD1A"));

            arraySize = FactorialArraySizeSetter.arraySizeSetter(x);

            Runnable r = new Runnable() {
                public void run() {

                    isStarted = true;
                    isStopped = false;
                    xInput.setEnabled(false);
                    btnSave.setEnabled(false);
                    btnCalculate.setEnabled(false);
                    btnBack.setEnabled(false);
                    btnStop.setEnabled(true);
                    btnPausePlay.setEnabled(true);
                    btnCopy.setEnabled(false);

                    FactorialCalc.Reinitialize();

                    // Play Start Sound
                    if (prefs.getBoolean(soundOn, true)) {
                        playClip(startSound, 0);
                    }

                    try {
                        eventDoc.insertString(
                                eventDoc.getLength(),
                                "Calculation started at: "
                                        + System.getProperty("line.separator")
                                        + new java.sql.Timestamp(Calendar
                                                .getInstance().getTime()
                                                .getTime())
                                        + System.getProperty("line.separator")
                                        + System.getProperty("line.separator"),
                                attr);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }

                    calcStartTime = System.currentTimeMillis();
                    FactorialCalc.factorialize(x, arraySize);

                    if (!isStopped) {

                        calcEndTime = System.currentTimeMillis();
                        calcRunTime = calcEndTime - calcStartTime;
                        // Play Finish Sound
                        if (prefs.getBoolean(soundOn, true)
                                && calcRunTime > 580) {
                            playClip(finishSound, 1);
                        }

                        try {
                            StyleConstants.setForeground(attr,
                                    Color.decode("#15AD1A"));
                            eventDoc.insertString(
                                    eventDoc.getLength(),
                                    "Calculation finished at: "
                                            + System.getProperty("line.separator")
                                            + new java.sql.Timestamp(Calendar
                                                    .getInstance().getTime()
                                                    .getTime()), attr);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                    } else {

                        calcEndTime = System.currentTimeMillis();
                        calcRunTime = calcEndTime - calcStartTime;
                        // Play Stop Sound
                        if (prefs.getBoolean(soundOn, true)
                                && calcRunTime > 580) {
                            playClip(stopSound, 4);
                        }

                        StyleConstants.setForeground(attr, Color.RED);
                        try {
                            eventDoc.insertString(
                                    eventDoc.getLength(),
                                    "Calculation stopped at: "
                                            + System.getProperty("line.separator")
                                            + new java.sql.Timestamp(Calendar
                                                    .getInstance().getTime()
                                                    .getTime()), attr);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }

                        isStarted = false;
                        isStopped = false;

                    }

                    btnStop.setEnabled(false);
                    btnPausePlay.setIcon(pauseIcon);
                    btnPausePlay.setMargin(new Insets(2, 6, 0, 9));
                    btnPausePlay.setFont(new Font("Tahoma", Font.BOLD, 16));
                    btnPausePlay.setText("Pause");
                    btnPausePlay
                            .setToolTipText("Pause the calculation so that it can be resumed later.");
                    isPaused = false;
                    btnPausePlay.setEnabled(false);

                    if (prefs.getBoolean(soundOn, true)) {

                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    xInput.setEnabled(true);
                    btnCalculate.setEnabled(true);
                    btnBack.setEnabled(true);
                    btnCopy.setEnabled(true);
                    btnSave.setEnabled(true);

                }
            };
            Thread calculationOngoing = new Thread(r);
            calculationOngoing.start();

        } catch (NumberFormatException e1) {
            // Play Error Sound
            if (prefs.getBoolean(soundOn, true)) {
                playClip(errorSound, 5);

                Runnable r = new Runnable() {
                    public void run() {
                        btnCalculate.setEnabled(false);
                        xInput.setEnabled(false);
                        btnBack.setEnabled(false);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        btnCalculate.setEnabled(true);
                        xInput.setEnabled(true);
                        btnBack.setEnabled(true);
                    }
                };
                Thread timer = new Thread(r);
                timer.start();
            }
            resultPane.setText("");
            try {
                StyleConstants.setForeground(attr, Color.RED);
                eventDoc.insertString(
                        eventDoc.getLength(),
                        "Please enter a positive integer less than or equal to 2000000, "
                                + "and then either press enter or click on calculate.",
                        attr);
            } catch (BadLocationException e2) {
                e2.printStackTrace();
            }
        }

    }

    private static class MyChooser extends JFileChooser {

        /**
		 * 
		 */
        private static final long serialVersionUID = 1L;

        protected JDialog createDialog(Component parent)
                throws HeadlessException {
            JDialog dlg = super.createDialog(parent);
            dlg.setTitle("Save Result to Text File");
            dlg.setLocation(555, 94);
            dlg.setIconImage(appIcon.getImage());
            return dlg;
        }

    }

    private void playClip(String file, int soundType) {
        try {

            clip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));

            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP)
                        clip.close();

                }
            });

            clip.open(AudioSystem.getAudioInputStream(ExponentFrame.class
                    .getResource(file)));
            float volume = 0;

            // Reduce volume
            // FloatControl min value is -80.0f
            if (soundType == 0) {
                volume = (float) (-(100 - prefs.getInt(startSoundVol, 60)) * 0.6);
                FloatControl gainControl = (FloatControl) clip
                        .getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                volume = prefs.getInt(startSoundVol, 60);
            } else if (soundType == 1) {
                volume = (float) (-(100 - prefs.getInt(finishSoundVol, 60)) * 0.6);
                FloatControl gainControl = (FloatControl) clip
                        .getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                volume = prefs.getInt(startSoundVol, 60);
            } else if (soundType == 2) {
                volume = (float) (-(100 - prefs.getInt(pauseSoundVol, 60)) * 0.6);
                FloatControl gainControl = (FloatControl) clip
                        .getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                volume = prefs.getInt(startSoundVol, 60);
            } else if (soundType == 3) {
                volume = (float) (-(100 - prefs.getInt(resumeSoundVol, 60)) * 0.6);
                FloatControl gainControl = (FloatControl) clip
                        .getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                volume = prefs.getInt(startSoundVol, 60);
            } else if (soundType == 4) {
                volume = (float) (-(100 - prefs.getInt(stopSoundVol, 60)) * 0.6);
                FloatControl gainControl = (FloatControl) clip
                        .getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                volume = prefs.getInt(startSoundVol, 60);
            } else if (soundType == 5) {
                volume = (float) (-(100 - prefs.getInt(errorSoundVol, 60)) * 0.6);
                FloatControl gainControl = (FloatControl) clip
                        .getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                volume = prefs.getInt(startSoundVol, 60);
            }

            if (volume == 0) {
                return;
            }

            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void chooseFile() {

        btnSave.setEnabled(false);

        if (!prefs.getBoolean(alwaysAskForSaveLoc, false)) {

            String pathName = "Timestamp_"
                    + new SimpleDateFormat("yyyy MM dd").format(Calendar
                            .getInstance().getTime()) + "_" + x + "!" + ".txt";

            if (prefs.get(saveLoc, "").equals("")) {

                fileChooser = new MyChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter(".txt",
                        "txt"));
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int status = fileChooser.showDialog(null, "Set");

                if (status == JFileChooser.APPROVE_OPTION) {

                    try {
                        String selectedFileDir = fileChooser.getSelectedFile()
                                .getCanonicalPath();
                        if (Files
                                .exists(fileChooser.getSelectedFile().toPath())) {
                            out = new PrintWriter(fileChooser.getSelectedFile()
                                    + "\\" + pathName, "UTF-8");
                            out.println(resultPane.getText());
                            out.close();
                            prefs.put(saveLoc, selectedFileDir + "\\");
                        } else {
                            prefs.put(saveLoc, "");
                            btnSave.setEnabled(true);
                        }

                    } catch (IOException e1) {
                        e1.printStackTrace();
                        btnSave.setEnabled(true);
                    }
                } else if (status == JFileChooser.CANCEL_OPTION) {

                    btnSave.setEnabled(true);

                }

            } else {

                try {
                    out = new PrintWriter(prefs.get(saveLoc, "") + pathName,
                            "UTF-8");
                    out.println(resultPane.getText());
                    out.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                    prefs.put(saveLoc, "");
                    chooseFile();
                } catch (UnsupportedEncodingException e2) {
                    e2.printStackTrace();
                }

            }

        } else {

            fileChooser = new MyChooser();
            fileChooser
                    .setFileFilter(new FileNameExtensionFilter(".txt", "txt"));

            int status = fileChooser.showSaveDialog(null);

            if (status == JFileChooser.APPROVE_OPTION) {

                try {
                    PrintWriter out = new PrintWriter(
                            fileChooser.getSelectedFile() + ".txt", "UTF-8");
                    out.println(resultPane.getText());
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    btnSave.setEnabled(true);
                }
            } else if (status == JFileChooser.CANCEL_OPTION) {

                btnSave.setEnabled(true);

            }

        }

    }

}
