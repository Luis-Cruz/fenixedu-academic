<%--

    Copyright © 2017 Instituto Superior Técnico

    This file is part of FenixEdu Academic.

    FenixEdu Academic is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    FenixEdu Academic is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with FenixEdu Academic.  If not, see <http://www.gnu.org/licenses/>.

--%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page trimDirectiveWhitespaces="true" %>

<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath() %>/CSS/accounting.css"/>

<spring:url var="payUrl" value="../../../{event}/pay">
    <spring:param name="event" value="${eventId}"/>
</spring:url>
<spring:url var="backUrl" value="../../../{event}/details">
    <spring:param name="event" value="${eventId}"/>
</spring:url>

<c:set var="totalAmount" value="#{debt.totalOpenAmount}"/>

<div class="container-fluid">
    <header>
        <h1>
            <jsp:include page="heading-event.jsp"/>
            <c:if test="${event.currentEventState == 'CANCELLED'}">
                <span class="text-danger"> <spring:message code="accounting.event.details.currentEventState.cancelled" text="(Cancelled)"/></span>
            </c:if>
        </h1>
    </header>
    <c:set var="person" scope="request" value="${event.person}"/>
    <jsp:include page="heading-person.jsp"/>
        <div class="row">
            <div class="col-md-10">
                <h2><c:out value="${debt.description}"/></h2>
            </div>
            <c:if test="${totalAmount > 0 && isEventOwner && event.currentEventState != 'CANCELLED'}">
                <div class="col-md-2">
                    <br/>
                    <a class="btn btn-primary btn-block" href="${payUrl}"><spring:message code="accounting.event.action.pay" text="Pay"/></a>
                </div>
            </c:if>
        </div>
    <div class="row">
        <div class="col-md-4 col-sm-12">
            <div class="overall-description">
                <dl>
                    <dt><spring:message code="accounting.event.details.amount.pay" text="To pay"/></dt>
                    <dd><c:out value="${totalAmount}"/><span>€</span></dd>
                </dl>
                <dl>
                    <dt><spring:message code="accounting.event.details.debt.amount.pay" text="Debt"/></dt>
                    <dd><c:out value="${debt.openAmount}"/><span>€</span></dd>
                </dl>
                <dl>
                    <dt><spring:message code="accounting.event.details.interestOrFines.amount.pay" text="Interest"/></dt>
                    <dd>
                        <c:out value="${debt.openInterestAmount.toPlainString() + debt.openFineAmount}"/><span>€</span>
                        <c:if test="${debt.isToExemptInterest() || debt.isToExemptFine()}">
                            <spring:message code="accounting.event.details.debt.hasExemption" text="(Exemption)"/>
                        </c:if>
                    </dd>
                </dl>
                <dl>
                    <dt><spring:message code="accounting.event.details.original.debt.amount" text="Original amount"/></dt>
                    <dd><c:out value="${debt.amount}"/><span>€</span></dd>
                </dl>
                <dl>
                    <dt><spring:message code="accounting.event.details.due.date" text="Due Date"/></dt>
                    <dd><time datetime="${debt.dueDate.toString("yyyy-MM-dd")}">${debt.dueDate.toString("dd/MM/yyyy")}</time></dd>
                </dl>
                <dl>
                    <dt><spring:message code="accounting.event.details.debt.paid" text="Paid"/></dt>
                    <c:set var="paidAmount" value="#{debt.paidAmount + debt.paidInterestAmount + debt.paidFineAmount}"/>
                    <dd><c:out value="${paidAmount}"/><span>€</span></dd>
                </dl>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <header>
                <h2><spring:message code="label.payments" text="Payments"/></h2>
            </header>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <section class="fee-payments">
                <table class="table">
                    <thead>
                    <tr>
                        <th><spring:message code="accounting.event.details.transactions.processingDate" text="Processing Date"/></th>
                        <th><spring:message code="accounting.event.details.transactions.effectiveDate" text="Effective Date"/></th>
                        <th><spring:message code="accounting.event.details.transactions.type" text="Type"/></th>
                        <th><spring:message code="accounting.event.details.debt.paid" text="Paid"/></th>
                        <th><spring:message code="accounting.event.details.transactions.debt" text="Debt"/></th>
                        <th><spring:message code="accounting.event.details.transactions.interestOrFine" text="Interest/Fine"/></th>
                        <th><span class="sr-only"><spring:message code="accounting.event.details.transactions.actions" text="Actions"/></span></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${empty payments}">
                        <tr>
                            <td colspan="5"><spring:message code="accounting.event.details.noPayments" text="There are no payments"/></td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty payments}">
                    <c:forEach var="accountingEntrySummary" items="${payments}">
                        <c:set var="amountUsedInInterestOrFine" value="#{accountingEntrySummary.amountUsedInInterest + accountingEntrySummary.amountUsedInFine}"/>
                        <tr>
                            <td><time datetime="${accountingEntrySummary.created.toString('yyyy-MM-dd HH:mm:ss')}"><c:out value="${accountingEntrySummary.created.toString('dd/MM/yyyy HH:mm:ss')}"/> </time></td>
                            <td><time datetime="${accountingEntrySummary.date.toString('yyyy-MM-dd')}"><c:out value="${accountingEntrySummary.date.toString('dd/MM/yyyy')}"/> </time></td>
                            <td><c:out value="${accountingEntrySummary.typeDescription.content}"/></td>
                            <td><c:out value="${accountingEntrySummary.amount.toPlainString()}"/><span>€</span></td>
                            <td><c:out value="${accountingEntrySummary.amountUsedInDebt.toPlainString()}"/><span>€</span></td>
                            <td><c:out value="${amountUsedInInterestOrFine}"/><span>€</span></td>
                            <spring:url value="../../../{event}/transaction/{id}/details" var="paymentUrl" scope="request">
                                <spring:param name="event" value="${eventId}"/>
                                <spring:param name="id" value="${accountingEntrySummary.id}"/>
                            </spring:url>
                            <td><a href="${paymentUrl}"><spring:message code="label.details"/></a></td>
                        </tr>
                    </c:forEach>
                    </c:if>
                    </tbody>
                </table>
            </section>
        </div>
    </div>
</div>
