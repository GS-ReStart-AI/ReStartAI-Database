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
                "Ana Beatriz Souza",
                "12345678988",
                LocalDate.of(1998, 3, 15),
                "ana.beatriz@restart.com",
                "senhaAna123"
        );

        redirectAttributes.addFlashAttribute(
                "mensagemSucesso",
                "Usuária de teste (Ana Beatriz Souza) criada via procedure."
        );

        return "redirect:/procedures/usuarios/teste";
    }

    @PostMapping("/teste2")
    public String executarSegundoTeste(RedirectAttributes redirectAttributes) {
        usuarioProcedureService.inserirUsuario(
                "Carlos Eduardo Lima",
                "98765432100",
                LocalDate.of(1995, 9, 30),
                "carlos.eduardo@restart.com",
                "senhaCarlos123"
        );

        redirectAttributes.addFlashAttribute(
                "mensagemSucesso",
                "Usuário de teste 2 (Carlos Eduardo Lima) criado via procedure."
        );

        return "redirect:/procedures/usuarios/teste";
    }
}
