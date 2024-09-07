package testtype;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

class Test extends JFrame implements ActionListener {
    JLabel l;
    JRadioButton anchoice[] = new JRadioButton[5];
    JButton b1, b2;
    ButtonGroup bg;
    int count = 0, current = 0, x = 1, y = 1, now = 0;
    int m[] = new int[10];
    ArrayList<Question> questions = new ArrayList<>();
    String currentTopic;

    class Question {
        String question;
        String[] options;
        int correctAnswerIndex;

        Question(String q, String[] opts, int correctIndex) {
            question = q;
            options = opts;
            correctAnswerIndex = correctIndex;
        }
    }

    Test(String s, String topic) {
        super(s);
        currentTopic = topic;
        l = new JLabel();
        bg = new ButtonGroup();
        Font font = new Font("Arial", Font.PLAIN, 16); 
        for (int i = 0; i < 5; i++) {
            anchoice[i] = new JRadioButton();
            anchoice[i].setFont(font);
            bg.add(anchoice[i]);
        }
        b1 = new JButton("Next");
        b2 = new JButton("Bookmark");
        b1.setFont(font);
        b2.setFont(font);
        b1.addActionListener(this);
        b2.addActionListener(this);

        setLayout(new BorderLayout());
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new GridLayout(6, 1)); 
        l.setFont(font); 
        questionPanel.add(l);
        for (JRadioButton jButton : anchoice) {
            questionPanel.add(jButton);
        }
        add(questionPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(b1);
        buttonPanel.add(b2);
        add(buttonPanel, BorderLayout.SOUTH);
        
        loadQuestions();
        initializeUI();
    }

    void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(250, 100);
        setSize(600, 400);
        setVisible(true);
        set(); 
    }

    void loadQuestions() {
        String filename = "src/testtype/" + currentTopic + ".txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 6) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }
                String question = parts[0];
                String[] options = Arrays.copyOfRange(parts, 1, 5);
                int correctAnswerIndex = Integer.parseInt(parts[5]);
                questions.add(new Question(question, options, correctAnswerIndex));
            }
            System.out.println("Loaded " + questions.size() + " questions from " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            if (check()) count++;
            current++;
            if (current >= questions.size()) {
                current = questions.size() - 1; 
            }
            set();
            if (current == questions.size() - 1) {
                b1.setEnabled(false);
                b2.setText("Result");
            }
        }
        if (e.getActionCommand().equals("Bookmark")) {
            JButton bk = new JButton("Bookmark" + x);
            bk.setFont(new Font("Arial", Font.PLAIN, 16)); 
            bk.setPreferredSize(new Dimension(150, 30));
         
            JPanel bookmarkPanel = new JPanel();
            bookmarkPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
            bookmarkPanel.add(bk);
            add(bookmarkPanel, BorderLayout.NORTH);
            bk.addActionListener(this);
            m[x] = current;
            x++;
            current++;
            if (current >= questions.size()) {
                current = questions.size() - 1; 
            }
            set();
            if (current == questions.size() - 1) b2.setText("Result");
            revalidate();
            repaint();
        }
        for (int i = 0, y = 1; i < x; i++, y++) {
            if (e.getActionCommand().equals("Bookmark" + y)) {
                if (check()) count++;
                now = current;
                current = m[y];
                set();
                ((JButton) e.getSource()).setEnabled(false);
                current = now;
            }
        }
        if (e.getActionCommand().equals("Result")) {
            if (check()) count++;
            current++;
            JOptionPane.showMessageDialog(this, "Correct answers = " + count);
            System.exit(0);
        }
    }

    void set() {
        if (questions.isEmpty()) {
            l.setText("No questions available.");
            return;
        }
        if (current >= questions.size()) {
            l.setText("No more questions.");
            return;
        }
        Question q = questions.get(current);
        l.setText(q.question);
        for (int i = 0; i < q.options.length; i++) {
            anchoice[i].setText(q.options[i]);
            anchoice[i].setVisible(true); 
        }
        for (int i = q.options.length; i < anchoice.length; i++) {
            anchoice[i].setVisible(false); 
        }
        anchoice[4].setSelected(true);
        l.setPreferredSize(new Dimension(500, 20));
        revalidate();
        repaint();
    }

    boolean check() {
        Question q = questions.get(current);
        return anchoice[q.correctAnswerIndex].isSelected();
    }

    public static void main(String[] args) {
        JFrame topicSelector = new JFrame("Select Topic");
        topicSelector.setSize(400, 300);
        topicSelector.setLayout(new GridLayout(5, 1));
        topicSelector.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font font = new Font("Arial", Font.PLAIN, 16); 

        JButton osButton = new JButton("OS");
        JButton dbmsButton = new JButton("DBMS");
        JButton reactButton = new JButton("React");
        JButton oopsButton = new JButton("OOP");
        JButton compButton = new JButton("Compiler Design");

        osButton.setFont(font);
        dbmsButton.setFont(font);
        reactButton.setFont(font);
        oopsButton.setFont(font);
        compButton.setFont(font);

        osButton.addActionListener(e -> {
            topicSelector.setVisible(false);
            new Test("Online Test Of OS", "os");
        });
        dbmsButton.addActionListener(e -> {
            topicSelector.setVisible(false);
            new Test("Online Test Of DBMS", "dbms");
        });
        reactButton.addActionListener(e -> {
            topicSelector.setVisible(false);
            new Test("Online Test Of React", "react");
        });
        oopsButton.addActionListener(e -> {
            topicSelector.setVisible(false);
            new Test("Online Test Of OOP", "oops");
        });
        compButton.addActionListener(e -> {
            topicSelector.setVisible(false);
            new Test("Online Test Of Compiler Design", "comp");
        });

        topicSelector.add(osButton);
        topicSelector.add(dbmsButton);
        topicSelector.add(reactButton);
        topicSelector.add(oopsButton);
        topicSelector.add(compButton);

        topicSelector.setVisible(true);
    }
}
