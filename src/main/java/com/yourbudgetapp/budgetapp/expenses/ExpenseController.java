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

import java.time.LocalDate;
import java.util.List;

//@Controller
@SessionAttributes("name")
public class ExpenseController {

    private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @RequestMapping("expenses")
    public String listAllExpenses(ModelMap model) {
        String username = getLoggedInUsername();
        List<Expense> expenses = expenseService.findByUsername(username);
        model.addAttribute("expenses", expenses);
        return "listExpenses";
    }

    @RequestMapping(value = "add-expense", method = RequestMethod.GET)
    public String showNewExpensePage(ModelMap model) {
        Expense expense = new Expense(0, getLoggedInUsername(), "", LocalDate.now(), 0);
        model.put("expense", expense);
        return "expense";
    }

    @RequestMapping(value = "add-expense", method = RequestMethod.POST)
    public String addNewExpense(ModelMap model, @Valid Expense expense, BindingResult result) {
        if (result.hasErrors()) {
            return "expense";
        }
        String username = getLoggedInUsername();
        expenseService.addExpense(username, expense.getDescription(), expense.getDueDate(), expense.getAmount());
        return "redirect:expenses";
    }

    @RequestMapping("delete-expense")
    public String deleteExpense(@RequestParam int id) {
        expenseService.deleteById(id);
        return "redirect:expenses";
    }

    @RequestMapping(value = "update-expense", method = RequestMethod.GET)
    public String showUpdateExpensePage(@RequestParam int id, ModelMap model) {
        Expense expense = expenseService.findById(id);
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
        expenseService.updateExpense(expense);
        return "redirect:expenses";
    }

    private static String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
