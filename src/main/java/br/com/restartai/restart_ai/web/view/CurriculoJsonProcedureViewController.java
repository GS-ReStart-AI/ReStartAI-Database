package br.com.restartai.restart_ai.web.view;

import br.com.restartai.restart_ai.service.CurriculoExportProcedureService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/procedures/curriculos")
public class CurriculoJsonProcedureViewController {

    private final CurriculoExportProcedureService exportService;

    public CurriculoJsonProcedureViewController(CurriculoExportProcedureService exportService) {
        this.exportService = exportService;
    }

    @PostMapping("/export")
    public String exportar(RedirectAttributes redirectAttributes) {
        exportService.exportarCurriculosParaJson();
        String json = exportService.buscarUltimoJson();

        if (json == null || json.isBlank()) {
            redirectAttributes.addFlashAttribute(
                    "mensagemJsonErro",
                    "Nenhum currículo encontrado para exportar."
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "mensagemJsonSucesso",
                    "Currículos exportados para JSON via procedure."
            );
            redirectAttributes.addFlashAttribute("jsonPayload", json);
        }

        return "redirect:/procedures/usuarios/teste";
    }
}
