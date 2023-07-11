package web.aaaeiftm.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import web.aaaeiftm.ajax.NotificacaoAlertify;
import web.aaaeiftm.ajax.TipoNotificaoAlertify;
import web.aaaeiftm.filter.UsuarioFilter;
import web.aaaeiftm.model.Papel;
import web.aaaeiftm.model.Usuario;
import web.aaaeiftm.pagination.PageWrapper;
import web.aaaeiftm.repository.PapelRepository;
import web.aaaeiftm.repository.UsuarioRepository;
import web.aaaeiftm.service.CadastroUsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private PapelRepository papelRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private void colocarPapeisNoModel(Model model) {
        List<Papel> papeis = papelRepository.findAll();
        logger.info("Papeis buscadas no BD: {}", papeis);
        model.addAttribute("todosPapeis", papeis);
    }

	@GetMapping("/cadastrar")
	public String abrirCadastroUsuario(Usuario usuario, Model model) {
		
		colocarPapeisNoModel(model);
		return "usuario/cadastrar";
	}
	
	@PostMapping("/cadastrar")
	public String cadastrarNovaUsuario(@Valid Usuario usuario, BindingResult resultado, Model model) {

		if (resultado.hasErrors()) {
			logger.info("O usuario recebido para cadastrar não é válido.");
			logger.info("Erros encontrados:");
			for (FieldError erro : resultado.getFieldErrors()) {
				logger.info("{}", erro);
			}
			
			colocarPapeisNoModel(model);
			return "usuario/cadastrar";
		} else {
			usuario.setAtivo(true);
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			cadastroUsuarioService.salvar(usuario);
			return "redirect:/usuarios/cadastrosucesso";
		}
	}
	
	@GetMapping("/cadastrosucesso")
	public String mostrarCadastroSucesso(Usuario usuario, Model model) {
		
		colocarPapeisNoModel(model);
		NotificacaoAlertify notificacao = 
				new NotificacaoAlertify("Cadastro de usuário efetuado com sucesso.",
						                TipoNotificaoAlertify.SUCESSO);
		model.addAttribute("notificacao", notificacao);
		return "usuario/cadastrar";
	} 

	// ==========

    @GetMapping("/abrirpesquisar")
    public String abrirPesquisar(Model model) {
		colocarPapeisNoModel(model);
        model.addAttribute("url", "/usuarios/pesquisar");
        model.addAttribute("uso", "usuarios");

        return "usuario/pesquisar";
    }

    @GetMapping("/pesquisar")
    public String pesquisar(UsuarioFilter filtro, Model model,
            @PageableDefault(size = 5) @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {
        Page<Usuario> pagina = usuarioRepository.filtrar(filtro, pageable);
        PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(pagina, request);
        logger.info("Usuarios buscadas no BD: {}", paginaWrapper.getConteudo());
        model.addAttribute("pagina", paginaWrapper);
        model.addAttribute("uso", "usuarios");
        return "usuario/mostrartodas";
    }
    // ==========
}
