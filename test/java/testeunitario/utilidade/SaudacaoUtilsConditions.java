package testeunitario.utilidade;

import org.assertj.core.api.Condition;

import static org.assertj.core.api.Assertions.assertThat;

public class SaudacaoUtilsConditions {

    private SaudacaoUtilsConditions() {

    }

    public static Condition<String> igualBomdia() {
        return igualSaudacao("Bom dia");

    }

    public static Condition<String> igualSaudacao(String saudacaoCorreta) {
        return new Condition<>((String) -> String.equals(saudacaoCorreta), "igual a %s", saudacaoCorreta);

    }
}
