package testeunitario.utilidade;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaTest {


    @Test
    @Disabled("esse teste já foi aplicado e passado")
    void assertrivaAgrupada() {
        Pessoa pessoa = new Pessoa("Marcus", "Faria");


        assertAll("Arsseções de pessoa",
                () -> assertEquals("Marcus", pessoa.getNome()),
                () -> assertEquals("Faria", pessoa.getSobrenome()));

    }

}