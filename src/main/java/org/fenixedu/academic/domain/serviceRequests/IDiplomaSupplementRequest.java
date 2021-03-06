/**
 * Copyright © 2002 Instituto Superior Técnico
 *
 * This file is part of FenixEdu Academic.
 *
 * FenixEdu Academic is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu Academic is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu Academic.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.fenixedu.academic.domain.serviceRequests;

import java.util.Locale;

import org.fenixedu.academic.domain.DegreeOfficialPublication;
import org.fenixedu.academic.domain.ExecutionYear;
import org.fenixedu.academic.domain.degreeStructure.EctsGraduationGradeConversionTable;
import org.fenixedu.academic.domain.student.Registration;

public interface IDiplomaSupplementRequest extends IProgramConclusionRequest {
    Integer getRegistrationNumber();

    String getPrevailingScientificArea(final Locale locale);

    double getEctsCredits();

    DegreeOfficialPublication getDegreeOfficialPublication();

    Integer getFinalAverage();

    String getFinalAverageQualified(final Locale locale);

    ExecutionYear getConclusionYear();

    ExecutionYear getStartYear();

    EctsGraduationGradeConversionTable getGraduationConversionTable();

    Integer getNumberOfCurricularYears();

    Integer getNumberOfCurricularSemesters();

    Boolean isExemptedFromStudy();

    Registration getRegistration();

    boolean hasRegistration();

}
