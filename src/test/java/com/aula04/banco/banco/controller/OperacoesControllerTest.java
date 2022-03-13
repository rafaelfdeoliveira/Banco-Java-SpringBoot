package com.aula04.banco.banco.controller;

import com.aula04.banco.banco.dto.RequestCliente;
import com.aula04.banco.banco.dto.RequestDeposito;
import com.aula04.banco.banco.dto.RequestSaque;
import com.aula04.banco.banco.model.Cliente;
import com.aula04.banco.banco.service.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Random;

public class OperacoesControllerTest {

    ClienteService clienteService = new ClienteService();
    OperacoesController operacoesController = new OperacoesController();
    Random random = new Random();

    @Test
    public void deveDepositarValorFornecido() {
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

    @Test
    public void deveSacarValorFornececido() {
        RequestCliente requestCliente = new RequestCliente(
                "cinthia",
                "cinthiaqcardoso@teste.com",
                "44934586719",
                "54353",
                3
        );
        Cliente cliente = clienteService.cadastraCliente(requestCliente);
        Double value = random.nextDouble();
        RequestDeposito requestDeposito = new RequestDeposito(value, cliente.getContas().get(0).getId());
        operacoesController.deposita(cliente.getId(), requestDeposito);
        RequestSaque requestSaque = new RequestSaque(value / 2, cliente.getContas().get(0).getId());
        operacoesController.saca(cliente.getId(), requestSaque);

        Assertions.assertEquals(cliente.getContas().get(0).getSaldo(), value / 2);
    }

    @Test
    public void naoDeveSacarMaisQueValorDisponivel() {
        RequestCliente requestCliente = new RequestCliente(
                "cinthia",
                "cinthiaqcardoso@teste.com",
                "44934586719",
                "54353",
                4
        );
        Cliente cliente = clienteService.cadastraCliente(requestCliente);
        Double value = random.nextDouble();
        RequestDeposito requestDeposito = new RequestDeposito(value, cliente.getContas().get(0).getId());
        operacoesController.deposita(cliente.getId(), requestDeposito);
        RequestSaque requestSaque = new RequestSaque(value + 1, cliente.getContas().get(0).getId());
        operacoesController.saca(cliente.getId(), requestSaque);

        Assertions.assertEquals(cliente.getContas().get(0).getSaldo(), value);
    }
}
