import java.awt.*;
import javax.swing.*;

public class VotingMachineSimulator extends JFrame {
    private int votesA = 0, votesB = 0, votesC = 0;
    private int totalVotes = 0;
    private final int VOTE_LIMIT;
    private final String CANDIDATE_A;
    private final String CANDIDATE_B;
    private final String CANDIDATE_C;
    private JLabel lblVotesA, lblVotesB, lblVotesC;
    private JButton btnA, btnB, btnC, btnShowWinner;

    public VotingMachineSimulator(int voteLimit, String candA, String candB, String candC) {
        this.VOTE_LIMIT = voteLimit;
        this.CANDIDATE_A = candA;
        this.CANDIDATE_B = candB;
        this.CANDIDATE_C = candC;

        setTitle("Voting Machine Simulator");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("Electronic Voting Machine", JLabel.CENTER);
        heading.setFont(new Font("Verdana", Font.BOLD, 26));
        heading.setOpaque(true);
        heading.setBackground(new Color(52, 73, 94));
        heading.setForeground(Color.white);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(heading, BorderLayout.NORTH);

        JPanel votePanel = new JPanel(new GridLayout(1, 3, 20, 20));
        votePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        votePanel.setBackground(new Color(236, 240, 241));

        btnA = createVoteButton(CANDIDATE_A, new Color(41, 128, 185));
        btnB = createVoteButton(CANDIDATE_B, new Color(39, 174, 96));
        btnC = createVoteButton(CANDIDATE_C, new Color(192, 57, 43));

        votePanel.add(wrapButtonWithLabel(btnA, CANDIDATE_A));
        votePanel.add(wrapButtonWithLabel(btnB, CANDIDATE_B));
        votePanel.add(wrapButtonWithLabel(btnC, CANDIDATE_C));

        add(votePanel, BorderLayout.CENTER);

        JPanel resultPanel = new JPanel(new GridLayout(1, 4, 20, 20));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        resultPanel.setBackground(new Color(236, 240, 241));

        lblVotesA = createResultLabel(CANDIDATE_A + ": 0");
        lblVotesB = createResultLabel(CANDIDATE_B + ": 0");
        lblVotesC = createResultLabel(CANDIDATE_C + ": 0");

        btnShowWinner = new JButton("Show Winner");
        btnShowWinner.setFont(new Font("Arial", Font.BOLD, 16));
        btnShowWinner.setBackground(new Color(241, 196, 15));
        btnShowWinner.setForeground(Color.BLACK);
        btnShowWinner.addActionListener(e -> showWinner());

        resultPanel.add(lblVotesA);
        resultPanel.add(lblVotesB);
        resultPanel.add(lblVotesC);
        resultPanel.add(btnShowWinner);

        add(resultPanel, BorderLayout.SOUTH);
    }

    private JButton createVoteButton(String name, Color color) {
        JButton btn = new JButton("Vote " + name);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(color);
        btn.setForeground(Color.white);
        btn.setFocusPainted(false);

        btn.addActionListener(e -> {
            if (totalVotes < VOTE_LIMIT) {
                if (name.equals(CANDIDATE_A)) votesA++;
                else if (name.equals(CANDIDATE_B)) votesB++;
                else if (name.equals(CANDIDATE_C)) votesC++;
                totalVotes++;
                updateResults();
                if (totalVotes == VOTE_LIMIT) {
                    btnA.setEnabled(false);
                    btnB.setEnabled(false);
                    btnC.setEnabled(false);
                }
            }
        });
        return btn;
    }

    private JPanel wrapButtonWithLabel(JButton btn, String name) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(name, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(btn.getBackground(), 3));
        panel.add(label, BorderLayout.NORTH);
        panel.add(btn, BorderLayout.CENTER);
        return panel;
    }

    private JLabel createResultLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setOpaque(true);
        label.setBackground(Color.white);
        label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        return label;
    }

    private void updateResults() {
        lblVotesA.setText(CANDIDATE_A + ": " + votesA);
        lblVotesB.setText(CANDIDATE_B + ": " + votesB);
        lblVotesC.setText(CANDIDATE_C + ": " + votesC);
    }

    private void showWinner() {
        String winner;
        if (votesA > votesB && votesA > votesC) {
            winner = CANDIDATE_A;
        } else if (votesB > votesA && votesB > votesC) {
            winner = CANDIDATE_B;
        } else if (votesC > votesA && votesC > votesB) {
            winner = CANDIDATE_C;
        } else {
            winner = "It's a tie!";
        }
        String message = winner.equals("It's a tie!") ?
                "It's a tie! No winner." :
                "Congratulations! " + winner + " wins!";
        JOptionPane.showMessageDialog(this, message, "Result", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String voteLimitStr = JOptionPane.showInputDialog(null, "Enter the vote limit:", "Vote Limit", JOptionPane.QUESTION_MESSAGE);
            int voteLimit = 10;
            try {
                if (voteLimitStr != null) voteLimit = Integer.parseInt(voteLimitStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Using default vote limit: 10");
            }
            String candA = JOptionPane.showInputDialog(null, "Enter Candidate A's name:", "Candidate Name", JOptionPane.QUESTION_MESSAGE);
            if (candA == null || candA.trim().isEmpty()) candA = "Candidate A";
            String candB = JOptionPane.showInputDialog(null, "Enter Candidate B's name:", "Candidate Name", JOptionPane.QUESTION_MESSAGE);
            if (candB == null || candB.trim().isEmpty()) candB = "Candidate B";
            String candC = JOptionPane.showInputDialog(null, "Enter Candidate C's name:", "Candidate Name", JOptionPane.QUESTION_MESSAGE);
            if (candC == null || candC.trim().isEmpty()) candC = "Candidate C";
            new VotingMachineSimulator(voteLimit, candA, candB, candC).setVisible(true);
        });
    }
}