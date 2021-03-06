import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.List;

public class main {

    //encryption components
    private static final JPanel panelEC = new JPanel();
    private static final JPanel panelDC = new JPanel();
    private static final JLabel labelP = new JLabel();
    private static final JLabel labelQ = new JLabel();
    private static final JLabel labelPhi = new JLabel();
    private static final JLabel labelPhiFactors = new JLabel();
    private static final JLabel labelE = new JLabel();
    private static final JButton step2Button = new JButton("Step 2");
    private static final JLabel labelInputM = new JLabel("m:");
    private static final JTextArea textM = new JTextArea();
    private static final JButton step3Button = new JButton("Step 3");
    private static final JTextArea labelM = new JTextArea();
    private static final JTextArea labelC = new JTextArea();
    private static JScrollPane scrollerLabelM = new JScrollPane();
    private static JScrollPane scrollerLabelC = new JScrollPane();
    private static JScrollPane scrollerTextM = new JScrollPane();

    //decryption components
    private static final JButton step1DCButton = new JButton("Step 1");
    private static final JLabel labelDED = new JLabel();
    private static final JLabel labelDEInputC = new JLabel("c:");
    private static final JTextArea textDEC = new JTextArea();
    private static JScrollPane scrollerLabelDEC = new JScrollPane();
    private static final JButton step2DCButton = new JButton("Step 2");
    private static final JTextArea labelDEM = new JTextArea();
    private static JScrollPane scrollerLabelDEM = new JScrollPane();

    //error tooltip
    private static final JLabel errorLabelDecrypt = new JLabel("");
    private static final JLabel errorLabelEncrypt = new JLabel("");

    //encryption variables
    private static BigInteger n;
    private static BigInteger p;
    private static BigInteger q;
    private static BigInteger phi;
    private static BigInteger[] phiPrimes;
    private static BigInteger e;
    private static BigInteger[] m;
    private static BigInteger[] c;

    //decryption variables
    private static BigInteger deN;
    private static BigInteger deE;
    private static BigInteger deP;
    private static BigInteger deQ;
    private static BigInteger dePhi;
    private static BigInteger deD;
    private static BigInteger[] deC;
    private static char[] deM;

    public static void main(String[] args) {

        // Creating instance of JFrame
        JFrame frame = new JFrame("Encrypt/decrypt");

        // Setting the width and height of frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridLayout myLayout = new GridLayout(1,2);
        myLayout.setHgap(10);

        frame.setLayout(myLayout);

        panelEC.setBackground(Color.LIGHT_GRAY);
        panelEC.setSize(250, 500);

        panelDC.setBackground(Color.LIGHT_GRAY);
        panelDC.setSize(250, 500);

        placeComponentsEC(panelEC);
        placeComponentsDC(panelDC);

        frame.add(panelEC);
        frame.add(panelDC);

//        frame.pack();
        frame.setSize(800, 800);
        frame.setMinimumSize(new Dimension(800, 800));
        frame.setVisible(true);
    }

