package web.aaaeiftm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import web.aaaeiftm.service.RelatorioService;

@Controller
@RequestMapping("/relatorio")
public class RelatorioController {

	private static final Logger logger = LoggerFactory.getLogger(RelatorioController.class);
	
	@Autowired
	private RelatorioService relatorioService;
	
	@GetMapping
	public ResponseEntity<byte[]> gerarRelatorioSimplesTodasPessoas() {
		logger.trace("Entrou em gerarRelatorioSimplesTodasPessoas");
		logger.debug("Gerando relatório simples de todas as pessoas");
		
		byte[] relatorio = relatorioService.gerarRelatorioSimplesTodasPessoas();
		
		logger.debug("Relatório simples de todas as pessoas gerado");
		logger.debug("Retornando o relatório simples de todas as pessoas");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Cherry_usuario.pdf")
				.body(relatorio);
	}
	
	@GetMapping("/relatoriocomprar")
	public ResponseEntity<byte[]> gerarRelatorioSimplesTodosCompras() {
		logger.trace("Entrou em gerarRelatorioSimplesTodosCompras");
		logger.debug("Gerando relatório simples de todas as pessoas");
		
		byte[] relatorio = relatorioService.gerarRelatorioSimplesTodosCompras();
		
		logger.debug("Relatório simples de todas as pessoas gerado");
		logger.debug("Retornando o relatório simples de todas as pessoas");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Cherry_carrinho.pdf")
				.body(relatorio);
	}
	
	@GetMapping("/relatorioproduto")
	public ResponseEntity<byte[]> gerarRelatorioSimplesTodosProdutos() {
		logger.trace("Entrou em gerarRelatorioSimplesTodosProdutos");
		logger.debug("Gerando relatório simples de todas as pessoas");
		
		byte[] relatorio = relatorioService.gerarRelatorioSimplesTodosProdutos();
		
		logger.debug("Relatório simples de todas as pessoas gerado");
		logger.debug("Retornando o relatório simples de todas as pessoas");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Cherry_produto.pdf")
				.body(relatorio);
	}

	@GetMapping("/relatoriocliente")
	public ResponseEntity<byte[]> gerarRelatorioSimplesTodosCliente() {
		logger.trace("Entrou em gerarRelatorioSimplesTodosCliente");
		logger.debug("Gerando relatório simples de todas as pessoas");
		
		byte[] relatorio = relatorioService.gerarRelatorioSimplesTodosCliente();
		
		logger.debug("Relatório simples de todas as pessoas gerado");
		logger.debug("Retornando o relatório simples de todas as pessoas");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Cherry_cliente.pdf")
				.body(relatorio);
	}

	@GetMapping("/relatoriodiretor")
	public ResponseEntity<byte[]> gerarRelatorioSimplesTodosDiretor() {
		logger.trace("Entrou em gerarRelatorioSimplesTodosDiretor");
		logger.debug("Gerando relatório simples de todas as pessoas");
		
		byte[] relatorio = relatorioService.gerarRelatorioSimplesTodosDiretor();
		
		logger.debug("Relatório simples de todas as pessoas gerado");
		logger.debug("Retornando o relatório simples de todas as pessoas");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Cherry_diretor.pdf")
				.body(relatorio);
	}
}