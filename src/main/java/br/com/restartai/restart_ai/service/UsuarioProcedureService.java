package br.com.restartai.restart_ai.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

@Service
public class UsuarioProcedureService {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioProcedureService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void inserirUsuario(String nomeCompleto,
                               String cpf,
                               LocalDate dataNascimento,
                               String email,
                               String senha) {

        jdbcTemplate.execute((Connection con) -> {
            try (CallableStatement cs =
                         con.prepareCall("{ call PKG_RESTART_CORE.PRC_INSERIR_USUARIO(?, ?, ?, ?, ?) }")) {

                cs.setString(1, nomeCompleto);
                cs.setString(2, cpf);
                cs.setDate(3, java.sql.Date.valueOf(dataNascimento));
                cs.setString(4, email);
                cs.setString(5, senha);

                cs.execute();
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao executar PRC_INSERIR_USUARIO: " + e.getMessage(), e);
            }
            return null;
        });
    }
}
