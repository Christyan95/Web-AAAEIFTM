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
import web.aaaeiftm.filter.DiretorFilter;
import web.aaaeiftm.model.Area;
import web.aaaeiftm.model.Diretor;
import web.aaaeiftm.pagination.PageWrapper;
import web.aaaeiftm.repository.AreaRepository;
import web.aaaeiftm.repository.DiretorRepository;
import web.aaaeiftm.service.DiretorService;

@Controller
@RequestMapping("/diretor")
public class DiretorController {

    private static final Logger logger = LoggerFactory.getLogger(DiretorController.class);

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private DiretorService diretorService;

    @Autowired
    private DiretorRepository diretorRepository;

    private void colocarAreasNoModel(Model model) {
        List<Area> areas = areaRepository.findAll();
        logger.info("Areas buscadas no BD: {}", areas);
        model.addAttribute("todasAreas", areas);
    }

    private void loggingErrosValidacao(String mensagem, BindingResult resultado) {
        logger.info(mensagem);
        logger.info("Erros encontrados:");
        for (FieldError erro : resultado.getFieldErrors()) {
            logger.info("{}", erro);
        }
    }

    // ==========
    @GetMapping("/cadastrar")
    public String abrirCadastro(Diretor diretor, Model model) {

        colocarAreasNoModel(model);
        return "diretor/cadastrar";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid Diretor diretor, BindingResult resultado, Model model) {

        if (resultado.hasErrors()) {
            loggingErrosValidacao("O diretor recebido para cadastrar não é válido.", resultado);

            return "diretor/cadastrar";
        } else {
            diretorService.salvar(diretor);
            return "redirect:/diretor/mostrarmensagemcadastrook";
        }
    }

    @GetMapping("/mostrarmensagemcadastrook")
    public String mostrarMensagemCadastroOK(Diretor diretor, Model model) {

        colocarAreasNoModel(model);
        
        NotificacaoAlertify notificacao = new NotificacaoAlertify("Diretor cadastrado com sucesso!",
                TipoNotificaoAlertify.SUCESSO);
        model.addAttribute("notificacao", notificacao);

        return "diretor/cadastrar";
    }
    // ==========

    // ==========
    @GetMapping("/abrirpesquisar")
    public String abrirPesquisar(Model model) {
        colocarAreasNoModel(model);
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

    // // ==========
    // @PostMapping("/abriralterar")
    // public String abrirAlterar(Long codigo, Model model) {
    // Optional<Diretor> optDiretor = diretorRepository.findById(codigo);
    // if (optDiretor.isPresent()) {
    // model.addAttribute("diretor", optDiretor.get());
    // return "diretor/alterar";
    // } else {
    // model.addAttribute("opcao", "diretor");
    // model.addAttribute("mensagem", "Não existe diretor com código: " + codigo);
    // return "mostrarmensagem";
    // }
    // }

    // @PostMapping("/alterar")
    // public String alterar(Diretor diretor) {
    // diretorService.salvar(diretor);
    // return "redirect:/diretor/mostrarmensagemalterarok";
    // }

    // @GetMapping("/mostrarmensagemalterarok")
    // public String mostrarMensagemAlterarOK(Diretor diretor, Model model) {

    // NotificacaoAlertify notificacao = new NotificacaoAlertify("Diretor alterado
    // com sucesso!", TipoNotificaoAlertify.SUCESSO);
    // model.addAttribute("notificacao", notificacao);
    // model.addAttribute("url", "/diretor/pesquisar");
    // model.addAttribute("uso", "diretor");

    // return "diretor/pesquisar";
    // }
    // // ==========

    // // ==========
    // @PostMapping("/remover")
    // public String remover(Long codigo, Model model) {
    // Optional<Diretor> optPessoa = diretorRepository.findById(codigo);
    // if (optPessoa.isPresent()) {
    // Diretor diretor = optPessoa.get();
    // diretor.setStatus(Status.INATIVO);
    // diretorService.alterar(diretor);
    // return "redirect:/diretor/mostrarmensagemremocaook";
    // } else {
    // model.addAttribute("opcao", "diretor");
    // model.addAttribute("mensagem", "Impossível remover diretor com código: " +
    // codigo);
    // return "mostrarmensagem";
    // }
    // }

    // @GetMapping("/mostrarmensagemremocaook")
    // public String mostrarMensagemRemoverOK(Model model) {

    // NotificacaoAlertify notificacao = new NotificacaoAlertify("Diretor removida
    // com sucesso!",
    // TipoNotificaoAlertify.SUCESSO);
    // model.addAttribute("notificacao", notificacao);
    // model.addAttribute("url", "/diretor/pesquisar");
    // model.addAttribute("uso", "diretor");

    // return "diretor/pesquisar";
    // }
    // // ==========
}
