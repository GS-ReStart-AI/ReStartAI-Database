package br.com.restartai.restart_ai.web.view;

import br.com.restartai.restart_ai.dto.usuario.UsuarioCadastroDTO;
import br.com.restartai.restart_ai.service.UsuarioProcedureService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/procedures/usuarios")
public class UsuarioProcedureFormController {

    private final UsuarioProcedureService usuarioProcedureService;

    public UsuarioProcedureFormController(UsuarioProcedureService usuarioProcedureService) {
        this.usuarioProcedureService = usuarioProcedureService;
    }

    @GetMapping("/novo")
    public String mostrarFormulario(Model model) {
        if (!model.containsAttribute("usuario")) {
            UsuarioCadastroDTO dto = new UsuarioCadastroDTO();
            dto.setEmail("teste.video@restart.com");
            model.addAttribute("usuario", dto);
        }
        return "usuario-procedure-form";
    }

    @PostMapping("/novo")
    public String processarFormulario(@Valid @ModelAttribute("usuario") UsuarioCadastroDTO dto,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.usuario", bindingResult);
            redirectAttributes.addFlashAttribute("usuario", dto);
            return "redirect:/procedures/usuarios/novo";
        }

        usuarioProcedureService.inserirUsuario(
                dto.getNomeCompleto(),
                dto.getCpf(),
                dto.getDataNascimento(),
                dto.getEmail(),
                dto.getSenha()
        );

        redirectAttributes.addFlashAttribute(
                "mensagemSucesso",
                "Usuário criado via procedure PKG_RESTART_CORE.PRC_INSERIR_USUARIO."
        );

        return "redirect:/procedures/usuarios/novo";
    }
}
