package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.domain.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {

    void preencherPagamentoComBoleto(PagamentoComBoleto pagamentoComBoleto, Date instante) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(instante);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        pagamentoComBoleto.setDataVencimento(calendar.getTime());
    }
}
