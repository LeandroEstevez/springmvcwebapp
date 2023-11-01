<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<div class="container">
    <h1>Your expenses are</h1>
    <table class="table">
        <thead>
            <tr>
                <th>Description</th>
                <th>Due Date</th>
                <th>Amount</th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${expenses}" var="expense">
                <tr>
                    <td>${expense.description}</td>
                    <td>${expense.dueDate}</td>
                    <td>${expense.amount}</td>
                    <td><a href="delete-expense?id=${expense.id}" class="btn btn-warning">Delete</a></td>
                    <td><a href="update-expense?id=${expense.id}" class="btn btn-success">Update</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="add-expense" class="btn btn-success">Add Expense</a>
</div>

<%@ include file="common/footer.jspf" %>