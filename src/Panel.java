import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class Panel extends JPanel {

    private JButton numbers [] = new JButton[10];
    private Font font = new Font("SanSerif", Font.BOLD, 15);
    private JTextField output = new JTextField();
    private JButton equ = new JButton("="), sum = new JButton("+"), sub = new JButton("-"),
            mul = new JButton("*"), div = new JButton("/"), back = new JButton("C");

    int firstValue = 0;
    Boolean flag = false;
    String operation = "+";


    public Panel(){
        setLayout(null);//размещаем эелементы на панели

        //кнопка =
        equ.setBounds(70,250,50,50);
        equ.setFont(font);
        add(equ);
        //кнопка C
        back.setBounds(130,250,50,50);
        back.setFont(font);
        add(back);
        //кнопка +
        sum.setBounds(190,70,50,50);
        sum.setFont(font);
        add(sum);
        //кнопка -
        sub.setBounds(190,130,50,50);
        sub.setFont(font);
        add(sub);
        //кнопка *
        mul.setBounds(190,190,50,50);
        mul.setFont(font);
        add(mul);
        //кнопка /
        div.setBounds(190,250,50,50);
        div.setFont(font);
        add(div);

        //кнопка 0
        numbers[0] = new JButton("0");
        numbers[0].setBounds(10, 250, 50, 50);
        numbers[0].setFont(font);
        add(numbers[0]);

        //кнопки 1-9
        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){
                numbers[x * 3 + y + 1] = new JButton((x * 3 + y + 1)+"");
                numbers[x * 3 + y + 1].setBounds(x*(50+10)+10, y*(50+10)+70, 50, 50);
                numbers[x * 3 + y + 1].setFont(font);
                add(numbers[x * 3 + y + 1]);
            }
        }

        //панель просмотра
        output.setBounds(10,10,230,50);
        output.setEditable(false);//не можем сами изменять то, что написано внутри
        add(output);

        //обработчик события "нажатие на цифру"
        for (JButton b : numbers){
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    output.setText(output.getText() + b.getText());
                }
            });
        }

        //обработчик события "нажатие на кнопку сброс"
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output.setText("");
            }
        });

        //обработчик события "нажатие на +"
        sum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output.setText(output.getText() + sum.getText());
                operation = "+";
            }
        });


        //обработчик события "нажатие на -"
        sub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = false;
                if ((output.getText() != null) && (output.getText().trim().length()!=0)) {
                    String s = output.getText();
                    char [] chars = s.toCharArray();
                    char ch = chars[chars.length-1];
                    if ((ch != '+') && (ch != '-') && (ch != '*') && (ch != '/')) {
                        firstValue = Integer.valueOf(output.getText());
                        output.setText(output.getText() + sub.getText());
                        operation = "-";
                    }else {
                        if (ch == '-'){
                            output.setText(output.getText() + sub.getText());
                            flag = true;
                        }
                        if ((ch == '+')||(ch == '*')||(ch == '/')){
                            output.setText(output.getText() + sub.getText());
                        }
                    }
                }
                if ((output.getText() == null) || (output.getText().trim().length()==0)) {
                    output.setText(sub.getText());
                }
            }
        });

        //обработчик события "нажатие на *"
        mul.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output.setText(output.getText() + mul.getText());
                operation = "*";
            }
        });

        //обработчик события "нажатие на /"
        div.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output.setText(output.getText() + div.getText());
                operation = "/";
            }
        });

        //обработчик события "нажатие на = "
        equ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("+".equals(operation)){
                    String text = output.getText();
                    Pattern p = Pattern.compile("[+]");
                    String [] arr = p.split(text);
                    output.setText(Integer.valueOf(arr[0])+Integer.valueOf(arr[1])+"");
                }
                if("-".equals(operation)){
                    String text = output.getText();
                    Pattern p = Pattern.compile("[-]");
                    String [] arr = p.split(text);
                    if (flag){
                        int a = firstValue + Integer.valueOf(arr[arr.length-1]);
                        output.setText(a + "");
                    }else {
                        int a = firstValue - Integer.valueOf(arr[arr.length-1]);
                        output.setText(a + "");
                    }
                }
                if("*".equals(operation)){
                    String text = output.getText();
                    Pattern p = Pattern.compile("[*]");
                    String [] arr = p.split(text);
                    output.setText(Integer.valueOf(arr[0])*Integer.valueOf(arr[1])+"");
                }
                if("/".equals(operation)){
                    String text = output.getText();
                    Pattern p = Pattern.compile("[/]");
                    String [] arr = p.split(text);
                    if (Double.valueOf(arr[1]) != 0){
                        output.setText(Double.valueOf(arr[0])/Double.valueOf(arr[1])+"");
                    } else {
                        output.setText("делить на 0 нельзя");
                    }
                }
            }
        });
    }
}
