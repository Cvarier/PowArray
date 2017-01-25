package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JEditorPane;
import java.awt.SystemColor;
import java.awt.Font;

public class WhatIsNew extends JFrame {

    private JPanel contentPane;
    private ImageIcon appIcon;
    private static final long serialVersionUID = 1L;

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
                    WhatIsNew frame = new WhatIsNew();
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
    public WhatIsNew() {

        appIcon = new ImageIcon(
                WhatIsNew.class.getResource("/ui/PowArray Icon.png"));

        setResizable(false);
        setIconImage(appIcon.getImage());
        setTitle("What's new in PowArray v1.0.1");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(1075, 100, 450, 500);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JEditorPane text = new JEditorPane();
        text.setFont(new Font("Tahoma", Font.PLAIN, 14));
        text.setContentType("text/html");
        text.setBackground(SystemColor.control);
        text.setEditable(false);
        text.setFocusable(false);
        text.setText("<html><font face='arial'; size ='6'; color='#B3151D'><b><center>"
                + "What's New</center></b></font><br><font face='arial'; size ='5'><font color = '#358515'>"
                + "Chaitanya Varier is proud to present you with the first official release of "
                + "<b>PowArray Aribtrary Precision Calculator (v1.0.1)</b>. "
                + "Since the latest Beta release of Powerful Calculator, many new features and changes have been "
                + "incorporated to deliver an optimal user experience, starting off with a name change. Among the <b>major</b> changes "
                + "are significant calculation algorithm performance optimizations, UI improvements and "
                + "customizable preferences.</font><br><br>"
                + "A short list of the changes is as follows:<br><br>• Heavily optimized exponent and factorial"
                + " calculator algorithms"
                + " - <font color ='blue'>Calculation algorithms are now on average 38% faster and consume 70% less RAM</font><br><br>"
                + "• PowArray is now multithreaded, which means the user retains full control "
                + "over the application during a calculation.<br><br>"
                + "• Calculations can now be stopped, paused and resumed once started.<br><br>"
                + "• The factorial calculation limit has been extended to 2000000!<br><br>"
                + "• The precise number of digits is given in the exponent calculator once a "
                + "calculation has started, which gives the user an idea how long it will take. "
                + "Likewise in the factorial calculator, an estimated number of digits is given.<br><br>"
                + "• Hand crafted icons have been added to many new buttons.<br><br>"
                + "• Options to save result upon completion to a text file and copy result to system clipboard have been added.<br><br>"
                + "• Sounds has been added to alert user of specific events during the calculation, with an option to mute them.<br><br>"
                + "• Preferences window with many customizable features has been added including the following "
                + "features - <font color ='blue'>Save Result Options: set default save location, always ask for save location."
                + " Sound options: set volumes of all sounds, reset sound options to their default values and test each sound.</font><br><br>"
                + "• Added self designed icon for PowArray<br><br>"
                + "• Added EULA<br><br>"
                + "<font face='arial'; size ='6'; color='#B3151D'><b><center>Expected in Future Releases:</b></center></font><br>"
                + "• Options to save and shutdown computer upon calculation completion<br><br>"
                + "• Save confirmation box on exit<br><br>"
                + "• Option for sending bug reports<br><br>"
                + "• Help button which links to a readme manual<br><br>"
                + "• Any additional requested features/changes");

        JScrollPane scrollPane = new JScrollPane(text);
        contentPane.add(scrollPane);
        text.setCaretPosition(0);
    }

}
