package net.sourceforge.fenixedu.presentationTier.Action.candidacy.graduatedPerson;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.fenixedu.applicationTier.Filtro.exception.FenixFilterException;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.dataTransferObject.person.ChoosePersonBean;
import net.sourceforge.fenixedu.dataTransferObject.person.PersonBean;
import net.sourceforge.fenixedu.domain.candidacy.CandidacyInformationBean;
import net.sourceforge.fenixedu.domain.candidacyProcess.CandidacyPrecedentDegreeInformationBean;
import net.sourceforge.fenixedu.domain.candidacyProcess.IndividualCandidacyProcess;
import net.sourceforge.fenixedu.domain.candidacyProcess.graduatedPerson.DegreeCandidacyForGraduatedPersonIndividualCandidacyResultBean;
import net.sourceforge.fenixedu.domain.candidacyProcess.graduatedPerson.DegreeCandidacyForGraduatedPersonIndividualProcess;
import net.sourceforge.fenixedu.domain.candidacyProcess.graduatedPerson.DegreeCandidacyForGraduatedPersonIndividualProcessBean;
import net.sourceforge.fenixedu.domain.candidacyProcess.graduatedPerson.DegreeCandidacyForGraduatedPersonProcess;
import net.sourceforge.fenixedu.domain.exceptions.DomainException;
import net.sourceforge.fenixedu.presentationTier.Action.candidacy.IndividualCandidacyProcessDA;
import net.sourceforge.fenixedu.presentationTier.formbeans.FenixActionForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.struts.annotations.Forward;
import pt.ist.fenixWebFramework.struts.annotations.Forwards;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;

@Mapping(path = "/caseHandlingDegreeCandidacyForGraduatedPersonIndividualProcess", module = "academicAdminOffice", formBeanClass = FenixActionForm.class)
@Forwards( { @Forward(name = "intro", path = "/candidacy/mainCandidacyProcess.jsp"),
	@Forward(name = "list-allowed-activities", path = "/candidacy/listIndividualCandidacyActivities.jsp"),
	@Forward(name = "prepare-create-new-process", path = "/candidacy/selectPersonForCandidacy.jsp"),
	@Forward(name = "fill-personal-information", path = "/candidacy/fillPersonalInformation.jsp"),
	@Forward(name = "fill-common-candidacy-information", path = "/candidacy/fillCommonCandidacyInformation.jsp"),
	@Forward(name = "fill-candidacy-information", path = "/candidacy/graduatedPerson/fillCandidacyInformation.jsp"),
	@Forward(name = "prepare-candidacy-payment", path = "/candidacy/candidacyPayment.jsp"),
	@Forward(name = "edit-candidacy-personal-information", path = "/candidacy/editPersonalInformation.jsp"),
	@Forward(name = "edit-candidacy-information", path = "/candidacy/graduatedPerson/editCandidacyInformation.jsp"),
	@Forward(name = "introduce-candidacy-result", path = "/candidacy/graduatedPerson/introduceCandidacyResult.jsp"),
	@Forward(name = "cancel-candidacy", path = "/candidacy/cancelCandidacy.jsp"),
	@Forward(name = "create-registration", path = "/candidacy/createRegistration.jsp"),
	@Forward(name = "prepare-edit-candidacy-documents", path = "/candidacy/editCandidacyDocuments.jsp"),
	@Forward(name = "select-person-for-bind-with-candidacy", path = "/candidacy/selectPersonForBind.jsp"),
	@Forward(name = "edit-personal-information-for-bind", path = "/candidacy/editPersonalInformationForCandidacyBind.jsp"),
	@Forward(name = "change-process-checked-state", path = "/candidacy/changeProcessCheckedState.jsp") })
public class DegreeCandidacyForGraduatedPersonIndividualProcessDA extends IndividualCandidacyProcessDA {

    @Override
    protected Class getParentProcessType() {
	return DegreeCandidacyForGraduatedPersonProcess.class;
    }

    @Override
    protected Class getProcessType() {
	return DegreeCandidacyForGraduatedPersonIndividualProcess.class;
    }

    @Override
    protected DegreeCandidacyForGraduatedPersonProcess getParentProcess(HttpServletRequest request) {
	return (DegreeCandidacyForGraduatedPersonProcess) super.getParentProcess(request);
    }

    @Override
    protected DegreeCandidacyForGraduatedPersonIndividualProcess getProcess(HttpServletRequest request) {
	return (DegreeCandidacyForGraduatedPersonIndividualProcess) super.getProcess(request);
    }

