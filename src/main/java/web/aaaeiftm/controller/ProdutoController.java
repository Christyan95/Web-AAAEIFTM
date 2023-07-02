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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import web.aaaeiftm.ajax.NotificacaoAlertify;
import web.aaaeiftm.ajax.RespostaJSON;
import web.aaaeiftm.ajax.ThymeleafUtil;
import web.aaaeiftm.ajax.TipoNotificaoAlertify;
import web.aaaeiftm.ajax.TipoResposta;
import web.aaaeiftm.filter.ProdutosFilter;
import web.aaaeiftm.model.Status;
import web.aaaeiftm.model.Produto;
import web.aaaeiftm.pagination.PageWrapper;
import web.aaaeiftm.repository.ProdutoRepository;
import web.aaaeiftm.service.ProdutoService;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ThymeleafUtil thymeleafUtil;

    private void loggingErrosValidacao(String mensagem, BindingResult resultado) {
        logger.info(mensagem);
        logger.info("Erros encontrados:");
        for (FieldError erro : resultado.getFieldErrors()) {
            logger.info("{}", erro);
        }
    }

    // ==========
    @GetMapping("/cadastrar")
    public String abrirCadastrar(Produto produto) {
        return "produtos/cadastrar";
    }

    @PostMapping(value = { "/cadastrar" }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public RespostaJSON cadastrar(@RequestBody @Valid Produto produto, BindingResult resultado,
            Model model, HttpServletRequest request,
            HttpServletResponse response) {
        RespostaJSON respostaJSON;

        if (resultado.hasErrors()) {
            loggingErrosValidacao("O produto recebido para cadastrar não é válido.", resultado);
            
            String html = thymeleafUtil.processThymeleafTemplate(request, response,
                    model.asMap(), "produtos/cadastrar", "formulario");

            respostaJSON = new RespostaJSON(TipoResposta.FRAGMENTO);
            respostaJSON.setHtmlFragmento(html);
        } else {
            produtoService.salvar(produto);
            model.addAttribute("produto", new Produto());

            String html = thymeleafUtil.processThymeleafTemplate(request, response,
                    model.asMap(), "produtos/cadastrar", "formulario");

            respostaJSON = new RespostaJSON(TipoResposta.FRAGMENTO_E_NOTIFICACAO);
            respostaJSON.setHtmlFragmento(html);

            NotificacaoAlertify notificacaoAlertify = new NotificacaoAlertify("Produto cadastrado com sucesso!",
                    TipoNotificaoAlertify.SUCESSO);

            respostaJSON.setNotificacao(notificacaoAlertify);
        }
        return respostaJSON;
    }

    @GetMapping("/mostrarmensagemcadastrook")
    public String mostrarMensagemCadastroOK(Produto produto, Model model) {

        NotificacaoAlertify notificacao = new NotificacaoAlertify("Produto cadastrado com sucesso!",
                TipoNotificaoAlertify.SUCESSO);
        model.addAttribute("notificacao", notificacao);

        return "produtos/cadastrar";
    }
    // ==========

    // //==========
    // @GetMapping("/cadastrar")
    // public String abrirCadastro(Produto produto) {
    //     return "produtos/cadastrar";
    // }

    // @PostMapping("/cadastrar")
    // public String cadastrar(Produto produto) {
    //     produtoService.salvar(produto);
    //     return "redirect:/produto/mostrarmensagemcadastrook";
    // }

    // @GetMapping("/mostrarmensagemcadastrook")
    // public String mostrarMensagemCadastroOK(Produto produto, Model model) {
        
    //     NotificacaoAlertify notificacao = new NotificacaoAlertify("Produto cadastrado com sucesso!", TipoNotificaoAlertify.SUCESSO);
    //     model.addAttribute("notificacao", notificacao);

    //     return "produto/cadastrar";
    // }
    // //==========

    // ==========
    @GetMapping("/abrirpesquisar")
    public String abrirPesquisar() {
        return "produtos/pesquisar";
    }

    @GetMapping("/pesquisar")
    public String pesquisar(ProdutosFilter filtro, Model model,
            @PageableDefault(size = 5) @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {
        Page<Produto> pagina = produtoRepository.filtrar(filtro, pageable);
        PageWrapper<Produto> paginaWrapper = new PageWrapper<>(pagina, request);
        logger.info("Produtos buscados no BD: {}", paginaWrapper.getConteudo());
        model.addAttribute("pagina", paginaWrapper);
        return "produtos/mostrartodas";
    }
    // ==========

    // ==========
    @GetMapping("/abriralterar")
    public String abrirAlterar(Long codigo, Model model) {
        Optional<Produto> optProduto = produtoRepository.findById(codigo);
        if (optProduto.isPresent()) {
            model.addAttribute("produto", optProduto.get());
            return "produtos/alterar";
        } else {
            model.addAttribute("opcao", "produtos");
            model.addAttribute("mensagem", "Não existe produto com código: " + codigo);
            return "mostrarmensagem";
        }
    }

    @PostMapping("/abriralterar")
    public String abrirAlterarPost(Long codigo, Model model) {
        Optional<Produto> optProduto = produtoRepository.findById(codigo);
        if (optProduto.isPresent()) {
            model.addAttribute("produto", optProduto.get());
            return "produtos/alterar";
        } else {
            model.addAttribute("opcao", "produtos");
            model.addAttribute("mensagem", "Não existe produto com código: " + codigo);
            return "mostrarmensagem";
        }
    }

    @PostMapping("/alterar")
    public String alterar(@Valid Produto produto, BindingResult resultado) {

        if (resultado.hasErrors()) {
            loggingErrosValidacao("O produto recebido para alterar não é válido.", resultado);

            return "produtos/alterar";
        } else {
            produtoService.salvar(produto);
            return "redirect:/produtos/mostrarmensagemalterarok";
        }
    }

    @GetMapping("/mostrarmensagemalterarok")
    public String mostrarMensagemAlterarOK(Model model) {

        NotificacaoAlertify notificacao = new NotificacaoAlertify("Produto alterado com sucesso!",
                TipoNotificaoAlertify.SUCESSO);
        model.addAttribute("notificacao", notificacao);

        return "produtos/pesquisar";
    }
    // ==========

    // ==========
    @PostMapping("/abrirremover")
    public String abrirConfirmar(Long codigo, Model model) {
        Optional<Produto> optProduto = produtoRepository.findById(codigo);
        if (optProduto.isPresent()) {
            model.addAttribute("produto", optProduto.get());
            return "produtos/confirmarremocao";
        } else {
            model.addAttribute("opcao", "produtos");
            model.addAttribute("mensagem", "Não existe produto com código: " + codigo);
            return "mostrarmensagem";
        }
    }

    @PostMapping("/remover")
    public String remover(Long codigo, Model model) {
        Optional<Produto> optProduto = produtoRepository.findById(codigo);
        if (optProduto.isPresent()) {
            Produto produto = optProduto.get();
            produto.setStatus(Status.INATIVO);
            produtoService.alterar(produto);
            return "redirect:/produtos/mostrarmensagemremocaook";
        } else {
            model.addAttribute("opcao", "produtos");
            model.addAttribute("mensagem", "Impossível remover produto com código: " + codigo);
            return "mostrarmensagem";
        }
    }

    @GetMapping("/mostrarmensagemremocaook")
    public String mostrarMensagemRemoverOK(Model model) {

        NotificacaoAlertify notificacao = new NotificacaoAlertify("Produto removido com sucesso!",
                TipoNotificaoAlertify.SUCESSO);
        model.addAttribute("notificacao", notificacao);

        return "produtos/pesquisar";
    }
    // ==========
}
