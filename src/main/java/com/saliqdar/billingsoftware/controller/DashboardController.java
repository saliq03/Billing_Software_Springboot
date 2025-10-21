package com.saliqdar.billingsoftware.controller;

import com.saliqdar.billingsoftware.io.DashboardResponse;
import com.saliqdar.billingsoftware.io.OrderResponse;
import com.saliqdar.billingsoftware.repository.OrderRepository;
import com.saliqdar.billingsoftware.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public DashboardResponse getDashboardData( @RequestParam(defaultValue = "0") int skip, @RequestParam(defaultValue = "10") int limit){
        LocalDate today = LocalDate.now();
        Double todaySale= orderService.sumSalesByDate(today);
        Long todayOrdersCount=orderService.countOrderByDate(today);
        List<OrderResponse> recentOrders=orderService.findRecentOrders(skip,limit);
        return new DashboardResponse(
              todaySale!=null?todaySale:0.0,
              todayOrdersCount!=null?todayOrdersCount:0,
              recentOrders
        );
    }
}
