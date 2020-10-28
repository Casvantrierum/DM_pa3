import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.util.*;
import java.util.List;

public class main {

    //encryption components
    private static JPanel panelEC = new JPanel();
    private static JPanel panelDC = new JPanel();
    private static JLabel labelP = new JLabel();
    private static JLabel labelQ = new JLabel();
    private static JLabel labelPhi = new JLabel();
    private static JLabel labelPhiFactors = new JLabel();
    private static JLabel labelE = new JLabel();
    private static JButton step2Button = new JButton("Step 2");
    private static JLabel labelInputM = new JLabel("m:");
    private static JTextField textM = new JTextField(20);
    private static JButton step3Button = new JButton("Step 3");
    private static JTextArea labelM = new JTextArea();
    private static JTextArea labelC = new JTextArea();

    //decryption components
    private static JButton step1DCButton = new JButton("Step 1");
    private static JLabel labelDED = new JLabel();
    private static JLabel labelDEInputC = new JLabel("c:");
    private static JTextField textDEC = new JTextField(20);
    private static JButton step2DCButton = new JButton("Step 2");
    private static JLabel labelDEM = new JLabel();

    //error tooltip
    private static JLabel errorLabelD = new JLabel("-");
    private static JLabel errorLabelE = new JLabel("-");

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
        frame.setSize(600, 600);
        frame.setMinimumSize(new Dimension(600, 600));
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
        step1DCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                step1DC(textDEN.getText(), textDEE.getText());
            }
        });
        panel.add(step1DCButton);

        labelDED.setBounds(10,150,300,25);
        panel.add(labelDED);

        labelDEInputC.setBounds(10,180,20,25);
        labelDEInputC.hide();
        panel.add(labelDEInputC);

        textDEC.setBounds(40,180,210,25);
        textDEC.hide();
        panel.add(textDEC);

        step2DCButton.setBounds(10,210,100,25);
        step2DCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                step2DC(textDEC.getText());
            }
        });
        step2DCButton.hide();
        panel.add(step2DCButton);

        labelDEM.setBounds(10,240,300,25);
        panel.add(labelDEM);

        errorLabelD.setBounds(10, 35, 500, 20);
        panel.add(errorLabelD);
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

        textM.setBounds(40,280,70,25);
        textM.hide();
        panel.add(textM);

        step3Button.setBounds(10,310,100,25);
        step3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                step3(textM.getText());
            }
        });
        step3Button.hide();
        panel.add(step3Button);

        labelM.setBounds(10,340,200,50);
        labelM.setLineWrap(true);
        labelM.setEditable(false);
        labelM.hide();
        panel.add(labelM);

        labelC.setBounds(10,395,200,200);
        labelC.setLineWrap(true);
        labelC.setEditable(false);
        labelC.hide();
        panel.add(labelC);

        errorLabelE.setBounds(10, 35, 500, 20);
        panel.add(errorLabelE);
    }

    private static void step1(String stringN){
        n = convertToBigInteger(stringN);
        long startTime = System.currentTimeMillis();
        BigInteger[] primesN = findPrimeFactors(n);
        long endTime = System.currentTimeMillis();
        System.out.println("Amount of time busy finding p and q: " + (endTime-startTime));
        if (primesN.length == 2){
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
        else if(primesN.length > 2){
            errorLabelE.setText("Error: TOO MANY N PRIMES");
            //System.out.println("TOO MANY N PRIMES");
        }
        else errorLabelE.setText("Error: NOT ENOUGH N PRIMES");
            //System.out.println("NOT ENOUGH N PRIMES");
    }

    private static void step2(){
        e = generateE();
        labelE.setText("e is " + e);
        labelInputM.show();
        textM.show();
        step3Button.show();
    }

    private static void step3(String mText){
        m = convertToBigIntegerArray(mText);
        labelM.show();
        labelM.setText("m is " + Arrays.toString(m));

        c = encryptMessage();
        labelC.show();
        labelC.setText("c is " + Arrays.toString(c));
    }

    private static void step1DC(String stringN, String stringE){

        deN = convertToBigInteger(stringN);
        deE = convertToBigInteger(stringE);

        if(deN.compareTo(new BigInteger("-1")) != 0 && deE.compareTo(new BigInteger("-1")) != 0 ) {

            BigInteger[] primesN = findPrimeFactors(deN);
            if (primesN.length == 2){
                deP = primesN[1];
                deQ = primesN[0];

                dePhi = calculatePhi(deP, deQ);
                errorLabelD.setText("dePhi: "+dePhi);
                System.out.println("dePhi"+ dePhi);

                deD = calculateD();
                labelDED.setText("d is " + deD);

                labelDEInputC.show();
                textDEC.show();
                step2DCButton.show();
            }
            else {
                errorLabelD.setText("Error: Exactly 2 prime factors required for N");
                //System.out.println("more or less than 2 prime factors, so this n is incorrect");
            }
        }
        else errorLabelD.setText("Error: Proper n and e required");
            //System.out.println("this is not going to work witha correct n or e");
    }

    private static void step2DC(String stringN){
        deC = convertCodeToBigIntegerArray(stringN);
        deM = decryptMessage();

        String messageResult = "";
        for (char c : deM){
            messageResult += c;
        }
        labelDEM.setText("Message after decryption is: " + messageResult);
    }

    private static BigInteger convertToBigInteger(String stringN){
        try {
            BigInteger bigInt = new BigInteger(stringN);
            return bigInt;
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
            errorLabelE.setText("Error: non-numeric value");
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
        BigInteger counter = BigInteger.ZERO;
        BigDecimal k = BigDecimal.ONE;
        BigDecimal localD = BigDecimal.ZERO;
        while (k.compareTo(BigDecimal.ONE) == 0 || !isIntegerValue(localD)){
            localD = k
                    .multiply(new BigDecimal(dePhi))
                    .add(BigDecimal.ONE)
                    .divide(new BigDecimal(deE),100, RoundingMode.HALF_UP);
            k = k.add(BigDecimal.ONE);
            counter = counter.add(BigInteger.ONE);
        }
        return localD.toBigInteger();
    }

    private static boolean isIntegerValue(BigDecimal bd) {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }

    private static BigInteger[] convertCodeToBigIntegerArray(String stringCode){

        stringCode = stringCode.replaceAll("\\s", "");

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