package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import java.awt.Desktop;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.prefs.Preferences;

public class About extends JFrame {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

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
                    About frame = new About();
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
    public About() {
        ImageIcon appIcon = new ImageIcon(
                About.class.getResource("/ui/PowArray Icon.png"));

        setIconImage(appIcon.getImage());
        setTitle("About PowArray");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(562, 100, 501, 645);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JEditorPane text = new JEditorPane();
        text.setFont(new Font("Tahoma", Font.PLAIN, 14));
        text.setBackground(SystemColor.control);
        text.setEditable(false);
        text.setFocusable(true);
        text.setEditorKit(JEditorPane
                .createEditorKitForContentType("text/html"));

        text.setText("<html><font face='arial'; size ='5'><b>PowArray Aribtrary Precision "
                + "Calculator</b> evolved from a set of solutions that I created in order to "
                + "solve various Project Euler problems. The factorial, exponent and prime "
                + "factorization calculators within this application do not utilize Java's "
                + "BigInteger "
                + "classes nor any external libraries, which essentially means that the "
                + "algorithms in this program were created entirely from scratch.<br><br>"
                + "<font color = 'red'><b>Important: </b>The following are "
                + "recommended system requirements to ensure that PowArray will run "
                + "properly on your computer:<br><br>• <b>Minimum 2 GHz Dual Core CPU</b> (Note, "
                + "having at least a 3 GHz Quad Core Processor is recommended for calculations "
                + "involving several millions of digits)<br><br>"
                + "• <b>Minimum 1 GB of RAM</b> (Note, having at least 4 GB of RAM is recommended for "
                + "calculations involving several millions of digits)<br><br>"
                + "<font color = 'blue'><b>Note about the calculator functions:</b> Calculations that are very large "
                + "(which return an answer more than  15,000 digits long) can range between "
                + "a few seconds to several hours long, depending on how large the calculation "
                + "is. The algorithms within this calculator have been tested very thoroughly "
                + "and guarantee an accurate result no matter how large the calculation is. "
                + "A typical calculation involving between 10,000 to 100,000 digits should take "
                + "under 10 minutes on a modestly powerful modern processor. However a calculation "
                + "involving more than a million digits can take more than 20 minutes. "
                + "One involving several millions of digits will take at least a few hours.<br><br>"
                + "<b>Note about sound:</b> The finish sound will not play if the calculation is too small.</font>"
                + "<br><br><font color = '#358515'>Should you have any inquiries, suggestions for additional "
                + "features/changes in future releases or notice "
                + "any bugs in the application,"
                + " please contact me through my developer website at: </font><i><font color = 'blue'>"
                + "<a href=\"http://www.chaitanyavarier.com\">www.chaitanyavarier.com</a>. "
                + "</i></font><font color = 'red'><font color = '#358515'>I would also appreciate "
                + "any feedback, and "
                + "would love to know of some of the successes and experiences you've had with "
                + "<b>PowArray</b>!</font></html>");

        JScrollPane scrollPane = new JScrollPane(text);

        text.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    if (Desktop.isDesktopSupported()) {
                        try {
                            Desktop.getDesktop().browse(e.getURL().toURI());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (URISyntaxException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });

        contentPane.add(scrollPane);
        text.setCaretPosition(0);

        final Preferences prefs = Preferences
                .userNodeForPackage(ui.MainGUI.class);

        final String aboutOpen = "ABOUTOPEN";

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                prefs.putBoolean(aboutOpen, false);
            }
        });

    }
}
