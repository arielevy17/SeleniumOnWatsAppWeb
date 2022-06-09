import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import javax.xml.bind.Element;
import java.awt.*;
import java.io.File;
import java.util.List;

public class MainPanel extends JPanel {

   private final int WITH_FRAME = 1300;
    private final int HEIGHT_FRAME = 1000;
    private final int WITH_BUTTON = 250;
    private final int HIGTH_BUTTON = 70;
    private final int X_Y_START =0;
    private final int FIELD_WITH =200;
    private final int FIELD_HEIGHT =70;
    private final int DISTANCE =200;
    private final int FONT_SIZE= 18;


    private ImageIcon backGround;
    private boolean loopConnectStatus=true;
    private JTextField  messageStatus;
    private Font font=new Font("",Font.BOLD,FONT_SIZE);
    private Font fontStatus=new Font("",Font.BOLD,FONT_SIZE*2);
    private WebElement lastMessageStatus;
    private ChromeDriver driver;

    public MainPanel(){

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\lenovo\\Desktop\\intelij\\chromedriver.exe");
        if (new File("C:\\Users\\lenovo\\Desktop\\intelij").exists()) { // בדיקה האם קובץ קיים
            System.out.println("OK");
        }
                  //   הגדרות הפאנל והרקע

        this.setLayout(null);
        this.setBounds(X_Y_START,X_Y_START,WITH_FRAME, HEIGHT_FRAME);
        this.setVisible(true);

        backGround =new ImageIcon("Whatsapp-logo-background.jpg");
        repaint();
        //  כפתור התחברות
        JButton connectButton =new JButton("Connect to my whatsApp");
        connectButton.setBounds((WITH_FRAME/2)-(WITH_BUTTON/2),(HEIGHT_FRAME /5)*4, WITH_BUTTON, HIGTH_BUTTON);
        connectButton.setBackground(Color.GREEN);
        connectButton.setVisible(true);



            connectButton.addActionListener((event1 -> {
                this.driver = new ChromeDriver();
                driver.get("https://web.whatsapp.com/");


                //  בדיקת התחברות
                checkConnecting(driver);
                //  הודעת התחברות
                JLabel connectMessage = new JLabel("you connect successfully!");
                connectMessage.setBounds((WITH_FRAME / 2) - (FIELD_WITH / 2), HIGTH_BUTTON, FIELD_WITH * 2, FIELD_HEIGHT);
                connectMessage.setFont(font);
                connectMessage.setForeground(Color.GREEN);
                connectMessage.setVisible(true);
                this.add(connectMessage);

                //  תיבת לכתיבת מספר הפלאפון+ טקסט נילווה
                JTextField phoneNumberField = new JTextField();
                phoneNumberField.setBounds(WITH_FRAME / 4, connectMessage.getY() + DISTANCE, FIELD_WITH, FIELD_HEIGHT);
                phoneNumberField.setFont(font);
                phoneNumberField.setVisible(true);
                this.add(phoneNumberField);

                JLabel phoneNumberText = new JLabel("Enter a phone number to send:");
                phoneNumberText.setBounds(phoneNumberField.getX(), phoneNumberField.getY() - (DISTANCE / 4), FIELD_WITH * 2, FIELD_HEIGHT);
                phoneNumberText.setFont(font);
                phoneNumberText.setVisible(true);
                phoneNumberText.setForeground(Color.GRAY);
                this.add(phoneNumberText);

                //  תיבת טקס לכתיבת הודעה + טקסט נילווה
                JTextArea messageFiled = new JTextArea();
                messageFiled.setBounds(phoneNumberField.getX() + FIELD_WITH + DISTANCE, phoneNumberField.getY(), FIELD_WITH * 2, FIELD_HEIGHT * 2);
                messageFiled.setFont(font);
                messageFiled.setVisible(true);
                this.add(messageFiled);

                JLabel messageText = new JLabel("Enter your message:");
                messageText.setBounds(messageFiled.getX(), phoneNumberText.getY(), FIELD_WITH, FIELD_HEIGHT);
                messageText.setFont(font);
                messageText.setForeground(Color.GRAY);
                messageText.setVisible(true);
                this.add(messageText);

                //  תיבת סטטוס קבלת הודעה
                messageStatus = new JTextField("  V");
                messageStatus.setBounds(phoneNumberField.getX(), connectMessage.getY() + DISTANCE * 2, FIELD_WITH, FIELD_WITH / 2);
                messageStatus.setBackground(Color.black);
                messageStatus.setForeground(Color.WHITE);
                messageStatus.setFont(fontStatus);
                messageStatus.setVisible(true);
                this.add(messageStatus);
                JLabel statusMessageText = new JLabel("Message Status:");
                statusMessageText.setBounds(messageStatus.getX(), messageStatus.getY() - FIELD_HEIGHT, FIELD_WITH, FIELD_HEIGHT);
                statusMessageText.setFont(font);
                statusMessageText.setForeground(Color.gray);
                statusMessageText.setVisible(true);
                this.add(statusMessageText);

                //  כפתור שליחת הודעה למספר
                JButton sendMessageButton = new JButton("send");
                sendMessageButton.setBounds((WITH_FRAME / 2) - (WITH_BUTTON / 2), connectButton.getY() - DISTANCE / 2, WITH_BUTTON, HIGTH_BUTTON);
                sendMessageButton.setVisible(true);
                sendMessageButton.setBackground(Color.CYAN);
                sendMessageButton.requestFocus();
                //  הודעת מצב שליחת ההודעה
                JLabel isItSendText = new JLabel("The message didnt send yet..");
                isItSendText.setForeground(Color.RED);
                isItSendText.setBounds(connectMessage.getX(), connectMessage.getY() + HIGTH_BUTTON / 2, FIELD_WITH * 3, FIELD_HEIGHT);
                isItSendText.setFont(font);
                isItSendText.setVisible(true);
                this.add(isItSendText);

                //  כפתור שליחה פעולות:
                sendMessageButton.addActionListener((event2 -> {
                    isItSendText.setForeground(Color.RED);
                    isItSendText.setText("The message didnt send yet..");
                    String phoneNumberCheat = phoneNumberField.getText();
                    //    ולידציה לתיבת הטקסט
                    if (messageFiled.getText().equals("")) {
                        JFrame wrongMessageFrame = new JFrame();
                        wrongMessageFrame.setSize(WITH_FRAME / 4, HEIGHT_FRAME / 4);
                        wrongMessageFrame.setBackground(Color.RED);
                        JLabel wrongFrameText = new JLabel("Your message is empty!");
                        wrongFrameText.setFont(font);
                        wrongFrameText.setVisible(true);
                        wrongMessageFrame.add(wrongFrameText);
                        wrongMessageFrame.setVisible(true);
                    } else {
                        //    ולידציה למספר
                        if (checkPhoneNumber(phoneNumberCheat)) {
                            phoneNumberCheat = phoneNumberField.getText().substring(1);
                            String urlChat = "https://web.whatsapp.com/send?phone=" + phoneNumberCheat;
                            boolean isItSend = false;
                            try {
                                driver.get(urlChat);  //  כניסה לעמוד הצ'אט
                            } catch (Exception e) {
                                System.out.println("the link to the chat is illegal!");
                            }
                            while (!isItSend) {  //  ההודעה לא נשלחת עד שלא כל העמוד נטען
                                try {
                                    WebElement cheatField = driver.findElement(By.cssSelector("div[title=\"הקלדת ההודעה\"]"));
                                    cheatField.sendKeys(messageFiled.getText());  //  הדבקת ההודעה
                                    WebElement sendButton = driver.findElement(By.cssSelector("span[data-testid=\"send\"]"));
                                    sendButton.click(); //  שליחה
                                    isItSend = true;
                                    System.out.println("send successfully!");
                                    isItSendText.setForeground(Color.GREEN);
                                    isItSendText.setText("massage send successfully!");
                                    System.out.println(messageStatus.getText());
                                    messageStatus.setVisible(true);

                                } catch (Exception e) {
                                    System.out.println("something wrong..  try again");
                                }
                            }

                        }
                        //   סטטוס ההודעה
                        List<WebElement> allMessages = driver.findElements(By.cssSelector("span[data-testid=\"msg-dblcheck\"]"));
                        try {  //  מניעת קריסה באם לא אותר סטטוס הודעה
                            lastMessageStatus = allMessages.get(allMessages.size() - 1);
                            // מישום שמהירות המחשב גבוה מהסלניום לעיתים ההודעה לא מספיקה לעדכן את הסטטוס וניתן סטטוס ההודעה הקודמת.  אם כחול בעייתי לגבי הודעה קודמת אם וי אחד זה כבר הסטטוס הקיים כברירת מחדל ולפני שהופך לוי כחול חייב להיות סטטוס נמסרה קודם
                            while (!lastMessageStatus.getAttribute("aria-label").equals(" נמסרה ")) {
                                allMessages = driver.findElements(By.cssSelector("span[data-testid=\"msg-dblcheck\"]"));
                                lastMessageStatus = allMessages.get(allMessages.size() - 1);
                            }
                            for (int i = 0; i < allMessages.size(); i++) {
                                System.out.println(allMessages.get(i).getAttribute("aria-label"));
                            }
                            System.out.println("----------------------------");
                            System.out.println(lastMessageStatus.getAttribute("aria-label"));

                            Thread thread=new Thread(() -> {
                                try {
                                    while (true) {  //  לולאה שנייה ע"מ לבדוק אם יש וי כחול ותמשיך לבדוק עד שההודעה לא תיקרא

                                        if (lastMessageStatus.getAttribute("aria-label").equals(" נקראה ")) {
                                            messageStatus.setForeground(Color.CYAN);
                                            messageStatus.setText("  VV");
                                            messageStatus.setVisible(true);
                                            System.out.println(2);
                                            System.out.println(messageStatus.getText());
                                            break;
                                        }
                                        if (lastMessageStatus.getAttribute("aria-label").equals(" נמסרה ")) {
                                            messageStatus.setText("  VV");
                                            messageStatus.setVisible(true);
                                        }
                                        System.out.println(lastMessageStatus.getAttribute("aria-label"));
                                        Thread.sleep(2000);
                                    }
                                }catch (Exception e){
                                    System.out.println("something wrong");
                                }
                            });

                            thread.start();

                        } catch (Exception e) {
                            System.out.println("the message you send is not the last message in the chat");
                        }
                    }

                }));

                this.add(sendMessageButton);

            }));
            this.add(connectButton);
            connectButton.requestFocus();


    }


