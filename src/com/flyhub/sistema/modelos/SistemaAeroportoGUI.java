package src.com.flyhub.sistema.modelos;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Queue;

class Passageiro {
    String nome, cpf, email;
    int idade;

    public Passageiro(String nome, int idade, String cpf, String email) {
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.email = email;
    }

    public String toString() {
        return nome + " | " + idade + " anos | CPF: " + cpf + " | Email: " + email;
    }
}

class Voo {
    String numeroVoo, origem, destino, horaPartida, horaChegada;
    int capacidadeMaxima;
    Queue<Passageiro> reservasPendentes = new LinkedList<>();
    List<Passageiro> confirmados = new ArrayList<>();
    Stack<Passageiro> checkIn = new Stack<>();

    public Voo(String numeroVoo, String origem, String destino, String horaPartida, String horaChegada,
            int capacidadeMaxima) {
        this.numeroVoo = numeroVoo;
        this.origem = origem;
        this.destino = destino;
        this.horaPartida = horaPartida;
        this.horaChegada = horaChegada;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public boolean temVagas() {
        return confirmados.size() < capacidadeMaxima;
    }

    public String toString() {
        return "Voo " + numeroVoo + " | " + origem + " → " + destino +
                " | Partida: " + horaPartida + " | Chegada: " + horaChegada +
                " | Capacidade: " + capacidadeMaxima +
                " | Confirmados: " + confirmados.size() + " | Pendentes: " + reservasPendentes.size();
    }
}

public class SistemaAeroportoGUI extends JFrame {
    private CardLayout layout = new CardLayout();
    private JPanel painelPrincipal = new JPanel(layout);

    private List<Voo> voos = new ArrayList<>();
    private List<Passageiro> passageiros = new ArrayList<>();

    public SistemaAeroportoGUI() {
        setTitle("✈️ Sistema de Controle de Aeroporto");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        // Voos pré-cadastrados
        voos.add(new Voo("2904", "São Paulo", "Rio de Janeiro", "08:00", "09:00", 2));
        voos.add(new Voo("3012", "Brasília", "Salvador", "12:00", "14:00", 3));
        voos.add(new Voo("4501", "Curitiba", "Florianópolis", "10:00", "11:00", 2));
        voos.add(new Voo("5123", "Fortaleza", "Recife", "15:00", "16:30", 3));

        // Adiciona telas
        painelPrincipal.add(telaLogin(), "login");
        painelPrincipal.add(menuPassageiro(), "passageiro");
        painelPrincipal.add(menuAdmin(), "admin");

        add(painelPrincipal);
        layout.show(painelPrincipal, "login");
    }

    // ====================== LOGIN ======================
    private JPanel telaLogin() {
        JPanel painel = new JPanel(null);
        painel.setBackground(new Color(230, 240, 255));

        JLabel titulo = new JLabel("Login - Sistema Aeroporto", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBounds(150, 40, 400, 30);

        JLabel lblUser = new JLabel("Usuário:");
        lblUser.setBounds(200, 120, 100, 25);
        JTextField txtUser = new JTextField();
        txtUser.setBounds(300, 120, 180, 25);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(200, 160, 100, 25);
        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setBounds(300, 160, 180, 25);

        JButton btnLogin = new JButton("Entrar");
        btnLogin.setBackground(new Color(100, 150, 255));
        btnLogin.setForeground(Color.white);
        btnLogin.setBounds(300, 210, 100, 35);

        painel.add(titulo);
        painel.add(lblUser);
        painel.add(txtUser);
        painel.add(lblSenha);
        painel.add(txtSenha);
        painel.add(btnLogin);

        btnLogin.addActionListener(e -> {
            String u = txtUser.getText().trim();
            String s = new String(txtSenha.getPassword()).trim();

            if (u.isEmpty() || s.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha usuário e senha corretamente!");
                return;
            }

            if (u.equalsIgnoreCase("admin") && s.equals("1234")) {
                layout.show(painelPrincipal, "admin");
            } else {
                Optional<Passageiro> p = passageiros.stream().filter(pass -> pass.nome.equalsIgnoreCase(u)).findFirst();
                if (p.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Primeiro cadastro: você será registrado como passageiro.");
                    cadastrarPassageiro(u);
                }
                layout.show(painelPrincipal, "passageiro");
            }
        });

        return painel;
    }

    // ====================== MENU PASSAGEIRO ======================
    private JPanel menuPassageiro() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(new Color(240, 250, 255));

        JLabel lbl = new JLabel("Menu do Passageiro", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 22));
        lbl.setAlignmentX(CENTER_ALIGNMENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JButton btnReservar = new JButton("Reservar Voo");
        JButton btnCheckIn = new JButton("Fazer Check-In");
        JButton btnVerVoos = new JButton("Ver Informações dos Voos");
        JButton btnSair = new JButton("Sair");

        estilizarBotao(btnReservar);
        estilizarBotao(btnCheckIn);
        estilizarBotao(btnVerVoos);
        estilizarBotao(btnSair);

        painel.add(lbl);
        painel.add(btnReservar);
        painel.add(btnCheckIn);
        painel.add(btnVerVoos);
        painel.add(Box.createVerticalStrut(20));
        painel.add(btnSair);

        btnReservar.addActionListener(e -> reservarVoo());
        btnCheckIn.addActionListener(e -> checkIn());
        btnVerVoos.addActionListener(e -> exibirVoos());
        btnSair.addActionListener(e -> layout.show(painelPrincipal, "login"));

        return painel;
    }

