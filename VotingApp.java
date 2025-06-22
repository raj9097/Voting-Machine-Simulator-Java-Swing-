import java.awt.*;
import javax.swing.*;
import java.util.*;

public class VotingApp extends JFrame {
    private int[] votes;
    private int totalVotes = 0;
    private final int VOTE_LIMIT;
    private final String[] candidateNames;
    private JLabel[] lblVotes;
    private JButton[] voteButtons;
    private JButton btnShowWinner;

    public VotingApp(int voteLimit, String[] candidateNames) {
        this.VOTE_LIMIT = voteLimit;
        this.candidateNames = candidateNames;
        this.votes = new int[candidateNames.length];
        this.lblVotes = new JLabel[candidateNames.length];
        this.voteButtons = new JButton[candidateNames.length];

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

        JPanel votePanel = new JPanel(new GridLayout(1, candidateNames.length, 20, 20));
        votePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        votePanel.setBackground(new Color(236, 240, 241));

        for (int i = 0; i < candidateNames.length; i++) {
            final int idx = i;
            voteButtons[i] = new JButton("Vote " + candidateNames[i]);
            voteButtons[i].setFont(new Font("Arial", Font.BOLD, 16));
            voteButtons[i].setBackground(new Color(41 + (i * 30) % 200, 128, 185 - (i * 30) % 100));
            voteButtons[i].setForeground(Color.white);
            voteButtons[i].setFocusPainted(false);
            voteButtons[i].addActionListener(e -> {
                if (totalVotes < VOTE_LIMIT) {
                    votes[idx]++;
                    totalVotes++;
                    updateResults();
                    if (totalVotes == VOTE_LIMIT) {
                        for (JButton btn : voteButtons) btn.setEnabled(false);
                    }
                }
            });
            votePanel.add(wrapButtonWithLabel(voteButtons[i], candidateNames[i]));
        }
        add(votePanel, BorderLayout.CENTER);

        JPanel resultPanel = new JPanel(new GridLayout(1, candidateNames.length + 1, 20, 20));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        resultPanel.setBackground(new Color(236, 240, 241));

        for (int i = 0; i < candidateNames.length; i++) {
            lblVotes[i] = createResultLabel(candidateNames[i] + ": 0");
            resultPanel.add(lblVotes[i]);
        }

        btnShowWinner = new JButton("Show Winner");
        btnShowWinner.setFont(new Font("Arial", Font.BOLD, 16));
        btnShowWinner.setBackground(new Color(241, 196, 15));
        btnShowWinner.setForeground(Color.BLACK);
        btnShowWinner.addActionListener(e -> showWinner());
        resultPanel.add(btnShowWinner);

        add(resultPanel, BorderLayout.SOUTH);
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
        for (int i = 0; i < candidateNames.length; i++) {
            lblVotes[i].setText(candidateNames[i] + ": " + votes[i]);
        }
    }

    private void showWinner() {
        int maxVotes = Arrays.stream(votes).max().orElse(0);
        java.util.List<String> winners = new ArrayList<>();
        for (int i = 0; i < votes.length; i++) {
            if (votes[i] == maxVotes) {
                winners.add(candidateNames[i]);
            }
        }
        String message = winners.size() == 1
                ? "Congratulations! " + winners.get(0) + " wins!"
                : "It's a tie between: " + String.join(", ", winners);
        JOptionPane.showMessageDialog(this, message, "Result", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int voteLimit = 10;
            int numCandidates = 3;
            try {
                String voteLimitStr = JOptionPane.showInputDialog(null, "Enter the vote limit:", "Vote Limit", JOptionPane.QUESTION_MESSAGE);
                if (voteLimitStr != null) voteLimit = Integer.parseInt(voteLimitStr);
                String numCandidatesStr = JOptionPane.showInputDialog(null, "Enter number of candidates:", "Number of Candidates", JOptionPane.QUESTION_MESSAGE);
                if (numCandidatesStr != null) numCandidates = Integer.parseInt(numCandidatesStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Using defaults.");
            }
            String[] candidateNames = new String[numCandidates];
            for (int i = 0; i < numCandidates; i++) {
                String name = JOptionPane.showInputDialog(null, "Enter name for candidate " + (i + 1) + ":", "Candidate Name", JOptionPane.QUESTION_MESSAGE);
                if (name == null || name.trim().isEmpty()) name = "Candidate " + (i + 1);
                candidateNames[i] = name;
            }
            new VotingApp(voteLimit, candidateNames).setVisible(true);
        });
    }
}