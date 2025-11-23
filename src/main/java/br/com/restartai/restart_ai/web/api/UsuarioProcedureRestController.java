package br.com.restartai.restart_ai.web.api;

import br.com.restartai.restart_ai.dto.usuario.UsuarioCadastroDTO;
import br.com.restartai.restart_ai.service.UsuarioProcedureService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/procedures/usuarios")
public class UsuarioProcedureRestController {

    private final UsuarioProcedureService usuarioProcedureService;

    public UsuarioProcedureRestController(UsuarioProcedureService usuarioProcedureService) {
        this.usuarioProcedureService = usuarioProcedureService;
    }

    @PostMapping
    public ResponseEntity<String> criarUsuario(@Valid @RequestBody UsuarioCadastroDTO dto) {
        if (!dto.getSenha().equals(dto.getConfirmarSenha())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("As senhas não conferem.");
        }

        usuarioProcedureService.inserirUsuario(
                dto.getNomeCompleto(),
                dto.getCpf(),
                dto.getDataNascimento(),
                dto.getEmail(),
                dto.getSenha()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuário criado via PKG_RESTART_CORE.PRC_INSERIR_USUARIO.");
    }
}
