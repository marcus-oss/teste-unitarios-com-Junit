package testeunitario.negocio;

import testeunitario.Modelo.Editor;

import java.math.BigDecimal;

public class EditorTestData {


    private EditorTestData() {
    }

    public static Editor.Builder editorNovo() {
        return Editor.builder()
                .comNome("Vinicius")
                .comEmail("vinicius@teste.com")
                .comvalorPagoPorPalavra(BigDecimal.TEN)
                .comPremium(true);
    }

    public static Editor.Builder umEditorExsitente() {
        return editorNovo().comId(1L);

    }

    public static Editor.Builder umEditorNaoExiste() {
        return editorNovo().comId(99L);

    }

}
