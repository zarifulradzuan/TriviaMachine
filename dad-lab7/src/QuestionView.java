import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JRadioButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class QuestionView {

	private JFrame frame;
	private ArrayList<Question> questions = new ArrayList<Question>();
	private int score;
	private int currentQuestion=0;
	private JPanel answerPanel;
	private JTextPane txtQuestionPane;
	ButtonGroup answerGroup;
	class Question{
		String category;
		String question;
		ArrayList<String> answers;
		String answer;
		
		public Question(String category, String question, String answer, ArrayList<String> answers) {
			this.category = category;
			this.question = question;
			this.answer = answer;
			this.answers = answers;
		}
		
		public String getAnswer() {
			return answer;
		}
	}
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuestionView window = new QuestionView(jsnObj);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public QuestionView(JSONObject jsnObj) {
		score=0;
		try {
			if(jsnObj.getInt("response_code")==0) {
				JSONArray jsnArr = jsnObj.getJSONArray("results");
				for(int i =0;i<jsnArr.length();i++) {
					String category = jsnArr.getJSONObject(i).getString("category");
					String question = jsnArr.getJSONObject(i).getString("question");
					String answer = jsnArr.getJSONObject(i).getString("correct_answer");
					JSONArray jsnIncorrectAnswers = jsnArr.getJSONObject(i).getJSONArray("incorrect_answers");
					ArrayList<String> answers = new ArrayList<String>();
					answers.add(answer);
					for(int j = 0;j<jsnIncorrectAnswers.length();j++)
						answers.add(jsnIncorrectAnswers.getString(j));
					Collections.shuffle(answers);
					questions.add(new Question(category, question, answer, answers));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 343, 318);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		answerPanel = new JPanel();
		answerPanel.setBounds(10, 124, 307, 104);
		frame.getContentPane().add(answerPanel);
		answerPanel.setLayout(null);
		
		txtQuestionPane = new JTextPane();
		txtQuestionPane.setText(questions.get(currentQuestion).question);
		txtQuestionPane.setForeground(Color.BLACK);
		txtQuestionPane.setEditable(false);
		txtQuestionPane.setBounds(10, 40, 307, 73);
		frame.getContentPane().add(txtQuestionPane);
		
		JLabel lblScore = new JLabel("Score: "+score+"/"+questions.size());
		lblScore.setBounds(10, 249, 104, 14);
		frame.getContentPane().add(lblScore);
		
		JLabel lblCategory = new JLabel();
		lblCategory.setBounds(10, 11, 245, 18);
		lblCategory.setText((currentQuestion+1)+".) "+questions.get(currentQuestion).category);
		frame.getContentPane().add(lblCategory);
		setup();
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(answerGroup.getSelection().getActionCommand().equalsIgnoreCase(questions.get(currentQuestion).answer)) {
							lblScore.setText(String.valueOf("Score: "+(++score)+"/"+questions.size()));
							JOptionPane.showMessageDialog(frame, "Correct!");
					}
					else
						JOptionPane.showMessageDialog(frame, "Sorry, wrong answer :(");
					if(currentQuestion!=questions.size()-1) {
						answerPanel.removeAll();
						currentQuestion++;
						txtQuestionPane.setText(questions.get(currentQuestion).question);
						lblCategory.setText((currentQuestion+1)+".) "+questions.get(currentQuestion).category);
						setup();
						frame.repaint();
					}
					else {
						JOptionPane.showMessageDialog(frame, "End of questions!\n Your final score is: "+score);
						frame.dispose();
					}
			}
		});
		btnNext.setBounds(228, 245, 89, 23);
		frame.getContentPane().add(btnNext);
	}
	
	public void setup() {
		JRadioButton rdbtnAnswer; 
		answerGroup = new ButtonGroup();
		for(int i =0;i<questions.get(currentQuestion).answers.size();i++) {
			rdbtnAnswer = new JRadioButton(questions.get(currentQuestion).answers.get(i));
			rdbtnAnswer.setActionCommand(questions.get(currentQuestion).answers.get(i));
			answerGroup.add(rdbtnAnswer);
			rdbtnAnswer.setBounds(6, 7+(26*i), 200, 23);
			answerPanel.add(rdbtnAnswer);;
		}
	}
}
