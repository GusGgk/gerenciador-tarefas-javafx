package tarefas;

import java.time.LocalDate;

/** Retrato imutável: alterações passam pelas regras do gerenciador. */
public record Tarefa(long id, String titulo, String descricao, Status status,
                     Prioridade prioridade, LocalDate prazo) {
    public boolean atrasada(LocalDate hoje) {
        return status == Status.PENDENTE && prazo != null && prazo.isBefore(hoje);
    }
}
