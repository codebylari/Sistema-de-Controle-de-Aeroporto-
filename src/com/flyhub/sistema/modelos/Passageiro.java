package src.com.flyhub.sistema.modelos;

public class Passageiro {

    public String nome;
    public int idade;
    public String cpf;
    public String email;

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