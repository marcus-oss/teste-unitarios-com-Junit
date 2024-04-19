package testeunitario.blog.utilidade;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ConversorSlugTest {

    @Test
    void DeveconverteJuntoComCodigo() {
        try (MockedStatic<GeradorCodigo> codigoMockedStatic = Mockito.mockStatic(GeradorCodigo.class)) {
            codigoMockedStatic.when(GeradorCodigo::gerar).thenReturn("12345");

            String converteredJuntoComCodigo = ConversorSlug.converterJuntoComCodigo("olá mundo");
            assertEquals("ola-mundo-12345", converteredJuntoComCodigo);

        }

    }

}