    @Override
    protected DegreeCandidacyForGraduatedPersonIndividualProcessBean getIndividualCandidacyProcessBean() {
	return (DegreeCandidacyForGraduatedPersonIndividualProcessBean) super.getIndividualCandidacyProcessBean();
    }

    @Override
    protected void setStartInformation(ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	final DegreeCandidacyForGraduatedPersonIndividualProcessBean bean = new DegreeCandidacyForGraduatedPersonIndividualProcessBean();
	bean.setCandidacyProcess(getParentProcess(request));

	/*
	 * 21/07/2009 - Now we create a person to process the payments
	 * imediately
	 */
	bean.setChoosePersonBean(new ChoosePersonBean());
	bean.setPersonBean(new PersonBean());
	bean.setCandidacyInformationBean(new CandidacyInformationBean());
	bean.setPrecedentDegreeInformation(new CandidacyPrecedentDegreeInformationBean());

	/*
	 * 06/05/2009 - Also we mark the bean as an external candidacy.
	 */
	bean.setInternalPersonCandidacy(Boolean.TRUE);
	request.setAttribute(getIndividualCandidacyProcessBeanName(), bean);
    }

    public ActionForward prepareExecuteEditCandidacyPersonalInformation(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	final DegreeCandidacyForGraduatedPersonIndividualProcessBean bean = new DegreeCandidacyForGraduatedPersonIndividualProcessBean();
	bean.setPersonBean(new PersonBean(getProcess(request).getPersonalDetails()));
	request.setAttribute(getIndividualCandidacyProcessBeanName(), bean);
	return mapping.findForward("edit-candidacy-personal-information");
    }

    public ActionForward executeEditCandidacyPersonalInformationInvalid(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	return mapping.findForward("edit-candidacy-personal-information");
    }

    public ActionForward executeEditCandidacyPersonalInformation(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) throws FenixFilterException, FenixServiceException {
	try {
	    executeActivity(getProcess(request), "EditCandidacyPersonalInformation", getIndividualCandidacyProcessBean());
	} catch (final DomainException e) {
	    addActionMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	    return mapping.findForward("edit-candidacy-personal-information");
	}
	return listProcessAllowedActivities(mapping, actionForm, request, response);
    }

    public ActionForward prepareExecuteEditCommonCandidacyInformation(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	final DegreeCandidacyForGraduatedPersonIndividualProcessBean bean = new DegreeCandidacyForGraduatedPersonIndividualProcessBean();
	bean.setCandidacyInformationBean(getProcess(request).getCandidacyInformationBean());
	request.setAttribute(getIndividualCandidacyProcessBeanName(), bean);
	return mapping.findForward("edit-common-candidacy-information");
    }

    public ActionForward executeEditCommonCandidacyInformationInvalid(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	return mapping.findForward("edit-common-candidacy-information");
    }

    public ActionForward executeEditCommonCandidacyInformation(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response) throws FenixFilterException, FenixServiceException {
	try {
	    final Set<String> messages = getIndividualCandidacyProcessBean().getCandidacyInformationBean().validate();
	    if (!messages.isEmpty()) {
		for (final String message : messages) {
		    addActionMessage(request, message);
		}
		request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
		return mapping.findForward("edit-common-candidacy-information");
	    }

	    executeActivity(getProcess(request), "EditCommonCandidacyInformation", getIndividualCandidacyProcessBean());

	} catch (DomainException e) {
	    addActionMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	    return mapping.findForward("edit-common-candidacy-information");
	}
	return listProcessAllowedActivities(mapping, form, request, response);
    }

    public ActionForward prepareExecuteEditCandidacyInformation(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	DegreeCandidacyForGraduatedPersonIndividualProcess process = getProcess(request);
	DegreeCandidacyForGraduatedPersonIndividualProcessBean bean = new DegreeCandidacyForGraduatedPersonIndividualProcessBean(
		process);
	bean.setCandidacyInformationBean(new CandidacyInformationBean(process.getCandidacy()));
	request.setAttribute(getIndividualCandidacyProcessBeanName(), bean);
	return mapping.findForward("edit-candidacy-information");
    }

    public ActionForward executeEditCandidacyInformationInvalid(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	return mapping.findForward("edit-candidacy-information");
    }