    public void setLoopConnectStatus(boolean loopConnectStatus) {
        this.loopConnectStatus = loopConnectStatus;
    }

    public void checkConnecting(ChromeDriver driver){
         try {
             while (this.loopConnectStatus){
                WebElement elementToCheckConnection= driver.findElement(By.className("_3yRMq"));
                 setLoopConnectStatus(false);
                 System.out.println("success");
             }
         } catch (Exception e){
             checkConnecting(driver);
         }

    }

    public boolean checkPhoneNumber(String phoneNumber) {
        boolean ans = false;
        int condition = 0;
        int correctNumber = 0;
        char [] allNumbersArr = {'0', '9', '8', '7', '6', '5', '4', '3', '2', '1'};
        char [] arrAreaCodeNum = {'0', '2', '3', '4', '5', '8'};
        if (phoneNumber.equals("")) {
            JFrame wrongMessageFrame = new JFrame();
            wrongMessageFrame.setSize(WITH_FRAME / 5, HEIGHT_FRAME / 5);
            wrongMessageFrame.setBackground(Color.RED);
            JLabel wrongFrameText = new JLabel("You didnt enter a phone number!");
            wrongFrameText.setSize(WITH_FRAME/5,HEIGHT_FRAME/7);
            wrongFrameText.setFont(font);
            wrongFrameText.setVisible(true);
            wrongMessageFrame.add(wrongFrameText);
            wrongMessageFrame.setVisible(true);
        }
        if (phoneNumber.length() == 10) {
            condition++;
        }
        if (phoneNumber.charAt(0) == '0') {
            condition++;
        }
        if (phoneNumber.charAt(1) == '5') {
            condition++;
        }

        for (int i = 0; i < arrAreaCodeNum.length; i++) {
            if (phoneNumber.charAt(2) == arrAreaCodeNum[i]) {
                condition++;
                break;
            }
        }
        for (int i = 3; i < phoneNumber.length(); i++) {
            for (int j = 0; j < allNumbersArr.length; j++) {
                if (phoneNumber.charAt(i) == allNumbersArr[j]) {
                    correctNumber++;
                }
            }
        }
        if (condition == 4 && correctNumber == 7) {
            ans = true;
        } else {
            JFrame wrongMessageFrame = new JFrame();
            wrongMessageFrame.setSize(WITH_FRAME / 3 , HEIGHT_FRAME / 4);
            JTextArea wrongFrameText = new JTextArea("Your phone number is illegal!"+"\n your number need to see like this: 05*-*******");
            wrongFrameText.setFont(font);
            wrongFrameText.setBackground(Color.RED);
            wrongFrameText.setVisible(true);
            wrongMessageFrame.add(wrongFrameText);
            wrongMessageFrame.setVisible(true);
        }
        return ans;
    }


    // מתודות ציור(גרפיקה)  עובד רק על פאנל!!
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.backGround.paintIcon(this, g, X_Y_START, X_Y_START);
        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();

    }

    private void doDrawing(Graphics g) {  // מתודת גרפיקה שמציירת את התמונה- בלעדיה אין תמונה-חשוב מאוד!!!
        Graphics2D g2d = (Graphics2D) g;
    }


    }









