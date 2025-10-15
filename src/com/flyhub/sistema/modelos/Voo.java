package src.com.flyhub.sistema.modelos;

import java.util.*;

public class Voo {
    public String numeroVoo, origem, destino, horaPartida, horaChegada;
    public int capacidadeMaxima;
    public Queue<Passageiro> reservasPendentes = new LinkedList<>();
    public List<Passageiro> confirmados = new ArrayList<>();
    public Stack<Passageiro> checkIn = new Stack<>();

    public Voo(String numeroVoo, String origem, String destino,
               String horaPartida, String horaChegada, int capacidadeMaxima) {
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
               " | Confirmados: " + confirmados.size() +
               " | Pendentes: " + reservasPendentes.size();
    }
}
