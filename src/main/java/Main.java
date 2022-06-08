import javax.swing.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        new Main();
    }

    private final int X_Y_START = 0;
    private final int WITH_FRAME = 1300;
    private final int HEIGHT_FRAME = 1000;

    public Main() {
        this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBounds(X_Y_START, X_Y_START, WITH_FRAME, HEIGHT_FRAME);
        MainPanel mainPanel = new MainPanel();
        this.add(mainPanel);
        this.setVisible(true);
    }
}
