package testeunitario.negocio;

import org.junit.jupiter.api.*;
import testeunitario.Modelo.Editor;
import testeunitario.Modelo.Ganhos;
import testeunitario.Modelo.Post;
import testeunitario.utilidade.ProcessadorTextoSimples;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CalculadoraGanhosTest {

    static CalculadoraGanhos calculadora;
    Editor autor;
    Post post;

    @BeforeAll
    static void initAll() {

        calculadora = new CalculadoraGanhos(new ProcessadorTextoSimples(), BigDecimal.TEN);

    }

    @BeforeEach
    void initBefore() {
        autor = new Editor(1l, "Marcus", "Marcus@gmail.com", new BigDecimal(5), true);

        post = new Post(1l, "codigo de Java", "desmonstrando um codigo executavel de Java", autor,
                "codigo de Java-12323289", null, false, false);
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Depois de todos os metodos");
    }

    @AfterEach
    void afterEach() {
        System.out.println("Depois de cada teste");
    }


    @Test
    public void dado_um_post_e_autor_premium_quando_calcular_ganhos_entao_deve_retornar_valor_com_bonus() {
        Ganhos ganhos = calculadora.calcular(post);

        assertEquals(new BigDecimal("40"), ganhos.getTotalGanho());
    }


    @Test
    public void Dado_um_Post_e_Autor_Quando_calcular_ganhos_entao_deve_retornar_qunatidade_de_palavras_no_post() {
        Ganhos ganhos = calculadora.calcular(post);


        assertEquals(6, ganhos.getQuantidadePalavras());
    }


    @Test
    public void Dado_um_post_e_autor_quando_calcular_ganhos_entao_deve_retornar_valor_pago_por_palavras() {
        Ganhos ganhos = calculadora.calcular(post);

        assertEquals(autor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());

    }


    @Test
    public void Dado__um_post_autor_nao_premium_quando_calcular_ganhos_deve_retonar_valor_sem_bonus() {
        autor.setPremium(false);
        Ganhos ganhos = calculadora.calcular(post);
        assertEquals(new BigDecimal("30"), ganhos.getTotalGanho());


    }


}