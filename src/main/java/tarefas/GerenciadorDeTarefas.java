package tarefas;

import java.time.LocalDate;
import java.util.*;

/** Regras de negócio sem dependência de JavaFX, relógio global ou banco de dados. */
public class GerenciadorDeTarefas {
    private final Map<Long, Tarefa> tarefas = new LinkedHashMap<>();
    private long proximoId = 1;

    public Tarefa criar(String titulo, String descricao, Prioridade prioridade, LocalDate prazo, LocalDate hoje) { throw new UnsupportedOperationException("Funcionalidade ainda não integrada"); }

    public Tarefa editar(long id, String titulo, String descricao, Prioridade prioridade, LocalDate prazo, LocalDate hoje) { throw new UnsupportedOperationException("Funcionalidade ainda não integrada"); }

    public Tarefa obter(long id) { throw new UnsupportedOperationException("Funcionalidade ainda não integrada"); }
    public void concluir(long id) { throw new UnsupportedOperationException("Funcionalidade ainda não integrada"); }
    public void reabrir(long id) { throw new UnsupportedOperationException("Funcionalidade ainda não integrada"); }
    public void excluir(long id) { throw new UnsupportedOperationException("Funcionalidade ainda não integrada"); }
    public void definirPrioridade(long id, Prioridade prioridade) { throw new UnsupportedOperationException("Funcionalidade ainda não integrada"); }
    public void definirPrazo(long id, LocalDate prazo, LocalDate hoje) { throw new UnsupportedOperationException("Funcionalidade ainda não integrada"); }
    public List<Tarefa> listar() { throw new UnsupportedOperationException("Funcionalidade ainda não integrada"); }
    public List<Tarefa> consultar(Prioridade prioridade, Status status, String busca) { throw new UnsupportedOperationException("Funcionalidade ainda não integrada"); }
    private void atualizar(Tarefa t, Status status, Prioridade prioridade, LocalDate prazo) { throw new UnsupportedOperationException("Funcionalidade ainda não integrada"); }
    private String validarTitulo(String titulo) { throw new UnsupportedOperationException("Funcionalidade ainda não integrada"); }
    private String normalizarDescricao(String descricao) { throw new UnsupportedOperationException("Funcionalidade ainda não integrada"); }
    private void validarHoje(LocalDate hoje) { throw new UnsupportedOperationException("Funcionalidade ainda não integrada"); }
    private void validarPrazo(LocalDate prazo, LocalDate hoje) { throw new UnsupportedOperationException("Funcionalidade ainda não integrada"); }
}
