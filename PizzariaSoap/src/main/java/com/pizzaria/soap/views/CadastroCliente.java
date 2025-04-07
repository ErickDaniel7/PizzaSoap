package com.pizzaria.soap.views;

import com.pizzaria.soap.daos.ClienteDAO;
import com.pizzaria.soap.daos.PedidoDAO;
import com.pizzaria.soap.models.*;
import com.pizzaria.soap.services.AtualizadorPedidos;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CadastroCliente {

    public static void main(String[] args) {
        AtualizadorPedidos.iniciarAtualizacaoAutomatica();

        // Criando a janela principal com um layout mais bonito
        JFrame frame = new JFrame("Pizzaria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        // Criando um painel com layout de borda
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Criando um título
        JLabel titleLabel = new JLabel("Menu Principal", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Criando o menu com botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));

        JButton cadastrarButton = new JButton("Cadastrar Cliente");
        JButton fazerPedidoButton = new JButton("Fazer Pedido");
        JButton consultarStatusButton = new JButton("Consultar Status");
        JButton sairButton = new JButton("Sair");

        // Estilizando os botões
        Font buttonFont = new Font("Arial", Font.PLAIN, 18);
        cadastrarButton.setFont(buttonFont);
        fazerPedidoButton.setFont(buttonFont);
        consultarStatusButton.setFont(buttonFont);
        sairButton.setFont(buttonFont);

        buttonPanel.add(cadastrarButton);
        buttonPanel.add(fazerPedidoButton);
        buttonPanel.add(consultarStatusButton);
        buttonPanel.add(sairButton);

        // Adicionando os botões ao painel
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Adicionando o painel principal ao frame
        frame.add(panel);
        frame.setVisible(true);

        // Ações dos botões
        cadastrarButton.addActionListener(e -> {
            Cliente cliente = cadastrarCliente();
            if (cliente != null) {
                JOptionPane.showMessageDialog(frame, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        fazerPedidoButton.addActionListener(e -> {
            Optional<Cliente> cliente = selecionarCliente();
            if (cliente.isPresent()) {
                realizarPedido(cliente.get());
            } else {
                JOptionPane.showMessageDialog(frame, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        consultarStatusButton.addActionListener(e -> consultarPedido());

        sairButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Saindo do sistema...");
            System.exit(0);
        });
    }

    // MÉTODO PARA CADASTRAR UM NOVO CLIENTE
    private static Cliente cadastrarCliente() {
        try {
            // Criar painel para cadastro
            JTextField nomeField = new JTextField(20);
            JTextField telefoneField = new JTextField(20);
            JTextField cpfField = new JTextField(20);
            JTextField enderecoField = new JTextField(20);
            JTextField dataNascimentoField = new JTextField(20);

            JPanel panel = new JPanel(new GridLayout(5, 2));
            panel.add(new JLabel("Nome:"));
            panel.add(nomeField);
            panel.add(new JLabel("Telefone:"));
            panel.add(telefoneField);
            panel.add(new JLabel("CPF:"));
            panel.add(cpfField);
            panel.add(new JLabel("Endereço:"));
            panel.add(enderecoField);
            panel.add(new JLabel("Data de Nascimento:"));
            panel.add(dataNascimentoField);

            int option = JOptionPane.showConfirmDialog(null, panel, "Cadastrar Cliente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                // Coleta de dados e validação
                String nome = nomeField.getText();
                String telefone = telefoneField.getText();
                String cpf = cpfField.getText();
                String endereco = enderecoField.getText();
                String dataNascimento = dataNascimentoField.getText();

                if (nome.isEmpty() || telefone.isEmpty() || cpf.isEmpty() || endereco.isEmpty() || dataNascimento.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return null;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date data = sdf.parse(dataNascimento);

                Cliente cliente = new Cliente();
                cliente.setNome(nome);
                cliente.setTelefone(telefone);
                cliente.setCpf(cpf);
                cliente.setEndereco(endereco);
                cliente.setDataNascimento(data);

                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.salvar(cliente);
                return cliente;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    // MÉTODO PARA SELECIONAR O CLIENTE PELO CPF
    private static Optional<Cliente> selecionarCliente() {
        String cpf = JOptionPane.showInputDialog("Digite o CPF do cliente para buscar o cadastro:");
        if (cpf == null || !cpf.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(null, "CPF inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return Optional.empty();
        }

        ClienteDAO clienteDAO = new ClienteDAO();
        Optional<Cliente> cliente = clienteDAO.buscarPorCpf(cpf);

        return cliente;
    }

    // MÉTODO PARA REALIZAR O PEDIDO
    private static void realizarPedido(Cliente cliente) {
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.RECEBIDO);
        BigDecimal valorTotalPedido = BigDecimal.ZERO;

        boolean adicionarMaisItens = true;
        while (adicionarMaisItens) {
            try {
                String saborPizza = JOptionPane.showInputDialog("Digite o sabor da pizza (Sabores disponiveis: Estrogonofe, Calabresa, Portuguesa)");
                String tamanho = JOptionPane.showInputDialog("Escolha o tamanho da pizza (P, M, G):");
                int quantidade = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de pizzas:"));
                if (quantidade <= 0) {
                    JOptionPane.showMessageDialog(null, "A quantidade deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                Pizza pizza = new Pizza(saborPizza, tamanho);
                ItensPedido itemPedido = new ItensPedido(tamanho, quantidade, pizza);

                itemPedido.calcularValor();
                valorTotalPedido = valorTotalPedido.add(itemPedido.getValorTotal());

                pedido.adicionarItem(itemPedido);

                int opcao = JOptionPane.showConfirmDialog(null, "Deseja adicionar mais itens?", "Confirmação", JOptionPane.YES_NO_OPTION);
                adicionarMaisItens = (opcao == JOptionPane.YES_OPTION);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao adicionar item: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (!pedido.getItens().isEmpty()) {
            PedidoDAO pedidoDAO = new PedidoDAO();
            Pedido novoPedido = pedidoDAO.salvar(pedido);
            JOptionPane.showMessageDialog(null, "Pedido realizado com sucesso! ID: " + novoPedido.getId() + "\nValor total: R$ " + valorTotalPedido);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum item adicionado ao pedido. Pedido não será salvo.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    // CONSULTAR PEDIDO PELO CPF
    private static void consultarPedido() {
        String cpf = JOptionPane.showInputDialog("Digite o seu CPF:");

        if (cpf == null || cpf.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "CPF inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PedidoDAO pedidoDAO = new PedidoDAO();
        List<Pedido> pedidos = pedidoDAO.buscarPorCpf(cpf);

        if (pedidos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum pedido encontrado para o CPF informado.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            StringBuilder mensagem = new StringBuilder("Pedidos encontrados:\n");
            for (Pedido pedido : pedidos) {
                mensagem.append("Pedido #").append(pedido.getId())
                        .append(" - Status: ").append(pedido.getStatus())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, mensagem.toString());
        }
    }
}
