package web.aaaeiftm.service;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class RelatorioService {

	private static final Logger logger = LoggerFactory.getLogger(RelatorioService.class);

	@Autowired
	private DataSource dataSource;
	
	public byte[] gerarRelatorioSimplesTodasPessoas() {
		logger.trace("Entrou em gerarRelatorioSimplesTodasPessoas");
		InputStream arquivoJasper = getClass().getResourceAsStream("/relatorio/usuario/Cherry5.jasper");
		try (Connection conexao = dataSource.getConnection()){
			try {
				JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasper, null, conexao);
				return JasperExportManager.exportReportToPdf(jasperPrint);
			} catch (JRException e) {
				logger.error("Problemas na geracao do PDF do relatório: " + e);
			}
		} catch (SQLException e) {
			logger.error("Problemas na obtenção de uma conexão com o BD na geração de relatório: " + e);
		}

		return null;
	}

	public byte[] gerarRelatorioSimplesTodosCompras() {
		logger.trace("Entrou em gerarRelatorioSimplesTodosCompras");
		InputStream arquivoJasper = getClass().getResourceAsStream("/relatorio/comprar/Cherry1.jasper");
		try (Connection conexao = dataSource.getConnection()){
			try {
				JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasper, null, conexao);
				return JasperExportManager.exportReportToPdf(jasperPrint);
			} catch (JRException e) {
				logger.error("Problemas na geracao do PDF do relatório: " + e);
			}
		} catch (SQLException e) {
			logger.error("Problemas na obtenção de uma conexão com o BD na geração de relatório: " + e);
		}

		return null;
	}

	public byte[] gerarRelatorioSimplesTodosProdutos() {
		logger.trace("Entrou em gerarRelatorioSimplesTodosProdutos");
		InputStream arquivoJasper = getClass().getResourceAsStream("/relatorio/produto/Cherry4.jasper");
		try (Connection conexao = dataSource.getConnection()){
			try {
				JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasper, null, conexao);
				return JasperExportManager.exportReportToPdf(jasperPrint);
			} catch (JRException e) {
				logger.error("Problemas na geracao do PDF do relatório: " + e);
			}
		} catch (SQLException e) {
			logger.error("Problemas na obtenção de uma conexão com o BD na geração de relatório: " + e);
		}

		return null;
	}

	public byte[] gerarRelatorioSimplesTodosCliente() {
		logger.trace("Entrou em gerarRelatorioSimplesTodosCliente");
		InputStream arquivoJasper = getClass().getResourceAsStream("/relatorio/cliente/Cherry2.jasper");
		try (Connection conexao = dataSource.getConnection()){
			try {
				JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasper, null, conexao);
				return JasperExportManager.exportReportToPdf(jasperPrint);
			} catch (JRException e) {
				logger.error("Problemas na geracao do PDF do relatório: " + e);
			}
		} catch (SQLException e) {
			logger.error("Problemas na obtenção de uma conexão com o BD na geração de relatório: " + e);
		}

		return null;
	}

	public byte[] gerarRelatorioSimplesTodosDiretor() {
		logger.trace("Entrou em gerarRelatorioSimplesTodosDiretor");
		InputStream arquivoJasper = getClass().getResourceAsStream("/relatorio/diretor/Cherry3.jasper");
		try (Connection conexao = dataSource.getConnection()){
			try {
				JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasper, null, conexao);
				return JasperExportManager.exportReportToPdf(jasperPrint);
			} catch (JRException e) {
				logger.error("Problemas na geracao do PDF do relatório: " + e);
			}
		} catch (SQLException e) {
			logger.error("Problemas na obtenção de uma conexão com o BD na geração de relatório: " + e);
		}

		return null;
	}
}
