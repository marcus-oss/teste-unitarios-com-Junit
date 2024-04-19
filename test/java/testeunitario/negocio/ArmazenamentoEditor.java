package testeunitario.negocio;

import testeunitario.Modelo.Editor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ArmazenamentoEditor implements testeunitario.blog.ArmazenamentoEditor {

    public boolean chamouSalvar;


    @Override
    public Editor salvar(Editor editor) {
        this.chamouSalvar = true;
        if (editor.getId() == null) {
            editor.setId(1L);
        }
        return editor;
    }

    @Override
    public Optional<Editor> encontrarPorId(Long editor) {
        return Optional.empty();
    }

    @Override
    public Optional<Editor> encontrarPorEmail(String email) {
        if (email.equals("marcus@teste.com")) {
            return Optional.of(new Editor(2L, "Marcus", "marcus@teste.com", BigDecimal.TEN, true));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Editor> encontrarPorEmailComIdDiferenteDe(String email, Long id) {
        return Optional.empty();
    }

    @Override
    public void remover(Long editorId) {

    }

    @Override
    public List<Editor> encontrarTodos() {
        return List.of();
    }
}
