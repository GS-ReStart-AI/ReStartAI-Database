package br.com.restartai.restart_ai.service;

import br.com.restartai.restart_ai.mongo.CurriculoMongo;
import br.com.restartai.restart_ai.mongo.CurriculoMongoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurriculoMongoImportService {

    private final CurriculoExportProcedureService exportService;
    private final CurriculoMongoRepository mongoRepository;
    private final ObjectMapper objectMapper;

    public CurriculoMongoImportService(CurriculoExportProcedureService exportService,
                                       CurriculoMongoRepository mongoRepository,
                                       ObjectMapper objectMapper) {
        this.exportService = exportService;
        this.mongoRepository = mongoRepository;
        this.objectMapper = objectMapper;
    }

    public int importarUltimoExportParaMongo() {
        String jsonArray = exportService.buscarUltimoJson();
        if (jsonArray == null || jsonArray.isBlank()) {
            return 0;
        }

        try {
            JsonNode root = objectMapper.readTree(jsonArray);
            if (!root.isArray()) {
                return 0;
            }

            List<CurriculoMongo> docs = new ArrayList<>();

            for (JsonNode node : root) {
                CurriculoMongo doc = new CurriculoMongo();

                if (node.hasNonNull("curriculo_id")) {
                    doc.setCurriculoId(node.get("curriculo_id").asLong());
                }

                JsonNode usuarioNode = node.get("usuario");
                if (usuarioNode != null && usuarioNode.isObject()) {
                    if (usuarioNode.hasNonNull("usuario_id")) {
                        doc.setUsuarioId(usuarioNode.get("usuario_id").asLong());
                    }
                    if (usuarioNode.hasNonNull("nome_completo")) {
                        doc.setNomeCompleto(usuarioNode.get("nome_completo").asText());
                    }
                    if (usuarioNode.hasNonNull("email")) {
                        doc.setEmail(usuarioNode.get("email").asText());
                    }
                }

                if (node.hasNonNull("tipo")) {
                    doc.setTipo(node.get("tipo").asInt());
                }
                if (node.hasNonNull("nome_arquivo")) {
                    doc.setNomeArquivo(node.get("nome_arquivo").asText());
                }
                if (node.hasNonNull("status")) {
                    doc.setStatus(node.get("status").asInt());
                }
                if (node.hasNonNull("texto")) {
                    doc.setTexto(node.get("texto").asText());
                }

                docs.add(doc);
            }

            mongoRepository.deleteAll();
            mongoRepository.saveAll(docs);

            return docs.size();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao importar JSON para MongoDB: " + e.getMessage(), e);
        }
    }
}
