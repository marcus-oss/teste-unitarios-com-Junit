package testeunitario.utilidade;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Conta Bancaria")
class ContaBancariaBDDTest {


    @Nested
    @DisplayName("Dado uma conta bancaria com saldo de R$ 10,00")
    class ContaBancariacomSaldo {

        private ContaBancaria contaBancaria;

        @BeforeEach
        void beforeEach() {
            contaBancaria = new ContaBancaria(BigDecimal.TEN);

        }

        @Nested
        @DisplayName("Quando efetuar um saque com menor")
        class SaquevalorMenor {

            private BigDecimal valorSaque = new BigDecimal("9.0");

            @Test
            @DisplayName("Então não deve  lancar exception")
            void deveLancarSaqueSemException() {
                assertDoesNotThrow(() -> contaBancaria.saque(valorSaque));
            }


            @Test
            @DisplayName("E deve subtrair do saldo")
            void deveSubtrairdoSaldo() {
                contaBancaria.saque(valorSaque);
                assertEquals(new BigDecimal("1.0"), contaBancaria.saldo());
            }
        }

        @Nested
        @DisplayName("Quando efetuar o saque com valor maior")
        class saqueComValorMaior {

            private BigDecimal valorSaque = new BigDecimal("20.0");

            @Test
            @DisplayName("Então deve lançar exception")
            void deveFalhar() {
                assertThrows(RuntimeException.class, () -> contaBancaria.saque(valorSaque));

            }

            @Test
            @DisplayName("Não deve alterar saldo")
            void naoDeveAlterarSaldo() {
                try {
                    contaBancaria.saque(valorSaque);
                } catch (Exception e) {
                }
                assertEquals(BigDecimal.TEN, contaBancaria.saldo());
            }
        }
    }


    @Nested
    @DisplayName("Dado uma conta bancaria com saldo de R$ 00")
    class ContabancariaSemSaldo {
        private ContaBancaria contaBancaria;

        @BeforeEach
        void beforeEach() {
            contaBancaria = new ContaBancaria(BigDecimal.ZERO);


        }

        @Nested
        @DisplayName("Quando efetuar o saque com valor maior")
        class saqueComValorMaior {

            private BigDecimal valorSaque = new BigDecimal("20.0");

            @Test
            @DisplayName("Então deve lançar exception")
            void deveFalhar() {
                assertThrows(RuntimeException.class, () -> contaBancaria.saque(valorSaque));

            }


        }

        @Nested
        @DisplayName("Quando efetuar saque com  R$ 8,00")
        class DepositoComDez {

            private BigDecimal valorDeposito = new BigDecimal("8.00");

            @Test
            @DisplayName("Então deve acumular no saldo")
            void deveAcumularAoSaldo() {
                contaBancaria.deposito(valorDeposito);
                assertEquals(new BigDecimal("8.00"), contaBancaria.saldo());
            }
        }

    }
}




