import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// Classe Evento
class Evento {
    private String nome;
    private String data;
    private String local;
    private int capacidade;
    private List<Participante> participantes;

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

    public void adicionarParticipante(Participante participante) throws EventoLotadoException, ParticipanteDuplicadoException {
        if (participantes.size() >= capacidade) {
            throw new EventoLotadoException("O evento já está lotado.");
        }
        for (Participante p : participantes) {
            if (p.getEmail().equals(participante.getEmail())) {
                throw new ParticipanteDuplicadoException("Participante já cadastrado no evento.");
            }
        }
        participantes.add(participante);
    }

    public void cancelarReserva(String email) {
        participantes.removeIf(p -> p.getEmail().equals(email));
    }

    public int quantidadeParticipantes() {
        return participantes.size();
    }

    public void listarParticipantes() {
        for (Participante p : participantes) {
            System.out.println(p.getNome() + " (" + p.getTipo() + ")");
        }
    }
}

// Classe Palestra
class Palestra extends Evento {
    private String palestrante;
    private int duracao;
    private String temas;

    public Palestra(String nome, String data, String local, int capacidade, String palestrante, int duracao, String temas) {
        super(nome, data, local, capacidade);
        this.palestrante = palestrante;
        this.duracao = duracao;
        this.temas = temas;
    }

    public String getPalestrante() {
        return palestrante;
    }
}

// Classe Workshop
class Workshop extends Evento {
    private String instrutor;
    private List<String> materiais;
    private int cargaHoraria;

    public Workshop(String nome, String data, String local, int capacidade, String instrutor, List<String> materiais, int cargaHoraria) {
        super(nome, data, local, capacidade);
        this.instrutor = instrutor;
        this.materiais = materiais;
        this.cargaHoraria = cargaHoraria;
    }

    public String getInstrutor() {
        return instrutor;
    }
}

