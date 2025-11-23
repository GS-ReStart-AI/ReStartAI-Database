package br.com.restartai.restart_ai.web.view;

import br.com.restartai.restart_ai.service.CurriculoMongoImportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/procedures/curriculos")
public class CurriculoMongoImportViewController {

    private final CurriculoMongoImportService importService;

    public CurriculoMongoImportViewController(CurriculoMongoImportService importService) {
        this.importService = importService;
    }

    @PostMapping("/import-mongo")
    public String importarParaMongo(RedirectAttributes redirectAttributes) {
        int qtd = importService.importarUltimoExportParaMongo();

        if (qtd <= 0) {
            redirectAttributes.addFlashAttribute(
                    "mensagemMongoErro",
                    "Nenhum currículo foi importado para o MongoDB. Verifique se existe JSON exportado."
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "mensagemMongoSucesso",
                    "Importação para MongoDB concluída. Documentos inseridos: " + qtd + "."
            );
        }

        return "redirect:/procedures/usuarios/teste";
    }
}
