package web.aaaeiftm.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import web.aaaeiftm.ajax.NotificacaoAlertify;
import web.aaaeiftm.ajax.TipoNotificaoAlertify;
import web.aaaeiftm.filter.DiretorFilter;
import web.aaaeiftm.model.Area;
import web.aaaeiftm.model.Diretor;
import web.aaaeiftm.model.Status;
import web.aaaeiftm.pagination.PageWrapper;
import web.aaaeiftm.repository.AreaRepository;
import web.aaaeiftm.repository.DiretorRepository;
import web.aaaeiftm.service.DiretorService;

@Controller
@RequestMapping("/diretor")
public class DiretorController {

    private static final Logger logger = LoggerFactory.getLogger(DiretorController.class);

    @Autowired
    private DiretorRepository diretorRepository;

    @Autowired
    private DiretorService diretorService;

    @Autowired
    private AreaRepository areaRepository;

    // ==========
    @GetMapping("/cadastrar")
    public String abrirCadastro(Diretor diretor, Model model) {
        List<Area> areas = areaRepository.findAll();
        model.addAttribute("todosAreas", areas);
        return "diretor/cadastrar";
    }

    @PostMapping("/cadastrar")
    public String cadastrarNovoDiretor(@Valid Diretor diretor, BindingResult resultado, Model model) {

        if (resultado.hasErrors()) {
			logger.info("O diretor recebido para cadastrar não é válido.");
			
			List<Area> areas = areaRepository.findAll();
			model.addAttribute("todosAreas", areas);
			return "diretor/cadastrar";
		} else {
			diretorService.salvar(diretor);
			return "redirect:/diretor/cadastrosucesso";
		}
    }

    @GetMapping("/cadastrosucesso")
    public String mostrarCadastroSucesso(Diretor diretor, Model model) {

        List<Area> areas = areaRepository.findAll();
			model.addAttribute("todosAreas", areas);
        NotificacaoAlertify notificacao = new NotificacaoAlertify("Diretor cadastrado com sucesso!",
                TipoNotificaoAlertify.SUCESSO);
        model.addAttribute("notificacao", notificacao);

        return "diretor/cadastrar";
    }
    // ==========

    // ==========
    @GetMapping("/abrirpesquisar")
    public String abrirPesquisar(Model model) {
        model.addAttribute("url", "/diretor/pesquisar");
        model.addAttribute("uso", "diretor");

        return "diretor/pesquisar";
    }

    @GetMapping("/pesquisar")
    public String pesquisar(DiretorFilter filtro, Model model,
            @PageableDefault(size = 5) @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {
        Page<Diretor> pagina = diretorRepository.filtrar(filtro, pageable);
        PageWrapper<Diretor> paginaWrapper = new PageWrapper<>(pagina, request);
        logger.info("Diretores buscados no BD: {}", paginaWrapper.getConteudo());
        model.addAttribute("pagina", paginaWrapper);
        model.addAttribute("uso", "diretor");
        return "diretor/mostrartodas";
    }
    // ==========

    // ==========
    @PostMapping("/abriralterar")
    public String abrirAlterar(Long codigo, Model model) {
        Optional<Diretor> optDiretor = diretorRepository.findById(codigo);
        if (optDiretor.isPresent()) {
            model.addAttribute("diretor", optDiretor.get());
            return "diretor/alterar";
        } else {
            model.addAttribute("opcao", "diretor");
            model.addAttribute("mensagem", "Não existe diretor com código: " + codigo);
            return "mostrarmensagem";
        }
    }

    @PostMapping("/alterar")
    public String alterar(Diretor diretor) {
        diretorService.salvar(diretor);
        return "redirect:/diretor/mostrarmensagemalterarok";
    }

    @GetMapping("/mostrarmensagemalterarok")
    public String mostrarMensagemAlterarOK(Diretor diretor, Model model) {

        NotificacaoAlertify notificacao = new NotificacaoAlertify("Diretor alterado com sucesso!", TipoNotificaoAlertify.SUCESSO);
        model.addAttribute("notificacao", notificacao);
        model.addAttribute("url", "/diretor/pesquisar");
        model.addAttribute("uso", "diretor");

        return "diretor/pesquisar";
    }
    // ==========

    // ==========
    @PostMapping("/remover")
    public String remover(Long codigo, Model model) {
        Optional<Diretor> optPessoa = diretorRepository.findById(codigo);
        if (optPessoa.isPresent()) {
            Diretor diretor = optPessoa.get();
            diretor.setStatus(Status.INATIVO);
            diretorService.alterar(diretor);
            return "redirect:/diretor/mostrarmensagemremocaook";
        } else {
            model.addAttribute("opcao", "diretor");
            model.addAttribute("mensagem", "Impossível remover diretor com código: " + codigo);
            return "mostrarmensagem";
        }
    }

    @GetMapping("/mostrarmensagemremocaook")
    public String mostrarMensagemRemoverOK(Model model) {

        NotificacaoAlertify notificacao = new NotificacaoAlertify("Diretor removida com sucesso!",
                TipoNotificaoAlertify.SUCESSO);
        model.addAttribute("notificacao", notificacao);
        model.addAttribute("url", "/diretor/pesquisar");
        model.addAttribute("uso", "diretor");

        return "diretor/pesquisar";
    }
    // ==========
}
