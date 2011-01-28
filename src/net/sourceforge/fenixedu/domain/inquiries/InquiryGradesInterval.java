/**
 * 
 */
package net.sourceforge.fenixedu.domain.inquiries;

/**
 * @author - Ricardo Rodrigues (ricardo.rodrigues@ist.utl.pt)
 * 
 */
public enum InquiryGradesInterval {

    //intervals are in the form of [x, y[, [y, z[
    DONT_KNOW, NOT_EVALUATED, FLUNKED, FROM_100_TO_110, FROM_110_TO_120, FROM_120_TO_130, FROM_130_TO_140, FROM_140_TO_150, FROM_150_TO_160, FROM_160_TO_170, FROM_170_TO_180, FROM_180_TO_200;

    final static public InquiryGradesInterval getInterval(Double grade) {
	if (grade == null) {
	    return null;
	}
	if (grade < 100) {
	    return FLUNKED;
	}
	if (grade >= 100 && grade < 110) {
	    return FROM_100_TO_110;
	}
	if (grade >= 110 && grade < 120) {
	    return FROM_110_TO_120;
	}
	if (grade >= 120 && grade < 130) {
	    return FROM_120_TO_130;
	}
	if (grade >= 130 && grade < 140) {
	    return FROM_130_TO_140;
	}
	if (grade >= 140 && grade < 150) {
	    return FROM_140_TO_150;
	}
	if (grade >= 150 && grade < 160) {
	    return FROM_150_TO_160;
	}
	if (grade >= 170 && grade < 180) {
	    return FROM_120_TO_130;
	}
	if (grade >= 180) {
	    return FROM_180_TO_200;
	}
	return DONT_KNOW;
    }
}
