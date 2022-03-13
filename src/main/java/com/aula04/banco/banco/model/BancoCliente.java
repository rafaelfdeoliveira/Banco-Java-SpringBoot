package com.aula04.banco.banco.model;

import com.aula04.banco.banco.dto.RequestCliente;
import com.aula04.banco.banco.dto.RequestDeposito;
import com.aula04.banco.banco.dto.RequestSaque;

import java.util.*;

public class BancoCliente {
    private static List<Cliente> clientes = new ArrayList<>();

    public void adiciona(Cliente cliente){
        BancoCliente.clientes.add(cliente);
    }

    public List<Cliente> buscaClientes(){
        return BancoCliente.clientes;
    }

    public Cliente detalhesCliente(UUID id) throws Exception {
           Optional<Cliente> resultCliente =
                   BancoCliente.clientes.stream().filter(cliente -> Objects.equals(cliente.getId(),id)).findAny();
           if(resultCliente.isPresent()){
               return resultCliente.get();
           } else {
               throw new Exception("Usuario nao encontrado");
           }
    }

    public Cliente atualizaCliente(UUID id, RequestCliente requestCliente) throws Exception {
        BancoCliente.clientes.stream().filter(cliente -> Objects.equals(cliente.getId(),id))
                .forEach(cliente -> {
                    cliente.setNome(requestCliente.getNome());
                    cliente.setEmail(requestCliente.getEmail());
                    cliente.setSenha(requestCliente.getSenha());
                });
        return detalhesCliente(id);
    }
    public void deletaCliente(UUID id) throws Exception {
        Cliente cliente = detalhesCliente(id);
        BancoCliente.clientes.remove(cliente);
    }
    public void deposita(UUID id, RequestDeposito requestDeposito) {
        BancoCliente.clientes.stream().filter(cliente -> Objects.equals(cliente.getId(),id))
                .forEach(cliente -> {
                    Optional<Conta> resultConta = cliente.getContas().stream().filter(conta -> Objects.equals(conta.getId(),requestDeposito.getConta())).findAny();
                   if(resultConta.isPresent()) {
                       Double novoSaldo = resultConta.get().getSaldo() + requestDeposito.getValor();
                       resultConta.get().setSaldo(novoSaldo);
                   } else {
                       this.throwAndHandleException("Conta não encontrada");
                   }
                });
    }

    public void saca(UUID id, RequestSaque requestSaque) {
        BancoCliente.clientes.stream().filter(cliente -> Objects.equals(cliente.getId(), id))
                .forEach(cliente -> {
                    Optional<Conta> resultConta = cliente.getContas().stream().filter(conta -> Objects.equals(conta.getId(), requestSaque.getConta())).findAny();
                    if (resultConta.isEmpty()) {
                        this.throwAndHandleException("Conta não encontrada");
                    } else if (resultConta.get().getSaldo() < requestSaque.getValor()) {
                        this.throwAndHandleException("Valor a sacar não pode ser maior que o valor disponível na conta");
                    } else {
                        Double novoSaldo = resultConta.get().getSaldo() - requestSaque.getValor();
                        resultConta.get().setSaldo(novoSaldo);
                    }
                });
    }

    private void throwAndHandleException(String message) {
        try {
            throw new Exception(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
