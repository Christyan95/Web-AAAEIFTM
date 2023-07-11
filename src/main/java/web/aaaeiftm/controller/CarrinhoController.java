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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import web.aaaeiftm.ajax.NotificacaoAlertify;
import web.aaaeiftm.ajax.TipoNotificaoAlertify;
import web.aaaeiftm.filter.PessoaFilter;
import web.aaaeiftm.filter.ProdutosFilter;
import web.aaaeiftm.model.Carrinho;
import web.aaaeiftm.model.Pessoa;
import web.aaaeiftm.model.Produto;
import web.aaaeiftm.pagination.PageWrapper;
import web.aaaeiftm.repository.PessoaRepository;
import web.aaaeiftm.repository.ProdutoRepository;
import web.aaaeiftm.repository.UsuarioRepository;
import web.aaaeiftm.service.CarrinhoService;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    private static final Logger logger = LoggerFactory.getLogger(CarrinhoController.class);

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    private void colocarProdutosNoModel(Model model) {
        List<Produto> produtos = produtoRepository.findAll();
        logger.info("Produtos buscadas no BD: {}", produtos);
        model.addAttribute("todosProdutos", produtos);
    }

    // ==========
    @GetMapping("/comprar")
    public String abrirComprar(HttpSession sessao) {

        sessao.setAttribute("carrinho", new Carrinho());

        return "carrinho/comprar";
    }

    // ==========

    @Autowired
    private PessoaRepository pessoaRepository;

    @PostMapping("/escolherpessoa")
    public String abrirEscolhaPessoa(Model model) {
        model.addAttribute("url", "/carrinho/pesquisarpessoa");
        model.addAttribute("uso", "carrinho");

        return "pessoas/pesquisar";
    }

    @GetMapping("/pesquisarpessoa")
    public String pesquisar(PessoaFilter filtro, Model model,
            @PageableDefault(size = 5) @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {
        Page<Pessoa> pagina = pessoaRepository.filtrar(filtro, pageable);
        PageWrapper<Pessoa> paginaWrapper = new PageWrapper<>(pagina, request);
        logger.info("Pessoas buscadas no BD: {}", paginaWrapper.getConteudo());
        model.addAttribute("pagina", paginaWrapper);
        model.addAttribute("uso", "carrinho");
        return "pessoas/mostrartodas";
    }

    @PostMapping("/definirpessoa")
    public String definirPessoa(Long codigo, HttpSession sessao) {
        // Optional<Pessoa> optPessoa = pessoaRepository.findById(codigo);
        // if (optPessoa.isPresent()) {
        // Carrinho carrinho = (Carrinho) sessao.getAttribute("carrinho");
        // carrinho.setPessoa(optPessoa.get());

        // return "carrinho/comprar";
        // } else {
        // model.addAttribute("opcao", "pessoa");
        // model.addAttribute("mensagem", "Não existe pessoa com código: " + codigo);
        // return "mostrarmensagem";
        // }
        Carrinho carrinho = (Carrinho) sessao.getAttribute("carrinho");
        Pessoa pessoa = pessoaRepository.findById(codigo).orElseThrow();
        carrinho.setPessoa(pessoa);
        sessao.setAttribute("carrinho", carrinho);
        return "carrinho/comprar";
    }

    // ================================================================================

    @PostMapping("/escolherproduto")
    public String abrirEscolhaProduto(Model model) {
        colocarProdutosNoModel(model);
        model.addAttribute("url", "/carrinho/pesquisarproduto");
        model.addAttribute("uso", "carrinho");

        return "produtos/pesquisar";
    }

    @GetMapping("/pesquisarproduto")
    public String pesquisarProduto(ProdutosFilter filtro, Model model,
            @PageableDefault(size = 5) @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {
        Page<Produto> pagina = produtoRepository.filtrar(filtro, pageable);
        PageWrapper<Produto> paginaWrapper = new PageWrapper<>(pagina, request);
        logger.info("Produtos buscados no BD: {}", paginaWrapper.getConteudo());
        model.addAttribute("pagina", paginaWrapper);
        model.addAttribute("uso", "carrinho");
        return "produtos/mostrartodas";
    }

    @PostMapping("/definirproduto")
    public String definirProduto(Long codigo, HttpSession sessao) {
        // Optional<Produto> optProduto = produtoRepository.findById(codigo);
        // if (optProduto.isPresent()) {
        //     Carrinho carrinho = (Carrinho) sessao.getAttribute("carrinho");
        //     carrinho.setProduto(optProduto.get());

        //     return "carrinho/comprar";
        // } else {
        //     model.addAttribute("opcao", "produtos");
        //     model.addAttribute("mensagem", "Não existe produto com código: " + codigo);
        //     return "mostrarmensagem";
        // }
        Carrinho carrinho = (Carrinho) sessao.getAttribute("carrinho");
        Produto produto = produtoRepository.findById(codigo).orElseThrow();
        carrinho.setProduto(produto);
        sessao.setAttribute("carrinho", carrinho);
        return "carrinho/comprar";
    }

    // ===================================================================================

    @PostMapping("/comprar")
    public String cadastrar(Model model, HttpSession sessao) {
        Carrinho carrinho = (Carrinho) sessao.getAttribute("carrinho");

        if (carrinho.getPessoa() != null && carrinho.getProduto() != null) {
            carrinhoService.salvar(carrinho);
            sessao.removeAttribute("carrinho");

            return "redirect:/carrinho/mostrarmensagemcadastrook";
        } else {
            model.addAttribute("opcao", "produtos");
            model.addAttribute("mensagem", "Aplicação inválida para cadastrar");
            return "mostrarmensagem";
        }
    }

    @GetMapping("/mostrarmensagemcadastrook")
    public String mostrarMensagemCadastroOK(Model model, HttpSession sessao) {

        NotificacaoAlertify notificacao = new NotificacaoAlertify("Carrinho cadastrado com sucesso!",
                TipoNotificaoAlertify.SUCESSO);
        model.addAttribute("notificacao", notificacao);
        sessao.setAttribute("carrinho", new Carrinho());

        return "carrinho/comprar";
    }
}
