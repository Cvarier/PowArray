package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.prefs.Preferences;

import javax.swing.UIManager;

public class MainGUI {

    public JFrame frmPowerfulCalculator;

    private JButton btnPrefs;

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
                    MainGUI window = new MainGUI();
                    window.frmPowerfulCalculator.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Create the application.
     */
    public MainGUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        ImageIcon appIcon = new ImageIcon(
                MainGUI.class.getResource("/ui/PowArray Icon.png"));

        frmPowerfulCalculator = new JFrame();
        frmPowerfulCalculator.setResizable(false);
        frmPowerfulCalculator.getContentPane().setBackground(
                UIManager.getColor("menu"));
        frmPowerfulCalculator.setIconImage(appIcon.getImage());
        frmPowerfulCalculator
                .setTitle("PowArray Arbitrary Precision Calculator v1.0.1");
        frmPowerfulCalculator.setBounds(100, 100, 450, 320);
        frmPowerfulCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final Preferences prefs = Preferences
                .userNodeForPackage(ui.MainGUI.class);

        final String aboutOpen = "ABOUTOPEN";
        final String openEULA = "EULAOPEN";
        final String started = "STARTED";
        final String triggeredacceptEULA = "EULATRIGGERED";
        final String openPrefs = "PREFSOPEN";

        if (!prefs.getBoolean(started, false)) {

            prefs.putBoolean(aboutOpen, true);
            prefs.putBoolean(started, true);
            About about = new About();
            about.setVisible(true);

        }

        JButton btnFactorial = new JButton("Factorial");
        btnFactorial.setBounds(25, 44, 169, 73);
        btnFactorial.setToolTipText("Calculate factorials of large integers");
        btnFactorial.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnFactorial.setForeground(Color.BLACK);
        btnFactorial.setBackground(Color.LIGHT_GRAY);
        btnFactorial.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                prefs.putBoolean(triggeredacceptEULA, false);
                FactorialFrame FactorialFrame = new FactorialFrame();
                FactorialFrame.setVisible(true);
                frmPowerfulCalculator.dispose();

            }
        });
        frmPowerfulCalculator.getContentPane().setLayout(null);

        JLabel lblCopyright = new JLabel("\u00A92015 Chaitanya Varier");
        lblCopyright.setHorizontalAlignment(SwingConstants.CENTER);
        lblCopyright.setBounds(274, 0, 158, 31);
        frmPowerfulCalculator.getContentPane().add(lblCopyright);
        frmPowerfulCalculator.getContentPane().add(btnFactorial);

        JButton btnExponent = new JButton("Exponent");
        btnExponent.setToolTipText("Perform exponentiation on large integers");
        btnExponent.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                prefs.putBoolean(triggeredacceptEULA, false);
                ExponentFrame ExponentFrame = new ExponentFrame();
                ExponentFrame.setVisible(true);
                frmPowerfulCalculator.dispose();

            }
        });
        btnExponent.setForeground(Color.BLACK);
        btnExponent.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnExponent.setBackground(Color.LIGHT_GRAY);
        btnExponent.setBounds(238, 44, 169, 73);
        frmPowerfulCalculator.getContentPane().add(btnExponent);

        JButton btnPrimeFact = new JButton("Prime Factorization");
        btnPrimeFact
                .setToolTipText("Find the prime factorizations of large integers");
        btnPrimeFact.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                prefs.putBoolean(triggeredacceptEULA, false);
                PrimeFactFrame PrimeFactFrame = new PrimeFactFrame();
                PrimeFactFrame.setVisible(true);
                frmPowerfulCalculator.dispose();

            }
        });
        btnPrimeFact.setForeground(Color.BLACK);
        btnPrimeFact.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnPrimeFact.setBackground(Color.LIGHT_GRAY);
        btnPrimeFact.setBounds(95, 145, 250, 73);
        frmPowerfulCalculator.getContentPane().add(btnPrimeFact);

        JLabel versionLabel = new JLabel("Version 1.0.1");
        versionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        versionLabel.setBounds(345, 255, 90, 22);
        frmPowerfulCalculator.getContentPane().add(versionLabel);

        JButton btnAbout = new JButton("About");
        btnAbout.setToolTipText("View important information about the application");
        btnAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (!prefs.getBoolean(aboutOpen, false)) {
                    prefs.putBoolean(aboutOpen, true);
                    About about = new About();
                    about.setVisible(true);
                }

            }
        });
        btnAbout.setBounds(20, 245, 100, 30);
        frmPowerfulCalculator.getContentPane().add(btnAbout);

        JButton btnEULA = new JButton("View EULA");
        btnEULA.setToolTipText("View the End User License Agreement");
        btnEULA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (!prefs.getBoolean(openEULA, false)) {
                    prefs.putBoolean(openEULA, true);
                    EULAPopUp eula = new EULAPopUp();
                    eula.setVisible(true);
                }

            }
        });
        btnEULA.setBounds(140, 245, 100, 30);
        frmPowerfulCalculator.getContentPane().add(btnEULA);

        ImageIcon prefsIcon = new ImageIcon(
                MainGUI.class.getResource("/ui/Preferences Icon.png"));

        btnPrefs = new JButton(prefsIcon);
        btnPrefs.setMargin(new Insets(1, 0, 1, -2));
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
        btnPrefs.setBounds(375, 164, 40, 40);
        frmPowerfulCalculator.getContentPane().add(btnPrefs);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            public void run() {

                prefs.remove(openEULA);
                prefs.remove(aboutOpen);
                prefs.remove(started);
                prefs.remove(openPrefs);
                prefs.putBoolean(triggeredacceptEULA, false);

            }

        }));

        if (prefs.getBoolean(triggeredacceptEULA, true)) {

            WhatIsNew whatIsNew = new WhatIsNew();
            whatIsNew.setVisible(true);

        }

    }
}