    // ====================== MENU ADMIN ======================
    private JPanel menuAdmin() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(new Color(250, 245, 240));

        JLabel lbl = new JLabel("Painel do Administrador", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 22));
        lbl.setAlignmentX(CENTER_ALIGNMENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JButton btnCadastrarVoo = new JButton("Cadastrar Voo");
        JButton btnProcessar = new JButton("Processar Reservas");
        JButton btnHistorico = new JButton("Ver Histórico de Passageiros");
        JButton btnSair = new JButton("Sair");

        estilizarBotao(btnCadastrarVoo);
        estilizarBotao(btnProcessar);
        estilizarBotao(btnHistorico);
        estilizarBotao(btnSair);

        painel.add(lbl);
        painel.add(btnCadastrarVoo);
        painel.add(btnProcessar);
        painel.add(btnHistorico);
        painel.add(Box.createVerticalStrut(20));
        painel.add(btnSair);

        btnCadastrarVoo.addActionListener(e -> cadastroVoo());
        btnProcessar.addActionListener(e -> processarReservasAdmin());
        btnHistorico.addActionListener(e -> mostrarHistoricoAdmin());
        btnSair.addActionListener(e -> layout.show(painelPrincipal, "login"));

        return painel;
    }

    private void estilizarBotao(JButton btn) {
        btn.setAlignmentX(CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(250, 40));
        btn.setBackground(new Color(100, 150, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    // ====================== FUNÇÕES ======================
    private void cadastrarPassageiro(String nome) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField idade = new JTextField();
        JTextField cpf = new JTextField();
        JTextField email = new JTextField();

        panel.add(new JLabel("Idade:"));
        panel.add(idade);
        panel.add(new JLabel("CPF:"));
        panel.add(cpf);
        panel.add(new JLabel("Email:"));
        panel.add(email);

        int result = JOptionPane.showConfirmDialog(this, panel, "Cadastro de Passageiro: " + nome,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int i = Integer.parseInt(idade.getText().trim());
                if (cpf.getText().isBlank() || email.getText().isBlank() || !email.getText().contains("@")) {
                    throw new Exception();
                }
                passageiros.add(new Passageiro(nome, i, cpf.getText(), email.getText()));
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar passageiro. Verifique os dados.");
            }
        }
    }

    private void cadastroVoo() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        JTextField numero = new JTextField();
        JTextField origem = new JTextField();
        JTextField destino = new JTextField();
        JTextField partida = new JTextField();
        JTextField chegada = new JTextField();
        JTextField capacidade = new JTextField();

        panel.add(new JLabel("Número do voo (4 dígitos):"));
        panel.add(numero);
        panel.add(new JLabel("Origem:"));
        panel.add(origem);
        panel.add(new JLabel("Destino:"));
        panel.add(destino);
        panel.add(new JLabel("Hora de Partida:"));
        panel.add(partida);
        panel.add(new JLabel("Hora de Chegada:"));
        panel.add(chegada);
        panel.add(new JLabel("Capacidade máxima:"));
        panel.add(capacidade);

        int result = JOptionPane.showConfirmDialog(this, panel, "Cadastrar Voo", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String num = numero.getText().trim();
                if (!num.matches("\\d{4}")) {
                    JOptionPane.showMessageDialog(this, "Número do voo deve ter 4 dígitos!");
                    return;
                }
                int cap = Integer.parseInt(capacidade.getText().trim());
                voos.add(new Voo(num, origem.getText(), destino.getText(), partida.getText(), chegada.getText(), cap));
                JOptionPane.showMessageDialog(this, "Voo cadastrado com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar voo. Verifique os dados.");
            }
        }
    }

    private void reservarVoo() {
    if (voos.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nenhum voo cadastrado.");
        return;
    }

    StringBuilder sb = new StringBuilder("Voos disponíveis:\n");
    for (Voo v : voos) {
        sb.append(v.numeroVoo)
          .append(" - ")
          .append(v.origem)
          .append(" → ")
          .append(v.destino)
          .append(" | Capacidade: ").append(v.capacidadeMaxima)
          .append(" | Confirmados: ").append(v.confirmados.size())
          .append(" | Pendentes: ").append(v.reservasPendentes.size())
          .append("\n");
    }

    String num = JOptionPane.showInputDialog(this, sb + "\nDigite o número do voo para reservar:");
    if (num == null || num.isBlank()) return;

    Voo voo = voos.stream().filter(v -> v.numeroVoo.equals(num)).findFirst().orElse(null);
    if (voo == null) {
        JOptionPane.showMessageDialog(this, "Voo não encontrado!");
        return;
    }

    String nome = JOptionPane.showInputDialog("Digite seu nome (como cadastrado):");
    if (nome == null || nome.isBlank()) return;

    Optional<Passageiro> p = passageiros.stream()
            .filter(pass -> pass.nome.equalsIgnoreCase(nome))
            .findFirst();

    if (p.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Passageiro não cadastrado!");
        return;
    }

    // Verifica se já tem reserva pendente ou confirmada
    if (voo.reservasPendentes.contains(p.get()) || voo.confirmados.contains(p.get())) {
        JOptionPane.showMessageDialog(this, "Você já possui uma reserva neste voo!");
        return;
    }

    // Adiciona o passageiro à fila de pendentes
    voo.reservasPendentes.add(p.get());
    JOptionPane.showMessageDialog(this, "Reserva adicionada à lista de pendentes! Aguarde confirmação do administrador.");
}


    private void checkIn() {
        String num = JOptionPane.showInputDialog("Digite o número do voo:");
        if (num == null || num.isBlank())
            return;
        Voo voo = voos.stream().filter(v -> v.numeroVoo.equals(num)).findFirst().orElse(null);
        if (voo == null) {
            JOptionPane.showMessageDialog(this, "Voo não encontrado!");
            return;
        }

        String nome = JOptionPane.showInputDialog("Digite seu nome (como cadastrado):");
        if (nome == null || nome.isBlank())
            return;
        Optional<Passageiro> p = voo.confirmados.stream().filter(ps -> ps.nome.equalsIgnoreCase(nome)).findFirst();
        if (p.isPresent()) {
            voo.checkIn.push(p.get());
            JOptionPane.showMessageDialog(this, "Check-in realizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Reserva não confirmada!");
        }
    }

    private void exibirVoos() {
        StringBuilder sb = new StringBuilder("Voos cadastrados:\n");
        for (Voo v : voos)
            sb.append(v.toString()).append("\n");
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    // ====================== PROCESSAR RESERVAS ======================
    private void processarReservasAdmin() {
        if (voos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum voo cadastrado.");
            return;
        }

        // Mostrar voos já cadastrados
        StringBuilder sb = new StringBuilder("Voos cadastrados:\n");
        for (Voo v : voos) {
            sb.append(v.numeroVoo).append(" - ").append(v.origem).append(" → ").append(v.destino)
                    .append(" | Confirmados: ").append(v.confirmados.size())
                    .append(" | Pendentes: ").append(v.reservasPendentes.size()).append("\n");
        }

        String num = JOptionPane.showInputDialog(this, sb + "\nDigite o número do voo para processar:");
        if (num == null || num.isBlank())
            return;

        Voo voo = voos.stream().filter(v -> v.numeroVoo.equals(num)).findFirst().orElse(null);
        if (voo == null) {
            JOptionPane.showMessageDialog(this, "Voo não encontrado!");
            return;
        }

        if (voo.reservasPendentes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há reservas pendentes para este voo.");
            return;
        }

        // Processar reservas uma a uma, perguntando ao admin se deseja confirmar
        Queue<Passageiro> tempPendentes = new LinkedList<>(voo.reservasPendentes);
        voo.reservasPendentes.clear();

        while (!tempPendentes.isEmpty() && voo.temVagas()) {
            Passageiro p = tempPendentes.poll();
            int resp = JOptionPane.showConfirmDialog(this,
                    "Deseja confirmar a reserva de " + p.nome + " para o voo " + voo.numeroVoo + "?",
                    "Confirmar Reserva",
                    JOptionPane.YES_NO_OPTION);

            if (resp == JOptionPane.YES_OPTION) {
                voo.confirmados.add(p);
                JOptionPane.showMessageDialog(this, "Reserva confirmada para " + p.nome);
            } else {
                // Se não confirmar, volta para a fila de pendentes
                voo.reservasPendentes.add(p);
            }
        }

        JOptionPane.showMessageDialog(this, "Processamento concluído!");
    }

    private void mostrarHistoricoAdmin() {
        StringBuilder sb = new StringBuilder("Histórico de Passageiros:\n");
        for (Voo v : voos) {
            sb.append("Voo ").append(v.numeroVoo).append(":\n");
            if (v.confirmados.isEmpty())
                sb.append("  Nenhum passageiro confirmado\n");
            else
                v.confirmados.forEach(p -> sb.append("  ").append(p).append("\n"));
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemaAeroportoGUI().setVisible(true));
    }
}
