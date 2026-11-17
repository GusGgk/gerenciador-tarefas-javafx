package tarefas;

public enum Status {
    PENDENTE("Pendente"), CONCLUIDA("Concluída");
    private final String rotulo;
    Status(String rotulo) { this.rotulo = rotulo; }
    @Override public String toString() { return rotulo; }
}