    private static void placeComponentsDC(JPanel panel) {
        /* We will discuss about layouts in the later sections
         * of this tutorial. For now we are setting the layout
         * to null
         */
        panel.setLayout(null);

        // Creating JLabel
        JLabel titleEncryption = new JLabel("Decryption");
        titleEncryption.setFont(new Font("Calibri", Font.BOLD, 30));
        titleEncryption.setBounds(10,10,300,30);
        panel.add(titleEncryption);

        // Creating JLabel
        JLabel labelDEInputN = new JLabel("n:");
        labelDEInputN.setBounds(10,60,20,25);
        panel.add(labelDEInputN);

        JTextField textDEN = new JTextField(20);
        textDEN.setBounds(40,60,70,25);
        panel.add(textDEN);

        JLabel labelDEInputE = new JLabel("e:");
        labelDEInputE.setBounds(10,90,20,25);
        panel.add(labelDEInputE);

        JTextField textDEE = new JTextField(20);
        textDEE.setBounds(40,90,70,25);
        panel.add(textDEE);

        step1DCButton.setBounds(10,120,100,25);
        step1DCButton.addActionListener(e -> step1DC(textDEN.getText(), textDEE.getText()));
        panel.add(step1DCButton);

        labelDED.setBounds(10,150,300,25);
        panel.add(labelDED);

        labelDEInputC.setBounds(10,180,20,25);
        labelDEInputC.hide();
        panel.add(labelDEInputC);

        textDEC.setBounds(10,210,300,50);
        textDEC.setLineWrap(true);
        textDEC.hide();
        panel.add(textDEC);

        scrollerLabelDEC = new JScrollPane(textDEC, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollerLabelDEC.setBounds(10,210,300,50);
        scrollerLabelDEC.hide();
        panel.add(scrollerLabelDEC);

        step2DCButton.setBounds(10,260,100,25);
        step2DCButton.addActionListener(e -> step2DC(textDEC.getText()));
        step2DCButton.hide();
        panel.add(step2DCButton);

        labelDEM.setBounds(10,290,300,300);
        labelDEM.setEditable(false);
        labelDEM.setLineWrap(true);
        labelDEM.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(labelDEM);

        scrollerLabelDEM = new JScrollPane(labelDEM, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollerLabelDEM.setBounds(10,290,300,300);
        scrollerLabelDEM.hide();
        panel.add(scrollerLabelDEM);

        errorLabelDecrypt.setBounds(10, 35, 500, 20);
        errorLabelDecrypt.setForeground(Color.RED);
        panel.add(errorLabelDecrypt);
    }

    private static void placeComponentsEC(JPanel panel) {

        panel.setLayout(null);

        // Creating JLabel
        JLabel titleEncryption = new JLabel("Encryption");
        titleEncryption.setFont(new Font("Calibri", Font.BOLD, 30));
        titleEncryption.setBounds(10,10,300,30);
        panel.add(titleEncryption);

        // Creating JLabel
        JLabel labelInputN = new JLabel("n:");
        labelInputN.setBounds(10,60,20,25);
        panel.add(labelInputN);

        JTextField textN = new JTextField(20);
        textN.setBounds(40,60,70,25);
        panel.add(textN);

        // Creating step1 button
        JButton step1Button = new JButton("Step 1");
        step1Button.setBounds(10,90,100,25);
        step1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                step1(textN.getText());
            }
        });
        panel.add(step1Button);

        labelP.setBounds(10,120,300,25);
        panel.add(labelP);
        labelQ.setBounds(10,145,300,25);
        panel.add(labelQ);
        labelPhi.setBounds(10,170,300,25);
        panel.add(labelPhi);
        labelPhiFactors.setBounds(10,195,300,25);
        panel.add(labelPhiFactors);

