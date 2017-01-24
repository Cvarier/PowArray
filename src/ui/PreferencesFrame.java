package ui;

import java.awt.EventQueue;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.util.prefs.Preferences;

public class PreferencesFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MyPanel contentPane;

	private Preferences prefs = Preferences
			.userNodeForPackage(ui.PreferencesFrame.class);
	private String soundOn = "SOUNDON";
	private String saveLoc = "SAVELOC";
	private String openPrefs = "PREFSOPEN";
	private String alwaysAskForSaveLoc = "ALWAYSASKFORSAVELOC";

	private String masterVolOn = "MASTERVOLUMESET";

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

	private Clip clip;

	private JLabel lblSave, lblSound, lblSaveLoc, lblManualSelect,
			lblSoundSelector, lblSoundVolume, lblSoundMasterVolume;
	private JButton btnDone, btnSetSaveLoc, btnTestSound, btnResetSound;
	private JSlider volControl;
	private JComboBox<String> soundSelector;
	private String[] soundOptions;

	private JTextPane path;
	private MyChooser fileChooser;
	private JCheckBox manualSelect, masterVolume;

	private static ImageIcon appIcon, testIcon, resetSoundIcon;

	private static int soundEvent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		try {
			UIManager.put("Slider.focus", UIManager.get("Slider.background"));
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PreferencesFrame frame = new PreferencesFrame();
					frame.setVisible(true);
					frame.pack();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	/**
	 * Create the frame.
	 */
	public PreferencesFrame() {

		appIcon = new ImageIcon(
				PreferencesFrame.class.getResource("/ui/PowArray Icon.png"));

		setIconImage(appIcon.getImage());
		setTitle("Powarray v1.0.1 - Preferences");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(562, 100, 505, 745);
		setResizable(false);
		contentPane = new MyPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblSave = new JLabel("Save Result Options");
		lblSave.setHorizontalAlignment(SwingConstants.CENTER);
		lblSave.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSave.setBounds(150, 13, 200, 30);
		contentPane.add(lblSave);

		lblSound = new JLabel("Sound Options");
		lblSound.setHorizontalAlignment(SwingConstants.CENTER);
		lblSound.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSound.setBounds(170, 288, 160, 30);
		contentPane.add(lblSound);

		btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				prefs.putBoolean(openPrefs, false);
				dispose();

			}
		});
		btnDone.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnDone.setToolTipText("Close the preferences dialog box");
		btnDone.setBounds(180, 657, 140, 40);
		contentPane.add(btnDone);

		lblSaveLoc = new JLabel("Current Default Result Save Location:");
		lblSaveLoc.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSaveLoc.setBounds(52, 60, 290, 30);
		contentPane.add(lblSaveLoc);

		lblManualSelect = new JLabel("Always Ask for Result Save Location:");
		lblManualSelect
				.setToolTipText("When set, you will be asked for a save location "
						+ "and a file name every time you click the save button");
		lblManualSelect.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblManualSelect.setBounds(52, 205, 290, 40);
		contentPane.add(lblManualSelect);

		manualSelect = new JCheckBox();
		manualSelect.setBounds(345, 205, 40, 40);
		manualSelect
				.setToolTipText("When set, you will be asked for a save location "
						+ "and a file name every time you click the save button");
		contentPane.add(manualSelect);

		if (prefs.getBoolean(alwaysAskForSaveLoc, false)) {

			manualSelect.setSelected(true);

		} else {

			manualSelect.setSelected(false);

		}

		manualSelect.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == ItemEvent.SELECTED) {

					prefs.putBoolean(alwaysAskForSaveLoc, true);

				} else {

					prefs.putBoolean(alwaysAskForSaveLoc, false);

				}
			}

		});

		path = new JTextPane();
		path.setEditable(false);
		path.setToolTipText("The current default result save location");
		path.setBounds(50, 110, 400, 20);
		path.setBorder(new LineBorder(new Color(0, 0, 0)));
		path.setText(" " + prefs.get(saveLoc, ""));
		contentPane.add(path);

		btnSetSaveLoc = new JButton("Set Default Save Location");
		btnSetSaveLoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				btnSetSaveLoc.setEnabled(false);

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
							prefs.put(saveLoc, selectedFileDir + "\\");
						}

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				btnSetSaveLoc.setEnabled(true);
				path.setText(" " + prefs.get(saveLoc, ""));

			}
		});

		btnSetSaveLoc.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSetSaveLoc.setToolTipText("Set the default result save location");
		btnSetSaveLoc.setBounds(140, 150, 220, 40);
		btnSetSaveLoc.setMargin(new Insets(0, 2, 0, 2));
		contentPane.add(btnSetSaveLoc);

		lblSoundSelector = new JLabel("Current Selected Sound:");
		lblSoundSelector.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSoundSelector.setBounds(52, 335, 290, 30);
		contentPane.add(lblSoundSelector);

		soundOptions = new String[] { "Start", "Finish", "Pause", "Resume",
				"Stop", "Error" };
		soundSelector = new JComboBox<String>(soundOptions);
		soundSelector.setToolTipText("Select the sound you wish to customize");
		soundSelector.setFont(new Font("Tahoma", Font.BOLD, 15));
		((JLabel) soundSelector.getRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		soundSelector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				soundEvent = soundSelector.getSelectedIndex();
				if (soundEvent == 0) {
					volControl.setValue(prefs.getInt(startSoundVol, 60));
				} else if (soundEvent == 1) {
					volControl.setValue(prefs.getInt(finishSoundVol, 60));
				} else if (soundEvent == 2) {
					volControl.setValue(prefs.getInt(pauseSoundVol, 60));
				} else if (soundEvent == 3) {
					volControl.setValue(prefs.getInt(resumeSoundVol, 60));
				} else if (soundEvent == 4) {
					volControl.setValue(prefs.getInt(stopSoundVol, 60));
				} else if (soundEvent == 5) {
					volControl.setValue(prefs.getInt(errorSoundVol, 60));
				}

			}
		});
		soundSelector.setBounds(190, 385, 120, 35);

		contentPane.add(soundSelector);

		lblSoundVolume = new JLabel("Selected Sound Volume:");
		lblSoundVolume.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSoundVolume.setBounds(52, 440, 290, 30);
		contentPane.add(lblSoundVolume);

		volControl = new JSlider(JSlider.HORIZONTAL, 0, 100, 60);
		volControl.setToolTipText("Set the volume of the chosen sound");
		volControl.setBounds(54, 490, 200, 60);
		volControl.setMajorTickSpacing(25);
		volControl.setPaintTicks(true);
		volControl.setPaintLabels(true);
		volControl.setValue(prefs.getInt(startSoundVol, 60));
		volControl.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				int volume = volControl.getValue();

				if (!prefs.getBoolean(masterVolOn, false)) {

					if (soundEvent == 0) {
						prefs.putInt(startSoundVol, volume);
					} else if (soundEvent == 1) {
						prefs.putInt(finishSoundVol, volume);
					} else if (soundEvent == 2) {
						prefs.putInt(pauseSoundVol, volume);
					} else if (soundEvent == 3) {
						prefs.putInt(resumeSoundVol, volume);
					} else if (soundEvent == 4) {
						prefs.putInt(stopSoundVol, volume);
					} else if (soundEvent == 5) {
						prefs.putInt(errorSoundVol, volume);
					}

				} else {

					prefs.putInt(startSoundVol, volume);
					prefs.putInt(finishSoundVol, volume);
					prefs.putInt(pauseSoundVol, volume);
					prefs.putInt(resumeSoundVol, volume);
					prefs.putInt(stopSoundVol, volume);
					prefs.putInt(errorSoundVol, volume);
				}

			}
		});
		contentPane.add(volControl);

		testIcon = new ImageIcon(
				ExponentFrame.class.getResource("/ui/ResumePlay Icon.png"));

		btnTestSound = new JButton("Test Sound");
		btnTestSound.setIcon(testIcon);
		btnTestSound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				soundEvent = soundSelector.getSelectedIndex();

				// Play Start Sound
				if (prefs.getBoolean(soundOn, true) && soundEvent == 0) {
					playClip(startSound, 0);
					// Play Finish Sound
				} else if (prefs.getBoolean(soundOn, true) && soundEvent == 1) {
					playClip(finishSound, 1);
					// Play Pause Sound
				} else if (prefs.getBoolean(soundOn, true) && soundEvent == 2) {
					playClip(pauseSound, 2);
					// Play Resume Sound
				} else if (prefs.getBoolean(soundOn, true) && soundEvent == 3) {
					playClip(resumeSound, 3);
					// Play Stop Sound
				} else if (prefs.getBoolean(soundOn, true) && soundEvent == 4) {
					playClip(stopSound, 4);
					// Play Error Sound
				} else if (prefs.getBoolean(soundOn, true) && soundEvent == 5) {
					playClip(errorSound, 5);
				}

				Runnable r = new Runnable() {
					public void run() {
						btnTestSound.setEnabled(false);
						try {
							Thread.sleep(1500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						btnTestSound.setEnabled(true);
					}
				};
				Thread timer = new Thread(r);
				timer.start();

			}
		});

		btnTestSound.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTestSound
				.setToolTipText("Test the selected sound at the current volume");
		btnTestSound.setBounds(300, 525, 132, 40);
		btnTestSound.setMargin(new Insets(0, 2, 0, 2));
		contentPane.add(btnTestSound);

		resetSoundIcon = new ImageIcon(
				ExponentFrame.class.getResource("/ui/Reset Sound Icon.png"));

		btnResetSound = new JButton("Reset");
		btnResetSound.setMargin(new Insets(0, 4, 0, 9));
		btnResetSound.setIcon(resetSoundIcon);
		btnResetSound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				prefs.putInt(startSoundVol, 60);
				prefs.putInt(finishSoundVol, 60);
				prefs.putInt(pauseSoundVol, 60);
				prefs.putInt(resumeSoundVol, 60);
				prefs.putInt(stopSoundVol, 60);
				prefs.putInt(errorSoundVol, 60);

				volControl.setValue(60);

			}
		});

		btnResetSound.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnResetSound
				.setToolTipText("Reset all sound volumes to their default values");
		btnResetSound.setBounds(300, 460, 132, 40);
		contentPane.add(btnResetSound);

		lblSoundMasterVolume = new JLabel("Enable Master Volume Control:");
		lblSoundMasterVolume.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSoundMasterVolume.setBounds(52, 585, 290, 30);
		contentPane.add(lblSoundMasterVolume);

		masterVolume = new JCheckBox();
		masterVolume.setBounds(295, 580, 40, 40);
		masterVolume
				.setToolTipText("When set, the volume slider will set the volume"
						+ "of all sounds simultaneously");
		contentPane.add(masterVolume);

		if (prefs.getBoolean(masterVolOn, false)) {

			masterVolume.setSelected(true);

		} else {

			masterVolume.setSelected(false);

		}

		masterVolume.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == ItemEvent.SELECTED) {

					prefs.putBoolean(masterVolOn, true);

				} else {

					prefs.putBoolean(masterVolOn, false);

				}
			}

		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				prefs.putBoolean(openPrefs, false);
			}
		});

	}

	private static class MyPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {

			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.BLACK);
			g2.drawRect(35, 30, 430, 240);
			g2.drawRect(35, 305, 430, 337);
			g2.setColor(getBackground());
			g2.fillRect(170, 15, 160, 30);
			g2.fillRect(188, 291, 125, 30);

		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(500, 710);
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
			dlg.setTitle("Set Default Result Save Location");
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

}
