package com.yourbudgetapp.budgetapp.expenses;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class ExpenseService {
    private static List<Expense> expenses = new ArrayList<>();

    private static int expensesCount = 0;

    static {
        expenses.add(new Expense(++expensesCount,
                "leo", "groceries",
                LocalDate.now().plusDays(10), 100));
        expenses.add(new Expense(++expensesCount,
                "leo", "electricity",
                LocalDate.now().plusDays(20), 200));
        expenses.add(new Expense(++expensesCount,
                "leo", "gas",
                LocalDate.now().plusDays(30), 300));
    }

    public List<Expense> findByUsername(String username) {
        Predicate<? super Expense> predicate = expense -> expense.getUsername().equalsIgnoreCase(username);
        return expenses.stream().filter(predicate).toList();
    }

    public void addExpense(String username, String description, LocalDate dueDate, int amount) {
        int id = ++expensesCount;
        Expense expense = new Expense(id, username, description, dueDate, amount);
        expenses.add(expense);
    }

    public void deleteById(int id) {
        Predicate<? super Expense> predicate = expense -> expense.getId() == id;
        expenses.removeIf(predicate);
    }

    public Expense findById(int id) {
        Predicate<? super Expense> predicate = expense -> expense.getId() == id;
        return expenses.stream().filter(predicate).findFirst().get();
    }

    public void updateExpense(Expense expense) {
        deleteById((int)expense.getId());
        expenses.add(expense);
    }
}
