package com.yourbudgetapp.budgetapp.expenses;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("name")
public class ExpenseControllerJpa {

    private ExpenseRepository expenseRepository;

    public ExpenseControllerJpa(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @RequestMapping("expenses")
    public String listAllExpenses(ModelMap model) {
        String username = getLoggedInUsername();
        List<Expense> expenses = expenseRepository.findByUsername(username);
        model.addAttribute("expenses", expenses);
        return "listExpenses";
    }

    @RequestMapping(value = "add-expense", method = RequestMethod.GET)
    public String showNewExpensePage(ModelMap model) {
        Expense expense = new Expense(25, getLoggedInUsername(), "", LocalDate.now(), 0);
        model.put("expense", expense);
        return "expense";
    }

    @RequestMapping(value = "add-expense", method = RequestMethod.POST)
    public String addNewExpense(@Valid Expense expense, BindingResult result) {
        System.out.println(expense.getId() + " This is the id");
        if (result.hasErrors()) {
            return "expense";
        }
        String username = getLoggedInUsername();
        expense.setUsername(username);
        expenseRepository.save(expense);
        return "redirect:expenses";
    }

    @RequestMapping("delete-expense")
    public String deleteExpense(@RequestParam int id) {
        expenseRepository.deleteById(id);
        return "redirect:expenses";
    }

    @RequestMapping(value = "update-expense", method = RequestMethod.GET)
    public String showUpdateExpensePage(@RequestParam int id, ModelMap model) {
        Expense expense = expenseRepository.findById(id).get();
        model.addAttribute("expense", expense);
        return "expense";
    }

    @RequestMapping(value = "update-expense", method = RequestMethod.POST)
    public String updateExpense(ModelMap model, @Valid Expense expense, BindingResult result) {
        if (result.hasErrors()) {
            return "expense";
        }
        String username = getLoggedInUsername();
        expense.setUsername(username);
        expenseRepository.save(expense);
        return "redirect:expenses";
    }

    private static String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
