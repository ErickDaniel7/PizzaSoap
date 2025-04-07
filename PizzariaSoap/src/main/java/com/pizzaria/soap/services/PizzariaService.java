package com.pizzaria.soap.services;

import com.pizzaria.soap.models.Cliente;
import com.pizzaria.soap.models.Pedido;
import com.pizzaria.soap.models.StatusPedido;
import com.pizzaria.soap.daos.ClienteDAO;
import com.pizzaria.soap.daos.PedidoDAO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@WebService
public class PizzariaService {

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final PedidoDAO pedidoDAO = new PedidoDAO();

    @WebMethod
    public String criarCliente(String nome, String telefone, String cpf, String endereco, String dataNascimento) {
        // Validações simples para CPF e telefone (de acordo com regras de formato)
        if (cpf == null || !cpf.matches("\\d{11}")) {
            return "Erro: CPF inválido. O CPF deve conter 11 dígitos.";
        }
        if (telefone == null || telefone.trim().isEmpty()) {
            return "Erro: Telefone não pode ser vazio.";
        }

        // Formatação e validação da data de nascimento
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataNasc;
        try {
            dataNasc = sdf.parse(dataNascimento);
        } catch (ParseException e) {
            return "Erro: Formato de data inválido. Use dd/MM/yyyy";
        }

        // Criação e persistência do cliente
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setCpf(cpf);
        cliente.setEndereco(endereco);
        cliente.setDataNascimento(dataNasc);

        clienteDAO.salvar(cliente);
        return "Cliente cadastrado com sucesso! ID: " + cliente.getId();
    }

    @WebMethod
    public String criarNovoPedido(int clienteId) {
        // Verificação se o cliente existe
        Cliente cliente = clienteDAO.buscarPorId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Erro: Cliente não encontrado"));

        // Criação e persistência do pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.RECEBIDO);
        pedidoDAO.salvar(pedido);

        return "Pedido criado com sucesso! ID: " + pedido.getId();
    }

    @WebMethod
    public String consultarStatusPedido(int pedidoId) {
        // Verificação se o pedido existe
        Pedido pedido = pedidoDAO.buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Erro: Pedido não encontrado"));

        return "Status do Pedido: " + pedido.getStatus();
    }
}
