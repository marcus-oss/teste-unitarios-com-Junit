package testeunitario.utilidade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static testeunitario.utilidade.SaudacaoUtil.saudar;
import static testeunitario.utilidade.SaudacaoUtilsConditions.*;

@DisplayName("Teste na classe SaudaçãoUtil")
class SaudacaoUtilTest {

    @Test
    @DisplayName("Saudação com bom dia")
    public void saudarComBomdia() {
        int horaValida = 11;
        String saudacao = saudar(horaValida);
        String saudacaoCorreta = "Bom dia";
        assertThat(saudacaoCorreta).is(igualBomdia());

    }


    @Test
    public void saudarComBomDiaAPartir5() {

        int horaValida = 5;
        String saudacao = saudar(horaValida);
        assertEquals("Bom dia", saudacao);


    }


    @Test
    public void saudarComBoaNoite() {
        int horaValida = 20;
        String saudacao = saudar(horaValida);
        assertEquals("Boa noite", saudacao);

    }

    @Test
    public void lancarException() {
        int horaInavlida = -10;
        assertThatThrownBy(() -> saudar(horaInavlida)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Hora inválida");

    }

    @Test
    public void naoDeveLancarException() {
        int horaValida = 12;
        Executable metodoLancarException = () -> saudar(horaValida);
        assertDoesNotThrow(metodoLancarException);

    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7, 8, 9, 10, 10})
    public void Dado_horario_matinal_entao_deve_retonar_bom_dia(int hora) {
        String saudacao = saudar(hora);
        assertEquals("Bom dia", saudacao);
    }
}