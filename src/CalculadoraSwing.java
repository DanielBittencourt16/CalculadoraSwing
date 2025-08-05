import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CalculadoraSwing extends JFrame implements ActionListener {

    private JTextField visor;
    private double num1, num2, resultado;
    private String operador;


    public CalculadoraSwing() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Centraliza a janela

        // Visor
        visor = new JTextField("0");
        visor.setEditable(false);
        visor.setFont(new Font("Arial", Font.BOLD, 24));
        add(visor, BorderLayout.NORTH);

        // Painel de botões
        JPanel botoes = new JPanel();
        botoes.setLayout(new GridLayout(4, 4, 5, 5)); // 4x4 com espaçamento

        String[] textos = {
                "7", "8", "9", "+",
                "4", "5", "6", "-",
                "1", "2", "3", "*",
                "0", ",", "=", "/",
                "C"
        };

        for (String texto : textos) {
            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 20));
            botao.addActionListener(this);
            botoes.add(botao);
        }

        add(botoes, BorderLayout.CENTER);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.matches("[0-9]")) {
            visor.setText(visor.getText() + comando);
        } else if (comando.equals(",")) {
            if (!visor.getText().contains(",")){
                visor.setText(visor.getText() + ",");
            }
        } else if (comando.matches("[+\\-*/]")) {
            try {
                num1 = Double.parseDouble(visor.getText().replace(',', '.'));
                operador = comando;
                visor.setText("");
            } catch (NumberFormatException ex) {
                visor.setText("Erro");
            }
        } else if (comando.equals("=")) {
            try {
                num2 = Double.parseDouble(visor.getText().replace(',', '.'));
                switch (operador) {
                    case "+": resultado = num1 + num2; break;
                    case "-": resultado = num1 - num2; break;
                    case "*": resultado = num1 * num2; break;
                    case "/":
                        if (num2 == 0) {
                            visor.setText("Divisão por Zero");
                            return;
                        }
                        resultado = num1 / num2; break;
                }

                // Verifica se o resultado é inteiro
                if (resultado == (int) resultado) {
                    visor.setText(String.valueOf((int) resultado));
                } else {
                    DecimalFormatSymbols simbolos = new DecimalFormatSymbols(new Locale("pt", "BR"));
                    simbolos.setDecimalSeparator(',');
                    DecimalFormat df = new DecimalFormat("#.##", simbolos);
                    visor.setText(df.format(resultado));
                }

            } catch (NumberFormatException ex) {
                visor.setText("Erro");
            }
        } else if (comando.equals("C")) {
            visor.setText("");
            num1 = num2 = resultado = 0;
            operador = "";
        }
    }

    public static void main(String[] args) {
            new CalculadoraSwing();
    }
}
