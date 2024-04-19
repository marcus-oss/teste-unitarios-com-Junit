package testeunitario.negocio;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import testeunitario.Modelo.Editor;
import testeunitario.blog.exception.EditorNaoEncontradoException;
import testeunitario.blog.exception.RegraNegocioException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CadastroEditocomMockTest {


    @Captor
    ArgumentCaptor<Mensagem> mensagemArgumentCaptor;
    @Mock
    GerenciadorEnvioEmail gerenciadorEnvioEmail;
    @Mock
    ArmazenamentoEditor armazenamentoEditor;
    @InjectMocks
    CadastroEditor cadastroEditor;

    @Nested
    class CadastrocomEditorValido {
        @Spy
        Editor editor = EditorTestData.editorNovo().build();

        @BeforeEach
        void initBefore() {
            when(armazenamentoEditor.salvar(any(Editor.class)))
                    .thenAnswer(invocationOnMock -> {
                        Editor editorPassado = invocationOnMock.getArgument(0, Editor.class);
                        editorPassado.setId(1L);
                        return editorPassado;
                    });

        }


        @Test
        void Dado_um_editor_Valido_entao_deve_retornar_um_id_de_cadastro() {
            Editor editorsalvo = cadastroEditor.criar(editor);
            assertEquals(1l, editorsalvo.getId());
        }

        @Test
        void Dado_um_editor_valido_quando_criar_deve_chamar_metodo_salvar_do_armazenamento() {
            cadastroEditor.criar(editor);
            verify(armazenamentoEditor, times(1))
                    .salvar(eq(editor));

        }

        @Test
        void Dado_um_editor_valido_Quando_criar_e_lancar_exception_ao_salvar_Entao_nao_deve_enviar_Email() {
            when(armazenamentoEditor.salvar(editor)).thenThrow(new RuntimeException());
            assertAll("não deve enviar email quando lançlar a exception ",
                    () -> assertThrows(RuntimeException.class, () -> cadastroEditor.criar(editor)),
                    () -> verify(gerenciadorEnvioEmail, never()).enviarEmail(any()));
        }

        @Test
        void Dado_ume_editor_valido_quando_cadastrar_entao_deve_enviar_email_com_destino_ao_editor() {
            Editor criared = cadastroEditor.criar(editor);
            verify(gerenciadorEnvioEmail).enviarEmail(mensagemArgumentCaptor.capture());

            Mensagem mensagemArgumentCaptorValue = mensagemArgumentCaptor.getValue();
            assertEquals(criared.getEmail(), mensagemArgumentCaptorValue.getDestinatario());
        }

        @Test
        void Dado_um_editor_valido_quando_cadastrar_entao_deve_verificar_email() {
            cadastroEditor.criar(editor);
            verify(editor, atLeast(1)).getEmail();

        }

        @Test
        void Dado_um_editor_com_email_existente_quando_cadstrar_entao_deve_lancar_exception() {
            when(armazenamentoEditor.encontrarPorEmail("vinicius@teste.com"))
                    .thenReturn(Optional.empty())
                    .thenReturn(Optional.of(editor));

            Editor editorComEmailExistente = EditorTestData.umEditorExsitente().build();
            cadastroEditor.criar(editor);
            assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editorComEmailExistente));
        }

        @Test
        void Dado_um_editor_valido_quando_cadastrar_entao_deve_enviar_email_apos_salvar() {
            cadastroEditor.criar(editor);

            InOrder inedOrder = inOrder(armazenamentoEditor, gerenciadorEnvioEmail);
            inedOrder.verify(armazenamentoEditor, times(1)).salvar(editor);
            inedOrder.verify(gerenciadorEnvioEmail, times(1)).enviarEmail(any(Mensagem.class));
        }
    }

    @Nested
    class cadastroComEditorNull {

        @Test
        void Dado_um_editor_null_quando_cadastrar_entao_deve_lancar_exception() {
            Assertions.assertThrows(NullPointerException.class, () -> cadastroEditor.criar(null));
            verify(armazenamentoEditor, never()).salvar(any());
            verify(gerenciadorEnvioEmail, never()).enviarEmail(any());
        }
    }

    @Nested
    class EdicaoComeditorValido {

        @Spy
        Editor editor = EditorTestData.editorNovo().build();

        @BeforeEach
        void InitBefore() {
            Mockito.when(armazenamentoEditor.salvar(editor)).thenAnswer(invocacao -> invocacao.getArgument(0, Editor.class));
            Mockito.when(armazenamentoEditor.encontrarPorId(1L)).thenReturn(Optional.of(editor));
        }

        @Test
        void dado_um_editor_valido_Quando_editar_entao_deve_alterar_editor_salvo() {
            Editor editorNovo = EditorTestData.umEditorExsitente()
                    .comEmail("vinicius@teste.com")
                    .comNome("Vinicius Faria")
                    .build();

            cadastroEditor.editar(editorNovo);
            verify(editor, times(1)).atualizarComDados(editorNovo);

            InOrder inOrder = inOrder(editor, armazenamentoEditor);
            inOrder.verify(editor).atualizarComDados(editorNovo);
            inOrder.verify(armazenamentoEditor).salvar(editor);
        }


    }

    @Nested
    class edicaoComEditorInexistente {
        Editor editor = EditorTestData.umEditorNaoExiste().build();

        @BeforeEach
        void initBefore() {
            when(armazenamentoEditor.encontrarPorId(99L)).thenReturn(Optional.empty());
        }

        @Test
        void Dado_um_editor_que_nao_exista_quando_editar_entao_nao_deve_alterar_o_editor_salvo() {
            assertThrows(EditorNaoEncontradoException.class, () -> cadastroEditor.editar(editor));
            verify(armazenamentoEditor, never()).salvar(Mockito.any(Editor.class));
        }
    }

}
