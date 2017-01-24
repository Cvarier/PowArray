package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Insets;
import java.awt.Font;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class EULA extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton accept;
	private JButton decline;
	private JScrollPane scrollPane;
	private JTextPane text;
	private ImageIcon appIcon;
	private static Preferences prefs = Preferences
			.userNodeForPackage(ui.EULA.class);

	private boolean scrollListening = true;

	public static String versionNumber = "1.0.1";
	public static String acceptedEULA = "EULAACCEPTED";
	public static String triggeredacceptEULA = "EULATRIGGERED";
	public static String latestInstalledVersionNumber = "VERSION";

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

				if (!prefs.getBoolean(acceptedEULA, false)) {

					try {
						EULA frame = new EULA();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {

					if (prefs.get(latestInstalledVersionNumber, "1.0.0")
							.compareTo(versionNumber) < 0) {
						prefs.putBoolean(acceptedEULA, false);
						prefs.put(latestInstalledVersionNumber, versionNumber);

						try {
							EULA frame = new EULA();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {

						MainGUI window = new MainGUI();
						window.frmPowerfulCalculator.setVisible(true);

					}

				}

			}
		});

	}

	/**
	 * Create the frame.
	 */
	public EULA() {

		appIcon = new ImageIcon(EULA.class.getResource("/ui/PowArray Icon.png"));

		setResizable(false);
		setIconImage(appIcon.getImage());

		setTitle("PowArray Arbitrary Precision Calculator v1.0.1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(562, 100, 501, 645);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		text = new JTextPane();
		text.setFont(new Font("Tahoma", Font.PLAIN, 16));
		text.setEditable(false);
		text.setFocusable(false);
		text.setMargin(new Insets(5, 5, 5, 5));
		text.setBackground(Color.WHITE);
		text.setEditorKit(JEditorPane
				.createEditorKitForContentType("text/html"));

		text.setText("<html><font face='arial'; size ='5'>PowArray Arbitrary Precision Calculator v1.0.1<br>"
				+ "Copyright (c) 2015 Chaitanya Varier<br>"
				+ "<br>"
				+ "<b>*** END USER LICENSE AGREEMENT ***<br>"
				+ "<br>"
				+ "IMPORTANT: YOU MUST READ THIS LICENSE IN ORDER TO USE THIS SOFTWARE.</b><br>"
				+ "<br>"
				+ "<b>1. LICENSE</b><br>"
				+ "<br>"
				+ "By clicking the accept button and/or using PowArray Arbitrary Precision Calculator v1.0.1 (\"Software\"), you agree that this End User User License Agreement (EULA) is a legally binding and valid contract and agree to be bound by it. You agree to abide by the intellectual property laws and all of the terms and conditions of this Agreement.<br>"
				+ "<br>"
				+ "Unless you have a different license agreement signed by Chaitanya Varier, your use of PowArray Arbitrary Precision Calculator v1.0.1 indicates your acceptance of this license agreement and warranty.<br>"
				+ "<br>"
				+ "Subject to the terms of this Agreement, Chaitanya Varier grants to you a limited, non-exclusive, non-transferable license, without right to sub-license, to use PowArray Arbitrary Precision Calculator v1.0.1 in accordance with this Agreement and any other written agreement with Chaitanya Varier.<b> Chaitanya Varier does not transfer the title of PowArray Arbitrary Precision Calculator v1.0.1 to you; the license granted to you is not a sale.</b> This agreement is a binding legal agreement between Chaitanya Varier and the purchasers or users of PowArray Arbitrary Precision Calculator v1.0.1.<br>"
				+ "<br>"
				+ "If you do not agree to be bound by this agreement, remove PowArray Arbitrary Precision Calculator v1.0.1 from your computer now. In the event that you do not agree to be bound by this agreement, you will be eligible for a full refund of the purchase price of this software if it was purchased.<br>"
				+ "<br>"
				+ "<b>2. DISTRIBUTION</b><br>"
				+ "<br>"
				+ "<b>PowArray Arbitrary Precision Calculator v1.0.1 and the license herein granted shall not be copied, shared, distributed, re-sold, offered for re-sale, transferred or sub-licensed in whole or in part.</b> <br>"
				+ "<br>"
				+ "<b>3. USER AGREEMENT</b><br>"
				+ "<br>"
				+ "<b>3.1 Use</b><br>"
				+ "<br>"
				+ "Your license to use PowArray Arbitrary Precision Calculator v1.0.1 is limited to the number of licenses purchased by you. You shall not allow others to use, copy or evaluate copies of PowArray Arbitrary Precision Calculator v1.0.1.<br>"
				+ "<br>"
				+ "<b>3.2 Use Restrictions</b><br>"
				+ "<br>"
				+ "You shall use PowArray Arbitrary Precision Calculator v1.0.1 in compliance with all applicable laws and not for any unlawful purpose.<br>"
				+ "<br>"
				+ "Each licensed copy of PowArray Arbitrary Precision Calculator v1.0.1 may be used on one single computer location by one user. Use of PowArray Arbitrary Precision Calculator v1.0.1 means that you have loaded, installed, or run PowArray Arbitrary Precision Calculator v1.0.1 on a computer or similar device. If you install PowArray Arbitrary Precision Calculator v1.0.1 onto a multi-user platform, server or network, each and every individual user of PowArray Arbitrary Precision Calculator v1.0.1 must be licensed separately.<br>"
				+ "<br>"
				+ "You may make one copy of PowArray Arbitrary Precision Calculator v1.0.1 for backup purposes, providing you only have one copy installed on one computer being used by one person. Other users may not use your copy of PowArray Arbitrary Precision Calculator v1.0.1. The assignment, sublicense, networking, sale, or distribution of copies of PowArray Arbitrary Precision Calculator v1.0.1 are strictly forbidden without the prior written consent of Chaitanya Varier. It is a violation of this agreement to assign, sell, share, loan, rent, lease, borrow, network or transfer the use of PowArray Arbitrary Precision Calculator v1.0.1.<br>"
				+ "<br>"
				+ "<b>3.3 Copyright Restriction</b><br>"
				+ "<br>"
				+ "<b>"
				+ "This Software contains copyrighted material, trade secrets and other proprietary material. You shall not, and shall not attempt to, modify, reverse engineer, disassemble or decompile PowArray Arbitrary Precision Calculator v1.0.1. Nor can you create any derivative works or other works that are based upon or derived from PowArray Arbitrary Precision Calculator v1.0.1 in whole or in part.<br>"
				+ "<br>"
				+ "Chaitanya Varier's name, logo and graphics file that represents PowArray Arbitrary Precision Calculator v1.0.1 shall not be used in any way to promote products developed with PowArray Arbitrary Precision Calculator v1.0.1. Chaitanya Varier retains sole and exclusive ownership of all right, title and interest in and to PowArray Arbitrary Precision Calculator v1.0.1 and all Intellectual Property rights relating thereto.<br>"
				+ "<br>"
				+ "Copyright law and international copyright treaty provisions protect all parts of PowArray Arbitrary Precision Calculator v1.0.1, products and services. No program, code, part, image, audio sample, or text may be copied or used in any way by the user except as intended within the bounds of the single user program. All rights not expressly granted hereunder are reserved for Chaitanya Varier.<br>"
				+ "<br>"
				+ "</b>"
				+ "<b>3.4 Limitation of Responsibility</b><br>"
				+ "<br>"
				+ "You will indemnify, hold harmless, and defend Chaitanya Varier, its employees, agents and distributors against any and all claims, proceedings, demand and costs resulting from or in any way connected with your use of Chaitanya Varier's Software.<br>"
				+ "<br>"
				+ "In no event (including, without limitation, in the event of negligence) will Chaitanya Varier be liable for any consequential, incidental, indirect, special or punitive damages whatsoever (including, without limitation, damages for loss of profits, loss of use, business interruption, loss of information or data, or pecuniary loss), in connection with or arising out of or related to this Agreement, PowArray Arbitrary Precision Calculator v1.0.1 or the use or inability to use PowArray Arbitrary Precision Calculator v1.0.1 or the furnishing, performance or use of any other matters hereunder whether based upon contract, tort or any other theory including negligence.<br>"
				+ "<br>"
				+ "Chaitanya Varier's entire liability, without exception, is limited to the customers' reimbursement of the purchase price of the Software (maximum being the lesser of the amount paid by you and the suggested retail price as listed by Chaitanya Varier) in exchange for the return of the product, all copies, registration papers and manuals, and all materials that constitute a transfer of license from the customer back to Chaitanya Varier.<br>"
				+ "<br>"
				+ "<b>3.5 Warranties</b><br>"
				+ "<br>"
				+ "Except as expressly stated in writing, Chaitanya Varier makes no representation or warranties in respect of this Software and expressly excludes all other warranties, expressed or implied, oral or written, including, without limitation, any implied warranties of merchantable quality or fitness for a particular purpose.<br>"
				+ "<br>"
				+ "<b>3.6 Governing Law</b><br>"
				+ "<br>"
				+ "This Agreement shall be governed by the law of the Canada applicable therein. You hereby irrevocably attorn and submit to the non-exclusive jurisdiction of the courts of Canada therefrom. If any provision shall be considered unlawful, void or otherwise unenforceable, then that provision shall be deemed severable from this License and not affect the validity and enforceability of any other provisions.<br>"
				+ "<br>"
				+ "<b>3.7 Termination</b><br>"
				+ "<br>"
				+ "Any failure to comply with the terms and conditions of this Agreement will result in automatic and immediate termination of this license. Upon termination of this license granted herein for any reason, you agree to immediately cease use of PowArray Arbitrary Precision Calculator v1.0.1 and destroy all copies of PowArray Arbitrary Precision Calculator v1.0.1 supplied under this Agreement. The financial obligations incurred by you shall survive the expiration or termination of this license.<br>"
				+ "<br>"
				+ "<b>4. DISCLAIMER OF WARRANTY</b><br>"
				+ "<br>"
				+ "THIS SOFTWARE AND THE ACCOMPANYING FILES ARE SOLD \"AS IS\" AND WITHOUT WARRANTIES AS TO PERFORMANCE OR MERCHANTABILITY OR ANY OTHER WARRANTIES WHETHER EXPRESSED OR IMPLIED. THIS DISCLAIMER CONCERNS ALL FILES GENERATED AND EDITED BY PowArray Arbitrary Precision Calculator v1.0.1 AS WELL.<br>"
				+ "<br>"
				+ "<b>5. CONSENT OF USE OF DATA</b><br>"
				+ "<br>"
				+ "You agree that Chaitanya Varier may collect and use information gathered in any manner as part of the product support services provided to you, if any, related to PowArray Arbitrary Precision Calculator v1.0.1. Chaitanya Varier may also use this information to provide notices to you which may be of use or interest to you.<br></font></html>");

		text.setCaretPosition(0);
		scrollPane = new JScrollPane(text);
		scrollPane.setBounds(5, 5, 485, 540);
		contentPane.add(scrollPane);

		accept = new JButton("Accept");
		accept.setBounds(100, 560, 125, 40);
		accept.setFont(new Font("Tahoma", Font.BOLD, 16));
		accept.setEnabled(false);
		accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				prefs.putBoolean(acceptedEULA, true);
				prefs.putBoolean(triggeredacceptEULA, true);
				prefs.put(latestInstalledVersionNumber, versionNumber);
				dispose();

				MainGUI window = new MainGUI();
				window.frmPowerfulCalculator.setVisible(true);

			}
		});

		contentPane.add(accept);

		decline = new JButton("Decline");
		decline.setBounds(266, 560, 125, 40);
		decline.setEnabled(false);
		decline.setFont(new Font("Tahoma", Font.BOLD, 16));
		decline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				prefs.putBoolean(acceptedEULA, false);
				prefs.put(latestInstalledVersionNumber, versionNumber);

				System.exit(0);

			}
		});

		contentPane.add(decline);

		scrollPane.getVerticalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {
					@Override
					public void adjustmentValueChanged(AdjustmentEvent ae) {

						if (scrollListening) {

							int bottom = scrollPane.getVerticalScrollBar()
									.getModel().getMaximum()
									- scrollPane.getVerticalScrollBar()
											.getModel().getExtent();
							int pos = scrollPane.getVerticalScrollBar()
									.getValue();

							if (pos == bottom) {
								accept.setEnabled(true);
								decline.setEnabled(true);
								scrollListening = false;
							}

						}
					}
				});

	}

}
