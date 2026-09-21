package com.medical.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.dto.DailyTransactionReport;
import com.medical.repository.BillRepository;
import com.medical.repository.PaymentRepository;
import com.medical.repository.PurchaseRepository;
import com.medical.repository.PurchaseReturnRepository;
import com.medical.repository.SaleReturnRepository;

@Service
public class DailyTransactionService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private SaleReturnRepository saleReturnRepository;

    @Autowired
    private PurchaseReturnRepository purchaseReturnRepository;

    public DailyTransactionReport getTodayReport() {

        DailyTransactionReport report =
                new DailyTransactionReport();

        LocalDate today = LocalDate.now();

        double sales =
                billRepository.findAll()
                .stream()
                .filter(b ->
                        b.getBillDate()!=null &&
                        b.getBillDate()
                         .toLocalDate()
                         .equals(today))
                .mapToDouble(b ->
                        b.getTotalAmount()==null
                        ? 0
                        : b.getTotalAmount())
                .sum();

        double purchases =
                purchaseRepository.findAll()
                .stream()
                .filter(p ->
                        p.getPurchaseDate()!=null &&
                        p.getPurchaseDate()
                         .equals(today))
                .mapToDouble(p ->
                        p.getTotalAmount()==null
                        ? 0
                        : p.getTotalAmount())
                .sum();

        double payments =
                paymentRepository.findAll()
                .stream()
                .filter(p ->
                        p.getPaymentDate()!=null &&
                        p.getPaymentDate()
                         .equals(today))
                .mapToDouble(p ->
                        p.getAmount()==null
                        ? 0
                        : p.getAmount())
                .sum();

        double saleReturns =
                saleReturnRepository.findAll()
                .stream()
                .filter(r ->
                        r.getReturnDate()!=null &&
                        r.getReturnDate()
                         .equals(today))
                .mapToDouble(r ->
                        r.getAmount()==null
                        ? 0
                        : r.getAmount())
                .sum();

        double purchaseReturns =
                purchaseReturnRepository.findAll()
                .stream()
                .filter(r ->
                        r.getReturnDate()!=null &&
                        r.getReturnDate()
                         .equals(today))
                .mapToDouble(r ->
                        r.getAmount()==null
                        ? 0
                        : r.getAmount())
                .sum();

        double profit =
                sales
                - purchases
                - saleReturns
                + purchaseReturns;

        report.setTotalSales(sales);
        report.setTotalPurchases(purchases);
        report.setTotalPayments(payments);
        report.setSaleReturns(saleReturns);
        report.setPurchaseReturns(purchaseReturns);
        report.setProfit(profit);

        return report;
    }
}