import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Connection.MakeHttpRequest;


import java.awt.Font;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class Trivia {

	private JFrame frmTriviaMachine;
	private JTextField optNumberOfQuestions;
	private String token;
	private JButton btnGo;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Trivia window = new Trivia();
					window.frmTriviaMachine.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Trivia() {
		 	lblStatus = new JLabel("Status: Getting data from server");
			getToken();
			initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws JSONException 
	 */
	private void initialize() {
		frmTriviaMachine = new JFrame();
		frmTriviaMachine.setTitle("Trivia Machine");
		frmTriviaMachine.setBounds(100, 100, 578, 261);
		frmTriviaMachine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblWelcomeToThe = new JLabel("Welcome to the Trivia Machine!");
		lblWelcomeToThe.setFont(new Font("Kristen ITC", Font.BOLD, 16));
		lblWelcomeToThe.setHorizontalAlignment(SwingConstants.CENTER);
		frmTriviaMachine.getContentPane().add(lblWelcomeToThe, BorderLayout.NORTH);
		
		JPanel optType = new JPanel();
		frmTriviaMachine.getContentPane().add(optType, BorderLayout.CENTER);
		GridBagLayout gbl_optType = new GridBagLayout();
		gbl_optType.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_optType.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_optType.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_optType.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		optType.setLayout(gbl_optType);
		
		JLabel lblOptions = new JLabel("Options");
		GridBagConstraints gbc_lblOptions = new GridBagConstraints();
		gbc_lblOptions.insets = new Insets(0, 0, 5, 5);
		gbc_lblOptions.gridx = 4;
		gbc_lblOptions.gridy = 0;
		optType.add(lblOptions, gbc_lblOptions);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		GridBagConstraints gbc_horizontalGlue_1 = new GridBagConstraints();
		gbc_horizontalGlue_1.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalGlue_1.gridx = 1;
		gbc_horizontalGlue_1.gridy = 1;
		optType.add(horizontalGlue_1, gbc_horizontalGlue_1);
		
		JLabel lblNumberOfQuestions = new JLabel("Number of questions:");
		lblNumberOfQuestions.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNumberOfQuestions = new GridBagConstraints();
		gbc_lblNumberOfQuestions.anchor = GridBagConstraints.EAST;
		gbc_lblNumberOfQuestions.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfQuestions.gridx = 2;
		gbc_lblNumberOfQuestions.gridy = 1;
		optType.add(lblNumberOfQuestions, gbc_lblNumberOfQuestions);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		GridBagConstraints gbc_horizontalGlue = new GridBagConstraints();
		gbc_horizontalGlue.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalGlue.gridx = 3;
		gbc_horizontalGlue.gridy = 1;
		optType.add(horizontalGlue, gbc_horizontalGlue);
		
		optNumberOfQuestions = new JTextField();
		GridBagConstraints gbc_txtOptnumberofquestions = new GridBagConstraints();
		gbc_txtOptnumberofquestions.insets = new Insets(0, 0, 5, 5);
		gbc_txtOptnumberofquestions.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtOptnumberofquestions.gridx = 4;
		gbc_txtOptnumberofquestions.gridy = 1;
		optType.add(optNumberOfQuestions, gbc_txtOptnumberofquestions);
		optNumberOfQuestions.setColumns(10);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.gridwidth = 5;
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut.gridx = 5;
		gbc_horizontalStrut.gridy = 1;
		optType.add(horizontalStrut, gbc_horizontalStrut);
		
		JLabel lblCategory = new JLabel("Category:");
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.anchor = GridBagConstraints.EAST;
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.gridx = 2;
		gbc_lblCategory.gridy = 2;
		optType.add(lblCategory, gbc_lblCategory);
		
		JComboBox<String> optCategory = new JComboBox<String>();
		optCategory.setModel(new DefaultComboBoxModel<String>(new String[] {"Any"}));
		Thread threadAddCategory = new Thread() {
			public void run(){
				JSONObject response = MakeHttpRequest.makeRequest(null, "getCategories");
				try {
					JSONArray categories = response.getJSONArray("trivia_categories");
					for(int i = 0; i < categories.length(); i++) {
						optCategory.addItem(categories.getJSONObject(i).getString("name"));
					}
				}catch(Exception e) {
					e.getStackTrace();
				}
				btnGo.setEnabled(true);
				lblStatus.setText("You can start now!");
			}
		};
		threadAddCategory.start();
		GridBagConstraints gbc_optCategory = new GridBagConstraints();
		gbc_optCategory.insets = new Insets(0, 0, 5, 5);
		gbc_optCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_optCategory.gridx = 4;
		gbc_optCategory.gridy = 2;
		optType.add(optCategory, gbc_optCategory);
		
		JLabel lblDifficulty = new JLabel("Difficulty:");
		GridBagConstraints gbc_lblDifficulty = new GridBagConstraints();
		gbc_lblDifficulty.anchor = GridBagConstraints.EAST;
		gbc_lblDifficulty.insets = new Insets(0, 0, 5, 5);
		gbc_lblDifficulty.gridx = 2;
		gbc_lblDifficulty.gridy = 3;
		optType.add(lblDifficulty, gbc_lblDifficulty);
		
		JComboBox<String> optDifficulty = new JComboBox<String>();
		optDifficulty.setModel(new DefaultComboBoxModel<String>(new String[] {"Mixed", "Easy", "Medium", "Hard"}));
		GridBagConstraints gbc_optDifficulty = new GridBagConstraints();
		gbc_optDifficulty.insets = new Insets(0, 0, 5, 5);
		gbc_optDifficulty.fill = GridBagConstraints.HORIZONTAL;
		gbc_optDifficulty.gridx = 4;
		gbc_optDifficulty.gridy = 3;
		optType.add(optDifficulty, gbc_optDifficulty);
		
		JLabel lblType = new JLabel("Type:");
		GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.anchor = GridBagConstraints.EAST;
		gbc_lblType.insets = new Insets(0, 0, 5, 5);
		gbc_lblType.gridx = 2;
		gbc_lblType.gridy = 4;
		optType.add(lblType, gbc_lblType);
		
		JComboBox<String> optQuestionType = new JComboBox<String>();
		optQuestionType.setModel(new DefaultComboBoxModel<String>(new String[] {"Any", "True/False", "Multiple Choice"}));
		GridBagConstraints gbc_optQuestionType = new GridBagConstraints();
		gbc_optQuestionType.insets = new Insets(0, 0, 5, 5);
		gbc_optQuestionType.fill = GridBagConstraints.HORIZONTAL;
		gbc_optQuestionType.gridx = 4;
		gbc_optQuestionType.gridy = 4;
		optType.add(optQuestionType, gbc_optQuestionType);
		
		btnGo = new JButton("Start!");
		btnGo.setEnabled(false);
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//try {
					if(Integer.parseInt(optNumberOfQuestions.getText())>1) {
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("amount", optNumberOfQuestions.getText()));
						params.add(new BasicNameValuePair("token", token));
						if(optCategory.getSelectedIndex()!=0)
							params.add(new BasicNameValuePair("category",String.valueOf(optCategory.getSelectedIndex()+8)));
						if(optDifficulty.getSelectedIndex()!=0)
							params.add(new BasicNameValuePair("difficulty",optDifficulty.getSelectedItem().toString().toLowerCase()));
						if(optQuestionType.getSelectedIndex()!=0) {
							if(optQuestionType.getSelectedIndex()==1)
								params.add(new BasicNameValuePair("type","boolean"));
							else
							params.add(new BasicNameValuePair("type","multiple"));
						}
						JSONObject questions = MakeHttpRequest.makeRequest(params, "getQuestions");
						QuestionView questionWindow = new QuestionView(questions);
					}
					//else
						//throw new Exception();
				/*}catch(Exception e1) {
					JOptionPane.showMessageDialog(frmTriviaMachine, "Number of question is invalid!");
				}*/
			}
		});
		GridBagConstraints gbc_btnGo = new GridBagConstraints();
		gbc_btnGo.insets = new Insets(0, 0, 5, 5);
		gbc_btnGo.gridx = 4;
		gbc_btnGo.gridy = 5;
		optType.add(btnGo, gbc_btnGo);
		
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatus.gridx = 2;
		gbc_lblStatus.gridy = 6;
		optType.add(lblStatus, gbc_lblStatus);
		
		JButton btnResetToken = new JButton("Reset Token");
		btnResetToken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetToken();
			}
		});
		GridBagConstraints gbc_btnResetToken = new GridBagConstraints();
		gbc_btnResetToken.insets = new Insets(0, 0, 5, 5);
		gbc_btnResetToken.gridx = 4;
		gbc_btnResetToken.gridy = 6;
		optType.add(btnResetToken, gbc_btnResetToken);
	}
	
	private void getToken() {
		Thread threadGetToken = new Thread() {
			public void run() {
				try {
					token = MakeHttpRequest.makeRequest(null, "getToken").getString("token");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(frmTriviaMachine, "Token generated!");
			}
		};
		threadGetToken.start();
	}
	
	private void resetToken() {
		Thread threadGetToken = new Thread() {
			public void run() {
				MakeHttpRequest.makeRequest(null, "resetToken");
				JOptionPane.showMessageDialog(frmTriviaMachine, "Token reset!");
			}
		};
		threadGetToken.start();
	}
}
