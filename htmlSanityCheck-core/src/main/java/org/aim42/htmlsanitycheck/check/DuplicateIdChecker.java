package org.aim42.htmlsanitycheck.check;

import org.aim42.htmlsanitycheck.Configuration;
import org.aim42.htmlsanitycheck.collect.SingleCheckResults;
import org.aim42.htmlsanitycheck.html.HtmlPage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DuplicateIdChecker extends Checker {
    private Set<String> idStringsSet;
    private List<String> idStringsList;

    public DuplicateIdChecker(Configuration pConfig) {
        super(pConfig);
    }

    @Override
    protected void initCheckingResultsDescription() {
        getCheckingResults().setWhatIsChecked("Duplicate Definition of id Check");
        getCheckingResults().setSourceItemName("id");
        getCheckingResults().setTargetItemName("duplicate id");
    }

    @Override
    protected SingleCheckResults check(final HtmlPage pageToCheck) {

        //get list of all tagsWithId '<... id="XYZ"...' in html file

        idStringsList = pageToCheck.getAllIdStrings();
        idStringsSet = new HashSet<>(idStringsList);

        checkForDuplicateIds(idStringsSet);

        return getCheckingResults();

    }

    private void checkForDuplicateIds(Set<String> idStringsSet) {
        idStringsSet.forEach(this::checkForDuplicateDefinition);
    }

    private void checkForDuplicateDefinition(final String idString) {
        getCheckingResults().incNrOfChecks();

        int nrOfOccurrences = (int) idStringsList.stream().filter(it -> it.equals(idString)).count();

        // duplicate, IFF idString appears more than once in idStringsList
        if (nrOfOccurrences > 1) {
            getCheckingResults().newFinding("id \"" + idString + "\" has " + nrOfOccurrences + " definitions.");
        }

    }

    public Set<String> getIdStringsSet() {
        return idStringsSet;
    }

    public void setIdStringsSet(Set<String> idStringsSet) {
        this.idStringsSet = idStringsSet;
    }

    public List<String> getIdStringsList() {
        return idStringsList;
    }

    public void setIdStringsList(List<String> idStringsList) {
        this.idStringsList = idStringsList;
    }
}
