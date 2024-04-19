package testeunitario.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class FiltroNumerosTest {

    @Test
    public void deveRetornarNumerosPares() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> numerosPares = Arrays.asList(2, 4);
        List<Integer> resultadoFiltro = FiltroNumeros.numerosPares(numerosPares);
        Assertions.assertIterableEquals(numerosPares, resultadoFiltro);


    }
}