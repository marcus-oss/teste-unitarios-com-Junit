package testeunitario.negocio;

import org.junit.jupiter.api.*;
import testeunitario.Modelo.Editor;
import testeunitario.blog.exception.RegraNegocioException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CadastroEditorStubTest {

    CadastroEditor cadastroEditor;
    Editor editor;
    ArmazenamentoEditor armazenamentoEditor;


    @BeforeEach
    void initBefore() {

        editor = new Editor(null, "Marcus", "marcus@teste123.com", BigDecimal.TEN, true);

        armazenamentoEditor = new ArmazenamentoEditor();

        cadastroEditor = new CadastroEditor(armazenamentoEditor, new GerenciadorEnvioEmail() {
            @Override
            void enviarEmail(Mensagem mensagem) {
                System.out.println("Enviando mensagem..." + mensagem.getDestinatario());
            }
        });

    }

    @Test
    public void Dado_um_editor_valido_quando_deve_criar_entao_deve_retornar_um_id_de_cadastro() {
        Editor criared = cadastroEditor.criar(editor);
        assertEquals(1l, criared.getId());
        assertTrue(armazenamentoEditor.chamouSalvar);
    }

    @Test
    public void Dado_um_editor_null_quando_criar_entao_deve_retornar_exception() {
        assertThrows(NullPointerException.class, () -> cadastroEditor.criar(null));
        assertFalse(armazenamentoEditor.chamouSalvar);
    }

    @Test
    public void Dado_um_editor_com_email_existente_qaundo_criar_entao_deve_lancar_exception() {
        editor.setEmail("marcus@teste.com");
        assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editor));
        assertFalse(armazenamentoEditor.chamouSalvar);
    }

    @Test
    public void Dado_um_editor_com_email_existente_qaundo_criar_entao_nao_de_salvar() {
        editor.setEmail("marcus@teste.com");
        try {
            cadastroEditor.criar(editor);
        } catch (RegraNegocioException e) {
            assertFalse(armazenamentoEditor.chamouSalvar);
        }
    }
}