// Classe Participante
class Participante {
    private String nome;
    private String email;
    private String tipo;

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


class EventoLotadoException extends Exception {
    public EventoLotadoException(String message) {
        super(message);
    }
}


class ParticipanteDuplicadoException extends Exception {
    public ParticipanteDuplicadoException(String message) {
        super(message);
    }
}

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
            System.out.println("4. Cancelar Reserva");
            System.out.println("5. Listar Eventos");
            System.out.println("6. Filtrar Eventos");
            System.out.println("7. Gerar Relatório");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

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
                    cancelarReserva();
                    break;
                case 5:
                    listarEventos();
                    break;
                case 6:
                    filtrarEventos();
                    break;
                case 7:
                    gerarRelatorio();
                    break;
                case 8:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 8);
    }

    private static boolean validarData(String data) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        formato.setLenient(false);
        try {
            formato.parse(data);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private static void criarPalestra() {
        System.out.print("Nome da Palestra: ");
        String nome = scanner.nextLine();
        System.out.print("Data (dd/mm/aaaa): ");
        String data = scanner.nextLine();
        if (!validarData(data)) {
            System.out.println("Data inválida. Tente novamente.");
            return;
        }
        System.out.print("Local: ");
        String local = scanner.nextLine();
        System.out.print("Capacidade máxima: ");
        int capacidade = scanner.nextInt();
        if (capacidade <= 0) {
            System.out.println("A capacidade deve ser um número positivo. Tente novamente.");
            return;
        }
        scanner.nextLine(); // Limpar o buffer
        System.out.print("Palestrante: ");
        String palestrante = scanner.nextLine();
        System.out.print("Duração (minutos): ");
        int duracao = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer
        System.out.print("Temas: ");
        String temas = scanner.nextLine();

        Palestra palestra = new Palestra(nome, data, local, capacidade, palestrante, duracao, temas);
        try {
            palestra.adicionarParticipante(new Participante(palestrante, "palestrante@evento.com", "normal"));
        } catch (EventoLotadoException | ParticipanteDuplicadoException e) {
            System.out.println("Erro ao adicionar o palestrante: " + e.getMessage());
        }
        eventos.add(palestra);
        System.out.println("Palestra criada com sucesso!");
    }

    private static void criarWorkshop() {
        System.out.print("Nome do Workshop: ");
        String nome = scanner.nextLine();
        System.out.print("Data (dd/mm/aaaa): ");
        String data = scanner.nextLine();
        if (!validarData(data)) {
            System.out.println("Data inválida. Tente novamente.");
            return;
        }
        System.out.print("Local: ");
        String local = scanner.nextLine();
        System.out.print("Capacidade máxima: ");
        int capacidade = scanner.nextInt();
        if (capacidade <= 0) {
            System.out.println("A capacidade deve ser um número positivo. Tente novamente.");
            return;
        }
        scanner.nextLine(); // Limpar o buffer
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
        scanner.nextLine(); // Limpar o buffer

        Workshop workshop = new Workshop(nome, data, local, capacidade, instrutor, materiais, cargaHoraria);
        try {
            workshop.adicionarParticipante(new Participante(instrutor, "instrutor@workshop.com", "normal"));
        } catch (EventoLotadoException | ParticipanteDuplicadoException e) {
            System.out.println("Erro ao adicionar o instrutor: " + e.getMessage());
        }
        eventos.add(workshop);
        System.out.println("Workshop criado com sucesso!");
    }

    private static void cadastrarParticipante() {
        System.out.print("Digite o nome do evento: ");
        String nomeEvento = scanner.nextLine();
        Evento evento = encontrarEventoPorNome(nomeEvento);
        if (evento == null) {
            System.out.println("Evento não encontrado.");
            return;
        }

        System.out.print("Nome do Participante: ");
        String nome = scanner.nextLine();
        System.out.print("Email do Participante: ");
        String email = scanner.nextLine();
        System.out.print("Tipo de Participante (normal/VIP): ");
        String tipo = scanner.nextLine();

        Participante participante = new Participante(nome, email, tipo);

        try {
            evento.adicionarParticipante(participante);
            System.out.println("Participante adicionado com sucesso!");
        } catch (EventoLotadoException | ParticipanteDuplicadoException e) {
            System.out.println("Erro ao adicionar participante: " + e.getMessage());
        }
    }

    private static void cancelarReserva() {
        System.out.print("Digite o nome do evento: ");
        String nomeEvento = scanner.nextLine();
        Evento evento = encontrarEventoPorNome(nomeEvento);
        if (evento == null) {
            System.out.println("Evento não encontrado.");
            return;
        }

        System.out.print("Email do Participante a ser removido: ");
        String email = scanner.nextLine();
        evento.cancelarReserva(email);
        System.out.println("Reserva cancelada com sucesso.");
    }

    private static void listarEventos() {
        for (Evento evento : eventos) {
            System.out.println("\nEvento: " + evento.getNome());
            System.out.println("Data: " + evento.getData());
            System.out.println("Local: " + evento.getLocal());
            System.out.println("Capacidade: " + evento.getCapacidade());
            System.out.println("Participantes (" + evento.quantidadeParticipantes() + "):");
            evento.listarParticipantes();
        }
    }

    private static void filtrarEventos() {
        System.out.println("Filtrar por: 1. Data 2. Tipo");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (opcao == 1) {
            System.out.print("Digite a data (dd/mm/aaaa): ");
            String data = scanner.nextLine();
            for (Evento evento : eventos) {
                if (evento.getData().equals(data)) {
                    System.out.println("Evento: " + evento.getNome() + " em " + evento.getLocal());
                }
            }
        } else if (opcao == 2) {
            System.out.print("Digite o tipo (palestra/workshop): ");
            String tipo = scanner.nextLine();
            for (Evento evento : eventos) {
                if ((tipo.equalsIgnoreCase("palestra") && evento instanceof Palestra) ||
                    (tipo.equalsIgnoreCase("workshop") && evento instanceof Workshop)) {
                    System.out.println("Evento: " + evento.getNome() + " em " + evento.getLocal());
                }
            }
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private static void gerarRelatorio() {
        for (Evento evento : eventos) {
            System.out.println("Relatório do Evento: " + evento.getNome());
            System.out.println("Participantes Inscritos:");
            evento.listarParticipantes();
        }
    }

    private static Evento encontrarEventoPorNome(String nome) {
        for (Evento evento : eventos) {
            if (evento.getNome().equalsIgnoreCase(nome)) {
                return evento;
            }
        }
        return null;
    }
}
