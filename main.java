import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe base Evento
abstract class Evento {
    private String nome;
    private String data;
    private String local;
    private int capacidade;
    protected List<Participante> participantes;

    public Evento(String nome, String data, String local, int capacidade) {
        this.nome = nome;
        this.data = data;
        this.local = local;
        this.capacidade = capacidade;
        this.participantes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getData() {
        return data;
    }

    public String getLocal() {
        return local;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }

    public boolean adicionarParticipante(Participante participante) {
        if (participantes.size() < capacidade) {
            participantes.add(participante);
            return true;
        }
        return false;
    }

    public abstract String getTipo();
}

// Classe Palestra
class Palestra extends Evento {
    private String palestrante;
    private int duracao; // duração em minutos
    private String temas;

    public Palestra(String nome, String data, String local, int capacidade, String palestrante, int duracao, String temas) {
        super(nome, data, local, capacidade);
        this.palestrante = palestrante;
        this.duracao = duracao;
        this.temas = temas;
    }

    @Override
    public String getTipo() {
        return "Palestra";
    }

    public String getPalestrante() {
        return palestrante;
    }
}

// Classe Workshop
class Workshop extends Evento {
    private String instrutor;
    private List<String> materiais;
    private int cargaHoraria; // carga horária em horas

    public Workshop(String nome, String data, String local, int capacidade, String instrutor, List<String> materiais, int cargaHoraria) {
        super(nome, data, local, capacidade);
        this.instrutor = instrutor;
        this.materiais = materiais;
        this.cargaHoraria = cargaHoraria;
    }

    @Override
    public String getTipo() {
        return "Workshop";
    }

    public String getInstrutor() {
        return instrutor;
    }
}

// Classe Participante
class Participante {
    private String nome;
    private String email;
    private String tipo; // normal ou VIP

    public Participante(String nome, String email, String tipo) {
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTipo() {
        return tipo;
    }
}

// Classe Main
public class main {
    private static List<Evento> eventos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Criar Palestra");
            System.out.println("2. Criar Workshop");
            System.out.println("3. Cadastrar Participante");
            System.out.println("4. Listar Eventos");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar o buffer

            switch (opcao) {
                case 1:
                    criarPalestra();
                    break;
                case 2:
                    criarWorkshop();
                    break;
                case 3:
                    cadastrarParticipante();
                    break;
                case 4:
                    listarEventos();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 5);
    }

    private static void criarPalestra() {
        System.out.print("Nome da Palestra: ");
        String nome = scanner.nextLine();
        System.out.print("Data (dd/mm/aaaa): ");
        String data = scanner.nextLine();
        System.out.print("Local: ");
        String local = scanner.nextLine();
        System.out.print("Capacidade máxima: ");
        int capacidade = scanner.nextInt();
        scanner.nextLine(); // limpar o buffer
        System.out.print("Palestrante: ");
        String palestrante = scanner.nextLine();
        System.out.print("Duração (minutos): ");
        int duracao = scanner.nextInt();
        scanner.nextLine(); // limpar o buffer
        System.out.print("Temas: ");
        String temas = scanner.nextLine();

        Palestra palestra = new Palestra(nome, data, local, capacidade, palestrante, duracao, temas);
        eventos.add(palestra);
        System.out.println("Palestra criada com sucesso!");
    }

    private static void criarWorkshop() {
        System.out.print("Nome do Workshop: ");
        String nome = scanner.nextLine();
        System.out.print("Data (dd/mm/aaaa): ");
        String data = scanner.nextLine();
        System.out.print("Local: ");
        String local = scanner.nextLine();
        System.out.print("Capacidade máxima: ");
        int capacidade = scanner.nextInt();
        scanner.nextLine(); // limpar o buffer
        System.out.print("Instrutor: ");
        String instrutor = scanner.nextLine();
        System.out.print("Materiais (separados por vírgula): ");
        String[] materiaisInput = scanner.nextLine().split(",");
        List<String> materiais = new ArrayList<>();
        for (String material : materiaisInput) {
            materiais.add(material.trim());
        }
        System.out.print("Carga Horária (horas): ");
        int cargaHoraria = scanner.nextInt();
        scanner.nextLine(); // limpar o buffer

        Workshop workshop = new Workshop(nome, data, local, capacidade, instrutor, materiais, cargaHoraria);
        eventos.add(workshop);
        System.out.println("Workshop criado com sucesso!");
    }

    private static void cadastrarParticipante() {
        System.out.print("Nome do Participante: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Tipo (normal ou VIP): ");
        String tipo = scanner.nextLine();

        Participante participante = new Participante(nome, email, tipo);
        for (Evento evento : eventos) {
            if (evento.adicionarParticipante(participante)) {
                System.out.println("Participante cadastrado no evento: " + evento.getNome());
                return;
            }
        }
        System.out.println("Nenhum evento disponível para adicionar o participante.");
    }

    private static void listarEventos() {
        System.out.println("Eventos Cadastrados:");
        for (Evento evento : eventos) {
            System.out.println("- " + evento.getNome() + " (" + evento.getTipo() + ")");
            System.out.println("  Data: " + evento.getData() + ", Local: " + evento.getLocal());
            System.out.println("  Capacidade: " + evento.getCapacidade() + ", Participantes: " + evento.getParticipantes().size());
        }
    }
}