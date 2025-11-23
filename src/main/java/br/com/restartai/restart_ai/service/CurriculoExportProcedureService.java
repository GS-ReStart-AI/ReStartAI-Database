package br.com.restartai.restart_ai.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class CurriculoExportProcedureService {

    private final JdbcTemplate jdbcTemplate;

    public CurriculoExportProcedureService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void exportarCurriculosParaJson() {
        jdbcTemplate.execute((Connection con) -> {
            try (CallableStatement cs =
                         con.prepareCall("{ call PKG_RESTART_CORE.PRC_EXPORTAR_CURRICULOS_JSON }")) {
                cs.execute();
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao executar PRC_EXPORTAR_CURRICULOS_JSON: " + e.getMessage(), e);
            }
            return null;
        });
    }

    public String buscarUltimoJson() {
        return jdbcTemplate.query(
                """
                SELECT payload
                  FROM export_json_curriculo
                 ORDER BY id_export DESC
                 FETCH FIRST 1 ROWS ONLY
                """,
                rs -> rs.next() ? rs.getString("payload") : null
        );
    }
}
