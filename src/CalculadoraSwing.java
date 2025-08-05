import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraSwing extends JFrame implements ActionListener {

    private JTextField visor;
    private double numero1 = 0;
    private String operador = "";
    private boolean novaOperacao = true;

    public CalculadoraSwing() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        visor = new JTextField("0");
        visor.setEditable(false);
        visor.setFont(new Font("Arial", Font.BOLD, 24));
        visor.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(4, 4, 5, 5)); // 4x4 com espaçamento

        String[] botoes = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String texto : botoes) {
            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 20));
            botao.addActionListener(this);
            painelBotoes.add(botao);
        }

        getContentPane().setLayout(new BorderLayout(5, 5));
        getContentPane().add(visor, BorderLayout.NORTH);
        getContentPane().add(painelBotoes, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String texto = e.getActionCommand();

        if (texto.matches("[0-9]")) {
            if (visor.getText().equals("0") || novaOperacao) {
                visor.setText(texto);
            } else {
                visor.setText(visor.getText() + texto);
            }
            novaOperacao = false;
        } else if (texto.matches("[\\+\\-\\*/]")) {
            numero1 = Double.parseDouble(visor.getText().replace(",", "."));
            operador = texto;
            novaOperacao = true;
        } else if (texto.equals("=")) {
            double numero2 = Double.parseDouble(visor.getText().replace(",", "."));
            double resultado = 0;

            switch (operador) {
                case "+": resultado = numero1 + numero2; break;
                case "-": resultado = numero1 - numero2; break;
                case "*": resultado = numero1 * numero2; break;
                case "/":
                    if (numero2 != 0) {
                        resultado = numero1 / numero2;
                    } else {
                        visor.setText("Erro: divisão por 0");
                        return;
                    }
                    break;
            }

            // Substitui o ponto por vírgula para estilo brasileiro
            visor.setText(String.valueOf(resultado).replace(".", ","));
            novaOperacao = true;
        } else if (texto.equals("C")) {
            visor.setText("0");
            numero1 = 0;
            operador = "";
            novaOperacao = true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraSwing calc = new CalculadoraSwing();
            calc.setVisible(true);
        });
    }
}
