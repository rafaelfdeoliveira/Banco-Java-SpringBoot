package com.aula04.banco.banco.controller;

import com.aula04.banco.banco.dto.RequestCliente;
import com.aula04.banco.banco.dto.RequestDeposito;
import com.aula04.banco.banco.model.Cliente;
import com.aula04.banco.banco.service.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class OperacoesControllerTest {

    ClienteService clienteService = new ClienteService();
    OperacoesController operacoesController = new OperacoesController();
    Random random = new Random();

    @Test
    public void deveDepositarValorFornecido() throws Exception {
        RequestCliente requestCliente = new RequestCliente(
                "cinthia",
                "cinthiaqcardoso@teste.com",
                "44934586719",
                "54353",
                3);
        Cliente cliente = clienteService.cadastraCliente(requestCliente);
        Double value = random.nextDouble();
        RequestDeposito requestDeposito = new RequestDeposito(value, cliente.getContas().get(0).getId());
        operacoesController.deposita(cliente.getId(), requestDeposito);
        Assertions.assertEquals(cliente.getContas().get(0).getSaldo(), value);
    }
}
