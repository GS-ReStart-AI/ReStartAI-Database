package br.com.restartai.restart_ai.web.view;

import br.com.restartai.restart_ai.service.UsuarioProcedureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/procedures/usuarios")
public class UsuarioProcedureTesteViewController {

    private final UsuarioProcedureService usuarioProcedureService;

    public UsuarioProcedureTesteViewController(UsuarioProcedureService usuarioProcedureService) {
        this.usuarioProcedureService = usuarioProcedureService;
    }

    @GetMapping("/teste")
    public String mostrarTelaTeste(Model model) {
        return "usuario-procedure-teste";
    }

    @PostMapping("/teste")
    public String executarProcedureTeste(RedirectAttributes redirectAttributes) {
        usuarioProcedureService.inserirUsuario(
                "Usuario Procedure Teste",
                "11122233344",
                LocalDate.of(2000, 1, 1),
                "teste.video@restart.com",
                "123456"
        );

        redirectAttributes.addFlashAttribute(
                "mensagemSucesso",
                "Procedure executada e usuário de teste criado com sucesso."
        );

        return "redirect:/procedures/usuarios/teste";
    }
}
