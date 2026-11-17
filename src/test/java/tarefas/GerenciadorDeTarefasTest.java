package tarefas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GerenciadorDeTarefasTest {
    private final LocalDate hoje = LocalDate.of(2026, 11, 12);
    private GerenciadorDeTarefas g;
    @BeforeEach void preparar() { g = new GerenciadorDeTarefas(); }
    private Tarefa criar(String titulo) { return g.criar(titulo, "", null, null, hoje); }
    private void erro(String mensagem, Runnable acao) {
        assertEquals(mensagem, assertThrows(IllegalArgumentException.class, acao::run).getMessage());
    }

    @Test void criaPendenteComPrioridadeMedia() {
        Tarefa t = criar("Estudar BDD");
        assertAll(() -> assertEquals(1, t.id()), () -> assertEquals(Status.PENDENTE, t.status()),
                () -> assertEquals(Prioridade.MEDIA, t.prioridade()), () -> assertNull(t.prazo()),
                () -> assertEquals(1, g.listar().size()));
    }
    @ParameterizedTest @NullAndEmptySource @ValueSource(strings={" ", "\t", "\n"})
    void rejeitaTituloVazioSemCriar(String titulo) {
        erro("O título é obrigatório", () -> criar(titulo));
        assertTrue(g.listar().isEmpty());
    }
    @Test void normalizaEspacosEDescricaoNula() {
        Tarefa t = g.criar("  Estudar  ", null, null, null, hoje);
        assertEquals("Estudar", t.titulo()); assertEquals("", t.descricao());
    }
    @Test void rejeitaHojeNulo() {
        erro("A data de hoje é obrigatória", () -> g.criar("Entrega", "", null, null, null));
    }
}