        step2Button.setBounds(10,220,100,25);
        step2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                step2();
            }
        });
        step2Button.hide();
        panel.add(step2Button);

        labelE.setBounds(10,250,100,25);
        panel.add(labelE);

        // Creating JLabel
        labelInputM.setBounds(10,280,20,25);
        labelInputM.hide();
        panel.add(labelInputM);

        textM.setBounds(10,310,300,100);
        textM.setLineWrap(true);
        textM.hide();
        panel.add(textM);

        scrollerTextM = new JScrollPane(textM, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollerTextM.setBounds(10,310,300,100);
        scrollerTextM.hide();
        panel.add(scrollerTextM);

        step3Button.setBounds(10,420,100,25);
        step3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                step3(textM.getText());
            }
        });
        step3Button.hide();
        panel.add(step3Button);

        labelM.setBounds(10,450,200,50);
        labelM.setLineWrap(true);
        labelM.setEditable(false);
        labelM.hide();
        labelM.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(labelM);

        scrollerLabelM = new JScrollPane(labelM, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollerLabelM.setBounds(10,450,300,100);
        scrollerLabelM.hide();
        panel.add(scrollerLabelM);

        labelC.setBounds(10,555,300,200);
        labelC.setLineWrap(true);
        labelC.setEditable(false);
        labelC.hide();
        labelC.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(labelC);

        scrollerLabelC = new JScrollPane(labelC, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollerLabelC.setBounds(10,555,300,200);
        scrollerLabelC.hide();
        panel.add(scrollerLabelC);

        errorLabelEncrypt.setBounds(10, 35, 300, 20);
        errorLabelEncrypt.setForeground(Color.RED);
        panel.add(errorLabelEncrypt);
    }

    private static void step1(String stringN){

        errorLabelEncrypt.setText("");
        
        n = convertToBigInteger(stringN);
        long startTime = System.currentTimeMillis();
        BigInteger[] primesN = findPrimeFactors(n);
        long endTime = System.currentTimeMillis();
        System.out.println("Amount of time busy finding p and q: " + (endTime-startTime));
        if (primesN.length == 2 && n.signum() == 1){
            p = primesN[1];
            q = primesN[0];

            phi = calculatePhi(p, q);
            labelPhi.setText("phi is " + phi);

            phiPrimes = findPrimeFactors(phi);
            labelPhiFactors.setText("phi prime factors are " + Arrays.toString(phiPrimes));

            labelP.setText("p is " + p);
            labelQ.setText("q is " + q);
            step2Button.show();
        }
        else if(n.signum() != 1){
            errorLabelEncrypt.setText("Error: non-numeric value");
        }
        else if(primesN.length > 2){
            errorLabelEncrypt.setText("Error: Too many n primes");
        }
        else errorLabelEncrypt.setText("Error: not enough n primes");
    }

    private static void step2(){

        errorLabelEncrypt.setText("");

        e = generateE();
        labelE.setText("e is " + e);
        labelInputM.show();
        textM.show();
        scrollerTextM.show();
        step3Button.show();
    }

    private static void step3(String mText){

        errorLabelEncrypt.setText("");

        m = convertToBigIntegerArray(mText);
        labelM.show();
        scrollerLabelM.show();
        labelM.setText("m is " + Arrays.toString(m));
        labelM.getPreferredSize();

        c = encryptMessage();
        labelC.show();
        scrollerLabelC.show();
        labelC.setText("c is " + Arrays.toString(c));
    }

    private static void step1DC(String stringN, String stringE){

        errorLabelDecrypt.setText("");

        deN = convertToBigInteger(stringN);
        deE = convertToBigInteger(stringE);

        if(deN.compareTo(new BigInteger("-1")) != 0 && deE.compareTo(new BigInteger("-1")) != 0 ) {

            BigInteger[] primesN = findPrimeFactors(deN);
            if (primesN.length == 2){
                deP = primesN[1];
                deQ = primesN[0];

                dePhi = calculatePhi(deP, deQ);

                deD = calculateD();
                labelDED.setText("d is " + deD);

                labelDEInputC.show();
                textDEC.show();
                scrollerLabelDEC.show();
                step2DCButton.show();
            }
            else {
                errorLabelDecrypt.setText("Error: Exactly 2 prime factors required for N");
            }
        }
        else errorLabelDecrypt.setText("Error: Proper n and e required");
    }

    private static void step2DC(String stringN){

        errorLabelDecrypt.setText("");

        deC = convertCodeToBigIntegerArray(stringN);
        deM = decryptMessage();

        String messageResult = "";
        for (char c : deM){
            messageResult += c;
        }
        labelDEM.setText("Message after decryption is: " + messageResult);
        labelDEM.show();
        scrollerLabelDEM.show();
        System.out.println("Message after decryption is: " + messageResult);
    }

    private static BigInteger convertToBigInteger(String stringN){
        try {
            return new BigInteger(stringN);
        } catch (NumberFormatException nfe) {
            return new BigInteger("-1");
        }
    }

    private static BigInteger[] findPrimeFactors(BigInteger x) {
        List<BigInteger> factorList = new ArrayList<BigInteger>();
        if (x.signum() == 1){//should be true when not -1

            BigInteger originalX = x;

            BigInteger bi2 = new BigInteger("2");
            while (x.mod(bi2).equals(BigInteger.ZERO)) {
                factorList.add(bi2);
                x = x.divide(bi2);
            }

            // skip one element (Note i = i +2)
            for (BigInteger bi = BigInteger.valueOf(3);
                 bi.multiply(bi).compareTo(originalX) <= 0; //for looping untill the higher then the square root
                 bi = bi.add(bi2)) {
                // While i divides n, print i and divide n
                while (x.mod(bi).equals(BigInteger.ZERO)) {
                    factorList.add(bi);
                    x = x.divide(bi);
                }
            }

            // This condition is to handle the case when
            // n is a prime number greater than 2
            if(x.compareTo(BigInteger.valueOf(2)) >= 0){
                factorList.add(x);
            }
        }
        else {
            errorLabelEncrypt.setText("Error: non-numeric value");
        }

        BigInteger[] result = new BigInteger[factorList.size()];
        for(int i = 0; i < factorList.size(); i++){
            result[i] = factorList.get(i);
        }
        return result;
    }

    private static BigInteger calculatePhi(BigInteger localP, BigInteger localQ){
        return localP.subtract(BigInteger.ONE).multiply((localQ.subtract(BigInteger.ONE)));
    }

    private static BigInteger generateE() {
        BigInteger x = BigInteger.ZERO;
        BigInteger gcdEPhi = BigInteger.ZERO;

        while (x.compareTo(BigInteger.ZERO) == 0 || gcdEPhi.compareTo(BigInteger.ONE) != 0 || x.compareTo(n) > 0){
            BigInteger maxLimit = phi;
            Random randNum = new Random();

            int len = maxLimit.bitLength();
            x = new BigInteger(len, randNum);

            //make sure it is uneven
            if (x.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)){
                x = x.add(BigInteger.ONE);
            }

            gcdEPhi = x.gcd(phi);
        }
        return x;
    }

    private static BigInteger[] convertToBigIntegerArray(String mText) {
        BigInteger[] x = new BigInteger[mText.length()];
        char[] ch = mText.toCharArray();
        for(int i = 0; i < mText.length(); i++) {
            x[i] = BigInteger.valueOf(ch[i]);
        }
        return x;
    }

    private static BigInteger[] encryptMessage() {
        BigInteger[] x = new BigInteger[m.length];
        for(int i = 0 ; i < m.length; i++){
            x[i] = m[i].modPow(e, n);
        }
        return x;
    }

    private static BigInteger calculateD(){
        long startTime = System.currentTimeMillis();
        BigDecimal k = BigDecimal.ONE;
        BigDecimal localD = BigDecimal.ZERO;
        while (k.compareTo(BigDecimal.ONE) == 0 || !isIntegerValue(localD)){
            localD = k
                    .multiply(new BigDecimal(dePhi))
                    .add(BigDecimal.ONE)
                    .divide(new BigDecimal(deE),100, RoundingMode.HALF_UP);
            k = k.add(BigDecimal.ONE);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Amount of time busy finding d: " + (endTime-startTime));
        return localD.toBigInteger();
    }

    private static boolean isIntegerValue(BigDecimal bd) {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }

    private static BigInteger[] convertCodeToBigIntegerArray(String stringCode){
        stringCode = stringCode
                .replaceAll("\\s", "")
                .replaceAll("\\[", "")
                .replaceAll("]", "");
        String[] arrOfStr = stringCode.split(",");
        BigInteger[] result =  new BigInteger[arrOfStr.length];

        for (int i = 0; i < arrOfStr.length ; i++){
            result[i] = new BigInteger(arrOfStr[i]);
        }

        return result;
    }

    private static char[] decryptMessage() {
        char[] x = new char[deC.length];

        for(int i = 0 ; i < deC.length; i++){
            x[i] = (char) deC[i].pow(deD.intValue()).mod(deN).intValue();
        }
        return x;
    }
}