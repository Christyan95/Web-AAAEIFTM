package web.aaaeiftm.controller;

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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import web.aaaeiftm.ajax.NotificacaoAlertify;
import web.aaaeiftm.ajax.TipoNotificaoAlertify;
import web.aaaeiftm.filter.PessoaFilter;
import web.aaaeiftm.model.Pessoa;
import web.aaaeiftm.model.Status;
import web.aaaeiftm.pagination.PageWrapper;
import web.aaaeiftm.repository.PessoaRepository;
import web.aaaeiftm.service.PessoaService;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {

    private static final Logger logger = LoggerFactory.getLogger(PessoaController.class);

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    private void loggingErrosValidacao(String mensagem, BindingResult resultado) {
        logger.info(mensagem);
        logger.info("Erros encontrados:");
        for (FieldError erro : resultado.getFieldErrors()) {
            logger.info("{}", erro);
        }
    }

    // ==========
    @GetMapping("/cadastrar")
    public String abrirCadastro(Pessoa pessoa) {
        return "pessoas/cadastrar";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid Pessoa pessoa, BindingResult resultado, Model model) {

        if (resultado.hasErrors()) {
            loggingErrosValidacao("A pessoa recebida para cadastrar não é válido.", resultado);

            return "pessoas/cadastrar";
        } else {
            pessoaService.salvar(pessoa);
            return "redirect:/pessoas/mostrarmensagemcadastrook";
        }
    }

    @GetMapping("/mostrarmensagemcadastrook")
    public String mostrarMensagemCadastroOK(Pessoa pessoa, Model model) {

        NotificacaoAlertify notificacao = new NotificacaoAlertify("Pessoa cadastrada com sucesso!",
                TipoNotificaoAlertify.SUCESSO);
        model.addAttribute("notificacao", notificacao);

        return "pessoas/cadastrar";
    }
    // ==========

    // ==========
    @GetMapping("/abrirpesquisar")
    public String abrirPesquisar(Model model) {
        model.addAttribute("url", "/pessoas/pesquisar");
        model.addAttribute("uso", "pessoas");

        return "pessoas/pesquisar";
    }

    @GetMapping("/pesquisar")
    public String pesquisar(PessoaFilter filtro, Model model,
            @PageableDefault(size = 5) @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {
        Page<Pessoa> pagina = pessoaRepository.filtrar(filtro, pageable);
        PageWrapper<Pessoa> paginaWrapper = new PageWrapper<>(pagina, request);
        logger.info("Pessoas buscadas no BD: {}", paginaWrapper.getConteudo());
        model.addAttribute("pagina", paginaWrapper);
        model.addAttribute("uso", "pessoas");
        return "pessoas/mostrartodas";
    }
    // ==========

    // ==========
    @PostMapping("/abriralterar")
    public String abrirAlterar(Long codigo, Model model) {
        Optional<Pessoa> optPessoa = pessoaRepository.findById(codigo);
        if (optPessoa.isPresent()) {
            model.addAttribute("pessoa", optPessoa.get());
            return "pessoas/alterar";
        } else {
            model.addAttribute("opcao", "pessoas");
            model.addAttribute("mensagem", "Não existe pessoa com código: " + codigo);
            return "mostrarmensagem";
        }
    }

    @PostMapping("/alterar")
    public String alterar(Pessoa pessoa) {
        pessoaService.salvar(pessoa);
        return "redirect:/pessoas/mostrarmensagemalterarok";
    }

    @GetMapping("/mostrarmensagemalterarok")
    public String mostrarMensagemAlterarOK(Pessoa pessoa, Model model) {

        NotificacaoAlertify notificacao = new NotificacaoAlertify("Pessoa alterada com sucesso!",
                TipoNotificaoAlertify.SUCESSO);
        model.addAttribute("notificacao", notificacao);
        model.addAttribute("url", "/pessoas/pesquisar");
        model.addAttribute("uso", "pessoas");

        return "pessoas/pesquisar";
    }
    // ==========

    // ==========
    @PostMapping("/remover")
    public String remover(Long codigo, Model model) {
        Optional<Pessoa> optPessoa = pessoaRepository.findById(codigo);
        if (optPessoa.isPresent()) {
            Pessoa pessoa = optPessoa.get();
            pessoa.setStatus(Status.INATIVO);
            pessoaService.alterar(pessoa);
            return "redirect:/pessoas/mostrarmensagemremocaook";
        } else {
            model.addAttribute("opcao", "pessoas");
            model.addAttribute("mensagem", "Impossível remover pessoa com código: " + codigo);
            return "mostrarmensagem";
        }
    }

    @GetMapping("/mostrarmensagemremocaook")
    public String mostrarMensagemRemoverOK(Model model) {

        NotificacaoAlertify notificacao = new NotificacaoAlertify("Pessoa removida com sucesso!",
                TipoNotificaoAlertify.SUCESSO);
        model.addAttribute("notificacao", notificacao);
        model.addAttribute("url", "/pessoas/pesquisar");
        model.addAttribute("uso", "pessoas");

        return "pessoas/pesquisar";
    }
    // ==========
}
