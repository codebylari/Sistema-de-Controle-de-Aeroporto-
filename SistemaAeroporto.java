import java.util.*;

class Passageiro {
    String nome;
    int idade;
    String cpf;
    String email;

    public Passageiro(String nome, int idade, String cpf, String email) {
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.email = email;
    }

    @Override
    public String toString() {
        return nome + " | " + idade + " anos | CPF: " + cpf + " | Email: " + email;
    }
}

class Voo {
    String numeroVoo;
    String origem;
    String destino;
    String horaPartida;
    String horaChegada;
    int capacidadeMaxima;
    Queue<Passageiro> reservasPendentes = new LinkedList<>();
    List<Passageiro> passageirosConfirmados = new ArrayList<>();
    Stack<Passageiro> checkIn = new Stack<>();

    public Voo(String numeroVoo, String origem, String destino, String horaPartida, String horaChegada, int capacidadeMaxima) {
        if (!numeroVoo.matches("\\d{4}")) {
            throw new IllegalArgumentException("Número do voo deve conter 4 dígitos.");
        }
        this.numeroVoo = numeroVoo;
        this.origem = origem;
        this.destino = destino;
        this.horaPartida = horaPartida;
        this.horaChegada = horaChegada;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public boolean temVagas() {
        return passageirosConfirmados.size() < capacidadeMaxima;
    }

    @Override
    public String toString() {
        return "Voo " + numeroVoo + ": " + origem + " -> " + destino + " | Partida: " + horaPartida + " | Chegada: " + horaChegada + " | Capacidade: " + capacidadeMaxima;
    }
}

public class SistemaAeroporto {
    static Scanner scanner = new Scanner(System.in);
    static List<Voo> voos = new ArrayList<>();

    public static void main(String[] args) {
        // Criar voos de exemplo
        voos.add(new Voo("2904", "São Paulo", "Rio de Janeiro", "08:00", "09:00", 2));
        voos.add(new Voo("3012", "Brasília", "Salvador", "12:00", "14:00", 3));

        while (true) {
            System.out.println("\n--- Sistema de Controle de Voos ---");
            System.out.println("1. Reservar voo");
            System.out.println("2. Realizar check-in");
            System.out.println("3. Visualizar informações dos voos");
            System.out.println("4. Processar reservas (Administrador)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> reservarVoo();
                case 2 -> realizarCheckIn();
                case 3 -> exibirVoos();
                case 4 -> processarReservas();
                case 0 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void reservarVoo() {
        System.out.println("\n--- Reservar Voo ---");
        Voo voo = selecionarVooDisponivel();
        if (voo == null) return;

        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        if (!email.contains("@")) {
            System.out.println("Email inválido!");
            return;
        }

        Passageiro passageiro = new Passageiro(nome, idade, cpf, email);
        voo.reservasPendentes.add(passageiro);
        System.out.println("Reserva realizada com sucesso! Aguardando confirmação do administrador.");
    }

    private static void realizarCheckIn() {
        System.out.println("\n--- Check-in ---");
        System.out.print("Número do voo: ");
        String numeroVoo = scanner.nextLine();

        Voo voo = buscarVoo(numeroVoo);
        if (voo == null) {
            System.out.println("Voo não encontrado!");
            return;
        }

        if (voo.passageirosConfirmados.isEmpty()) {
            System.out.println("Nenhuma reserva confirmada para este voo.");
            return;
        }

        System.out.print("Nome do passageiro: ");
        String nome = scanner.nextLine();

        Optional<Passageiro> passageiroOpt = voo.passageirosConfirmados.stream()
                .filter(p -> p.nome.equalsIgnoreCase(nome))
                .findFirst();

        if (passageiroOpt.isPresent()) {
            voo.checkIn.push(passageiroOpt.get());
            System.out.println("Check-in realizado com sucesso!");
        } else {
            System.out.println("Passageiro não encontrado ou reserva não confirmada.");
        }
    }

    private static void exibirVoos() {
        System.out.println("\n--- Informações dos Voos ---");
        System.out.println("Voos disponíveis:");
        voos.stream().filter(Voo::temVagas).forEach(System.out::println);

        System.out.println("\nVoos com reservas pendentes:");
        voos.stream().filter(v -> !v.reservasPendentes.isEmpty()).forEach(System.out::println);

        System.out.println("\nVoos cheios:");
        voos.stream().filter(v -> !v.temVagas()).forEach(System.out::println);
    }

    private static void processarReservas() {
        System.out.println("\n--- Processar Reservas (Administrador) ---");
        System.out.print("Número do voo: ");
        String numeroVoo = scanner.nextLine();

        Voo voo = buscarVoo(numeroVoo);
        if (voo == null) {
            System.out.println("Voo não encontrado!");
            return;
        }

        while (!voo.reservasPendentes.isEmpty() && voo.temVagas()) {
            Passageiro p = voo.reservasPendentes.poll();
            voo.passageirosConfirmados.add(p);
            System.out.println("Reserva de " + p.nome + " confirmada.");
        }

        if (!voo.reservasPendentes.isEmpty()) {
            System.out.println("Algumas reservas não puderam ser confirmadas devido à lotação máxima.");
        }
    }

    private static Voo selecionarVooDisponivel() {
        System.out.println("Voos disponíveis:");
        voos.stream().filter(Voo::temVagas).forEach(System.out::println);
        System.out.print("Escolha o número do voo: ");
        String numeroVoo = scanner.nextLine();
        Voo voo = buscarVoo(numeroVoo);
        if (voo == null || !voo.temVagas()) {
            System.out.println("Voo indisponível.");
            return null;
        }
        return voo;
    }

    private static Voo buscarVoo(String numeroVoo) {
        return voos.stream()
                .filter(v -> v.numeroVoo.equals(numeroVoo))
                .findFirst()
                .orElse(null);
    }
}