    public ActionForward executeEditCandidacyInformation(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) throws FenixFilterException, FenixServiceException {
	try {
	    DegreeCandidacyForGraduatedPersonIndividualProcessBean bean = getIndividualCandidacyProcessBean();
	    copyPrecedentBeanToCandidacyInformationBean(bean.getPrecedentDegreeInformation(), bean.getCandidacyInformationBean());
	    executeActivity(getProcess(request), "EditCandidacyInformation", getIndividualCandidacyProcessBean());
	} catch (final DomainException e) {
	    addActionMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	    return mapping.findForward("edit-candidacy-information");
	}

	return listProcessAllowedActivities(mapping, actionForm, request, response);
    }

    public ActionForward prepareExecuteIntroduceCandidacyResult(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	request.setAttribute("individualCandidacyResultBean", new DegreeCandidacyForGraduatedPersonIndividualCandidacyResultBean(
		getProcess(request)));
	return mapping.findForward("introduce-candidacy-result");
    }

    public ActionForward executeIntroduceCandidacyResultInvalid(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	request.setAttribute("individualCandidacyResultBean", getCandidacyResultBean());
	return mapping.findForward("introduce-candidacy-result");
    }

    public ActionForward executeIntroduceCandidacyResult(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) throws FenixFilterException, FenixServiceException {

	try {
	    executeActivity(getProcess(request), "IntroduceCandidacyResult", getCandidacyResultBean());
	} catch (final DomainException e) {
	    addActionMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute("individualCandidacyResultBean", getCandidacyResultBean());
	    return mapping.findForward("introduce-candidacy-result");
	}

	return listProcessAllowedActivities(mapping, actionForm, request, response);
    }

    private DegreeCandidacyForGraduatedPersonIndividualCandidacyResultBean getCandidacyResultBean() {
	return (DegreeCandidacyForGraduatedPersonIndividualCandidacyResultBean) getRenderedObject("individualCandidacyResultBean");
    }

    public ActionForward prepareExecuteCreateRegistration(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) throws FenixFilterException, FenixServiceException {
	request.setAttribute("degree", getProcess(request).getCandidacySelectedDegree());
	return mapping.findForward("create-registration");
    }

    public ActionForward executeCreateRegistration(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
	    HttpServletResponse response) throws FenixFilterException, FenixServiceException {
	try {
	    executeActivity(getProcess(request), "CreateRegistration");
	} catch (final DomainException e) {
	    addActionMessage(request, e.getMessage(), e.getArgs());
	    request.setAttribute("degree", getProcess(request).getCandidacySelectedDegree());
	    return mapping.findForward("create-registration");
	}
	return listProcessAllowedActivities(mapping, actionForm, request, response);
    }

    @Override
    /*
     * * Prepare the beans to choose a person or create a new one
     */
    protected void prepareInformationForBindPersonToCandidacyOperation(HttpServletRequest request,
	    IndividualCandidacyProcess process) {
	final DegreeCandidacyForGraduatedPersonIndividualProcessBean bean = new DegreeCandidacyForGraduatedPersonIndividualProcessBean(
		(DegreeCandidacyForGraduatedPersonIndividualProcess) process);
	bean.setCandidacyProcess(getParentProcess(request));

	bean.setChoosePersonBean(new ChoosePersonBean(process.getCandidacy().getPersonalDetails()));
	bean.setPersonBean(new PersonBean(process.getCandidacy().getPersonalDetails()));

	request.setAttribute(getIndividualCandidacyProcessBeanName(), bean);
    }

    @Override
    public ActionForward createNewProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	    HttpServletResponse response) throws FenixFilterException, FenixServiceException {
	DegreeCandidacyForGraduatedPersonIndividualProcessBean bean = (DegreeCandidacyForGraduatedPersonIndividualProcessBean) getIndividualCandidacyProcessBean();

	boolean isValid = hasInvalidViewState();
	if (!isValid) {
	    invalidateDocumentFileRelatedViewStates();
	    request.setAttribute(getIndividualCandidacyProcessBeanName(), getIndividualCandidacyProcessBean());
	    return mapping.findForward("fill-candidacy-information");
	}

	copyPrecedentBeanToCandidacyInformationBean(bean.getPrecedentDegreeInformation(), bean.getCandidacyInformationBean());

	return super.createNewProcess(mapping, form, request, response);
    }

    public ActionForward prepareExecuteChangeProcessCheckedState(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	request.setAttribute(getIndividualCandidacyProcessBeanName(), new DegreeCandidacyForGraduatedPersonIndividualProcessBean(
		getProcess(request)));

	return mapping.findForward("change-process-checked-state");
    }

}
