<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<div class="container">
    <h1>Enter Expense details</h1>
    <form:form method="post" modelAttribute="expense">
        <fieldset class="mb-3">
            <form:label path="description">Description</form:label>
            <form:input type="text" path="description" required="required"/>
            <form:errors path="description" cssClass="text-warning"/>
        </fieldset>
        <fieldset class="mb-3">
            <form:label path="dueDate">Due Date</form:label>
            <form:input type="text" path="dueDate" required="required"/>
            <form:errors path="dueDate" cssClass="text-warning"/>
        </fieldset>
        <fieldset class="mb-3">
            <form:label path="amount">Amount</form:label>
            <form:input type="number" path="amount" required="required"/>
            <form:errors path="amount" cssClass="text-warning"/>
        </fieldset>
        <form:input type="hidden" path="id"/>
        <input type="submit" class="btn btn-success">
    </form:form>
</div>

<%@ include file="common/footer.jspf" %>

<script type="text/javascript">
    $('#dueDate').datepicker({
        format: 'dd-mm-yyyy'
    });
</script>